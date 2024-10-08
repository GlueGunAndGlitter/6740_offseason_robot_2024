// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import javax.crypto.spec.GCMParameterSpec;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;

public class ChangeAngelShooter extends SubsystemBase {
  TalonFX rightAngelMotor = new TalonFX(Constants.AngelChangeConstants.RIGHT_ANGEL_MOTOR_ID);
  TalonFX leftAngelMotor = new TalonFX(Constants.AngelChangeConstants.LEFT_ANGEL_MOTOR_ID);
  
  PIDController rotatePID = new PIDController(0.3, 0, 0);

  InterpolatingDoubleTreeMap estimatedAngle = new InterpolatingDoubleTreeMap();

  double angle = 0;
  /** Creates a new ChangeAngelShooter. */
  public ChangeAngelShooter() {
    putData();
    leftAngelMotor.setInverted(true);
  }


  private void setTargetAngle(){
    angle = estimatedAngle.get(RobotContainer.swerve.calculateDesinence());
  }

  public Command setTargetAngaleCommand(){
    return this.runOnce(()-> setTargetAngle());
  }

  private void zeroEncoder(){
    leftAngelMotor.setPosition(0);
    rightAngelMotor.setPosition(0);
  }
  

  
    private void setToZeroRotations(){
      
      if (rotatePID.calculate(leftAngelMotor.getPosition().getValue(),0) < -0.2 ){
        leftAngelMotor.set(-0.2);
        rightAngelMotor.set(-0.2);
  
      }else{
        leftAngelMotor.set(rotatePID.calculate(leftAngelMotor.getPosition().getValue(),0));
        rightAngelMotor.set(rotatePID.calculate(rightAngelMotor.getPosition().getValue(),0));
      }
    }
  private void setRotaion(){
    double rotaion = (double) Robot.shooterAngel.getDouble(0) / 2;
    // double rotaion = (angle / 2);
    if (rotatePID.calculate(leftAngelMotor.getPosition().getValue(),rotaion) > 0.5){
      leftAngelMotor.set(0.5);
      rightAngelMotor.set(0.5);
    }else{
      leftAngelMotor.set(rotatePID.calculate(leftAngelMotor.getPosition().getValue(),rotaion));
      rightAngelMotor.set(rotatePID.calculate(rightAngelMotor.getPosition().getValue(),rotaion));
    }
 
}

  private double getRotation(){
    return leftAngelMotor.getPosition().getValue();
  }

  private double getAngle(){
    return leftAngelMotor.getPosition().getValue() * 2;
  }
  private void stopMotor(){
    leftAngelMotor.stopMotor();
    rightAngelMotor.stopMotor();
  }
  

  private void putData(){
      estimatedAngle.put(5.34,23.0);
      estimatedAngle.put(3.08, 35.0);
      estimatedAngle.put(4.23, 26.0);
      estimatedAngle.put(4.08, 26.0);
      estimatedAngle.put(2.4, 45.0);
      estimatedAngle.put(3.35, 32.0);
      estimatedAngle.put(4.4, 26.0);
      estimatedAngle.put(7.0,21.7);
  
  }

   public Command setToZeroRotationCommand(){
    return this.run(() -> setToZeroRotations());
   }
   public Command setRotationCommand(){
    return this.run(() -> setRotaion());
  }

  public Command stopMotorsCommand(){
    return this.run(() -> stopMotor());
  }

  public Command zeroEncodersCommand(){
    return this.runOnce(()-> zeroEncoder());
  }

  
  @Override
  public void periodic() {
    // System.out.println(estimatedAngle.get(RobotContainer.swerve.calculateDesinence()));
    System.out.println(getAngle());

  }
}
