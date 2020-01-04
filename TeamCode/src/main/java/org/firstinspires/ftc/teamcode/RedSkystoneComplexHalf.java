/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Locale;

/*
 * This is an example LinearOpMode that shows how to use
 * the REV Robotics Color-Distance Sensor.
 *
 * It assumes the sensor is configured with the name "sensor_color_distance".
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
 */
@Autonomous(name = "Red Skystone Half", group = "Skystone")
//@Disabled                            // Comment this out to add to the opmode list
public class RedSkystoneComplexHalf extends LinearOpMode {

    ColorSensor sensorColor;
    DistanceSensor sensorDistance;

    static final float FORWARD_SPEED = .8f;
    static final float TURN_SPEED = .6f;

    String currentColor = "Unknown";
    String currentStatus = "Running";

    DcMotor leftMotor1 = null;
    DcMotor leftMotor2 = null;
    DcMotor rightMotor1 = null;
    DcMotor rightMotor2 = null;

    DcMotor yoinkMotor = null;

    float hsvValues[] = {0F, 0F, 0F};

    // values is a reference to the hsvValues array.
    final float values[] = hsvValues;

    int currentBlock = 6;

    /**
     * Note that the REV Robotics Color-Distance incorporates two sensors into one device.
     * It has an IR proximity sensor which is used to calculate distance and an RGB color sensor.
     *
     * There will be some variation in the values measured depending on whether you are using a
     * V3 color sensor versus the older V2 and V1 sensors, as the V3 is based around a different chip.
     *
     * For V1/V2, the light/distance sensor saturates at around 2" (5cm).  This means that targets that are 2"
     * or closer will display the same value for distance/light detected.
     *
     * For V3, the distance sensor as configured can handle distances between 0.25" (~0.6cm) and 6" (~15cm).
     * Any target closer than 0.25" will display as 0.25" and any target farther than 6" will display as 6".
     *
     * Note that the distance sensor function of both chips is built around an IR proximity sensor, which is
     * sensitive to ambient light and the reflectivity of the surface against which you are measuring. If
     * very accurate distance is required you should consider calibrating the raw optical values read from the
     * chip to your exact situation.
     *
     * Although you configure a single REV Robotics Color-Distance sensor in your configuration file,
     * you can treat the sensor as two separate sensors that share the same name in your op mode.
     *
     * In this example, we represent the detected color by a hue, saturation, and value color
     * model (see https://en.wikipedia.org/wiki/HSL_and_HSV).  We change the background
     * color of the screen to match the detected color.
     *
     * In this example, we  also use the distance sensor to display the distance
     * to the target object.
     *
     */

    public void move(double power){
        leftMotor1.setPower(power);
        rightMotor1.setPower(power);
        leftMotor2.setPower(power);
        rightMotor2.setPower(power);
    }

    public void strafeRight(double power){
        leftMotor1.setPower(power);
        rightMotor1.setPower(-power);
        leftMotor2.setPower(-power);
        rightMotor2.setPower(power);
    }

    public void strafeLeft(double power){
        leftMotor1.setPower(-power);
        rightMotor1.setPower(power);
        leftMotor2.setPower(power);
        rightMotor2.setPower(-power);
    }

    public void turnLeft(double power){
        leftMotor1.setPower(-power);
        leftMotor2.setPower(-power);
        rightMotor1.setPower(power);
        rightMotor2.setPower(power);
    }

    public void turnRight(double power){
        leftMotor1.setPower(power);
        leftMotor2.setPower(power);
        rightMotor1.setPower(-power);
        rightMotor2.setPower(-power);
    }

    public void sensorBoilerplate(){
        // sometimes it helps to multiply the raw RGB values with a scale factor
        // to amplify/attentuate the measured values.
        final double SCALE_FACTOR = 255;

            // convert the RGB values to HSV values.
            // multiply by the SCALE_FACTOR.
            // then cast it back to int (SCALE_FACTOR is a double)
        Color.RGBToHSV((int) (sensorColor.red() * SCALE_FACTOR),
                    (int) (sensorColor.green() * SCALE_FACTOR),
                    (int) (sensorColor.blue() * SCALE_FACTOR),
                    hsvValues);

            // send the info back to driver station using telemetry function.
        telemetry.addData("Distance (cm)",
                    String.format(Locale.US, "%.02f", sensorDistance.getDistance(DistanceUnit.CM)));
        telemetry.addData("Hue", hsvValues[0]);
        telemetry.addData("Sat: ", hsvValues[1]);
        telemetry.addData("Value: ", hsvValues[2]);

        telemetry.addData("Block: ", currentColor);
        telemetry.addData("Status", currentStatus);

        telemetry.update();
    }

