package EulerCycle;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.io.Serializable;

public class myLine implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final int r = 15;

	private int indexPointA , indexPointB ;
	private Line2D.Double l = new Line2D.Double() ;
	
	public myLine(Line2D.Double l , int indexPointA , int indexPointB) {
		this.setIndexPointA(indexPointA);
		this.setIndexPointB(indexPointB);
		this.setL(l);
	}
	
	public void drawLine(Graphics2D g , Point p1 , Point p2,Color colorLine , int size) {
		g.setColor(colorLine);
		g.setStroke(new BasicStroke(size));
//		double theta = Math.atan2(p2.y - p1.y, p2.x - p1.x);
		g.draw(l);
		
//		double x = p2.getX() - r * Math.cos(theta);
//		double y =p2.getY() - r * Math.sin(theta);
		
//		g.drawString("", (int) Math.abs(p1.x+p2.x)/2,(int) Math.abs(p1.y+p2.y)/2 );
	}
	
	public int getIndexPointB() {
		return indexPointB;
	}

	public void setIndexPointB(int indexPointB) {
		this.indexPointB = indexPointB;
	}

	public int getIndexPointA() {
		return indexPointA;
	}

	public void setIndexPointA(int indexPointA) {
		this.indexPointA = indexPointA;
	}

	public Line2D.Double getL() {
		return l;
	}

	public void setL(Line2D.Double l) {
		this.l = l;
	}
}
