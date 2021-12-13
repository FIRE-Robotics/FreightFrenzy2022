package org.firstinspires.ftc.teamcode.opModes.TeleOps;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Movement.ActiveLocation;
import org.firstinspires.ftc.teamcode.util.Hardware;
import org.firstinspires.ftc.teamcode.util.HardwareOld;

@Deprecated
@TeleOp(name = "Old Field Oriented TeleOp", group = "TeleOps")
public class OLDFieldOrientedDrive extends LinearOpMode {

    HardwareOld robot = new HardwareOld();

    @Override
    public void runOpMode() {

        //Values that hold the values that the joysticks return.
        double drive = 0;
        double strafe = 0;
        double turn = 0;

        //Variables that will hold the power each respective motor should be at
        double frontLeftPower;
        double frontRightPower;
        double backLeftPower;
        double backRightPower;

        //set values that will be used for robot later.
        final double carouselSpeed = 0.8;
        double max;
        final double maxSpeed = 0.6;

        robot.initialize(hardwareMap);

        ActiveLocation activeLocation;
        //activeLocation = new ActiveLocation(robot);

        waitForStart();

        while (opModeIsActive()) {
            //Gets values from joystick, and does math to change direction for field
//            drive = -gamepad1.left_stick_y * Math.cos(activeLocation.getAngleInRadians()) +
//                    gamepad1.left_stick_x * Math.sin(activeLocation.getAngleInRadians());
//            strafe = gamepad1.left_stick_x * Math.cos(activeLocation.getAngleInRadians()) -
//                    -gamepad1.left_stick_y * Math.sin(activeLocation.getAngleInRadians());
//            turn = gamepad1.right_stick_x;

            //calculates the drivetrain motor powers based off of the joystick's outputs
            frontLeftPower = drive + strafe + turn;
            frontRightPower = drive - strafe - turn;
            backLeftPower = drive - strafe + turn;
            backRightPower = drive + strafe - turn;


            //if any of the motor values are > 1, than it lowers all proportionally to get good proportions and speed
            if (Math.abs(frontLeftPower) > 1 || Math.abs(backLeftPower) > 1 || Math.abs(frontRightPower) > 1 || Math.abs(backRightPower) > 1) {
                max = Math.max(Math.abs(frontLeftPower), Math.abs(backLeftPower));
                max = Math.max(max, Math.abs(frontRightPower));
                max = Math.max(max, Math.abs(backRightPower));

                frontLeftPower /= max;
                frontRightPower /= max;
                backLeftPower /= max;
                backRightPower /= max;
            }

            //assigns motor powers to the motors
            robot.frontLeftMotor.setPower(frontLeftPower*maxSpeed);
            robot.frontRightMotor.setPower(frontRightPower*maxSpeed);
            robot.backLeftMotor.setPower(backLeftPower*maxSpeed);
            robot.backRightMotor.setPower(backRightPower*maxSpeed);


            //Controls for everything else.
            if (gamepad2.right_bumper){
                robot.carouselSpinner.setPower(carouselSpeed);
            }
            else if (gamepad2.left_bumper){
                robot.carouselSpinner.setPower(-carouselSpeed);
            }
            else{
                robot.carouselSpinner.setPower(0);
            }

            if (gamepad2.left_trigger > 0.1){
                robot.intakeSpinner.setPower(gamepad2.left_trigger*0.8);
            }
            else if (gamepad2.right_trigger > 0.1){
                robot.intakeSpinner.setPower(-gamepad2.right_trigger*0.4);
            }
            else{
                robot.intakeSpinner.setPower(0);
            }

            if (gamepad2.a) {
                robot.intakeLifter.setPower(-0.65);
            }
            else if (gamepad2.y){
                robot.intakeLifter.setPower(0.75);
            }
            else{
                robot.intakeLifter.setPower(0);
            }


            telemetry.addData("Driving: ", drive);    //sends data on forward power
            telemetry.addData("Strafing: ", strafe);   //sends data for strafing powers
            telemetry.addData("Turning: ", turn);    //sends data for turning powers
            telemetry.addData("frontRightMotor:", robot.frontRightMotor.getPower());
            telemetry.addData("frontLeftMotor", robot.frontLeftMotor.getPower());
            telemetry.addData("backRightMotor:", robot.backRightMotor.getPower());
            telemetry.addData("backLeftMotor", robot.backLeftMotor.getPower());
            telemetry.addData("Carousel Spinner Power", robot.carouselSpinner.getPower());
            telemetry.addData("Intake Spinner Power", robot.intakeSpinner.getPower());
            telemetry.addData("Intake Lifter Power", robot.intakeLifter.getPower());
            telemetry.update();
        }
        //activeLocation.stop();
    }
}














