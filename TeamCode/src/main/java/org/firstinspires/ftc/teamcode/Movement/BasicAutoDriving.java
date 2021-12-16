package org.firstinspires.ftc.teamcode.Movement;



import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Const;
import org.firstinspires.ftc.teamcode.util.Angle;
import org.firstinspires.ftc.teamcode.util.Constants;
import org.firstinspires.ftc.teamcode.util.Hardware;
import org.firstinspires.ftc.teamcode.util.Vector2D;

public class BasicAutoDriving {

    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;

    public double ticksPerMilimeterDrive = Constants.driveTicksPerMM;
    //should be the same as drive according to physics. might need to tune cuz physics doesnt work in robotics
    public double getTicksPerMilimeterStrafe = Constants.driveTicksPerMM;
    public double ticksPerMilimeterStrafe = 0;
    public int ticksPerRadian = Constants.ticksPerRadian;
    public double ticksPerDiagonalTile;

    double robotPositionX = 0;
    double robotPositionY = 0;

    int turnTimePerRadian = 750;
    int driveTimePerMeter = 1500;


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
     * This will change the robot's position by the specified amount. All values are field oriented.
     * @param xChange The amount (field-oriented, mm) you want the robot to move in the x direction based off of current position
     * @param yChange The amount (field-oriented, mm) you want the robot to move in the y direction based off of current position
     * @param maxPower The max power that can go to the motors
     * @param trimmedTargetAngle The angle you want the robot to end in based off of field orientation
     */
    public void changeCurrentPosition(double xChange, double yChange, double trimmedTargetAngle, double maxPower){
        Vector2D robotOrientedTargetVector = Pathfinder.getRobotOrientedVector(0, 0, xChange, yChange);
        turn(robotOrientedTargetVector.getAngle().getTrimmedAngleInRadians());
        int driveTicks = (int) (robotOrientedTargetVector.getMagnitude() * ticksPerMilimeterDrive);
        setTargetLocationChanges(driveTicks, driveTicks, driveTicks, driveTicks, maxPower);
        while (frontLeftMotor.isBusy() || frontRightMotor.isBusy() || backLeftMotor.isBusy() || backRightMotor.isBusy()){

        }
        turnTo(trimmedTargetAngle);
    }


    /**
     *NOT DONE
     * @param targetX The desired x value in mm from the starting position
     * @param targetY The desired y value in mm from the starting position
     * @param targetAngle The desired angle in RADIANS from starting position
     */
    public void goToPosition(double targetX, double targetY, Angle targetAngle){
//        double robotAngle = activeLocation.getTrimmedAngleInRadians();
//        double turnDistance = Pathfinder.findAngleDifferenceInRadians(robotAngle, targetAngle.getAngleInRadians());
//        double xChange = targetX - robotPositionX;
//        double yChange = targetY - robotPositionY;
//        double robotOrientedChangeX;
//        double robotOrientedChangeY;
//
//        turn(turnDistance, (int) (turnTimePerRadian * turnDistance));
//
//        Vector2D robotOrientedTargetVector = Pathfinder.getRobotOrientedVector(robotPositionX, robotPositionY, targetX, targetY);
//
//        robotOrientedChangeX = robotOrientedTargetVector.getXComponent();
//        robotOrientedChangeY = robotOrientedTargetVector.getYComponent();
        }

