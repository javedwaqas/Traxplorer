import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

import edu.umd.cs.piccolo.PCamera;
import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.nodes.PPath;
import edu.umd.cs.piccolo.nodes.PText;
import edu.umd.cs.piccolo.util.PBounds;
import edu.umd.cs.piccolox.handles.PBoundsHandle;

public class myNode {
	int level,position;
	DefaultMutableTreeNode tree;
	PNode node;
	static final int XnumLabels = 10, YnumLabels = 20, MaxGraphs = 100;
	static final float translevel = 1.0f;
	PNode xLabel[] = new PNode [XnumLabels];
	PNode yLabel[] = new PNode [YnumLabels];
	int numLabelsX, numLabelsY;
	int graphcount = 0;
	LayoutManager atest;
	myNode parent;
	myNode children[] = new myNode [5];
	int nchild = 0;
	int startX,start,endX,end;
	float x[], y[][] = new float [MaxGraphs][];
	ArrayList<PPath> graphs = new ArrayList<PPath> ();
	ArrayList <PPath> horizonGraph = new ArrayList<PPath> ();
	PPath rectangle = PPath.createRectangle(0, 0, 5, 5);
	boolean drag = false, rsize = true;
	Point2D p1,p2;
	myHighLight highLight[] = new myHighLight [5];
	PPath arrow = new PPath();
	PPath arrow1 = new PPath();
	PPath arrow2 = new PPath();
	//float highLightX[] = new float [5];
	//float highLightW[] = new float [5];
	int phighLight;
	closeN cr;
	int i = 0;
	int c = 0;
	int b = 0;
	int z = 1;
	float ltrans = 0.9f;
	boolean close = false, dragHighLight = false;
	PCamera camera = new PCamera();
	PLayer layer = new PLayer();
	PropertyChangeListener pc, pc1, pc2;
    Color ncolor;
    myNode mn;
    float q[][] = new float [MaxGraphs][];
    String p[], rr[];
    boolean ischild = false;
    boolean dspd = false;
    PText displayText = new PText();
    public ArrayList<Boolean> selections  = new ArrayList<Boolean> ();
    dataTable dt;
    String annotation = new String("");
    
