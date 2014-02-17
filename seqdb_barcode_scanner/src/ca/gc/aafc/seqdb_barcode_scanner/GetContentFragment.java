package ca.gc.aafc.seqdb_barcode_scanner;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TableRow.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import ca.gc.aafc.seqdb_barcode_scanner.entities.*;

import java.util.ArrayList;
import java.util.List;

/**
* This allows for creating a getContent Fragment.
* 
* @author Matt Graham
* @author Vincent Maliko
* 
* @version 1.0
*/
public class GetContentFragment extends Fragment implements OnItemClickListener{
    // The container with a list of elements.
    
	List<SpecimenReplicate> contentList = new ArrayList<SpecimenReplicate>();
    private TableLayout tableLayoutHeaderLeft;
    private TableLayout tableLayout;
    private TableLayout tableLayoutHeaderRight;
    
    private int selectedContentRow = 0;
    private int selectedContentColumn = 0;
    
    /*
     * this should be from the container info
     * */
    private int contentRow = 9;
    private int contentColumn = 9;
    
    final private int WIDTH_OF_TABLE_ELEMENT = 250;
	final private int HEIGHT_OF_TABLE_ELEMENT = 250;
    
    // The listener we are to notify when a headline is selected
    OnContentSelectedListener contentSelectedListener = null;

    /**
     * Represents a listener that will be notified of content selections.
     */
    public interface OnContentSelectedListener {
        /**
         * Called when a given content is selected.
         * @param index the index of the selected content.
         */
        public void onContentSelected(int index);
		
    }

    /**
     * Default constructor required by framework.
     */
    public GetContentFragment() {
        super();
    }
    
    /**
     * This method links the activity that uses this fragment with 
     * the callback method (which should be implemented in the activity) 
     *
     * @param  activity  the activity 
     */
    @Override
	public void onAttach(Activity activity) {
	        super.onAttach(activity);
	        
	        try {
	        	contentSelectedListener = (OnContentSelectedListener) activity;
	        	
	        } catch (ClassCastException e) {
	            throw new ClassCastException(activity.toString()
	                    + " must implement OnContentSelectedListener");
	        }
	}
    
   /**
    * Creates the tablelayout
    *
    * @param  LayoutInflater 
    * @param  ViewGroup
    * @param  Bundle
    * @return View
    */
    @Override
    public View onCreateView(LayoutInflater inflater, 
          ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    	View view =  inflater.inflate(R.layout.fragment_get_contents,
                  container, false);
    	
    	this.tableLayout =  (TableLayout) view.findViewById(R.id.tableLayout);
    	this.tableLayoutHeaderLeft = (TableLayout) view.findViewById(R.id.tableLayoutLeftColumnHeader);
    	this.tableLayoutHeaderRight = (TableLayout) view.findViewById(R.id.tableLayoutRightColumnHeader);
    	
    	// get data from bundle (that will be the container entity or something else ??)
        
    	return view;
   }
    
	/**
     * Handles a click on a button.
     *
     * This causes the configured listener to be notified that a content was selected
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != contentSelectedListener) {
        	contentSelectedListener.onContentSelected(position);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
       
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
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
	
	
	/*
	 * This function should be called after the system has fetched the content of the container from the server
	 * perhaps a parameter to loadContent
	 * */
	public void loadContent(){
		/*
    	 * 
    	 * Creating the left column header
    	 * 
    	 * */
    	TableLayout leftHeader = this.tableLayoutHeaderLeft;
    	for(int r = 0; r  <= this.contentRow+1; r++){
			TableRow tableLeftHeader = new TableRow(leftHeader.getContext());

			TextView currentTextView = new TextView(leftHeader.getContext());
			LayoutParams textViewParams = null;

			//make top left and right elements blank 
			if(r==0 || r==this.contentRow+1){
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
		TableLayout table = this.tableLayout;
		Button[][] buttonArray = new Button[this.contentRow][this.contentColumn];

		//create top table header (alphabetical)
		TableRow tableTopHeader = new TableRow(table.getContext());
		for(int c = 0; c  < this.contentColumn; c++){
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
		for(int row = 0; row  < this.contentRow; row++){
			TableRow currentRow = new TableRow(table.getContext());

			for(int col = 0; col < this.contentColumn; col++){
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
		for(int c = 0; c  < this.contentColumn; c++){
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
		TableLayout rightHeader = this.tableLayoutHeaderRight;

		for(int r = 0; r  <= this.contentRow+1; r++){
			TableRow tableRightHeader = new TableRow(rightHeader.getContext());
			TextView currentTextView = new TextView(rightHeader.getContext());
			LayoutParams textViewParams = null;

			//make bottom left and right elements blank 
			if(r==0 || r==this.contentRow+1){
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
	

}