    public boolean skystoneCheck(){

        /*if (currentDistance <= 6.5) {
            if ((currentHue >= 70 && currentHue <= 100) && currentSat >= .6) {
                currentColor = "Normal";
            } else if (currentHue >= 100 && currentSat <= .5) {
                currentColor = "Skystone";
            }
        }*/



        // h >= 20 && h <= 50 && s >= .55
        if((hsvValues[0] >= 60 && hsvValues[0] <= 95) && hsvValues[1] >= .7){ //skystone check
            currentColor = "Normal";
            //skystoneTimer.reset(); // "stop" the clock
            telemetry.addLine("RESET");
            telemetry.update();
            teleUpdate();
            return false;
        } else if(hsvValues[1] <= .55 && hsvValues[0] >= 100){ // s <= .25
            currentColor = "Skystone"; // "resume" the clock
            //telemetry.addData("Timer", skystoneTimer.time());
            telemetry.update();
            teleUpdate();
            return true;
        } else {
            //skystoneTimer.reset(); // also "stop" the clock
            currentColor = "Unknown";
            telemetry.update();
            teleUpdate();
            return false;
        }
    }

    public void teleUpdate(){
        telemetry.addData("Block: ", currentColor);
        telemetry.addData("Status", currentStatus);

        telemetry.update();
    }

    @Override
    public void runOpMode() {

        ElapsedTime runtime = new ElapsedTime();

        leftMotor1 = hardwareMap.dcMotor.get("left motor 1");
        rightMotor1 = hardwareMap.dcMotor.get("right motor 1");
        leftMotor2 = hardwareMap.dcMotor.get("left motor 2");
        rightMotor2 = hardwareMap.dcMotor.get("right motor 2");

        yoinkMotor = hardwareMap.dcMotor.get("yoink motor");

        leftMotor1.setDirection(DcMotorSimple.Direction.FORWARD);
        leftMotor2.setDirection(DcMotorSimple.Direction.FORWARD);
        rightMotor1.setDirection(DcMotorSimple.Direction.REVERSE);
        rightMotor2.setDirection(DcMotorSimple.Direction.REVERSE);

        leftMotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        leftMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightMotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        // get a reference to the color sensor.
        sensorColor = hardwareMap.get(ColorSensor.class, "sensor_color_distance");

        // get a reference to the distance sensor that shares the same name.
        sensorDistance = hardwareMap.get(DistanceSensor.class, "sensor_color_distance");

        // wait for the start button to be pressed.
        waitForStart();

        double blockPower = .5;
        double grabTime = 1;

        // Move Up to the Stones
        sensorBoilerplate();
        while(opModeIsActive() && sensorDistance.getDistance(DistanceUnit.CM) >= 6.5) {
            move(.6);
            currentStatus = "Moving";
            sensorBoilerplate();
            teleUpdate();
        }

        move(0);
        sleep(1000);

        // Check For Skystone
        sensorBoilerplate();
        while (opModeIsActive() && !skystoneCheck()){
            sensorBoilerplate();
            strafeLeft(blockPower);
            teleUpdate();
        }

        //Insert Claw Grab
        move(0);
        yoinkMotor.setPower(1);
        runtime.reset();
        while (opModeIsActive() && runtime.time() <= grabTime){
            currentStatus = "Grabbing";
            teleUpdate();
        }

        // Backup
        yoinkMotor.setPower(.4);
        move(-FORWARD_SPEED);
        runtime.reset();
        while (opModeIsActive() && runtime.time() <= .45){
            currentStatus = "Backing Up";
            teleUpdate();
        }

        move(0);

        /* Im using this variable to sync up my to strafing commands
        The first one moves to the bridge, the second one moves away
        Thus, I created this to make sure that both commands are
        executed for the same amount of time, hopefully
         */
        double buildZoneTime = 3.0;

        // Strafe to bridge
        strafeRight(FORWARD_SPEED);
        runtime.reset();
        while (opModeIsActive() && runtime.time() <= buildZoneTime){
            currentStatus = "Strafing Right";
            teleUpdate();
        }

        // Claw Ungrab
        move(0);
        yoinkMotor.setPower(-1);
        runtime.reset();
        while(opModeIsActive() && runtime.time() <= grabTime){
            currentStatus = "Dropping";
            teleUpdate();
        }

        // Close claw, strafe back to the stones
        yoinkMotor.setPower(0);
        strafeLeft(FORWARD_SPEED);
        runtime.reset();
        while(opModeIsActive() && runtime.time() <= 1.5){
            currentStatus = "Strafing Left";
            teleUpdate();
        }

        move(0);
        sleep(2000);
    }
}
