package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Titan", group="Teleop")
public class TitanTeleop extends OpMode {

    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor leftMotor1 = null;
    private DcMotor leftMotor2 = null;
    private DcMotor rightMotor1 = null;
    private DcMotor rightMotor2 = null;

    private DcMotor slideMotor = null;
    private DcMotor yoinkMotor = null;

    private Servo foundationServo1 = null;
    private Servo foundationServo2 = null;

    private double motorThreshold = 0.065;

    private int tracker = 0;

    public void telemetrys() {

        telemetry.addData("Runtime: ", runtime.time());

        telemetry.addData("Left Motor 1 Power: ", leftMotor1.getPower());
        telemetry.addData("Left Motor 2 Power: ", leftMotor2.getPower());
        telemetry.addData("Right Motor 1 Power: ", rightMotor1.getPower());
        telemetry.addData("Right Motor 2 Power: ", rightMotor2.getPower());

        telemetry.addData("Left Motor 1 Port: ", leftMotor1.getPortNumber());
        telemetry.addData("Left Motor 2 Port: ", leftMotor2.getPortNumber());
        telemetry.addData("Right Motor 1 Port: ", rightMotor1.getPortNumber());
        telemetry.addData("Right Motor 2 Port: ", rightMotor2.getPortNumber());

        telemetry.addData("Slide Motor Power: ", slideMotor.getPower());
        telemetry.addData("Foundation 1 Position", foundationServo1.getPosition());
        telemetry.addData("Foundation 2 Position", foundationServo2.getPosition());

        telemetry.update();
    }

    public void driveInit() {
        
        leftMotor1 = hardwareMap.dcMotor.get("left motor 1");
        leftMotor2 = hardwareMap.dcMotor.get("left motor 2");
        rightMotor1 = hardwareMap.dcMotor.get("right motor 1");
        rightMotor2 = hardwareMap.dcMotor.get("right motor 2");

        slideMotor = hardwareMap.dcMotor.get("slide motor");

        yoinkMotor = hardwareMap.dcMotor.get("yoink motor");

        foundationServo1 = hardwareMap.servo.get("foundation 1");
        foundationServo2 = hardwareMap.servo.get("foundation 2");

        foundationServo1.setPosition(0);
        foundationServo2.setPosition(1);

        leftMotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        yoinkMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void init() {

        driveInit();
    }

    public void driveLoop() {
        telemetry.addData("Status", "Running: " + runtime.toString());
        telemetry.addData("RightX", gamepad1.right_stick_x);
        telemetry.addData("RightY", gamepad1.right_stick_y);
        telemetry.addData("LeftX", gamepad1.left_stick_x);
        telemetry.addData("LeftY", gamepad1.left_stick_y);

        float leftX = -(gamepad1.left_stick_x);// assigning controller values to a variable
        float rightX = -(gamepad1.right_stick_x);
        float leftY = -(gamepad1.left_stick_y);
        float rightY = -(gamepad1.right_stick_y);

        float coord1 = leftY;
        float coord = rightY;
        DcMotor.Direction dir1 = DcMotor.Direction.FORWARD;
        DcMotor.Direction dir2 = DcMotor.Direction.FORWARD;
        DcMotor.Direction dir3 = DcMotor.Direction.REVERSE;
        DcMotor.Direction dir4 = DcMotor.Direction.REVERSE;

        if (((leftX >= motorThreshold) || (leftX <= -motorThreshold)) && ((rightX >= motorThreshold) || (rightX <= -motorThreshold))) {
            dir1 = DcMotor.Direction.REVERSE;
            dir2 = DcMotor.Direction.FORWARD;
            dir3 = DcMotor.Direction.REVERSE;
            dir4 = DcMotor.Direction.FORWARD;
            coord1 = leftX;
            coord = rightX;
        }

        leftMotor1.setDirection(dir1);// Set to FORWARD if using AndyMark motors
        leftMotor2.setDirection(dir2);
        rightMotor1.setDirection(dir3);
        rightMotor2.setDirection(dir4);

        if (gamepad1.right_bumper){
            leftMotor1.setPower(coord1 / 2);
            leftMotor2.setPower(coord1 / 2);
            rightMotor1.setPower(coord / 2);
            rightMotor2.setPower(coord / 2);
        } else {
            leftMotor1.setPower(coord1);
            leftMotor2.setPower(coord1);
            rightMotor1.setPower(coord);
            rightMotor2.setPower(coord);
        }



    }

    @Override
    public void init_loop() {

        telemetrys();
    }

    public void gunnerLoop(){

        if (gamepad2.a){
            yoinkMotor.setPower(.5);
        } else if(gamepad2.b) {
            yoinkMotor.setPower(-.5);
        } else {
            yoinkMotor.setPower(0);
        }

        if (gamepad2.left_trigger > 0){
            slideMotor.setPower(gamepad2.left_trigger);
        } else if (gamepad2.right_trigger > 0){
            slideMotor.setPower(-gamepad2.right_trigger);
        }
        else{
            slideMotor.setPower(0);
        }
    }

    public void foundationLoop(){
        if (gamepad2.x){
            foundationServo1.setPosition(1);
            foundationServo2.setPosition(0);
        } else {
            if (foundationServo1.getPosition() != 0){
                foundationServo1.setPosition(0);
            }

            if (foundationServo2.getPosition() != 1){
                foundationServo2.setPosition(1);
            }
        }
    }



    @Override
    public void loop() {

        driveLoop();
        gunnerLoop();
        foundationLoop();


        telemetrys();
    }

    @Override
    public void stop() {

    }
}

/*

        telemetry.addData("Block Servo Position: ", blockServo.getPosition());

        telemetry.addData("Relic Arm Motor: ", relicMotor.getPower());
        telemetry.addData("Relic Arm Servo 1: ", relicServo1.getPosition());
        telemetry.addData("Relic Arm Servo 2: ", relicServo2.getPosition());

        telemetry.addData("Glyph Arm Motor Position: ", glyphArmMotor.getCurrentPosition());


       telemetry.addData("Joule Thief Servo Position: ", jouleThiefServo.getPosition());
       */

