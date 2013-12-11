package ca.gc.aafc.seqdb_barcode_scanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
/**
 *Used during testing of prototype
 *
 *NOT CURRENTLY IN USE
 */
public class ScanPlaceholderActivity extends Activity{

	//Variable declaration
	Button button_scanPlaceholder;
	String nextActivity;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_scan_placeholder);

        //get data from the intent
        nextActivity = getIntent().getStringExtra("next_activity");
        
        //Instantiate and set click listener for buttons
        button_scanPlaceholder = (Button) findViewById(R.id.b_scanPlaceholder);
        button_scanPlaceholder.setOnClickListener(Button_Click_Listener);
	}
	
	OnClickListener Button_Click_Listener = new OnClickListener(){
		public void onClick(View v){
			int id_of_view = v.getId();

			//Button to bring to next activity
			if(id_of_view == button_scanPlaceholder.getId()){
				System.out.println(nextActivity);

				//when the next activity is the main menu
				if(nextActivity.equals("main_menu_activity")){
					//Create an intent to go to the main menu
					Intent intent = new Intent(ScanPlaceholderActivity.this, MainMenuActivity.class);
					//add intent flag to make the calling activity refresh
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					//User feedback using toast message
					Toast.makeText(ScanPlaceholderActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
					startActivity(intent);
					finish();
				}
				//when the next activity is lookup
				else if(nextActivity.equals("lookup_activity")){
					//Create an intent to go to lookup
					Intent intent = new Intent(ScanPlaceholderActivity.this, LookupActivity.class);
					startActivity(intent);
					finish();
				}
			}
		}
	};
}
