package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp (name="Wall-E Bot", group = "Teleop")
public class WallEBot extends OpMode{

    private DcMotor leftMotor1 = null;
    private DcMotor leftMotor2 = null;
    private DcMotor rightMotor1 = null;
    private DcMotor rightMotor2 = null;

    private double motorThreshold = 0.065;

    @Override
    public void init() {
        leftMotor1 = hardwareMap.dcMotor.get("left motor 1");
        leftMotor2 = hardwareMap.dcMotor.get("left motor 2");
        rightMotor1 = hardwareMap.dcMotor.get("right motor 1");
        rightMotor2 = hardwareMap.dcMotor.get("right motor 2");

        leftMotor1.setDirection(DcMotor.Direction.REVERSE);
        leftMotor2.setDirection(DcMotor.Direction.REVERSE);
        //leftMotor2.setDirection(DcMotor.Direction.REVERSE);

    }

    public void tankDrive(){
        leftMotor1.setPower(gamepad1.left_stick_y);
        leftMotor2.setPower(gamepad1.left_stick_y);
        rightMotor1.setPower(gamepad1.right_stick_y);
        rightMotor2.setPower(gamepad1.right_stick_y);
    }

    public void driveLoop() {
        //telemetry.addData("Status", "Running: " + runtime.toString());

        float leftX = -(gamepad1.left_stick_x);// assigning controller values to a variable
        float rightX = -(gamepad1.right_stick_x);
        float leftY = -(gamepad1.left_stick_y);
        float rightY = -(gamepad1.right_stick_y);

        float coord1 = leftY;
        float coord = rightY;
        DcMotor.Direction dir1 = DcMotor.Direction.REVERSE;
        DcMotor.Direction dir2 = DcMotor.Direction.REVERSE;
        DcMotor.Direction dir3 = DcMotor.Direction.FORWARD;
        DcMotor.Direction dir4 = DcMotor.Direction.FORWARD;

        if (((leftX >= motorThreshold) || (leftX <= -motorThreshold)) && ((rightX >= motorThreshold) || (rightX <= -motorThreshold))) {
            dir1 = DcMotor.Direction.FORWARD;
            dir2 = DcMotor.Direction.REVERSE;
            dir3 = DcMotor.Direction.FORWARD;
            dir4 = DcMotor.Direction.REVERSE;
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

        if (gamepad1.right_bumper){
            leftMotor1.setPower(gamepad1.left_stick_y/2);
            leftMotor2.setPower(gamepad1.left_stick_y/2);
            rightMotor1.setPower(gamepad1.right_stick_y/2);
            rightMotor2.setPower(gamepad1.right_stick_y/2);
        }



    }

    @Override
    public void loop() {
        //tankDrive();
        driveLoop();

    }

    public void stop(){

    }

}