    /**
     * Will change the robots position to go to a specified field posiiton, will not be that accurate
     * as errors will compound
     * @param targetX The x coordinate of where you want to go
     * @param targetY The y coordinate of where you want to go
     * @param targetAngle The angle you want the robot to be facing at the end (should be in trimmed radians)
     * @param maxSpeed The max speed the robot should go at.
     */
    public void goToPositionBackUp(double targetX, double targetY, Angle targetAngle, double maxSpeed){
        double robotAngle = activeLocation.getTrimmedAngleInRadians();
        double xChange = targetX - robotPositionX;
        double yChange = targetY - robotPositionY;
        double robotOrientedChangeX;
        double robotOrientedChangeY;
        double driveAngle;
        double turnDistance;

        Vector2D robotOrientedTargetVector = Pathfinder.getRobotOrientedVector(robotPositionX, robotPositionY, targetX, targetY);

        robotOrientedChangeX = robotOrientedTargetVector.getXComponent();
        robotOrientedChangeY = robotOrientedTargetVector.getYComponent();
        driveAngle = robotOrientedTargetVector.getAngle().getTrimmedAngleInRadians();

        turnDistance = Pathfinder.findAngleDifferenceInRadians(robotAngle, driveAngle);

        turnTo(driveAngle, (int) (turnTimePerRadian * turnDistance));

        int driveTicks = (int)(ticksPerMilimeterDrive * robotOrientedTargetVector.getMagnitude());
        setTargetLocationChanges(driveTicks, driveTicks, driveTicks, driveTicks, maxSpeed);

        elapsedTime.reset();
        while (frontLeftMotor.isBusy() || frontRightMotor.isBusy() || backLeftMotor.isBusy() || backRightMotor.isBusy()){
            if (elapsedTime.milliseconds() >= (robotOrientedTargetVector.getMagnitude()/1000.0)*driveTimePerMeter){
                break;
            }
        }

        turnTo(targetAngle.getTrimmedAngleInRadians(), (int) (turnTimePerRadian * turnDistance));

        robotPositionX = targetX;
        robotPositionY = targetY;
    }


    /**
     * Turns the robot by a given angle, is not intelligent and just turns in the specified direction in the specified amount
     * @param angle How much and in what direction the robot should turn (negative is counter-clockwise, positive is clockwise)
     */
    public void turn (double angle){
        frontLeftMotor.setTargetPosition(frontLeftMotor.getCurrentPosition() + (int) Math.round(angle * ticksPerRadian));
        backLeftMotor.setTargetPosition(frontRightMotor.getCurrentPosition() + (int) Math.round(angle * ticksPerRadian));
        frontRightMotor.setTargetPosition(backLeftMotor.getCurrentPosition() - (int) Math.round(angle * ticksPerRadian));
        backRightMotor.setTargetPosition(backRightMotor.getCurrentPosition() - (int) Math.round(angle * ticksPerRadian));
    }

    /**
     * Turns the robot by a given angle, is not intelligent and just turns in the specified direction in the specified amount. Will stop running after breaktime is reached
     * @param angle How much and in what direction the robot should turn (negative is counter-clockwise, positive is clockwise)
     * @param breakTime How long in millisecond until the function gives up.
     */
    public void turn (double angle, int breakTime){
        elapsedTime.reset();
        frontLeftMotor.setTargetPosition(frontLeftMotor.getCurrentPosition() + (int) Math.round(angle * ticksPerRadian));
        backLeftMotor.setTargetPosition(frontRightMotor.getCurrentPosition() + (int) Math.round(angle * ticksPerRadian));
        frontRightMotor.setTargetPosition(backLeftMotor.getCurrentPosition() - (int) Math.round(angle * ticksPerRadian));
        backRightMotor.setTargetPosition(backRightMotor.getCurrentPosition() - (int) Math.round(angle * ticksPerRadian));

        while (frontLeftMotor.isBusy() || frontRightMotor.isBusy() || backLeftMotor.isBusy() || backRightMotor.isBusy()){
            if (elapsedTime.milliseconds() >= breakTime){
                return;
            }
        }
    }

    /**
     * Will turn to the specified angle, uses active location.
     * @param targetAngle The angle you want the robot to be facing at the end.
     */
    public void turnTo(double targetAngle){
        turn(Pathfinder.findAngleFromCurrentAngleInRadians(targetAngle));
    }

    /**
     * Will turn to the specified angle, uses active location.
     * @param targetAngle The angle you want the robot to be facing at the end.
     * @param breakTime The amount of time in milliseconds until the function gives up.
     */
    public void turnTo(double targetAngle, int breakTime){
        turn(Pathfinder.findAngleFromCurrentAngleInRadians(targetAngle));
    }

