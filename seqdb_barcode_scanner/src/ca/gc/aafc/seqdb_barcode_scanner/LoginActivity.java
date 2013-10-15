package ca.gc.aafc.seqdb_barcode_scanner;


import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;


public class LoginActivity extends Activity {
    
	Button button_login;
	ImageView imageview_scanLogin;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_login);
  
        //initializing variables to layout elements
        button_login = (Button) findViewById(R.id.b_login);
        imageview_scanLogin = (ImageView) findViewById(R.id.iv_scanLogin);
        
        
        //setting click listeners
        button_login.setOnClickListener(Button_Click_Listener);
        imageview_scanLogin.setOnClickListener(Button_Click_Listener);

	}
	
	OnClickListener Button_Click_Listener = new OnClickListener(){
		public void onClick(View v){
			int id_of_view = v.getId();

			//login button
			if(id_of_view == button_login.getId()){
				Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
				Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
				startActivity(intent);
				finish();
			}
			
			//scan login imageview
			else if(id_of_view == imageview_scanLogin.getId()){
				Intent intent = new Intent(LoginActivity.this, ScanPlaceholderActivity.class);
				intent.putExtra("next_activity", "main_menu_activity");
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			}
		}
	};
}
