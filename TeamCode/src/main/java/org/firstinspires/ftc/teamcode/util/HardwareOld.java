package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.checkerframework.checker.units.qual.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

public class HardwareOld {

    //Possible reference = HardwarePushbot.java
    //Motors for drivetrain
    public DcMotor frontRightMotor = null;
    public DcMotor frontLeftMotor = null;
    public DcMotor backRightMotor = null;
    public DcMotor backLeftMotor = null;

    //Motor for carousel spinner
    public DcMotor carouselSpinner = null;

    //Motors for intake system
    public DcMotor intakeSpinner = null;
    public DcMotor intakeLifter = null;

    //IMU for gyroscope
    public BNO055IMU imu = null;

    HardwareMap hwMap = null;
    public ElapsedTime runtime = new ElapsedTime();


    //public Hardware (HardwareMap hwMap)
    public HardwareOld (){
        //initialize(hwMap);
    }

    public void initialize(HardwareMap ahwMap){
        hwMap = ahwMap;


        //connect motors
        frontRightMotor = hwMap.get(DcMotor.class,"frontRightMotor");
        frontLeftMotor = hwMap.get(DcMotor.class,"frontLeftMotor");
        backRightMotor= hwMap.get(DcMotor.class,"backRightMotor");
        backLeftMotor = hwMap.get(DcMotor.class,"backLeftMotor");

        carouselSpinner = hwMap.get(DcMotor.class, "carouselSpinner");

        intakeSpinner = hwMap.get(DcMotor.class, "intakeSpinner");
        intakeLifter = hwMap.get(DcMotor.class, "intakeLifter");

        //connect imu and set its settings
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        //TODO: If Sensor mode needs to be declared, and if so what
        //parameters.mode = BNO055IMU.SensorMode.


        imu = hwMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
        imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);


        //set directions
        frontRightMotor.setDirection(DcMotor.Direction.FORWARD);
        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backRightMotor.setDirection(DcMotor.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);

        carouselSpinner.setDirection(DcMotor.Direction.REVERSE);
//
        intakeSpinner.setDirection(DcMotor.Direction.FORWARD);
        intakeLifter.setDirection(DcMotor.Direction.REVERSE);


        //Set zero power behavior
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        carouselSpinner.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        intakeSpinner.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeLifter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        //set power to zero
        frontRightMotor.setPower(0);
        frontLeftMotor.setPower(0);
        backRightMotor.setPower(0);
        backLeftMotor.setPower(0);

        carouselSpinner.setPower(0);

        intakeSpinner.setPower(0);
        intakeLifter.setPower(0);


        //set Motor Mode
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        carouselSpinner.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        intakeSpinner.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeLifter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}

