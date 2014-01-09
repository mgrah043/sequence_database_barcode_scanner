package ca.gc.aafc.seqdb_barcode_scanner.utils;

import android.content.*;
import android.content.SharedPreferences;
import ca.gc.aafc.seqdb_barcode_scanner.R;
import ca.gc.aafc.seqdb_barcode_scanner.service.ContainerService;
import ca.gc.aafc.seqdb_barcode_scanner.service.EntityServiceI;
import ca.gc.aafc.seqdb_barcode_scanner.service.LocationService;
import ca.gc.aafc.seqdb_barcode_scanner.service.MixedSpecimenService;
import ca.gc.aafc.seqdb_barcode_scanner.service.PcrPrimerService;
import ca.gc.aafc.seqdb_barcode_scanner.service.SampleService;
import ca.gc.aafc.seqdb_barcode_scanner.service.SpecimenReplicateService;
import ca.gc.aafc.seqdb_barcode_scanner.service.StorageService;

public class Session {
	private Context context;
	private SharedPreferences session;
	private SharedPreferences config;
	private SharedPreferences.Editor sessionEditor;
	
	public Session(Context c, String type){
		context = c;
		
		/*
		 * Getting the config shared preferences
		 * 
		 */
		config = context.getSharedPreferences(context.getString(R.string.config_file), Context.MODE_PRIVATE);
		
		/*
		 * type either move / bulkmove / lookup / getContent
		 * */
		session = context.getSharedPreferences(type, Context.MODE_PRIVATE);
		sessionEditor = session.edit();
	}
	
	public SharedPreferences getSession(){
		return this.session;
	}
	
	public SharedPreferences.Editor getSessionEditor(){
		return this.sessionEditor;
	}
	
	public EntityServiceI getService(String acronym){
		EntityServiceI service = null;
		String serverURL = this.config.getString("SERVER_URL", "http://localhost:4567/v1");
		
		if (acronym.equalsIgnoreCase("CON")){
			service = new ContainerService(serverURL);
		} else if (acronym.equalsIgnoreCase("LOC")){
			service = new LocationService(serverURL);
		} else if (acronym.equalsIgnoreCase("MSP")){
			service = new MixedSpecimenService(serverURL);
		} else if (acronym.equalsIgnoreCase("PPR")){
			service = new PcrPrimerService(serverURL);
		} else if (acronym.equalsIgnoreCase("SAM")){
			service = new SampleService(serverURL);
		} else if (acronym.equalsIgnoreCase("SPR")){
			service = new SpecimenReplicateService(serverURL);
		} else if (acronym.equalsIgnoreCase("STG")){
			service = new StorageService(serverURL);
		}
		
		return service;
	}

	

}
