package org.firstinspires.ftc.teamcode.drivebase

import com.qualcomm.robotcore.hardware.DcMotor

//import org.firstinspires.ftc.teamcode.java.util.MovementData
//import org.firstinspires.ftc.teamcode.java.util.Vector2d


class MecanumDrive(frontLeft: DcMotor, frontRight: DcMotor, backLeft: DcMotor, backRight: DcMotor) : Drivetrain() {
    internal enum class MotorPosition(var position: Int) {
        frontLeft(0), frontRight(1), backLeft(2), backRight(3);
    }

    /**
     * Creates an Array of Motors which is connected to the <code>MotorPosition</code>
     * enum to make modifying values easier
     */
    var motors: Array<DcMotor> = arrayOf(frontLeft, frontRight, backLeft, backRight)
//    var angleOffset = 0.0

//    constructor(frontLeft: DcMotor, frontRight: DcMotor, backLeft: DcMotor, backRight: DcMotor, angleOffset: Double) : this(frontLeft, frontRight, backLeft, backRight) {
//        this.angleOffset = angleOffset
//    }
//

    /**
     * Stop the motors
     */
    override fun stop() {
        for (motor in motors) {
            motor.power = 0.0
        }
    }

    /**
     * Sets each motor's power using the values passed in for each motor
     */
    private fun driveWithPower(frontLeft: Double, frontRight: Double, backLeft: Double, backRight: Double) {
        motors[MotorPosition.frontLeft.position].power = frontLeft
        motors[MotorPosition.frontRight.position].power = frontRight
        motors[MotorPosition.backLeft.position].power = backLeft
        motors[MotorPosition.backRight.position].power = backRight
    }

    /**
     * Sets each motor's power using the values passed in for each type of movement: driveSpeed,
     * strafeSpeed, and turnSpeed
     */
    fun drive(driveSpeed: Double, strafeSpeed: Double, turnSpeed: Double) {
        driveWithPower(
                driveSpeed + strafeSpeed + turnSpeed,
                driveSpeed - strafeSpeed - turnSpeed,
                driveSpeed - strafeSpeed + turnSpeed,
                driveSpeed + strafeSpeed - turnSpeed
        )
    }

    /**
     * Sets each motor's power using the values passed in for each type of movement: driveSpeed,
     * strafeSpeed, and turnSpeed after squaring the values, leading to quadratic growth in power
     */
    fun drive(driveSpeed: Double, strafeSpeed: Double, turnSpeed: Double, quadraticGrowth: Boolean) {
        if (quadraticGrowth)
            drive(squareInput(driveSpeed), squareInput(strafeSpeed), squareInput(turnSpeed))
        else
            drive(driveSpeed, strafeSpeed, turnSpeed)
    }

//    fun driveFieldOriented(driveSpeed: Double, strafeSpeed: Double, turnSpeed: Double, gyroAngle: Double) {
//        var translation = Vector2d(strafeSpeed, driveSpeed)
//        translation = translation.rotateBy(gyroAngle) //Maybe need to make negative angles
//        val theta: Double = translation.angle()
//        val wheelSpeeds = DoubleArray(4)
//        val flp = MotorPosition.frontLeft.position
//        val frp = MotorPosition.frontRight.position
//        val blp = MotorPosition.backLeft.position
//        val brp = MotorPosition.backRight.position
//        wheelSpeeds[flp] = Math.sin(theta + PI4)
//        wheelSpeeds[frp] = Math.sin(theta - PI4)
//        wheelSpeeds[blp] = Math.sin(theta - PI4)
//        wheelSpeeds[brp] = Math.sin(theta + PI4)
//        scaleSpeeds(wheelSpeeds, translation.magnitude())
//        wheelSpeeds[flp] += turnSpeed
//        wheelSpeeds[frp] -= turnSpeed
//        wheelSpeeds[blp] -= turnSpeed
//        wheelSpeeds[brp] += turnSpeed
//        scaleSpeeds(wheelSpeeds)
//        motors[flp].power = wheelSpeeds[flp] * maxSpeed
//        motors[frp].power = wheelSpeeds[frp] * maxSpeed
//        motors[blp].power = wheelSpeeds[blp] * maxSpeed
//        motors[brp].power = wheelSpeeds[brp] * maxSpeed
//    }

//    fun driveFieldOriented(driveSpeed: Double, strafeSpeed: Double, turnSpeed: Double, gyroAngle: Double, quadraticGrowth: Boolean) {
//        var driveSpeed = driveSpeed
//        var strafeSpeed = strafeSpeed
//        var turnSpeed = turnSpeed
//        if (quadraticGrowth) {
//            driveSpeed = squareInput(driveSpeed)
//            strafeSpeed = squareInput(strafeSpeed)
//            turnSpeed = squareInput(turnSpeed)
//        }
//        driveFieldOriented(driveSpeed, strafeSpeed, turnSpeed, gyroAngle)
//    }

    companion object {
        fun calculateDrivePowers(driveSpeed: Double, strafeSpeed: Double, turnSpeed: Double): DoubleArray {
            return doubleArrayOf(
                    driveSpeed + strafeSpeed + turnSpeed,
                    driveSpeed - strafeSpeed - turnSpeed,
                    driveSpeed - strafeSpeed + turnSpeed,
                    driveSpeed + strafeSpeed - turnSpeed
            )
        }
    }

}