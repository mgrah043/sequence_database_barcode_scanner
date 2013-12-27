package ca.gc.aafc.seqdb_barcode_scanner;

import ca.gc.aafc.seqdb_barcode_scanner.entities.Location;
import ca.gc.aafc.seqdb_barcode_scanner.entities.SpecimenReplicate;
import android.app.Activity;
import android.content.Intent;
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
        String type = savedInstanceState.getString("TYPE");
        
        setContentView(R.layout.activity_lookup);
        
        //Instantiate and set click listener for buttons
        button_mainMenu = (ImageButton) findViewById(R.id.btn_header_menu);
        button_mainMenu.setOnClickListener(Button_Click_Listener);
        
        header_title = (TextView) findViewById(R.id.tv_header_main_title);
        header_title.setText("LOOKUP RESULT");
        
        if (type != null && type.equalsIgnoreCase("SPR")){
        	SpecimenReplicate specimenReplicate = (SpecimenReplicate)savedInstanceState.getSerializable("ENTITY");
        	Location location = specimenReplicate.getLocation();
        	
        	//Instantiate and set text for textviews
            textview_name = (TextView) findViewById(R.id.tv_lookup_spec);
            textview_name.setText(specimenReplicate.getName());
            
            textview_desc = (TextView) findViewById(R.id.tv_lookup_desc);
            String content = "State : " + specimenReplicate.getState() + " \n";
            content += "Specimen Identifier : " + specimenReplicate.getSpecimenIdentifier() + " \n";
            content += "Version : " + specimenReplicate.getVersion() + " \n";
            content += "Contents : " + specimenReplicate.getContents() + " \n";
            content += "Notes : " + specimenReplicate.getNotes() + " \n";
            content += "Storage Medium : " + specimenReplicate.getStorageMedium() + " \n";
            content += "Start Date : " + specimenReplicate.getStartDate() + " \n";
            content += "Revival Date : " + specimenReplicate.getRevivalDate() + " \n";
            content += "Date Destroyed : " + specimenReplicate.getDateDestroyed() + " \n";
            
            content += "\n Location \n";
            content += "Container Number : " + location.getContainerNumber() + " \n";
            content += "Storage Unit : " + location.getStorageUnit() + " \n";
            content += "Compartment : " + location.getCompartment() + " \n";
            content += "Shelf : " + location.getShelf() + " \n";
            content += "Rack : " + location.getRack() + " \n";
            content += "Date Moved : " + location.getDateMoved() + " \n";
            content += "Well Column : " + location.getWellColumn() + " \n";
            content += "Well Row : " + location.getWellRow() + " \n";
            textview_desc.setText(content);
        }
        // TODO add other options besides SPR
        
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			Bundle resultBundle = data.getExtras();
			String type = resultBundle.getString("TYPE");
			
			if (type != null && type.equalsIgnoreCase("SPR")){
	        	SpecimenReplicate specimenReplicate = (SpecimenReplicate)resultBundle.getSerializable("ENTITY");
	        	Location location = specimenReplicate.getLocation();
	        	
	        	//Instantiate and set text for textviews
	            textview_name = (TextView) findViewById(R.id.tv_lookup_spec);
	            textview_name.setText(specimenReplicate.getName());
	            
	            textview_desc = (TextView) findViewById(R.id.tv_lookup_desc);
	            String content = "State : " + specimenReplicate.getState() + " \n";
	            content += "Specimen Identifier : " + specimenReplicate.getSpecimenIdentifier() + " \n";
	            content += "Version : " + specimenReplicate.getVersion() + " \n";
	            content += "Contents : " + specimenReplicate.getContents() + " \n";
	            content += "Notes : " + specimenReplicate.getNotes() + " \n";
	            content += "Storage Medium : " + specimenReplicate.getStorageMedium() + " \n";
	            content += "Start Date : " + specimenReplicate.getStartDate() + " \n";
	            content += "Revival Date : " + specimenReplicate.getRevivalDate() + " \n";
	            content += "Date Destroyed : " + specimenReplicate.getDateDestroyed() + " \n";
	            
	            content += "\n Location \n";
	            content += "Container Number : " + location.getContainerNumber() + " \n";
	            content += "Storage Unit : " + location.getStorageUnit() + " \n";
	            content += "Compartment : " + location.getCompartment() + " \n";
	            content += "Shelf : " + location.getShelf() + " \n";
	            content += "Rack : " + location.getRack() + " \n";
	            content += "Date Moved : " + location.getDateMoved() + " \n";
	            content += "Well Column : " + location.getWellColumn() + " \n";
	            content += "Well Row : " + location.getWellRow() + " \n";
	            textview_desc.setText(content);
	        }
	        // TODO add other options besides SPR
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
	
	
	
}
