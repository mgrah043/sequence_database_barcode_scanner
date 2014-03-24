package ca.gc.aafc.seqdb_barcode_scanner;

import java.util.HashMap;

import ca.gc.aafc.seqdb_barcode_scanner.entities.Container;
import ca.gc.aafc.seqdb_barcode_scanner.service.EntityServiceI;
import ca.gc.aafc.seqdb_barcode_scanner.utils.DataParser;
import ca.gc.aafc.seqdb_barcode_scanner.utils.ServiceTask;
import ca.gc.aafc.seqdb_barcode_scanner.utils.Session;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.TextView;


public class GetContentsActivity extends FragmentActivity implements GetContentFragment.OnContentSelectedListener, ServiceTask.OnServiceCallCompletedListener{
	//Variable declaration
	TextView header_title;
	ImageButton button_mainMenu;

	private GetContentFragment getContentFragment;
	private Container contentContainer;
	private DataParser parser;
	private ServiceTask taskRunner;

	private Session getContentSession;
	static String SESSION_TYPE = "GET_CONTENT";
	static final String SCAN_TYPE = "SCAN_CONTAINER";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Should had possibility to get data from bundle in case we start this view for move or bulk move
		setContentView(R.layout.activity_get_contents_template);
		
		getContentFragment = (GetContentFragment) getSupportFragmentManager().findFragmentById(R.id.get_content_fragment);
		
		getContentSession = new Session(this,SESSION_TYPE);
	    Toast.makeText(this, "Please scan the container to get its content", Toast.LENGTH_LONG).show();
	    
		launchScanner(SCAN_TYPE);
		
		parser = new DataParser();
		taskRunner = new ServiceTask(this);
	}

	@Override
	public void onBackPressed() {
		finish();
	}
	
	/*
	 * Is called whenever scanning was done
	 * get scanned result and decode using getEntity
	 * */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	   // Make sure the request was successful
	   if (resultCode == RESULT_OK) {
	       Bundle resultBundle = data.getExtras();
	       String decodedData = resultBundle.getString("DATA_RESULT");
	       String scanAction = resultBundle.getString("SCAN_ACTION");
		   
		   if(scanAction != null && scanAction.equalsIgnoreCase(SCAN_TYPE)){
			   //With the decoded data use session.get entity etc... then call server to get container info
			   try{
				   parser.parse(decodedData);
				   

				   String acronym = parser.getAcronym();
				   long id = parser.getId();
				   EntityServiceI service = null;
				   
				   if (acronym.equalsIgnoreCase("CON") || acronym.equalsIgnoreCase("07")){
					   service = getContentSession.getService(acronym);
				   }else{
					   Toast.makeText(this, "Wrong object scanned please scan a container", Toast.LENGTH_LONG).show();
					   this.launchScanner("SCAN_CONTAINER");
				   }

				   if(service != null){
					   taskRunner.setService(service);
					   HashMap<String,Object> params = new HashMap<String,Object>();
					   params.put(ServiceTask.GET_BY_ID, id);
					   taskRunner.execute(params);
				   }
			   }catch(Exception e){
				   Toast.makeText(this, "Unknown barcode format: please scan a valid container", Toast.LENGTH_LONG).show();
				   launchScanner(SCAN_TYPE);
			   }
			   
		   }else{
			   Toast.makeText(this, "NO SCANNING ACTION", Toast.LENGTH_LONG).show();
			   launchScanner(SCAN_TYPE);
		   }
	   }else{
		   finish();		   
	   } 
	}

	@Override
	public void onContentSelected(String row, int col,boolean state) {
		/*
		 * fetch the content at index of container entity that we got from the server
		 * if row and col == null then it was an empty cell
		 * */
		if(!state){
			Toast.makeText(this, "You've clicked on an empty cell", Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(this, "A content has been clicked at "+row+" "+col, Toast.LENGTH_LONG).show();
		}
	}
	
	 private void launchScanner(String action){
		   Intent intent = new Intent(this, ScannerActivity.class);
		   intent.putExtra("SCAN_ACTION", action);
		   
		   startActivityForResult(intent,0);
	 }

	@Override
	public void onServiceCalled(String method, Object output) {
		if(method.equalsIgnoreCase(ServiceTask.GET_BY_ID)){
			if(output !=null){
				
				contentContainer = (Container)output;
				getContentFragment.loadContent(contentContainer);
				
			}else{
				Toast.makeText(this, "Error getById failed", Toast.LENGTH_LONG).show();

			}
		}
	}

}
