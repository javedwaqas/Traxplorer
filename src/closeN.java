import java.awt.Color;
import java.awt.event.MouseEvent;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.nodes.PPath;


public class closeN {
	
	PNode close;
	PNode rsize;
	PNode select;
	myNode pnode;
	LayoutManager atest;
	boolean r,cr;
	float x, y, w, h;
	public closeN(myNode n, LayoutManager l){
		
		pnode = n;
		r = false;
		cr = true;
		atest = l;
		
		close = PPath.createRectangle((float)(pnode.getNode().getX()+pnode.getNode().getWidth()-10), (float)(pnode.getNode().getY()), (float)10.0, (float)10.0);
		pnode.getNode().addChild(close);
		close.setPaint(Color.RED);
		close.setTransparency((float) 0.5);
		close.moveToFront();
		
		pnode = n;
		rsize = PPath.createRectangle((float)(pnode.getNode().getX()+pnode.getNode().getWidth()-2*10), (float)(pnode.getNode().getY()), (float)10.0, (float)10.0);
		pnode.getNode().addChild(rsize);
		rsize.setPaint(Color.GREEN);
		rsize.setTransparency((float) 0.5);
		rsize.moveToFront();
		
		pnode = n;
		select = PPath.createRectangle((float)(pnode.getNode().getX()+pnode.getNode().getWidth()-3*10), (float)(pnode.getNode().getY()), (float)10.0, (float)10.0);
		pnode.getNode().addChild(select);
		select.setPaint(Color.BLUE);
		select.setTransparency((float) 0.5);
		select.moveToFront();
		
		
		rsize.addInputEventListener(new PBasicInputEventHandler() {
			public void mousePressed(PInputEvent e) {
				if (r){
					//System.out.println("press");
					r = false;
					cr = true;
					pnode.getNode().animateToBounds(x, y, w, h, 0);
					atest.repaint();
					if (atest.graphType ==1)
						atest.aNode[0][0].reDraw(0);
					if (atest.graphType ==2)
						atest.aNode[0][0].reDrawHorizon(0);
					//System.err.print("print");
					
					/*
					pnode.getGraph().animateToBounds(x, y, w, h,0);
					pnode.getCamera().animateToBounds(x, y, w, h, 0);
					pnode.animatHighLight();
					close.animateToBounds(x+w-10, (float)(y), (float)10.0, (float)10.0, 0);
					rsize.animateToBounds(x+w-2*10, (float)(y), (float)10.0, (float)10.0, 0);
					*/
					
				}
				
				else{
					
					x = (float) pnode.getNode().getX();
					y = (float) pnode.getNode().getY();
					w = (float) pnode.getNode().getWidth();
					h = (float) pnode.getNode().getHeight();
					r = true;
					cr = false;
					
					pnode.getNode().moveToFront();
					
					pnode.getNode().animateToBounds(0, 0, 1128, 726, 0);
					
					/*
					pnode.getCamera().animateToBounds(5, 5, 1270, 740, 0);
					pnode.getGraph().animateToBounds(5, 5, 1270, 740, 0);
					pnode.animatHighLight();
					close.animateToBounds(1270-10, (float)(5), (float)10.0, (float)10.0, 0);
					rsize.animateToBounds(1270-2*10, (float)(5), (float)10.0, (float)10.0, 0);
					*/
					
				}
			}
		});	
		
		close.addInputEventListener(new PBasicInputEventHandler() {
			public void mousePressed(PInputEvent e) {
				pnode.close = true;
				pnode.parent.adjChild(pnode.phighLight,pnode.getPosition(),pnode.ncolor);
				pnode.remove(); 
				pnode.atest.reAnimate();
			}
			
		});
		
		select.addInputEventListener(new PBasicInputEventHandler() {
			public void mousePressed(PInputEvent e) {
				//pnode.setHighLightTransperency(false);
				TreePath t = new TreePath(pnode.tree.getPath());
			    atest.tree.setSelectionPath(t);
			    if (e.getClickCount() == 2){
			    	atest.tree.collapsePath(t);
			    	if (pnode.tree.isLeaf()){
			    		pnode.node.setVisible(false);
			    		((treeNode) pnode.tree.getUserObject()).setVisible("H");
			    		((treeNode) pnode.tree.getUserObject()).updateName();
			    		atest.tree.repaint();
			    	}
			    }
			    	
				
			}
		});
		
	}
	
	public void animateCR(){
		close.animateToBounds(pnode.getNode().getX()+pnode.getNode().getWidth()-10, (float)(pnode.getNode().getY()), (float)10.0, (float)10.0, 0);
		rsize.animateToBounds(pnode.getNode().getX()+pnode.getNode().getWidth()-2*10, (float)(pnode.getNode().getY()), (float)10.0, (float)10.0, 0);
		select.animateToBounds(pnode.getNode().getX()+pnode.getNode().getWidth()-3*10, (float)(pnode.getNode().getY()), (float)10.0, (float)10.0, 0);
		if (cr){
			x = (float) pnode.getNode().getX();
			y = (float) pnode.getNode().getY();
			w = (float) pnode.getNode().getWidth();
			h = (float) pnode.getNode().getHeight();
		}
	}

}
