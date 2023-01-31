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
    double yOffset;
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
        yOffset = table.getEntry("ty").getDouble(0);
        visible = table.getEntry("tv").getBoolean(false);

        
        SmartDashboard.putNumber("Limelight x-offset", xOffset);
        SmartDashboard.putBoolean("Targets visible", visible);
      
    }
    public void aim(){
        double Kp = -0.1;
        double min_command = 0.05;
        double heading_error = -xOffset;
        while (!visible){
            ChassisSpeeds rotate = new ChassisSpeeds(0, 0, Math.toRadians(30));
            SwerveModuleState[] bruh =  Constants.Swerve.swerveKinematics.toSwerveModuleStates(rotate);
            //s_Swerve.setModuleStates(bruh);
        }
        if (xOffset > 1.0)
        {
                steering_adjust = Kp*heading_error - min_command;
        }
        else if (xOffset < 1.0)
        {
                steering_adjust = Kp*heading_error + min_command;
        }
        ChassisSpeeds speeds = new ChassisSpeeds(0, 0, steering_adjust);

        // Convert to module states
        SwerveModuleState[] moduleStates = Constants.Swerve.swerveKinematics.toSwerveModuleStates(speeds);
        SmartDashboard.putNumber("steering adjust", steering_adjust);
        //s_Swerve.setModuleStates(moduleStates);
    }
    public double getDistance(){
        return ((Constants.Swerve.targetHeightInches - Constants.Swerve.visionHeightInches)/(Math.tan(yOffset)));
    }
}
