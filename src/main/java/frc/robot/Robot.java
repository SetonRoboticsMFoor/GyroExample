
package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  // declare motors and robot systems/////////////////////////////////

  private CANSparkMax fLMotor;
  private CANSparkMax rLMotor;
  private CANSparkMax fRMotor;
  private CANSparkMax rRMotor;

  private DifferentialDrive driveTrain;

  // This is the gyro that we use- for details Google Analog Devices ADXRS450
  private ADXRS450_Gyro driveGyro;
  private double heading;
  private double headingSetpoint;
  private double error;
  private double kP;

  ///////////////////////////////// Everything declared in this comment block is for the I term
  // You will need a timer to calculate how long your command has been running 
  // so you can figure out your command time (dt) and error sum. For the I term only
  private Timer driveTimer;

  // this will be used with the error sum to determine your output speed
  private double kI;

  // The error sum is used to increase motor output when the error is so low that 
  // the output won't affect it. As error sum increases it slowly increases 
  private double errorSum;
  // the value belows will be used to find the amount of time the command is running- 
  // dt from the 0 to autonomous video 4 on the I term
  private double commandStartTime;
  private double commandTime;

  /////////////////////////////////////////////End I term declarations//////////
  // OI declarations
  private double xSpeed;
  private double ySpeed;

  private Joystick driveStick;
  private JoystickButton driveStraightButton;

  //end declarations/////////////////////////////////////////////////////
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    //Initialize robot motors and systems///////////////////////////////

    fLMotor = new CANSparkMax(2, MotorType.kBrushless);
    rLMotor =new CANSparkMax(40, MotorType.kBrushless);
    fRMotor =new CANSparkMax(42, MotorType.kBrushless);
    rRMotor =new CANSparkMax(7, MotorType.kBrushless);

    // the rear motors follow the front ones 
    rLMotor.follow(fLMotor);
    rRMotor.follow(fRMotor);

    driveTrain = new DifferentialDrive(fLMotor, fRMotor);
    driveGyro = new ADXRS450_Gyro();
    driveTimer = new Timer();

    //reset gyro to 0 when robot is turned on
    driveGyro.reset();

    // reset the timer on the brain
    driveTimer.reset();
  
    // PID values to drive straight- setpoint is set at 0

    //!!!!!!!!!!!!!!!!!!!  CHANGE ME- use these P and I values in your code !!!!!!!!!!//////
    kP = 0.00;
    kI = 0.00;

    // Heading setpoint will point you straight ahead
    headingSetpoint = 0.0;

    // 


    driveStick = new Joystick(0);
    driveStraightButton = new JoystickButton(driveStick, 1);

    //end initializations ////////////////////////////////////////////////////
  }

  @Override
  public void robotPeriodic() {
    // Get the gyro angle and use it to calculate the error- for debug only
    // These values are within the periodic scope and not accessible by other 
    // methods
    heading = driveGyro.getAngle();
    error = headingSetpoint - heading;

    // Put values on the smart dashboard for debugging
    SmartDashboard.putNumber("Heading", heading);
    SmartDashboard.putNumber("Error", error);
    SmartDashboard.putNumber("Heading Setpoint", headingSetpoint);
    SmartDashboard.putNumber("kP", kP);
    SmartDashboard.putNumber("kI", kI);
  }

  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
  
    System.out.println("Auto selected: " + m_autoSelected);
  }

  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
  
        break;
      case kDefaultAuto:
      default:
    
        break;
    }
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {

    /////////////////////// Joystick drive code- so you can reposition the bot////////
    xSpeed = driveStick.getX() *.5;

    //On our drive train a negative y speed is forward
    //Also with xSpeed- right is positive and left is negative
    ySpeed = driveStick.getY() *.5;

    SmartDashboard.putNumber("X Speed", xSpeed);
    SmartDashboard.putNumber("Y Speed", ySpeed);

    driveTrain.arcadeDrive(xSpeed, ySpeed);

    /////////////////////Working with gyro code//////////////////////////

    // Notes on the gyro:
    // When reset and pointing straight the gyro angle will be 0
    // When turning right it will increase and will not wrap around after 180 or 360,
    //        it will keep incrementing
    // When turnging left it will decrease and will keep decrementing

    // This code will drive straight when button A on the X box controller is pressed

    

    if(driveStraightButton.getAsBoolean()) {
      /////////////////!!!!!!!!!!  Add your code here !!!!!!!!!!!!!!!!!///////////////
      // Get the code working with just the error and P value, then add the i value //


    }


  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

}
