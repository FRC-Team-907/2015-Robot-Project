
package org.usfirst.frc.team907.robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ShapeMode;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.vision.AxisCamera;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
 /**
 * FRC team 907. 2015 Recycle Rush code. 
 * @author Dinoyan
 */
public class Robot extends IterativeRobot {
	public RobotDrive myRobot;
	public Joystick driveStick;
	public Joystick shootStick;
	public Talon example;
	public Solenoid piston1;
	public Solenoid piston2;
	public Compressor mainCompressor;
	public int autoLoopCounter;
	public int session;
	Image frame;
	AxisCamera camera;
	
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
   
    myRobot = new RobotDrive(0,1,2,3);
    driveStick = new Joystick(1);
    shootStick = new Joystick(2);
    example = new Talon(4);
    mainCompressor  = new Compressor(0);
    piston1 = new Solenoid(1);
    piston2 = new Solenoid(2);
    	
    // open the camera at the IP address assigned. This is the IP address that the camera
    // can be accessed through the web interface.
    camera = new AxisCamera("10.9.7.11");
    frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    if(autoLoopCounter < 100)    //Loop for 2 seconds
    {	
    	myRobot.drive(-0.5, 0.0);  // drive forward half speed
    	autoLoopCounter++;
    }
    if(autoLoopCounter <10)
    {
    	myRobot.drive(-0.1, 0.0);
    }
    else  {
    	myRobot.drive(0.0, 0.0);  // Stop
    	}
    
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    //Drive Train
    myRobot.arcadeDrive(driveStick);
    	
    	
    // UPPER MECHANISAM
    //--------------------------------------------------------------------------------------  
    // SPEED CONTROLLER EX
    if(shootStick.getRawButton(1)){
    	example.set(0.50);
    }else if(shootStick.getRawButton(2)){
    	example.set(1);
    }
    else{
    	example.set(0.0);
    }
    //-------------------------------------------------------------------------------------- 
    	
    //-------------------------------------------------------------------------------------- 
    // SOLENOID EX
    if(shootStick.getRawButton(3)){
    	piston1.set(true);
    	piston2.set(false);	
    } 
    else if(shootStick.getRawButton(4)){
    	piston1.set(false);
    	piston2.set(true);
    }
    //-------------------------------------------------------------------------------------- 
    
    //-------------------------------------------------------------------------------------- 
    //AXIS CAMERA
    NIVision.Rect rect = new NIVision.Rect(10, 10, 100, 100);
    camera.getImage(frame);
    NIVision.imaqDrawShapeOnImage(frame, frame, rect,
            DrawMode.DRAW_VALUE, ShapeMode.SHAPE_OVAL, 0.0f);
    CameraServer.getInstance().setImage(frame);
    //-------------------------------------------------------------------------------------- 	
} 
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
