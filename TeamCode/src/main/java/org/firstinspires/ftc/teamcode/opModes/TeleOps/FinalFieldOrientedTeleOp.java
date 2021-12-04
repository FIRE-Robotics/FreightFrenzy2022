package org.firstinspires.ftc.teamcode.opModes.TeleOps;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.util.Hardware;

@TeleOp(name = "Final TeleOp", group = "TeleOps")
public class FinalFieldOrientedTeleOp extends LinearOpMode {

    //TODO: initialize robot
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

        double spin = 0.6;

        double max;

        robot.initialize(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            drive = -gamepad1.left_stick_y;
            strafe = gamepad1.left_stick_x;
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

            //TODO: Set Motor Power
            robot.frontLeftMotor.setPower(frontLeftPower);
            robot.frontRightMotor.setPower(frontRightPower);
            robot.backRightMotor.setPower(backRightPower);
            robot.backLeftMotor.setPower(backLeftPower);

            //TODO: Controls
            if (gamepad2.left_trigger >= 0.1){
                robot.intakeMotor.setPower(gamepad2.left_trigger);
            }
            if (gamepad2.right_trigger >= 0.1){
                robot.intakeMotor.setPower(-gamepad2.left_trigger);
            }
            if (gamepad2.left_bumper){
                robot.carouselSpinner.setPower(spin);
            }
            if (gamepad2.right_bumper){
                robot.carouselSpinner.setPower(-spin);
            }



            //TODO: Telemetry
            telemetry.addData("Front Left Motor Power", robot.frontLeftMotor.getPower());
            telemetry.addData("Front Right Motor Power: ", robot.frontRightMotor.getPower());
            telemetry.update();
        }
    }
}















