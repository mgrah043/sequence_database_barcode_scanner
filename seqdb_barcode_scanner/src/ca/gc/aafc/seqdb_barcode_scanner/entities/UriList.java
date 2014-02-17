/**
 * 
 */
package ca.gc.aafc.seqdb_barcode_scanner.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author NazirLKC
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
	@JsonProperty("count")
	private long count;
	@JsonProperty("previousPageUrl")
	private String previousPageUrl;
	@JsonProperty("nextPageUrl")
	private String nextPageUrl;
	@JsonProperty("urls")
	private UrlPath[] uris;
	
	// nested class UrlPath is used to map received JSON objects
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class UrlPath {
		@JsonProperty("urlPath")
		private String urlPath;

		public String getUrlPath() {
			return urlPath;
		}

		public void setUrlPath(String urlPath) {
			this.urlPath = urlPath;
		}
	}
	
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
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
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
	public UrlPath[] getUris() {
		return uris;
	}
	public void setUris(UrlPath[] uris) {
		this.uris = uris;
	}
}
