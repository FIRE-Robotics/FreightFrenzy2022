package org.firstinspires.ftc.teamcode.util;

public class Vector2D {
    double distance;
    double angle;

    public Vector2D(double Distance, double Angle){
        distance = Distance;
        angle = Angle;
    }

    public double getAngle(){
        return angle;
    }

    public double getDistance(){
        return distance;
    }
}
