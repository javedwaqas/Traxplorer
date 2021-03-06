/* ------------------------------------------------------------------
 * LayoutManager.java
 * 
 * Created 2008-??-?? by Waqas Javed.
 * ------------------------------------------------------------------
 */
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.nodes.PPath;
import edu.umd.cs.piccolox.PFrame;

public class LayoutManager extends PFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean inDrag = false;
	int graphType = 1;
	int level;
	int levels;
	int lfmatrix[] = new int [10];
	float width, height;
	public JTree tree;
	static final int maxLevels = 10;
	static final int maxFrames = 5;
	int labelGraph = 0;
	//DefaultMutableTreeNode [][]top = new DefaultMutableTreeNode [maxLevels][maxFrames];
	String [][] treeObject = new String [maxLevels][maxFrames];
	String x[] ;
	float y[] ;
	int position[][] = new int [10][5]; 
	Color color[][] = new Color[maxLevels][maxFrames];	
	myNode [][]aNode = new myNode[maxLevels][maxFrames];
	DefaultMutableTreeNode [][]treeNode = new DefaultMutableTreeNode [maxLevels][maxFrames];
	public PLayer layer = getCanvas().getLayer();
	ArrayList<Integer> pos = new ArrayList<Integer> ();
	public ArrayList<String> trackName = new ArrayList<String> ();
	public ArrayList<Float> LayerMax = new ArrayList<Float> ();
    public ArrayList<Float> LayerMin = new ArrayList<Float> ();
	JScrollPane dataScrollPane, scrollPane1, scrollPane2;
	boolean ds = false; 
	boolean fill = false;
	int selectedLevel, selectedPosition;
	dataTable datat;
	myNode selectedNode = null;
	
	public LayoutManager(){
		//this.setSize(1128, 726);
		//this.setBounds(150, 0, this.getWidth(), this.getHeight());
	}
	public void draw(String xx[], float yy[]) {
		
		
		x = xx;
		y = yy;
		
		// Create the Target for our Activities.

		// Create a new node that we will apply different activities to, and
		// place that node at location 200, 200.
	/*			
		for (int i=0; i< x.length; i++) {
			x[i] = (float) (i*Math.PI/180);
			y[i] = (float) Math.sin(x[i]);
		}
	*/	
		//this.setSize(1128, 726);
		//this.setBounds(150, 0, this.getWidth(), this.getHeight());
		//this.setSize(400, 400);
		
		width = 1128; //this.getWidth();
		height = 726; //this.getHeight();
		//aNode[0][0] = new PNode();
		PNode a = PPath.createRectangle(0, 0, width, height);
		
		a.setVisible(true);
		aNode[0][0] = new myNode(this, a, 1, 1, Color.BLACK);
		Color tcolor[] = {Color.RED, Color.GREEN, Color.BLACK, Color.BLUE, Color.YELLOW};
		color[0] = tcolor;
		
		layer.addChild(aNode[0][0].getNode());
		TreeInitialize(aNode[0][0].tree);
		
		lfmatrix[0] = 1;
		lfmatrix[1] = 0;
		levels = 1;
		
		if (graphType == 1)
			aNode[0][0].drawGraph(x, y, 0, x.length);
		if (graphType == 2)
			aNode[0][0].drawHorizonGraph(x, y, 0, x.length);
		//aNode[0][0].addGraph(y);
		position[0][0] = 0;
		addTable1(aNode[0][0]);
		addTable2(aNode[0][0]);
		try {
		    TreePath tt = new TreePath(aNode[0][0].tree.getPath());
		    tree.setSelectionPath(tt);
		    }
		    catch (Exception ee){
		    	//System.out.println("error");
		    }
		//aNode[0][0].displayTable();
		getCanvas().setPanEventHandler(null);
		
		//aNode.setOffset(200, 200);
		
	}

	public int newFrame(int l, int p, int s, int e, Color nodeColor, int hLight, DefaultMutableTreeNode ptree, int gcount, float u[][]){
		float startX, startY, w, h;
		int f, g, rt;
		f = l;
		g = p;
		startX =0;
		startY =0;
		w = width;
		h = height;
		int k = 0,t = 0;
		
		
		if (levels == l){	
			Color tcolor[] = {Color.RED, Color.GREEN, Color.BLACK, Color.BLUE, Color.YELLOW};
			color[levels] = tcolor;
			
			levels++;
			lfmatrix[l] = 1;
			//createNodes(top[0][0],top[l][0]);
			t = levels;
			lfmatrix[levels] = 0;
			k = 1;
			p = 1;
		}
		else {
			lfmatrix[l]++;
			t = l+1;
			p = findPosition(l, s, lfmatrix[l]);
			k = p;
			
		}
		rt = k;
		for (int i = 0; i< levels; i++) {
			for (int j = 1; j <= lfmatrix[i]; j++){
				
				if(i==l && j == p){
					//System.out.println(l + "  ");
					
					PNode a;
					a = PPath.createRectangle(startX, startY, w/lfmatrix[i],h/levels);
					//top[i][j-1] = new DefaultMutableTreeNode("A new node");
					//a.setVisible(true);
					aNode[i][j-1] = new myNode(this, a, t, k,nodeColor);
					aNode[i][j-1].addParent(aNode[f-1][g-1]);
					if (graphType == 1)
						aNode[i][j-1].drawGraph(x, y, s, e);
					if (graphType == 2){
						aNode[i][j-1].drawHorizonGraph(x, y, s, e);
						
					}
					//aNode[i][j-1].addGraph(y);
					
					for (int ii = 1; ii < gcount; ii++){
						aNode[i][j-1].addGraph(u[ii]);
					}
					if (graphType == 1){
						aNode[i][j-1].reDraw(0);
						aNode[i][j-1].reDrawLinking(0);
					}
					//if (graphType == 2)
						//aNode[i][j-1].reDraw(0);
					position[i][j-1] = s;
					aNode[f-1][g-1].addChild(aNode[i][j-1]);
					
					aNode[i][j-1].setHighLight(hLight);
					layer.addChild(aNode[i][j-1].getNode());
					createTreeNode(ptree,aNode[i][j-1].tree);
					try {
					    TreePath tt = new TreePath(aNode[i][j-1].tree.getPath());
					    tree.setSelectionPath(tt);
					    }
					    catch (Exception ee){
					    	//System.out.println("error");
					    }
					System.out.println("i = "+i);
										
				}
				else {
					//System.out.println(i + "  "+ j);
					//aNode[i][j-1].animate(startX, startY,w/lfmatrix[i],h/levels,0);				
					aNode[i][j-1].getNode().animateToBounds(startX, startY,w/lfmatrix[i],h/levels,0);
					
					aNode[i][j-1].getCamera().animateToBounds(startX, startY,w/lfmatrix[i],h/levels,0);
					aNode[i][j-1].animateGraphs(startX, startY,w/lfmatrix[i],h/levels,0);
					aNode[i][j-1].animatHighLight();
					aNode[i][j-1].getCR().animateCR();
					if (graphType == 1)
						aNode[i][j-1].animateLabels();
								
				}
				startX = startX+(w/lfmatrix[i])+1;
			}
			startX = 0;
			startY = startY+(h/levels)+1;
			
		}
		
		//aNode[f-1][g-1].rsize = true;
		return rt;
		
	}
	
	public void animateBounds(int l, int p){
		float startX, startY;
		startX = 0;
		startY = 0;
		
		for (int i = 0; i< levels; i++) {
			for (int j = 1; j <= lfmatrix[i]; j++){
				
				if (i == (l-1)){
						if (j < p){
							aNode[i][j-1].getNode().animateToBounds(startX, aNode[l-1][p-1].getNode().getY()+1,(aNode[l-1][p-1].getNode().getX()-1)/(p-1),aNode[l-1][p-1].getNode().getHeight(),0);
							if (graphType == 1)
								aNode[i][j-1].animateLabels();				
							aNode[i][j-1].getNode().animateToBounds(startX, aNode[l-1][p-1].getNode().getY()+1,(aNode[l-1][p-1].getNode().getX()-1)/(p-1),aNode[l-1][p-1].getNode().getHeight(),0);
							aNode[i][j-1].getCamera().animateToBounds(startX, aNode[l-1][p-1].getNode().getY()+1,(aNode[l-1][p-1].getNode().getX()-1)/(p-1),aNode[l-1][p-1].getNode().getHeight(),0);
							aNode[i][j-1].animateGraphs(startX, aNode[l-1][p-1].getNode().getY()+1,(aNode[l-1][p-1].getNode().getX()-1)/(p-1),aNode[l-1][p-1].getNode().getHeight(),0);
							aNode[i][j-1].animatHighLight();
							aNode[i][j-1].getCR().animateCR();
							startX = (float) (startX+(aNode[l-1][p-1].getNode().getX()/(p-1)));
						}
						if (j > p) {
							aNode[i][j-1].getNode().animateToBounds(startX, aNode[l-1][p-1].getNode().getY()+1,(width - aNode[l-1][p-1].getNode().getX()-aNode[l-1][p-1].getNode().getWidth())/(lfmatrix[l-1]-p),aNode[l-1][p-1].getNode().getHeight(),0);
							if (graphType == 1)
								aNode[i][j-1].animateLabels();				
							aNode[i][j-1].getNode().animateToBounds(startX, aNode[l-1][p-1].getNode().getY()+1,(width - aNode[l-1][p-1].getNode().getX()-aNode[l-1][p-1].getNode().getWidth())/(lfmatrix[l-1]-p),aNode[l-1][p-1].getNode().getHeight(),0);
							aNode[i][j-1].getCamera().animateToBounds(startX, aNode[l-1][p-1].getNode().getY()+1,(width - aNode[l-1][p-1].getNode().getX()-aNode[l-1][p-1].getNode().getWidth())/(lfmatrix[l-1]-p),aNode[l-1][p-1].getNode().getHeight(),0);
							aNode[i][j-1].animateGraphs(startX, aNode[l-1][p-1].getNode().getY()+1,(width - aNode[l-1][p-1].getNode().getX()-aNode[l-1][p-1].getNode().getWidth())/(lfmatrix[l-1]-p),aNode[l-1][p-1].getNode().getHeight(),0);
							aNode[i][j-1].animatHighLight();
							aNode[i][j-1].getCR().animateCR();
							startX = startX + 1+ (float) ((width - aNode[l-1][p-1].getNode().getX()-aNode[l-1][p-1].getNode().getWidth())/(lfmatrix[l-1]-p));						
						}
						if (j == p){
							startX = 1+ (float) (aNode[l-1][p-1].getNode().getX()+aNode[l-1][p-1].getNode().getWidth());
						}
								
				}
				
				else {
					if (i < l-1){
						aNode[i][j-1].getNode().animateToBounds(startX, startY, width/lfmatrix[i],(aNode[l-1][p-1].getNode().getY()-1)/(l-1), 0);
						if (graphType == 1)
							aNode[i][j-1].animateLabels();				
						aNode[i][j-1].getNode().animateToBounds(startX, startY, width/lfmatrix[i],(aNode[l-1][p-1].getNode().getY()-1)/(l-1), 0);
						aNode[i][j-1].getCamera().animateToBounds(startX, startY, width/lfmatrix[i],(aNode[l-1][p-1].getNode().getY()-1)/(l-1), 0);
						aNode[i][j-1].animateGraphs(startX, startY, width/lfmatrix[i],(aNode[l-1][p-1].getNode().getY()-1)/(l-1), 0);
						aNode[i][j-1].animatHighLight();
						aNode[i][j-1].getCR().animateCR();
						startX = 1+ (float) (startX+width/lfmatrix[i]);
					}
					if (i > l-1) {
						aNode[i][j-1].getNode().animateToBounds(startX, startY, width/lfmatrix[i],(height-aNode[l-1][p-1].getNode().getY()-aNode[l-1][p-1].getNode().getHeight())/(levels-l), 0);
						if (graphType == 1)
							aNode[i][j-1].animateLabels();				
						aNode[i][j-1].getNode().animateToBounds(startX, startY, width/lfmatrix[i],(height-aNode[l-1][p-1].getNode().getY()-aNode[l-1][p-1].getNode().getHeight())/(levels-l), 0);
						aNode[i][j-1].getCamera().animateToBounds(startX, startY, width/lfmatrix[i],(height-aNode[l-1][p-1].getNode().getY()-aNode[l-1][p-1].getNode().getHeight())/(levels-l), 0);
						aNode[i][j-1].animateGraphs(startX, startY, width/lfmatrix[i],(height-aNode[l-1][p-1].getNode().getY()-aNode[l-1][p-1].getNode().getHeight())/(levels-l), 0);
						aNode[i][j-1].animatHighLight();
						aNode[i][j-1].getCR().animateCR();
						startX = 1+ (float) (startX+width/lfmatrix[i]);
					}
												
				}
				
			}
			
			if (i == l-1){
				startY = 1+ (float) (aNode[l-1][p-1].getNode().getY()+aNode[l-1][p-1].getNode().getHeight());
			}
			else{
				startY = 1+ (float) (startY+aNode[i][0].getNode().getHeight());
			}
			
			startX = 0;
			
			
		}
		
	}
	
	public int findPosition (int l, int s, int a) {
		int p = a;
		for (int i=0; i<a-1; i++){
			if (aNode[l][i].startX > s){
				p = i+1;
				i = a;
			}
		}
		for (int i=a-1; i>=p ; i-- ){
			aNode[l][i] = aNode[l][i-1];
			aNode[l][i].setPosition(i+1);
			
			position[l][i] = position[l][i-1];
		}
		return p;		
	}
	
	public void moveFront(int l, int p){
		
		for (int i=0; i< lfmatrix[l-1]; i++){
			if (i != p-1)
				aNode[l-1][i].getNode().moveToFront();
			
		}
		aNode[l-1][p-1].getNode().moveToFront();
	}

	public void adjPosition(int l, int p){
		
		lfmatrix[l-1]--;
		for (int i = p-1; i <= lfmatrix[l-1]-1; i++){
			aNode[l-1][i] = aNode[l-1][i+1];
			aNode[l-1][i].setPosition(i+1);
		}
		aNode[l-1][lfmatrix[l-1]] = null;
		if (lfmatrix[l-1] == 0)
			levels--;
		
	}
	
	public void remove (myNode r){
		int d = 0;
		//aNode[r.getLevel()-1][r.getPosition()].getNode().removeFromParent();
		//aNode[r.getLevel()-1][r.getPosition()] = null;
		for (int j=0; j<lfmatrix[r.getLevel()-1]; j++){
			if (color[r.getLevel()-2][j] == r.ncolor){
				d = j;
				j = lfmatrix[r.getLevel()-1];
			}
		}
		for (int j=d; j<4; j++){
			color[r.getLevel()-2][j] = color[r.getLevel()-2][j+1];
		}
		color[r.getLevel()-2][4] = r.ncolor;
		
		adjPosition(r.getLevel(), r.getPosition());
		
	}
	
	public void reAnimate(){
		
		float startX =0, startY =0;
		float w = width, h = height;
		for (int i = 0; i< levels; i++) {
			for (int j = 1; j <= lfmatrix[i]; j++){
				aNode[i][j-1].getNode().animateToBounds(startX, startY,w/lfmatrix[i],h/levels,0);
				aNode[i][j-1].animateLabels();
				aNode[i][j-1].getCamera().animateToBounds(startX, startY,w/lfmatrix[i],h/levels,0);
				aNode[i][j-1].animateGraphs(startX, startY,w/lfmatrix[i],h/levels,0);
				aNode[i][j-1].animatHighLight();
				aNode[i][j-1].getCR().animateCR();
							
				startX = startX+(w/lfmatrix[i])+1;
			}
		startX = 0;
		startY = startY+(h/levels)+1;
		
		}
	}
	
	public ArrayList<Float> calculateMaxLayer(int l){
		ArrayList <Float> LayerMax = new ArrayList <Float> ();
		int graphcount = aNode[0][0].graphcount;
		float max;
		for (int i=0; i < graphcount; i++){
			LayerMax.add(i, aNode[l-1][0].findMaximum(i));
		}
		for (int j=1; j< lfmatrix[l-1]; j++){
			for (int i=0; i < graphcount; i++){
				max = aNode[l-1][j].findMaximum(i);
				if (max < LayerMax.get(i))
					LayerMax.add(i, max);
			}
		}
			
		return LayerMax;
	}
	
	public ArrayList<Float> calculateMinLayer(int l){
		ArrayList <Float> LayerMin = new ArrayList <Float> ();
		int graphcount = aNode[0][0].graphcount;
		float min;
		for (int i=0; i < graphcount; i++){
			LayerMin.add(i, aNode[l-1][0].findMinimum(i));
		}
		for (int j=1; j< lfmatrix[l-1]; j++){
			for (int i=0; i < graphcount; i++){
				min = aNode[l-1][j].findMaximum(i);
				if (min > LayerMin.get(i))
					LayerMin.add(i, min);
			}
		}
			
		return LayerMin;
	}
	
	
	public void fillGraph (){
		fill = true;
		System.out.println(""+fill);
		aNode[0][0].fillGraph();
		layer.repaint();
	}
	
	public void notFillGraph (){		
		fill = false;
		aNode[0][0].notFillGraph();
		layer.repaint();
	}
	
	public boolean isFill(){
		System.out.println(""+fill);
		return fill;
	}
	
	

	public void TreeInitialize(DefaultMutableTreeNode t) {
	    
	    //top[0][0] = new DefaultMutableTreeNode(treeObject[0][0]);
	    //createNodes(top);
	    tree = new JTree(t);
	    
	    JScrollPane treeView = new JScrollPane(tree);
	    this.add(treeView);
	    treeView.setBounds(1130, 0, 145, 200);
	    treeView.setVisible(true);
	    
	    tree.getSelectionModel().setSelectionMode
	            (TreeSelectionModel.SINGLE_TREE_SELECTION);

	    //Listen for when the selection changes.
	    tree.addTreeSelectionListener(new TreeSelectionListener (){
	    	public void valueChanged(TreeSelectionEvent e) {
	    		
	    		    DefaultMutableTreeNode node = (DefaultMutableTreeNode)
	    		                       tree.getLastSelectedPathComponent();

	    		    if (node == null)
	    		    //Nothing is selected.	
	    		    return;

	    		    else {
	    		    	selectedNode = ((treeNode) node.getUserObject()).getNode();
	    		    	
	    		    	((treeNode) node.getUserObject()).getNode().displayTable();
	    		    	((treeNode) node.getUserObject()).getNode().addDisplay(datat.atributes, datat.values);
	    		    	
	    		    }
	    		}	    	
	    });
	    
	    tree.addMouseListener(new MouseAdapter() {
	    	
	      	@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
	      		
	      		if (e.getClickCount() == 2){
	      			DefaultMutableTreeNode node = (DefaultMutableTreeNode)
												tree.getLastSelectedPathComponent();
	      			if (node.isLeaf()){
    		    		myNode a = ((treeNode) node.getUserObject()).getNode();
    		    		a.node.setVisible(!a.node.getVisible());
    		    		if (a.node.getVisible()){
    		    			((treeNode) node.getUserObject()).setVisible("V");
    			    		((treeNode) node.getUserObject()).updateName();
    		    		}
    		    		else{
    		    			((treeNode) node.getUserObject()).setVisible("H");
    			    		((treeNode) node.getUserObject()).updateName(); 
    		    		}
    		    		if (a.getLevel() !=1){
    		    			a.parent.highLight[a.phighLight-1].setVisible(a.node.getVisible());
    		    			a.parent.node.repaint();
	      				}
    		    		a.node.repaint();
    		    		tree.repaint();
    		    	}
	      		}
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
	    });
	        
	    
	    tree.addTreeExpansionListener(new TreeExpansionListener(){
	    	public void treeExpanded(TreeExpansionEvent e) {
	    		try{
		    		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		    		((treeNode) node.getUserObject()).setVisible("V");
		    		((treeNode) node.getUserObject()).updateName();
		    		myNode a = ((treeNode) node.getUserObject()).getNode();
		    		a.node.setVisible(true);
		    		if (a.getLevel() !=1){
		    			a.parent.highLight[a.phighLight-1].setVisible(a.node.getVisible());
		    			a.parent.node.repaint();
		    		}
		    		a.node.repaint();
		    		tree.repaint();
		    		tree.updateUI();
	    		}
	    		catch (java.lang.NullPointerException ee){
	    			System.out.println("exception");
	    		}
	    		
	    		
	    	}
	    	
	    	public void treeCollapsed(TreeExpansionEvent e) {
	    		try {
		    		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		    		((treeNode) node.getUserObject()).setVisible("H");
		    		((treeNode) node.getUserObject()).updateName(); 
		    		myNode a = ((treeNode) node.getUserObject()).getNode();
		    		a.node.setVisible(false);
		    		if (a.getLevel() !=1){
		    			a.parent.highLight[a.phighLight-1].setVisible(a.node.getVisible());
		    			a.parent.node.repaint();
		    		}
		    		a.node.repaint();
		    		tree.repaint();
		    		tree.updateUI();
	    		}
	    		catch (java.lang.NullPointerException ee){
	    			System.out.println("Exception");
	    		}
	        }
			

	    });
	
	}
	
	private void createTreeNode(DefaultMutableTreeNode parent, DefaultMutableTreeNode child) {
	    
		//DefaultMutableTreeNode Tempchild = new DefaultMutableTreeNode("A new node");
	    parent.add(child);
	    /*try {
	    TreePath t = new TreePath(parent.getPath());
	    tree.setSelectionPath(t);
	    }
	    catch (Exception e){
	    	System.out.println("error");
	    }*/
	    //child = Tempchild;
	    tree.updateUI();
	    tree.repaint();
	}
	void removeTreeNode(DefaultMutableTreeNode ptree, DefaultMutableTreeNode ctree, int level){
		
		ptree.remove(ctree);
		tree.updateUI();
		tree.repaint();
		
	}
	
		
	public void addTable1(myNode a){
		//new OverlayTable (graphs);
		OverlayTable table = new OverlayTable(a.graphs, pos, this, trackName);	
		JTable jt = new JTable(table);
		jt.setDefaultRenderer(Color.class, new OverlayColorRenderer(true));
		jt.setDefaultEditor(Color.class, new OverlayColorEditor());
		scrollPane1 = new JScrollPane(jt);
		jt.setFillsViewportHeight(true);
		this.add(scrollPane1);
		scrollPane1.setBounds(1130, 454, 145, 100);
		scrollPane1.setVisible(true);
	}
	
	public void addTable2(myNode a){
		//new OverlayTable (graphs);
		TransTable table = new TransTable(a.graphs, this);	
		JTable jt = new JTable(table);
		jt.setDefaultRenderer(Float.class, new OverlayAlphaRenderer());
		jt.setDefaultEditor(Float.class, new OverlayAlphaEditor());
		jt.setDefaultRenderer(Color.class, new OverlayColorRenderer(true));
		jt.setDefaultEditor(Color.class, new OverlayColorEditor());
		scrollPane2 = new JScrollPane(jt);
		jt.setFillsViewportHeight(true);
		this.add(scrollPane2);
		scrollPane2.setBounds(1130, 556, 145, 192);
		scrollPane2.setVisible(true);
	}
	
	public void addTable3 (dataTable table){
		
		if (ds){
			this.remove(dataScrollPane);
			try {
			aNode[selectedLevel-1][selectedPosition-1].removeDisplay();
			}
			catch (Exception e){
				System.out.println("error");
			}
			
		}
		datat = table;
		JTable jt = new JTable(table);
		dataScrollPane = new JScrollPane(jt);
		selectedLevel = table.aNode.getLevel();
		selectedPosition = table.aNode.getPosition();
		ds = true;
		jt.setFillsViewportHeight(true);
		this.add(dataScrollPane);
		dataScrollPane.setBounds(1130, 202, 145, 250);
		dataScrollPane.setVisible(true);		
	}
	
}

