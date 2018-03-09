package ca.warp7.robot;


//TODO Update Constants!
public class ConstantsComp {
	//TODO Update Constants!
	// PWM Pins
	public static final int[] RIGHT_DRIVE_MOTOR_IDS = { 1,3 };
	public static final int[] LEFT_DRIVE_MOTOR_IDS = { 2,8 };
	public static final int[] INTAKE_MOTOR_RIGHT_IDS = { 6 };
	public static final int[] INTAKE_MOTOR_LEFT_IDS = { 4 };
	public static final int[] LIFT_MOTOR_LEFT_IDS = { 0 };
	public static final int[] LIFT_MOTOR_RIGHT_IDS = { 7 };
	public static final int[] CLIMBER_MOTORS_IDS = { 9, 5 };

	//TODO Update Constants!
	// DIG Pins
	public static final int LEFT_DRIVE_ENCODER_A = 0;
	public static final int LEFT_DRIVE_ENCODER_B = 1;
	public static final int RIGHT_DRIVE_ENCODER_A = 2;
	public static final int RIGHT_DRIVE_ENCODER_B = 3;
	
	public static final int HALL_DIO = 6;
	
	//TODO Update Constants!
	// Solenoids (manifold ports)
	public static final int DRIVE_SHIFTER_PORT = 1;
	public static final int INTAKE_PISTONS = 0;
	
	//TODO Update Constants!
	// Compressor
	public static final int COMPRESSOR_PIN = 0;
	
	//TODO Update Constants!
	// Remote IDs
	public static final int DRIVER_ID = 0;
	public static final int OPERATOR_ID = 1;
	
	//TODO Update Constants!
	// Robot dimensions and stuff
	public static double WHEEL_DIAMETER = 6; // inches
	public static double WHEEL_CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;
	public static int DRIVE_TICKS_PER_REV = 256; //256
	public static double DRIVE_INCHES_PER_TICK = WHEEL_CIRCUMFERENCE / DRIVE_TICKS_PER_REV;
    
    public static double RIGHT_DRIFT_OFFSET = 1;
    public static double LEFT_DRIFT_OFFSET = 0.97;
    
    public static double LIFT_HEIGHT = 12100;
    public static double CLIMBER_HEIGHT = 255; // string potentiometer max number
    
    public static final int LIFT_ENCODER_A = 4;
	public static final int LIFT_ENCODER_B = 5;
	
	//input data points here -> https://www.wolframalpha.com/input/?i=linear+fit+%7B1,+3%7D,%7B2,+4%7D
	// y = mx + b
	public static final double CUBE_DISTANCE_M = 0.741402;
	public static final double CUBE_DISTANCE_B = 91.1675;
	
	public static final double SPEED_OFFSET = 0.195;
	public static final double SPEED_OFFSET_CUBE = 0 - SPEED_OFFSET;
	public static final double SPEED_OFFSET_2ND_STAGE = 0;
}
