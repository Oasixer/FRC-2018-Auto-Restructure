package ca.warp7.robot.controls;

import static ca.warp7.robot.controls.Control.DOWN;
import static ca.warp7.robot.controls.Control.PRESSED;
import static ca.warp7.robot.controls.Control.UP;
import static edu.wpi.first.wpilibj.GenericHID.Hand.*;

import ca.warp7.robot.misc.DataPool;

public class SingleRemote extends ControlsBase{
	
	/*
	private double rpm = 4450;
	
	
	public SingleRemote() {
		super();
		
		rpm = 4450;
	}
	*/
	@SuppressWarnings("unused")
	@Override
	public void periodic() {
		if(driver.getTrigger(kLeft) == UP || true){ // are we doing auto stuff
			
			if(driver.getStickButton(kRight) == PRESSED)
				drive.setDrivetrainReversed(!drive.driveReversed());
			
			if(driver.getTrigger(kRight) == DOWN){
				
			}else if(driver.getTrigger(kRight) == UP){
				
			}
			
			
			if(driver.getYButton() == DOWN){
				
			}
				
			
			if(driver.getBackButton() == PRESSED){
				
			}
			
			if(driver.getBButton() == DOWN){
				
			}else if(driver.getDpad(270) == DOWN){
				
			}else if(driver.getBButton() == UP){
				
			}
			
			if (driver.getDpad(90) == DOWN){
				
			}
			 
			// vvvv for testing rpm's only don't use this during an actually comp
			/*
			 if(driver.getDpad(0) == DOWN)
				rpm += 5;
			else if (driver.getDpad(180) == DOWN)
				rpm -= 5;
			else if (driver.getDpad(270) == DOWN)
				rpm = 4425;
			 */
			 
			//drive.tankDrive(driver.getY(kLeft), driver.getY(kRight));
			drive.cheesyDrive(-driver.getX(kRight), driver.getY(kLeft), driver.getBumper(kLeft) == DOWN, driver.getTrigger(kLeft) == DOWN, driver.getBumper(kRight) != DOWN);
		}else{
			try{
				if(DataPool.getBooleanData("vision", "found")){
					drive.tankDrive(DataPool.getDoubleData("vision", "left"), DataPool.getDoubleData("vision", "right"));
				}else{
					drive.cheesyDrive(-driver.getX(kRight), driver.getY(kLeft), driver.getBumper(kLeft) == DOWN, driver.getTrigger(kLeft) == DOWN, driver.getBumper(kRight) != DOWN);
				}
			}catch(Exception e){
				System.out.println("me no work no moar");
			}
		}
	}

}
