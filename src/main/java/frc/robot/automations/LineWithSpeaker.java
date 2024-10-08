// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.automations;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest.RobotCentric;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.Constants.Swerve;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class LineWithSpeaker extends PIDCommand {
  /** Creates a new LineWithSpeaker. */

  public LineWithSpeaker(DoubleSupplier translationX, DoubleSupplier translationY,
      DoubleSupplier rotationSup) {
        super(
          // The controller that the command will use
          new PIDController(Constants.LineWithSpeakerConstants.KP, 0, Constants.LineWithSpeakerConstants.KD),
          // This should return the measurement
          () -> Math.IEEEremainder(RobotContainer.swerve.getHeading().getDegrees(), 360),
          // This should return the setpoint (can also be a constant)
          () -> Math.IEEEremainder(RobotContainer.angle,360),
          // This uses the output
          output -> {
      
            // Use the output here
            // apply deadband
            double translationVal = MathUtil.applyDeadband(translationX.getAsDouble(), Constants.stickDeadband);
            double strafeVal = MathUtil.applyDeadband(translationY.getAsDouble(), Constants.stickDeadband);


            RobotContainer.swerve.drive(
            new Translation2d(translationVal, strafeVal).times(Constants.Swerve.maxSpeed),
            output * Constants.Swerve.maxAngularVelocity,
            false,
            true);
          }
          );
          // Use addRequirements() here to declare subsystem dependencies.
          // Configure additional PID options by calling `getController` here.
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
