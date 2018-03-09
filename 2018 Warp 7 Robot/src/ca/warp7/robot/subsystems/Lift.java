package ca.warp7.robot.subsystems;

import static ca.warp7.robot.Constants.LIFT_MOTOR_RIGHT_IDS;
import static ca.warp7.robot.Constants.LIFT_ENCODER_A;
import static ca.warp7.robot.Constants.LIFT_ENCODER_B;
import static ca.warp7.robot.Constants.LIFT_MOTOR_LEFT_IDS;
import static ca.warp7.robot.Constants.LIFT_HEIGHT;
import static ca.warp7.robot.Constants.HALL_DIO;
import static ca.warp7.robot.Constants.SPEED_OFFSET;
import static ca.warp7.robot.Constants.SPEED_OFFSET_CUBE;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.stormbots.MiniPID;

import ca.warp7.robot.Robot;
import ca.warp7.robot.misc.MotorGroup;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;

public class Lift {
	private MotorGroup LiftMotorRight;
	private MotorGroup LiftMotorLeft;
	private Encoder liftEncoder;
	private DigitalInput liftHallaffect;
	private MiniPID liftPID;
	
	private Intake intake = Robot.intake;
	
	public Lift(){
		LiftMotorLeft = new MotorGroup(LIFT_MOTOR_LEFT_IDS, WPI_VictorSPX.class);
		LiftMotorRight = new MotorGroup(LIFT_MOTOR_RIGHT_IDS, WPI_VictorSPX.class);
		LiftMotorLeft.setInverted(true);
		
		liftEncoder =  new Encoder(LIFT_ENCODER_A, LIFT_ENCODER_B, false, EncodingType.k4X);
		liftEncoder.setDistancePerPulse(1);
		liftHallaffect = new DigitalInput(HALL_DIO);
		zeroEncoder();
		liftPID = new MiniPID(2.5,0,0);
		liftPID.setOutputLimits(-0.5,1);
	}
	
	private double ramp = 0;
	private final double rampSpeed = 6;
	public void setSpeed(double speed){
		LiftMotorLeft.set(speed);
		LiftMotorRight.set(speed);
	}
	
	public void rampSpeed(double speed){
		ramp += (speed - ramp)/rampSpeed;
		
		if (false && speed > 0)//is max limit hit
			ramp = 0;
		LiftMotorLeft.set(ramp);
		LiftMotorRight.set(ramp);
	}
	
	public void setLoc(double scale) {
		double target = Math.abs(scale);
		SmartDashboard.putNumber("loc dfliusafusd", target);
		liftPID.setSetpoint(target);
	}
	
	public void periodic(){
		if (isBottom()) //zero switch is active zero encoder
			zeroEncoder();
		double speed = liftPID.getOutput(getEncoderVal()/LIFT_HEIGHT);
		
		//if (intake.hasCube())
			//rampSpeed(speed+SPEED_OFFSET_CUBE);
		//else
			//rampSpeed(speed+SPEED_OFFSET);
		
		rampSpeed(speed);
	}
	
	public double getEncoderVal() {
		return Math.abs(liftEncoder.getDistance()); 
	}
	
	public void zeroEncoder() {
		liftEncoder.reset();
	}
	
	public boolean isBottom(){
		return !(boolean) liftHallaffect.get();//is lift at bottom
	}
}
