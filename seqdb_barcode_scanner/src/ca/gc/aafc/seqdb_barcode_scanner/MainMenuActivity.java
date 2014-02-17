package ca.gc.aafc.seqdb_barcode_scanner;

import java.io.Serializable;

import ca.gc.aafc.seqdb_barcode_scanner.service.ContainerService;
import ca.gc.aafc.seqdb_barcode_scanner.service.EntityServiceI;
import ca.gc.aafc.seqdb_barcode_scanner.service.LocationService;
import ca.gc.aafc.seqdb_barcode_scanner.service.MixedSpecimenService;
import ca.gc.aafc.seqdb_barcode_scanner.service.PcrPrimerService;
import ca.gc.aafc.seqdb_barcode_scanner.service.SampleService;
import ca.gc.aafc.seqdb_barcode_scanner.service.SpecimenReplicateService;
import ca.gc.aafc.seqdb_barcode_scanner.service.StorageService;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenuActivity extends Activity{
	Button button_lookup;
	Button button_getContents;
	Button button_move;
	Button button_bulkMove;

	ImageButton button_mainMenu;

	TextView header_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_mainmenu);

		button_mainMenu = (ImageButton) findViewById(R.id.btn_header_menu);
		button_mainMenu.setVisibility(View.GONE);

		header_title = (TextView) findViewById(R.id.tv_header_main_title);

		button_lookup = (Button) findViewById(R.id.btn_lookup);
		button_lookup.setOnClickListener(Button_Click_Listener);

		button_getContents = (Button) findViewById(R.id.btn_getContents);
		button_getContents.setOnClickListener(Button_Click_Listener);

		button_move = (Button) findViewById(R.id.btn_move);
		button_move.setOnClickListener(Button_Click_Listener);

		button_bulkMove = (Button) findViewById(R.id.btn_bulkMove);
		button_bulkMove.setOnClickListener(Button_Click_Listener);

	}

	OnClickListener Button_Click_Listener = new OnClickListener(){
		public void onClick(View v){
			int id_of_view = v.getId();

			//Login button
			if(id_of_view == button_lookup.getId()){
				Intent intent = new Intent(MainMenuActivity.this, ScannerActivity.class);
				intent.putExtra("NEXT_ACTIVITY", "lookup");

				startActivityForResult(intent,0);

			//GetContents button
			}else if(id_of_view == button_getContents.getId()){
				Intent intent = new Intent(MainMenuActivity.this, GetContentsActivity.class);
				startActivity(intent);
				
			}else if(id_of_view == button_move.getId()){
				Intent intent = new Intent(MainMenuActivity.this, MoveActivity.class);
				startActivity(intent);

			}else if(id_of_view == button_bulkMove.getId()){
				Toast.makeText(MainMenuActivity.this, "bulk move button pressed", Toast.LENGTH_LONG).show();
			}
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Make sure the request was successful
		if (resultCode == RESULT_OK) {
			Bundle resultBundle = data.getExtras();

			String nextActivity = resultBundle.getString("NEXT_ACTIVITY");

			if(nextActivity.equalsIgnoreCase("lookup")){

				String decodedData = resultBundle.getString("DATA_RESULT");
				Toast.makeText(MainMenuActivity.this, "Data decoded : "+decodedData, Toast.LENGTH_LONG).show();
				//TODO check if the decodedData is null if so then throw an error

				System.out.print("Success data is - "+decodedData);

				//send request to server to get result
				String acronym;
				long id;
				String[] barcodeText = decodedData.split("-");
				try {
					Integer.parseInt(barcodeText[0]);
					// if the characters before the hyphen are numbers, the entity type is 
					// identified by the first two digits after the hyphen
					acronym = barcodeText[1].substring(0, 2);
					id = Long.parseLong(barcodeText[1].substring(2));
				} catch (NumberFormatException e){
					// if the characters before the hyphen are letters, the entity type is 
					// identified by these characters
					acronym = barcodeText[0];
					id = Long.parseLong(barcodeText[1]);
				}
				
				EntityServiceI service = getService(acronym);
				if (service != null){
					Serializable entity = service.getById(id);

					// prepare data to send to LookUp Activity
					Bundle dataBundle = new Bundle();
					dataBundle.putSerializable("ENTITY", entity);
					dataBundle.putString("TYPE", acronym);

					Intent intent = new Intent(MainMenuActivity.this, LookupActivity.class);
					intent.putExtras(dataBundle);
					startActivity(intent);
				}

				else {
					// TODO display error message to user
				}

			}
			else if(nextActivity.equalsIgnoreCase("getcontents")){

				String decodedData = resultBundle.getString("DATA_RESULT");
				Toast.makeText(MainMenuActivity.this, "Data decoded : "+ decodedData, Toast.LENGTH_LONG).show();
				//TODO check if the decodedData is null if so then throw an error

				System.out.print("Success data is - "+decodedData);
				
				
				// prepare data to send to GetContents Activity
				Bundle dataBundle = new Bundle();
				dataBundle.putString("DECODEDDATA", decodedData);			

				Intent intent = new Intent(MainMenuActivity.this, GetContentsActivity.class);
				intent.putExtras(dataBundle);

				startActivity(intent);
				
				}else if(nextActivity.equalsIgnoreCase("move_step_1")){

				}else if(nextActivity.equalsIgnoreCase("move_step_2")){

				}else if(nextActivity.equalsIgnoreCase("bulkMove")){

				}else{

				}

			}else{
				
				System.out.print("Failure");
			}

		}

		@Override
		public void onBackPressed() {

			//Exit to home screen and keep activity alive for re-entry to this point in the app
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);

		}

		private EntityServiceI getService(String acronym){
			EntityServiceI service = null;

			SharedPreferences preferences = getSharedPreferences(getString(R.string.config_file), MODE_PRIVATE);
			String serverURL = preferences.getString("SERVER_URL", "http://localhost:4567/v1");

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
//			} else if (acronym.equalsIgnoreCase("PRD") || acronym.equalsIgnoreCase("06")){
//				service = new ProductService(serverURL);
//			} else if (acronym.equalsIgnoreCase("ACC") || acronym.equalsIgnoreCase("00")){
//				service = new AccountService(serverURL);
			}

			return service;
		}

	}
