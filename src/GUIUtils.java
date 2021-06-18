

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.Timer;

import java.util.Hashtable;

/**
 * Various GUI utilities.
 * @author Pierre Dragicevic
 */

public class GUIUtils {

	public final static Cursor NO_CURSOR = Toolkit.getDefaultToolkit().createCustomCursor
	(Toolkit.getDefaultToolkit().createImage(
    new MemoryImageSource(16, 16, new int[16 * 16], 0, 16)), new Point(0, 0), "invisibleCursor");

	/**
	 * Centers a window on the primary display
	 */
	public static void centerOnPrimaryScreen(Window toplevel) {
		Dimension screenRes = Toolkit.getDefaultToolkit().getScreenSize();
		toplevel.setLocation((screenRes.width - toplevel.getWidth()) / 2, (screenRes.height - toplevel.getHeight()) / 2);
	}
	
	/**
	 * Centers a window on the primary display
	 */
	public static void centerOnPrimaryScreen(Window toplevel, int newwidth, int newheight) {
		Dimension screenRes = Toolkit.getDefaultToolkit().getScreenSize();
		toplevel.setBounds((screenRes.width - newwidth) / 2, (screenRes.height - newheight) / 2, newwidth, newheight);
	}
	
	/**
	 * Manages a waiting cursor. Make sure you always pass the same window argument.
	 */
	static int computationCount = 0;
	public static void beginLongComputation(Window window) {
		computationCount++;
		if (computationCount == 1) {
			window.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		}
	}
	public static void endLongComputation(Window window) {
		computationCount--;
		if (computationCount == 0) {
			window.setCursor(Cursor.getDefaultCursor());
			window.toFront();
		}
	}	
	
	/**
	 * Mixes two colors.
	 */
	public static Color mix(Color c0, Color c1, float amount) {
		float r0 = c0.getRed() / 255f;
		float g0 = c0.getGreen() / 255f;
		float b0 = c0.getBlue() / 255f;
		float a0 = c0.getAlpha() / 255f;
		float r1 = c1.getRed() / 255f;
		float g1 = c1.getGreen() / 255f;
		float b1 = c1.getBlue() / 255f;
		float a1 = c1.getAlpha() / 255f;
		return new Color(
			bound01(r0 + (r1-r0) * amount),
			bound01(g0 + (g1-g0) * amount),
			bound01(b0 + (b1-b0) * amount),
			bound01(a0 + (a1-a0) * amount));
	}
	private static float bound01(float x) {
		if (x < 0) x = 0;
		if (x > 1) x = 1;
		return x;
	}
	
	/**
	 * Clears an image (makes it totally transparent)
	 */
	public static Image createTransparentImage(int width, int height) {
		BufferedImage im = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2D = im.createGraphics();
		g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f));
		Rectangle2D.Double rect = new Rectangle2D.Double(0, 0, width, height); 
		g2D.fill(rect);
		return im;
	}
	
	// The following adds a separate & fast auto-repeat feature to the
	// keyboard event handling mechanism, and removes the default
	// auto-repeat.
	
	private static Vector<AdvancedKeyListenerFilter> listeners = new Vector<AdvancedKeyListenerFilter>();
	
	public static interface AdvancedKeyListener extends KeyListener {
		public void keyRepeated(KeyEvent e);
	}
	
	private static class AdvancedKeyListenerFilter implements KeyListener {
		AdvancedKeyListener delegate;
		boolean autorepeat;
		
		final Hashtable<Integer, KeyEvent> keysDown = new java.util.Hashtable<Integer, KeyEvent>();
		private Timer keyRepeatTimer = new Timer(20, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				repeatKeys();
			}
		});
		
		AdvancedKeyListenerFilter(AdvancedKeyListener destination, boolean autorepeat) {
			this.autorepeat = autorepeat;
			this.delegate = destination;
		}
		
		boolean repeatKeys() {
			for (Enumeration<Integer> e = keysDown.keys(); e.hasMoreElements();) {
				delegate.keyRepeated(keysDown.get(e.nextElement()));
			}
			return keysDown.size() > 0;
		}

		public void keyPressed(KeyEvent e) {
			// 
			if (keysDown.containsKey(e.getKeyCode()))
				return;
			keysDown.put(e.getKeyCode(), e);
			
			// 
			delegate.keyPressed(e);
			
			// 
			if (keysDown.size() == 1 && autorepeat) {
				keyRepeatTimer.start();
			}
		}

		public void keyReleased(KeyEvent e) {
			//
			if (!keysDown.containsKey(e.getKeyCode()))
				return;
			keysDown.remove(e.getKeyCode());
			
			//
			if (keysDown.size() == 0 && autorepeat)
				keyRepeatTimer.stop();
			
			//
			delegate.keyReleased(e);
		}

		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			delegate.keyTyped(arg0);
		}
	}
	
	public static void addAdvancedKeyListener(Component c, AdvancedKeyListener l, boolean autorepeat) {
		AdvancedKeyListenerFilter listener = new AdvancedKeyListenerFilter(l, autorepeat);
		c.addKeyListener(listener);
		listeners.add(listener);
	}
	
	public static boolean repeatKeys() {
		int N = listeners.size();
		boolean needsRepeat = false;
		for (int i=0; i<N; i++)
			needsRepeat |= listeners.elementAt(i).repeatKeys();
		return needsRepeat;
	}
	
}
