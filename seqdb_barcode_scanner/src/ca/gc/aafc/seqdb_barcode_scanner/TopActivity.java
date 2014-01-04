package ca.gc.aafc.seqdb_barcode_scanner;

import ca.gc.aafc.seqdb_barcode_scanner.R;
import ca.gc.aafc.seqdb_barcode_scanner.utils.Config;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * Handles launching activity where user selects method of signing in
 */
public class TopActivity extends Activity{
	//Variable declaration
	Button button_scanLogin;
	Button button_manualLogin;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_top);
        /*
         * ViewGroup vg = (ViewGroup) findViewById(R.id.root);
         * Need to find a way to set font
         */
		
		//Instantiate and set click listeners for buttons
        button_scanLogin = (Button) findViewById(R.id.btn_scan_login);
        button_manualLogin = (Button) findViewById(R.id.btn_manual_login);
        
        button_scanLogin.setOnClickListener(Button_Click_Listener);
        button_manualLogin.setOnClickListener(Button_Click_Listener);

        //sets the configurations
        new Config(this);
	}
	
	OnClickListener Button_Click_Listener = new OnClickListener(){
		public void onClick(View v){
			int id_of_view = v.getId();

			//Scan login
			if(id_of_view == button_scanLogin.getId()){
				Intent intent = new Intent(TopActivity.this, ScannerActivity.class);
				startActivityForResult(intent,0);
			}
			//Manual login
			else if(id_of_view == button_manualLogin.getId()){
				Intent intent = new Intent(TopActivity.this, LoginActivity.class);
				startActivity(intent);
			}
		}
	};
	
	// Make sure the request was successful and send to main menu activity
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	   //Request was successful
	   if (resultCode == RESULT_OK) {
		   //data to be sent to next activity
	       Bundle resultBundle = data.getExtras();
	       String decodedData = resultBundle.getString("DATA_RESULT");
	       //TODO check if the decodedData is null if so then throw an error
	       
	       //Feedback using toast message showing the user's name/id
	       Toast.makeText(TopActivity.this, "Data decoded : "+decodedData, Toast.LENGTH_LONG).show();
	       System.out.print("Success data is - "+decodedData);
	       
	       Intent intent = new Intent(TopActivity.this, MainMenuActivity.class);
		   startActivity(intent);
		   
		   finish();
	   }
	   //Result failed
	   else{
		   System.out.print("Failure");
	   }
	   
	}
	
	@Override
	public void onBackPressed() {
	
		//Exit to home screen and keep activity alive for re-entry to this point in the app
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		
	}
	
	
	
}
