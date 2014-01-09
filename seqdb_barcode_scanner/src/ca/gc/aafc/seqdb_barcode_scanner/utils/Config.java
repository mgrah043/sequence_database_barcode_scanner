/**
 * 
 */
package ca.gc.aafc.seqdb_barcode_scanner.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import ca.gc.aafc.seqdb_barcode_scanner.R;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author NazirLKC
 *
 */
public class Config {
	private String CONFIG_FILE;
	private final String DEFAULT_SERVER_URL = "http://localhost:4567/v1";
	private String SERVER_URL = "";
	private String AUTHORIZATION = "";
	private Context context;
	
	public Config(Context context){
		this.context = context;
		
		CONFIG_FILE = context.getString(R.string.config_file);
		readConfigFile();
		
		SharedPreferences preferences = context.getSharedPreferences(CONFIG_FILE, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("SERVER_URL", SERVER_URL);
		editor.commit();
	}
	
	// Reads server address from /Android/data/ca.gc.aafc.seqdb_barcode_scanner/cache/
		private void readConfigFile() {
			File storage = context.getExternalCacheDir();
			File configFile = new File(storage, CONFIG_FILE);
			String line = "";
			
			// Create default config file if it doesn't exist
			if (!configFile.exists()){
				try {
					configFile.createNewFile();
					BufferedWriter bw = new BufferedWriter(new FileWriter(configFile));
					bw.write(DEFAULT_SERVER_URL);
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				SERVER_URL = DEFAULT_SERVER_URL;
			} else {
				// Read config file
				try {
					BufferedReader br = new BufferedReader(new FileReader(configFile));
					if ((line = br.readLine()) != null){
						SERVER_URL = line;
					}
					br.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		public String getSERVER_URL() {
			return SERVER_URL;
		}

		public void setSERVER_URL(String sERVER_URL) {
			//TODO write new url to config file
			SERVER_URL = sERVER_URL;
		}
		
}
