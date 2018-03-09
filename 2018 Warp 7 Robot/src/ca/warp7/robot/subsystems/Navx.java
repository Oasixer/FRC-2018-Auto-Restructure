package ca.warp7.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import ca.warp7.robot.misc.RTS;
import edu.wpi.first.wpilibj.SPI;

public class Navx {
	private AHRS ahrs;
	private RTS updater;
	
	private double last_velocity[] = new double[2];
    private double displacement[] = new double[2];
	
	public Navx(int rate){
		ahrs = new AHRS(SPI.Port.kMXP,(byte) rate);
		
		if(!ahrs.isConnected()) {
			System.out.println("Navx is not Connected");
		}
		else if (ahrs.isCalibrating()) {
			System.out.println("Calibrating Navx");
		}
		ahrs.zeroYaw();
	}

	public Navx(){
		ahrs = new AHRS(SPI.Port.kMXP);
		
		if(!ahrs.isConnected()) {
			System.out.println("Navx is not Connected");
		}
		else if (ahrs.isCalibrating()) {
			System.out.println("Calibrating Navx");
		}
		ahrs.zeroYaw();
	}
	
	private void updateDisplacement() {
        if (ahrs.isMoving()) {
        	double accel_g[] = new double[2];
            double accel_m_s2[] = new double[2];
            double curr_velocity_m_s[] = new double[2];
            double sample_time = 1.0 / updater.getHz();
            accel_g[0] = ahrs.getRawAccelX();
            accel_g[1] = ahrs.getRawAccelY();
            for (int i = 0; i < 2; i++) {
                accel_m_s2[i] = accel_g[i] * 9.80665;
                curr_velocity_m_s[i] = last_velocity[i] + accel_m_s2[i] * sample_time;
                displacement[i] += last_velocity[i] * sample_time + (0.5 * accel_m_s2[i] * sample_time * sample_time);
                last_velocity[i] = curr_velocity_m_s[i];
            }
        } else {
            last_velocity[0] = 0.0;
            last_velocity[1] = 0.0;
        }
     }
	
	public void startUpdateDisplacement(int refesh) {
		updater = new RTS("DisplacementUpdater",refesh);
		Runnable methodCall = () -> updateDisplacement();
		updater.addTask(methodCall);
		updater.start();
	}
	
	public void resetDisplacement() {
        for ( int i = 0; i < 2; i++ ) {
            last_velocity[i] = 0.0;
            displacement[i] = 0.0;
        }        
    }
	
	public double getAngle() {
		return ahrs.getAngle();
	}
	
	public void stopUpdateDisplacement(){
		updater.stop();
	}
	
	public double getDispX() {		
		return displacement[0];
	}
	
	public double getDispY(){
		return displacement[1];
	}
	
	public double getVelX() {
        return last_velocity[0];
    }

    public double getVelY() {
        return last_velocity[1];
    }
	
	public boolean isMoving(){
		return ahrs.isMoving();
	}
	
	public RTS getDisplacementUpdater(){
		return updater;
	}
	
	public void resetAngle() {
		ahrs.reset();
	}

	public double getPitch() {
		return ahrs.getPitch();
	}
}
