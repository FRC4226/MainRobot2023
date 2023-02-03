package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.math.geometry.Translation2d;
//import frc.robot.Constants;


public class Vision extends SubsystemBase{
    double xOffset;
    double yOffset;
    boolean visible;
    double steering_adjust;
    NetworkTable table;
    private final Swerve s_Swerve;
    public Vision(Swerve s_Swerve){
        this.s_Swerve = s_Swerve;
    }
    @Override
    public void periodic(){
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        xOffset = table.getEntry("tx").getDouble(0);
        yOffset = table.getEntry("ty").getDouble(0);
        SmartDashboard.putNumber("Limelight x offset", xOffset);
        SmartDashboard.putBoolean("Targets visible", visible);
    }
    public void aim(){
        double Kp = 0.1;
        double min_command = 0.05;
        double heading_error = -xOffset;
        if (Math.abs(getX()) == 0){
            s_Swerve.drive(new Translation2d(0, 0), 2, true, false);
        }
        else{
            if (xOffset > 1.0)
            {
                    steering_adjust = Kp*heading_error - min_command;
            }
            else if (xOffset < 1.0)
            {
                    steering_adjust = Kp*heading_error + min_command;
            }
            s_Swerve.drive(new Translation2d(0, 0), steering_adjust, true, true);
        }
        

        // Convert to module states

        
    }
    public double getX(){
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    }
    //public double getDistance(){
        //return ((Constants.Swerve.targetHeightInches - Constants.Swerve.visionHeightInches)/(Math.tan(yOffset)));
    //}
}
