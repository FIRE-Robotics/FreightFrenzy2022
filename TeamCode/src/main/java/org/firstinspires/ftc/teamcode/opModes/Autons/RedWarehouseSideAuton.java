package org.firstinspires.ftc.teamcode.opModes.Autons;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Movement.ActiveLocation;
import org.firstinspires.ftc.teamcode.Movement.BasicAutoDriving;
import org.firstinspires.ftc.teamcode.Movement.Pathfinder;
import org.firstinspires.ftc.teamcode.util.Constants;
import org.firstinspires.ftc.teamcode.util.Hardware;


@Autonomous(name="Red Warehouse Side Auton", group="Auton")
public class RedWarehouseSideAuton extends LinearOpMode {

    Hardware robot = new Hardware();

    @Override
    public void runOpMode() {

        //Sets up all subsystems
        robot.initialize(hardwareMap);
        ActiveLocation activeLocation = new ActiveLocation(robot);
        Pathfinder pathfinder = new Pathfinder(activeLocation);
        BasicAutoDriving basicAutoDriving = new BasicAutoDriving(activeLocation, robot, 0.5, pathfinder);


        // Wait for the game to start (driver presses PLAY)
        waitForStart();


        if (opModeIsActive()){
            //TODO: AUTON PATH GOES HERE
            //Raises slides to the 3rd wobble level
            robot.slideMotor.setTargetPosition(3);

            //Moves forward one meter BUT it needs to be tuned cause these numbers aren't right, I just guessed
            basicAutoDriving.changeCurrentPosition(1,1,0,0.4);

            //Turns right 270 degrees
            //I might be dumb, but I couldn't figure out how to turn 90 degress counterclockwise.
            //so for now this just turns all the way in the other direction.
            basicAutoDriving.turn(Constants.PI_OVER_2);
            basicAutoDriving.turn(Constants.PI);

            //Extends the slides, waits, then retracts the slides. These values also need to be tuned
            robot.slideMotor.setTargetPosition(robot.slideMotor.getCurrentPosition() + 100);
            sleep(1000);
            robot.slideMotor.setTargetPosition(robot.slideMotor.getCurrentPosition() -100);

            //Navigates back to the wall, and hopefully wall rides back into the warehouse. The values need to be tuned
            basicAutoDriving.turn(Constants.PI_OVER_2);
            basicAutoDriving.changeCurrentPosition(1,1,0,0.4);
            basicAutoDriving.turn(Constants.PI_OVER_2);
            basicAutoDriving.changeCurrentPosition(1.5,1.5,0,0.5);
        }

    }
}