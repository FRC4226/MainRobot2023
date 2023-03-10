package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RepeatCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.commands.ArmScore;
import frc.robot.commands.ArmStore;
import frc.robot.commands.GripOut;
import frc.robot.commands.HighScore;
import frc.robot.commands.TowerScore;
import frc.robot.commands.TowerStore;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Gripper;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.Tower;
import java.util.List;

public class ScoreAuto extends ParallelCommandGroup {
  public ScoreAuto(Swerve s_Swerve, Tower m_Tower, Arm m_Arm, Gripper m_Gripper) {


    addCommands(
      new InstantCommand(() -> s_Swerve.zeroGyro()).withTimeout(1),
      new TowerScore(m_Tower).withTimeout(7),// 9
      new WaitCommand(2.5).andThen(new ArmScore(m_Arm)).withTimeout(5), //7
      new WaitCommand(4.25).andThen(new GripOut(m_Gripper).withTimeout(.4)));
    
  }
}
