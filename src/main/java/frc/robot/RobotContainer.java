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

  private XboxControllerAxisButton rt_xBox_Driver;
  private XboxControllerAxisButton lt_xBox_Driver;

  private XboxPOVButton povNorth_xBox_Driver;
  private XboxPOVButton povNorthEast_xBox_Driver;
  private XboxPOVButton povNorthWest_xBox_Driver;
  private XboxPOVButton povSouth_xBox_Driver;
  private XboxPOVButton povSouthEast_xBox_Driver;
  private XboxPOVButton povSouthWest_xBox_Driver;
  private XboxPOVButton povWest_xBox_Driver;
  private XboxPOVButton povEast_xBox_Driver;





    /* Controllers */

    private final XboxController m_Controller = new XboxController(0);
    private final Joystick driver = new Joystick(1);

    /* Drive Controls */
    private final int translationAxis = XboxController.Axis.kLeftY.value;
    private final int strafeAxis = XboxController.Axis.kLeftX.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;


    /* Driver Buttons */
    private final JoystickButton zeroGyro = new JoystickButton(m_Controller, XboxController.Button.kX.value);
    private final JoystickButton robotCentric = new JoystickButton(driver, 12);
    private final JoystickButton aim = new JoystickButton(driver, 1);
    private final JoystickButton Grip = new JoystickButton(m_Controller, XboxController.Button.kRightBumper.value);
    private final JoystickButton OneUp = new JoystickButton(m_Controller, XboxController.Button.kY.value);

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
                () -> -m_Controller.getRawAxis(translationAxis), 
                () -> -m_Controller.getRawAxis(strafeAxis), 
                () -> -m_Controller.getRawAxis(rotationAxis), 
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
        /* Driver Buttons */
        rb_xBox_Driver = new JoystickButton(m_Controller, XboxController.Button.kRightBumper.value);
        rb_xBox_Driver.whileTrue(new Grip( m_gripper));
        lb_xBox_Driver = new JoystickButton(m_Controller, XboxController.Button.kLeftBumper.value);
        lb_xBox_Driver.whileTrue(new GripOut(m_gripper));


        a_xBox_Driver = new JoystickButton(m_Controller, XboxController.Button.kA.value);
        a_xBox_Driver.toggleOnTrue(new ArmPickup( m_Arm));

       // lt_xBox_Driver = new XboxControllerAxisButton(m_Controller, XboxController.Axis.kLeftTrigger.value);
       // lt_xBox_Driver.whileTrue(new GripOut(m_gripper));

       // rt_xBox_Driver = new XboxControllerAxisButton(m_Controller, XboxController.Axis.kRightTrigger.value);
       // rt_xBox_Driver.whileTrue(new Grip(m_gripper));

        b_xBox_Driver = new JoystickButton(m_Controller, XboxController.Button.kB.value);
       // b_xBox_Driver.toggleOnTrue(new TowerScore(m_Tower));
        b_xBox_Driver.toggleOnTrue(new TowerMidScore(m_Tower));
       // b_xBox_Driver.toggleOnTrue(new HighScore(m_Tower, m_Arm));
       y_xBox_Driver = new JoystickButton(m_Controller, XboxController.Button.kY.value);
       y_xBox_Driver.toggleOnTrue(new ArmScore(m_Arm));

      
        zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroGyro()));
       // aim.whileTrue(new orientDrive(LimeLight));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */

    public XboxController getXboxController() {
        return m_Controller;
      }
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return new exampleAuto(s_Swerve, m_Tower, m_Arm, m_gripper);
    }
}
