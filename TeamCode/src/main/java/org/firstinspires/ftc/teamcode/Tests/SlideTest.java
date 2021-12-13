package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Movement.ActiveLocation;
import org.firstinspires.ftc.teamcode.util.Hardware;

@TeleOp(name = "Slide Test", group = "Tests")
public class SlideTest extends LinearOpMode {

    Hardware robot = new Hardware();

    @Override
    public void runOpMode() {


        robot.initialize(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.dpad_up){
                robot.slideMotor.setTargetPosition(robot.slideMotor.getCurrentPosition() + 100);
                sleep(100);
            }
            if (gamepad1.dpad_down){
                robot.slideMotor.setTargetPosition(robot.slideMotor.getCurrentPosition() - 100);
                sleep(100);
            }

            telemetry.addData("Slide Motor Encoder: ", robot.slideMotor.getCurrentPosition());


            telemetry.update();
        }
    }
}















