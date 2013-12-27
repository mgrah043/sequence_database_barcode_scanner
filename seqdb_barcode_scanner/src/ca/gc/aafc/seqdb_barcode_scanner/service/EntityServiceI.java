/**
 * 
 */
package ca.gc.aafc.seqdb_barcode_scanner.service;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author NazirLKC
 *
 */
public interface EntityServiceI {

	public long getCount();
	public ArrayList<?> getAll();
	public Serializable getById(long id);
}
