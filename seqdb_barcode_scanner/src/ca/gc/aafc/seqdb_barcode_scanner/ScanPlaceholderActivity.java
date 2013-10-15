package ca.gc.aafc.seqdb_barcode_scanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class ScanPlaceholderActivity extends Activity{

	Button button_scanPlaceholder;
	String nextActivity;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_scan_placeholder);

        nextActivity = getIntent().getStringExtra("next_activity");
        button_scanPlaceholder = (Button) findViewById(R.id.b_scanPlaceholder);
        
        button_scanPlaceholder.setOnClickListener(Button_Click_Listener);
	}
	
	OnClickListener Button_Click_Listener = new OnClickListener(){
		public void onClick(View v){
			int id_of_view = v.getId();

			//login button
			if(id_of_view == button_scanPlaceholder.getId()){
				System.out.println(nextActivity);

				if(nextActivity.equals("main_menu_activity")){
					Intent intent = new Intent(ScanPlaceholderActivity.this, MainMenuActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					Toast.makeText(ScanPlaceholderActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
					startActivity(intent);
					finish();
				}
				else if(nextActivity.equals("lookup_activity")){
					Intent intent = new Intent(ScanPlaceholderActivity.this, LookupActivity.class);
					startActivity(intent);
					finish();

				}
				
			}
		}
	};
}
