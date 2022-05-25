import java.util.HashMap;
import java.util.Map;

import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;
import de.tu_bs.cs.isf.cbc.parser.annotations.MethodReceiver;
import de.tu_bs.cs.isf.cbc.parser.annotations.SecurityLevel;

public class Row {

	@SecurityLevel("low") Map<String, String> row;
	
	public Row() {
		row = new HashMap<String, String>();
	}
	
	@MethodReceiver(SL = "low", MDF = MDF.IMMUTABLE)
	public @SecurityLevel("low") String getEntry(@SecurityLevel("low") String key) {
		return row.get(key);
	}
	
	@MethodReceiver(SL = "low", MDF = MDF.IMMUTABLE)
	public void setEntry(@SecurityLevel("low") String key, @SecurityLevel("low") String value) {
		row.put(key, value);
	}
}