    /**
     * Strafes the robot a given amount of milimeters to the left or right, - = left, positive = right
     * @param sideDistance
     */
    public void strafe(double sideDistance){
        frontLeftMotor.setTargetPosition(frontLeftMotor.getCurrentPosition() + (int) Math.round(sideDistance * ticksPerMilimeterStrafe));
        backLeftMotor.setTargetPosition(frontRightMotor.getCurrentPosition() - (int) Math.round(sideDistance * ticksPerMilimeterStrafe));
        frontRightMotor.setTargetPosition(backLeftMotor.getCurrentPosition() - (int) Math.round(sideDistance * ticksPerMilimeterStrafe));
        backRightMotor.setTargetPosition(backRightMotor.getCurrentPosition() + (int) Math.round(sideDistance * ticksPerMilimeterStrafe));
    }


    /**
     * Sets the motors to change there target encoder position by a given amount
     * @param frontLeftMotorTargetPositionChange The change in ticks for the front left motor
     * @param frontRightMotorTargetPositionChange The change in ticks for the front right motor
     * @param backLeftMotorTargetPositionChange The change in ticks for the back left motor
     * @param backRightMotorTargetPositionChange The change in ticks for the back right motor
     * @param maxPower The maximum power the motors can be set to
     */
    public void setTargetLocationChanges(int frontLeftMotorTargetPositionChange, int frontRightMotorTargetPositionChange, int backLeftMotorTargetPositionChange, int backRightMotorTargetPositionChange, double maxPower){
        frontRightMotor.setPower(maxPower);
        frontLeftMotor.setPower(maxPower);
        backRightMotor.setPower(maxPower);
        backLeftMotor.setPower(maxPower);

        frontRightMotor.setTargetPosition(frontRightMotor.getCurrentPosition() + frontRightMotorTargetPositionChange);
        frontLeftMotor.setTargetPosition(frontLeftMotor.getCurrentPosition() + frontLeftMotorTargetPositionChange);
        backRightMotor.setTargetPosition(backRightMotor.getCurrentPosition() + backRightMotorTargetPositionChange);
        backLeftMotor.setTargetPosition(backLeftMotor.getCurrentPosition() + backLeftMotorTargetPositionChange);
    }

    //NOT ACCURATE, WILL LIKELY TURN BOT AND NOT GO WHERE YOU EXPECT
//    public void setTargetLocationChanges(int strafeDistanceInMM, int driveDistanceInMM, double maxSpeed){
//        double frontLeftPower = driveDistanceInMM + strafeDistanceInMM;
//        double frontRightPower = driveDistanceInMM - strafeDistanceInMM;
//        double backLeftPower = driveDistanceInMM - strafeDistanceInMM;
//        double backRightPower = driveDistanceInMM + strafeDistanceInMM;
//
//        double max = Math.max(frontLeftPower, Math.max(frontRightPower, Math.max(backLeftPower, backRightPower)));
//
//        frontLeftPower = (frontLeftPower/max)*maxSpeed;
//        frontRightPower = (frontRightPower/max)*maxSpeed;
//        backLeftPower = (backLeftPower/max)*maxSpeed;
//        backRightPower = (backRightPower/max)*maxSpeed;
//
//
//        int frontRightMotorTargetPositionChange = 0;
//        int frontLeftMotorTargetPositionChange = 0;
//        int backRightMotorTargetPositionChange = 0;
//        int backLeftMotorTargetPositionChange = 0;
//
//        frontRightMotor.setTargetPosition(frontRightMotor.getCurrentPosition() + frontRightMotorTargetPositionChange);
//        frontLeftMotor.setTargetPosition(frontLeftMotor.getCurrentPosition() + frontLeftMotorTargetPositionChange);
//        backRightMotor.setTargetPosition(backRightMotor.getCurrentPosition() + backRightMotorTargetPositionChange);
//        backLeftMotor.setTargetPosition(backLeftMotor.getCurrentPosition() + backLeftMotorTargetPositionChange);
//    }








}
