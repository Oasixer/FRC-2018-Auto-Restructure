package ca.warp7.robot.auto;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.interpolation.AkimaSplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

public class Path{
	public Point[] points;
	
	private static final int MAX_POINTS = 5;
	private static final AkimaSplineInterpolator splineInterp = new AkimaSplineInterpolator();
	
	private String sides;
	private double[] groupX;
	private double[] groupY;
	private PolynomialSplineFunction splineX;
	private PolynomialSplineFunction splineY;
	private double[] numPoints;
	private UnivariateFunction xderiv;
	private UnivariateFunction yderiv;
	private double totalDistance;
	private int numberOfPoints;
	
	public Path(JSONObject json) {
		JSONArray data = (JSONArray) json.get("data");
		sides = (String) json.get("sides");
		totalDistance = (double) json.get("totalDistance");
		
		List<Long> groupXtemp = new ArrayList<>();
		List<Long> groupYtemp = new ArrayList<>();
		points = new Point[data.size()+MAX_POINTS];
		for(int i=0; i<data.size();i++) {
			points[i] = new Point((JSONObject)data.get(i));
			groupXtemp.add(points[i].x);
			groupYtemp.add(points[i].y);
		}
		
		for(int i=data.size(); i<data.size()+MAX_POINTS;i++) {
			points[i] = points[data.size()-1];
			groupXtemp.add(points[i].x);
			groupYtemp.add(points[i].y);
			points[i].startMethods = new Method[0];
			points[i].endMethods = new Method[0];
			points[i].scaledMethods = new Method[0];
		}
		groupX = toDoubleArray(groupXtemp);
		groupY = toDoubleArray(groupYtemp);
		numberOfPoints = data.size();
		numPoints = new double[data.size()+MAX_POINTS];
	    for (int i = 0; i < data.size()+MAX_POINTS; ++i)
	    	numPoints[i] = i;
	    
	}
	
	private double[] toDoubleArray(List<Long> list) {
		return list.stream().mapToDouble(i->i).toArray();
	}
	
	public void calculateSpline() {
		splineX = splineInterp.interpolate(numPoints,groupX);
		splineY = splineInterp.interpolate(numPoints,groupY);
		xderiv = splineX.derivative();
		yderiv = splineY.derivative();
	}
	
	public double[] derivative(double val) {
		double[] point = new double[2];
		point[0] = xderiv.value(val);
		point[1] = yderiv.value(val);
		return point;
	}
	
	public double[] value(double val) {
		double[] point = new double[2];
		point[0] = splineX.value(val);
		point[1] = splineY.value(val);
		return point;
	}
	
	public double getTotalDistance() {
		return totalDistance;
	}
	
	public String getSides() {
		return sides;
	}
	
	public int getNumberOfPoints() {
		return numberOfPoints;
	}
}

class Point {	
	public long x;
	public long y;
	public Method[] startMethods;
	public Method[] endMethods;
	public Method[] scaledMethods;
	public double distance;
	public boolean slowStop;
	
	public Point(JSONObject json){
		JSONArray a = (JSONArray) json.get("point");
		x = (long) a.get(0);
		y = (long) a.get(1);
		distance = (double) json.get("distance");
		slowStop = (boolean) json.get("slowStop");
		startMethods = methodLoop(json,"startMethods");
		endMethods = methodLoop(json,"endMethods");
		scaledMethods = methodLoop(json,"scaledMethods");
	}
	
	private Method[] methodLoop(JSONObject json, String key) {
		JSONArray methodArr = (JSONArray) json.get(key);
		Method[] methods = new Method[methodArr.size()];
		for(int i=0; i<methodArr.size();i++)
			methods[i] = new Method((JSONObject)methodArr.get(i));
		return methods;
	}
}

class Method{
	public String name;
	public JSONArray args;
	public Method(JSONObject json) {
		name = (String) json.get("name");
		args = (JSONArray) json.get("args");
	}
}
