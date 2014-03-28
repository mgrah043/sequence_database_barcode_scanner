package ca.gc.aafc.seqdb_barcode_scanner;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableRow.LayoutParams;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ToggleButton;
import ca.gc.aafc.seqdb_barcode_scanner.entities.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

/**
* This allows for creating a getContent Fragment.
* 
* @author Matt Graham
* @author Vincent Maliko
* 
* @version 1.0
*/
public class GetContentFragment extends Fragment {
    // The container with a list of elements.
    
    private TableLayout tableLayoutHeaderLeft;
    private TableLayout tableLayout;
    private TableLayout tableLayoutHeaderRight;
    private ListView listView;
    private ToggleButton gridToggle;
    private TextView containerId;
    private TextView containerSize;
    private Button[][] buttonArray;
    
    private ArrayList<String> locationDetails;
    private ArrayAdapter<String> adapter;
    
    /*
     * this should be from the container info
     * */
    private int contentRow = 0;
    private int contentColumn = 0;
    
    final private int WIDTH_OF_TABLE_ELEMENT = 250;
	final private int HEIGHT_OF_TABLE_ELEMENT = 250;
    
    // The listener when a button is selected
    OnContentSelectedListener contentSelectedListener = null;

    /**
     * Represents a listener that will be notified of content selections.
     */
    public interface OnContentSelectedListener {
        /**
         * Called when a given content is selected.
         * @param index the index of the selected content.
         */
        public void onContentSelected(String id,String row,int column, boolean state);
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
    	
    	this.containerId = (TextView) view.findViewById(R.id.tv_container_id);
    	this.containerSize = (TextView) view.findViewById(R.id.tv_container_size);
    	this.tableLayout =  (TableLayout) view.findViewById(R.id.tableLayout);
    	this.tableLayoutHeaderLeft = (TableLayout) view.findViewById(R.id.tableLayoutLeftColumnHeader);
    	this.tableLayoutHeaderRight = (TableLayout) view.findViewById(R.id.tableLayoutRightColumnHeader);
    	listView = (ListView) view.findViewById(R.id.listView);
    	gridToggle = (ToggleButton) view.findViewById(R.id.gridToggle);
    	
    	// get data from bundle (that will be the container entity or something else ??)
        
    	return view;
   }
    
	/**
     * Handles a click on a button.
     *
     * This causes the configured listener to be notified that a content was selected
     */
   /* @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != contentSelectedListener) {
        	contentSelectedListener.onContentSelected(position);
        }
    }
*/
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
	
