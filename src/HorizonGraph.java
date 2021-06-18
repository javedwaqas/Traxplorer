import java.awt.Color;
import java.util.ArrayList;

import edu.umd.cs.piccolo.nodes.PPath;


public class HorizonGraph {

	public void drawGraph(float x[], float y[], ArrayList<PPath> graph){
		
		PPath g1, g2;
		float level;
		float [] y1 = new float [x.length];
		float [] y2 = new float [x.length];
		level = findLevel(y, 2);
		for (int i=0; i<x.length; i++){
			if (Math.abs(y[i])<level){
				y1[i] = y[i];
				y2[i] = 0;
			}
			else {
				y1[i] = -1*level;
				y2[i] = y[i] + level; //Here adding the level due to negative values
			}
		}
		
		g1 = PPath.createPolyline(x, y1);
		g2 = PPath.createPolyline(x, y2);
		g2.setPaint(Color.darkGray);
		g2.setVisible(true);
		g2.setStrokePaint(Color.darkGray);
		g1.setPaint(Color.lightGray);
		g1.setVisible(true);
		g1.setStrokePaint(Color.lightGray);
		
		graph.add(g1);
		graph.add(g2);		
			
	}
	
	public static float findLevel(float a[], int bands){
		float max = 0, min = 0;
		max = Math.abs(a[0]);
		min = Math.abs(a[0]);
		for (int i=1; i < a.length; i++){
			if (max < Math.abs(a[i])){
				max = Math.abs(a[i]);
			}
		}
		for (int i=1; i < a.length; i++){
			if (min > Math.abs(a[i])){
				min = Math.abs(a[i]);
			}
		}
		return (min+max)/bands;
				
	}
}
