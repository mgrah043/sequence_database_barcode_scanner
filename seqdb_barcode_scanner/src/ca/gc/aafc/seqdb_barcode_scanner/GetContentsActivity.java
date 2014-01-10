package ca.gc.aafc.seqdb_barcode_scanner;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;


public class GetContentsActivity extends Activity{
	
	TextView header_title;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_get_contents);
        
        
        header_title = (TextView) findViewById(R.id.tv_header_main_title);
        header_title.setText("Get Contents Result");
        
        Toast.makeText(GetContentsActivity.this, "In get contents", Toast.LENGTH_LONG).show();
   }



	OnClickListener Button_Click_Listener = new OnClickListener(){
		public void onClick(View v){
			int id_of_view = v.getId();

		}
	};

	@Override
	public void onBackPressed() {
	
		finish();
		
	}
	
		
}
