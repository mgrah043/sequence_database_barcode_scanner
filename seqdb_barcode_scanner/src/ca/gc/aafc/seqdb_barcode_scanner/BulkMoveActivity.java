package ca.gc.aafc.seqdb_barcode_scanner;

import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonProperty;

import ca.gc.aafc.seqdb_barcode_scanner.entities.Container;
import ca.gc.aafc.seqdb_barcode_scanner.entities.Location;
import ca.gc.aafc.seqdb_barcode_scanner.entities.MixedSpecimen;
import ca.gc.aafc.seqdb_barcode_scanner.entities.PcrPrimer;
import ca.gc.aafc.seqdb_barcode_scanner.entities.Sample;
import ca.gc.aafc.seqdb_barcode_scanner.entities.SpecimenReplicate;
import ca.gc.aafc.seqdb_barcode_scanner.service.EntityServiceI;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import ca.gc.aafc.seqdb_barcode_scanner.utils.*;

public class BulkMoveActivity extends FragmentActivity implements GetContentFragment.OnContentSelectedListener, ServiceTask.OnServiceCallCompletedListener{
	TextView header_title;
	ImageButton button_mainMenu;
	EditText et_well_row;
	EditText et_well_col;
	Button button_cancel;
	Button button_autofill;
	
	public boolean scan_item_triggered = false;
	Dialog confirmDialog;
	private DataParser parser;
	private ServiceTask taskRunner;
	
	private Location itemLocation;
	private Container contentContainer;
	private ArrayList<String[]> emptyCells = new ArrayList<String[]>();
	
	private Location updatedLocation;
	
	//private ContainerType contentContainerType;
	Session bulkMoveSession;
	static final String SESSION_TYPE = "BULK_MOVE";
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
		
		setContentView(R.layout.activity_bulk_move);
		//initialize fragment to display table
		getContentFragment = (GetContentFragment) getSupportFragmentManager().findFragmentById(R.id.get_content_fragment);
		
		
		bulkMoveSession = new Session(this,SESSION_TYPE);
		confirmDialog = new Dialog(this);
		
