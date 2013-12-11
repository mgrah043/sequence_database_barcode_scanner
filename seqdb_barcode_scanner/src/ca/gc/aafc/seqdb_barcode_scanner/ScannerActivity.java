package ca.gc.aafc.seqdb_barcode_scanner; 

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import ca.gc.aafc.seqdb_barcode_scanner.camera.CameraManager;
import ca.gc.aafc.seqdb_barcode_scanner.camera.CaptureHandler;
import ca.gc.aafc.seqdb_barcode_scanner.camera.PreviewCallback;
import ca.gc.aafc.seqdb_barcode_scanner.camera.view.BoundingView;
import ca.gc.aafc.seqdb_barcode_scanner.camera.view.CameraPreviewView;

/**
 * Capture activity (camera barcode activity)
 */
public class ScannerActivity extends Activity {
	
    /**
     * Camera preview view
     */
    private CameraPreviewView cameraPreview;
    /**
     * Camera manager
     */
    private CameraManager cameraManager;
    
    /**
     * Capture handler
     */
    private Handler captureHandler;
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        
        // Create an instance of Camera
        cameraManager = new CameraManager(this);
        
        //Check if camera is available
        if(cameraManager.getCamera() == null){
        	//No camera or camera not enabled on device
        	Log.e(CameraManager.class.getSimpleName(), "There's no camera activated on this device");
        	
        }
        //There is a camera enabled on the device
        else{  
        	
        	captureHandler = new CaptureHandler(cameraManager, new OnDecoded());

        	//Requesting next frame for decoding
        	cameraManager.requestNextFrame(new PreviewCallback(captureHandler, cameraManager));

        	// Create our Preview view and set it as the content of the activity.
        	cameraPreview = (CameraPreviewView) findViewById(R.id.camera_preview);
        	cameraPreview.setCameraManager(cameraManager);
        	((BoundingView) findViewById(R.id.bounding_view)).setCameraManager(cameraManager);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //release camera when application is paused
        cameraManager.release();
    }

    private class OnDecoded implements CaptureHandler.OnDecodedCallback {
        @Override
        public void onDecoded(String decodedData) {
        	//Create intent
        	Intent returnIntent = new Intent();
        	
        	//Data to send through intent
        	Bundle dataBundle = new Bundle();
        	dataBundle.putString("DATA_RESULT", decodedData);
        	
        	//Next activity to be shown
        	String nextActivity = (getIntent().getStringExtra("NEXT_ACTIVITY") != "")? getIntent().getStringExtra("NEXT_ACTIVITY") : null;
        	dataBundle.putString("NEXT_ACTIVITY", nextActivity);
        	
        	//add data to intent and go back to previous activity
        	returnIntent.putExtras(dataBundle);
        	setResult(RESULT_OK,returnIntent);
        	
        	finish();
        }
    }
}
