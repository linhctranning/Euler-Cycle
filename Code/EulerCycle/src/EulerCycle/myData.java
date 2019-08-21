package EulerCycle;

import java.io.Serializable;
import java.util.ArrayList;

public class myData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<myPoint> arrmyPoint ;
	private ArrayList<myLine> arrmyLine;
	
	final int r = 15 , r2 = 2* r ; //radius , diameter
	
	public myData() {
		arrmyLine = new ArrayList<myLine>();
		arrmyPoint = new ArrayList<myPoint>();
	}

	public ArrayList<myPoint> getArrmyPoint() {
		return arrmyPoint;
	}

	public void setArrmyPoint(ArrayList<myPoint> arrmyPoint) {
		this.arrmyPoint = arrmyPoint;
	}

	public ArrayList<myLine> getArrmyLine() {
		return arrmyLine;
	}

	public void setArrmyLine(ArrayList<myLine> arrmyLine) {
		this.arrmyLine = arrmyLine;
	}
}
