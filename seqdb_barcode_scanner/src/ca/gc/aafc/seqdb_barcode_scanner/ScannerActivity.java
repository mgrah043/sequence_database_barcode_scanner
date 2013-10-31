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
        cameraManager = new CameraManager();
        
        /*
         * 
         * Check if camera is available
         * 
         * */
        if(cameraManager.getCamera() == null){
        	// there's no camera please check your device and enable your camera
        	Log.e(CameraManager.class.getSimpleName(), "There's no camera activated on this device");
        	
        }else{
        	captureHandler = new CaptureHandler(cameraManager, this, new OnDecoded());

        	//requesting next frame for decoding
        	cameraManager.requestNextFrame(new PreviewCallback(captureHandler, cameraManager));

        	// Create our Preview view and set it as the content of our activity.
        	cameraPreview = (CameraPreviewView) findViewById(R.id.camera_preview);
        	cameraPreview.setCameraManager(cameraManager);
        	((BoundingView) findViewById(R.id.bounding_view)).setCameraManager(cameraManager);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraManager.release();
    }

    private class OnDecoded implements CaptureHandler.OnDecodedCallback {
        @Override
        public void onDecoded(String decodedData) {
        	Intent returnIntent = new Intent();
        	
        	Bundle dataBundle = new Bundle();
        	dataBundle.putString("DATA_RESULT", decodedData);
        	
        	String nextActivity = (getIntent().getStringExtra("NEXT_ACTIVITY") != "")? getIntent().getStringExtra("NEXT_ACTIVITY") : null;
        	dataBundle.putString("NEXT_ACTIVITY", nextActivity);
        	
        	returnIntent.putExtras(dataBundle);
        	setResult(RESULT_OK,returnIntent);
        	
        	finish();
        }
    }
}
