package org.firstinspires.ftc.teamcode.Movement;

import org.firstinspires.ftc.robotcore.external.Const;
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
     *
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

    /**
     * Will find the shortest path between 2 angles
     * @param startAngle Takes in an Angle object representing the start angle
     * @param finalAngle Takes in an Angle object representing the start end
     * @returns a double value holding the shortest path to get to the final angle in radians
     */
    public double findAngleDifferenceInRadians(Angle startAngle, Angle finalAngle){
        return findAngleDifferenceInRadians(startAngle.getAngleInRadians(), finalAngle.getAngleInRadians());
    }

    /**
     * Will find the shortest path between 2 angles
     * @param startAngle Takes in an Angle object representing the start angle
     * @param finalAngle Takes in an Angle object representing the start end
     * @returns a double value holding the shortest path to get to the final angle in degrees
     */
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

    /**
     * @param finalAngle The angle (in radians) that the robot should go to
     * @return It returns the direction and magnitude (in radians) that the robot should turn to reach
     * that angle starting from the angle that the imu returns.
     */
    public double findAngleFromCurrentAngleInRadians(double finalAngle){
        return findAngleDifferenceInRadians(activeLocation.getTrimmedAngleInRadians(), finalAngle);
    }


    /**
     * Finds the vector from a starting position to the ending position. The vector is field oriented, going off of the fields coordinate graph
     * @param startX Starting X position (field oriented)
     * @param startY Starting Y position (field oriented)
     * @param finalX Final X position (field oriented)
     * @param finalY Final Y position (field oriented)
     * @return Returns the vector from the start to end points. Is field oriented
     */
    public Vector2D getFieldOrientedVector(double startX, double startY, double finalX, double finalY){
        double netX = finalX - startX;
        double netY = finalY - startY;

        double distance = Math.hypot(netX, netY);

        double angle = Constants.PI_OVER_2 - Math.atan(Math.abs(netY/netX));

        if (netY < 0){
            angle += 2 * (Constants.PI_OVER_2 - angle);
        }
        if (netX < 0){
            angle = -angle;
        }


        return new Vector2D(distance, new Angle(angle));
    }

    /**
     * Finds the vector from a starting position to the ending position. The vector is robot oriented, going off of the robots point of view
     * @param startX Starting X position (Field oriented). Meant to be field oriented lol
     * @param startY Starting Y position (Field oriented)
     * @param finalX Final X position (Field oriented)
     * @param finalY Final Y position (Field oriented)
     * @return Returns the vector from the start to end points. Is robot oriented
     */
    public Vector2D getRobotOrientedVector(double startX, double startY, double finalX, double finalY){
        Vector2D fieldOrientedVector = getFieldOrientedVector(startX, startY, finalX, finalY);
        return new Vector2D(getFieldOrientedVector(startX, startY, finalX, finalY).getMagnitude(), new Angle(findAngleDifferenceInRadians(activeLocation.getTrimmedAngleInRadians(), fieldOrientedVector.getAngle().getTrimmedAngleInRadians())));
    }


}
