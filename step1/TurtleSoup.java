/* Copyright (c) 2007-2014 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 * 
 * John Belak
 * Brayden Miranda
 */
package turtle;

import java.lang.Math;
import java.util.List;
import java.util.ArrayList;

public class TurtleSoup {

    /**
     * Draw a square.
     * 
     * @param turtle     the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {
        for (int i = 0; i < 4; i++) {
            turtle.forward(sideLength);
            turtle.turn(90.0);
        }
    }

    /**
     * Determine inside angles of a regular polygon.
     * 
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     * 
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
        if (sides <= 2) {
            throw new IllegalArgumentException("Sides must be greater than 2");
        }

        return ((sides - 2) * 180.0) / sides;
    }

    /**
     * Determine number of sides given the size of interior angles of a regular
     * polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see
     * java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle size of interior angles in degrees
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {

        return (int) Math.round(360.0 / (180.0 - angle));

    }

    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to
     * draw.
     * 
     * @param turtle     the turtle context
     * @param sides      number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
        double angle = calculateRegularPolygonAngle(sides);
        for (int i = 0; i < sides; i++) {
            turtle.forward(sideLength);
            turtle.turn(180 - angle);
        }
    }

    /**
     * Given the current direction, current location, and a target location,
     * calculate the heading
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in
     * the direction of
     * the target point (targetX,targetY), given that the turtle is already at the
     * point
     * (currentX,currentY) and is facing at angle currentHeading. The angle must be
     * expressed in
     * degrees, where 0 <= angle < 360.
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentHeading current direction as clockwise from north
     * @param currentX       currentY current location
     * @param targetX        targetY target point
     * @return adjustment to heading (right turn amount) to get to target point,
     *         must be 0 <= angle < 360.
     */
    public static double calculateHeadingToPoint(double currentHeading, int currentX, int currentY,
            int targetX, int targetY) {

        if (currentHeading >= 360 || currentHeading < 0) {
            throw new IllegalArgumentException("Current heading must fall [ 0 , 360 )");
        }

        double dx = targetX - currentX;
        double dy = targetY - currentY;

        double angleToTarget = Math.toDegrees(Math.atan2(dx, dy));
        if (angleToTarget < 0) {
            angleToTarget += 360.0;
        }

        double adjustment = angleToTarget - currentHeading;
        if (adjustment < 0) {
            adjustment += 360.0;
        }

        return adjustment;
    }

    /**
     * Given a sequence of points, calculate the heading adjustments needed to get
     * from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0
     * degrees).
     * For each subsequent point, assumes that the turtle is still facing in the
     * direction it was
     * facing when it moved to the previous point.
     * You should use calculateHeadingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of heading adjustments between points, of size (# of points) -
     *         1.
     */
    public static List<Double> calculateHeadings(List<Integer> xCoords, List<Integer> yCoords) {

        if (xCoords.size() != yCoords.size()) {
            throw new IllegalArgumentException("Coordinate lists must be of equal length.");
        }

        List<Double> headings = new ArrayList<>();
        double currentHeading = 0.0; // Turtle starts facing north

        for (int i = 1; i < xCoords.size(); i++) {
            double adjustment = calculateHeadingToPoint(
                    currentHeading,
                    xCoords.get(i - 1),
                    yCoords.get(i - 1),
                    xCoords.get(i),
                    yCoords.get(i));

            headings.add(adjustment);
            currentHeading = (currentHeading + adjustment) % 360;
        }

        return headings;
    }

    /**
     * Draws all points given a turtle, and the list of xCoordinates and
     * yCoordinates.
     * 
     * @param turtle  the turtle that will move across the screen and draw
     * @param xCoords the list of all of the xcoordinates of the shape
     * @param yCoords the list of all of the ycoordinates of the shape
     */
    public static void drawAllPoints(Turtle turtle, List<Integer> xCoords, List<Integer> yCoords) {

        if (xCoords.size() != yCoords.size()) {
            throw new IllegalArgumentException("X and Y coordinate list must be of the same size");
        }

        List<Double> headings = calculateHeadings(xCoords, yCoords);

        for (int i = 1; i < headings.size(); i++) {

            int x1 = xCoords.get(i - 1);
            int x2 = xCoords.get(i);

            int y1 = yCoords.get(i - 1);
            int y2 = yCoords.get(i);

            double distance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));

            turtle.turn(headings.get(i - 1));
            turtle.forward((int) distance);

        }

    }

    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a
     * turtle. For this
     * function, draw something interesting; the complexity can be as little or as
     * much as you want.
     * We'll be peer-voting on the different images, and the highest-rated one will
     * win a prize.
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {

        List<Integer> xCoords = new ArrayList<Integer>();
        List<Integer> yCoords = new ArrayList<Integer>();

        // STAR

        double angle = 0.0;
        double radius = 10.0;
        double angleStep = 144; // Star point angle
        int steps = 30;

        int centerX = 200;
        int centerY = 200;

        for (int i = 0; i < steps; i++) {
            int x = centerX + (int) (radius * Math.cos(Math.toRadians(angle)));
            int y = centerY + (int) (radius * Math.sin(Math.toRadians(angle)));
            xCoords.add(x);
            yCoords.add(y);

            angle += angleStep;
            radius += 5;
        }

        // SPIRAL

        int spiralCenterX = 0;
        int spiralCenterY = 0;
        double spiralAngle = 0.0;
        double spiralRadius = 0.0;

        while (spiralRadius < 200) {
            int x = (int) (spiralCenterX + spiralRadius * Math.cos(Math.toRadians(spiralAngle)));
            int y = (int) (spiralCenterY + spiralRadius * Math.sin(Math.toRadians(spiralAngle)));
            xCoords.add(x);
            yCoords.add(y);

            spiralAngle += 15; // rotate by 15 degrees per step
            spiralRadius += 2; // slowly grow outward
        }

        drawAllPoints(turtle, xCoords, yCoords);

    }

    /**
     * Main method.
     * 
     * This is the method that runs when you run "java TurtleSoup".
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();

        drawPersonalArt(turtle);

        // draw the window
        turtle.draw();

    }

}