	public int getIntegerFromChar(char character){
		return ((int)character)-65;
	}
	
	
	/*
	 * This function should be called after the system has fetched the content of the container from the server
	 * perhaps a parameter to loadContent
	 * */
	public void loadContent(Container container){
		/*
    	 * 
    	 * Creating the left column header
    	 * 
    	 * */
		this.contentRow = container.getContainerType().getNumberOfRows();
		this.contentColumn = container.getContainerType().getNumberOfColumns();
		
		this.containerId.setText("Container "+container.getId());
		this.containerSize.setText("Size: "+this.contentRow * this.contentColumn+" locations");
		
    	TableLayout leftHeader = this.tableLayoutHeaderLeft;
    	boolean firstElementLeft = true;
    	for(int r = 0; r  < this.contentRow; r++){
			TableRow tableLeftHeader = new TableRow(leftHeader.getContext());

			TextView currentTextView = new TextView(leftHeader.getContext());
			LayoutParams textViewParams = null;

			
				//store row number in textview
				currentTextView.setText("" + getCharacterOfNumber(r));

				//Set textview Layout parameters
				textViewParams = new LayoutParams(
						LayoutParams.WRAP_CONTENT,      
						HEIGHT_OF_TABLE_ELEMENT
						);
			
			//Set textview attributes
			currentTextView.setTextAppearance(leftHeader.getContext(), R.style.TableHeaderFont);
			currentTextView.setGravity(Gravity.CENTER);

			//adds extra margin for first element
			if(firstElementLeft){
				textViewParams.setMargins(2,75, 2, 2);
				firstElementLeft = false;
			}
			else{
				textViewParams.setMargins(2, 2, 2, 2);
			}

			//add textview to tablerow
			tableLeftHeader.addView(currentTextView, textViewParams);

			//add tablerow to tablelayout
			leftHeader.addView(tableLeftHeader);
		}
    	
    	/*
		 * creating get contents table
		 */
		TableLayout table = this.tableLayout;
		
		this.buttonArray = new Button[this.contentRow][this.contentColumn];

		//create top table header (alphabetical)
		TableRow tableTopHeader = new TableRow(table.getContext());
		for(int c = 0; c  < this.contentColumn; c++){
			TextView currentTextView = new TextView(table.getContext());

			//store column number in textview
			currentTextView.setText(""+(c+1));

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
		ArrayList<Location> containerLocations = container.getlocationList();
		HashMap<String,Location> rowCol = new HashMap<String,Location>();
		
		for (Location l : containerLocations) rowCol.put(l.getWellRow(),l);
		
		for(int row = 0; row  < this.contentRow; row++){
			TableRow currentRow = new TableRow(table.getContext());

			currentRow.setBaselineAligned(false);

			for(int col = 0; col < this.contentColumn; col++){
				Button currentButton = new Button(table.getContext());
			
				String[] buttonTag = null;
				String output = "";
				String rowChar= getCharacterOfNumber(row);
				
				if(rowCol.containsKey(rowChar) && rowCol.get(rowChar).getWellColumn() == col){
					buttonTag = new String[]{rowChar,col+"","true","MSP-"+rowCol.get(rowChar).getMixedSpecimen().getId()};
					currentButton.setBackgroundResource(R.drawable.ui_button_blue);
					output = rowCol.get(rowChar).getMixedSpecimen().getFungiIsolated();
				}else{
					buttonTag = new String[]{"","","false",""};
					currentButton.setBackgroundResource(R.drawable.ui_button_red);
				}
				
				currentButton.setText(output);
				currentButton.setTag(buttonTag);
				

				//TODO Change these to work on all devices...get size of screen then apply calculations?
				//set parameters of button
				LayoutParams params = new LayoutParams(
						WIDTH_OF_TABLE_ELEMENT,      
						HEIGHT_OF_TABLE_ELEMENT
						);

				params.setMargins(2, 2, 2, 2);

				

				//store button in array
				this.buttonArray[row][col] = currentButton;
				
				currentButton.setOnClickListener(Button_Click_Listener);
				
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
			currentTextView.setText("" +(c+1));

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
		
		boolean firstElementRight = true;
		for(int r = 0; r  < this.contentRow; r++){
			TableRow tableRightHeader = new TableRow(rightHeader.getContext());
			TextView currentTextView = new TextView(rightHeader.getContext());
			LayoutParams textViewParams = null;

			//make bottom left and right elements blank 

				//store row number in textview
				currentTextView.setText("" + getCharacterOfNumber(r));

				//Set textview Layout parameters
				textViewParams = new LayoutParams(
						LayoutParams.WRAP_CONTENT,      
						HEIGHT_OF_TABLE_ELEMENT
						);


			//Set textview attributes
			currentTextView.setTextAppearance(rightHeader.getContext(), R.style.TableHeaderFont);
			currentTextView.setGravity(Gravity.CENTER);

			//adds extra margin for first element
			if(firstElementRight){
				textViewParams.setMargins(2,75, 2, 2);
				firstElementRight = false;
			}
			else{
				textViewParams.setMargins(2, 2, 2, 2);
			}

			//add textview to tablerow
			tableRightHeader.addView(currentTextView, textViewParams);

			//add tablerow to tablelayout
			rightHeader.addView(tableRightHeader);
		}
		
		// generate the list view
		ArrayList<Location> locations = container.getlocationList();
		locationDetails = new ArrayList<String>();
		
		for (int i = 0; i < locations.size(); i++){
			Location location = locations.get(i);
			//TODO check which entity is populated
			locationDetails.add((i + 1) + ". " + location.getMixedSpecimen().getFungiIsolated() + ": " 
					+ location.getWellRow() + location.getWellColumn());
		}
		adapter=new ArrayAdapter<String>(this.getActivity(), R.layout.list_black_text, R.id.list_content, locationDetails);
		listView.setAdapter(adapter);
		listView.setVisibility(View.GONE);
		
		OnClickListener gridToggleClickListener = new OnClickListener(){
			public void onClick(View v){
				if (gridToggle.isChecked()) {
					listView.setVisibility(View.GONE);
					tableLayout.setVisibility(View.VISIBLE);
					tableLayoutHeaderLeft.setVisibility(View.VISIBLE);
					tableLayoutHeaderRight.setVisibility(View.VISIBLE);
		        }
				else {
					listView.setVisibility(View.VISIBLE);
					tableLayout.setVisibility(View.GONE);
					tableLayoutHeaderLeft.setVisibility(View.GONE);
					tableLayoutHeaderRight.setVisibility(View.GONE);
				}
			}
		};
		gridToggle.setOnClickListener(gridToggleClickListener);
	}
	
	OnClickListener Button_Click_Listener = new OnClickListener(){
		public void onClick(View v){
			if (contentSelectedListener != null) {
				String[] tag = (String[])v.getTag();
				
				String row = (String)tag[0];
				int col = 0;
				boolean state = (((String)tag[2]).equalsIgnoreCase("true"))? true : false;
				String id = (String)tag[3];
				try{
					col = Integer.parseInt((String)tag[1]);
				}catch(Exception e){
					// either the col or state threw an error
					System.out.println(e.getMessage());
				}
				
					
				contentSelectedListener.onContentSelected(id,row,col,state); // pass row then column
				
	        }
		}
	};

	/*@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		System.out.println("onclick listener triggered");
		if (null != contentSelectedListener) {
        	contentSelectedListener.onContentSelected(0);
        }
	}*/
	

}
