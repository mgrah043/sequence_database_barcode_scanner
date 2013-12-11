package ca.gc.aafc.seqdb_barcode_scanner;

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
	TextView textview_specimen;
	TextView textview_specimen_desc;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_lookup);
        
        //Instantiate and set click listener for buttons
        button_mainMenu = (ImageButton) findViewById(R.id.btn_header_menu);
        button_mainMenu.setOnClickListener(Button_Click_Listener);
        
        header_title = (TextView) findViewById(R.id.tv_header_main_title);
        header_title.setText("LOOKUP RESULT");
        
        //Instantiate and set text for textviews
        textview_specimen = (TextView) findViewById(R.id.tv_lookup_spec);
        textview_specimen.setText("Sample Name");
        
        textview_specimen_desc = (TextView) findViewById(R.id.tv_lookup_desc);
        String content = "Version : version content \n";
        content += "Experimenter : experimenter content \n";
        content += "Extraction Date : date \n";
        content += "Specimen . Taxonomy . Genus \n";
        content += "Specimen . Taxonomy . Species \n";
        content += "Location : location goes here \n";
        textview_specimen_desc.setText(content);
        
        
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
