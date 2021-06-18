/* ------------------------------------------------------------------
 * Column.java
 * 
 * Created 2008-10-30 by Niklas Elmqvist.
 * ------------------------------------------------------------------
 */
import java.util.Collection;


public interface Column {
	public enum Type { Integer, Real, String };
	public String getName();
	public Type getType();
	public int getIntValue(int row);
	public double getRealValueAt(int row);
	public String getStringValueAt(int row);
	public Object getValueAt(int row);
	public void addValue(Object value);
	public int getRowCount();
	public void setValueAt(int row, Object value);
	public void reorder(Collection<Integer> rowOrder);
	public void clear();
	public void ensureCapacity(int mows);
	public double getMin();
	public double getMax();
}
