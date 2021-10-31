package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Hardware;


@TeleOp(name="JoystickTeleOp2", group="TeleOps")
public class JoystickTeleOp2 extends LinearOpMode {

    /* Declare OpMode members. */

    Hardware robot = new Hardware();   // sets established hardware


    @Override
    public void runOpMode() {



        //Sets variables for driving/strafing
        double drivingX;
        double drivingY;

        //set variables for turning
        double turning;

        double frontLeftPower;
        double frontRightPower;
        double backLeftPower;
        double backRightPower;

        //int intakePos = robot.intakeLifter.getCurrentPosition();
        int outtakePos = 0;
        //TODO: tune intakePos and outtakePos to get the correct positions of the motor for there respected actions.

        robot.initialize(hardwareMap);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();


        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            /*
                Controller 1:
                    Controls:
                        Left Joystick: Driving/Strafing
                        Right Joystick: Turning
                        Left-Bumper: Intake
                        Right-Bumper: Outtake
                        X: Carousel Spinner
                        D-Pad Up: Lifts up intake to outtake
                        D-Pad Down: Lets intake back down to intake
             */

            /*TODO: Not needed but driver preferance, can make it so the intake/outtake motor is binded
                to one button and intelligentely knows power based off of the lifter motor position.
            */






            //Y is negative because forward on controller is negative.
            drivingY = -gamepad1.left_stick_y;
            drivingX = gamepad1.left_stick_x * 1.5; //Multiplied to counteract imperfect strafing
            //TODO: Tune multiplier?


            //value of spinning.
            turning = gamepad1.right_stick_x;

            //calculate Powers
            frontLeftPower = (drivingX + drivingY + turning)*0.8;
            backLeftPower = (drivingY - drivingX + turning)*0.8;
            frontRightPower = (drivingY - drivingX - turning)*0.8;
            backRightPower = (drivingY + drivingX - turning)*0.8;



            //put powers in range of -1 and 1 if they are not for proper driving
            if (Math.abs(frontLeftPower) > 1 || Math.abs(backLeftPower) > 1 || Math.abs(frontRightPower) > 1 || Math.abs(backRightPower) > 1) {
                double max;
                max = Math.max(Math.abs(frontLeftPower), Math.abs(backLeftPower));
                max = Math.max(max, Math.abs(frontRightPower));
                max = Math.max(max, Math.abs(backRightPower));

                frontLeftPower /= max;
                frontRightPower /= max;
                backLeftPower /= max;
                backRightPower /= max;
            }

            robot.frontLeftMotor.setPower(frontLeftPower);
            robot.frontRightMotor.setPower(frontRightPower);
            robot.backLeftMotor.setPower(backLeftPower);
            robot.backRightMotor.setPower(backRightPower);


            //Code for everything that isn't drivetrain

            //carousel spinner, hold down right bumper to keep it spinning
            if (gamepad1.x){
                //TODO: tune power so that duck doesnt go ouiiiiiiii
                //robot.carouselSpinner.setPower(0.5);
            }
            else{
                //robot.carouselSpinner.setPower(0);
            }


            //intake: picking up stuff binded to left bumper, and right bumper is binded to outtaking
            if (gamepad1.left_bumper){
                //robot.intakeSpinner.setPower(0.8);
            }
            else if (gamepad1.right_bumper){
                //robot.intakeSpinner.setPower(-0.3);
            }
            else {
                //robot.intakeSpinner.setPower(0);
            }


            //intake lifter
            if (gamepad1.dpad_up){
                //robot.intakeLifter.setTargetPosition(outtakePos);
            }

            if (gamepad1.dpad_down){
                //robot.intakeLifter.setTargetPosition(intakePos);
            }

            // Send telemetry message to signify robot
            telemetry.addData("Driving: ", drivingX);    //sends data on forward power
            telemetry.addData("Strafing: ", drivingY);   //sends data for strafing powers
            telemetry.addData("Turning: ", turning);    //sends data for turning powers
            telemetry.addData("frontRightMotor:", robot.frontRightMotor.getPower());
            telemetry.addData("frontLeftMotor", robot.frontLeftMotor.getPower());
            telemetry.addData("backRightMotor:", robot.backRightMotor.getPower());
            telemetry.addData("backLeftMotor", robot.backLeftMotor.getPower());

            //telemetry.addData("intakeLifter Position: ", robot.intakeLifter.getCurrentPosition()); //Sends the position of the intakeLifter position (usefule for tuning)
            //telemetry.addData("intakeSpinner Power: ", robot.intakeSpinner.getPower()); //Sends the power of the intakeSpinner to the phone
            //telemetry.addData("carouselSpinner Power: ", robot.carouselSpinner.getPower()); //sends the power of the carousel spinner to the phone
            telemetry.update();




            /*  Sets phone to sleep so that phone resources are not completely used on this code. Lowering will make the code
                more responsive, but will also slow down the phone
            */
            sleep(50);

        }
    }
}
