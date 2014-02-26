package ca.gc.aafc.seqdb_barcode_scanner.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
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
	
	OnServiceCallCompletedListener serviceCallListener = null;
	
	public ServiceTask(Activity activity){
		super();
		this.context = activity;
		
		try {
			this.serviceCallListener = (OnServiceCallCompletedListener) this.context;
        	
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnContentSelectedListener");
        }
	}
	
	/*
	 * Setters and getters
	 * */
	public void setContext(Activity activity){
		this.context = activity;
	}
	
	public Activity getContext(){
		return this.context;
	}
	
	public void setService(EntityServiceI service){
		this.service = service;
	}
	
	public EntityServiceI getService(){
		return this.service;
	}
	
	public void setServiceCall(String methodCall){
		this.serviceCall = methodCall;
	}
	
	public String getServiceCall(){
		return this.serviceCall;
	}
	
	
	/**
     * Represents a listener that will be notified of content selections.
     */
    public interface OnServiceCallCompletedListener {
        /**
         * @param 
         * @param
         * 
         */
        public void onServiceCalled(String method,Object output);
    }
	
	@Override
    protected void onPreExecute() {
    super.onPreExecute();
        progressDialog = ProgressDialog.show(this.context, "Loading", "Loading", true);
    }
	
	/*
	 * whenever we make a service call we should pass a map array with 
	 * the method's name as key and the params as object
	 * */
	@Override
	protected Result doInBackground(HashMap<String, Object>... params) {
		// TODO Auto-generated method stub
		
		for(HashMap<String,Object> param : params){
			for(Entry<String, Object> entry : param.entrySet()) {
			    String key = entry.getKey();
			    Object value = entry.getValue();

			    /*
			     * 
			     * */
			    if(key.equalsIgnoreCase("getById"))
			    	this.output = this.service.getById((Long) value);
			    
			    if(key.equalsIgnoreCase("deleteById"))
			    	this.output = this.service.deleteById((Long) value);
			    
			    if(key.equalsIgnoreCase("create"))
			    	this.output = this.service.create((Serializable) value);
			    
			    if(key.equalsIgnoreCase("update"))
			    	this.output = this.service.update((Serializable) value);
			    
			    this.serviceCall = key;
			}
		}
		return null;
	}
	
	@Override
    protected void onPostExecute(Result result) {
        progressDialog.dismiss();
        
        // call activity  
        if (null != this.serviceCallListener) {
        	this.serviceCallListener.onServiceCalled(this.serviceCall, this.output); // pass row then column
        	this.output = null;
        	this.setServiceCall("");//clear the call
        }
        
    }

	
}
