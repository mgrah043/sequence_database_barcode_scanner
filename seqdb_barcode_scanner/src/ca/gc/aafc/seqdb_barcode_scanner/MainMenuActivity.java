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
import ca.gc.aafc.seqdb_barcode_scanner.utils.DataParser;
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
				Intent intent = new Intent(MainMenuActivity.this, LookupActivity.class);
				startActivity(intent);

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
		public void onBackPressed() {

			//Exit to home screen and keep activity alive for re-entry to this point in the app
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);

		}

	}
