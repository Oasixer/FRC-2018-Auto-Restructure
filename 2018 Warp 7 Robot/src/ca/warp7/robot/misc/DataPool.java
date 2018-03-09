package ca.warp7.robot.misc;

import java.util.ArrayList;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableEntry;

public class DataPool{
	
	private static NetworkTable table = NetworkTableInstance.getDefault().getTable("data");
	private static ArrayList<DataPool> allPools = new ArrayList<>();
	private ArrayList<Object> data;
	private ArrayList<String> keys;
	private String name;

	
	public DataPool(String name) {
		allPools.add(this);
		data = new ArrayList<>();
		keys = new ArrayList<>();
		this.name = name;
	}

	public void logData(String key, Object o) {
		boolean alreadyExists = false;
		int index = 0;
		for (String item : keys) {
			if (item.equals(key)) {
				alreadyExists = true;
				break;
			}
			index++;
		}
		if (alreadyExists) {
			data.set(index, o);
		} else {
			data.add(o);
			keys.add(key);
		}
	}

	public void logInt(String key, int i) {
		logData(key, new Integer(i));
	}

	public void logBoolean(String key, boolean b) {
		logData(key, new Boolean(b));
	}

	public void logDouble(String key, double d) {
		logData(key, new Double(d));
	}

	public static void collectAllData() {
		if (allPools.isEmpty())
			return;
		for (DataPool pool : allPools) {
			if (!pool.data.isEmpty()) {
				NetworkTable poolTable = table.getSubTable(pool.name);
				for (int i = 0; i < pool.data.size(); i++) {
					NetworkTableEntry binTable = poolTable.getEntry(pool.keys.get(i));
					binTable.setValue(pool.data.get(i));
				}
			}
		}
	}
	
	public static Object getObjectData(String subTableName, String valueName){
		NetworkTable data = NetworkTableInstance.getDefault().getTable("data").getSubTable(subTableName);
		NetworkTableEntry stuff = data.getEntry(valueName);
		return stuff.getValue().getValue();
	}
	
	public static String getStringData(String subTableName, String valueName){
		return getObjectData(subTableName, valueName).toString();
	}
	
	public static double getDoubleData(String subTableName, String valueName) throws NumberFormatException{
		return Double.parseDouble(getStringData(subTableName, valueName));
	}
	
	public static int getIntData(String subTableName, String valueName) throws NumberFormatException{
		return Integer.parseInt(getStringData(subTableName, valueName));
	}
	
	public static boolean getBooleanData(String subTableName, String valueName){
		return Boolean.parseBoolean(getStringData(subTableName, valueName));
	}
}