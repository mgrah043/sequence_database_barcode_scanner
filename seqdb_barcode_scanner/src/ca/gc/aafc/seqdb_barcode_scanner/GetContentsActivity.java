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
                        

        //creating table
        TableLayout table = (TableLayout) findViewById(R.id.tableLayout);
        Button[][] buttonArray = new Button[numRows][numCols];
        
        
        //create top table header (alphabetical)
    	TableRow tableTopHeader = new TableRow(table.getContext());
        for(int c = 0; c  <= numCols+1; c++){
        	TextView currentTextView = new TextView(table.getContext());

        	//make top left and right elements blank 
        	if(c==0 || c==numCols+1){
            	currentTextView.setText(" ");
        	}
        	else{
        		currentTextView.setText("" + getCharacterOfNumber(c));
        	}
        	
        	//Set textview attributes
        	currentTextView.setTextAppearance(table.getContext(), R.style.TableHeaderFont);
        	currentTextView.setGravity(Gravity.CENTER);
        	
        	//Set textview Layout parameters
        	LayoutParams textViewParams = new LayoutParams(
        			WIDTH_OF_TABLE_ELEMENT,      
    		        LayoutParams.WRAP_CONTENT
    		        
    		);
    	
    		textViewParams.setMargins(2, 2, 2, 2);
        	
        	tableTopHeader.addView(currentTextView, textViewParams);
        }
        table.addView(tableTopHeader);

        int indexButtonRow = -1;
        int indexButtonCol = -1;
        for(int row = 0; row  < numRows; row++){
        	TableRow currentRow = new TableRow(table.getContext());

        	indexButtonRow ++;
        	
        	indexButtonCol = -1;
        	
        	for(int col = 0; col <= numCols+1; col++){
        		Button currentButton = new Button(table.getContext());
        		
        		if(col == 0 || col == numCols+1){
        			//Add header item
        			TextView currentTextView = new TextView(table.getContext());

        			
                	int rowNumber = row+1;
        			currentTextView.setText("" + rowNumber);
          
                	//Set textview attributes
                	currentTextView.setTextAppearance(table.getContext(), R.style.TableHeaderFont);
                	currentTextView.setGravity(Gravity.CENTER);
                	
                	//Set textview Layout parameters
                	LayoutParams textViewParams = new LayoutParams(
                			0,      
            		        LayoutParams.WRAP_CONTENT,
            		        1f
            		);
            	
            		textViewParams.setMargins(2, 2, 2, 2);
                	
                	currentRow.addView(currentTextView, textViewParams);    
                	
                	//currentRow.addView(currentTextView); 
              }
        		
        		else{
	        			
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
	        		
	        		
	        		/*
	        		 * initialize button....check to see if there is an element at this position
	        		 */
	        		
	        		
	        		if (col == 2 && row == 2){
	        			currentButton.setBackgroundColor(Color.RED);
	            		currentButton.setText("");
	        		}
	        		
	        		currentButton.setOnClickListener(Button_Click_Listener);
	        		
	        		indexButtonCol++;

	        		//store button in array
	        		buttonArray[indexButtonRow][indexButtonCol] = currentButton;
	        		
	        		
	        		//add to table row
	        		currentRow.addView(currentButton, params);	
        		}
        	}
        	
        	//add row to table
        	table.addView(currentRow);  	
        	
        }
        
        //create top table header (alphabetical)
    	TableRow tableBottomHeader = new TableRow(table.getContext());
        for(int c = 0; c  <= numCols+1; c++){
        	TextView currentTextView = new TextView(table.getContext());

        	//make top left element blank
        	if(c==0 || c==numCols+1){
            	currentTextView.setText(" ");
        	}
        	else{
        		currentTextView.setText("" + getCharacterOfNumber(c));
        	}
        	
        	//Set textview attributes
        	currentTextView.setTextAppearance(table.getContext(), R.style.TableHeaderFont);
        	currentTextView.setGravity(Gravity.CENTER);
        	
        	//Set textview Layout parameters
        	LayoutParams textViewParams = new LayoutParams(
        			WIDTH_OF_TABLE_ELEMENT,      
    		        LayoutParams.WRAP_CONTENT
    		);
    	
    		textViewParams.setMargins(2, 2, 2, 2);
        	
    		tableBottomHeader.addView(currentTextView, textViewParams);
        }
        table.addView(tableBottomHeader);
   }



	OnClickListener Button_Click_Listener = new OnClickListener(){
		public void onClick(View v){
			int id_of_view = v.getId();

		}
	};

	@Override
	public void onBackPressed() {
	
		finish();
		
	}
	
	public String getCharacterOfNumber(int number){
		//convert to ascii format (capital characters - A is 65)
		int asciiNumber = number + 64;
		
		return  String.valueOf(Character.toChars(asciiNumber));
		
		 
	}
	
		
}
