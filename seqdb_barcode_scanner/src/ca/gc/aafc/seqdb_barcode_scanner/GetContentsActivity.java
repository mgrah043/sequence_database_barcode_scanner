package ca.gc.aafc.seqdb_barcode_scanner;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;


public class GetContentsActivity extends Activity{
	//Variable declaration
	TextView header_title;
	ImageButton button_mainMenu;

	private int numRows;
	private int numCols;

	final private int WIDTH_OF_TABLE_ELEMENT = 250;
	final private int HEIGHT_OF_TABLE_ELEMENT = 250;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//get data from bundle
		//TODO initialize number of rows and columns from bundle.........hardcode for now
		numRows = 9;
		numCols = 9;


		setContentView(R.layout.activity_get_contents_template);

		/*
		 * creating left column header
		 */
		TableLayout leftHeader = (TableLayout) findViewById(R.id.tableLayoutLeftColumnHeader);

		for(int r = 0; r  <= numRows+1; r++){
			TableRow tableLeftHeader = new TableRow(leftHeader.getContext());

			TextView currentTextView = new TextView(leftHeader.getContext());
			LayoutParams textViewParams = null;

			//make top left and right elements blank 
			if(r==0 || r==numRows+1){
				currentTextView.setText(" ");

				//Set textview Layout parameters
				textViewParams = new LayoutParams(
						LayoutParams.WRAP_CONTENT,      
						LayoutParams.WRAP_CONTENT
						);
			}
			else{
				//store row number in textview
				currentTextView.setText("" + r);

				//Set textview Layout parameters
				textViewParams = new LayoutParams(
						LayoutParams.WRAP_CONTENT,      
						HEIGHT_OF_TABLE_ELEMENT
						);
			}

			//Set textview attributes
			currentTextView.setTextAppearance(leftHeader.getContext(), R.style.TableHeaderFont);
			currentTextView.setGravity(Gravity.CENTER);

			textViewParams.setMargins(2, 2, 2, 2);

			//add textview to tablerow
			tableLeftHeader.addView(currentTextView, textViewParams);

			//add tablerow to tablelayout
			leftHeader.addView(tableLeftHeader);
		}

		/*
		 * creating get contents table
		 */
		TableLayout table = (TableLayout) findViewById(R.id.tableLayout);
		Button[][] buttonArray = new Button[numRows][numCols];

		//create top table header (alphabetical)
		TableRow tableTopHeader = new TableRow(table.getContext());
		for(int c = 0; c  < numCols; c++){
			TextView currentTextView = new TextView(table.getContext());

			//store column number in textview
			currentTextView.setText("" + getCharacterOfNumber(c));

			//Set textview attributes
			currentTextView.setTextAppearance(table.getContext(), R.style.TableHeaderFont);
			currentTextView.setGravity(Gravity.CENTER);

			//Set textview Layout parameters
			LayoutParams textViewParams = new LayoutParams(
					WIDTH_OF_TABLE_ELEMENT,      
					LayoutParams.WRAP_CONTENT
					);

			textViewParams.setMargins(2, 2, 2, 2);

			//add textview to tablerow
			tableTopHeader.addView(currentTextView, textViewParams);
		}
		//add tablerow to tablelayout
		table.addView(tableTopHeader);

		/*
		 * create main table
		 */
		for(int row = 0; row  < numRows; row++){
			TableRow currentRow = new TableRow(table.getContext());

			for(int col = 0; col < numCols; col++){
				Button currentButton = new Button(table.getContext());

				//Set button attributes
				currentButton.setBackgroundResource(R.drawable.ui_button_blue);
				currentButton.setText("SAM-111");


				//TODO Change these to work on all devices...get size of screen then apply calculations?
				//set parameters of button
				LayoutParams params = new LayoutParams(
						WIDTH_OF_TABLE_ELEMENT,      
						HEIGHT_OF_TABLE_ELEMENT
						);

				params.setMargins(2, 2, 2, 2);

				//initialize button....check to see if there is a data or empty element at this position
				if (col == 2 && row == 2){
					currentButton.setBackgroundColor(Color.RED);
					currentButton.setText("");
				}

				//set click listener for button
				currentButton.setOnClickListener(Button_Click_Listener);

				//store button in array
				buttonArray[row][col] = currentButton;

				//add button to tablerow
				currentRow.addView(currentButton, params);	
			}

			//add tablerow to tablelayout
			table.addView(currentRow);  	

		}

		//create bottom table header (alphabetical)
		TableRow tableBottomHeader = new TableRow(table.getContext());
		for(int c = 0; c  < numCols; c++){
			TextView currentTextView = new TextView(table.getContext());

			//store column number in textview
			currentTextView.setText("" + getCharacterOfNumber(c));

			//Set textview attributes
			currentTextView.setTextAppearance(table.getContext(), R.style.TableHeaderFont);
			currentTextView.setGravity(Gravity.CENTER);

			//Set textview Layout parameters
			LayoutParams textViewParams = new LayoutParams(
					WIDTH_OF_TABLE_ELEMENT,      
					LayoutParams.WRAP_CONTENT
					);

			textViewParams.setMargins(2, 2, 2, 2);

			//add textviews to tablerow
			tableBottomHeader.addView(currentTextView, textViewParams);
		}
		//add tablerow to tablelayout
		table.addView(tableBottomHeader);

		/*
		 * creating right column header
		 */
		TableLayout rightHeader = (TableLayout) findViewById(R.id.tableLayoutRightColumnHeader);

		for(int r = 0; r  <= numRows+1; r++){
			TableRow tableRightHeader = new TableRow(rightHeader.getContext());
			TextView currentTextView = new TextView(rightHeader.getContext());
			LayoutParams textViewParams = null;

			//make bottom left and right elements blank 
			if(r==0 || r==numRows+1){
				currentTextView.setText(" ");

				//Set textview Layout parameters
				textViewParams = new LayoutParams(
						LayoutParams.WRAP_CONTENT,      
						LayoutParams.WRAP_CONTENT
						);
			}
			else{
				//store row number in textview
				currentTextView.setText("" + r);

				//Set textview Layout parameters
				textViewParams = new LayoutParams(
						LayoutParams.WRAP_CONTENT,      
						HEIGHT_OF_TABLE_ELEMENT
						);
			}

			//Set textview attributes
			currentTextView.setTextAppearance(rightHeader.getContext(), R.style.TableHeaderFont);
			currentTextView.setGravity(Gravity.CENTER);

			textViewParams.setMargins(2, 2, 2, 2);

			//add textview to tablerow
			tableRightHeader.addView(currentTextView, textViewParams);

			//add tablerow to tablelayout
			rightHeader.addView(tableRightHeader);
		}
	}

	/**
	 * General click listener for buttons
	 */
	OnClickListener Button_Click_Listener = new OnClickListener(){
		public void onClick(View v){
			int id_of_view = v.getId();

		}
	};

	@Override
	public void onBackPressed() {

		finish();

	}

	/**
	 * Takes an integer as a parameter and convers it to a capital letter
	 * Integer Range: 0 - 26 will produce String A - Z (Capitalized)
	 */
	public String getCharacterOfNumber(int number){
		//convert to ascii format (capital characters - A is 65)
		int asciiNumber = number + 65;

		return  String.valueOf(Character.toChars(asciiNumber));


	}


}
