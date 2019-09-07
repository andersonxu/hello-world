package pg09.components;

import lejos.hardware.BrickFinder;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.MovePilot;

/**
 * The Pilot class contains methods to initial a pilot and to control robot movements. 
 * 
 * @author sunruoxi
 *
 */
public class Pilot {
	private MovePilot pilot;
	private OdometryPoseProvider opp;
	private EV3MediumRegulatedMotor mediumMotor;
	private int direction;
	float[] sampleOpp;
	float step;
	int targetAngle;
	
	/**
	 * Constructor for a two wheel differential MovePilot
	 * @param diameter the diameter of wheels
	 * @param offset the half width between two wheels
	 * @param left the port name of left motor
	 * @param right the port name of right motor
	 */
	public Pilot(double diameter, double offset, String left, String right) { 
		EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(BrickFinder.getDefault().getPort(left));
		EV3LargeRegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(BrickFinder.getDefault().getPort(right));
		
		Wheel[] wheels = {WheeledChassis.modelWheel(LEFT_MOTOR, diameter).offset(-offset), 
				WheeledChassis.modelWheel(RIGHT_MOTOR, diameter).offset(offset)};
		Chassis chassis = new WheeledChassis(wheels, WheeledChassis.TYPE_DIFFERENTIAL);
		
		this.pilot = new MovePilot(chassis);
		this.opp = new OdometryPoseProvider(pilot);
		this.sampleOpp = new float[opp.sampleSize()];
		this.targetAngle = 0;
	}
	
	public void rotate(int deg, Sensor sensor) {
		this.getPilot().rotate(deg);
		this.targetAngle += deg;
		Brick.delay(2000);
		this.getPilot().rotate(targetAngle - sensor.getAngle());
		Brick.delay(1000);
	}
	
	public float getStep() {
		return step;
	}

	public void setStep(float step) {
		this.step = step;
	}

	public MovePilot getPilot() {
		return this.pilot;
	}
	
	public EV3MediumRegulatedMotor getMediumMotor() {
		return this.mediumMotor;
	}
	
	/**
	 * initial the medium motor
	 * @param port the port name of mediium motor
	 */
	public void setMediumMotor(String port) {
		this.mediumMotor = new EV3MediumRegulatedMotor(BrickFinder.getDefault().getPort(port)); 
	}
	
	public OdometryPoseProvider getOpp() {
		return opp;
	}

	/**
	 * set the speed of robot
	 * @param linearSpeed linear speed
	 * @param angularSpeed angular speed
	 */
	public void setSpeed(float linearSpeed, float angularSpeed) {
		this.pilot.setLinearSpeed(linearSpeed);
		this.pilot.setAngularSpeed(angularSpeed);
	}
	
	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	public int getDirection() {
		return this.direction;
	}
	
	public String getDirectionString() {
		switch(direction) {
		case 0: return "RIGHT";
		case 1: return "DOWN";
		case 2: return "LEFT";
		case 3: return "UP";
		}
		
		return null;
	}
	
	public int getTargetAngle() {
		return targetAngle;
	}

	public void setTargetAngle(int targetAngle) {
		this.targetAngle = targetAngle;
	}

	/**
	 * read the location data and return in an array, using a OdometryPoseProvider
	 * @return the location data: x, y, and direction
	 */
	public float[] readLocation() {
		this.opp.fetchSample(sampleOpp, 0);
		return sampleOpp;
	}
	
	public void close() {
		this.mediumMotor.close();
	}
	
}
