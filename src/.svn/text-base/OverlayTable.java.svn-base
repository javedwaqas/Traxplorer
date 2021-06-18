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

public class OverlayTable extends AbstractTableModel {

       private static final long serialVersionUID = 1L;
       private static String columnNames[] = { "V", "C", "Title", "Link", "Label" };
       private ArrayList<PPath> graphs = new ArrayList<PPath>();
       private ArrayList<Integer> pos = new ArrayList<Integer> ();
       private LayoutManager atest;
       private ArrayList<Integer> l = new ArrayList<Integer> ();
       private ArrayList<String> trackName;

       public OverlayTable(ArrayList<PPath> g, ArrayList<Integer> p, LayoutManager a, ArrayList<String> n) {
               graphs = g;
               pos = p;
               l.add(0, 0);
               atest = a;
               trackName = n;
                            
       }

       public void addOverlay(PPath graph) {
               graphs.add(graph);
               
       }

       public Class<?> getColumnClass(int columnIndex) {
               switch (columnIndex) {
                       case 0: return Boolean.class;
                       case 1: return Color.class;
                       case 2: return String.class;
                       case 3: return Boolean.class;
                       case 4: return Boolean.class;
               }
               return Object.class;
       }

       public int getColumnCount() {
               return 5;
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
                       case 0: return graph.getVisible();
                       case 1: return (Color)graph.getStrokePaint();
                       case 2: return trackName.get(rowIndex);
                       case 3: 
                    	   if (pos.contains(new Integer(rowIndex)))
                    		   return true;
                    	   else
                    		   return false;
                       case 4: return l.contains(new Integer(rowIndex));
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
                       case 0: graph.setVisible((Boolean) value); atest.layer.repaint(); atest.scrollPane2.repaint(); break;
                       case 1: atest.aNode[0][0].setStroke(rowIndex, (Color) value); 
                       		   if (atest.isFill()) graph.setPaint((Color) value);	
                       		   atest.layer.repaint(); atest.scrollPane2.repaint(); break;
                       case 2: trackName.remove(rowIndex); trackName.add(rowIndex, (String) value); 
                       		   atest.dataScrollPane.repaint();
                               break;
                       case 3: 
                    	   if((Boolean)value) {
                    		   pos.add(new Integer(rowIndex)); 
                    		   atest.aNode[0][0].reDrawLinking(0); 
                    		   atest.aNode[0][0].reDraw(0);
                    		   
                    		   //System.out.println("add "+ rowIndex);
                    		   break;
                    	   }
                    	   else{
                    		   pos.remove(pos.indexOf(new Integer(rowIndex)));
                    		   atest.aNode[0][0].reDraw(0);
                    		   atest.aNode[0][0].reDrawLinking(0);
                    		   
                    		   //System.out.println("remove "+ rowIndex);
                    		   break;
                    	   } 
                       case 4: l.clear(); l.add(0, rowIndex); atest.labelGraph = rowIndex; 
                       		   atest.aNode[0][0].animateLabels();
                       		   break;
                      default: return;
               }
               
                             
       }
}