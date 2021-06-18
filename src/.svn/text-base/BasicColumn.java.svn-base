import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/* ------------------------------------------------------------------
 * BasicColumn.java
 * 
 * Created 2008-10-30 by Niklas Elmqvist.
 * ------------------------------------------------------------------
 */

public abstract class BasicColumn implements Column {	
	private String name;
	private Type type;
	public BasicColumn(String name, Type type) {
		this.name = name;
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public Type getType() {
		return type;
	}
	public String toString() { 
		return name + " (" + type + ")";
	}
	public static <E> void reorder(Collection<Integer> rowOrder, ArrayList<E> oldRows, ArrayList<E> newRows) {
		for (Iterator<Integer> i = rowOrder.iterator(); i.hasNext(); ) { 
			int nextRow = i.next();
			newRows.add(oldRows.get(nextRow));
		}
	}	
	public double getMin() {
		return 0.0;
	}
	public double getMax() {
		return 0.0;
	}
}
