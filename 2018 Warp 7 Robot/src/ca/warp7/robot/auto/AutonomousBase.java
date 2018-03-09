package ca.warp7.robot.auto;

import static ca.warp7.robot.Constants.*;

import com.stormbots.MiniPID;
import static ca.warp7.robot.Constants.CUBE_DISTANCE_B;
import ca.warp7.robot.Robot;
import ca.warp7.robot.misc.DataPool;
import ca.warp7.robot.misc.RTS;
import ca.warp7.robot.subsystems.Drive;
import ca.warp7.robot.subsystems.Intake;
import ca.warp7.robot.subsystems.Lift;
import ca.warp7.robot.subsystems.Limelight;
import ca.warp7.robot.subsystems.Navx;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonomousBase {
	public static DataPool autoPool = new DataPool("auto");
	private Drive drive = Robot.drive;
	private Navx navx = Robot.navx;
	private Limelight limelight = Robot.limelight;
	private Intake intake = Robot.intake;
	private Lift lift = Robot.lift;
	private static final double speed = 1;
	private static int autoCurrentState;
	private PIDTask driveTask = new PIDTask();
	private static double dist_kp,dist_ki,dist_kd,angle_kp,angle_ki,angle_kd,relTurn_kp,relTurn_ki,relTurn_kd;
	
	//TODO: initiate lift somewhere. make another pid tick method
	/*
	RTS liftRTS = new RTS("liftRTS", 8);
	Runnable liftPer = () -> lift.periodic();
	liftRTS.addTask(liftPer);
	liftRTS.start();
	*/
	
	public AutonomousBase(){
		autoCurrentState=0;
		dist_kp=0;
		dist_ki=0;
		dist_kd=0;
		angle_kp=0;
		angle_ki=0;
		angle_kd=0;
		relTurn_kp=0;		//last values used when tuning relTurn were 0.0123,0,0
		relTurn_ki=0;
		relTurn_kd=0;
	}
	
	public void execTick(int autoTargetType, char autoTargetSide, int autoStartPosition) {
		switch (autoTargetType) {
			case (AUTO_TARGET_TYPE_BASELINE): {
				switch (autoStartPosition) { 
					case(AUTO_START_POS_LEFT):baselineTick_leftStart();
						break;
					case(AUTO_START_POS_RIGHT): baselineTick_rightStart();
						break;
					case(AUTO_START_POS_MIDDLE): baselineTick_middleStart();
						break;
					default: System.out.println("error. there will be no auto");
				}
			}break;
			case (AUTO_TARGET_TYPE_SWITCH): {
				switch (autoStartPosition) { 
					case(AUTO_START_POS_LEFT):switchTick_leftStart(autoTargetSide);
						break;
					case(AUTO_START_POS_RIGHT): switchTick_rightStart(autoTargetSide);
						break;
					case(AUTO_START_POS_MIDDLE): switchTick_middleStart(autoTargetSide);
						break;
					default: System.out.println("error. there will be no auto");
				}
			}break;
			case (AUTO_TARGET_TYPE_SCALE): {
				switch (autoStartPosition) { 
					case(AUTO_START_POS_LEFT):scaleTick_leftStart(autoTargetSide);
						break;
					case(AUTO_START_POS_RIGHT): scaleTick_rightStart(autoTargetSide);
						break;
					case(AUTO_START_POS_MIDDLE): scaleTick_middleStart(autoTargetSide);
						break;
					default: System.out.println("error. there will be no auto");
				}
			}break;
			default: System.out.println("error. there will be no auto");
		}
	}
	
	public void baselineTick_leftStart() {
	}
	
	public void baselineTick_rightStart() {
	}
	
	public void baselineTick_middleStart() {
	}
	
	//EXAMPLE AUTO CODE
	public void switchTick_leftStart(char autoTargetSide) {
		if (autoTargetSide == 'L') { //AUTO CODE FOR LEFT SIDE GOES HERE
			switch (autoCurrentState) {
			case (1): {// TODO SWITCH OUT TEMP FOR SETDISTANCE
				driveTask.setDrive(1, getOverallDistance(), navx.getAngle() % 360, dist_kp, dist_ki, dist_kd, angle_kp,
						angle_ki, angle_kd);
				autoCurrentState++;
			}
				break;
			case (2): {
				if (driveTask.executeDriveTick(getOverallDistance(), navx.getAngle() % 360)) { // add values later
					autoCurrentState++;
					driveTask.setRelTurn(90.0, relTurn_kp, relTurn_ki, relTurn_kd);
				}
			}
				break;
			case (3): {
				if (driveTask.executeRelTurnTick(navx.getAngle() % 360)) {
					autoCurrentState++;
				}
			}
			default:
				System.out.println("auto finished");
			}
		}
		else if (autoTargetSide == 'R') {
			//RIGHT SIDE AUTO CODE GOES HERE
		}
	}
	
	public void switchTick_rightStart(char autoTargetSide) {
	}
	
	public void switchTick_middleStart(char autoTargetSide) {
	}
	
	public void scaleTick_leftStart(char autoTargetSide) {
	}
	
	public void scaleTick_rightStart(char autoTargetSide) {
	}
	
	public void scaleTick_middleStart(char autoTargetSide) {
	}
	
	//TODO check encoder direction
		private double getOverallDistance() {
			return (drive.getLeftDistance() + drive.getRightDistance())/2;
		}
	
	/* THE TESTING METHODS CAN BE PUT BACK HERE WHEN WE WANT THEM AGAIN.
	 * LIKE ALIGN INTAKE CUBE AND VISION TEST */
}