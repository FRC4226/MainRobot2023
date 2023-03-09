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
import frc.robot.commands.ScoreAuto;
import frc.robot.commands.TowerScore;
import frc.robot.commands.TowerStore;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Gripper;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.Tower;
import java.util.List;

public class OneCone extends SequentialCommandGroup {
  public OneCone(Swerve s_Swerve, Tower m_Tower, Arm m_Arm, Gripper m_Gripper) {
  
    TrajectoryConfig config =
    new TrajectoryConfig(
            Constants.AutoConstants.kMaxSpeedMetersPerSecond,
            Constants.AutoConstants.kMaxAccelerationMetersPerSecondSquared)
        .setKinematics(Constants.Swerve.swerveKinematics);

// An example trajectory to follow.  All units in meters.
Trajectory OneConeTrajectory =
    TrajectoryGenerator.generateTrajectory(
        // Start at the origin facing the +X direction
      // new HighScore(m_Tower, m_Arm),
            
        new Pose2d(0, 0, new Rotation2d(0)),
        // Pass through these two interior waypoints, making an 's' curve path
        List.of(new Translation2d(-1, 0), new Translation2d(-4, 0)),
        // End 3 meters straight ahead of where we started, facing forward
        new Pose2d(-6, 0, new Rotation2d(0)),
        config);

var thetaController =
    new ProfiledPIDController(
        Constants.AutoConstants.kPThetaController,
        0,
        0,
        Constants.AutoConstants.kThetaControllerConstraints);
thetaController.enableContinuousInput(-Math.PI, Math.PI);

SwerveControllerCommand swerveControllerCommand =
    new SwerveControllerCommand(
        OneConeTrajectory,
        s_Swerve::getPose,
        Constants.Swerve.swerveKinematics,
        new PIDController(Constants.AutoConstants.kPXController, 0, 0),
        new PIDController(Constants.AutoConstants.kPYController, 0, 0),
        thetaController,
        s_Swerve::setModuleStates,
        s_Swerve);

    addCommands(
    new ScoreAuto(s_Swerve, m_Tower, m_Arm, m_Gripper),
    new InstantCommand(() -> s_Swerve.resetOdometry(OneConeTrajectory.getInitialPose())),
    swerveControllerCommand);
    
  }
}
