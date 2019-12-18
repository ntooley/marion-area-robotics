package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Olympian 2019", group="Teleop")
public class OlympianTeleop2019 extends OpMode {

    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor leftMotor1 = null;
    private DcMotor leftMotor2 = null;
    private DcMotor rightMotor1 = null;
    private DcMotor rightMotor2 = null;
    private DcMotor blockMotor = null;

    private Servo capServo = null;
    //private Servo grabServo2 = null;
    //private DcMotor lifterMotor = null;
    //private DcMotor lifterMotor2 = null;


    private int tracker = 0;
    private double servoPosition = .5;

    private double motorThreshold = 0.065;

    public void telemetrys() {

        telemetry.addData("Runtime: ", (runtime.seconds() / 60));

        telemetry.addData("Left Motor 1 Power: ", leftMotor1.getPower());
        telemetry.addData("Left Motor 2 Power: ", leftMotor2.getPower());
        telemetry.addData("Right Motor 1 Power: ", rightMotor1.getPower());
        telemetry.addData("Right Motor 2 Power: ", rightMotor2.getPower());

        telemetry.addData("Capstone Servo Position:  ", capServo.getPosition());
        telemetry.addLine("it is building");


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

        capServo = hardwareMap.servo.get("cap servo");
        blockMotor = hardwareMap.dcMotor.get("block motor");
        //grabServo2 = hardwareMap.servo.get("grab servo 2");
        //lifterMotor = hardwareMap.dcMotor.get("lifter motor");
        //lifterMotor2 = hardwareMap.dcMotor.get("lifter motor 2");

        //lifterMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //grabServo2.setDirection(Servo.Direction.REVERSE);
        capServo.setPosition(.5);
        //grabServo2.setPosition(0);
    }

    @Override
    public void init() {
        driveInit();
        armInit();
    }

    @Override
    public void start() {
        runtime.reset();
    }

    public void driveLoop() {
        // Driving control "telemetry"
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
        leftMotor1.setPower(coord1);
        leftMotor2.setPower(coord1);
        rightMotor1.setPower(coord);
        rightMotor2.setPower(coord);

    }

    public void armLoop(){

        /*if(gamepad2.left_trigger > motorThreshold){
            lifterMotor.setPower(gamepad2.left_trigger);
            //lifterMotor2.setPower(gamepad2.left_trigger);
        } else if(gamepad2.right_trigger > motorThreshold){
            lifterMotor.setPower(-gamepad2.left_trigger);
            //lifterMotor2.setPower(-gamepad2.left_trigger);
        } else {
            lifterMotor.setPower(0);
            //lifterMotor2.setPower(0);
        }*/

        if (gamepad2.x){
           blockMotor.setPower(.5);
        }
        if (gamepad2.y){
            blockMotor.setPower(-.5);
        }

        if(gamepad2.a){
            capServo.setPosition(.7);
            //grabServo2.setPosition(0);
        } else if(gamepad2.b){
            capServo.setPosition(.4);
            //grabServo2.setPosition(1);
        }
    }


    @Override
    public void loop() {
        driveLoop();
        armLoop();
        telemetrys();
    }

    @Override
    public void stop() {

    }
}