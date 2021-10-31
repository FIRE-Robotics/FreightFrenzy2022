package org.firstinspires.ftc.teamcode.drivebase

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

abstract class Drivetrain {
    var maxSpeed = defaultMaxSpeed

    abstract fun stop()

    /**
     * Scales all wheel speeds to a scaling factor. This means that the max speed
     * will become <code>scaleTo</code>.
     *
     * @param wheelSpeeds the speeds to scale
     * @param scaleTo the scaling factor
     * @throws IllegalArgumentException when scaleTo is greater than 1
     */
    @kotlin.Throws(IllegalArgumentException::class)
    protected fun scaleSpeeds(wheelSpeeds: DoubleArray, scaleTo: Double) {
        require(scaleTo > 1) { "scaleTo can not be greater than 1" }

        var scaleTo = scaleTo
        val maxMagnitude = scalarMax(wheelSpeeds)
        scaleTo = min(maxSpeed, scaleTo)

        // Scales the speeds
        for (i in wheelSpeeds.indices) {
            wheelSpeeds[i] = wheelSpeeds[i] / maxMagnitude * scaleTo
        }
    }

    /**
     * Scales all wheel speeds so that none are greater than the maximum speed.
     *
     * @param wheelSpeeds the speeds to scale
     */
    protected fun scaleSpeeds(wheelSpeeds: DoubleArray) {
        if (wheelSpeeds.isEmpty())
            return
        val maxMagnitude = scalarMax(wheelSpeeds)
        if (maxMagnitude > maxSpeed) {
            for (i in wheelSpeeds.indices) {
                wheelSpeeds[i] = wheelSpeeds[i] / maxMagnitude * maxSpeed
            }
        }
    }

    /**
     * Finds the scalar maximum of an array of doubles
     *
     * Scalar Maximum means that only the magnitude, not the sign, will be taken into account
     * in the calculation
     *
     * @param values an array of values from which the maximum must be found
     * @return the maximum
     * @throws IllegalArgumentException protects against arrays with no elements
     */
    @kotlin.Throws(IllegalArgumentException::class)
    protected fun scalarMax(values: DoubleArray): Double {
        require(values.isNotEmpty()) { "There must be at least one value in the array" }
        var scalarMax = abs(values[0])
        for (i in 1 until values.size) {
            scalarMax = max(scalarMax, abs(values[i]))
        }
        return scalarMax
    }

    /**
     * Finds the vector maximum of an array of doubles
     *
     * Vector Maximum means that both the direction (positive/negative) and the magnitude are taken
     * into account, where positive numbers are all greater than negative numbers.
     *
     * @param values an array of values from which the maximum must be found
     * @return the maximum
     * @throws IllegalArgumentException protects against arrays with no elements
     */
    @kotlin.Throws(IllegalArgumentException::class)
    protected fun vectorMax(values: DoubleArray): Double {
        require(values.isNotEmpty()) { "There must be at least one value in the array" }
        var vectorMax = values[0]
        for (i in 1 until values.size) {
            vectorMax = vectorMax.coerceAtLeast(values[i])
        }
        return vectorMax
    }

    /**
     * Squares Inputs but maintains direction
     *
     * This is useful to implement non-linear increase for joystick powers.
     *
     * @param input the value to be squared
     * @return the value squared, maintaining the direction
     */
    protected fun squareInput(input: Double): Double {
        return (if (input < 0) -input else input) * input
    }

    companion object {
        protected const val PI4 = Math.PI / 4
        const val defaultMaxSpeed = 1.0
    }
}