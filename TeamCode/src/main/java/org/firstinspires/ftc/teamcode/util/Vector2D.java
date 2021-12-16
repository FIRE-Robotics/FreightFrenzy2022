package org.firstinspires.ftc.teamcode.util;

import com.sun.tools.javac.code.Attribute;

import org.firstinspires.ftc.robotcore.external.Const;

public class Vector2D {
    private double magnitude;
    private Angle angle;
    private double XComponent;
    private double YComponent;

    //TODO: Make a constructor with only start and end point as parameters.

    /**
     * Creates an object that stores the Magnitude, angle, xComponent, and Ycomponent of a line.
     * @param Magnitude The length or value of th eline
     * @param Angle The angle of the line in robotics circle (0 to +- PI to 0)
     */
    public Vector2D(double Magnitude, Angle Angle){
        magnitude = Magnitude;
        angle = Angle;


        double currentAngle = angle.getTrimmedAngleInRadians();
        double adjustedAngle = Math.abs(Math.abs(currentAngle) - Constants.PI_OVER_2);
        if (Math.abs(currentAngle) == Constants.PI)
            adjustedAngle = Constants.PI;

        XComponent= magnitude * Math.cos(adjustedAngle);
        if (currentAngle < 0){
            XComponent = -XComponent;
        }

        YComponent= magnitude * Math.sin(adjustedAngle);
        if (Math.abs(currentAngle) > Constants.PI_OVER_2){
            YComponent = -YComponent;
        }
    }

    //NOT DONE NEED TO FINISH ANGLE MATH
    public Vector2D(int startX, int startY, int endX, int endY){
        XComponent = endX - startX;
        YComponent = endY - startY;

        magnitude = Math.hypot(XComponent, YComponent);

        double rawAngle = Math.atan(YComponent/XComponent);

        if (YComponent < 0)
            rawAngle += Constants.PI_OVER_2;
        if (XComponent < 0)
            rawAngle = -rawAngle;

        angle = new Angle(rawAngle);


    }

    /**
     *
     * @returns the angle of the line
     */
    public Angle getAngle(){
        return angle;
    }

    /**
     *
     * @returns the length of the line
     */
    public double getMagnitude(){
        return magnitude;
    }

    /**
     *
     * @returns the X component of the line
     */
    public double getXComponent(){
        return XComponent;
    }

    /**
     *
      * @returns the y component of the line
     */
    public double getYComponent(){
        return YComponent;
    }

}
