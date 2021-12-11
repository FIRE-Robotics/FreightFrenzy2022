package org.firstinspires.ftc.teamcode.Movement;

import org.firstinspires.ftc.teamcode.util.Angle;
import org.firstinspires.ftc.teamcode.util.Constants;
import org.firstinspires.ftc.teamcode.util.Vector2D;

import java.util.Vector;

public class Pathfinder {
    ActiveLocation activeLocation;

    public Pathfinder(ActiveLocation ActiveLocation){
        activeLocation = ActiveLocation;
    }

    /**
     * @param trimmedStartAngle: The double value of an angle (radians) that the robot is currently in
     * @param trimmedFinalAngle: The double value of an angle (radians) that the robot should go to
     * @return: Returns the magnitude (in radians) and direction that the robot should go in to get to the final
     * angle from the starting angle.
     */
    public double findAngleDifferenceInRadians(double trimmedStartAngle, double trimmedFinalAngle){
        double clockwiseDistance = 0;
        double finalAngleDistance = 0;
        int startAngleSide;
        int finalAngleSide;

        //-1 signifies that the angle is negative, and 1 signifies that it is on the positive side
        //Detirmines if the angle is positive or negative
        if (trimmedStartAngle < 0)
            startAngleSide = -1;
        else
            startAngleSide = 1;

        if (trimmedFinalAngle < 0)
            finalAngleSide = -1;
        else
            finalAngleSide = 1;

        //if they are on the same side does simple math to find finalAngleDistance
        if (startAngleSide == finalAngleSide){
            finalAngleDistance = trimmedFinalAngle - trimmedStartAngle;
            return finalAngleDistance;
        }
        else{
            //moves final angle to go to 0
            finalAngleDistance += -trimmedStartAngle;
            finalAngleDistance += trimmedFinalAngle;

            if (finalAngleDistance > Constants.PI){
                finalAngleDistance = -Constants.TAU + finalAngleDistance;
            }
            if (finalAngleDistance < -Constants.PI){
                finalAngleDistance = Constants.TAU + finalAngleDistance;
            }
        }
        return finalAngleDistance;
    }


    /**
     * @param trimmedStartAngle: The double value of an angle (degrees) that the robot is currently in
     * @param trimmedFinalAngle: The double value of an angle (degrees) that the robot should go to
     * @return: Returns the magnitude (in degrees) and direction that the robot should go in to get to the final
     * angle from the starting angle.
     */
    public double findAngleDifferenceInDegrees(double trimmedStartAngle, double trimmedFinalAngle){
        double clockwiseDistance = 0;
        double finalAngleDistance = 0;
        int startAngleSide;
        int finalAngleSide;

        //-1 signifies that the angle is negative, and 1 signifies that it is on the positive side
        //Detirmines if the angle is positive or negative
        if (trimmedStartAngle < 0)
            startAngleSide = -1;
        else
            startAngleSide = 1;

        if (trimmedFinalAngle < 0)
            finalAngleSide = -1;
        else
            finalAngleSide = 1;

        //if they are on the same side does simple math to find finalAngleDistance
        if (startAngleSide == finalAngleSide){
            finalAngleDistance = trimmedFinalAngle - trimmedStartAngle;
            return finalAngleDistance;
        }
        else{
            //moves final angle to go to 0
            finalAngleDistance += -trimmedStartAngle;
            finalAngleDistance += trimmedFinalAngle;

            if (finalAngleDistance > 180){
                finalAngleDistance = -360 + finalAngleDistance;
            }
            if (finalAngleDistance < -180){
                finalAngleDistance = 360 + finalAngleDistance;
            }
        }
        return finalAngleDistance;
    }

    public double findAngleDifferenceInRadians(Angle startAngle, Angle finalAngle){
        return findAngleDifferenceInRadians(startAngle.getAngleInRadians(), finalAngle.getAngleInRadians());
    }

    public double findAngleDifferenceInDegrees(Angle startAngle, Angle finalAngle){
        return findAngleDifferenceInDegrees(startAngle.getTrimmedAngleInDegrees(), finalAngle.getTrimmedAngleInDegrees());
    }

    /**
     * @param finalAngle The angle (in degrees) that the robot should go to
     * @return It returns the direction and magnitude (in degrees) that the robot should turn to reach
     * that angle starting from the angle that the imu returns.
     */
    public double findAngleFromCurrentAngleInDegrees(double finalAngle){
        return findAngleDifferenceInDegrees(activeLocation.getTrimmedAngleInDegrees(), finalAngle);
    }

    public double findAngleFromCurrentAngleInRadians(double finalAngle){
        return findAngleDifferenceInRadians(activeLocation.getTrimmedAngleInRadians(), finalAngle);
    }

    public Vector2D getFieldOrientedVector(double startX, double startY, double finalX, double finalY){
        double netX = finalX - startX;
        double netY = finalY - startY;

        double distance = Math.sqrt(Math.pow(netX, 2) + Math.pow(netY, 2));
        double angle = Math.tan(netY/netX);

        return new Vector2D(distance, angle);
    }

    public Vector2D getRobotOrientedVector(double startX, double startY, double finalX, double finalY){
        Vector2D fieldOrientedVector = getFieldOrientedVector(startX, startY, finalX, finalY);
        return new Vector2D(fieldOrientedVector.getDistance(), findAngleDifferenceInRadians(activeLocation.getTrimmedAngleInRadians(), fieldOrientedVector.getAngle()));
    }


}
