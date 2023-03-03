package frc.robot.autos;

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

public class OneCone extends ParallelCommandGroup {
  public OneCone(Swerve S_swerve, Tower m_Tower, Arm m_Arm, Gripper m_Gripper) {
  
    addCommands(
      new TowerScore(m_Tower),
      new WaitCommand(3).andThen(new ArmScore(m_Arm)),
      new WaitCommand(5).andThen(new GripOut(m_Gripper).withTimeout(.4)));
     // new WaitCommand(7).andThen(new ArmStore(m_Arm)));
      //new WaitCommand(9).andThen(new TowerStore(m_Tower)));
     // new WaitCommand(11).andThen(new RepeatCommand(new InstantCommand(() -> swerve.drive(new Translation2d(0, 3), 0, true, true), swerve))));
    
  }
}
