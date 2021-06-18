/* ------------------------------------------------------------------
 * myHighlight.java
 * 
 * Created 2008-??-?? by Waqas Javed.
 * ------------------------------------------------------------------
 */
import java.awt.geom.Point2D;

import javax.swing.tree.TreePath;

import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.nodes.PPath;

public class myHighLight extends PPath{

	private static final long serialVersionUID = 1L;
	private myNode node, linkNode;
	private Point2D p1, p2;
	float X = 0, tempX = 0;
	float W = 0;
	int b;
	int start = 0, end = 0;
	
	public myHighLight(myNode n){
		
		node = n;
		
		this.addInputEventListener(new PBasicInputEventHandler(){
			
			public void mouseEntered(PInputEvent e){
				moveToFront();
				
			}
			public void mouseExited (PInputEvent e){
				//moveToBack();
					
			}	
	
			public void mousePressed(PInputEvent e) {
				// TODO Auto-generated method stub
				node.dragHighLight = true;
				Point2D p = e.getPosition();
				p1 = p;
				//p1.setLocation(p.getX(), node.getY());
				p2 = p1;
				tempX = X;
				b = e.getButton();
			    System.err.println("HmousePressed at " + p);
			    start = node.startX+(int) (p.getX()-node.getNode().getX())*(node.endX - node.startX)/ ((int)node.getNode().getWidth());
			    System.out.println("start: "+ start);
			    
			    
			}
			public void mouseDragged(PInputEvent e) {
				// TODO Auto-generated method stub
				if (b==1){
					
					Point2D p = e.getPosition();
					p2 = p;
					X = tempX + (float)((p2.getX()-p1.getX())/node.getNode().getWidth());
					int diff = (int) (p2.getX()-p1.getX())*(node.endX - node.startX)/ ((int)node.getNode().getWidth());
					dragHighLight();
					if (node.atest.graphType == 1){
						linkNode.reDraw(diff);
						linkNode.reDrawLinking(diff);
					}
					if (node.atest.graphType == 2){
						linkNode.reDrawHorizon(diff);
					}
					linkNode.displayTable();
					linkNode.addDisplay(linkNode.dt.atributes, linkNode.dt.values);
					TreePath t = new TreePath(linkNode.tree.getPath());
				    linkNode.atest.tree.setSelectionPath(t);
				}
			    
			}
			
			public void mouseReleased(PInputEvent e) {
				
					Point2D p = e.getPosition();
					p2 = p;
					X = tempX + (float)((p2.getX()-p1.getX())/node.getNode().getWidth());
					int diff = (int) (p2.getX()-p1.getX())*(node.endX - node.startX)/ ((int)node.getNode().getWidth());
					dragHighLight();
					if (node.atest.graphType == 1){
						linkNode.reDraw(diff);
						linkNode.reDrawLinking(diff);
					}
					if (node.atest.graphType == 2){
						linkNode.reDrawHorizon(diff);
					}
					linkNode.setBound(diff);
					node.dragHighLight = false;
					
					
					System.err.println("hmouse dragged to " + p);
					System.out.println("end: "+ end);
			}
			
		});
	}
	
	public void addLink(myNode n){
		linkNode = n;
	}
	
	public void dragHighLight() {
	
		animateToBounds(node.getNode().getX()+(X*node.getNode().getWidth()), node.getNode().getY(), node.getNode().getWidth()*W, node.getNode().getHeight(), 0);	
	}
}
