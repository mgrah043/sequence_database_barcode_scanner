package ca.gc.aafc.seqdb_barcode_scanner.utils;


/**
* Helper function used to parse scanned data
* 
* @author Vincent Maliko
* 
* @version 1.0
*/
public class DataParser {
	private String acronym;
	private long id;
	
	public DataParser(){
		this.acronym = "";
		this.id = 0;
	}
	
	public void parse(String data) throws Exception{
		String[] barcodeText = data.split("-");
		
		try {
			Integer.parseInt(barcodeText[0]);
			// if the characters before the hyphen are numbers, the entity type is 
			// identified by the first two digits after the hyphen
				this.setAcronym(barcodeText[1].substring(0, 2));
				this.setId(Long.parseLong(barcodeText[1].substring(2)));
				
		} catch (NumberFormatException e){
			// if the characters before the hyphen are letters, the entity type is 
			// identified by these characters
			this.setAcronym(barcodeText[0]);
			this.setId(Long.parseLong(barcodeText[1]));
		}
	}
	
	public String getAcronym(){
		return this.acronym;
	}
	
	public void setAcronym(String acronym){
		this.acronym = acronym;
	}
	
	public long getId(){
		return this.id;
	}
	
	public void setId(long id){
		this.id = id;
	}
	
	public void reset(){
		this.acronym = "";
		this.id=0;
		
	}
}
