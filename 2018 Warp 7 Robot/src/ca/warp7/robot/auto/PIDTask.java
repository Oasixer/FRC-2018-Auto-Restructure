package ca.warp7.robot.auto;
import com.stormbots.MiniPID;
import ca.warp7.robot.Robot;
import ca.warp7.robot.subsystems.Drive;


public class PIDTask {
	private static double distSetpoint;
	private static double turnSetpoint;
	private static MiniPID angleDrivePID;
	private static MiniPID distancePID;
	private static MiniPID relTurnPID;
	private static int ticks;

	private Drive drive = Robot.drive;
	
	public void setDrive (double setDistance, double overallDistance, double angleSetpoint, double dist_kp, double dist_ki, double dist_kd, double angle_kp, double angle_ki, double angle_kd) {
		angleDrivePID=new MiniPID(angle_kp,angle_ki,angle_kd); //need tuning
		angleDrivePID.setSetpoint(angleSetpoint);
		angleDrivePID.setOutputLimits(0.1); //need tuning
		distancePID=new MiniPID(dist_kp,dist_ki,dist_kd); //need tuning
		distancePID.setSetpoint(setDistance);
		distancePID.setOutputLimits(1);
		distSetpoint=setDistance;
		ticks=0;
	}
	
	public boolean executeDriveTick(double dist, double curAngle) {
		double turnSpeed=angleDrivePID.getOutput(curAngle);
		double driveSpeed = distancePID.getOutput(dist);
		if (within(dist,distSetpoint,2)) 
			ticks++;
		if ((within(dist,distSetpoint,2)) && ticks > 200) {
			drive.tankDrive(0,0);
			return true;
		}
		else {
			if (turnSpeed >= 0 )//turn right
				drive.tankDrive(driveSpeed*driveSpeed,driveSpeed*driveSpeed+(turnSpeed-1));
			else //turn left
				drive.tankDrive(driveSpeed*driveSpeed+(turnSpeed-1),driveSpeed*driveSpeed);
			ticks=0;
		}
		return false;
	}
	
	public void setRelTurn (double desiredAngle, double relTurn_kp, double relTurn_ki, double relTurn_kd) {
		relTurnPID=new MiniPID(relTurn_kp,relTurn_ki,relTurn_kd); //need tuning
		relTurnPID.setSetpoint(desiredAngle);
		angleDrivePID.setOutputLimits(1); //need tuning
		ticks=0;
	}
	
	public boolean executeRelTurnTick(double curAngle) {
		double turnSpeed = relTurnPID.getOutput(curAngle);
		if (within(curAngle,turnSetpoint,2)) {
			ticks++;
			if (ticks > 200) {
				drive.tankDrive(0,0);
				return true;
			}
		}
		else {
			drive.tankDrive(turnSpeed,-turnSpeed);
			ticks=0;
		}
		return false;
	}
	
	private boolean within(double angle,double setAngle, double thresh) {
		return (setAngle-thresh)<angle && (setAngle+thresh)>angle;
	}
}
	
