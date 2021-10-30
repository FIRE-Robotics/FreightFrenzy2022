package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Hardware;


@TeleOp(name="JoystickTeleOp", group="TeleOps")
public class JoystickTeleOp extends LinearOpMode {

    /* Declare OpMode members. */
    Hardware robot = new Hardware(hardwareMap);   // sets established hardware


    @Override
    public void runOpMode() {
        //Sets variables for driving/strafing
        double drivingX;
        double drivingY;

        //set variables for turning
        double turning;

        //Math Shit
        double fortyFivedegreesInRadians = -Math.PI/4; //Gets 45 degrees and then converts it to radians.
        double cosine45 = Math.cos(fortyFivedegreesInRadians); //Gets the cosine of 45 degrees
        double sine45 = Math.sin(fortyFivedegreesInRadians); //Gets the sine of 45 degrees

        //values that we set motor power to after doing math.
        double finalDrivingX;
        double finalDrivingY;



        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            //Y is negative because forward on controller is negative.
            drivingY = -gamepad1.left_stick_y;
            drivingX = gamepad1.left_stick_x;

            //value of spinning.
            turning = gamepad1.right_stick_x;

            //checks if the spin you want is actually a real amount, and if so spins, otherwise drives
            if (Math.abs(turning) > 0.05){
                robot.frontRightMotor.setPower(-turning);
                robot.backRightMotor.setPower(-turning);

                robot.frontLeftMotor.setPower(turning);
                robot.backLeftMotor.setPower(turning);
            }
            else {
                //Calculates motor powers
                finalDrivingX = drivingX * cosine45 - drivingY * sine45;
                finalDrivingY = drivingY * cosine45 - drivingX * sine45;

                //Sets power for motors
                robot.frontLeftMotor.setPower(finalDrivingX);
                robot.frontRightMotor.setPower(finalDrivingY);
                robot.backLeftMotor.setPower(finalDrivingY);
                robot.backRightMotor.setPower(finalDrivingX);
            }



            // Send telemetry message to signify robot
            telemetry.addData("Driving", drivingX);    //sends data on forward power
            telemetry.addData("Strafing", drivingY);   //sends data for strafing powers
            telemetry.addData("Turning", turning);    //sends data for turning powers
            telemetry.update();




            /*  Sets phone to sleep so that phone resources are not completely used on this code. Lowering will make the code
                more responsive, but will also slow down the phone
            */
            sleep(50);

        }
    }
}
