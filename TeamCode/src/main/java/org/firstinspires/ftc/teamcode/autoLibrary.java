package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.lang.reflect.Array;
import java.util.Locale;

public class autoLibrary {

    private float hsvValues[] = {0F, 0F, 0F};

    // values is a reference to the hsvValues array.
    private final float values[] = hsvValues;

    private DcMotor leftMotor1;
    private DcMotor leftMotor2;
    private DcMotor rightMotor1;
    private DcMotor rightMotor2;

    private ColorSensor colorSensor;
    private DistanceSensor distanceSensor;

    private ColorSensor floorSensor;

    private String currentBlock = "";
    private String currentMotorStatus = "";


    public void setColorSensor(ColorSensor color){
        colorSensor = color;
    }

    public void setFloorSensor(ColorSensor floor){
        floorSensor = floor;
    }

    public void setDistanceSensor(DistanceSensor distance){
        distanceSensor = distance;
    }

    public void setMotors(DcMotor lm1, DcMotor lm2, DcMotor rm1, DcMotor rm2){
        leftMotor1 = lm1;
        leftMotor2 = lm2;
        rightMotor1 = rm1;
        rightMotor2 = rm2;
    }

    public String getCurrentBlock(){
        return currentBlock;
    }

    public double getCurrentDistance(){
        return distanceSensor.getDistance(DistanceUnit.CM);
    }

    public float[] getHsvValues(){
        return hsvValues;
    }

    public String getCurrentMotorStatus(){
        return currentMotorStatus;
    }

    public void move(double power){
        leftMotor1.setPower(power);
        leftMotor2.setPower(power);
        rightMotor1.setPower(power);
        rightMotor2.setPower(power);

        // currentMotorStatus = String.format("Motors Currently Moving at %p Power", power);
    }

    public void stop(){
        move(0);
        // currentMotorStatus = "Motors Stopped";
    }

    public void turnLeft(double power){
        leftMotor1.setPower(-power);
        leftMotor2.setPower(-power);
        rightMotor1.setPower(power);
        rightMotor2.setPower(power);

        // currentMotorStatus = String.format("Motors currently turning left at %p power", power);
    }

    public void turnRight(double power){
        leftMotor1.setPower(power);
        leftMotor2.setPower(power);
        rightMotor1.setPower(-power);
        rightMotor2.setPower(-power);

        // currentMotorStatus = String.format("Motors currently turning right at %p power", power);
    }

    public void strafeRight(double power){
        leftMotor1.setPower(power);
        rightMotor1.setPower(-power);
        leftMotor2.setPower(-power);
        rightMotor2.setPower(power);

        // currentMotorStatus = String.format("Motors currently strafing right at %p power", power);
    }

    public void strafeLeft(double power){
        leftMotor1.setPower(-power);
        rightMotor1.setPower(power);
        leftMotor2.setPower(power);
        rightMotor2.setPower(-power);

        // currentMotorStatus = String.format("Motors currently strafing left at %p power", power);
    }

    public float[] sensorLoop(){
        // sometimes it helps to multiply the raw RGB values with a scale factor
        // to amplify/attentuate the measured values.
        final double SCALE_FACTOR = 255;

        // convert the RGB values to HSV values.
        // multiply by the SCALE_FACTOR.
        // then cast it back to int (SCALE_FACTOR is a double)
        Color.RGBToHSV((int) (colorSensor.red() * SCALE_FACTOR),
                (int) (colorSensor.green() * SCALE_FACTOR),
                (int) (colorSensor.blue() * SCALE_FACTOR),
                hsvValues);

        // send the info back to driver station using telemetry function.
        /*telemetry.addData("Distance (cm)",
                String.format(Locale.US, "%.02f", distance.getDistance(DistanceUnit.CM)));
        telemetry.addData("Hue", hsvValues[0]);
        telemetry.addData("Sat: ", hsvValues[1]);
        telemetry.addData("Value: ", hsvValues[2]);

        telemetry.addData("Block: ", currentColor);
        telemetry.addData("Status", currentStatus);

        telemetry.update();*/

        return hsvValues;
    }

    private float[] floorLoop(){
        // sometimes it helps to multiply the raw RGB values with a scale factor
        // to amplify/attentuate the measured values.
        final double SCALE_FACTOR = 255;

        // convert the RGB values to HSV values.
        // multiply by the SCALE_FACTOR.
        // then cast it back to int (SCALE_FACTOR is a double)
        Color.RGBToHSV((int) (floorSensor.red() * SCALE_FACTOR),
                (int) (floorSensor.green() * SCALE_FACTOR),
                (int) (floorSensor.blue() * SCALE_FACTOR),
                hsvValues);

        // send the info back to driver station using telemetry function.
        /*telemetry.addData("Distance (cm)",
                String.format(Locale.US, "%.02f", distance.getDistance(DistanceUnit.CM)));
        telemetry.addData("Hue", hsvValues[0]);
        telemetry.addData("Sat: ", hsvValues[1]);
        telemetry.addData("Value: ", hsvValues[2]);

        telemetry.addData("Block: ", currentColor);
        telemetry.addData("Status", currentStatus);

        telemetry.update();*/

        return hsvValues;
    }

    public boolean skystoneCheck(){

        float[] hsvArray = sensorLoop();

        /*if (currentDistance <= 6.5) {
            if ((currentHue >= 70 && currentHue <= 100) && currentSat >= .6) {
                currentColor = "Normal";
            } else if (currentHue >= 100 && currentSat <= .5) {
                currentColor = "Skystone";
            }
        }*/



        // h >= 20 && h <= 50 && s >= .55
        if((hsvArray[0] >= 60 && hsvArray[0] <= 95) && hsvArray[1] >= .7){ //skystone check
            currentBlock = "Normal";
            //skystoneTimer.reset(); // "stop" the clock
            return false;
        } else if(hsvArray[1] <= .53 && hsvArray[0] >= 100){ // s <= .25
            currentBlock = "Skystone"; // "resume" the clock
            //telemetry.addData("Timer", skystoneTimer.time());
            return true;
        } else {
            //skystoneTimer.reset(); // also "stop" the clock
            currentBlock = "Unknown";
            return false;
        }
    }

    public boolean floorCheck(){
        float[] hsvArray = floorLoop();

        /*if (currentDistance <= 6.5) {
            if ((currentHue >= 70 && currentHue <= 100) && currentSat >= .6) {
                currentColor = "Normal";
            } else if (currentHue >= 100 && currentSat <= .5) {
                currentColor = "Skystone";
            }
        }*/



        // h >= 20 && h <= 50 && s >= .55
        if(hsvArray[1] >= .55){ //skystone check
            currentBlock = "Tape";
            //skystoneTimer.reset(); // "stop" the clock
            return true;
        } else { // s <= .25
            currentBlock = "Ground"; // "resume" the clock
            //telemetry.addData("Timer", skystoneTimer.time());
            return false;
        }
    }

}
