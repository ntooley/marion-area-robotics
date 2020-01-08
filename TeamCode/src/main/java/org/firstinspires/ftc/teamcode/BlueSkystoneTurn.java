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

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
 * This is an example LinearOpMode that shows how to use
 * the REV Robotics Color-Distance Sensor.
 *
 * It assumes the sensor is configured with the name "sensor_color_distance".
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
 */
@Autonomous(name = "Blue Skystone Turn", group = "Skystone")
// @Disabled                            // Comment this out to add to the opmode list
public class BlueSkystoneTurn extends LinearOpMode {

    ColorSensor sensorColor;
    DistanceSensor sensorDistance;

    ColorSensor floorSensor;

    static final double FORWARD_SPEED = TimeConstants.FORWARD_SPEED.getValue();
    static final double TURN_SPEED = TimeConstants.TURN_SPEED.getValue();

    String currentStatus = "Running";

    DcMotor leftMotor1 = null;
    DcMotor leftMotor2 = null;
    DcMotor rightMotor1 = null;
    DcMotor rightMotor2 = null;

    DcMotor yoinkMotor = null;

    AutoLibrary DriveTrain = new AutoLibrary();

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

    public void teleUpdate(){
        float[] values = DriveTrain.getHsvValues();

        telemetry.addData("L1 Power: ", leftMotor1.getPower());
        telemetry.addData("L2 Power: ", leftMotor2.getPower());
        telemetry.addData("R1 Power: ", rightMotor1.getPower());
        telemetry.addData("R2 Power: ", rightMotor2.getPower());

        telemetry.addData("Status: ", currentStatus);
        telemetry.addData("Block: ", DriveTrain.getCurrentBlock());
        telemetry.addData("Distance: ", DriveTrain.getCurrentDistance());
        telemetry.addData("Hue: ", values[0]);
        telemetry.addData("Saturation: ", values[1]);
        telemetry.addData("Value: ", values[2]);

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

        DriveTrain.motorFloat();

        // get a reference to the color sensor.
        sensorColor = hardwareMap.get(ColorSensor.class, "sensor_color_distance");

        // get a reference to the distance sensor that shares the same name.
        sensorDistance = hardwareMap.get(DistanceSensor.class, "sensor_color_distance");

        floorSensor = hardwareMap.get(ColorSensor.class, "floor sensor");

        DriveTrain.setMotors(leftMotor1, leftMotor2, rightMotor1, rightMotor2);

        DriveTrain.setColorSensor(sensorColor);
        DriveTrain.setDistanceSensor(sensorDistance);
        DriveTrain.setFloorSensor(floorSensor);

        waitForStart();
        // wait for the start button to be pressed.

        double blockPower = TimeConstants.BLOCK_POWER.getValue();
        double grabTime = TimeConstants.GRAB_TIME.getValue();
        double turnTime = TimeConstants.TURN_TIME.getValue();
        double tapePower = TimeConstants.TAPE_POWER.getValue();

        /* Im using this variable to sync up my to strafing commands
        The first one moves to the bridge, the second one moves away
        Thus, I created this to make sure that both commands are
        executed for the same amount of time, hopefully
         */
        double buildZoneTime = TimeConstants.BUILD_ZONE_TIME.getValue();

        // Move Up to the Stones
        while(opModeIsActive() && DriveTrain.getCurrentDistance() >= 5.8) {
            DriveTrain.move(blockPower);
            currentStatus = "Moving";
            teleUpdate();
        }

        DriveTrain.stop();
        sleep(500);

        // Check For Skystone
        while (opModeIsActive() && !DriveTrain.skystoneCheck()){
            DriveTrain.strafeRight(blockPower);
            teleUpdate();
        }

        //Insert Claw Grab
        DriveTrain.stop();
        yoinkMotor.setPower(.9);

        runtime.reset();
        while (opModeIsActive() && runtime.time() <= grabTime){
            currentStatus = "Grabbing";
            teleUpdate();
        }

        // Backup
        yoinkMotor.setPower(.3);
        DriveTrain.move(-FORWARD_SPEED);
        runtime.reset();
        while (opModeIsActive() && runtime.time() <= .4){
            currentStatus = "Backing Up";
            teleUpdate();
        }

        DriveTrain.stop();

        // turn to bridge
        DriveTrain.turnLeft(TURN_SPEED);
        runtime.reset();
        while (opModeIsActive() && runtime.time() <= turnTime){
            teleUpdate();
        }

        DriveTrain.stop();

        /*// move to bridge
        DriveTrain.move(FORWARD_SPEED);
        runtime.reset();
        while (opModeIsActive() && runtime.time() <= buildZoneTime){
            teleUpdate();
        }*/

        // Floor Check
        while (opModeIsActive() && !DriveTrain.floorCheck()){
            DriveTrain.move(tapePower);
            teleUpdate();
        }

        // Move past the tape
        DriveTrain.move(FORWARD_SPEED);
        runtime.reset();
        while (opModeIsActive() && runtime.time() <= .5){
            teleUpdate();
        }

        // Claw Ungrab
        DriveTrain.move(0);
        yoinkMotor.setPower(-1);
        runtime.reset();
        while(opModeIsActive() && runtime.time() <= grabTime){
            currentStatus = "Dropping";
            teleUpdate();
        }

        // Move back to stones
        DriveTrain.move(-FORWARD_SPEED);
        runtime.reset();
        while(opModeIsActive() && runtime.time() <= buildZoneTime){
            teleUpdate();
        }

        // Close claw, turn back to the stones
        yoinkMotor.setPower(0);
        DriveTrain.turnRight(TURN_SPEED);
        runtime.reset();
        while(opModeIsActive() && runtime.time() <= turnTime){
            currentStatus = "Strafing Left";
            teleUpdate();
        }

        DriveTrain.stop();

        // Move Up to the Stones
        while(opModeIsActive() && DriveTrain.getCurrentDistance() >= 5.5) {
            DriveTrain.move(.6);
            currentStatus = "Moving";
            teleUpdate();
        }

        DriveTrain.stop();
        sleep(500);

        // Skystone Check
        while (opModeIsActive() && !DriveTrain.skystoneCheck()){
            DriveTrain.strafeRight(blockPower);
            teleUpdate();
        }

        //Claw Grab
        DriveTrain.stop();
        yoinkMotor.setPower(.9);
        runtime.reset();
        while (opModeIsActive() && runtime.time() <= grabTime){
            currentStatus = "Grabbing";
            teleUpdate();
        }

        // move back
        DriveTrain.move(-FORWARD_SPEED);
        runtime.reset();
        while (opModeIsActive() && runtime.time() <= .6){
            currentStatus = "Backing Up";
            teleUpdate();
        }

        DriveTrain.stop();

        // turn to the bridge
        DriveTrain.turnLeft(TURN_SPEED);
        runtime.reset();
        while (opModeIsActive() && runtime.time() <= turnTime + .1){
            teleUpdate();
        }

        // Floor Check
        while (opModeIsActive() && !DriveTrain.floorCheck()){
            DriveTrain.move(tapePower);
            teleUpdate();
        }

        // Move past the tape
        DriveTrain.move(FORWARD_SPEED);
        runtime.reset();
        while (opModeIsActive() && runtime.time() <= .5){
            teleUpdate();
        }

        /*// move to the bridge
        DriveTrain.move(FORWARD_SPEED);
        runtime.reset();
        while(opModeIsActive() && runtime.time() <= buildZoneTime + 1){
            teleUpdate();
        }*/

        //Drop skystone
        DriveTrain.stop();
        yoinkMotor.setPower(-1);
        runtime.reset();
        while (opModeIsActive() && runtime.time() <= grabTime){
            currentStatus = "Dropping skystone";
            teleUpdate();
        }

        // Parking
        while (opModeIsActive() && !DriveTrain.floorCheck()){
            DriveTrain.move(-tapePower);
            teleUpdate();
        }

        DriveTrain.motorBrake();
        DriveTrain.stop();
        currentStatus = "Done";
        sleep(2000);

    }
}