		//launch scanner screen with message
		Toast.makeText(BulkMoveActivity.this, "Please scan container to start bulk move", Toast.LENGTH_LONG).show();
		launchScanner(SCAN_TYPE_CONTAINER);
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
			/*
			 * TODO TO BE COMPLETED WHEN WEB SERVICE HANDLES AUTOFILLING
			else if(id_of_view == button_autofill.getId()){
				ArrayList<Location> locationArray = contentContainer.getlocationList();
				Toast.makeText(MoveActivity.this, "Autofill button pressed", Toast.LENGTH_LONG).show();
			}
			*/
			else{
				Toast.makeText(BulkMoveActivity.this, "This action is not authorized", Toast.LENGTH_LONG).show();
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

			//Check if item scanner
			if(scanAction != null && scanAction.equalsIgnoreCase(SCAN_TYPE_ITEM)){
				if(decodedData != null){				   
					//Store move item in session
					this.bulkMoveSession.getSessionEditor().putString("MOVE_ITEM", decodedData);
					this.bulkMoveSession.getSessionEditor().commit();
					
					try{
						//Parsing item
						parser = new DataParser();
						parser.parse(decodedData);
						entityType = parser.getAcronym();
						itemId = parser.getId();
						
						if(entityType.equalsIgnoreCase("MSP") || entityType.equalsIgnoreCase("03")){
							//Item service
							EntityServiceI itemService = bulkMoveSession.getService(entityType);
							if(itemService != null){
								ServiceTask taskRunner = new ServiceTask(this);
								taskRunner.setService(itemService);
								HashMap<String,Object> params = new HashMap<String,Object>();
								params.put(ServiceTask.GET_BY_ID, itemId);
								taskRunner.execute(params);
								scan_item_triggered = true;
							}
						}else{
							//Re-launch item scanner screen
							Toast.makeText(BulkMoveActivity.this, "Invalid item, please scan an item", Toast.LENGTH_LONG).show();
							this.launchScanner(SCAN_TYPE_ITEM);
						}
					}catch(Exception e){
						//Re-launch item scanner screen
						Toast.makeText(BulkMoveActivity.this, "Error scanning item, please scan an item", Toast.LENGTH_LONG).show();
						this.launchScanner(SCAN_TYPE_ITEM);
					}
				}
				else{
					//Re-launch item scanner screen
					Toast.makeText(BulkMoveActivity.this, "Item not found", Toast.LENGTH_LONG).show();
					this.launchScanner(SCAN_TYPE_ITEM);
				}
			}
			
			//Check if container scanner
			else  if(scanAction != null && scanAction.equalsIgnoreCase(SCAN_TYPE_CONTAINER)){
				//If item is empty ask to scan item again			    
				if(decodedData != null){
					
					this.bulkMoveSession.getSessionEditor().putString("MOVE_CONTAINER", decodedData);
					this.bulkMoveSession.getSessionEditor().commit();
					
					try{
						
						
						//Parsing container
						parser = new DataParser();
						parser.parse(decodedData);
						
						containerType = parser.getAcronym();
						containerId = parser.getId();
						
						//Verify it is a container
						if(containerType.equalsIgnoreCase("CON") || containerType.equalsIgnoreCase("07")){
							
							//Container service
							EntityServiceI containerService = bulkMoveSession.getService(containerType);
							if(containerService != null){
								ServiceTask containerTaskRunner = new ServiceTask(this);
								containerTaskRunner.setService(containerService);
								HashMap<String,Object> params = new HashMap<String,Object>();
								params.put(ServiceTask.GET_CONTAINER_BY_ID, containerId);
								containerTaskRunner.execute(params);
							}
						}	
						else{
							Toast.makeText(this, "Unknown barcode format: please scan a container barcode", Toast.LENGTH_LONG).show();
							launchScanner(SCAN_TYPE_CONTAINER);
						}
					}
					catch(Exception e){
						Toast.makeText(this, "An error occured, please scan a container", Toast.LENGTH_LONG).show();
						e.printStackTrace();
						launchScanner(SCAN_TYPE_CONTAINER);
					}
				}else{
					Toast.makeText(BulkMoveActivity.this, "Please scan container to start bulk move ", Toast.LENGTH_LONG).show();
					System.out.print("Current container is empty; rescan restart container");
					this.launchScanner("SCAN_TYPE_CONTAINER");
				}
				
			}else{
				Toast.makeText(BulkMoveActivity.this, "No scanning action, please scan a container", Toast.LENGTH_LONG).show();
				this.launchScanner("SCAN_TYPE_CONTAINER");
			}		   
		}
		else{
			Toast.makeText(BulkMoveActivity.this, "Error when scanning", Toast.LENGTH_LONG).show();	
			finish();
		}
	}

	@Override
	public void onBackPressed() {
		finish();
	}

	@Override
	public void onContentSelected(String id, String row, int column,boolean state) {
		System.out.println("Clicked on an element");
	}

	private void launchScanner(String action){
		Intent intent = new Intent(BulkMoveActivity.this, ScannerActivity.class);
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
				//run update location
				updateLocation();
			}
			
			
		}
		else if(method.equalsIgnoreCase(ServiceTask.GET_CONTAINER_BY_ID)){
			if(containerType.equalsIgnoreCase("CON") || containerType.equalsIgnoreCase("07")){
				contentContainer = (Container)output;
				
				int contentRow =  contentContainer.getContainerType().getNumberOfRows();
				int contentColumn = contentContainer.getContainerType().getNumberOfColumns();
				
				// fetch empty cells
				ArrayList<Location> containerLocations = contentContainer.getlocationList();
				HashMap<String,Location> rowCol = new HashMap<String,Location>();
				
				for (Location l : containerLocations) rowCol.put(l.getWellRow(),l);
				
				for(int row = 0; row  < contentRow; row++){
					for(int col = 0; col < contentColumn; col++){
						String rowChar= getCharacterOfNumber(row);
						
						try{
							if(rowCol.get(rowChar).getWellColumn()-1 != col){
								emptyCells.add(new String[]{""+rowChar,""+(col+1)});
							}
						}catch(Exception e){
							emptyCells.add(new String[]{""+rowChar,""+(col+1)});
						}
						
					}
				}
				getContentFragment.loadContent(contentContainer);
				
				//launch scanner screen with message
				Toast.makeText(BulkMoveActivity.this, "Please scan an item to move", Toast.LENGTH_LONG).show();
				launchScanner(SCAN_TYPE_ITEM);
			}
		}
		else if(method.equalsIgnoreCase(ServiceTask.UPDATE)){
				updatedLocation = (Location)output;
				//Toast.makeText(BulkMoveActivity.this, "LOCATION!!!!!!!!!!!!!!!!!", Toast.LENGTH_LONG).show();
				//getContentFragment.loadContent(contentContainer);
				
				
		}

		else{
			//TODO Handle Error
			Toast.makeText(this, "Error getById failed", Toast.LENGTH_LONG).show();
			finish();
		}
	}
	
	private void updateLocation(){
		/*
		 * Update once fecthing the item is done
		 * */
		if(!(emptyCells.size() == 0)){
			String[] rowCol = emptyCells.get(0);
			itemLocation.setWellRow(rowCol[0]);
			itemLocation.setWellColumn(Integer.parseInt(rowCol[1]));
			itemLocation.setContainerId(contentContainer.getId());
			
			try{
				EntityServiceI locationService = bulkMoveSession.getService("LOC");
				if(locationService != null){
					taskRunner = new ServiceTask(this);
					taskRunner.setService(locationService);
					HashMap<String,Object> params = new HashMap<String,Object>();
					params.put(ServiceTask.UPDATE, itemLocation);
					taskRunner.execute(params);	
				}
			}
			catch(Exception e){
				Toast.makeText(this, "Error while moving item", Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}
			
			emptyCells.remove(0);
			
			showDialog("Move Successful!");
			//update container
			ArrayList<Location> tempList= contentContainer.getlocationList();
			
			Location l = new Location();
			l.setId(itemLocation.getId());
			l.setWellColumn(itemLocation.getWellColumn());
			l.setWellRow(itemLocation.getWellRow());
			l.setContainerId(itemLocation.getContainerId());
			l.setMixedSpecimen(itemLocation.getMixedSpecimen());
			l.setMixedSpecimenUrl(itemLocation.getMixedSpecimenUrl());
			tempList.add(l);
			
			contentContainer.setlocationList(tempList);
		}
	}
	private String getCharacterOfNumber(int number){
		//convert to ascii format (capital characters - A is 65)
		int asciiNumber = number + 65;

		return  String.valueOf(Character.toChars(asciiNumber));
	}
	private void showDialog(String text){
		 // get dialog xml
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		
		View promptView = layoutInflater.inflate(R.layout.dialog_bulk_move, null);
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		
		alertDialogBuilder.setView(promptView);
		
		
		TextView input = (TextView) promptView.findViewById(R.id.move_text);
		input.setText(text);          
		
		alertDialogBuilder
		.setCancelable(false)
		.setPositiveButton("Scan More Item", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				Toast.makeText(BulkMoveActivity.this, "Please scan an item to move", Toast.LENGTH_LONG).show();
				launchScanner(SCAN_TYPE_ITEM);
				dialog.cancel();
			}
		})
		.setNegativeButton("End Bulk Move",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
				//finish();
				Intent intent = new Intent(BulkMoveActivity.this, GetContentsActivity.class);
				
				//Data to send through intent
	        	Bundle dataBundle = new Bundle();
	        	dataBundle.putSerializable("CONTAINER", contentContainer);
				intent.putExtras(dataBundle);
				
				startActivity(intent);
				finish();
			}
		});
	
		AlertDialog alertD = alertDialogBuilder.create();
		alertD.show();

	}
}

