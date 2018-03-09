package ca.warp7.robot.controls;

import static ca.warp7.robot.Constants.DRIVER_ID;

import static ca.warp7.robot.Constants.OPERATOR_ID;

import ca.warp7.robot.Robot;
import ca.warp7.robot.misc.DataPool;
import ca.warp7.robot.subsystems.Climber;
import ca.warp7.robot.subsystems.Drive;
import edu.wpi.first.wpilibj.Timer;

import ca.warp7.robot.subsystems.Intake;
import ca.warp7.robot.subsystems.Lift;

public abstract class ControlsBase {

	public static DataPool controlPool = new DataPool("controls");
	
	protected XboxControllerPlus driver;
	protected XboxControllerPlus operator;
	protected Climber climber;
	protected Drive drive;
	protected Intake intake;
	protected Lift lift;
	
	public ControlsBase(){
		driver = new XboxControllerPlus(DRIVER_ID);
		operator = new XboxControllerPlus(OPERATOR_ID);
		
		lift = Robot.lift;
		intake = Robot.intake;
		climber = Robot.climber;
		drive = Robot.drive;
	}
	
	abstract public void periodic();
	
	protected double timer = -1;
	protected boolean timePassed(double seconds) {
		if(timer <= 0)
			timer = Timer.getFPGATimestamp();
		
		if(Timer.getFPGATimestamp() - timer >= seconds){
			timer = -1;
			return true;
		}else{
			return false;
		}
	}
    
}