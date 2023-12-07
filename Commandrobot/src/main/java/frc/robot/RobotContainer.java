// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.CameraServerCvJNI;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.macro.LimelightAlign;
import frc.robot.commands.macro.ResetEncoders;
import frc.robot.commands.macro.SetDeport;
import frc.robot.commands.macro.SetHigh;
import frc.robot.commands.macro.SetHighCube;
import frc.robot.commands.macro.SetHome;
import frc.robot.commands.macro.SetLowConeTilted;
import frc.robot.commands.macro.SetLowCube;
import frc.robot.commands.macro.SetMid;
import frc.robot.commands.macro.SetMidCube;
import frc.robot.commands.macro.timed.PIDTurnTimed;
import frc.robot.commands.macro.SetHumanPlayer;
import frc.robot.commands.macro.SetLowConeStanding;
import frc.robot.commands.persistent.Elevate;
import frc.robot.commands.persistent.MoveArmJoystick;
import frc.robot.commands.persistent.MoveWristManual;
import frc.robot.commands.persistent.RollClaw;
import frc.robot.commands.persistent.SwerveDriveCmd;
// import frc.robot.commands.auton.AutonBalance;
import frc.robot.commands.auton.*;
// import frc.robot.commands.persistent.VibrateControllerTimed;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.SwerveDrive;
import frc.robot.subsystems.Wrist;
import frc.robot.subsystems.SwerveDrive.Direction;
import frc.robot.subsystems.LEDStrip;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public final class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Wrist wrist = new Wrist();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  Timer timer;
	double last = 0.0;
	double[] desiredStates = new double[13];

	//Controllers
	private final XboxController driver = new XboxController(0);
	private final XboxController operator = new XboxController(1);
	XboxController[] controllers = {driver, operator};

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    new Trigger(m_exampleSubsystem::exampleCondition)
        .onTrue(new ExampleCommand(m_exampleSubsystem));

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    wrist.setDefaultCommand(new MoveWristManual(wrist, operator));
    new JoystickButton(operator, RobotMap.Controller.A).whenHeld(new WristMovement(wrist));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.exampleAuto(m_exampleSubsystem);
  }
}
