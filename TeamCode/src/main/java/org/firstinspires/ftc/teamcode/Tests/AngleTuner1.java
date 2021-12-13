package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Const;
import org.firstinspires.ftc.teamcode.Movement.ActiveLocation;
import org.firstinspires.ftc.teamcode.util.Constants;
import org.firstinspires.ftc.teamcode.util.Hardware;

@TeleOp(name = "AngleTuner 1", group = "Tests")
public class AngleTuner1 extends LinearOpMode {

    Hardware robot = new Hardware();

    @Override
    public void runOpMode() {

        double currentAngle;


        robot.initialize(hardwareMap);

        ActiveLocation activeLocation;
        activeLocation = new ActiveLocation(robot);
        waitForStart();

        robot.frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        robot.frontLeftMotor.setPower(0.5);
        robot.frontRightMotor.setPower(0.5);
        robot.backLeftMotor.setPower(0.5);
        robot.backRightMotor.setPower(0.5);

        robot.frontLeftMotor.setTargetPosition(0);
        robot.frontRightMotor.setTargetPosition(0);
        robot.backLeftMotor.setTargetPosition(0);
        robot.backRightMotor.setTargetPosition(0);

        while (opModeIsActive()) {
            currentAngle = activeLocation.getTrimmedAngleInDegrees();

            if (gamepad1.dpad_right){
                robot.frontLeftMotor.setTargetPosition(robot.frontLeftMotor.getCurrentPosition() + 50);
                robot.frontRightMotor.setTargetPosition(robot.frontRightMotor.getCurrentPosition() - 50);
                robot.backLeftMotor.setTargetPosition(robot.backLeftMotor.getCurrentPosition() + 50);
                robot.backRightMotor.setTargetPosition(robot.backRightMotor.getCurrentPosition() - 50);
                sleep(100);
            }
            if (gamepad1.dpad_left){
                robot.frontLeftMotor.setTargetPosition(robot.frontLeftMotor.getCurrentPosition() - 50);
                robot.frontRightMotor.setTargetPosition(robot.frontRightMotor.getCurrentPosition() + 50);
                robot.backLeftMotor.setTargetPosition(robot.backLeftMotor.getCurrentPosition() - 50);
                robot.backRightMotor.setTargetPosition(robot.backRightMotor.getCurrentPosition() + 50);
                sleep(100);
            }


            telemetry.addData("Total Motor Ticks", robot.frontLeftMotor.getCurrentPosition());
            telemetry.addData("Trimmed Angle in Degrees: ", currentAngle);

            telemetry.update();
        }
        activeLocation.stopActiveLocation();
    }
}















