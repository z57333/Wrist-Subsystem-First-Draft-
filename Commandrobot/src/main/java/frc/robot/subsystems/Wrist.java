// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.CommandBase;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Wrist extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */

  private final Spark wristMotor = new Spark(5);
  private final Encoder wristEncoder = new Encoder(5, 6);
  private final double kEncoderTick2Meter = 1.0 / 4096.0 * 0.128 * Math.PI;
  private PIDController wristPIDController = new PIDController(Constants.Wrist.P, Constants.Wrist.I, Constants.Wrist.D);

  public double getEncoderValues() {
    return (wristEncoder.get() * kEncoderTick2Meter);
  }

  public double getWristAngle(){
    double angle = wristEncoder.getPosition() * Constants.Wrist.angleConversionFactor;
    return angle;
  }

  public void resetWristEncoder(){
    wrist.getEncoder().setPosition(0.1);
  }

  public void setWrist(double position) {
    wrist.set(position);
  }

  public void stopMovement() {
    wrist.stopMotor();
  }

  public Wrist() {}

  /**
   * Example command factory method.
   *
   * @return a command
   */
  // public CommandBase exampleMethodCommand() {
  //   // Inline construction of command goes here.
  //   // Subsystem::RunOnce implicitly requires `this` subsystem.
  //   return runOnce(
  //       () -> {
  //         /* one-time action goes here */
  //       });
  // }

  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Elevator encoder value", getEncoderValues());
    SmartDashboard.putNumber("Wrist current value", wrist.getOutputCurrent()); 
  }

  public void setMotor(double speed) {
    wristMotor.set(speed);
  }
}
