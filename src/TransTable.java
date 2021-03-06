/* ------------------------------------------------------------------
 * OverlayTable.java
 *
 * Created 2009-02-26 by Niklas Elmqvist <elm@purdue.edu>.
 * ------------------------------------------------------------------
 */


import java.awt.Color;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import edu.umd.cs.piccolo.nodes.PPath;

public class TransTable extends AbstractTableModel {

       private static final long serialVersionUID = 1L;
       private static String columnNames[] = { "Transparency Level", "Color" };
       private ArrayList<PPath> graphs = new ArrayList<PPath>();
       private LayoutManager atest;
      
       public TransTable(ArrayList<PPath> g, LayoutManager a) {
               graphs = g;
               atest = a;
              
       }

       public void addTrans(PPath graph) {
               graphs.add(graph);
               
       }

       public Class<?> getColumnClass(int columnIndex) {
               switch (columnIndex) {
                       case 0: return Float.class;
                       case 1: return Color.class;
                       
               }
               return Object.class;
       }

       public int getColumnCount() {
               return 2;
       }

       public String getColumnName(int columnIndex) {
               return columnNames[columnIndex];
       }

       public int getRowCount() {
               return graphs.size();
       }

       public Object getValueAt(int rowIndex, int columnIndex) {
               PPath graph = graphs.get(rowIndex);
               switch (columnIndex) {
                       case 0: return graph.getTransparency();
                       case 1: return (Color) graph.getStrokePaint();
                       default: break;
               }
               return null;
       }

       public boolean isCellEditable(int rowIndex, int columnIndex) {
               return true;
       }

       public void setValueAt(Object value, int rowIndex, int columnIndex) {
               PPath graph = graphs.get(rowIndex);
               switch (columnIndex) {
               case 0: graph.setTransparency((Float) value); atest.layer.repaint(); break;
               case 1: atest.aNode[0][0].setStroke(rowIndex, (Color) value); 
		       		   if (atest.isFill()) graph.setPaint((Color) value);	
		       		   atest.layer.repaint(); atest.scrollPane2.repaint(); break;            	               	  
               default: return;
               }
               fireTableCellUpdated(rowIndex, columnIndex);
       }
}