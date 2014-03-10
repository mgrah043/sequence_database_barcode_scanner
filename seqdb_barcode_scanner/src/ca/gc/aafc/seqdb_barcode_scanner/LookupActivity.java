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
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class LookupActivity extends Activity implements ServiceTask.OnServiceCallCompletedListener{
	
	//Variable declaration
	ImageButton button_mainMenu;
	TextView header_title;
	TextView textview_name;
	TextView textview_desc;
	
	private DataParser parser;
	private ServiceTask taskRunner;
	private String entityType = "";
	
	private Session lookupSession;
	static final String SESSION_TYPE = "LOOKUP";
	static final String SCAN_TYPE = "SCAN_BARCODE";
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        lookupSession = new Session(this,SESSION_TYPE);
	    Toast.makeText(this, "Please scan a barcode", Toast.LENGTH_LONG).show();
	    
		launchScanner(SCAN_TYPE);
		
		parser = new DataParser();
		taskRunner = new ServiceTask(this);
		
		 setContentView(R.layout.activity_lookup);
	        
        //Instantiate and set click listener for buttons
        button_mainMenu = (ImageButton) findViewById(R.id.btn_header_menu);
        button_mainMenu.setOnClickListener(Button_Click_Listener);
        
        header_title = (TextView) findViewById(R.id.tv_header_main_title);
        header_title.setText("LOOKUP RESULT");
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
				   parser.parse(decodedData);
				   entityType = parser.getAcronym();
				   
				   EntityServiceI service = lookupSession.getService(entityType);
				   
				   if(service != null){
					   taskRunner.setService(service);
					   HashMap<String,Object> params = new HashMap<String,Object>();
					   params.put("getById", parser.getId());
					   taskRunner.execute(params);
				   }
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

	private void displayContainer(Container container){
		ContainerType containerType = container.getContainerType();
		
		//Instantiate and set text for textviews
        textview_name = (TextView) findViewById(R.id.tv_lookup_name);
        textview_name.setText("Container: " + container.getContainerNumber());
        
        textview_desc = (TextView) findViewById(R.id.tv_lookup_desc);
        String content = "No Data is available";
        if (containerType != null){
        	content = "Container Type: \n";
            content += "\t Name: " + containerType.getName() + " \n";
            content += "\t Base Type: " + containerType.getBaseType() + " \n";
            content += "\t Number Of Wells: " + containerType.getNumberOfWells() + " \n";
            content += "\t Number Of Columns: " + containerType.getNumberOfColumns() + " \n";
            content += "\t Number Of Rows: " + containerType.getNumberOfRows() + " \n";
        }
        textview_desc.setText(content);
	}
	
	private void displayLocation(Location location){
		//Instantiate and set text for textviews
        textview_name = (TextView) findViewById(R.id.tv_lookup_name);
        textview_name.setText("Location");
        
        textview_desc = (TextView) findViewById(R.id.tv_lookup_desc);
        String content = "Well Column: " + location.getWellColumn() + " \n";
        content += "Well Row: " + location.getWellRow() + " \n";
        textview_desc.setText(content);
	}
	
	private void displayMixedSpecimen(MixedSpecimen mixedSpecimen){
		//Instantiate and set text for textviews
        textview_name = (TextView) findViewById(R.id.tv_lookup_name);
        textview_name.setText("Mixed Specimen: " + mixedSpecimen.getFungiIsolated());
        
        textview_desc = (TextView) findViewById(R.id.tv_lookup_desc);
        String content = "Notes: " + mixedSpecimen.getNotes() + " \n";
        content += "Treatment: " + mixedSpecimen.getTreatment() + " \n";
        content += "Project: " + mixedSpecimen.getProject() + " \n";

        textview_desc.setText(content);
	}
	
	private void displayPcrPrimer(PcrPrimer pcrPrimer){
		//Instantiate and set text for textviews
        textview_name = (TextView) findViewById(R.id.tv_lookup_name);
        textview_name.setText("PCR Primer: " + pcrPrimer.getPrimerName());
        
        textview_desc = (TextView) findViewById(R.id.tv_lookup_desc);
        String content = "Direction: " + pcrPrimer.getDirection() + " \n";

        textview_desc.setText(content);
	}
	
	private void displaySample(Sample sample){
		//Instantiate and set text for textviews
        textview_name = (TextView) findViewById(R.id.tv_lookup_name);
        textview_name.setText("Sample: " + sample.getSampleName());
        
        textview_desc = (TextView) findViewById(R.id.tv_lookup_desc);
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
        textview_name = (TextView) findViewById(R.id.tv_lookup_name);
        textview_name.setText("Specimen Replicate: " + specimenReplicate.getName());
        
        textview_desc = (TextView) findViewById(R.id.tv_lookup_desc);
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
        textview_name = (TextView) findViewById(R.id.tv_lookup_name);
        textview_name.setText("Storage: " + storage.getStorageName());
        
        textview_desc = (TextView) findViewById(R.id.tv_lookup_desc);
        String content = "Room: " + storage.getRoom() + " \n";
        content += "Compartment Types: " + storage.getCompartmentTypes() + " \n";
        content += "Number Of Shelves: " + storage.getNumberOfShelves() + " \n";
        textview_desc.setText(content);
	}
	
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
		if(method.equalsIgnoreCase("getById")){
			if(output !=null && !entityType.isEmpty()){
				
		        if (entityType.equalsIgnoreCase("CON") || entityType.equalsIgnoreCase("07")){
		        	Container container = (Container)output;
		        	displayContainer(container);
		        }else if(entityType.equalsIgnoreCase("LOC")){
		        	Location location = (Location)output;
		        	displayLocation(location);
		        }else if(entityType.equalsIgnoreCase("MSP") || entityType.equalsIgnoreCase("03")){
		        	MixedSpecimen mixedSpecimen = (MixedSpecimen)output;
		        	displayMixedSpecimen(mixedSpecimen);
		        }else if(entityType.equalsIgnoreCase("PRI") || entityType.equalsIgnoreCase("05")){
		        	PcrPrimer pcrPrimer = (PcrPrimer)output;
		        	displayPcrPrimer(pcrPrimer);
		        }else if(entityType.equalsIgnoreCase("SAM") || entityType.equalsIgnoreCase("04")){
		        	Sample sample = (Sample)output;
		        	displaySample(sample);
		        }else if(entityType.equalsIgnoreCase("SPE") || entityType.equalsIgnoreCase("01") || entityType.equalsIgnoreCase("02")){
		        	SpecimenReplicate specimenReplicate = (SpecimenReplicate)output;
		        	displaySpecimenReplicate(specimenReplicate);
		        }else if(entityType.equalsIgnoreCase("STR") || entityType.equalsIgnoreCase("08")){
		        	Storage storage = (Storage)output;
		        	displayStorage(storage);
		        }
				
			}else{
				// display an error message
	        	textview_name = (TextView) findViewById(R.id.tv_lookup_name);
	            textview_name.setText("Error: Content not found");
	            
	            textview_desc = (TextView) findViewById(R.id.tv_lookup_desc);
	            //TODO add a detailed description of why the error occurred
	            textview_desc.setText("");
	            
	            Toast.makeText(this, "Error getById failed", Toast.LENGTH_LONG).show();
			}
			
		}
	}
}
