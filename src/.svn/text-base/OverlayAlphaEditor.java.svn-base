/* ------------------------------------------------------------------
 * OverlayAlphaEditor.java
 *
 * Created 2009-02-26 by Niklas Elmqvist <elm@purdue.edu>.
 * ------------------------------------------------------------------
 */


import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellEditor;

public class OverlayAlphaEditor extends AbstractCellEditor implements TableCellEditor, ChangeListener {
       private static final long serialVersionUID = 1L;
       private JSlider transSlider;
       private float alpha;

       public OverlayAlphaEditor() {
           transSlider = new JSlider();
           transSlider.setMinimum(0);
           transSlider.setMaximum(1000);
           transSlider.setMinorTickSpacing(1);
           transSlider.setMajorTickSpacing(10);
           transSlider.addChangeListener(this);
       }

       public Object getCellEditorValue() {
           return alpha;
       }

       public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
           alpha = (Float) value;
           transSlider.setValue((int) (alpha * 1000));
           return transSlider;
       }

       public void stateChanged(ChangeEvent e) {
           alpha = transSlider.getValue() / 1000.0f;
       }
}
