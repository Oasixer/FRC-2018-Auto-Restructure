package ca.warp7.robot.controls;

import static ca.warp7.robot.controls.Control.DOWN;
import static ca.warp7.robot.controls.Control.PRESSED;
import static ca.warp7.robot.controls.Control.UP;
import static edu.wpi.first.wpilibj.GenericHID.Hand.kLeft;
import static edu.wpi.first.wpilibj.GenericHID.Hand.kRight;

import ca.warp7.robot.misc.DataPool;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;

public class DualRemote extends ControlsBase {
	
	@Override
	public void periodic() {
		if(driver.getTrigger(kRight) == DOWN){//intake
			intake.rampSpeed(0.75);
		}else if (driver.getTrigger(kLeft) == DOWN) {//out take
			intake.rampSpeed(-0.5);
		}else {
			intake.rampSpeed(0);
		}
		
		if(driver.getStickButton(kRight) == PRESSED)
			drive.setDrivetrainReversed(!drive.driveReversed());
		
		if (driver.getAButton() == PRESSED)
			intake.pistonToggle();
		
		if(operator.getBumper(kRight) == DOWN){
			
		}else if(operator.getBumper(kLeft) == DOWN){
			
		}else{
			
		}
		
		if(operator.getBackButton() == PRESSED){
			
		}
		
		if(operator.getBButton() == DOWN){
			
		}
		else if(operator.getTrigger(kLeft) == DOWN){
			
		}
		else if(operator.getTrigger(kLeft) == UP){
			
		}
		
		if (operator.getDpad(90) == DOWN){
			
		}
		
		if(operator.getAButton() == DOWN)
			lift.setLoc(operator.getY(kLeft));
		
		if(operator.getBButton() == DOWN)
			climber.setSpeed(operator.getY(kRight)*-1);
		
		 //drive.tankDrive(driver.getY(Hand.kLeft), driver.getY(Hand.kLeft));
		drive.cheesyDrive(-driver.getX(kRight), driver.getY(kLeft), driver.getBumper(kLeft) == DOWN, false, driver.getBumper(kRight) != DOWN);
	}
}
