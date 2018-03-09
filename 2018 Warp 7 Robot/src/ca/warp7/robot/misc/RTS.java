package ca.warp7.robot.misc;

import java.util.ArrayList;
import java.util.List;

public class RTS {
	private List<Runnable> tasks = new ArrayList<>();
	private int TARGET_HZ = 60;
	private long OPTIMAL_TIME; 
	private boolean running = false;
	private Thread t;
	private String name;
	private int hz = 0;
	private double delta = 0;
	
	public RTS(String name, int TARGET_HZ){
		this.TARGET_HZ = TARGET_HZ; 
		this.OPTIMAL_TIME = 1000000000 / TARGET_HZ;
		this.name = name;
		this.hz = TARGET_HZ;
	}
	
	public RTS(String name){ 
		this.OPTIMAL_TIME = 1000000000 / this.TARGET_HZ;
		this.name = name;
		this.hz = this.TARGET_HZ;
	}
	
	public void start() {
		if (!running){
			running = true;
			t = new Thread(() -> {
				int hzcont=0;
				int lastHzTime=0;
				long lastLoopTime = System.nanoTime();
				while (running){
					long now = System.nanoTime();
					long updateLength = now - lastLoopTime;
					lastLoopTime = now;
					delta = updateLength / ((double)OPTIMAL_TIME);
					
					lastHzTime += updateLength;
					hzcont++;
					  
					if (lastHzTime >= 1000000000)
					{
						lastHzTime = 0;
						hz = hzcont;
						hzcont = 0;
					}
					
					for(Runnable task : tasks)
				        task.run();
					
					try {
						Thread.sleep((lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000 );
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		    t.start();
		}else{
			System.out.println("RTS is already running for object " + this.name);
		}
	}
	
	public void stop(){
		running = false;
	}
	
	public void addTask(Runnable task){
		tasks.add(task);
	}
	
	public int getHz(){
		return hz;
	}
	
	public double getDelta(){
		return delta;
	}
	
	public boolean isRunning(){
		return running;
	}
	
	public int getTargetHz(){
		return TARGET_HZ;
	}
	
	public String getName(){
		return name;
	}
}
