package EulerCycle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;

public class myPoint implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Ellipse2D.Float el = new Ellipse2D.Float();
	private Point p = new Point() ;
	final int r = 15 ; 
	
	private void drawIndex(Graphics2D g,int index,Color colorIndex) {
		g.setColor(colorIndex);
		int stringLen = (int) g.getFontMetrics().getStringBounds(String.valueOf(index),g).getWidth();
		int stringHeight = (int) g.getFontMetrics().getStringBounds(String.valueOf(index),g).getHeight();
		int startX = -stringLen / 2; // stringLen => skewed
		int startY = stringHeight / 2;
		g.drawString(String.valueOf(index),startX + (int) p.x, startY + (int)p.y);
	}
	
	private void drawPoint(Graphics2D g,Color colorPoint) {
		g.setColor(colorPoint);
		g.fill(el);
	}
	
	public void drawResult(Graphics2D g , int index , Color colorPoint , Color colorIndex) {
		drawPoint(g, colorPoint);
		drawIndex(g, index, colorIndex);
	}
	
	public void draw(Graphics2D g , int index , Color colorPoint , Color colorIndex) {
		drawPoint(g, colorPoint);
		drawIndex(g, index, colorIndex);
	}
	
	public myPoint(Ellipse2D.Float el) {
		super();
		setEl(el);
	}


	public Ellipse2D.Float getEl() {
		return el;
	}


	public void setEl(Ellipse2D.Float el) {
		this.el = el;
		this.p.x = (int) (el.x + r) ;
		this.p.y = (int) (el.y +r) ;
	}


	public Point getP() {
		return p;
	}


	public void setP(Point p) {
		this.p = p;
	}
}
