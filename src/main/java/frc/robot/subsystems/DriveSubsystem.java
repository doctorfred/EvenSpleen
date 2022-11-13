// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {
  private Spark leftMotor=new Spark(1);
  private Spark rightMotor=new Spark(2);

  private DifferentialDrive dDrive=new DifferentialDrive(leftMotor,rightMotor);

  /** Creates a new DriveSubsystem. */
  public DriveSubsystem() {
    leftMotor.setInverted(true);
  }

  public void drive(double speed, double turn) {
    dDrive.arcadeDrive(speed, turn);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
