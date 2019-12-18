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
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Disabled
@Autonomous(name="Red SkyStone Simple", group="Skystone")
public class titanSkystoneSimpleRed extends LinearOpMode {

    /* Declare OpMode members. */
    private DcMotor leftMotor2 = null;
    private DcMotor leftMotor1 = null;
    private DcMotor rightMotor1 = null;
    private DcMotor rightMotor2 = null;

    private Servo yoinkServo = null;

    private DcMotor slideMotor = null;


    private ElapsedTime     runtime = new ElapsedTime();


    static final double     FORWARD_SPEED = 0.8;
    static final double     TURN_SPEED    = 0.7;

    public void normalDir(){
        leftMotor1.setDirection(DcMotor.Direction.FORWARD);
        leftMotor2.setDirection(DcMotor.Direction.FORWARD);
        rightMotor1.setDirection(DcMotor.Direction.REVERSE);
        rightMotor2.setDirection(DcMotor.Direction.REVERSE);
    }

    public void strafeDir(){
        leftMotor1.setDirection(DcMotor.Direction.FORWARD);
        leftMotor2.setDirection(DcMotor.Direction.REVERSE);
        rightMotor1.setDirection(DcMotor.Direction.FORWARD);
        rightMotor2.setDirection(DcMotor.Direction.REVERSE);
    }

    public void move(double power){
        normalDir();

        leftMotor1.setPower(power);
        leftMotor2.setPower(power);
        rightMotor1.setPower(power);
        rightMotor2.setPower(power);
    }

    public void powerMotors(double power){
        // It's the move function but without the direction change

        leftMotor1.setPower(power);
        leftMotor2.setPower(power);
        rightMotor1.setPower(power);
        rightMotor2.setPower(power);
    }

    public void strafeRight(double power){
        strafeDir();
        powerMotors(power);
    }

    public void strafeLeft(double power){
        strafeDir();
        powerMotors(-power);
    }

    public void turnLeft(double power){
        leftMotor1.setPower(-power);
        leftMotor2.setPower(-power);
        rightMotor1.setPower(power);
        rightMotor2.setPower(-power);
    }

    public void turnRight(double power){
        leftMotor1.setPower(power);
        leftMotor2.setPower(power);
        rightMotor1.setPower(-power);
        rightMotor2.setPower(-power);
    }

    @Override
    public void runOpMode() {

        leftMotor1 = hardwareMap.dcMotor.get("left motor 1");
        leftMotor2 = hardwareMap.dcMotor.get("left motor 2");
        rightMotor1 = hardwareMap.dcMotor.get("right motor 1");
        rightMotor2 = hardwareMap.dcMotor.get("right motor 2");

        yoinkServo = hardwareMap.servo.get("yoink servo");

        slideMotor = hardwareMap.dcMotor.get("slide motor");

        yoinkServo.setPosition(0);


        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Step through each leg of the path, ensuring that the Auto mode has not been stopped along the way

        // Step 1:  Drive forward for 1.6 seconds
        move(FORWARD_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.6)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 2:  Stop and grab Skystone for 2 seconds
        move(0);
        yoinkServo.setPosition(1);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 2.0)) {
            telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 3: Move Backward for .6 second(s)
        move(-FORWARD_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < .6)){
            telemetry.addData("Path", "Leg 3: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 4: Strafe Left towards Foundation for 5 second(s)
        strafeRight(FORWARD_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 5)){
            telemetry.addData("Path", "Leg 4: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 5: Lift slides for 1 second(s)
        move(0);
        slideMotor.setPower(.6);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.0)){
            telemetry.addData("Path", "Leg 5: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 6: Move to foundation for 1.1 second(s)
        move(FORWARD_SPEED);
        slideMotor.setPower(0);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.1)) {
            telemetry.addData("Path", "Leg 6: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Pause for 1.1 second(s)
        move(0);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.1)) {
            telemetry.addData("Path", "Leg 6: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 7: Let go of stone for 2 second(s)
        move(0);
        yoinkServo.setPosition(0);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 2.0)){
            telemetry.addData("Path", "Leg 7: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 8: Back up for .6 second(s)
        move(-FORWARD_SPEED);
        // slideMotor.setPower(-.25);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < .6)){
            telemetry.addData("Path", "Leg 8: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Manual Self-Correction
        turnLeft(TURN_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < .3)) {
            telemetry.addData("Path", "Leg 6: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 9: Strafe to the left, toward the bridge
        yoinkServo.setPosition(1);
        strafeLeft(FORWARD_SPEED);
        slideMotor.setPower(-.15);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 3.5)){
            telemetry.addData("Path", "Leg 9: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step End:  Stop
        move(0);
        slideMotor.setPower(0);
        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);
    }
}
