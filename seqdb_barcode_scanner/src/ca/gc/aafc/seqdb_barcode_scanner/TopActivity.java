package ca.gc.aafc.seqdb_barcode_scanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TopActivity extends Activity{
	Button button_scanLogin;
	Button button_manualLogin;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_top);
        
        button_scanLogin = (Button) findViewById(R.id.b_scanLogin);
        button_manualLogin = (Button) findViewById(R.id.b_manualLogin);
        
        button_scanLogin.setOnClickListener(Button_Click_Listener);
        button_manualLogin.setOnClickListener(Button_Click_Listener);

	}
	
	OnClickListener Button_Click_Listener = new OnClickListener(){
		public void onClick(View v){
			int id_of_view = v.getId();

			//login button
			if(id_of_view == button_scanLogin.getId()){
				Intent intent = new Intent(TopActivity.this, ScanPlaceholderActivity.class);
				//closes all activities that start after LookupActivity
				//intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
				intent.putExtra("next_activity", "main_menu_activity");


				startActivity(intent);
			}
			else if(id_of_view == button_manualLogin.getId()){
				Intent intent = new Intent(TopActivity.this, LoginActivity.class);

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
