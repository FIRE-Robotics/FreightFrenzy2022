package org.firstinspires.ftc.teamcode.op_modes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.drivebase.MecanumDrive;

@TeleOp(name = "Final TeleOp", group = "TeleOp")
public class Teleop extends LinearOpMode {
    Hardware robot = new Hardware();

    private double maxSpeed = 1;

    private boolean slowModePressed = false;
    private boolean slowMode = false;

    @Override
    public void runOpMode() {
        robot.initialize(hardwareMap);

        DcMotor frontLeftMotor = robot.frontLeftMotor;
        DcMotor frontRightMotor = robot.frontRightMotor;
        DcMotor backLeftMotor = robot.backLeftMotor;
        DcMotor backRightMotor = robot.backRightMotor;

        MecanumDrive drivetrain = new MecanumDrive(
                frontLeftMotor,
                frontRightMotor,
                backLeftMotor,
                backRightMotor
        );

        drivetrain.setMaxSpeed(maxSpeed);

        waitForStart();

        while (opModeIsActive()) {
            double drive = gamepad1.left_stick_x;
            double strafe = -gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;

            if (slowMode) {
                drive *= 0.3;
                strafe *= 0.3;
                turn *= 0.3;
            }

            drivetrain.drive(drive, strafe, turn, true);

            telemetry.addData("Driving: ", drive);    //sends data on forward power
            telemetry.addData("Strafing: ", strafe);   //sends data for strafing powers
            telemetry.addData("Turning: ", turn);    //sends data for turning powers
            telemetry.addData("frontRightMotor:", robot.frontRightMotor.getPower());
            telemetry.addData("frontLeftMotor", robot.frontLeftMotor.getPower());
            telemetry.addData("backRightMotor:", robot.backRightMotor.getPower());
            telemetry.addData("backLeftMotor", robot.backLeftMotor.getPower());
            telemetry.update();
        }
    }
}
