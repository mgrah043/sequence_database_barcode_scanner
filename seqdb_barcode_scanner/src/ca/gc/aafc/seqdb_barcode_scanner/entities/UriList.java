/**
 * 
 */
package ca.gc.aafc.seqdb_barcode_scanner.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Nazir
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class UriList {

	@JsonProperty("total")
	private long total;
	@JsonProperty("limit")
	private int limit;
	@JsonProperty("offset")
	private int offset;
	@JsonProperty("type")
	private String type;
	@JsonProperty("previousPageUrl")
	private String previousPageUrl;
	@JsonProperty("nextPageUrl")
	private String nextPageUrl;
	@JsonProperty("uris")
	private String[] uris;
	
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPreviousPageUrl() {
		return previousPageUrl;
	}
	public void setPreviousPageUrl(String previousPageUrl) {
		this.previousPageUrl = previousPageUrl;
	}
	public String getNextPageUrl() {
		return nextPageUrl;
	}
	public void setNextPageUrl(String nextPageUrl) {
		this.nextPageUrl = nextPageUrl;
	}
	public String[] getUris() {
		return uris;
	}
	public void setUris(String[] uris) {
		this.uris = uris;
	}
}
