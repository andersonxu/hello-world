package demo.simpleDemo;

import lejos.hardware.Button;

public class HelloWorld {

	public static void main(String[] args) {
		Button.setKeyClickVolume(0);
		System.out.println("Hello World!");
        Button.waitForAnyPress();
	}

}
