// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.lang.ModuleLayer.Controller;
import java.time.Instant;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.DriveSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  private DriveSubsystem DRIVE_SUBSYSTEM=new DriveSubsystem();
  //private Joystick CONTROLLER=new Joystick(0);
  private XboxController CONTROLLER = new XboxController(0);
  private Spark climberMotor = new Spark(3);
  private Spark intakeMotor = new Spark(5);
  private Spark shooterMotor = new Spark(6);
  private Servo shooterServo = new Servo(11);


  //private XboxController CONTROLLER=new XboxController(0);
 
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    //twostick
/*    DRIVE_SUBSYSTEM.setDefaultCommand( new RunCommand(()-> DRIVE_SUBSYSTEM.drive(
      inputScaler(CONTROLLER.getRawAxis(1),0.8), 
      inputScaler(-CONTROLLER.getRawAxis(2),0.8)), 
      DRIVE_SUBSYSTEM ) ); */
    
    DRIVE_SUBSYSTEM.setDefaultCommand( new RunCommand(()-> DRIVE_SUBSYSTEM.drive(
      inputScaler(CONTROLLER.getRawAxis(1),0.7), 
      inputScaler(-CONTROLLER.getRawAxis(2),0.7)), 
      DRIVE_SUBSYSTEM ) );
    intakeMotor.setInverted(false);
    shooterMotor.setInverted(true);
    shooterServo.set(0);
  }

  private double inputScaler(double input, double baseline) {
    double scaledInput;

    if(Math.abs(input) < 0.1) return 0;

    if(input<0) { 
      scaledInput=(input*(1-baseline)) - baseline;
      //scaledInput=-scaledInput*scaledInput;
    }
    else { 
      scaledInput=(input*(1-baseline)) + baseline; 
      //scaledInput=scaledInput*scaledInput;
    }

    //round it.
    scaledInput=Math.round(scaledInput*20) / 20.0;

    return scaledInput;
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    //Climb!
    JoystickButton a = new JoystickButton(CONTROLLER,1);
    a.whenPressed(new InstantCommand(() -> climberMotor.set(1))).whenReleased(new InstantCommand(() -> climberMotor.set(0)));

    //Descend!
    JoystickButton b = new JoystickButton(CONTROLLER,2);
    b.whenPressed(new InstantCommand(() -> climberMotor.set(-1))).whenReleased(new InstantCommand(() -> climberMotor.set(0)));

    //Intake 
    JoystickButton c = new JoystickButton(CONTROLLER,8);
    c.whenPressed(new InstantCommand(() -> intakeMotor.set(1))).whenReleased(new InstantCommand(() -> intakeMotor.set(0)));

//outtake
    JoystickButton e = new JoystickButton(CONTROLLER,5);
    e.whenPressed(new InstantCommand(() -> intakeMotor.set(-1))).whenReleased(new InstantCommand(() -> intakeMotor.set(0)));

    //auto
    JoystickButton f = new JoystickButton(CONTROLLER,7);
    //f.whenPressed(new RunCommand(() -> DRIVE_SUBSYSTEM.drive(-1,0)).withTimeout(3));
    f.whenPressed(new InstantCommand(() -> DRIVE_SUBSYSTEM.drive(-1, 0))
    .andThen(new InstantCommand(() -> DRIVE_SUBSYSTEM.drive(-1, 0)))
    .andThen(new InstantCommand(() -> DRIVE_SUBSYSTEM.drive(-1, 0)))
    .andThen(new InstantCommand(() -> DRIVE_SUBSYSTEM.drive(-1, 0)))
    .andThen(new InstantCommand(() -> DRIVE_SUBSYSTEM.drive(-1, 0)))
    .andThen(new InstantCommand(() -> DRIVE_SUBSYSTEM.drive(-1, 0)))
    .andThen(new InstantCommand(() -> DRIVE_SUBSYSTEM.drive(-1, 0)))
    .andThen(new InstantCommand(() -> DRIVE_SUBSYSTEM.drive(-1, 0)))
    .andThen(new InstantCommand(() -> DRIVE_SUBSYSTEM.drive(-1, 0)))
    .andThen(new InstantCommand(() -> DRIVE_SUBSYSTEM.drive(-1, 0))));

    //Shoot
    JoystickButton d = new JoystickButton(CONTROLLER,4);
    d.whenPressed(new InstantCommand(() -> shooterMotor.set(1))
          .andThen(new WaitCommand(1.2))
          .andThen(new InstantCommand(() -> shooterServo.set(90)))
          .andThen(new WaitCommand(.5))
          .andThen(new InstantCommand(() -> shooterServo.set(0)))
          .andThen(new InstantCommand(() -> shooterMotor.set(0))));
//      .whenReleased(new InstantCommand(() -> shooterMotor.set(0))
//          .andThen(new InstantCommand(() -> shooterServo.set(0))));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }
}
