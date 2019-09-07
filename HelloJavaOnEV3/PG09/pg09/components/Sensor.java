package pg09.components;

import lejos.hardware.BrickFinder;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

public class Sensor {
	public static final int SONIC = 0, COLOR = 1, GYRO = 2;
	private EV3UltrasonicSensor sonicSensor;
	private EV3ColorSensor colorSensor; 
	private EV3GyroSensor gyroSensor;
	private SampleProvider spSonic, spColor, spGyro;
	float[] sampleSonic, sampleColor, sampleGyro;
	float distance;
	String color;
	float angle;
	
	public Sensor(String sonicSensorPort, String colorSensorPort){
		this.sonicSensor = new EV3UltrasonicSensor(BrickFinder.getDefault().getPort(sonicSensorPort));
		this.spSonic = sonicSensor.getDistanceMode();
		this.sampleSonic = new float[spSonic.sampleSize()];
		
		this.colorSensor = new EV3ColorSensor(BrickFinder.getDefault().getPort(colorSensorPort));
		this.spColor = colorSensor.getColorIDMode();
		this.sampleColor = new float[spColor.sampleSize()];
	}
	
	public Sensor(String sonicSensorPort, String colorSensorPort, String gyroSensorPort) {
		this.sonicSensor = new EV3UltrasonicSensor(BrickFinder.getDefault().getPort(sonicSensorPort));
		this.spSonic = sonicSensor.getDistanceMode();
		this.sampleSonic = new float[spSonic.sampleSize()];
		
		this.colorSensor = new EV3ColorSensor(BrickFinder.getDefault().getPort(colorSensorPort));
		this.spColor = colorSensor.getColorIDMode();
		this.sampleColor = new float[spColor.sampleSize()];
		
		this.gyroSensor = new EV3GyroSensor(BrickFinder.getDefault().getPort(gyroSensorPort));
		this.spGyro = gyroSensor.getAngleMode();
		this.sampleGyro = new float[spGyro.sampleSize()];
	}
	
	public Sensor(String sensorPort, int type) {
		switch(type) {
		case 0:
			this.sonicSensor = new EV3UltrasonicSensor(BrickFinder.getDefault().getPort(sensorPort));
			this.spSonic = sonicSensor.getDistanceMode();
			this.sampleSonic = new float[spSonic.sampleSize()];
			return;
		case 1:
			this.colorSensor = new EV3ColorSensor(BrickFinder.getDefault().getPort(sensorPort));
			this.spColor = colorSensor.getColorIDMode();
			this.sampleColor = new float[spColor.sampleSize()];
			return;
		case 2:
			this.gyroSensor = new EV3GyroSensor(BrickFinder.getDefault().getPort(sensorPort));
			this.spGyro = gyroSensor.getAngleMode();
			this.sampleGyro = new float[spGyro.sampleSize()];
		}
	}
	
	public float readUltraSensor() {
		spSonic.fetchSample(sampleSonic, 0);
		if(sampleSonic[0] == Double.POSITIVE_INFINITY) {
			this.distance = 1000;
		}
		else
			this.distance = sampleSonic[0]*100;
		
		return sampleSonic[0]*100;
	}
	
	public int readColorID() {
		spColor.fetchSample(sampleColor, 0);
		return (int)sampleColor[0];
	} 
	
	public String readColor() {
		spColor.fetchSample(sampleColor, 0);
		switch((int)sampleColor[0]) {
		case 0: this.color = "RED"; return "RED";
		case 2: this.color = "BLUE"; return "BLUE";
		case 6: this.color = "WHITE"; return "WHITE";
		case 7: this.color = "BLACK"; return "BLACK";
		default: this.color = "";
//		case 0: return "RED";
//		case 2: return "BLUE";
//		case 6: return "WHITE";
//		case 7: return "BLACK";
//		default: this.color = "";
		}
		return "";
	}

	public float readColorValue() {
		spColor.fetchSample(sampleColor, 0);
		return sampleColor[0];
	}
	
	public float readAngle() {
		spGyro.fetchSample(sampleGyro, 0);
		this.angle = sampleGyro[0];
		
		return sampleGyro[0];
	}
	
	public void resetGyro() {
		this.gyroSensor.reset();
	}
	
	public void close() {
		if(this.sonicSensor != null) {
			this.sonicSensor.close();
		}
		if(this.colorSensor != null) {
			this.colorSensor.close();
		}
	}

	public float getDistance() {
		return distance;
	}

	public String getColor() {
		return color;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}
	
}
