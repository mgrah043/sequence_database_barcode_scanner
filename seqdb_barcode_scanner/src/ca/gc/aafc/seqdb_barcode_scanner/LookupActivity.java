package ca.gc.aafc.seqdb_barcode_scanner;

import ca.gc.aafc.seqdb_barcode_scanner.entities.Container;
import ca.gc.aafc.seqdb_barcode_scanner.entities.ContainerType;
import ca.gc.aafc.seqdb_barcode_scanner.entities.Location;
import ca.gc.aafc.seqdb_barcode_scanner.entities.MixedSpecimen;
import ca.gc.aafc.seqdb_barcode_scanner.entities.PcrPrimer;
import ca.gc.aafc.seqdb_barcode_scanner.entities.Sample;
import ca.gc.aafc.seqdb_barcode_scanner.entities.SpecimenReplicate;
import ca.gc.aafc.seqdb_barcode_scanner.entities.Storage;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class LookupActivity extends Activity{
	
	//Variable declaration
	ImageButton button_mainMenu;
	TextView header_title;
	TextView textview_name;
	TextView textview_desc;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //get the data to be displayed
        Bundle dataBundle = getIntent().getExtras();
        String type = (dataBundle != null) ? dataBundle.getString("TYPE") : null;
        
        setContentView(R.layout.activity_lookup);
        
        //Instantiate and set click listener for buttons
        button_mainMenu = (ImageButton) findViewById(R.id.btn_header_menu);
        button_mainMenu.setOnClickListener(Button_Click_Listener);
        
        header_title = (TextView) findViewById(R.id.tv_header_main_title);
        header_title.setText("LOOKUP RESULT");
        
        if (type != null && dataBundle.getSerializable("ENTITY") != null){
	        if (type.equalsIgnoreCase("CON") || type.equalsIgnoreCase("07")){
	        	Container container = (Container)dataBundle.getSerializable("ENTITY");
	        	displayContainer(container);
	        }else if(type.equalsIgnoreCase("LOC")){
	        	Location location = (Location)dataBundle.getSerializable("ENTITY");
	        	displayLocation(location);
	        }else if(type.equalsIgnoreCase("MSP") || type.equalsIgnoreCase("03")){
	        	MixedSpecimen mixedSpecimen = (MixedSpecimen)dataBundle.getSerializable("ENTITY");
	        	displayMixedSpecimen(mixedSpecimen);
	        }else if(type.equalsIgnoreCase("PRI") || type.equalsIgnoreCase("05")){
	        	PcrPrimer pcrPrimer = (PcrPrimer)dataBundle.getSerializable("ENTITY");
	        	displayPcrPrimer(pcrPrimer);
	        }else if(type.equalsIgnoreCase("SAM") || type.equalsIgnoreCase("04")){
	        	Sample sample = (Sample)dataBundle.getSerializable("ENTITY");
	        	displaySample(sample);
	        }else if(type.equalsIgnoreCase("SPE") || type.equalsIgnoreCase("01") || type.equalsIgnoreCase("02")){
	        	SpecimenReplicate specimenReplicate = (SpecimenReplicate)dataBundle.getSerializable("ENTITY");
	        	displaySpecimenReplicate(specimenReplicate);
	        }else if(type.equalsIgnoreCase("STR") || type.equalsIgnoreCase("08")){
	        	Storage storage = (Storage)dataBundle.getSerializable("ENTITY");
	        	displayStorage(storage);
	        }
        }else{
        	// display an error message
        	textview_name = (TextView) findViewById(R.id.tv_lookup_name);
            textview_name.setText("Error: Content not found");
            
            textview_desc = (TextView) findViewById(R.id.tv_lookup_desc);
            //TODO add a detailed description of why the error occurred
            textview_desc.setText("");
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
	
}
