/* ------------------------------------------------------------------
 * StringColumn.java
 * 
 * Created 2008-10-30 by Niklas Elmqvist.
 * ------------------------------------------------------------------
 */
import java.util.ArrayList;
import java.util.Collection;

public class StringColumn extends BasicColumn {

	private ArrayList<String> rows = new ArrayList<String>();

	public StringColumn(String name) {
		super(name, Column.Type.String);
	}

	public Object getValueAt(int row) {
		return rows.get(row);
	}

	public int getIntValue(int row) {
		return Integer.parseInt(rows.get(row));
	}

	public double getRealValueAt(int row) {
		return Double.parseDouble(rows.get(row));
	}

	public String getStringValueAt(int row) {
		return rows.get(row);
	}
	
	public void addValue(Object value) {
		if (value instanceof String) {
			rows.add((String) value);
		}
		else { 
			rows.add(new String(""));
		}
	}

	public int getRowCount() {
		return rows.size();
	}

	public void reorder(Collection<Integer> rowOrder) {
		ArrayList<String> newRows = new ArrayList<String>();
		reorder(rowOrder, rows, newRows);
		rows = newRows;
	}
	
	public void ensureCapacity(int minRows) {
		while (rows.size() < minRows) { 
			rows.add(null);
		}
	}

	public void setValueAt(int row, Object value) {
		ensureCapacity(row + 1);
		if (value instanceof String) {
			rows.set(row, (String) value);
		}
		else { 
			rows.set(row, new String(""));
		}
	}

	public void clear() {
		rows.clear();
	}
}
