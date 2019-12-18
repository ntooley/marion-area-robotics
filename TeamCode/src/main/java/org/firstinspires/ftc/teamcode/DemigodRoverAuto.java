package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous (name = "Demigod Depot Auto", group = "Autonomous")
public class DemigodRoverAuto extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor leftMotor1 = null;
    private DcMotor leftMotor2 = null;
    private DcMotor rightMotor1 = null;
    private DcMotor rightMotor2 = null;

    private DcMotor lifterMotor = null;

    private Servo boxServo = null;

    private Servo pinionServo = null;
    private Servo rackServo = null;

    private int tracker = 0;

    public void telemetrys() {

        telemetry.addData("Runtime: ", (runtime.seconds()));

        telemetry.addData("Left Motor 1 Power: ", leftMotor1.getPower());
        telemetry.addData("Left Motor 2 Power: ", leftMotor2.getPower());
        telemetry.addData("Right Motor 1 Power: ", rightMotor1.getPower());
        telemetry.addData("Right Motor 2 Power: ", rightMotor2.getPower());
        telemetry.addData("Left Motor 1 Position: ", leftMotor1.getCurrentPosition());
        telemetry.addData("Left Motor 2 Position: ", leftMotor2.getCurrentPosition());
        telemetry.addData("Right Motor 1 Position: ", rightMotor1.getCurrentPosition());
        telemetry.addData("Right Motor 2 Position: ", rightMotor2.getCurrentPosition());

        telemetry.addData("Lifter Motor Power:  ", lifterMotor.getPower());
        telemetry.addData("Lifter Motor Position:", lifterMotor.getCurrentPosition());

        telemetry.addData("Box Servo: ", boxServo.getPosition());

        telemetry.addData("Tracker", tracker);

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

    public void lifterInit() {
        lifterMotor = hardwareMap.dcMotor.get("lifter motor");
        boxServo = hardwareMap.servo.get("box servo");

        pinionServo = hardwareMap.servo.get("pinion servo");
        rackServo = hardwareMap.servo.get("rack servo");
        // one needs to be reversed
        rackServo.setDirection(Servo.Direction.REVERSE);
        lifterMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }


    @Override
    public void init() {
        driveInit();
        lifterInit();
    }

    public void move(float power1, float power2, float power3, float power4){

        leftMotor1.setPower(power1);
        leftMotor2.setPower(power2);
        rightMotor1.setPower(power3);
        rightMotor2.setPower(power4);
    }

    @Override
    public void loop() {

        telemetrys();
        // drops robot off lander
        if (runtime.time() <= 3.1) {
            lifterMotor.setPower(1);
        }
        //stops lifter
        if (runtime.time() >= 3.1 && runtime.time() <= 3.2) {
            lifterMotor.setPower(0);
        }
        // turns robot off the hook
        if (runtime.time() >= 3.2 && runtime.time() <= 3.9){
            move(1, 1, -1, -1);
        }
        //moves forward
        if (runtime.time() >= 3.9 && runtime.time() <= 5.5) {
            move(0,0,0,0);
        }
        /*
        // stops robot
        if (runtime.time() >= 5.5 && runtime.time()<= 5.75){
            move(0,0,0,0);
        }
        // places marker
        if (runtime.time() >= 5.75 && runtime.time() <= 6.5){
            if (pinionServo.getPosition() != 1 && rackServo.getPosition() != 1){
                pinionServo.setPosition(1);
                rackServo.setPosition(1);
            }
            move(0,0,0,0);
        }

        if (runtime.time() >= 6.5 && runtime.time() <= 6.75){
            move(0,0,0,0);
            if (pinionServo.getPosition() != 0 && rackServo.getPosition() != 0){
                pinionServo.setPosition(0);
                rackServo.setPosition(0);
            }
        }

        if (runtime.time() >= 6.6 && runtime.time() <= 7.6){
            move(1,1,-1,-1);
        }

       if (runtime.time() <= 11.25 && runtime.time() >= 7.5) {
           move(1,1,1,1);

       }

       if (runtime.time() >= 11.25){
            move(0,0,0,0);
       }
       */
    }
}

