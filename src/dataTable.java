
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class dataTable extends AbstractTableModel {

       private static final long serialVersionUID = 1L;
       private static String columnNames[] = { "V", "attribute", "value" };
       public ArrayList<String> atributes = new ArrayList<String>();
       public ArrayList<String> values = new ArrayList<String> ();
       public ArrayList<Boolean> selections;
       public myNode aNode;
       public ArrayList<String> trackName;
       public dataTable(myNode a, ArrayList<Boolean> s, ArrayList<String> n) {
              aNode = a;
              selections = s;
              trackName = n; 
       }

       public void addData(String atribute, String value) {
               atributes.add(atribute);
               values.add(value);
               if (values.size() > selections.size())
            	   selections.add(false);
               fireTableDataChanged();
       }

       public Class<?> getColumnClass(int columnIndex) {
               switch (columnIndex) {
                       case 0: return Boolean.class;
                       case 1: return String.class;
                       case 2: return String.class;
                       
               }
               return Object.class;
       }

       public int getColumnCount() {
               return 3;
       }

       public String getColumnName(int columnIndex) {
               return columnNames[columnIndex];
       }

       public int getRowCount() {
               return atributes.size();
       }

       public Object getValueAt(int rowIndex, int columnIndex) {
               
               switch (columnIndex) {
                       case 0: return selections.get(rowIndex);
                       case 1: return atributes.get(rowIndex);
                       case 2: return values.get(rowIndex);
                       default: break;
               }

               return null;
       }

       public boolean isCellEditable(int rowIndex, int columnIndex) {
               return true;
       }

       public void setValueAt(Object value, int rowIndex, int columnIndex) {
               
               switch (columnIndex) {
                       case 0: selections.remove(rowIndex); selections.add(rowIndex,(Boolean) value); 
                       		   aNode.addDisplay(atributes, values);
                       		   break;
                       case 1: atributes.remove(rowIndex); atributes.add(rowIndex,(String) value); break;
                       case 2: values.remove(rowIndex); values.add(rowIndex,(String) value); 
                       		   if (rowIndex == 0){
                       			   aNode.annotation = values.get(rowIndex);
                       		   }
                       		   if ((rowIndex-6)%4 == 0){
                       			   trackName.remove(((rowIndex-6)/4)+1); trackName.add(((rowIndex-6)/4)+1, (String) value);
                       			   aNode.atest.scrollPane1.repaint();
                       		   }
                       		   if (rowIndex == 2){
                    			   trackName.remove(0); trackName.add(0, (String) value);
                    		   }
                               break;
                       default: return;
               }
               fireTableCellUpdated(rowIndex, columnIndex);
       }
}
