package ca.gc.aafc.seqdb_barcode_scanner;

import java.io.Serializable;

import ca.gc.aafc.seqdb_barcode_scanner.service.ContainerService;
import ca.gc.aafc.seqdb_barcode_scanner.service.EntityServiceI;
import ca.gc.aafc.seqdb_barcode_scanner.service.LocationService;
import ca.gc.aafc.seqdb_barcode_scanner.service.MixedSpecimenService;
import ca.gc.aafc.seqdb_barcode_scanner.service.PcrPrimerService;
import ca.gc.aafc.seqdb_barcode_scanner.service.SampleService;
import ca.gc.aafc.seqdb_barcode_scanner.service.SpecimenReplicateService;
import ca.gc.aafc.seqdb_barcode_scanner.service.StorageService;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class MoveActivity extends FragmentActivity implements GetContentFragment.OnContentSelectedListener{
	EditText et_well_row;
	EditText et_well_col;
	
	Button button_use;
	Button button_ignore;
	ImageButton button_mainMenu;
	
	TextView header_title;
	Session moveSession;
	static String SESSION_TYPE = "MOVE";
	
	private int numRows;
	private int numCols;
	private GetContentFragment getContentFragment;
	

	final private int WIDTH_OF_TABLE_ELEMENT = 250;
	final private int HEIGHT_OF_TABLE_ELEMENT = 250;

	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_move);
		
		getContentFragment = (GetContentFragment) getSupportFragmentManager().findFragmentById(R.id.get_content_fragment);
		
        
        button_mainMenu = (ImageButton) findViewById(R.id.btn_header_menu);
        button_mainMenu.setOnClickListener(Button_Click_Listener);
        
        header_title = (TextView) findViewById(R.id.tv_header_main_title);
        header_title.setText("MOVE");
        
        button_ignore = (Button) findViewById(R.id.btn_ignore_well_info);
        button_ignore.setOnClickListener(Button_Click_Listener);
        
        moveSession = new Session(this,SESSION_TYPE);
        Toast.makeText(MoveActivity.this, "Please scan item to move", Toast.LENGTH_LONG).show();
		this.launchScanner("SCAN_ITEM");
		
		numRows = 9;
		numCols = 9;


		
		
   }
	
	
 
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
	}



	OnClickListener Button_Click_Listener = new OnClickListener(){
		public void onClick(View v){
			int id_of_view = v.getId();

			//login button
			if(id_of_view == button_use.getId()){
				/*
				 * get info from shared pref
				 * 		- item id
				 * 		- container id
				 * 
				 * create entities:
				 * 		- to change specimen location from item id
				 *      - to change container from container id
				 *  
				 * */
				Toast.makeText(MoveActivity.this, "use well info button pressed", Toast.LENGTH_LONG).show();
			}if(id_of_view == button_ignore.getId()){
				/*
				 * get info from shared pref
				 * 		- item id
				 * 		- containe id
				 * */
				Toast.makeText(MoveActivity.this, "Ignore for autofill button pressed", Toast.LENGTH_LONG).show();
			}else{
				Toast.makeText(MoveActivity.this, "This action is not authorized", Toast.LENGTH_LONG).show();
			}
			
			/*
			 * When done display get contents or success message ??
			 * */
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		/*
		 * Is called whenever scanning was done
		 * get scanned result and decode using getEntity
		 * */
	   // Make sure the request was successful
	   if (resultCode == RESULT_OK) {
	       Bundle resultBundle = data.getExtras();
	       String decodedData = resultBundle.getString("DATA_RESULT");
	       String scanAction = resultBundle.getString("SCAN_ACTION");
	       
	       Toast.makeText(MoveActivity.this, "Data decoded : "+decodedData, Toast.LENGTH_LONG).show();
	       //TODO check if the decodedData is null if so then throw an error
		       
		   System.out.print("Success data is - "+decodedData);
		   
		   String current_item = this.moveSession.getSession().getString("MOVE_ITEM", "");
		   String current_container = this.moveSession.getSession().getString("MOVE_CONTAINER", "");
		   
		   if(scanAction != null && scanAction.equalsIgnoreCase("SCAN_ITEM")){
			   /*Check if scanned element was indeed an item and save otherwise ask to scan item
			    *  right now it doesn't check that
			    * should parse decoded Data and created appropriate service for the element just scanned
			    * before saving the id in the session
			    * */
			   this.moveSession.getSessionEditor().putString("MOVE_ITEM", decodedData);
			   this.moveSession.getSessionEditor().commit();
			   
			   Toast.makeText(MoveActivity.this, "Please scan container", Toast.LENGTH_LONG).show();
			   this.launchScanner("SCAN_CONTAINER");
		   }else  if(scanAction != null && scanAction.equalsIgnoreCase("SCAN_CONTAINER")){
			   /*
			    * if item is empty ask for scanning the item again
			    * */
			   if(!current_item.equalsIgnoreCase("")){
				   
				   this.moveSession.getSessionEditor().putString("MOVE_CONTAINER", decodedData);
				   this.moveSession.getSessionEditor().commit();
				   
			   }else{
				   Toast.makeText(MoveActivity.this, "Please scan item to move ", Toast.LENGTH_LONG).show();
				   System.out.print("current item is empty rescan itme");
				   this.launchScanner("SCAN_ITEM");
			   }
		   }else{
			   Toast.makeText(MoveActivity.this, "NO SCANNING ACTION", Toast.LENGTH_LONG).show();
			   this.launchScanner("SCAN_ITEM");
		   }
		 
		   
	   }else{
		   Toast.makeText(MoveActivity.this, "ERROR when scanning", Toast.LENGTH_LONG).show();		   
	   }
		   /*
		   //send request to server to get result
		   String acronym = decodedData.split("-")[0];
		       EntityServiceI service = getService(acronym);
		       if (service != null){
		    	   long id = Long.parseLong(decodedData.split("-")[1]);
		    	   Serializable entity = service.getById(id);
		    	   
		    	   // prepare data to send to LookUp Activity
		    	   Bundle dataBundle = new Bundle();
		    	   dataBundle.putSerializable("ENTITY", entity);
		    	   dataBundle.putString("TYPE", acronym);
		    	   
		    	   Intent intent = new Intent(MoveActivity.this, LookupActivity.class);
		    	   intent.putExtras(dataBundle);
		    	   startActivity(intent);
		       }else {
		    	   // TODO display error message to user
		       }*/
		  
	}
	
	@Override
	public void onBackPressed() {
	
		//Exit to home screen and keep activity alive for re-entry to this point in the app
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		
	}
	
	@Override
	public void onContentSelected(int index) {
		// TODO Auto-generated method stub
		/*
		 * fetch the content at index of container entity that we got from the server
		 * */
		Toast.makeText(MoveActivity.this, "A content has been clicked at index : "+index, Toast.LENGTH_LONG).show();
	}
	
	  private void launchScanner(String action){
		   Intent intent = new Intent(MoveActivity.this, ScannerActivity.class);
		   intent.putExtra("SCAN_ACTION", action);
		   
		   startActivityForResult(intent,0);
	   }
		
}
