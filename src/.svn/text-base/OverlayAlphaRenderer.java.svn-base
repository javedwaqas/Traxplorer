/* ------------------------------------------------------------------
 * OverlayAlphaRenderer.java
 *
 * Created 2009-02-26 by Niklas Elmqvist <elm@purdue.edu>.
 * ------------------------------------------------------------------
 */


import java.awt.Component;

import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class OverlayAlphaRenderer extends JSlider implements TableCellRenderer {
       private static final long serialVersionUID = 1L;

       public OverlayAlphaRenderer() {
               setMinimum(0);
               setMaximum(1000);
       }

       public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

               float alpha = (Float) value;
               setValue((int) (alpha * 1000));

               if (isSelected) {
		           setBackground(table.getSelectionBackground());
		           setForeground(table.getSelectionForeground());
               } else {
            	   setBackground(table.getBackground());
            	   setForeground(table.getForeground());
               }

               setToolTipText("Alpha " + (alpha * 1000) + "%");
               return this;
       }
}