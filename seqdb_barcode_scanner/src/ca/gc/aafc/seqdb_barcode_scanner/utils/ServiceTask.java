package ca.gc.aafc.seqdb_barcode_scanner.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map.Entry;

import ca.gc.aafc.seqdb_barcode_scanner.service.EntityServiceI;
import android.app.Activity;
import android.app.ProgressDialog;
import android.database.CursorJoiner.Result;
import android.os.AsyncTask;

/**
* This allows for performing calls to the webservice in the background
* 
* @author Vincent Maliko
* 
* @version 1.0
*/
public class ServiceTask extends AsyncTask<HashMap<String,Object>,Void,Result> {
	private Activity context;
	private EntityServiceI service;
	private ProgressDialog progressDialog;
	private String serviceCall;
	private Object output;
	
	public static final String GET_BY_ID = "getById";
	public static final String GET_CONTAINER_BY_ID = "getContainerById";
	public static final String DELETE_BY_ID = "deleteById";
	public static final String CREATE = "create";
	public static final String UPDATE = "update";
	
	OnServiceCallCompletedListener serviceCallListener = null;
	
	public ServiceTask(Activity activity){
		super();
		context = activity;
		
		try {
			serviceCallListener = (OnServiceCallCompletedListener) context;
        	
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnContentSelectedListener");
        }
	}
	
	/*
	 * Setters and getters
	 * */
	public void setContext(Activity activity){
		context = activity;
	}
	
	public Activity getContext(){
		return context;
	}
	
	public void setService(EntityServiceI service){
		this.service = service;
	}
	
	public EntityServiceI getService(){
		return service;
	}
	
	public void setServiceCall(String methodCall){
		serviceCall = methodCall;
	}
	
	public String getServiceCall(){
		return serviceCall;
	}
	
	
	/**
     * Represents a listener that will be notified of content selections.
     */
    public interface OnServiceCallCompletedListener {
        /**
         * @param method
         * @param output
         * 
         */
        public void onServiceCalled(String method,Object output);
    }
	
	@Override
    protected void onPreExecute() {
    super.onPreExecute();
        progressDialog = ProgressDialog.show(context, "Loading", "Loading", true);
        
    }
	
	/*
	 * whenever we make a service call we should pass a map array with 
	 * the method's name as key and the params as object
	 * */
	@Override
	protected Result doInBackground(HashMap<String, Object>... params) {
		
		for(HashMap<String,Object> param : params){
			for(Entry<String, Object> entry : param.entrySet()) {
			    String key = entry.getKey();
			    Object value = entry.getValue();

			    if(key.equalsIgnoreCase(GET_BY_ID))
			    	output = service.getById((Long) value);
			    
			    if(key.equalsIgnoreCase(GET_CONTAINER_BY_ID))
			    	output = service.getById((Long) value);
			    
			    if(key.equalsIgnoreCase(DELETE_BY_ID))
			    	output = service.deleteById((Long) value);
			    
			    if(key.equalsIgnoreCase(CREATE))
			    	output = service.create((Serializable) value);
			    
			    if(key.equalsIgnoreCase(UPDATE))
			    	output = service.update((Serializable) value);
			    
			    serviceCall = key;
			}
		}
		return null;
	}
	
	@Override
    protected void onPostExecute(Result result) {
        progressDialog.dismiss();
        
        // call activity  
        if (null != serviceCallListener) {
        	serviceCallListener.onServiceCalled(serviceCall, output); // pass row then column
        	output = null;
        	setServiceCall("");//clear the call
        }
        
    }

	
}
