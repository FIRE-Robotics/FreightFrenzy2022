package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Hardware {

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

    HardwareMap hardwareMap = null;
    public ElapsedTime runtime = new ElapsedTime();

    public Hardware (HardwareMap hwMap){
        initialize(hwMap);
    }

    private void initialize(HardwareMap hwMap){
        hardwareMap = hwMap;


        //connect motors
        frontRightMotor = hardwareMap.get(DcMotor.class,"frontRightMotor");
        frontLeftMotor = hardwareMap.get(DcMotor.class,"frontLeftMotor");
        backRightMotor= hardwareMap.get(DcMotor.class,"backRightMotor");
        backLeftMotor = hardwareMap.get(DcMotor.class,"backLeftMotor");

        carouselSpinner = hardwareMap.get(DcMotor.class, "carouselSpinner");

        intakeSpinner = hardwareMap.get(DcMotor.class, "intakeSpinner");
        intakeLifter = hardwareMap.get(DcMotor.class, "intakeLifter");


        //set directions
        frontRightMotor.setDirection(DcMotor.Direction.FORWARD);
        frontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        backRightMotor.setDirection(DcMotor.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        carouselSpinner.setDirection(DcMotor.Direction.FORWARD);

        intakeSpinner.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeLifter.setDirection(DcMotorSimple.Direction.FORWARD);


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
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        carouselSpinner.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        intakeSpinner.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intakeLifter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
}
