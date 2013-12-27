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

import android.content.Context;

/**
 * @author NazirLKC
 *
 */
public class Config {
	final String CONFIG_FILE = "seqdb_barcode_scanner.properties";
	final String DEFAULT_SERVER_URL = "http://localhost:4567/v1";
	String SERVER_URL = "";
	String AUTHORIZATION = "";
	Context context;
	
	public Config(Context context){
		this.context = context;
		readConfigFile();
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
