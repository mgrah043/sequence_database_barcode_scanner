package com.example.seqdb_barcode_scanner;

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

public class MainActivity extends Activity {
	public final static String SCANNER = "com.google.zxing.client.android.SCAN";
    public Button scannerBtn;
    public TextView helloWorld;
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        
        scannerBtn = (Button) findViewById(R.id.ScannerBtn);
        helloWorld = (TextView) findViewById(R.id.textView1);
        /*
         * this is quick and dirty and needs to be cleaned it is just for the purpose of making a working prototype asap
         * 
         * The onclick callback is setup here
         * 
         * */
        scannerBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to clicking on the scanner button :)
            	Intent intent = new Intent(SCANNER);
            	startActivityForResult(intent, 0);
            }
        });
        
    }
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		   if (requestCode == 0) {
		      if (resultCode == RESULT_OK) {
		         String contents = intent.getStringExtra("SCAN_RESULT");
		         String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
		         // Handle successful scan
		         System.out.println("contents : "+contents);
		         System.out.println("format :"+format);
		         this.helloWorld.setText("Barcode format : "+format+" -- Scanning Result : "+contents);
		      } else if (resultCode == RESULT_CANCELED) {
		         // Handle cancel
		    	  this.helloWorld.setText("No result you've cancelled the scanning :(");
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
