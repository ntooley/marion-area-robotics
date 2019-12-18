package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
 * Created by mars on 11/11/17.
 */
@Disabled
@Autonomous(name = "Titan Auto", group = "Titans")
public class TitanAuto extends OpMode {

    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor leftMotor1 = null;
    private DcMotor leftMotor2 = null;
    private DcMotor rightMotor1 = null;
    private DcMotor rightMotor2 = null;

    private double prevTime = 0;

    private int tracker = 0;

    public void motorBrake(DcMotor motor) {
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void telemetrys(){

        telemetry.addData("Left Motor 1 Power: ", leftMotor1.getPower());
        telemetry.addData("Left Motor 2 Power: ", leftMotor2.getPower());
        telemetry.addData("Right Motor 1 Power: ", rightMotor1.getPower());
        telemetry.addData("Right Motor 2 Power: ", rightMotor2.getPower());

        telemetry.addData("Tracker: ", tracker);
        telemetry.addData("Prev Time: ", prevTime);
        telemetry.update();
    }

    public void driveInit() {

        leftMotor1 = hardwareMap.dcMotor.get("left motor 1");
        leftMotor2 = hardwareMap.dcMotor.get("left motor 2");
        rightMotor1 = hardwareMap.dcMotor.get("right motor 1");
        rightMotor2 = hardwareMap.dcMotor.get("right motor 2");

        motorBrake(leftMotor1);
        motorBrake(leftMotor2);
        motorBrake(rightMotor1);
        motorBrake(rightMotor2);
    }

    @Override
    public void init() {

        driveInit();
    }

    @Override
    public void init_loop() {

        telemetrys();
    }

    @Override
    public void start() {

    }

    public void move(double power) {
        leftMotor1.setDirection(DcMotor.Direction.FORWARD);
        leftMotor2.setDirection(DcMotor.Direction.FORWARD);
        rightMotor1.setDirection(DcMotor.Direction.REVERSE);
        rightMotor2.setDirection(DcMotor.Direction.REVERSE);

        leftMotor1.setPower(power);
        leftMotor2.setPower(power);
        rightMotor1.setPower(power);
        rightMotor2.setPower(power);
    }

    public void turnRight(double power){
        leftMotor1.setDirection(DcMotor.Direction.FORWARD);
        leftMotor2.setDirection(DcMotor.Direction.FORWARD);
        rightMotor1.setDirection(DcMotor.Direction.REVERSE);
        rightMotor2.setDirection(DcMotor.Direction.REVERSE);

        leftMotor1.setPower(power);
        leftMotor2.setPower(power);
        rightMotor1.setPower(-power);
        rightMotor2.setPower(-power);
    }

    public void turnLeft(double power){
        leftMotor1.setDirection(DcMotor.Direction.FORWARD);
        leftMotor2.setDirection(DcMotor.Direction.FORWARD);
        rightMotor1.setDirection(DcMotor.Direction.REVERSE);
        rightMotor2.setDirection(DcMotor.Direction.REVERSE);

        leftMotor1.setPower(-power);
        leftMotor2.setPower(-power);
        rightMotor1.setPower(power);
        rightMotor2.setPower(power);
    }

    public void botStrafeLeft(double power){
        leftMotor1.setDirection(DcMotor.Direction.FORWARD);
        leftMotor2.setDirection(DcMotor.Direction.REVERSE);
        rightMotor1.setDirection(DcMotor.Direction.FORWARD);
        rightMotor2.setDirection(DcMotor.Direction.REVERSE);

        leftMotor1.setPower(-power);
        leftMotor2.setPower(-power);
        rightMotor1.setPower(-power);
        rightMotor2.setPower(-power);
    }

    public void botStop(){
        move(0);
    }


    public void timeMoveForward(double time, double power, double order){
        /*
        * Accepts a double time and double power
        * Bot will move forward for the given time at the given power
        * Sets up prevTime for next timeMove function
        *
        * */
        if (tracker == order){
            double endTime = (prevTime + time);
            int newOrder = (tracker + 1);
            if (runtime.time() >= prevTime && runtime.time() < endTime){
                move(power);
            } else if(runtime.time() == endTime){
                prevTime = endTime;
                botStop();
                tracker = newOrder;
            }
        }

    }

    public void timeMoveBackward(double time, double power, int order){
        /*
        * Accepts a double time and double power
        * Bot will move backward for the given time at the given power
        * Sets up prevTime for next timeMove function
        *
        * */

        if (tracker == order){
            double endTime = (prevTime + time);
            int newOrder = (tracker + 1);
            if (runtime.time() >= prevTime && runtime.time() < endTime){
                move(-power);
            } else if(runtime.time() == endTime){
                prevTime = endTime;
                botStop();
                tracker = newOrder;
            }
        }
    }

    public void timeStrafeLeft(double time, double power){
        /*
         * Accepts a double time and double power
         * Bot will strafe for the given time at the given power
         * Sets up prevTime for next timeMove function
         *
         * */

        double endTime = (prevTime + time);
        if (runtime.time() >= prevTime && runtime.time() < endTime){
            leftMotor1.setPower(power);
        } else if(runtime.time() == endTime){
            prevTime = endTime;
            botStop();
        }
    }


    @Override
    public void loop() {
        telemetrys();
        timeMoveForward(3, 1, 0);
        timeMoveBackward(2,1,1);

    }

    @Override
    public void stop() {

    }
}
