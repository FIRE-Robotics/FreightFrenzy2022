package org.firstinspires.ftc.teamcode.Movement;



import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.util.Angle;
import org.firstinspires.ftc.teamcode.util.Constants;
import org.firstinspires.ftc.teamcode.util.Hardware;

public class BasicAutoDriving {

    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;

    double ticksPerMilimeterDrive = 0;
    double ticksPerMilimeterStrafe = 0;
    double TicksPerRadian = 0;

    int robotPositionX = 0;
    int robotPositionY = 0;

    int turnTimePerRadian = 1000;

    ElapsedTime elapsedTime;

    private Pathfinder Pathfinder;
    private ActiveLocation activeLocation;

    public BasicAutoDriving (ActiveLocation ActiveLocation, Hardware robot, double speed, Pathfinder pathfinder){
        frontLeftMotor = robot.frontRightMotor;
        frontRightMotor = robot.frontRightMotor;
        backLeftMotor = robot.backLeftMotor;
        backRightMotor = robot.backRightMotor;

        Pathfinder = pathfinder;
        activeLocation = ActiveLocation;
        elapsedTime = robot.runtime;

        robotPositionX = 0;
        robotPositionY = 0;

        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontLeftMotor.setTargetPosition(0);
        frontRightMotor.setTargetPosition(0);
        backLeftMotor.setTargetPosition(0);
        backRightMotor.setTargetPosition(0);

        frontLeftMotor.setPower(speed);
        frontRightMotor.setPower(speed);
        backLeftMotor.setPower(speed);
        backRightMotor.setPower(speed);
    }

    public void changeMaxMotorSpeeds(double speed){
        frontLeftMotor.setPower(speed);
        frontRightMotor.setPower(speed);
        backLeftMotor.setPower(speed);
        backRightMotor.setPower(speed);
    }

    /**
     *
     * @param targetX The desired x value in mm from the starting position
     * @param targetY The desired y value in mm from the starting position
     * @param targetAngle The desired angle in RADIANS from starting position
     */
    public void goToPosition(int targetX, int targetY, Angle targetAngle){
        double robotAngle = activeLocation.getTrimmedAngleInRadians();
        double turnDistance = Pathfinder.findAngleDifferenceInRadians(robotAngle, targetAngle.getAngleInRadians());
        int xChange = targetX - robotPositionX;
        int yChange = targetY - robotPositionY;

        int robotOrientedX;
        int robotOrientedY;

        turn(turnDistance, (int) (turnTimePerRadian * turnDistance));
        }


    public void turn (double angle){
        frontLeftMotor.setTargetPosition(frontLeftMotor.getCurrentPosition() - (int) Math.round(angle * TicksPerRadian));
        backLeftMotor.setTargetPosition(frontLeftMotor.getCurrentPosition() - (int) Math.round(angle * TicksPerRadian));
        frontRightMotor.setTargetPosition(frontLeftMotor.getCurrentPosition() + (int) Math.round(angle * TicksPerRadian));
        backRightMotor.setTargetPosition(frontLeftMotor.getCurrentPosition() + (int) Math.round(angle * TicksPerRadian));
    }


    public void turn (double angle, int breakTime){
        elapsedTime.reset();
        frontLeftMotor.setTargetPosition(frontLeftMotor.getCurrentPosition() - (int) Math.round(angle * TicksPerRadian));
        backLeftMotor.setTargetPosition(frontLeftMotor.getCurrentPosition() - (int) Math.round(angle * TicksPerRadian));
        frontRightMotor.setTargetPosition(frontLeftMotor.getCurrentPosition() + (int) Math.round(angle * TicksPerRadian));
        backRightMotor.setTargetPosition(frontLeftMotor.getCurrentPosition() + (int) Math.round(angle * TicksPerRadian));

        while (frontLeftMotor.isBusy() || frontRightMotor.isBusy() || backLeftMotor.isBusy() || backRightMotor.isBusy()){
            if (elapsedTime.milliseconds() >= breakTime){
                return;
            }
        }
    }

    public void turnTo(double targetAngle){
        turn(Pathfinder.findAngleFromCurrentAngleInRadians(targetAngle));
    }

    public void turnTo(double targetAngle, int breakTime){
        turn(Pathfinder.findAngleFromCurrentAngleInRadians(targetAngle));
    }









}
