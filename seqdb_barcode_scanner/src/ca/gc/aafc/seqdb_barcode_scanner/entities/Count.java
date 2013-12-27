package ca.gc.aafc.seqdb_barcode_scanner.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author NazirLKC
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Count {

	@JsonProperty("type")
	private String type;
	@JsonProperty("count")
	private long count;
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public long getCount() {
		return count;
	}
	
	public void setCount(long count) {
		this.count = count;
	}
	
}
