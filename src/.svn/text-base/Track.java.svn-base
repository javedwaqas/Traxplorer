

/* ------------------------------------------------------------------
 * Track.java
 * 
 * Created 2008-10-30 by Niklas Elmqvist.
 * ------------------------------------------------------------------
 */

public class Track {

	private double timeStep;
	private Column valueAxis;
	
	public Track(double timeStep, Column valueAxis) { 
		this.timeStep = timeStep;
		this.valueAxis = valueAxis;
	}
	
	public Track(Column timeAxis, Column valueAxis) {
		this.valueAxis = valueAxis;
		resample(timeAxis);
	}
	
	public double getTimeStep() {
		return timeStep;
	}
	
	public void setTimeStep(double timeStep) { 
		this.timeStep = timeStep;
	}
	
	public Column getValueAxis() { 
		return valueAxis;
	}
	
	public void resample(Column timeAxis) {
		
		// Assume that the columns are sorted wrt time!
		for (int i = 0; i < timeAxis.getRowCount(); i++) {
			
		}
		
		// Find the smallest distance---this will be our time step
		timeStep = Double.MAX_VALUE;
		double prev = Double.MAX_VALUE;
		for (int i = 0; i < timeAxis.getRowCount(); i++) { 
			double curr = timeAxis.getRealValueAt(i);
			double diff = Math.abs(prev - curr);
			if (diff == 0) diff = 0.01;
			if (diff < timeStep) timeStep = diff;
			prev = curr;
		}
		
		// 
	}
}