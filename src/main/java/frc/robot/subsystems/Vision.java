package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import frc.robot.Constants;
import edu.wpi.first.math.kinematics.SwerveModuleState;


public class Vision extends SubsystemBase{
    double xOffset;
    boolean visible;
    double steering_adjust;
    private final Swerve s_Swerve;
    public Vision(Swerve s_Swerve){
        this.s_Swerve = s_Swerve;
    }
    @Override
    public void periodic(){
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        xOffset = table.getEntry("tx").getDouble(0);
        visible = table.getEntry("tv").getBoolean(false);
        SmartDashboard.putNumber("Limelight x offset", xOffset);
        SmartDashboard.putBoolean("Targets visible", visible);
    }
    public void aim(){
        double Kp = -0.1f;
        double min_command = 0.05f;
        double heading_error = -xOffset;
        if (xOffset > 1.0)
        {
                steering_adjust = Kp*heading_error - min_command;
        }
        else if (xOffset < 1.0)
        {
                steering_adjust = Kp*heading_error + min_command;
        }
        ChassisSpeeds speeds = new ChassisSpeeds(0, 0, steering_adjust * 500);

        // Convert to module states
        SwerveModuleState[] moduleStates = Constants.Swerve.swerveKinematics.toSwerveModuleStates(speeds);

        s_Swerve.setModuleStates(moduleStates);
    }
}
