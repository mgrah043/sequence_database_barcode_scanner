package ca.gc.aafc.seqdb_barcode_scanner;

import ca.gc.aafc.seqdb_barcode_scanner.entities.Container;
import ca.gc.aafc.seqdb_barcode_scanner.service.ContainerService;
import ca.gc.aafc.seqdb_barcode_scanner.service.EntityServiceI;
import ca.gc.aafc.seqdb_barcode_scanner.utils.DataParser;
import ca.gc.aafc.seqdb_barcode_scanner.utils.Session;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;


public class GetContentsActivity extends FragmentActivity implements GetContentFragment.OnContentSelectedListener{
	//Variable declaration
	TextView header_title;
	ImageButton button_mainMenu;

	private GetContentFragment getContentFragment;
	private Container contentContainer;
	private DataParser parser;

	Session getContentSession;
	static String SESSION_TYPE = "GET_CONTENT";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Should had possibility to get data from bundle in cas we start this view for move or bulk move
		
		setContentView(R.layout.activity_get_contents_template);
		
		getContentFragment = (GetContentFragment) getSupportFragmentManager().findFragmentById(R.id.get_content_fragment);
		
		getContentSession = new Session(this,SESSION_TYPE);
	    Toast.makeText(GetContentsActivity.this, "Please scan the container to get its content", Toast.LENGTH_LONG).show();
	    
		this.launchScanner("SCAN_CONTAINER");
		
		parser = new DataParser();
	}

	@Override
	public void onBackPressed() {
		finish();
	}
	
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
	       
	       Toast.makeText(GetContentsActivity.this, "Data decoded : "+decodedData, Toast.LENGTH_LONG).show();
	       //TODO check if the decodedData is null if so then throw an error
		       
		   System.out.print("Success data is - "+decodedData);
		   
		   //String current_container = this.getContentSession.getSession().getString("GET_CONTENTS_CONTAINER", "");
		   
		   if(scanAction != null && scanAction.equalsIgnoreCase("SCAN_CONTAINER")){
			   /*
			    * With the decoded data use session.get entity etc... then call server to get container info
			    * 
			    * */
			   this.parser.parse(decodedData);
			   
			   String acronym = this.parser.getAcronym();
			   long id = this.parser.getId();
			   
			   EntityServiceI service = this.getContentSession.getService(acronym);
			   
			   if(service != null){
				   this.contentContainer = (Container)service.getById(id);
			   }
			   
			   //this.getContentSession.getSessionEditor().putString("GET_CONTENTS_CONTAINER", decodedData);
			   //this.getContentSession.getSessionEditor().commit();
			   if(this.contentContainer == null){
				   Toast.makeText(GetContentsActivity.this, "Error getById failed", Toast.LENGTH_LONG).show();
			   }else{
				   this.getContentFragment.loadContent(this.contentContainer);
			   }
			   
		   }else{
			   Toast.makeText(GetContentsActivity.this, "NO SCANNING ACTION", Toast.LENGTH_LONG).show();
			   this.launchScanner("SCAN_CONTAINER");
		   }
		 
		   
	   }else{
		   Toast.makeText(GetContentsActivity.this, "ERROR when scanning", Toast.LENGTH_LONG).show();		   
	   }
		  
	}

	@Override
	public void onContentSelected(String row, int index) {
		// TODO Auto-generated method stub
		/*
		 * fetch the content at index of container entity that we got from the server
		 * */
		Toast.makeText(GetContentsActivity.this, "A content has been clicked at index : "+index, Toast.LENGTH_LONG).show();
	}
	
	 private void launchScanner(String action){
		   Intent intent = new Intent(GetContentsActivity.this, ScannerActivity.class);
		   intent.putExtra("SCAN_ACTION", action);
		   
		   startActivityForResult(intent,0);
	 }

}
