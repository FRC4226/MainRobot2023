package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Button;
//import frc.robot.lib.utli.XboxControllerAxisButton;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.lib.util.XboxControllerAxisButton;
import frc.lib.util.XboxPOVButton;
import frc.robot.autos.*;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    /* Drive Buttons */
   // Driver Buttons
  private JoystickButton a_xBox_Driver;
  private JoystickButton b_xBox_Driver;
  private JoystickButton x_xBox_Driver;
  private JoystickButton y_xBox_Driver;
  private JoystickButton lb_xBox_Driver;
  private JoystickButton rb_xBox_Driver;
  private JoystickButton r_Stick_Button_xbox_Driver;
  private JoystickButton l_Stick_Button_xbox_Driver;
  private JoystickButton start_xBox_Driver;
  private JoystickButton reset_xBox_Driver;
// Co-Pilot Sr. Homie Richard
  private JoystickButton a_xBox_Richard;
  private JoystickButton b_xBox_Richard;
  private JoystickButton x_xBox_Richard;
  private JoystickButton y_xBox_Richard;
  private JoystickButton lb_xBox_Richard;
  private JoystickButton rb_xBox_Richard;
  private JoystickButton r_Stick_Button_xbox_Richard;
  private JoystickButton l_Stick_Button_xbox_Richard;
  private JoystickButton start_xBox_Richard;
  private JoystickButton reset_xBox_Richard;





    /* Controllers */

    private final XboxController driverXbox = new XboxController(0);
    private final XboxController RichardXbox = new XboxController(1);
    private final Joystick driver = new Joystick(4);

    /* Drive Controls */
    private final int translationAxis = XboxController.Axis.kLeftY.value;
    private final int strafeAxis = XboxController.Axis.kLeftX.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;


    /* Driver Buttons */
    private final JoystickButton zeroGyro = new JoystickButton(driverXbox, XboxController.Button.kX.value);
    private final JoystickButton robotCentric = new JoystickButton(driver, 12);
    private final JoystickButton aim = new JoystickButton(driver, 1);
    private final JoystickButton Grip = new JoystickButton(driverXbox, XboxController.Button.kRightBumper.value);
    private final JoystickButton OneUp = new JoystickButton(driverXbox, XboxController.Button.kY.value);

    /* Subsystems */
    public final Swerve s_Swerve = new Swerve();
    public final Vision LimeLight = new Vision(s_Swerve);
    public final Gripper m_gripper = new Gripper();
    public final Tower m_Tower = new Tower();
    public final Arm m_Arm = new Arm();

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
          s_Swerve.setDefaultCommand(
            new TeleopSwerve(
                s_Swerve, 
                () -> -driverXbox.getRawAxis(translationAxis), 
                () -> -driverXbox.getRawAxis(strafeAxis), 
                () -> -driverXbox.getRawAxis(rotationAxis), 
                () -> robotCentric.getAsBoolean()
            )
        ); 

        // Configure the button bindings
        configureButtonBindings();
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
       //Driver Bindings
    rb_xBox_Driver = new JoystickButton(driverXbox, XboxController.Button.kRightBumper.value);
    rb_xBox_Driver.whileTrue(new Grip( m_gripper));
    lb_xBox_Driver = new JoystickButton(driverXbox, XboxController.Button.kLeftBumper.value);
    lb_xBox_Driver.whileTrue(new GripOut(m_gripper));
    a_xBox_Driver = new JoystickButton(driverXbox, XboxController.Button.kA.value);
    a_xBox_Driver.toggleOnTrue(new ArmPickup( m_Arm));

   // lt_xBox_Driver = new XboxControllerAxisButton(m_Controller, XboxController.Axis.kLeftTrigger.value);
   // lt_xBox_Driver.whileTrue(new GripOut(m_gripper));

   // rt_xBox_Driver = new XboxControllerAxisButton(m_Controller, XboxController.Axis.kRightTrigger.value);
   // rt_xBox_Driver.whileTrue(new Grip(m_gripper));

   //Richard Bindings
    a_xBox_Richard = new JoystickButton(RichardXbox, XboxController.Button.kA.value);
    a_xBox_Richard.toggleOnTrue(new TowerScore(m_Tower));
    b_xBox_Richard = new JoystickButton(RichardXbox, XboxController.Button.kB.value);
    b_xBox_Richard.toggleOnTrue(new ArmScore(m_Arm));
    x_xBox_Richard = new JoystickButton(RichardXbox, XboxController.Button.kX.value);
    x_xBox_Richard.toggleOnTrue(new TowerMidScore(m_Tower));
    
    //rb_xBox_Richard = new JoystickButton(driverXbox, XboxController.Button.kRightBumper.value);
    //rb_xBox_Richard.whileTrue(new HighScore(m_Tower, m_Arm));
    //lb_xBox_Richard = new JoystickButton(driverXbox, XboxController.Button.kLeftBumper.value);
    //lb_xBox_Richard.whileTrue(new MidScore(m_Tower, m_Arm));
      
        zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroGyro()));
       // aim.whileTrue(new orientDrive(LimeLight));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public XboxController getXboxController1() {
      return RichardXbox;
    }
    public XboxController getXboxController2() {
        return driverXbox;
      }
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return new exampleAuto(s_Swerve, m_Tower, m_Arm, m_gripper);
    }
}
