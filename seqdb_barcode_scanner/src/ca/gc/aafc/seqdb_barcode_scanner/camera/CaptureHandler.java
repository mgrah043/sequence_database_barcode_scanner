package ca.gc.aafc.seqdb_barcode_scanner.camera;



import ca.gc.aafc.seqdb_barcode_scanner.R;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;


/**
 */
public class CaptureHandler extends Handler {
    public static final String DECODED_DATA = "decoded_data";
    private CameraManager cameraManager;
    private Context context;
    private OnDecodedCallback callback;

    public CaptureHandler(CameraManager cameraManager, Context context, OnDecodedCallback callback) {
        this.cameraManager = cameraManager;
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case R.id.decoded:
                String data = msg.getData().getString(DECODED_DATA);
                //Toast.makeText(context, data, Toast.LENGTH_LONG).show();
                if (callback != null){
                    callback.onDecoded(data);
                }
                break;
            case R.id.decode_failed:
                //getting new frame
                cameraManager.requestNextFrame(new PreviewCallback(this, cameraManager));
                break;
        }
    }

    public static interface OnDecodedCallback {
        void onDecoded(String decodedData);
    }
}
