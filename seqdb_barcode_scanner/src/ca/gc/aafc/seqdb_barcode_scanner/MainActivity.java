package ca.gc.aafc.seqdb_barcode_scanner;

/*
 * This is a comment to test the first commit
 * Let me know if this works
 * 
 * new newnewnwen
 * 
 * test
 * te
 * tes
 * tes
 *  TESTING again
 * */
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	//Variable declaration
	public final static String SCANNER = "com.google.zxing.client.android.SCAN";
    public Button scannerBtn;
    public TextView helloWorld;
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        
        //Instantiate and set click listener for button
        scannerBtn = (Button) findViewById(R.id.ScannerBtn);
        scannerBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(SCANNER);
            	startActivityForResult(intent, 0);
            }
        });
        
    }
	
	//Handles activities that were started for a result
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		   if (requestCode == 0) {
		      // Handle successful scan
		      if (resultCode == RESULT_OK) {
		         String contents = intent.getStringExtra("SCAN_RESULT");
		         String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
		         System.out.println("contents : "+contents);
		         System.out.println("format :"+format);
		         this.helloWorld.setText("Barcode format : "+format+" -- Scanning Result : "+contents);
		      } 
		      //Handle cancelled scan
		      else if (resultCode == RESULT_CANCELED) {
	                Toast.makeText(this, "No result since the scanner was cancelled", Toast.LENGTH_LONG).show();
		      }
		   }
		}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