	public myNode (LayoutManager a, PNode n,int l, int p, Color nodeColor){
						
		level = l;
		position = p;
		atest = a;
		node = n;	
		ncolor = nodeColor;
		mn = this;
		
		tree = new DefaultMutableTreeNode(new treeNode(this));
		
		startX = 0; start = 0; endX = 0; end = 0;
		 
		//PBoundsHandle.addBoundsHandlesTo(node);
		((PPath) node).setStroke(new BasicStroke(1.5f));
		((PPath) node).setStrokePaint(nodeColor);
		
		node.addChild(camera);
		camera.addLayer(layer);
		camera.setBounds(node.getX(), node.getY(), node.getWidth(), node.getHeight());
		
		cr = new closeN(this, atest);
		
		//setHighLightTransperency(true);
		
		node.addInputEventListener(new PBasicInputEventHandler() {
			public void mousePressed(PInputEvent e) {
				// TODO Auto-generated method stub
				Point2D p = e.getPosition();
				p1 = p;
				p1.setLocation(p.getX(), node.getY());
				p2 = p1;
				b = e.getButton();
			    System.err.println("mousePressed at " + p);
			    start = startX+(int) (p.getX()-node.getX())*(endX - startX)/ ((int)node.getWidth());
			    System.out.println("start: "+ start);
			    
			    highLight[i] = new myHighLight(mn);
			    highLight[i].start = start;
			    ((PPath) highLight[i]).setStroke(new BasicStroke((float) (1 / e.getCamera().getViewScale())));
                layer.addChild(highLight[i]);
                updateRectangle (highLight[i]);
			    
			}
			public void mouseDragged(PInputEvent e) {
				// TODO Auto-generated method stub
				if (b==1 && !dragHighLight){
					//rsize = false;
					setDrag(true);
					Point2D p = e.getPosition();
					p2 = p;
					p2.setLocation(p.getX(), node.getY()+node.getHeight());
					((PPath) highLight[i]).setPaint(atest.color[level-1][(atest.lfmatrix[level])%5]);
					highLight[i].setTransparency((float) 0.2);
					updateRectangle(highLight[i]);
					
				    //System.err.println("mouse dragged to " + p);
				}
			    
			}
			public void mouseReleased(PInputEvent e) {
				
				if (isDrag()) {
					rsize = false;
					//updateRectangle(highLight[i]);
					Point2D p = e.getPosition();
					p2 = p;
					updateRectangle(highLight[i]);
					//((PPath)node).createRectangle((float)p1.getX(), (float)node.getY(), (float)(p2.getX()-p1.getX()), (float) node.getHeight());
					//highLight[i] = PPath.createRectangle((float)p1.getX(), (float)node.getY(), (float)(p2.getX()-p1.getX()), (float) node.getHeight());
					
					if (p1.getX() < p2.getX()){
						highLight[i].X = (float) ((float) (p1.getX() - node.getX())/node.getWidth());
						highLight[i].W = (float) ((p2.getX()-p1.getX())/node.getWidth());
					}
					else{
						highLight[i].X = (float) ((float) (p2.getX() - node.getX())/node.getWidth());
						highLight[i].W = (float) ((p1.getX()-p2.getX())/node.getWidth());
					}
					
					//((PPath) highLight[i]).setPaint(color[(atest.lfmatrix[level]+level)%5]);
					//highLight[i].setTransparency((float) 0.2);
					//highLight.setVisible(true);
					//layer.addChild(highLight[i]);
					//highLight.moveToFront();
					end = startX+(int) (p.getX()-node.getX())*(endX - startX)/ ((int)node.getWidth());
					
					System.err.println("mouse dragged to " + p);
					System.out.println("end: "+ end);
					i++;
					if (start < end){ 
						atest.newFrame(level, position, start, end, atest.color[level-1][(atest.lfmatrix[level])%5], i, tree, graphcount, q);
						highLight[i-1].end = end;						
					}
					else{
						atest.newFrame(level, position, end, start, atest.color[level-1][(atest.lfmatrix[level])%5], i, tree, graphcount, q);
						highLight[i-1].end = start;
						highLight[i-1].start = end;
					}
					
				}
				setDrag(false);
				
				rsize = true;		
				
			}
			
			public void mouseEntered(PInputEvent e){
				PBoundsHandle.addBoundsHandlesTo(node);	
				//node.moveToFront();
				rsize = true;
				/*if (level != 1){
					parent.highLight[phighLight-1].moveToFront();
					parent.highLight[phighLight-1].setTransparency((float) 0.9);
				}*/
				setHighLightTransperency(true);
				
				node.addPropertyChangeListener( PNode.PROPERTY_BOUNDS, pc);
				if ((e.getPosition().getX()-node.getX()) > (node.getWidth()-200)){
					cr.close.setVisible(true);
					cr.rsize.setVisible(true);
					cr.select.setVisible(true);
				}
				else{
					cr.close.setVisible(false);
					cr.rsize.setVisible(false);
					cr.select.setVisible(false);
				}
					
				
				
			}
			public void mouseExited (PInputEvent e){
				PBoundsHandle.removeBoundsHandlesFrom(node);
				/*if (level != 1 && !close){
					parent.highLight[phighLight-1].moveToBack();
					parent.graph.moveToBack();
					parent.highLight[phighLight-1].setTransparency((float) 0.2);
				}*/
				setHighLightTransperency(false);
				
				node.removePropertyChangeListener( PNode.PROPERTY_BOUNDS, pc);
				
				cr.close.setVisible(false);
				cr.rsize.setVisible(false);
				cr.select.setVisible(false);
				
			}
			/*
			public void mouseClicked (PInputEvent e){
				
				atest.aNode[level-1][position-1].displayTable();
			}
			*/
			public void updateRectangle(PNode node) {
                // create a new bounds that contains both the press and current
                // drag point.
                PBounds b = new PBounds();
                b.add(p1);
                b.add(p2);

                // Set the rectangles bounds.
                ((PPath) node).setPathTo(b);
            }
			
				
		});
		pc = new PropertyChangeListener() {
		      public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
		    	 if(rsize){ 
		    	  //System.out.println("property change1");
	    		getCamera().animateToBounds(node.getX(), node.getY(), node.getWidth(), node.getHeight(), 0);
	    	  	animateGraphs(node.getX(), node.getY(), node.getWidth(), node.getHeight(), 0);
	    	  	animatHighLight();
	    	  	getCR().animateCR();
	    	  	node.moveToFront();
	    	  	//atest.moveFront(level, position);
	    	  	if (atest.graphType == 1)
	    	  		animateLabels();
	    	  	atest.animateBounds(level, position);
		    	 }
		    		
		        
		      }
		    };
		    	
		
	}
	
	public boolean isDrag () {
		
		return drag;
	}
	
	public void setDrag(boolean a) {
		
		drag = a;
	}
	public void setHighLightTransperency(boolean a){
		if (a){
			if (level != 1){
				float aa [] = new float [3];
				float bb [] = new float [3];
				aa[0] = (float)(parent.highLight[phighLight-1].getX()+parent.highLight[phighLight-1].getWidth()/2);
				aa[1] = aa[2] = (float)(node.getX()+node.getWidth()/2);
				bb[0] = (float)(parent.highLight[phighLight-1].getY()+parent.highLight[phighLight-1].getHeight()/1.2);
				bb[1] = (float)(node.getY()+node.getHeight()/4 - 10);
				bb[2] = (float)(node.getY()+node.getHeight()/4);
				parent.highLight[phighLight-1].moveToFront();
				parent.highLight[phighLight-1].setTransparency((float) 0.5);
//				parent.arrow = PPath.createPolyline(aa, bb); //((float)(parent.highLight[phighLight-1].getX()+parent.highLight[phighLight-1].getWidth()/2), (float)(parent.highLight[phighLight-1].getY()+parent.highLight[phighLight-1].getHeight()/1.2), (float)(node.getX()+node.getWidth()/2), (float)(node.getY()+node.getHeight()/4));
//				//parent.arrow = PPath.createLine((float)(parent.highLight[phighLight-1].getX()+parent.highLight[phighLight-1].getWidth()/2), (float)(parent.highLight[phighLight-1].getY()+parent.highLight[phighLight-1].getHeight()/1.2), (float)(node.getX()+node.getWidth()/2), (float)(node.getY()+node.getHeight()/4));
//				parent.arrow.setTransparency(0.5f);
//				//parent.arrow.setStrokePaint(Color.GRAY);
//				parent.arrow1= PPath.createLine((float)(node.getX()+node.getWidth()/2), (float)(node.getY()+node.getHeight()/4), (float)(node.getX()+node.getWidth()/2 - 2.5), (float)(node.getY()+node.getHeight()/4 -5.5));
//				parent.arrow2= PPath.createLine((float)(node.getX()+node.getWidth()/2), (float)(node.getY()+node.getHeight()/4), (float)(node.getX()+node.getWidth()/2 + 2.5), (float)(node.getY()+node.getHeight()/4 -5.5));
//				atest.layer.addChild(parent.arrow);
//				atest.layer.addChild(parent.arrow1);
//				atest.layer.addChild(parent.arrow2);
				parent.setHighLightTransperency(a);
				

			}
		}
		else {
			if (level != 1 && !close){
				parent.highLight[phighLight-1].moveToBack();
				for (int i = 0; i < parent.graphcount; i++){
					parent.graphs.get(i).moveToBack();
				}
				parent.highLight[phighLight-1].setTransparency((float) 0.2);
//				atest.layer.removeChild(parent.arrow);
//				atest.layer.removeChild(parent.arrow1);
//				atest.layer.removeChild(parent.arrow2);
				parent.setHighLightTransperency(a);
			}
		}
	}
	
		
	public PNode getNode() {
		
		return node;
	}
	
	public int getLevel(){
		return level;
	}
	
	public int getPosition(){
		return position;
	}
	
	public void setPosition(int p){
		position = p;
		((treeNode) tree.getUserObject()).updateName();
	}
	
	public closeN getCR() {
		return cr;
	}
	public PCamera getCamera(){
		return camera;
	}
	
	public void animateLabels(){
		
		int xxx, yyy;
		xxx = numLabelsX;
		yyy = numLabelsY;
		
		numLabelsX = (int) Math.floor(node.getWidth()/XnumLabels);
		if (numLabelsX > XnumLabels)
			numLabelsX = XnumLabels;
		numLabelsY = (int) Math.floor(node.getHeight()/YnumLabels);
		if (numLabelsY > YnumLabels)
			numLabelsY = YnumLabels;
		
		for (int i = 1; i < xxx || i < numLabelsX; i++ ){
			
			if (i < xxx)
				layer.removeChild(xLabel[i-1]);
			
			if (i < numLabelsX){
				xLabel[i-1] = new PText(""+rr[ (int) Math.floor(i*rr.length/numLabelsX)]);
				((PText) xLabel[i-1]).setTextPaint(Color.GRAY);
				xLabel[i-1].setTransparency(ltrans);
				layer.addChild(xLabel[i-1]);
				//xLabel[i-1].animateToBounds(node.getX()+(i*node.getWidth()/numLabelsX), node.getY()+node.getHeight()-20, 20, 20, 0);
				xLabel[i-1].animateToPositionScaleRotation( node.getX()+(int)(i*node.getWidth()/numLabelsX), node.getY()+node.getHeight()-2, 1, -1*Math.PI/2, 0);
			}
		}
		
		for (int i = 1; i < yyy || i < numLabelsY ; i++ ){
			
			if (i < yyy)
				layer.removeChild(yLabel[i-1]);
			
			if (i < numLabelsY){
				yLabel[i-1] = new PText(""+ Round(-1*y[atest.labelGraph][findMax(y[atest.labelGraph])]+(i*(y[atest.labelGraph][findMax(y[atest.labelGraph])]-y[atest.labelGraph][findMin(y[atest.labelGraph])])/numLabelsY), 1));
				((PText) yLabel[i-1]).setTextPaint(Color.GRAY);
				yLabel[i-1].setTransparency(ltrans);
				//yLabel[i-1] = new PText(""+ Round(-1*y[(int) Math.floor((i*(endX-startX)/numLabelsY))], 3));
				layer.addChild(yLabel[i-1]);
				yLabel[i-1].animateToBounds(node.getX()+5, node.getY()+node.getHeight()-(i*node.getHeight()/numLabelsY), 20, 20, 0);
			}
		}
		
		for (int i =0; i < nchild; i++){
			children[i].animateLabels();
		}
		
	}
	
	public void animateGraphs(double x, double y, double width, double height, long  duration){
		if (atest.graphType == 1){
			for (int i = 0; i < graphcount; i++){
				graphs.get(i).animateToBounds(x, y, width, height, duration);
			}
		}
		if (atest.graphType == 2){
			for (int i = 0; i <= graphcount; i++){
				horizonGraph.get(i).animateToBounds(x, y, width, height, duration);
			}
		}
		
	}
	
	public void animatHighLight() {
		
		displayText.animateToPositionScaleRotation(node.getX()+10, node.getY()+10, 1, 0, 0);
		for (int j=0; j<i; j++){
			highLight[j].animateToBounds(node.getX()+(highLight[j].X*node.getWidth()), node.getY(), node.getWidth()*highLight[j].W, node.getHeight(), 0);
		}
	}
	
	public void setHighLight(int p){
		phighLight = p;
	}
	
	public void adjChild(int h, int p, Color c){
		
		highLight[h-1].removeFromParent();
		
		for (int i = h - 1; i < nchild - 1; i++){
			highLight[i].X = highLight[i+1].X; 
			highLight[i].W = highLight[i+1].W;
			highLight[i] = highLight[i+1];
			children[i] = children[i+1];
			children[i].setHighLight(i+1);
		}
		
		
		highLight[nchild-1] = null;
		//highLight[nchild-1].X = 0;
		//highLight[nchild-1].W = 0;
		
		this.i--;
		children[nchild-1] = null;
		nchild--;
		
	}
	
	public void drawGraph(String p[], float q[], int s, int e){
		
		//int a,b,c;
		graphcount++;
		final int ii = graphcount -1;
		x = new float [e-s+2];
		y[graphcount-1] = new float [e-s+2];
		rr = new String [e-s];
		startX = s;
		endX = e;
		//this.p = p;
		this.p = p;
		this.q[graphcount-1] = q;
		
		for (int i = 0; i < e-s; i++){
			rr[i] = p[i+s];
			x[i+1] = i+1;
			y[graphcount-1][i+1] = -1*q[i+s];
			//node.addChild(PPath.createLine(p[i+s], -1*q[i+s], p[i+s+1], -1*q[i+s+1]));
		}
		x[0]=0;
		x[x.length-1]=x.length-1;
		y[graphcount-1][0] =  y[graphcount-1][findMax1(y[graphcount-1])];
		y[graphcount-1][y[graphcount-1].length-1] =  y[graphcount-1][findMax1(y[graphcount-1])];
		
		PPath pp = PPath.createPolyline(x, y[graphcount-1]);
		//PPath pp = HorizonGraph.drawGraph(x, y[graphcount-1]);
		//pp.setStrokePaint(Color.red);
		graphs.add(graphcount-1, pp);
			
		//graphs.get(graphcount-1).setStroke(new BasicStroke(1.5f));
		//graphs.get(graphcount-1).Brush = Brushes.RED;
		if(ischild){
			graphs.get(graphcount-1).setStrokePaint(parent.graphs.get(graphcount-1).getStrokePaint());
			if (atest.fill)
				graphs.get(graphcount-1).setPaint(parent.graphs.get(graphcount-1).getStrokePaint());
			else
				graphs.get(graphcount-1).setPaint(null);
			graphs.get(graphcount-1).setVisible(parent.graphs.get(graphcount-1).getVisible());
			graphs.get(graphcount-1).setTransparency(parent.graphs.get(graphcount-1).getTransparency());
		}
		else{
			graphs.get(graphcount-1).setStrokePaint(Color.RED);
			if (atest.fill)
				graphs.get(graphcount-1).setPaint(Color.RED);
			else
				graphs.get(graphcount-1).setPaint(null);
			graphs.get(graphcount-1).setVisible(true);
			graphs.get(graphcount-1).setTransparency(translevel);
		}
		
		//graphs.get(graphcount-1).setVisible(false);
		layer.addChild(graphs.get(graphcount-1));
		graphs.get(graphcount-1).animateToBounds(node.getX(), node.getY(), node.getWidth(), node.getHeight(), 0);
		
		numLabelsX = (int) Math.floor(node.getWidth()/XnumLabels);
		if (numLabelsX > XnumLabels)
			numLabelsX = XnumLabels;
		numLabelsY = (int) Math.floor(node.getHeight()/YnumLabels);
		if (numLabelsY > YnumLabels)
			numLabelsY = YnumLabels;
		
		for (int i = 1; i < numLabelsX ; i++ ){
			xLabel[i-1] = new PText(""+rr[(int) Math.floor(i*rr.length/numLabelsX)]);
			((PText) xLabel[i-1]).setTextPaint(Color.GRAY);
			xLabel[i-1].setTransparency(ltrans);
			layer.addChild(xLabel[i-1]);
			//xLabel[i-1].animateToBounds(node.getX()+(i*node.getWidth()/numLabelsX), node.getY()+node.getHeight()-20, 20, 20, 0);
			xLabel[i-1].animateToPositionScaleRotation( node.getX()+(i*node.getWidth()/numLabelsX), node.getY()+node.getHeight()-2, 1, -1*Math.PI/2, 0);
		}
		
		for (int i = 1; i < numLabelsY ; i++ ){
			yLabel[i-1] = new PText(""+ Round(-1*y[graphcount-1][findMax(y[graphcount-1])]+(i*(y[graphcount-1][findMax(y[graphcount-1])]-y[graphcount-1][findMin(y[graphcount-1])])/numLabelsY), 3));
			((PText) yLabel[i-1]).setTextPaint(Color.GRAY);
			yLabel[i-1].setTransparency(ltrans);
//			yLabel[i-1] = new PText(""+ Round(-1*y[(int) Math.floor((i*(endX-startX)/numLabelsY))], 3));
			layer.addChild(yLabel[i-1]);
			yLabel[i-1].animateToBounds(node.getX()+5, node.getY()+node.getHeight()-(i*node.getHeight()/numLabelsY), 20, 20, 0);		
		}
		
		
		
		graphs.get(graphcount-1).addInputEventListener(new PBasicInputEventHandler(){
			int graphlocal = graphcount-1;
			public void mouseEntered(PInputEvent e){
				//moveToFront();
				try {
					Point2D p1 = e.getPosition();
					int diff = (int) (p1.getX()-node.getX())*(endX - startX)/ ((int)node.getWidth());
					rectangle = PPath.createRectangle((float)p1.getX(), (float)p1.getY()-15.0f, 100.0f, 15.0f);
					String a = " "+ rr[diff] + " , " + Round (-1*y[graphlocal][diff], 4);
					PNode text = new PText(a);
					text.setBounds(rectangle.getBounds());
					//text.animateToBounds(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), 0);
					rectangle.addChild(text);
					//layer.addChild(rectangle);
				}catch (ArrayIndexOutOfBoundsException ee){
					
				}
				
				
			}
			public void mouseMoved(PInputEvent e){
				//moveToFront();
				try{
					layer.removeChild(rectangle);
					Point2D p1 = e.getPosition();
					int diff = (int) (p1.getX()-node.getX())*(endX - startX)/ ((int)node.getWidth());
					rectangle = PPath.createRectangle((float)p1.getX(), (float)p1.getY()-15.0f, 100.0f, 15.0f);
					String a = " "+ rr[diff] + " , " + Round (-1*y[graphlocal][diff], 4);
					PNode text = new PText(a);
					//text.setOffset(p1);
					text.setBounds(rectangle.getBounds());
					text.animateToBounds(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), 0);
					rectangle.addChild(text);
					//layer.addChild(rectangle);
				}catch (ArrayIndexOutOfBoundsException ee){
					
				}
				
				
			}
			public void mouseExited (PInputEvent e){
				//moveToBack();
				try{
					//layer.removeChild(rectangle);
				}catch (ArrayIndexOutOfBoundsException ee){
					
				}
					
			}	
	
			
		});
		
		graphs.get(ii).addPropertyChangeListener(PPath.PROPERTY_PAINT, new PropertyChangeListener() {
		       public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
		    	 
		    	  for (int j = 0; j < nchild; j++){
		  			children[j].graphs.get(ii).setPaint(graphs.get(ii).getPaint());
		  		  }
		    	  atest.invalidate();
		       }
		    }
		 );
		graphs.get(ii).addPropertyChangeListener(PPath.PROPERTY_VISIBLE, new PropertyChangeListener() {
			   
		       public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
		    	 
		    	  for (int j = 0; j < nchild; j++){
		  			children[j].graphs.get(ii).setVisible(graphs.get(ii).getVisible());
		  		  }
		    	  atest.invalidate();
		       }
		    }
		);
		graphs.get(ii).addPropertyChangeListener(PPath.PROPERTY_TRANSPARENCY, new PropertyChangeListener() {
			   
		       public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
		    	 
		    	  for (int j = 0; j < nchild; j++){
		  			children[j].graphs.get(ii).setTransparency(graphs.get(ii).getTransparency());
		  		  }
		    	  atest.invalidate();
		       }
		    }
		);
		
	}
	
	
	public void addGraph(float q[]){
		
		graphcount++;
		final int ii = graphcount-1;
		y[graphcount-1] = new float [endX-startX+2];
		
		this.q[graphcount-1] = q;
		
		for (int i = 0; i < endX-startX; i++){
			y[graphcount-1][i+1] = -1*q[i+startX];
		}
		
		y[graphcount-1][0] =  y[graphcount-1][findMax1(y[graphcount-1])];
		y[graphcount-1][y[graphcount-1].length-1] =  y[graphcount-1][findMax1(y[graphcount-1])];
		
		PPath pp = PPath.createPolyline(x, y[graphcount-1]);
		//PPath pp = HorizonGraph.drawGraph(x, y[graphcount-1]);
		//pp.setStrokePaint(Color.red);
		graphs.add(graphcount-1, pp); 
		//graph[graphcount-1] = new PLine();
		//createline(graph[graphcount-1], x, y[graphcount-1]);
		
		layer.addChild(graphs.get(graphcount-1));
		graphs.get(graphcount-1).animateToBounds(node.getX(), node.getY(), node.getWidth(), node.getHeight(), 0);
		if(ischild){
			graphs.get(graphcount-1).setStrokePaint(parent.graphs.get(graphcount-1).getStrokePaint());
			if (atest.fill)
				graphs.get(graphcount-1).setPaint(parent.graphs.get(graphcount-1).getStrokePaint());
			else 
				graphs.get(graphcount-1).setPaint(null);
			graphs.get(graphcount-1).setVisible(parent.graphs.get(graphcount-1).getVisible());
			graphs.get(graphcount-1).setTransparency(parent.graphs.get(graphcount-1).getTransparency());
		}
		else{
			graphs.get(graphcount-1).setStrokePaint(Color.RED);
			if (atest.fill)
				graphs.get(graphcount-1).setPaint(Color.RED);
			else
				graphs.get(graphcount-1).setPaint(null);
			graphs.get(graphcount-1).setVisible(true);
			graphs.get(graphcount-1).setTransparency(translevel);
		}
		//graphs.get(graphcount-1).moveToFront();
		//graphs.get(graphcount-1).setVisible(false);
		
		graphs.get(graphcount-1).addInputEventListener(new PBasicInputEventHandler(){
			int graphlocal = graphcount-1;
			public void mouseEntered(PInputEvent e){
				//moveToFront();
				try{
					Point2D p1 = e.getPosition();
					int diff = (int) (p1.getX()-node.getX())*(endX - startX)/ ((int)node.getWidth());
					rectangle = PPath.createRectangle((float)p1.getX(), (float)p1.getY()-15.0f, 100.0f, 15.0f);
					String a = " "+ rr[diff] + " , " + Round (-1*y[graphlocal][diff], 4);
					PNode text = new PText(a);
					text.setBounds(rectangle.getBounds());
					//text.animateToBounds(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), 0);
					rectangle.addChild(text);
					//layer.addChild(rectangle);
				}catch(ArrayIndexOutOfBoundsException ee){
					
				}
				
				
			}
			public void mouseMoved(PInputEvent e){
				//moveToFront();
				try{
					layer.removeChild(rectangle);
					Point2D p1 = e.getPosition();
					int diff = (int) (p1.getX()-node.getX())*(endX - startX)/ ((int)node.getWidth());
					rectangle = PPath.createRectangle((float)p1.getX(), (float)p1.getY()-15.0f, 100.0f, 15.0f);
					String a = " "+ rr[diff] + " , " + Round (-1*y[graphlocal][diff], 4);
					PNode text = new PText(a);
					//text.setOffset(p1);
					text.setBounds(rectangle.getBounds());
					text.animateToBounds(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), 0);
					rectangle.addChild(text);
					//layer.addChild(rectangle);
				}catch(ArrayIndexOutOfBoundsException ee){
					
				}
				
				
			}
			public void mouseExited (PInputEvent e){
				//moveToBack();
				try{
					//layer.removeChild(rectangle);
				}catch(ArrayIndexOutOfBoundsException ee){
					
				}
					
			}	
	
			
		});
		
		graphs.get(ii).addPropertyChangeListener(PPath.PROPERTY_PAINT, new PropertyChangeListener() {
		       public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
		    	 
		    	  for (int j = 0; j < nchild; j++){
		  			children[j].graphs.get(ii).setPaint(graphs.get(ii).getPaint());
		  		  }
		    	  atest.invalidate();
		       }
		    }
		 );
		graphs.get(ii).addPropertyChangeListener(PPath.PROPERTY_VISIBLE, new PropertyChangeListener() {
			   
		       public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
		    	 
		    	  for (int j = 0; j < nchild; j++){
		  			children[j].graphs.get(ii).setVisible(graphs.get(ii).getVisible());
		  		  }
		        atest.invalidate();
		       }
		    }
		);
		
		graphs.get(ii).addPropertyChangeListener(PPath.PROPERTY_TRANSPARENCY, new PropertyChangeListener() {
			   
		       public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
		    	 
		    	  for (int j = 0; j < nchild; j++){
		  			children[j].graphs.get(ii).setTransparency(graphs.get(ii).getTransparency());
		  		  }
		    	  atest.invalidate();
		       }
		    }
		);
		
		for (int i = 0; i < nchild; i++){
			children[i].addGraph(q);
		}
		
		node.repaint();
		atest.scrollPane1.repaint();
		atest.scrollPane2.repaint();
		//atest.dataScrollPane.repaint();
		
	}
	
