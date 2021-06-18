

/* ------------------------------------------------------------------
 * Table.java
 * 
 * Created 2008-10-30 by Niklas Elmqvist.
 * ------------------------------------------------------------------
 */

public interface Table {
	public void addColumn(Column column);
	public void removeColumn(Column column);
	public Column getColumnAt(int column);
	public Column getColumn(String name);
	public String getColumnName(int column);
	public Column.Type getColumnType(int column);
	public int getColumnCount();
	public int getRowCount();
	public Object[] getRow(int row);
	public void sort(int column, boolean ascending);
	public void addRow(Object... objects);
	public void clearRows();
	public void clear();
}