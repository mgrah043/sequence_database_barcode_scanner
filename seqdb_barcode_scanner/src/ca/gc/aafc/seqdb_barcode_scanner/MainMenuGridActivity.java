package ca.gc.aafc.seqdb_barcode_scanner;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;

/**
 * Main Menu implemented using a grid
 * 
 * NOT CURRENTLY IN USE
 */

public class MainMenuGridActivity extends Activity{

Button button_switch;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_mainmenu_grid);
        
        //button_switch = (Button) findViewById(R.id.b_switch_layout);
        
       // button_switch.setOnClickListener(Button_Click_Listener);
        
        
        GridView gridview = (GridView) findViewById(R.id.gv_main_menu);
        gridview.setAdapter(new TextAdapter(this));
        
        
	}
	
	OnClickListener Button_Click_Listener = new OnClickListener(){
		public void onClick(View v){
			//int id_of_view = v.getId();

			//switch layout to list view
			//if(id_of_view == button_switch.getId()){
				//Intent intent = new Intent(MainMenuGridActivity.this, MainMenuActivity.class);
				//startActivity(intent);
			//}

		}
	};
	
	

}
