package ca.gc.aafc.seqdb_barcode_scanner.utils;

import android.content.*;
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
				
		String serverURL = this.config.getString("SERVER_URL", "http://192.168.43.160:8080/seqdb-ws/v1");


		if (acronym.equalsIgnoreCase("CON") || acronym.equalsIgnoreCase("07")){
			service = new ContainerService(serverURL);
		} else if (acronym.equalsIgnoreCase("LOC")){
			service = new LocationService(serverURL);
		} else if (acronym.equalsIgnoreCase("MSP") || acronym.equalsIgnoreCase("03")){
			service = new MixedSpecimenService(serverURL);
		} else if (acronym.equalsIgnoreCase("PRI") || acronym.equalsIgnoreCase("05")){
			service = new PcrPrimerService(serverURL);
		} else if (acronym.equalsIgnoreCase("SAM") || acronym.equalsIgnoreCase("04")){
			service = new SampleService(serverURL);
		} else if (acronym.equalsIgnoreCase("SPE") || acronym.equalsIgnoreCase("01") || acronym.equalsIgnoreCase("02")){
			service = new SpecimenReplicateService(serverURL);
		} else if (acronym.equalsIgnoreCase("STR") || acronym.equalsIgnoreCase("08")){
			service = new StorageService(serverURL);
//		} else if (acronym.equalsIgnoreCase("PRD") || acronym.equalsIgnoreCase("06")){
//			service = new ProductService(serverURL);
//		} else if (acronym.equalsIgnoreCase("ACC") || acronym.equalsIgnoreCase("00")){
//			service = new AccountService(serverURL);
		}

		return service;
	}

	

}
