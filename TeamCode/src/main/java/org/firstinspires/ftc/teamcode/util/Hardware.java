package org.firstinspires.ftc.teamcode.util;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.checkerframework.checker.units.qual.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

public class Hardware {

    //Declare motors for the robot
    public DcMotor frontRightMotor = null;
    public DcMotor frontLeftMotor = null;
    public DcMotor backRightMotor = null;
    public DcMotor backLeftMotor = null;

    public DcMotor intakeMotor = null;
    public DcMotor slideMotor = null;

    public DcMotor carouselSpinner = null;

    public BNO055IMU imu = null;

    HardwareMap hwMap = null;

    public ElapsedTime runtime = new ElapsedTime();

    public Hardware (){

    }


    public void initialize(HardwareMap ahwMap){
        hwMap = ahwMap;

        frontRightMotor = hwMap.get(DcMotor.class, "frontRightMotor");
        frontLeftMotor = hwMap.get(DcMotor.class, "frontLeftMotor");
        backRightMotor = hwMap.get(DcMotor.class, "backRightMotor");
        backLeftMotor = hwMap.get(DcMotor.class, "backLeftMotor");

        intakeMotor = hwMap.get(DcMotor.class, "intakeMotor");
        slideMotor = hwMap.get(DcMotor.class, "slideMotor");

        carouselSpinner = hwMap.get(DcMotor.class, "carouselSpinner");



        //TODO: Set Imu Parameters and declaration



        //TODO: Set Motor Directions
        frontRightMotor.setDirection(FORWARD);
        frontLeftMotor.setDirection(REVERSE);
        backRightMotor.setDirection(FORWARD);
        backLeftMotor.setDirection(REVERSE);

        intakeMotor.setDirection(FORWARD);
        slideMotor.setDirection(FORWARD);
        carouselSpinner.setDirection(FORWARD);


        //TODO: Set motor zero power behavior
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        carouselSpinner.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //TODO: Set motor powers
        frontRightMotor.setPower(0);
        frontLeftMotor.setPower(0);
        backRightMotor.setPower(0);
        backLeftMotor.setPower(0);

        intakeMotor.setPower(0);
        slideMotor.setPower(0);

        carouselSpinner.setPower(0);

        //TODO: set Motor Mode
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        carouselSpinner.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


    }
}

