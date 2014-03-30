package ca.gc.aafc.seqdb_barcode_scanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.gc.aafc.seqdb_barcode_scanner.entities.Container;
import ca.gc.aafc.seqdb_barcode_scanner.entities.Location;
import ca.gc.aafc.seqdb_barcode_scanner.entities.MixedSpecimen;
import ca.gc.aafc.seqdb_barcode_scanner.entities.PcrPrimer;
import ca.gc.aafc.seqdb_barcode_scanner.entities.Sample;
import ca.gc.aafc.seqdb_barcode_scanner.entities.SpecimenReplicate;
import ca.gc.aafc.seqdb_barcode_scanner.service.EntityServiceI;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import ca.gc.aafc.seqdb_barcode_scanner.utils.*;

public class MoveActivity extends FragmentActivity implements GetContentFragment.OnContentSelectedListener, ServiceTask.OnServiceCallCompletedListener{
	TextView header_title;
	ImageButton button_mainMenu;
	EditText et_well_row;
	EditText et_well_col;
	Button button_cancel;
	Button button_autofill;

	private DataParser parser;
	private Location itemLocation;
	private Container contentContainer;
	private Location updatedLocation;
	//private ContainerType contentContainerType;
	Session moveSession;
	static final String SESSION_TYPE = "MOVE";
	static final String SCAN_TYPE_ITEM = "SCAN_ITEM";
	static final String SCAN_TYPE_CONTAINER = "SCAN_CONTAINER";
	private String entityType = "";
	private String containerType = "";
	long containerId;
	long itemId;
	private GetContentFragment getContentFragment;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_move);
		//initialize fragment to display table
		getContentFragment = (GetContentFragment) getSupportFragmentManager().findFragmentById(R.id.get_content_fragment);
		
		//instantiate and set views
		button_mainMenu = (ImageButton) findViewById(R.id.btn_header_menu);
		button_mainMenu.setOnClickListener(Button_Click_Listener);

		header_title = (TextView) findViewById(R.id.tv_header_main_title);
		header_title.setText("MOVE");

		button_autofill = (Button) findViewById(R.id.btn_ignore_well_info);
		button_autofill.setOnClickListener(Button_Click_Listener);

		button_cancel = (Button) findViewById(R.id.btn_cancel_move);
		button_cancel.setOnClickListener(Button_Click_Listener);

		moveSession = new Session(this,SESSION_TYPE);

		//launch scanner screen with message
		Toast.makeText(MoveActivity.this, "Please scan an item to move", Toast.LENGTH_LONG).show();
		launchScanner(SCAN_TYPE_ITEM);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

	}

	OnClickListener Button_Click_Listener = new OnClickListener(){
		public void onClick(View v){
			int id_of_view = v.getId();
			//Cancel button
			if(id_of_view == button_cancel.getId()){
				finish();
			}
			//main menu button
			else if(id_of_view == button_mainMenu.getId()){
				finish();
			}
			//Autofill button
			/*
			 * TODO TO BE COMPLETED WHEN WEB SERVICE HANDLES AUTOFILLING
			else if(id_of_view == button_autofill.getId()){
				ArrayList<Location> locationArray = contentContainer.getlocationList();
				Toast.makeText(MoveActivity.this, "Autofill button pressed", Toast.LENGTH_LONG).show();
			}
			*/
			else{
				Toast.makeText(MoveActivity.this, "This action is not authorized", Toast.LENGTH_LONG).show();
			}
		}
	};

	//Called when scanning is complete
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		// Make sure the request was successful
		if (resultCode == RESULT_OK) {
			Bundle resultBundle = data.getExtras();
			String decodedData = resultBundle.getString("DATA_RESULT");
			String scanAction = resultBundle.getString("SCAN_ACTION");

			//Store scanned item ID in session
			String current_item = this.moveSession.getSession().getString("MOVE_ITEM", "");
			String current_container = this.moveSession.getSession().getString("MOVE_CONTAINER", "");
			
			//Check if item scanner
			if(scanAction != null && scanAction.equalsIgnoreCase(SCAN_TYPE_ITEM)){
				if(decodedData != null){
					try{
						//Parsing item
						parser = new DataParser();
						parser.parse(decodedData);
						entityType = parser.getAcronym();
						itemId = parser.getId();
					}
					catch(Exception e){
						Toast.makeText(this, "Unknown barcode format, please scan an item", Toast.LENGTH_LONG).show();
						e.printStackTrace();
						launchScanner(SCAN_TYPE_ITEM);
					}
					if(entityType.equalsIgnoreCase("MSP")){
						//Store move item in session
						this.moveSession.getSessionEditor().putString("MOVE_ITEM", decodedData);
						this.moveSession.getSessionEditor().commit();

						//Launch container scanner screen
						Toast.makeText(MoveActivity.this, "Please scan a container", Toast.LENGTH_LONG).show();
						this.launchScanner(SCAN_TYPE_CONTAINER);
					}
					else{
						//Re-launch item scanner screen
						Toast.makeText(MoveActivity.this, "Unknown barcode format, please scan an item", Toast.LENGTH_LONG).show();
						this.launchScanner(SCAN_TYPE_ITEM);
					}
				}
				else{
					//Re-launch item scanner screen
					Toast.makeText(MoveActivity.this, "Item not found", Toast.LENGTH_LONG).show();
					this.launchScanner(SCAN_TYPE_ITEM);
				}
			}
			
			//Check if container scanner
			else  if(scanAction != null && scanAction.equalsIgnoreCase(SCAN_TYPE_CONTAINER)){
				//If item is empty ask to scan item again			    
				if(!current_item.equalsIgnoreCase("") && decodedData != null){
					this.moveSession.getSessionEditor().putString("MOVE_CONTAINER", decodedData);
					this.moveSession.getSessionEditor().commit();
					try{			
						//Parsing container
						parser = new DataParser();
						parser.parse(decodedData);
						containerType = parser.getAcronym();
						containerId = parser.getId();
						
						//Verify it is a container
						if(containerType.equalsIgnoreCase("CON") || containerType.equalsIgnoreCase("07")){
							//Item service
							EntityServiceI itemService = moveSession.getService(entityType);
							if(itemService != null){
								ServiceTask taskRunner = new ServiceTask(this);
								taskRunner.setService(itemService);
								HashMap<String,Object> params = new HashMap<String,Object>();
								params.put(ServiceTask.GET_BY_ID, itemId);
								taskRunner.execute(params);
							}
							
							//Container service
							EntityServiceI containerService = moveSession.getService(containerType);
							if(containerService != null){
								ServiceTask containerTaskRunner = new ServiceTask(this);
								containerTaskRunner.setService(containerService);
								HashMap<String,Object> params = new HashMap<String,Object>();
								params.put(ServiceTask.GET_CONTAINER_BY_ID, containerId);
								containerTaskRunner.execute(params);
							}
						}	
						else{
							Toast.makeText(this, "Unknown barcode format, please scan an item", Toast.LENGTH_LONG).show();
							launchScanner(SCAN_TYPE_ITEM);
						}
					}
					catch(Exception e){
						Toast.makeText(this, "An error occured, please scan an item", Toast.LENGTH_LONG).show();
						e.printStackTrace();
						launchScanner(SCAN_TYPE_ITEM);
					}
				}
				else{
					Toast.makeText(MoveActivity.this, "Please scan an item", Toast.LENGTH_LONG).show();
					System.out.print("Current item is empty; rescan item");
					this.launchScanner("SCAN_TYPE_ITEM");
				}
			}
			else{
				Toast.makeText(MoveActivity.this, "No scanning action, please scan an item", Toast.LENGTH_LONG).show();
				this.launchScanner("SCAN_TYPE_ITEM");
			}		   
		}
		else{
			finish();
		}
	}

	@Override
	public void onBackPressed() {
		finish();
	}

	@Override
	public void onContentSelected(String id, String row, int column,boolean state) {
		boolean isFull = state;
		
		//move item to this location in the container
		if (!isFull){		
				//update location
				itemLocation.setWellRow(row);
				itemLocation.setWellColumn(column);
				itemLocation.setContainerId(contentContainer.getId());
				//update container
				ArrayList<Location> tempList= contentContainer.getlocationList();
				tempList.add(itemLocation);
				contentContainer.setlocationList(tempList);

				try{
					EntityServiceI locationService = moveSession.getService("LOC");

					if(locationService != null){
						//updating location
						ServiceTask taskRunner = new ServiceTask(this);
						taskRunner.setService(locationService);
						HashMap<String,Object> params = new HashMap<String,Object>();
						params.put(ServiceTask.UPDATE, itemLocation);
						taskRunner.execute(params);
					}
				}
				catch(Exception e){
					Toast.makeText(this, "Error while moving item, please select another location", Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}
		}
		else{
			Toast.makeText(MoveActivity.this, "Please select an empty location", Toast.LENGTH_LONG).show();
		}
	}

	private void launchScanner(String action){
		Intent intent = new Intent(MoveActivity.this, ScannerActivity.class);
		intent.putExtra("SCAN_ACTION", action);
		startActivityForResult(intent,0);
	}

	@Override
	public void onServiceCalled(String method, Object output) {
		if(method.equalsIgnoreCase(ServiceTask.GET_BY_ID)){
			if(output !=null && !entityType.isEmpty()){
				if(entityType.equalsIgnoreCase("MSP") || entityType.equalsIgnoreCase("03")){
					MixedSpecimen mixedSpecimen = (MixedSpecimen)output;
					itemLocation = mixedSpecimen.getLocation();
					//If item already has location set the mixed specimen
					if(itemLocation == null){
						itemLocation = new Location();
					}				
					itemLocation.setMixedSpecimen(mixedSpecimen);
				}
				else if(entityType.equalsIgnoreCase("PRI") || entityType.equalsIgnoreCase("05")){
					//PCR PRIMER NOT IMPLEMENTED YET
					//PcrPrimer pcrPrimer = (PcrPrimer)output;
					//itemLocation = pcrPrimer.getLocation();
					//itemLocation.setPcrPrimer(pcrPrimer);

				}
				else if(entityType.equalsIgnoreCase("SAM") || entityType.equalsIgnoreCase("04")){
					//SAMPLE NOT IMPLEMENTED YET
					//Sample sample = (Sample)output;
					//itemLocation = sample.getLocation();
					//itemLocation.setSample(sample);
				}
				else if(entityType.equalsIgnoreCase("SPE") || entityType.equalsIgnoreCase("01") || entityType.equalsIgnoreCase("02")){
					//SAMPLE NOT IMPLEMENTED YET					
					//SpecimenReplicate specimenReplicate = (SpecimenReplicate)output;
					//itemLocation = specimenReplicate.getLocation();
					//itemLocation.setSpecimenReplicate(specimenReplicate);
				}
			}
		}
		else if(method.equalsIgnoreCase(ServiceTask.GET_CONTAINER_BY_ID)){
			if(containerType.equalsIgnoreCase("CON") || containerType.equalsIgnoreCase("07")){
				contentContainer = (Container)output;
				getContentFragment.loadContent(contentContainer);
			}
		}
		else if(method.equalsIgnoreCase(ServiceTask.UPDATE)){
			//launch GetContents activity
			Intent intent = new Intent(this, GetContentsActivity.class);
			//Data to send through intent
        	Bundle dataBundle = new Bundle();
        	dataBundle.putSerializable("CONTAINER", contentContainer);
			intent.putExtras(dataBundle);
			
			startActivity(intent);
			finish();
		}

		else{
			//TODO Handle Error
			Toast.makeText(this, "Error getById failed", Toast.LENGTH_LONG).show();
			finish();
		}
	}
}

