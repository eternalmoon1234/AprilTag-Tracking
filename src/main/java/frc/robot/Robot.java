package frc.robot;


import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.AprilTags.AprilTagCode;

public class Robot extends TimedRobot {
  private AprilTagCode aprilTagTest = new AprilTagCode();

  @Override
  public void robotInit() {}

  @Override
  public void robotPeriodic() {
    aprilTagTest.calculate();
    //System.out.println("Field to Robot: " + testAprilTag.getPos());
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {

  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

  @Override
  public void simulationInit() {
  }

  @Override
  public void simulationPeriodic() {
  }
}
