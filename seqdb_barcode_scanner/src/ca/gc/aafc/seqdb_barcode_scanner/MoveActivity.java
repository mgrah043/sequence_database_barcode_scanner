package ca.gc.aafc.seqdb_barcode_scanner;

import java.util.ArrayList;
import java.util.HashMap;

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
	private ServiceTask taskRunner;
	private Location itemLocation;
	private Container contentContainer;
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
		Toast.makeText(MoveActivity.this, "Please scan item to move", Toast.LENGTH_LONG).show();
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
			//Autofill button
			else if(id_of_view == button_autofill.getId()){
				ArrayList<Location> locationArray = contentContainer.getlocationList();
				Toast.makeText(MoveActivity.this, "Autofill button pressed", Toast.LENGTH_LONG).show();
			}
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
					//Store move item in session
					this.moveSession.getSessionEditor().putString("MOVE_ITEM", decodedData);
					this.moveSession.getSessionEditor().commit();

					//Launch container scanner screen
					Toast.makeText(MoveActivity.this, "Please scan container", Toast.LENGTH_LONG).show();
					this.launchScanner(SCAN_TYPE_CONTAINER);
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
						//Parsing item
						parser = new DataParser();
						parser.parse(current_item);
						entityType = parser.getAcronym();
						itemId = parser.getId();
						
						//Parsing container
						parser = new DataParser();
						parser.parse(decodedData/*current_container*/);
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
							Toast.makeText(this, "Unknown barcode format: please scan an item barcode", Toast.LENGTH_LONG).show();
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
					Toast.makeText(MoveActivity.this, "Please scan item to move ", Toast.LENGTH_LONG).show();
					System.out.print("Current item is empty; rescan item");
					this.launchScanner("SCAN_ITEM");
				}
			}
			else{
				Toast.makeText(MoveActivity.this, "No scanning action, please scan an item", Toast.LENGTH_LONG).show();
				this.launchScanner("SCAN_ITEM");
			}		   
		}
		else{
			Toast.makeText(MoveActivity.this, "Error when scanning", Toast.LENGTH_LONG).show();		   
		}
	}

	@Override
	public void onBackPressed() {
		finish();
	}

	@Override
	public void onContentSelected(String row, int column,boolean state) {
		//TODO delete test output
		//Toast.makeText(MoveActivity.this, "Content has been clicked at index : "+row+column, Toast.LENGTH_LONG).show();
		//TODO delete variable isEmptyElement, should be passed in
		boolean isEmptyElement = false;
		//move item to this location in the container
		if (isEmptyElement){		
			itemLocation.setWellRow(row);
			itemLocation.setWellColumn(column);
			itemLocation.setContainerId(contentContainer.getId());
			try{
				EntityServiceI locationService = moveSession.getService("LOC");
				if(locationService != null){
					taskRunner = new ServiceTask(this);
					taskRunner.setService(locationService);
					HashMap<String,Object> params = new HashMap<String,Object>();
					params.put("update", itemLocation);
					taskRunner.execute(params);	
				}
			}
			catch(Exception e){
				Toast.makeText(this, "Error while moving item, please select another location", Toast.LENGTH_LONG).show();
				e.printStackTrace();
				launchScanner(SCAN_TYPE_ITEM);
			}
		}
		else{
			Toast.makeText(MoveActivity.this, "Please select an empty location.", Toast.LENGTH_LONG).show();
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
			if(output !=null && !entityType.isEmpty() || !containerType.isEmpty()){
				if(entityType.equalsIgnoreCase("MSP") || entityType.equalsIgnoreCase("03")){
					MixedSpecimen mixedSpecimen = (MixedSpecimen)output;
					//TODO itemLocation = mixedSpecimen.getLocation;
					Toast.makeText(MoveActivity.this, "MIXED SPECIMEN!!!!!!!!!!!!!!!!!", Toast.LENGTH_LONG).show();
				}
				else if(entityType.equalsIgnoreCase("PRI") || entityType.equalsIgnoreCase("05")){
					PcrPrimer pcrPrimer = (PcrPrimer)output;
					itemLocation = pcrPrimer.getLocation();
				}
				else if(entityType.equalsIgnoreCase("SAM") || entityType.equalsIgnoreCase("04")){
					Sample sample = (Sample)output;
					itemLocation = sample.getLocation();
				}
				else if(entityType.equalsIgnoreCase("SPE") || entityType.equalsIgnoreCase("01") || entityType.equalsIgnoreCase("02")){
					SpecimenReplicate specimenReplicate = (SpecimenReplicate)output;
					itemLocation = specimenReplicate.getLocation();
				}
			}
		}
		else if(method.equalsIgnoreCase(ServiceTask.GET_CONTAINER_BY_ID)){
			if(containerType.equalsIgnoreCase("CON") || containerType.equalsIgnoreCase("07")){
				contentContainer = (Container)output;
				Toast.makeText(MoveActivity.this, "CONTAINER!!!!!!!!!!!!!!!!!", Toast.LENGTH_LONG).show();
				getContentFragment.loadContent(contentContainer);
			}
		}
		else{
			//TODO Handle Error
			Toast.makeText(this, "Error getById failed", Toast.LENGTH_LONG).show();
		}
	}
}

