package com.bram.concat.tom_dotmatrices_associations.pattern.grid;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

import javax.swing.Icon;
import javax.swing.JButton;

import com.bram.concat.tom_dotmatrices_associations.Options;

/**
 * A cell of the 4x4 grid that can contain a dot.
 * @author Bram Van Rensbergen (http://fac.ppw.kuleuven.be/lep/concat/bram/)
 */
@SuppressWarnings("serial")
public class Cell extends JButton {
	private DotIcon dot;
	public boolean containsDot;
	
	Cell(boolean containsDot) {
		dot = new DotIcon((int) (Options.GRID_PIXELSIZE * 0.4));
		setBackground(Color.white);
		setFocusable(false);
		setEnabled(false);		
		if (containsDot) {
			addDot();
		} else {
			removeDot();
		}
	}
	
	public void addDot() {
		this.containsDot = true;
		setIcon(dot);
	}
	
	public void removeDot() {
		this.containsDot = false;
		setIcon(null);
	}

	private class DotIcon implements Icon {
		private int size;
		
		private DotIcon(int size) {
			this.size = size;
			setFocusable(false);
		}
		
		@Override
		public int getIconHeight() {
			return size;
		}

		@Override
		public int getIconWidth() {
			return size;
		}

		@Override
		public void paintIcon(Component c, Graphics g, int w, int h) {					    
			Graphics2D g2d = (Graphics2D)g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			Ellipse2D.Double circle = new Ellipse2D.Double(Options.GRID_PIXELSIZE / 2 - size / 2, Options.GRID_PIXELSIZE / 2 - size / 2, size, size);
			g2d.setColor(Color.black);
			g2d.fill(circle);						
		}						
	}	
}
