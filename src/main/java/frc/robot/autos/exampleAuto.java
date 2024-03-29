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

public class exampleAuto extends ParallelCommandGroup {
  public exampleAuto(Swerve s_Swerve, Tower m_Tower, Arm m_Arm, Gripper m_Gripper) {
    TrajectoryConfig config =
        new TrajectoryConfig(
                Constants.AutoConstants.kMaxSpeedMetersPerSecond,
                Constants.AutoConstants.kMaxAccelerationMetersPerSecondSquared)
            .setKinematics(Constants.Swerve.swerveKinematics);

    // An example trajectory to follow.  All units in meters.
    Trajectory exampleTrajectory =
        TrajectoryGenerator.generateTrajectory(
            // Start at the origin facing the +X direction
          // new HighScore(m_Tower, m_Arm),
                
            new Pose2d(0, 0, s_Swerve.getPose().getRotation()),
            // Pass through these two interior waypoints, making an 's' curve path
            List.of(new Translation2d(-3, 0)),
            // End 3 meters straight ahead of where we started, facing forward
            new Pose2d(-6, 0, s_Swerve.getPose().getRotation()),
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
            exampleTrajectory,
            s_Swerve::getPose,
            Constants.Swerve.swerveKinematics,
            new PIDController(Constants.AutoConstants.kPXController, 0, 0),
            new PIDController(Constants.AutoConstants.kPYController, 0, 0),
            thetaController,
            s_Swerve::setModuleStates,
            s_Swerve);

    addCommands(
      //  new TowerScore(m_Tower), 
      //  new ArmScore(m_Arm),
      //  new WaitCommand(.5),
      //  new GripOut(m_Gripper).withTimeout(.5),
      // new WaitCommand(2),
      // new ArmStore(m_Arm).withTimeout(1),
      // new TowerStore(m_Tower),
      // new InstantCommand(() -> s_Swerve.zeroGyro()).withTimeout(1),

      // new WaitCommand(1).andThen( new InstantCommand(() -> s_Swerve.resetOdometry(exampleTrajectory.getInitialPose()))),
      new WaitCommand(2).andThen(  swerveControllerCommand));
  }
}
