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
    Hardware robot = new Hardware(hardwareMap);   // sets established hardware


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


        // Wait for the game to start (driver presses PLAY)
        waitForStart();


        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {


            //Y is negative because forward on controller is negative.
            drivingY = -gamepad1.left_stick_y;
            drivingX = gamepad1.left_stick_x * 1.5; //Multiplied to counteract imperfect strafing
            //TODO: Tune multiplier?


            //value of spinning.
            turning = gamepad1.right_stick_x;

            //calculate Powers
            frontLeftPower = drivingX + drivingY + turning;
            backLeftPower = drivingX - drivingY + turning;
            frontRightPower = drivingY - drivingX - turning;
            backRightPower = drivingY + drivingX - turning;



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
