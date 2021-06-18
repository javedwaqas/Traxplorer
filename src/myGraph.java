/* ------------------------------------------------------------------
 * myGraph.java
 * 
 * Created 2008-??-?? by Waqas Javed.
 * ------------------------------------------------------------------
 */
import java.awt.geom.Point2D;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.nodes.PPath;

public class myGraph extends PPath{
	
	private static final long serialVersionUID = 1L;
	PPath rectangle;
	PNode node;
	public myGraph (PNode n){
		
		node = n;
		this.addInputEventListener(new PBasicInputEventHandler(){
			
			public void mouseEntered(PInputEvent e){
				//moveToFront();
				Point2D p = e.getPosition();
				rectangle = PPath.createRectangle((float)p.getX(), (float)p.getY(), 5.0f, 5.0f);
				node.addChild(rectangle);
				
				
			}
			public void mouseExited (PInputEvent e){
				//moveToBack();
				node.removeChild(rectangle);
					
			}	
		});		
	}
}
