package ca.gc.aafc.seqdb_barcode_scanner;

import java.util.HashMap;

import ca.gc.aafc.seqdb_barcode_scanner.entities.Container;
import ca.gc.aafc.seqdb_barcode_scanner.entities.ContainerType;
import ca.gc.aafc.seqdb_barcode_scanner.entities.Location;
import ca.gc.aafc.seqdb_barcode_scanner.entities.MixedSpecimen;
import ca.gc.aafc.seqdb_barcode_scanner.entities.PcrPrimer;
import ca.gc.aafc.seqdb_barcode_scanner.entities.Sample;
import ca.gc.aafc.seqdb_barcode_scanner.entities.SpecimenReplicate;
import ca.gc.aafc.seqdb_barcode_scanner.entities.Storage;
import ca.gc.aafc.seqdb_barcode_scanner.service.EntityServiceI;
import ca.gc.aafc.seqdb_barcode_scanner.utils.DataParser;
import ca.gc.aafc.seqdb_barcode_scanner.utils.ServiceTask;
import ca.gc.aafc.seqdb_barcode_scanner.utils.Session;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LookupActivity extends Activity implements ServiceTask.OnServiceCallCompletedListener{
	
	//Variable declaration
	ImageButton button_mainMenu;
	TextView header_title;
	
	private DataParser parser;
	private ServiceTask taskRunner;
	private String entityType = "";
	
	private LinearLayout layout_content;
	
	private Session lookupSession;
	static final String SESSION_TYPE = "LOOKUP";
	static final String SCAN_TYPE = "SCAN_BARCODE";
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        lookupSession = new Session(this,SESSION_TYPE);
	    Toast.makeText(this, "Please scan a barcode", Toast.LENGTH_LONG).show();
	    
	    
	    parser = new DataParser();
		taskRunner = new ServiceTask(this);
		
		setContentView(R.layout.activity_lookup);
	        
        //Instantiate and set click listener for buttons
        button_mainMenu = (ImageButton) findViewById(R.id.btn_header_menu);
        button_mainMenu.setOnClickListener(Button_Click_Listener);
        
        header_title = (TextView) findViewById(R.id.tv_header_main_title);
        header_title.setText("LOOKUP RESULT");
        
        layout_content = (LinearLayout) findViewById(R.id.content_layout);
        
        //coming from get contents process data and display
        Bundle dataBundle = getIntent().getExtras();
	    if(dataBundle != null){
	    	String decodedData = dataBundle.getString("OBJECT_ID");
	    	try{
	    		processData(decodedData);
	    	}catch(Exception e){
	    		System.out.println("Incorrect Id passed");
	    	}
	    }else{
	    	launchScanner(SCAN_TYPE);
	    }
	}
	
	OnClickListener Button_Click_Listener = new OnClickListener(){
		public void onClick(View v){
			int id_of_view = v.getId();

			//Main menu button
			if(id_of_view == button_mainMenu.getId()){
				finish();
			}
		}
	};
	
	
	@Override
	public void onBackPressed() {
		finish();
	}
	
	/*
	 * Is called whenever scanning was done
	 * get scanned result and decode using getEntity
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
	   // Make sure the request was successful
	   if (resultCode == RESULT_OK) {
	       Bundle resultBundle = data.getExtras();
	       String decodedData = resultBundle.getString("DATA_RESULT");
	       String scanAction = resultBundle.getString("SCAN_ACTION");
		   
		   if(scanAction != null && scanAction.equalsIgnoreCase(SCAN_TYPE)){
			   // With the decoded data use session.get entity etc... then call server to get entity info
			   try{
				   processData(decodedData);
			   }catch(Exception e){
				   Toast.makeText(this, "Unknown barcode format: please scan a valid barcode", Toast.LENGTH_LONG).show();
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
	
	//***************************************
	// Display Methods - START
	//***************************************
	private void processData(String decodedData)throws Exception{
		  parser.parse(decodedData);
		  entityType = parser.getAcronym();
		   
		  EntityServiceI service = lookupSession.getService(entityType);
		   
		  if(service != null){
			   taskRunner.setService(service);
			   HashMap<String,Object> params = new HashMap<String,Object>();
			   params.put(ServiceTask.GET_BY_ID, parser.getId());
			   taskRunner.execute(params);
		  }
	}

	private void displayContainer(Container container){
		ContainerType containerType = container.getContainerType();
		
		//Instantiate and set text for textviews
		 TextView textview_name_header = new TextView(this);
        textview_name_header.setText("Container: " + container.getContainerNumber());
        textview_name_header.setTextSize(20);
        textview_name_header.setTypeface(null, Typeface.BOLD);
        textview_name_header.setTextColor(Color.BLACK);
        textview_name_header.setPadding(0, 5, 0, 0);
        layout_content.addView(textview_name_header);
        
        TextView textview_type_header = new TextView(this);
        textview_type_header.setText("Container Type: ");
        textview_type_header.setTextSize(20);
        textview_type_header.setTextColor(Color.BLACK);
        textview_type_header.setPadding(0, 5, 0, 0);
        textview_type_header.setTypeface(null, Typeface.BOLD);
        layout_content.addView(textview_type_header);
        
        TextView textview_name = new TextView(this);
        textview_name.setText("Name: " + containerType.getName());
        textview_name.setTextSize(20);
        textview_name.setTextColor(Color.BLACK);
        textview_name.setPadding(50, 5, 0, 0);
        layout_content.addView(textview_name);
        
        TextView textview_base = new TextView(this);
        textview_base.setText("Base Type: " + containerType.getBaseType());
        textview_base.setTextSize(20);
        textview_base.setTextColor(Color.BLACK);
        textview_base.setPadding(50, 5, 0, 0);
        layout_content.addView(textview_base);
	        
        TextView textview_num = new TextView(this);
        textview_num.setText("Number of Wells: " + containerType.getNumberOfWells());
        textview_num.setTextSize(20);
        textview_num.setTextColor(Color.BLACK);
        textview_num.setPadding(50, 5, 0, 0);
        layout_content.addView(textview_num);
	        
        TextView textview_num_cols = new TextView(this);
        textview_num_cols.setText("Number of Columns: " + containerType.getNumberOfColumns());
        textview_num_cols.setTextSize(20);
        textview_num_cols.setTextColor(Color.BLACK);
        textview_num_cols.setPadding(50, 5, 0, 0);
        layout_content.addView(textview_num_cols);
        
        TextView textview_num_rows = new TextView(this);
        textview_num_rows.setText("Number of Rows: " + containerType.getNumberOfRows());
        textview_num_rows.setTextSize(20);
        textview_num_rows.setTextColor(Color.BLACK);
        textview_num_rows.setPadding(50, 5, 0, 0);
        layout_content.addView(textview_num_rows);
  	}
	
	/*
	
	private void displayLocation(Location location){
		//Instantiate and set text for textviews
		TextView textview_name = (TextView) findViewById(R.id.tv_lookup_name);
        textview_name.setText("Location");
        
        TextView textview_desc = (TextView) findViewById(R.id.tv_lookup_desc);
        String content = "Well Column: " + location.getWellColumn() + " \n";
        content += "Well Row: " + location.getWellRow() + " \n";
        textview_desc.setText(content);
	}
	*/
	
	private void displayMixedSpecimen(MixedSpecimen mixedSpecimen){
		//Instantiate and set text for textviews
        TextView textview_name_header = new TextView(this);
        textview_name_header.setText("Mixed Specimen: ");
        textview_name_header.setTypeface(null, Typeface.BOLD);
        textview_name_header.setTextSize(20);
        textview_name_header.setTextColor(Color.BLACK);
        textview_name_header.setPadding(0, 5, 0, 0);
        layout_content.addView(textview_name_header);
        
        TextView textview_name = new TextView(this);
        textview_name.setText(mixedSpecimen.getFungiIsolated());
        textview_name.setTextSize(20);
        textview_name.setTextColor(Color.BLACK);
        layout_content.addView(textview_name);
        
        TextView textview_note_header = new TextView(this);
        textview_note_header.setText("Notes: ");
        textview_note_header.setTypeface(null, Typeface.BOLD);
        textview_note_header.setTextSize(20);
        textview_note_header.setPadding(0, 5, 0, 0);
        textview_note_header.setTextColor(Color.BLACK);
        layout_content.addView(textview_note_header);
        
        TextView textview_note = new TextView(this);
        textview_note.setText(mixedSpecimen.getNotes());
        textview_note.setTextSize(20);
        textview_note.setTextColor(Color.BLACK);
        layout_content.addView(textview_note);
        
        TextView textview_treatment_header = new TextView(this);
        textview_treatment_header.setText("Treatment: ");
        textview_treatment_header.setTypeface(null, Typeface.BOLD);
        textview_treatment_header.setTextSize(20);
        textview_treatment_header.setPadding(0, 5, 0, 0);
        textview_treatment_header.setTextColor(Color.BLACK);
        layout_content.addView(textview_treatment_header);
        
        TextView textview_treatment = new TextView(this);
        textview_treatment.setText(mixedSpecimen.getTreatment());
        textview_treatment.setTextSize(20);
        textview_treatment.setTextColor(Color.BLACK);
        layout_content.addView(textview_treatment);
        
        TextView textview_project_header = new TextView(this);
        textview_project_header.setText("Project: ");
        textview_project_header.setTypeface(null, Typeface.BOLD);
        textview_project_header.setTextSize(20);
        textview_project_header.setPadding(0, 5, 0, 0);
        textview_project_header.setTextColor(Color.BLACK);
        layout_content.addView(textview_project_header);
        
        TextView textview_project = new TextView(this);
        textview_project.setText(mixedSpecimen.getProject());
        textview_project.setTextSize(20);
        textview_project.setTextColor(Color.BLACK);
        layout_content.addView(textview_project);
        
        TextView textview_location_header = new TextView(this);
        textview_location_header.setText("Location: ");
        textview_location_header.setTypeface(null, Typeface.BOLD);
        textview_location_header.setTextSize(20);
        textview_location_header.setPadding(0, 5, 0, 0);
        textview_location_header.setTextColor(Color.BLACK);
        layout_content.addView(textview_location_header);
        
        TextView textview_location1 = new TextView(this);
        textview_location1.setText("Container ID: "+mixedSpecimen.getLocation().getContainerId());
        textview_location1.setPadding(50, 0, 0, 0);
        textview_location1.setTextSize(20);
        textview_location1.setTextColor(Color.BLACK);
        layout_content.addView(textview_location1);
        
        TextView textview_location2 = new TextView(this);
        textview_location2.setText("Well Row: "+mixedSpecimen.getLocation().getWellRow());
        textview_location2.setPadding(50, 0, 0, 0);
        textview_location2.setTextSize(20);
        textview_location2.setTextColor(Color.BLACK);
        layout_content.addView(textview_location2);    
        
        TextView textview_location3 = new TextView(this);
        textview_location3.setText("Well Row: "+mixedSpecimen.getLocation().getWellRow());
        textview_location3.setPadding(50, 0, 0, 0);
        textview_location3.setTextSize(20);
        textview_location3.setTextColor(Color.BLACK);
        layout_content.addView(textview_location3);       
        
	}
	/*
	private void displayPcrPrimer(PcrPrimer pcrPrimer){
		//Instantiate and set text for textviews
        TextView textview_name = (TextView) findViewById(R.id.tv_lookup_name);
        textview_name.setText("PCR Primer: " + pcrPrimer.getPrimerName());
        
        TextView textview_desc = (TextView) findViewById(R.id.tv_lookup_desc);
        String content = "Direction: " + pcrPrimer.getDirection() + " \n";

        textview_desc.setText(content);
	}
	
	private void displaySample(Sample sample){
		//Instantiate and set text for textviews
		TextView textview_name = (TextView) findViewById(R.id.tv_lookup_name);
        textview_name.setText("Sample: " + sample.getSampleName());
        
        TextView textview_desc = (TextView) findViewById(R.id.tv_lookup_desc);
        String content = "Version: " + sample.getVersion() + " \n";
        content += "Experimenter: " + sample.getExperimenter() + " \n";
        content += "Extraction Date: " + sample.getExtractionDate() + " \n";
        content += "Genus: " + sample.getSpecimenTaxonomyGenus() + " \n";
        content += "Species: " + sample.getSpecimenTaxonomySpecies() + " \n";
        content += "Biological Collection: " + sample.getMixedSpecimenBiologicalCollection() + " \n";
        content += "Mixed Specimen Number: " + sample.getMixedSpecimenNumber() + " \n";

        textview_desc.setText(content);
	}
	
	private void displaySpecimenReplicate(SpecimenReplicate specimenReplicate){
    	//Instantiate and set text for textviews
		TextView textview_name = (TextView) findViewById(R.id.tv_lookup_name);
        textview_name.setText("Specimen Replicate: " + specimenReplicate.getName());
        
        TextView textview_desc = (TextView) findViewById(R.id.tv_lookup_desc);
        String content = "State: " + specimenReplicate.getState() + " \n";
        content += "Specimen Identifier: " + specimenReplicate.getSpecimenIdentifier() + " \n";
        content += "Version: " + specimenReplicate.getVersion() + " \n";
        content += "Contents: " + specimenReplicate.getContents() + " \n";
        content += "Notes: " + specimenReplicate.getNotes() + " \n";
        content += "Storage Medium: " + specimenReplicate.getStorageMedium() + " \n";
        content += "Start Date: " + specimenReplicate.getStartDate() + " \n";
        content += "Revival Date: " + specimenReplicate.getRevivalDate() + " \n";
        content += "Date Destroyed: " + specimenReplicate.getDateDestroyed() + " \n";
        
        textview_desc.setText(content);
	}
	
	private void displayStorage(Storage storage){
		//Instantiate and set text for textviews
		TextView textview_name = (TextView) findViewById(R.id.tv_lookup_name);
        textview_name.setText("Storage: " + storage.getStorageName());
        
        TextView textview_desc = (TextView) findViewById(R.id.tv_lookup_desc);
        String content = "Room: " + storage.getRoom() + " \n";
        content += "Compartment Types: " + storage.getCompartmentTypes() + " \n";
        content += "Number Of Shelves: " + storage.getNumberOfShelves() + " \n";
        textview_desc.setText(content);
	}
	
	*/
	
	//***************************************
	// Display Methods - END
	//***************************************
	
	private void launchScanner(String action){
		   Intent intent = new Intent(this, ScannerActivity.class);
		   intent.putExtra("SCAN_ACTION", action);
		   
		   startActivityForResult(intent,0);
	 }
	
	@Override
	public void onServiceCalled(String method, Object output) {
		if(method.equalsIgnoreCase(ServiceTask.GET_BY_ID)){
			if(output !=null && !entityType.isEmpty()){
				
		        if (entityType.equalsIgnoreCase("CON") || entityType.equalsIgnoreCase("07")){
		        	Container container = (Container)output;
		        	displayContainer(container);
		        }else if(entityType.equalsIgnoreCase("LOC")){
		        	Location location = (Location)output;
		        	//displayLocation(location);
		        }else if(entityType.equalsIgnoreCase("MSP") || entityType.equalsIgnoreCase("03")){
		        	MixedSpecimen mixedSpecimen = (MixedSpecimen)output;
		        	displayMixedSpecimen(mixedSpecimen);
		        }else if(entityType.equalsIgnoreCase("PRI") || entityType.equalsIgnoreCase("05")){
		        	PcrPrimer pcrPrimer = (PcrPrimer)output;
		        	//displayPcrPrimer(pcrPrimer);
		        }else if(entityType.equalsIgnoreCase("SAM") || entityType.equalsIgnoreCase("04")){
		        	Sample sample = (Sample)output;
		        	//displaySample(sample);
		        }else if(entityType.equalsIgnoreCase("SPE") || entityType.equalsIgnoreCase("01") || entityType.equalsIgnoreCase("02")){
		        	SpecimenReplicate specimenReplicate = (SpecimenReplicate)output;
		        	//displaySpecimenReplicate(specimenReplicate);
		        }else if(entityType.equalsIgnoreCase("STR") || entityType.equalsIgnoreCase("08")){
		        	Storage storage = (Storage)output;
		        	//displayStorage(storage);
		        }
				
			}else{
				// display an error message
		        TextView textview_error = new TextView(this);
		        textview_error.setText(R.string.no_data_error);
		        textview_error.setPadding(10, 0, 0, 0);
		        textview_error.setTextSize(20);
		        layout_content.addView(textview_error);
	           }
			
		}
	}
}
