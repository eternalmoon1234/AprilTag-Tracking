package frc.robot.AprilTags;

import javax.swing.plaf.synth.SynthEditorPaneUI;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonTrackedTarget;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AprilTagCode {
    AllAprilTags targetIDs = new AllAprilTags();
    private PhotonCamera camera = new PhotonCamera("gloworm");
    private Transform3d cameraToRobotCenter = new Transform3d(
            new Translation3d(Units.inchesToMeters(13.75), 0, Units.inchesToMeters(35.5)),
            new Rotation3d(0, Units.degreesToRadians(-29.25), Units.degreesToRadians(3.57))).inverse();
    private Pose3d robotPos;
    // private Transform3d targetFix = new Transform3d(new Translation3d(0, 0, 0),
    // new Rotation3d(90, 0, 90))

    // targetToCameraFLIP: [-0.02, -0.49, 1.49] [60.43, 2.00, -88.52]
    // globalCameraPose: [-0.49, 1.49, -0.02] [-2.30, -29.55, -177.38]

    public Pose3d fixedTransformBy(Pose3d original, Transform3d transf) {
        return new Pose3d(
                original.getTranslation().plus(transf.getTranslation().rotateBy(original.getRotation())),
                transf.getRotation().plus(original.getRotation()));
    }

    public void calculate() {
        var result = camera.getLatestResult();
        if (result.hasTargets()) {
            PhotonTrackedTarget target = result.getBestTarget();
            Transform3d targetToCamera = target.getCameraToTarget().inverse();

            var tagGlobalPose = new Pose3d(new Translation3d(Units.inchesToMeters(0), 0, Units.inchesToMeters(0)),
                    new Rotation3d(Units.degreesToRadians(-90), Units.degreesToRadians(180), 0));
            var cameraGlobalPose = this.fixedTransformBy(tagGlobalPose, targetToCamera);
            var robotGlobalPose = this.fixedTransformBy(cameraGlobalPose, cameraToRobotCenter);

            System.out.printf("robotGlobalPose: [%.02f, %.02f, %.02f] [%.02f, %.02f, %.02f]\n",
                    robotGlobalPose.getTranslation().getX(),
                    robotGlobalPose.getTranslation().getY(),
                    robotGlobalPose.getTranslation().getZ(),
                    Units.radiansToDegrees(robotGlobalPose.getRotation().getX()),
                    Units.radiansToDegrees(robotGlobalPose.getRotation().getY()),
                    Units.radiansToDegrees(robotGlobalPose.getRotation().getZ()));
        }
    }

    public Pose3d getPos() {
        return robotPos;
    }
}
