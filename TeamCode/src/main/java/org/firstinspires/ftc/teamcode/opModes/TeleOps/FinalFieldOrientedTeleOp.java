package org.firstinspires.ftc.teamcode.opModes.TeleOps;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Const;
import org.firstinspires.ftc.teamcode.Movement.ActiveLocation;
import org.firstinspires.ftc.teamcode.util.Constants;
import org.firstinspires.ftc.teamcode.util.Hardware;

@TeleOp(name = "Final Field Oriented TeleOp", group = "TeleOps")
public class FinalFieldOrientedTeleOp extends LinearOpMode {

    Hardware robot = new Hardware();

    @Override
    public void runOpMode() {

        double drive;
        double strafe;
        double turn;

        double frontLeftPower;
        double frontRightPower;
        double backLeftPower;
        double backRightPower;

        int restintakeSlidePosition = 0;
        int firstLevelSlidePosition = Constants.bottomLevelShippingHubTicks;
        int secondLevelSlidePosition = Constants.secondLevelShippingHubTicks;
        int thirdLevelSlidePosition = Constants.thirdLevelShippingHubTicks;
        int shippingElementHeight = Constants.teamElementHeightTicks;

        double maxMotorSpeed = 0.8;
        double maxOuttakeSpeed = 0.4;

        double currentAngle;

        double spin = 0.6;
        double max;

        robot.initialize(hardwareMap);

        ActiveLocation activeLocation;
        activeLocation = new ActiveLocation(robot);
        waitForStart();

        while (opModeIsActive()) {
            currentAngle = activeLocation.getAngleInRadians();

            drive = -gamepad1.left_stick_y * Math.cos(currentAngle) +
                    gamepad1.left_stick_x * Math.sin(currentAngle);
            strafe = gamepad1.left_stick_x * Math.cos(currentAngle) -
                    -gamepad1.left_stick_y * Math.sin(currentAngle);
            turn = gamepad1.right_stick_x;

            frontLeftPower = drive + strafe + turn;
            frontRightPower = drive - strafe - turn;
            backLeftPower = drive - strafe + turn;
            backRightPower = drive + strafe - turn;


            if (Math.abs(frontLeftPower) > 1 || Math.abs(frontRightPower) > 1 || Math.abs(backLeftPower) > 1 || Math.abs(backRightPower) > 1){
                max = Math.max(frontLeftPower, Math.max(frontRightPower, Math.max(backLeftPower, backRightPower)));

                frontLeftPower /= max;
                frontRightPower /= max;
                backLeftPower /= max;
                backRightPower /= max;
            }

            robot.frontLeftMotor.setPower(frontLeftPower * maxMotorSpeed);
            robot.frontRightMotor.setPower(frontRightPower * maxMotorSpeed);
            robot.backRightMotor.setPower(backRightPower * maxMotorSpeed);
            robot.backLeftMotor.setPower(backLeftPower * maxMotorSpeed);

            if (gamepad2.left_trigger >= 0.1){
                robot.intakeMotor.setPower(gamepad2.left_trigger * maxMotorSpeed);
            }
            if (gamepad2.right_trigger >= 0.1){
                robot.intakeMotor.setPower(-gamepad2.right_trigger * maxOuttakeSpeed);
            }
            if (gamepad2.left_bumper){
                robot.carouselSpinner.setPower(spin);
            }
            if (gamepad2.right_bumper){
                robot.carouselSpinner.setPower(-spin);
            }

            if (gamepad2.dpad_up){
                robot.slideMotor.setTargetPosition(restintakeSlidePosition);
            }
            else if (gamepad2.dpad_right){
                robot.slideMotor.setTargetPosition(firstLevelSlidePosition);
            }
            else if (gamepad2.dpad_down){
                robot.slideMotor.setTargetPosition(secondLevelSlidePosition);
            }
            else if (gamepad2.dpad_left){
                robot.slideMotor.setTargetPosition(thirdLevelSlidePosition);
            }





            telemetry.addData("Front Left Motor Power: ", robot.frontLeftMotor.getPower());
            telemetry.addData("Front Right Motor Power: ", robot.frontRightMotor.getPower());
            telemetry.addData("Back Left Motor Power: ", robot.backLeftMotor.getPower());
            telemetry.addData("Back Right Motor Power: ", robot.backRightMotor.getPower());

            telemetry.addData("Slide Motor Encoder: ", robot.slideMotor.getCurrentPosition());

            telemetry.addData("Angle: ", currentAngle);

            telemetry.update();
        }
        activeLocation.stopActiveLocation();
    }
}