 /*

    public void blockServoInit() {

        blockServo = hardwareMap.servo.get("block servo");
        //blockServo.setPosition(0);
    }

    public void relicMotorInit() {

        relicMotor = hardwareMap.dcMotor.get("relic motor");

        relicMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void relicServoInit() {

        relicServo1 = hardwareMap.servo.get("relic servo 1");
        relicServo2 = hardwareMap.servo.get("relic servo 2");
        //relicServo1.setPosition(0);
        //relicServo2.setPosition(0);
        relicServo2.setDirection(Servo.Direction.REVERSE);

    }

    public void jouleThiefInit() {

        jouleThiefServo = hardwareMap.servo.get("joule thief");
        //jouleThiefServo.setPosition(1);
    }

    public void glyphArmInit() {

        glyphArmMotor = hardwareMap.dcMotor.get("glyph arm");
        glyphArmMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        glyphArmMotor.setPower(0);
    }
*/

/*


    public void blockServoLoop() {

        if(gamepad2.right_bumper) {
            if (blockServo.getPosition() != 1) {
                blockServo.setPosition(1);
            }
        } else {
            if (blockServo.getPosition() != 0){
                blockServo.setPosition(0);
            }
        }
    }

    public void jouleThiefLoop() {
        if (gamepad2.a) {
            if (jouleThiefServo.getPosition() != 1) {
                jouleThiefServo.setPosition(1);
            }
        } else if (gamepad2.b) {
            if (jouleThiefServo.getPosition() != 0) {
                jouleThiefServo.setPosition(0);
            }
        }
    }

    public void relicMotorLoop() {

        if (gamepad2.left_trigger > 0) {
            relicMotor.setPower(1);
        } else if (gamepad2.left_bumper) {
            relicMotor.setPower(-1);
        } else {
            relicMotor.setPower(0);
        }
    }

    public void relicServoLoop() {

        if (gamepad2.right_stick_y > 0) {
            relicServo2.setPosition(gamepad2.right_stick_y);
        } else if (gamepad2.right_stick_y < 0) {
            relicServo2.setPosition(-gamepad2.right_stick_y);
        } else {
            if (relicServo2.getPosition() != 0){
                relicServo2.setPosition(0);
            }
        }

        if (gamepad2.x) {
            if (relicServo1.getPosition() != 1) {
                relicServo1.setPosition(1);
                tracker = 1;
            }
        } else if (gamepad2.y) {
            if (relicServo1.getPosition() != 0) {
                relicServo1.setPosition(0);
                tracker = 0;
            }
        }
    }

    public void glyphArmLoop() {

        glyphArmMotor.setTargetPosition(0);
        glyphArmMotor.setPower(-.15);
    }
*/