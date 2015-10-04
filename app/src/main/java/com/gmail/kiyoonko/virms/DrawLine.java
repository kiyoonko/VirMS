package com.gmail.kiyoonko.virms;

/**
 * Created by JaehyeongLee on 10/3/15.
 */
public class DrawLine{
    public DrawLine(Vectors v1, Vectors v2)
    {
        final double dr = 0.01;
        double xi = v1.getX() + 0.1;
        double yi = v1.getY() + 0.1;
        double zi = v1.getZ();

        for (double i = xi; i < v2.getX() + 0.1; i=i+dr) {
            Vectors p = new Vectors(xi,yi,zi);
            yi = yi + dr;
            zi = zi + dr;
            DrawPoint(p);
        }
    }
}
