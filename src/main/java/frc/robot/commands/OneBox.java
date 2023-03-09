// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Gripper;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.Tower;
import frc.robot.autos.OneCone;
import frc.robot.commands.ArmScore;
import frc.robot.commands.ArmStore;
import frc.robot.commands.GripOut;
import frc.robot.commands.HighScore;
import frc.robot.commands.ScoreAuto;
import frc.robot.commands.TowerScore;
import frc.robot.commands.TowerStore;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class OneBox extends SequentialCommandGroup {
  /** Creates a new OneBox. */
  public OneBox(Swerve s_Swerve, Tower m_Tower, Arm m_Arm, Gripper m_Gripper) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new ScoreAuto(s_Swerve, m_Tower, m_Arm, m_Gripper).withTimeout(11),
      new OneCone(s_Swerve, m_Tower, m_Arm, m_Gripper)

    
    );
  }
}
