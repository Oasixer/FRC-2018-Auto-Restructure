package ca.warp7.robot.misc;

import ca.warp7.robot.Robot;
import ca.warp7.robot.subsystems.Drive;
import ca.warp7.robot.subsystems.Limelight;

public class LimelightPhotosensor {
	protected Limelight limelight;
	protected Drive drive = Robot.drive;
	
	private boolean found = false;
	private int pipeline;
	
	public LimelightPhotosensor(Limelight limelight, int pipeline) {
		this.limelight = limelight;
		this.pipeline = pipeline;
	}
	
	public void update() {
		found = limelight.getPipeline() == pipeline && limelight.foundObject();
	}
	
	public boolean isTriggered() {
		return found;
	}
}
