package ca.gc.aafc.seqdb_barcode_scanner;

import ca.gc.aafc.seqdb_barcode_scanner.entities.Container;
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
        
        if (type != null){
	        if (type.equalsIgnoreCase("CON")){
	        	Container container = (Container)dataBundle.getSerializable("ENTITY");
	        	displayContainer(container);
	        }else if(type.equalsIgnoreCase("LOC")){
	        	Location location = (Location)dataBundle.getSerializable("ENTITY");
	        	displayLocation(location);
	        }else if(type.equalsIgnoreCase("MSP")){
	        	MixedSpecimen mixedSpecimen = (MixedSpecimen)dataBundle.getSerializable("ENTITY");
	        	displayMixedSpecimen(mixedSpecimen);
	        }else if(type.equalsIgnoreCase("PPR")){
	        	PcrPrimer pcrPrimer = (PcrPrimer)dataBundle.getSerializable("ENTITY");
	        	displayPcrPrimer(pcrPrimer);
	        }else if(type.equalsIgnoreCase("SAM")){
	        	Sample sample = (Sample)dataBundle.getSerializable("ENTITY");
	        	displaySample(sample);
	        }else if(type.equalsIgnoreCase("SPR")){
	        	SpecimenReplicate specimenReplicate = (SpecimenReplicate)dataBundle.getSerializable("ENTITY");
	        	displaySpecimenReplicate(specimenReplicate);
	        }else if(type.equalsIgnoreCase("STG")){
	        	Storage storage = (Storage)dataBundle.getSerializable("ENTITY");
	        	displayStorage(storage);
	        }
        }
        // TODO add other options besides SPR
        
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
		//Instantiate and set text for textviews
        textview_name = (TextView) findViewById(R.id.tv_lookup_name);
        textview_name.setText("Container: " + container.getContainer());
        
        textview_desc = (TextView) findViewById(R.id.tv_lookup_desc);
        String content = "Storage Unit: " + container.getStorageUnit() + " \n";
        content += "Compartment: " + container.getCompartment() + " \n";
        content += "Shelf: " + container.getShelf() + " \n";
        content += "Rack: " + container.getRack() + " \n";
        textview_desc.setText(content);
	}
	
	private void displayLocation(Location location){
		//Instantiate and set text for textviews
        textview_name = (TextView) findViewById(R.id.tv_lookup_name);
        textview_name.setText("Location");
        
        textview_desc = (TextView) findViewById(R.id.tv_lookup_desc);
        String content = "Container Number: " + location.getContainerNumber() + " \n";
        content += "Storage Unit: " + location.getStorageUnit() + " \n";
        content += "Compartment: " + location.getCompartment() + " \n";
        content += "Shelf: " + location.getShelf() + " \n";
        content += "Rack: " + location.getRack() + " \n";
        content += "Date Moved: " + location.getDateMoved() + " \n";
        content += "Well Column: " + location.getWellColumn() + " \n";
        content += "Well Row: " + location.getWellRow() + " \n";
        textview_desc.setText(content);
	}
	
	private void displayMixedSpecimen(MixedSpecimen mixedSpecimen){
		Location location = mixedSpecimen.getLocation();
		
		//Instantiate and set text for textviews
        textview_name = (TextView) findViewById(R.id.tv_lookup_name);
        textview_name.setText("Mixed Specimen: " + mixedSpecimen.getMixedSpecimenNumber());
        
        textview_desc = (TextView) findViewById(R.id.tv_lookup_desc);
        String content = "Biological Collection: " + mixedSpecimen.getMixedSpecimenBiologicalCollection() + " \n";

        content += "\n Location \n";
        content += "Container Number: " + location.getContainerNumber() + " \n";
        content += "Storage Unit: " + location.getStorageUnit() + " \n";
        content += "Compartment: " + location.getCompartment() + " \n";
        content += "Shelf: " + location.getShelf() + " \n";
        content += "Rack: " + location.getRack() + " \n";
        content += "Date Moved: " + location.getDateMoved() + " \n";
        content += "Well Column: " + location.getWellColumn() + " \n";
        content += "Well Row: " + location.getWellRow() + " \n";
        textview_desc.setText(content);
	}
	
	private void displayPcrPrimer(PcrPrimer pcrPrimer){
		Location location = pcrPrimer.getLocation();
		
		//Instantiate and set text for textviews
        textview_name = (TextView) findViewById(R.id.tv_lookup_name);
        textview_name.setText("PCR Primer: " + pcrPrimer.getPrimerName());
        
        textview_desc = (TextView) findViewById(R.id.tv_lookup_desc);
        String content = "Direction: " + pcrPrimer.getDirection() + " \n";

        content += "\n Location \n";
        content += "Container Number: " + location.getContainerNumber() + " \n";
        content += "Storage Unit: " + location.getStorageUnit() + " \n";
        content += "Compartment: " + location.getCompartment() + " \n";
        content += "Shelf: " + location.getShelf() + " \n";
        content += "Rack: " + location.getRack() + " \n";
        content += "Date Moved: " + location.getDateMoved() + " \n";
        content += "Well Column: " + location.getWellColumn() + " \n";
        content += "Well Row: " + location.getWellRow() + " \n";
        textview_desc.setText(content);
	}
	
	private void displaySample(Sample sample){
		Location location = sample.getLocation();
		
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

        content += "\n Location \n";
        content += "Container Number: " + location.getContainerNumber() + " \n";
        content += "Storage Unit: " + location.getStorageUnit() + " \n";
        content += "Compartment: " + location.getCompartment() + " \n";
        content += "Shelf: " + location.getShelf() + " \n";
        content += "Rack: " + location.getRack() + " \n";
        content += "Date Moved: " + location.getDateMoved() + " \n";
        content += "Well Column: " + location.getWellColumn() + " \n";
        content += "Well Row: " + location.getWellRow() + " \n";
        textview_desc.setText(content);
	}
	
	private void displaySpecimenReplicate(SpecimenReplicate specimenReplicate){
		Location location = specimenReplicate.getLocation();
    	
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
        
        content += "\n Location \n";
        content += "Container Number: " + location.getContainerNumber() + " \n";
        content += "Storage Unit: " + location.getStorageUnit() + " \n";
        content += "Compartment: " + location.getCompartment() + " \n";
        content += "Shelf: " + location.getShelf() + " \n";
        content += "Rack: " + location.getRack() + " \n";
        content += "Date Moved: " + location.getDateMoved() + " \n";
        content += "Well Column: " + location.getWellColumn() + " \n";
        content += "Well Row: " + location.getWellRow() + " \n";
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
