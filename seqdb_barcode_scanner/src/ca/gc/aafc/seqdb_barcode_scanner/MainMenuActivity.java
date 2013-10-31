package ca.gc.aafc.seqdb_barcode_scanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenuActivity extends Activity{
	Button button_lookup;
	Button button_getContents;
	Button button_move;
	Button button_bulkMove;
	
	ImageButton button_mainMenu;
	
	TextView header_title;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_mainmenu);
        
        button_mainMenu = (ImageButton) findViewById(R.id.btn_header_menu);
        button_mainMenu.setVisibility(View.GONE);
        
        header_title = (TextView) findViewById(R.id.tv_header_main_title);
        
        button_lookup = (Button) findViewById(R.id.btn_lookup);
        button_lookup.setOnClickListener(Button_Click_Listener);
        
        button_getContents = (Button) findViewById(R.id.btn_getContents);
        button_getContents.setOnClickListener(Button_Click_Listener);
        
        button_move = (Button) findViewById(R.id.btn_move);
        button_move.setOnClickListener(Button_Click_Listener);
        
        button_bulkMove = (Button) findViewById(R.id.btn_bulkMove);
        button_bulkMove.setOnClickListener(Button_Click_Listener);
        
	}
	
	OnClickListener Button_Click_Listener = new OnClickListener(){
		public void onClick(View v){
			int id_of_view = v.getId();

			//login button
			if(id_of_view == button_lookup.getId()){
				Intent intent = new Intent(MainMenuActivity.this, ScannerActivity.class);
				intent.putExtra("NEXT_ACTIVITY", "lookup");
				
				startActivityForResult(intent,0);
				
			}else if(id_of_view == button_getContents.getId()){
				Toast.makeText(MainMenuActivity.this, "get contents button pressed", Toast.LENGTH_LONG).show();
				
			}else if(id_of_view == button_move.getId()){
				Toast.makeText(MainMenuActivity.this, "move button pressed", Toast.LENGTH_LONG).show();
				
			}else if(id_of_view == button_bulkMove.getId()){
				Toast.makeText(MainMenuActivity.this, "bulk move button pressed", Toast.LENGTH_LONG).show();
			}
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	   // Make sure the request was successful
	   if (resultCode == RESULT_OK) {
	       Bundle resultBundle = data.getExtras();
	       
	       String nextActivity = resultBundle.getString("NEXT_ACTIVITY");
	       
	       if(nextActivity.equalsIgnoreCase("lookup")){
	    	   
	    	   String decodedData = resultBundle.getString("DATA_RESULT");
	    	   Toast.makeText(MainMenuActivity.this, "Data decoded : "+decodedData, Toast.LENGTH_LONG).show();
	    	   //check if the decodedData is null if so then throw an error
		       
		       System.out.print("Success data is - "+decodedData);
		       
		       Intent intent = new Intent(MainMenuActivity.this, LookupActivity.class);
			   startActivity(intent);
			   
	       }else if(nextActivity.equalsIgnoreCase("move_step_1")){
	    	   
	       }else if(nextActivity.equalsIgnoreCase("move_step_2")){
	    	   
	       }else if(nextActivity.equalsIgnoreCase("bulkMove")){
	       
	       }else{
	    	   
	       }
	      
	   }else{
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
