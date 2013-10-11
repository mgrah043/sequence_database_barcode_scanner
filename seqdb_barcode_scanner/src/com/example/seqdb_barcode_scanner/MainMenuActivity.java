package com.example.seqdb_barcode_scanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainMenuActivity extends Activity{
	Button button_lookup;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_mainmenu);
        
        button_lookup = (Button) findViewById(R.id.b_lookup);

        button_lookup.setOnClickListener(Button_Click_Listener);

	}
	
	OnClickListener Button_Click_Listener = new OnClickListener(){
		public void onClick(View v){
			int id_of_view = v.getId();

			//login button
			if(id_of_view == button_lookup.getId()){
				Intent intent = new Intent(MainMenuActivity.this, LookupActivity.class);
				//closes all activities that start after LookupActivity
				//intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 

				startActivity(intent);
			}
		}
	};

	
	@Override
	public void onBackPressed() {
	
		//Exit to home screen and keep activity alive for re-entry to this point in the app
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		
	}
	
	
	
}
