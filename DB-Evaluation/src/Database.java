import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;
import de.tu_bs.cs.isf.cbc.parser.annotations.MethodReceiver;
import de.tu_bs.cs.isf.cbc.parser.annotations.SecurityLevel;

public class Database {

	@SecurityLevel("low") String name;
	@SecurityLevel("low") List<Row> rows;
	
	public Database(@SecurityLevel("low") String name) {
		this.name = name;
		rows = new ArrayList<Row>();
	}
	
	@MethodReceiver(SL = "low", MDF = MDF.IMMUTABLE)
	public @SecurityLevel("low") String getName() {
		return name;
	}

	@MethodReceiver(SL = "low", MDF = MDF.IMMUTABLE)
	public void setName(@SecurityLevel("low") String name) {
		this.name = name;
	}
	
	@MethodReceiver(SL = "low", MDF = MDF.IMMUTABLE)
	public @SecurityLevel("low") Row addRow() {
		@SecurityLevel("low") Row row = new Row();
		rows.add(row);
		return row;
	}
	
	@MethodReceiver(SL = "low", MDF = MDF.IMMUTABLE)
	public void removeRow(@SecurityLevel("low") int index) {
		rows.remove(index);
	}
		
	@MethodReceiver(SL = "low", MDF = MDF.IMMUTABLE)
	public void setEntry(@SecurityLevel("low") String key, @SecurityLevel("low") String value, @SecurityLevel("low") int rowNumber) {
		Row row;
		if (rowNumber < 0 || rowNumber >= rows.size()) {
			row = addRow();
		} else {
			row = rows.get(rowNumber);
		}
		
		row.setEntry(key, value);
	}
	
	@MethodReceiver(SL = "low", MDF = MDF.IMMUTABLE)
	public @SecurityLevel("low") String getEntry(@SecurityLevel("low") String key, @SecurityLevel("low") int rowNumber) {
		Row row;
		if (rowNumber < 0 || rowNumber >= rows.size()) {
			return null;
		} else {
			row = rows.get(rowNumber);
		}
		
		return row.getEntry(key);
	}
	
	@MethodReceiver(SL = "low", MDF = MDF.IMMUTABLE)
	public List<String> getAllEntries(@SecurityLevel("low") String key) {
		@SecurityLevel("low") int i = 0;
		@SecurityLevel("low") List<String> result = new ArrayList<String>();
		while (i < rows.size()) {
			@SecurityLevel("low") String nextValue = rows.get(i).getEntry(key);
			result.add(nextValue);
		}
		return result;
	}
}
