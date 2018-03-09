package ca.warp7.robot.auto;

import org.json.simple.JSONArray;

public class Mapper {
	public double scaledLocation = 0;
	public double currentDistance = 0;
	public double speed = 0;
	
	public Runnable mapMethod(Method method){
		String name = method.name;
		JSONArray args = method.args;
		switch(name){
			case "print":
				return () -> print(
							(String) args.get(0), 
							(String) args.get(1)
						);
			}
		
		return () -> waXIcXTiT9vzoomsDDM2YqAaoVhYC0w3kZczE33yscsxffdBJfv6lYYf2skiaUeN(); //this is just a blank method
	}
	private void waXIcXTiT9vzoomsDDM2YqAaoVhYC0w3kZczE33yscsxffdBJfv6lYYf2skiaUeN(){ //this is just a blank method
	}
	
	private void print(String one, String two){
		//System.out.println(one+" "+two);
	}
}