/*	public void createline(PLine a, float xx[], float yy[]){
		for (int i = 0; i < yy.length; i++){
			a.addPoint(i, xx[i], yy[i]);  
		}
	}
*/	
	public void reDraw(int diff){
		int tempSX = startX + diff;
		int tempEX = endX + diff;
		Color c;
		float d;
		boolean cc;
		
		x = new float [tempEX-tempSX+2];
		for (int i =0; i < graphcount; i++){ 
			if (!atest.pos.contains(new Integer(i)))
				y[i] = new float [tempEX-tempSX+2];
		}
		
		for (int i = 0; i < tempEX-tempSX; i++){
			rr[i] = p[i+tempSX];
			x[i+1] = i+1;
			for (int j = 0; j < graphcount; j++)
				if (! atest.pos.contains(new Integer(j)))
					y[j][i+1] = -1*q[j][i+tempSX];
			//node.addChild(PPath.createLine(p[i+s], -1*q[i+s], p[i+s+1], -1*q[i+s+1]));
		}
		x[0]=0;
		x[x.length-1]=x.length-1;
		for (int i =0; i < graphcount; i++){ 
			if (! atest.pos.contains(new Integer(i))){
				y[i][0] =  y[i][findMax1(y[i])];
				y[i][y[i].length-1] = y[i][findMax1(y[i])];

			}
		}
		
		
		for (int i = 0; i < graphcount; i++){
			final int ii = i;
			if (! atest.pos.contains(new Integer(i))){
				c = (Color) graphs.get(i).getStrokePaint();
				cc = graphs.get(i).getVisible();
				d = graphs.get(i).getTransparency();
				layer.removeChild(graphs.get(i));
				PPath pp = PPath.createPolyline(x, y[i]);
				pp.setStrokePaint(c);
				graphs.set(i, pp);
				if (atest.fill)	
					graphs.get(i).setPaint(c);
				else
					graphs.get(i).setPaint(null);
				graphs.get(i).setTransparency(d);
				graphs.get(i).setVisible(cc);
				//createline(graph[i], x, y[i]);
				layer.addChild(graphs.get(i));
				//graph[i].moveToBack();
				graphs.get(i).animateToBounds(node.getX(), node.getY(), node.getWidth(), node.getHeight(), 0);
				
				graphs.get(i).addInputEventListener(new PBasicInputEventHandler(){
					int graphlocal = ii;
					public void mouseEntered(PInputEvent e){
						//moveToFront();
						try{
							Point2D p1 = e.getPosition();
							int diff = (int) (p1.getX()-node.getX())*(endX - startX)/ ((int)node.getWidth());
							rectangle = PPath.createRectangle((float)p1.getX(), (float)p1.getY()-15.0f, 100.0f, 15.0f);
							String a = " "+ rr[diff] + " , " + Round (-1*y[graphlocal][diff], 4);
							PNode text = new PText(a);
							text.setBounds(rectangle.getBounds());
							//text.animateToBounds(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), 0);
							rectangle.addChild(text);
							//layer.addChild(rectangle);
						}catch(ArrayIndexOutOfBoundsException ee){
							
						}
						
						
					}
					public void mouseMoved(PInputEvent e){
						//moveToFront();
						try{
							layer.removeChild(rectangle);
							Point2D p1 = e.getPosition();
							int diff = (int) (p1.getX()-node.getX())*(endX - startX)/ ((int)node.getWidth());
							rectangle = PPath.createRectangle((float)p1.getX(), (float)p1.getY()-15.0f, 100.0f, 15.0f);
							String a = " "+ rr[diff] + " , " + Round (-1*y[graphlocal][diff], 4);
							PNode text = new PText(a);
							//text.setOffset(p1);
							text.setBounds(rectangle.getBounds());
							text.animateToBounds(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), 0);
							rectangle.addChild(text);
							//layer.addChild(rectangle);
						}catch(ArrayIndexOutOfBoundsException ee){
							
						}
						
						
					}
					public void mouseExited (PInputEvent e){
						//moveToBack();
						try{
							//layer.removeChild(rectangle);
						}catch(ArrayIndexOutOfBoundsException ee){
							
						}
							
					}	
			
					
				});
				
				    
			    graphs.get(ii).addPropertyChangeListener(PPath.PROPERTY_PAINT, new PropertyChangeListener() {
				       public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
				    	 
				    	  for (int j = 0; j < nchild; j++){
				  			children[j].graphs.get(ii).setPaint(graphs.get(ii).getPaint());
				  		  }
				    	  atest.invalidate();
				        
				       }
				    }
				 );
				graphs.get(ii).addPropertyChangeListener(PPath.PROPERTY_VISIBLE, new PropertyChangeListener() {
					   
				       public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
				    	 
				    	  for (int j = 0; j < nchild; j++){
				  			children[j].graphs.get(ii).setVisible(graphs.get(ii).getVisible());
				  		  }
				    	  atest.invalidate();
				       }
				    }
				);
				graphs.get(ii).addPropertyChangeListener(PPath.PROPERTY_TRANSPARENCY, new PropertyChangeListener() {
					   
				       public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
				    	 
				    	  for (int j = 0; j < nchild; j++){
				  			children[j].graphs.get(ii).setTransparency(graphs.get(ii).getTransparency());
				  		  }
				    	  atest.invalidate();
				       }
				    }
				);
				
			}
			animateLabels();
			
			for (int j = 0; j < nchild; j++){
				children[j].reDraw(diff);
			}
		}
		
	}
	
	public void reDrawLinking(int diff){
		int tempSX = startX + diff;
		int tempEX = endX + diff;
		float max=0 , min=0;
		Color c;
		float d;
		boolean cc;
				
		for (int i =0; i < graphcount; i++){ 
			if (atest.pos.contains(new Integer(i)))
				y[i] = new float [tempEX-tempSX+2];
		}
		
		for (int i = 0; i < tempEX-tempSX; i++){
			for (int j = 0; j < graphcount; j++)
				if (atest.pos.contains(new Integer(j)))
					y[j][i+1] = -1*q[j][i+tempSX];
			//node.addChild(PPath.createLine(p[i+s], -1*q[i+s], p[i+s+1], -1*q[i+s+1]));
		}
		
		if (! atest.pos.isEmpty()){
			max = y[atest.pos.get(0)][findMin1(y[atest.pos.get(0)])]; // values are with reverse signs
			min = y[atest.pos.get(0)][findMax1(y[atest.pos.get(0)])]; // values are with reverse signs
		}
		
		for (int i =1; i < atest.pos.size(); i++){ 
			if (y[atest.pos.get(i).intValue()][findMin1(y[atest.pos.get(i).intValue()])] < max){
				max = y[atest.pos.get(i).intValue()][findMin1(y[atest.pos.get(i).intValue()])];
			} 
			if(y[atest.pos.get(i)][findMax1(y[atest.pos.get(i)])] > min){
				min = y[atest.pos.get(i)][findMax1(y[atest.pos.get(i)])];
			}
		}
		
		//System.out.println(min+ "   "+max);
		
		for (int i =0; i < graphcount; i++){ 
			if (atest.pos.contains(new Integer(i))){
				y[i][0] =  min;
				y[i][y[i].length-2] =  max;
				y[i][y[i].length-1] =  min;
			}
		}
		
		
		for (int i = 0; i < graphcount; i++){
			final int ii = i;
			if (atest.pos.contains(new Integer(i))){
				c = (Color) graphs.get(i).getStrokePaint();
				cc = graphs.get(i).getVisible();
				d = graphs.get(i).getTransparency();
				layer.removeChild(graphs.get(i));	
				PPath pp =  PPath.createPolyline(x, y[i]);
				pp.setStrokePaint(c);
				graphs.set(i, pp);
				if (atest.fill == true)
					graphs.get(i).setPaint(c);
				else
					graphs.get(i).setPaint(null);
				graphs.get(i).setTransparency(d);
				graphs.get(i).setVisible(cc);
				//createline(graph[i], x, y[i]);
				layer.addChild(graphs.get(i));
				//graph[i].moveToBack();
				graphs.get(i).animateToBounds(node.getX(), node.getY(), node.getWidth(), node.getHeight(), 0);
				    
				graphs.get(i).addInputEventListener(new PBasicInputEventHandler(){
					int graphlocal = ii;
					public void mouseEntered(PInputEvent e){
						//moveToFront();
						try{
							Point2D p1 = e.getPosition();
							int diff = (int) (p1.getX()-node.getX())*(endX - startX)/ ((int)node.getWidth());
							rectangle = PPath.createRectangle((float)p1.getX(), (float)p1.getY()-15.0f, 100.0f, 15.0f);
							String a = " "+ rr[diff] + " , " + Round (-1*y[graphlocal][diff], 4);
							PNode text = new PText(a);
							text.setBounds(rectangle.getBounds());
							//text.animateToBounds(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), 0);
							rectangle.addChild(text);
							//layer.addChild(rectangle);
						}catch(ArrayIndexOutOfBoundsException ee){
							
						}
						
						
					}
					public void mouseMoved(PInputEvent e){
						//moveToFront();
						try{
							layer.removeChild(rectangle);
							Point2D p1 = e.getPosition();
							int diff = (int) (p1.getX()-node.getX())*(endX - startX)/ ((int)node.getWidth());
							rectangle = PPath.createRectangle((float)p1.getX(), (float)p1.getY()-15.0f, 100.0f, 15.0f);
							String a = " "+ rr[diff] + " , " + Round (-1*y[graphlocal][diff], 4);
							PNode text = new PText(a);
							//text.setOffset(p1);
							text.setBounds(rectangle.getBounds());
							text.animateToBounds(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), 0);
							rectangle.addChild(text);
							//layer.addChild(rectangle);
						}catch(ArrayIndexOutOfBoundsException ee){
							
						}
						
						
					}
					public void mouseExited (PInputEvent e){
						//moveToBack();
						try{
							//layer.removeChild(rectangle);
						}catch(ArrayIndexOutOfBoundsException ee){
							
						}
							
					}	
			
					
				});
				
				
				graphs.get(ii).addPropertyChangeListener(PPath.PROPERTY_PAINT, new PropertyChangeListener() {
				       public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
				    	 
				    	  for (int j = 0; j < nchild; j++){
				  			children[j].graphs.get(ii).setPaint(graphs.get(ii).getPaint());
				  		  }
				    	  atest.invalidate();
				        
				       }
				    }
				 );
				graphs.get(ii).addPropertyChangeListener(PPath.PROPERTY_VISIBLE, new PropertyChangeListener() {
					   
				       public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
				    	 
				    	  for (int j = 0; j < nchild; j++){
				  			children[j].graphs.get(ii).setVisible(graphs.get(ii).getVisible());
				  		  }
				    	  atest.invalidate();
				       }
				    }
				);
				graphs.get(ii).addPropertyChangeListener(PPath.PROPERTY_TRANSPARENCY, new PropertyChangeListener() {
					   
				       public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
				    	 
				    	  for (int j = 0; j < nchild; j++){
				  			children[j].graphs.get(ii).setTransparency(graphs.get(ii).getTransparency());
				  		  }
				    	  atest.invalidate();
				       }
				    }
				);
				
			}
			animateLabels();
			
			for (int j = 0; j < nchild; j++){
				children[j].reDrawLinking(diff);
			}
		}
		
	}
	
	public void reDrawLinkLayer(boolean b){
		int tempSX = startX;
		int tempEX = endX;
		ArrayList <Float> max, min;
		Color c;
		float d;
		boolean cc;
				
		max = atest.calculateMaxLayer(getLevel());
		min = atest.calculateMinLayer(getLevel());
		
		if (b)
			x = new float [tempEX-tempSX+1];
		else
			x = new float [tempEX-tempSX];
		
		for (int i =0; i < graphcount; i++){
			if (b)
				y[i] = new float [tempEX-tempSX+1];
			else 
				y[i] = new float [tempEX-tempSX];
		}
		
		for (int i = 0; i < tempEX-tempSX; i++){
			x[i] = i;
			for (int j = 0; j < graphcount; j++)
				y[j][i] = -1*q[j][i+tempSX];
			
		}
		
		if (b){
			x[x.length-1] = x.length-1;
			for (int i =0; i < graphcount; i++){ 
				y[i][y[i].length-2] =  max.get(i).floatValue();
			
			}
			
			for (int i =0; i < graphcount; i++){ 
				y[i][0] =  min.get(i);
				y[i][y[i].length-1] =  min.get(i);
			}
		}
		else {
			for (int i =0; i < graphcount; i++){
				
				y[i][0] =  y[i][findMax1(y[i])];
				y[i][y[i].length-1] =  y[i][0];
			}
		}
		

		for (int i = 0; i < graphcount; i++){
			final int ii = i;
			
			c = (Color) graphs.get(i).getStrokePaint();
			cc = graphs.get(i).getVisible();
			d = graphs.get(i).getTransparency();
			layer.removeChild(graphs.get(i));	
			PPath pp = PPath.createPolyline(x, y[i]);
			pp.setStrokePaint(c);
			graphs.set(i, pp);
			if (atest.fill)
				graphs.get(i).setPaint(c);
			else
				graphs.get(i).setPaint(null);
			graphs.get(i).setTransparency(d);
			graphs.get(i).setVisible(cc);
			//createline(graph[i], x, y[i]);
			layer.addChild(graphs.get(i));
			//graph[i].moveToBack();
			graphs.get(i).animateToBounds(node.getX(), node.getY(), node.getWidth(), node.getHeight(), 0);
			    
			graphs.get(i).addInputEventListener(new PBasicInputEventHandler(){
				int graphlocal = ii;
				public void mouseEntered(PInputEvent e){
					//moveToFront();
					try{
						Point2D p1 = e.getPosition();
						int diff = (int) (p1.getX()-node.getX())*(endX - startX)/ ((int)node.getWidth());
						rectangle = PPath.createRectangle((float)p1.getX(), (float)p1.getY()-15.0f, 100.0f, 15.0f);
						String a = " "+ rr[diff] + " , " + Round (-1*y[graphlocal][diff], 4);
						PNode text = new PText(a);
						text.setBounds(rectangle.getBounds());
						//text.animateToBounds(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), 0);
						rectangle.addChild(text);
						//layer.addChild(rectangle);
					}catch(ArrayIndexOutOfBoundsException ee){
						
					}
					
					
				}
				public void mouseMoved(PInputEvent e){
					//moveToFront();
					try{
						layer.removeChild(rectangle);
						Point2D p1 = e.getPosition();
						int diff = (int) (p1.getX()-node.getX())*(endX - startX)/ ((int)node.getWidth());
						rectangle = PPath.createRectangle((float)p1.getX(), (float)p1.getY()-15.0f, 100.0f, 15.0f);
						String a = " "+ rr[diff] + " , " + Round (-1*y[graphlocal][diff], 4);
						PNode text = new PText(a);
						//text.setOffset(p1);
						text.setBounds(rectangle.getBounds());
						text.animateToBounds(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), 0);
						rectangle.addChild(text);
						//layer.addChild(rectangle);
					}catch(ArrayIndexOutOfBoundsException ee){
						
					}
					
					
				}
				public void mouseExited (PInputEvent e){
					//moveToBack();
					try{
						//layer.removeChild(rectangle);
					}catch(ArrayIndexOutOfBoundsException ee){
						
					}
						
				}	
		
				
			});
			
			
			graphs.get(ii).addPropertyChangeListener(PPath.PROPERTY_PAINT, new PropertyChangeListener() {
			       public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
			    	 
			    	  for (int j = 0; j < nchild; j++){
			  			children[j].graphs.get(ii).setPaint(graphs.get(ii).getPaint());
			  		  }
			    	  atest.invalidate();
			        
			       }
			    }
			 );
			graphs.get(ii).addPropertyChangeListener(PPath.PROPERTY_VISIBLE, new PropertyChangeListener() {
				   
			       public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
			    	 
			    	  for (int j = 0; j < nchild; j++){
			  			children[j].graphs.get(ii).setVisible(graphs.get(ii).getVisible());
			  		  }
			    	  atest.invalidate();
			       }
			    }
			);
			graphs.get(ii).addPropertyChangeListener(PPath.PROPERTY_TRANSPARENCY, new PropertyChangeListener() {
				   
			       public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
			    	 
			    	  for (int j = 0; j < nchild; j++){
			  			children[j].graphs.get(ii).setTransparency(graphs.get(ii).getTransparency());
			  		  }
			    	  atest.invalidate();
			       }
			    }
			);
			
		}
		animateLabels();
		
		for (int j = 0; j < nchild; j++){
			children[j].reDrawLinkLayer(b);
		}
		
		
	}
	
	
	public void drawHorizonGraph(String p[], float q[], int s, int e){
		
		//int a,b,c;
		graphcount++;
		x = new float [e-s+2];
		y[graphcount-1] = new float [e-s+2];
		rr = new String [e-s];
		startX = s;
		endX = e;
		//this.p = p;
		this.p = p;
		this.q[graphcount-1] = q;
		
		for (int i = 0; i < e-s; i++){
			rr[i] = p[i+s];
			x[i+1] = i+1;
			y[graphcount-1][i+1] = -1*q[i+s];
			//node.addChild(PPath.createLine(p[i+s], -1*q[i+s], p[i+s+1], -1*q[i+s+1]));
		}
		x[0]=0;
		x[x.length-1]=x.length-1;
		y[graphcount-1][0] =  y[graphcount-1][findMax1(y[graphcount-1])];
		y[graphcount-1][y[graphcount-1].length-1] =  y[graphcount-1][findMax1(y[graphcount-1])];
		
		
		HorizonGraph hg = new HorizonGraph();
		hg.drawGraph(x, y[graphcount-1], horizonGraph);
		graphs.add(0, horizonGraph.get(1));

		for (int jj=0; jj<= graphcount; jj++){
			
				
			//graphs.get(graphcount-1).setStroke(new BasicStroke(1.5f));
			//graphs.get(graphcount-1).Brush = Brushes.RED;
			
			horizonGraph.get(jj).setStrokePaint(Color.BLUE);
			horizonGraph.get(jj).setPaint(Color.BLUE);
			
			horizonGraph.get(jj).setVisible(true);
			horizonGraph.get(jj).setTransparency(0.3f+jj*0.3f);
			
			
			//graphs.get(graphcount-1).setVisible(false);
			layer.addChild(horizonGraph.get(jj));
			horizonGraph.get(jj).animateToBounds(node.getX(), node.getY(), node.getWidth(), node.getHeight(), 0);
			
			numLabelsX = (int) Math.floor(node.getWidth()/XnumLabels);
			if (numLabelsX > XnumLabels)
				numLabelsX = XnumLabels;
			numLabelsY = (int) Math.floor(node.getHeight()/YnumLabels);
			if (numLabelsY > YnumLabels)
				numLabelsY = YnumLabels;
		/*	
			for (int i = 1; i < numLabelsX ; i++ ){
				xLabel[i-1] = new PText(""+rr[(int) Math.floor(i*rr.length/numLabelsX)]);
				((PText) xLabel[i-1]).setTextPaint(Color.GRAY);
				xLabel[i-1].setTransparency(ltrans);
				layer.addChild(xLabel[i-1]);
				//xLabel[i-1].animateToBounds(node.getX()+(i*node.getWidth()/numLabelsX), node.getY()+node.getHeight()-20, 20, 20, 0);
				xLabel[i-1].animateToPositionScaleRotation( node.getX()+(i*node.getWidth()/numLabelsX), node.getY()+node.getHeight()-2, 1, -1*Math.PI/2, 0);
			}
			
			for (int i = 1; i < numLabelsY ; i++ ){
				yLabel[i-1] = new PText(""+ Round(-1*y[graphcount-1][findMax(y[graphcount-1])]+(i*(y[graphcount-1][findMax(y[graphcount-1])]-y[graphcount-1][findMin(y[graphcount-1])])/numLabelsY), 3));
				((PText) yLabel[i-1]).setTextPaint(Color.GRAY);
				yLabel[i-1].setTransparency(ltrans);
	//			yLabel[i-1] = new PText(""+ Round(-1*y[(int) Math.floor((i*(endX-startX)/numLabelsY))], 3));
				layer.addChild(yLabel[i-1]);
				yLabel[i-1].animateToBounds(node.getX()+5, node.getY()+node.getHeight()-(i*node.getHeight()/numLabelsY), 20, 20, 0);		
			}			
			*/
		}
	}
	
	public void reDrawHorizon(int diff){
		int tempSX = startX + diff;
		int tempEX = endX + diff;
		
		//this.layer.removeAllChildren();
		//this.drawHorizonGraph(p, q[0], tempSX, tempEX);
		
		
		x = new float [tempEX-tempSX+2];
		for (int i =0; i < graphcount; i++){ 
			y[i] = new float [tempEX-tempSX+2];
		}
		
		for (int i = 0; i < tempEX-tempSX; i++){
			rr[i] = p[i+tempSX];
			x[i+1] = i+1;
			for (int j = 0; j < graphcount; j++)
				y[j][i+1] = -1*q[j][i+tempSX];
			//node.addChild(PPath.createLine(p[i+s], -1*q[i+s], p[i+s+1], -1*q[i+s+1]));
		}
		x[0]=0;
		x[x.length-1]=x.length-1;
		for (int i =0; i < graphcount; i++){ 
			y[i][0] =  y[i][findMax1(y[i])];
			y[i][y[i].length-1] = y[i][findMax1(y[i])];
		}
		
		layer.removeChild(horizonGraph.get(0));
		layer.removeChild(horizonGraph.get(1));
		layer.repaint();
		HorizonGraph hg = new HorizonGraph();
		hg.drawGraph(x, y[graphcount-1], horizonGraph);
		graphs.set(0, horizonGraph.get(1));
		
		for (int i = 0; i <= graphcount; i++){
					
			horizonGraph.get(i).setStrokePaint(Color.BLUE);
			horizonGraph.get(i).setPaint(Color.BLUE);
			
			horizonGraph.get(i).setTransparency(0.3f+i*0.3f);
			
				//createline(graph[i], x, y[i]);
			layer.addChild(horizonGraph.get(i));
				//graph[i].moveToBack();
			horizonGraph.get(i).animateToBounds(node.getX(), node.getY(), node.getWidth(), node.getHeight(), 0);																  		
		
			animateLabels();
			node.repaint();
			
		
			for (int j = 0; j < nchild; j++){
				children[j].reDrawHorizon(diff);
			}
		}
		
	}
	
	
	
	public void setStroke (int seriesNo, Color c){		
		((PPath)graphs.get(seriesNo)).setStrokePaint(c);
		for (int j = 0; j < nchild; j++){
			children[j].setStroke(seriesNo, c);
		}
	}
	
	public void fillGraph (){		
		Color cc;
		for (int j = 0; j < graphcount; j++){
			cc = (Color) (graphs.get(j)).getStrokePaint();
			graphs.get(j).setPaint(cc);
		}
	}
	
	public void notFillGraph (){		
		
		for (int j = 0; j < graphcount; j++){
			graphs.get(j).setPaint(null);
		}
	}
	
		public void setBound(int diff){
		startX = startX + diff;
		endX = endX + diff;
		
		for (int i = 0; i < nchild; i++){
			children[i].setBound(diff);
		}
		
	}
	
	public void addParent (myNode a) {
		
		parent = a;
		ischild = true;
	}
	
	public void addChild(myNode a) {
		
		children[nchild] = a;
		highLight[nchild].addLink(children[nchild]);
		nchild++;
	}
	
	public boolean isParent(){
		
		if (nchild == 0){
			return false;			
		}
		else
			return true;
	}
	
	public void remove(){
		
		atest.removeTreeNode(parent.tree, tree, level);
		if (!isParent()){
			node.removeFromParent();
			atest.remove(this);
		}
		else {
			for (int i=0; i<nchild; i++){
				children[i].remove();
			}
			node.removeFromParent();
			atest.remove(this);
		}
			
	}
	
	public float Round(float Rval, int Rpl) {
		  float p = (float)Math.pow(10,Rpl);
		  Rval = Rval * p;
		  float tmp = Math.round(Rval);
		  return (float)tmp/p;
	}
	
	public float findMaximum(int i){
		float a = y[i][0];
		
		for (int k=1; k < y[i].length; k++){
			if (y[i][k] < a)
				a = y[i][k];
		}
		
		return a;
	}
	
	public float findMinimum(int i){
		float a = y[i][0];
		
		for (int k=1; k < y[i].length; k++){
			if (y[i][k] > a)
				a = y[i][k];
		}
		
		return a;
	}
	
	public int findMax(float [] a){
		int i = 0;
		
		for (int j=0; j< a.length; j++){
			if (a[j] > a[i])
				i = j;
		}
		
		return i;
	}
	
	public int findMax1(float [] a){
		int i = 1;
		
		for (int j=1; j< a.length-1; j++){
			if (a[j] > a[i])
				i = j;
		}
		
		return i;
	}
	
	public int findMin(float [] a){
		int i = 0;
		
		for (int j=0; j< a.length; j++){
			if (a[j] < a[i])
				i = j;
		}
		
		return i;
	}
	
	public int findMin1(float [] a){
		int i = 0;
		
		for (int j=1; j< a.length-1; j++){
			if (a[j] < a[i])
				i = j;
		}
		
		return i;
	}
	
	public String findRange(){
		
		return ""+rr[0]+" to "+ rr[rr.length-1];
	}
	
	public String displayAvg(float []b){
		
		return ""+average(b);
		
	}
	
	public float average(float []b){
		float a = 0;
		for (int i=1; i<b.length-1; i++){
			a = a+b[i];
		}
		
		return -1*(a/b.length);
	}
	public String displayMax(float []b){
		int i=1;
		for (int j=1; j< b.length-1; j++){
			if (b[j] < b[i])
				i = j;
		}
		
		return ""+(-1*b[i]);
	}
	
	public String displayMin(float []b){
		int i=1;
		for (int j=1; j< b.length-1; j++){
			if (b[j] > b[i])
				i = j;
		}
		
		return ""+(-1*b[i]);
	}
	
	public void displayTable(){
		dt = new dataTable(this, selections, atest.trackName);		

		for (int i=0; i<graphcount; i++){
			if (i==0){
				dt.addData("comments",annotation);
				dt.addData("Range",""+findRange());
			}
			dt.addData("Track-Name", atest.trackName.get(i));	
			dt.addData("Avg",""+displayAvg(y[i]));
			dt.addData("Max",""+displayMax(y[i]));
			dt.addData("Min",""+displayMin(y[i]));
			
		}
		
		atest.addTable3(dt);
	}
	
	public void addDisplay(ArrayList<String> atributes, ArrayList<String> values){
		
		//displayTable displaytable = new displayTable();
		
		String data = new String("");
		for (int i = 0; i< selections.size(); i++){
			if (selections.get(i)){
				data = data+atributes.get(i)+": "+ values.get(i)+"\n";
			}
		}
		//JTable jt = new JTable(displaytable);
		//displayScrollPane = new PScrollPane(jt);
		if (dspd)
			layer.removeChild(displayText);
		displayText.setText(data);
		layer.addChild(displayText);
		dspd = true;
		displayText.animateToPositionScaleRotation(node.getX()+35, node.getY()+10, 1, 0, 0);
		displayText.setVisible(true);
		displayText.moveToFront();
		displayText.setTransparency(0.8f);
		
		
	}
	
	public void removeDisplay (){
		if (dspd)
			layer.removeChild(displayText);
		dspd = false;
	}
	
}
