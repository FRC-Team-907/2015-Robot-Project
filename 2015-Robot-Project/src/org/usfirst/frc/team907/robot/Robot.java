package org.usfirst.frc.team907.robot;


import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * FRC team 907. 2015 Recycle Rush code. 
 * @author Dinoyan
 */


public class Robot extends SampleRobot {

	RobotDrive robotDrive;
	Joystick stick;
	Joystick upper;
	public Jaguar lifter1Jaguar;
	public Jaguar lifter2Jaguar;


	// Channels for the wheels
	final int frontLeftChannel	= 2;
	final int rearLeftChannel	= 3;
	final int frontRightChannel	= 1;
	final int rearRightChannel	= 0;

	// The channel on the driver station that the joystick is connected to
	final int joystickChannel	= 0;

	public Robot() {
		robotDrive = new RobotDrive(frontLeftChannel, rearLeftChannel, frontRightChannel, rearRightChannel);
		robotDrive.setInvertedMotor(MotorType.kFrontLeft, true);	// invert the left side motors
		robotDrive.setInvertedMotor(MotorType.kRearLeft, true);		// you may need to change or remove this to match your robot
		robotDrive.setExpiration(0.1);

		stick = new Joystick(joystickChannel);
		upper = new Joystick(1);
		lifter1Jaguar = new Jaguar(4);
		lifter2Jaguar = new Jaguar(5);
	}


	/**
	 * Tele-Op mode
	 * Drive the robot using the joystick, and raise/lower the elevator with the other joystick
	 */
	public void operatorControl() {
		robotDrive.setSafetyEnabled(true);
		while (isOperatorControl() && isEnabled()) {

		// Square the values of the joystick, while preserving the sign (positive or negative)
		// This is done to improve driving
		double forwardSpeed = Math.copySign((stick.getRawAxis(0)*stick.getRawAxis(0)),stick.getRawAxis(0));
		double strafeSpeed = Math.copySign((stick.getRawAxis(1)*stick.getRawAxis(1)),stick.getRawAxis(1));
		double turnSpeed = -1*Math.copySign((stick.getRawAxis(4)*stick.getRawAxis(4)), stick.getRawAxis(4));

		SmartDashboard.putNumber("Forward", forwardSpeed);
		SmartDashboard.putNumber("Strafe", strafeSpeed);
		SmartDashboard.putNumber("Turn", turnSpeed);


		// Use the joystick X axis for lateral movement, Y axis for forward movement, and Z axis for rotation.
		// This sample does not use field-oriented drive, so the gyro input is set to zero.            
		robotDrive.mecanumDrive_Cartesian(forwardSpeed, strafeSpeed, turnSpeed, 0.0);


		// Set the speed of the lifter Jaguars to the joystick position
		lifter1Jaguar.set(upper.getRawAxis(1));
		lifter2Jaguar.set(upper.getRawAxis(1));


		Timer.delay(0.005);	// wait 5ms to avoid hogging CPU cycles
		}
	}


	
	
	/**
	 * Autonomous mode
	 * Drive forwards for the specified time at the specified speed, and then stop
	 */
	public void autonomous(){
		// These are likely good values to start with, but you should test them
		// out and adjust the numbers as needed.
		// `Timeout` is how long the robot will drive for, in seconds.
		// `Speed` is the speed at which to drive, in the range [-1.0, 1.0]
		double timeout = 2.5;
		double speed = 0.35;

		robotDrive.setInvertedMotor(MotorType.kFrontLeft, false);
		robotDrive.setInvertedMotor(MotorType.kRearLeft, false);


		// Setup the timer, which will be used to count up to 5 seconds
		Timer m_timer = new Timer();
		m_timer.reset();		// Reset the gyro to 0 sec
		m_timer.start();		// Start counting up on the timer


		// While the robot is in autonomous mode AND the timer is less that 5 seconds...
		while (isAutonomous() && isEnabled() && m_timer.get() < timeout) {

		// Drive forwards with no turning
		robotDrive.drive(speed, 0);
		// Delay for a bit so that we don't overload the CPU
		Timer.delay(0.001);
		}

		// After the 5 seconds is up, stop driving and turn off the timer.
		robotDrive.drive(0, 0);
		m_timer.stop();

		robotDrive.setInvertedMotor(MotorType.kFrontLeft, true);
		robotDrive.setInvertedMotor(MotorType.kRearLeft, true);
	}

}
