package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp (name= "Demigod" , group = "Teleop")
public class DemigodTeleop extends OpMode{

    //DECLARATION SECTION
    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor leftMotor1 = null;
    private DcMotor leftMotor2 = null;
    private DcMotor rightMotor1 = null;
    private DcMotor rightMotor2 = null;

    private double motorThreshold = 0.065;

    public void telemetrys() {

        telemetry.addData("Runtime: ", (runtime.seconds()));

        telemetry.addData("Left Motor 1 Power: ", leftMotor1.getPower());
        telemetry.addData("Left Motor 2 Power: ", leftMotor2.getPower());
        telemetry.addData("Right Motor 1 Power: ", rightMotor1.getPower());
        telemetry.addData("Right Motor 2 Power: ", rightMotor2.getPower());

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


    @Override
    public void init() {
       driveInit();
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

    public void ServoMethod(Servo servo, double Position, boolean button) {
        if (button) {
            if (servo.getPosition() != Position) {
                servo.setPosition(Position);
            }
        }
    }

    @Override
    public void loop() {
        driveLoop();
        telemetrys();
    }

    public void stop() {

    }
}