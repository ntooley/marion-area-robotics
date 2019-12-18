package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@Disabled
@Autonomous (name= "Olympian Crater Auto", group = "Autonomous")
public class OlympianRoverAuto extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor leftMotor1 = null;
    private DcMotor leftMotor2 = null;
    private DcMotor rightMotor1 = null;
    private DcMotor rightMotor2 = null;

    private Servo grabServo1 = null;
    private Servo grabServo2 = null;
    private Servo armServo = null;
    private DcMotor armMotor = null;
    private DcMotor lifterMotor = null;

    private Servo markerServo = null;

    private int tracker = 0;

    private double motorThreshold = 0.065;

    public void telemetrys() {

        telemetry.addData("Runtime: ", (runtime.seconds()));

        telemetry.addData("Left Motor 1 Power: ", leftMotor1.getPower());
        telemetry.addData("Left Motor 2 Power: ", leftMotor2.getPower());
        telemetry.addData("Right Motor 1 Power: ", rightMotor1.getPower());
        telemetry.addData("Right Motor 2 Power: ", rightMotor2.getPower());

        telemetry.addData("Left Motor 2 Position", leftMotor2.getCurrentPosition());

        telemetry.addData("Lifter Motor Position", lifterMotor.getCurrentPosition());
        telemetry.addData("Lifter Motor Power:  ", lifterMotor.getPower());

        telemetry.addData("Tracker:  ", tracker);


        telemetry.update();
    }

    public void driveInit() {

        leftMotor1 = hardwareMap.dcMotor.get("left motor 1");
        leftMotor2 = hardwareMap.dcMotor.get("left motor 2");
        rightMotor1 = hardwareMap.dcMotor.get("right motor 1");
        rightMotor2 = hardwareMap.dcMotor.get("right motor 2");
        rightMotor1.setDirection(DcMotor.Direction.REVERSE);
        rightMotor2.setDirection(DcMotor.Direction.REVERSE);


        leftMotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void armInit (){

        grabServo1 = hardwareMap.servo.get("grab servo 1");
        grabServo2 = hardwareMap.servo.get("grab servo 2");
        armServo = hardwareMap.servo.get("arm servo");
        armMotor = hardwareMap.dcMotor.get("arm motor");
        lifterMotor = hardwareMap.dcMotor.get("lifter motor");
        markerServo = hardwareMap.servo.get("marker servo");

        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lifterMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        grabServo2.setDirection(Servo.Direction.REVERSE);
        grabServo1.setPosition(.4);
    }

    @Override
    public void init() {
        driveInit();
        armInit();
    }

    @Override
    public void init_loop(){
        telemetrys();

    }

    @Override
    public void start() {
        runtime.reset();

    }


    public void move(double power1, double power2, double power3, double power4){

        leftMotor1.setPower(power1);
        leftMotor2.setPower(power2);
        rightMotor1.setPower(power3);
        rightMotor2.setPower(power4);
    }

    @Override
    public void loop() {
        telemetrys();

        //lowers robot
        if (runtime.time() <= 1){
            lifterMotor.setPower(.5);
        }

        if (runtime.time() >= 1.2) {
            lifterMotor.setPower(0);
        }

        //strafes robot off hook
        if (runtime.time() >= 2 && runtime.time() <= 2.15) {
            move(-.5, .5, .5, -.5);
        }

        /*moves robot forward
        if (runtime.time() >= 2.2 && runtime.time() <= 2.25){
            move(1,1,1,1);
        }*/
        //turns robot
        if (runtime.time() >= 3.5 && runtime.time() <= 3.6){
            move(-1,-1,1,1);
        }
        if (runtime.time() >= 3.75 && runtime.time() <= 4){
            move(1,1,1,1);
        }
        if (runtime.time() >= 4.1 && runtime.time() <= 4.4){
            move(-1,-1,1,1);
        }
        if (runtime.time() >= 4.5 && runtime.time() <= 6){
            move(1,1,1,1);
        }
        //moves robot forward
        if (runtime.time() >= 6 && runtime.time() <= 7.5){
            move(1,1,1,1);
        }

        //stops robot and drops marker
        if (runtime.time() >= 8 && runtime.time() <= 9.5){
            move(0,0,0,0);
            markerServo.setPosition(1);
        }

        if (runtime.time() >= 13 && runtime.time() <= 16){
            move(-1,-1,-1,-1);
        }

        if (runtime.time() >= 17) {
            move(0,0,0,0);
        }

    }

}
