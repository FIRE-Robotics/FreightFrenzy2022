package org.firstinspires.ftc.teamcode.opModes.Autons;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Movement.ActiveLocation;
import org.firstinspires.ftc.teamcode.Movement.BasicAutoDriving;
import org.firstinspires.ftc.teamcode.Movement.Pathfinder;
import org.firstinspires.ftc.teamcode.util.Hardware;


@Autonomous(name="Blue Warehouse Side Auton", group="Auton")
public class BlueWarehouseSideAuton extends LinearOpMode {

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
        }

    }
}