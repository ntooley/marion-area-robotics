package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


@Disabled
@Autonomous (name="Foundation", group = "autonmous")
public class foundationAuto extends OpMode {

    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor leftMotor1 = null;
    private DcMotor leftMotor2 = null;
    private DcMotor rightMotor1 = null;
    private DcMotor rightMotor2 = null;

    public void motorbreak(DcMotor motor) {
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void telemetrys() {
        telemetry.addData("Left Motor 1 Power", leftMotor1.getPower());
        telemetry.addData("Left Motor 2 Power", leftMotor2.getPower());
        telemetry.addData("Right Motor 1 Power", rightMotor1.getPower());
        telemetry.addData("Right Motor 2 Power", rightMotor2.getPower());

        telemetry.update();
    }

    @Override
    public void init() {
        leftMotor1 = hardwareMap.dcMotor.get("left motor 1");
        leftMotor2 = hardwareMap.dcMotor.get("left motor 2");
        rightMotor1 = hardwareMap.dcMotor.get("right motor 1");
        rightMotor2 = hardwareMap.dcMotor.get("right motor 2");

        rightMotor1.setDirection(DcMotor.Direction.REVERSE);
        rightMotor2.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop() {
        //telemetrys();

        // go straight
        if (runtime.time() >= 0 && runtime.time() <= 2.5) {
            leftMotor1.setPower(1);
            leftMotor2.setPower(1);
            rightMotor1.setPower(1);
            rightMotor2.setPower(1);
        }

        // turn left
        if (runtime.time() >= 2.5 && runtime.time() <= 3.5) {

            leftMotor1.setPower(-1);
            leftMotor2.setPower(-1);
            rightMotor1.setPower(1);
            rightMotor2.setPower(1);
        }

        // go straight
        if (runtime.time() >= 4 && runtime.time() <= 5) {
            leftMotor1.setPower(1);
            leftMotor2.setPower(1);
            rightMotor1.setPower(1);
            rightMotor2.setPower(1);
        }

        // turn left
        if (runtime.time() >= 5.5 && runtime.time() <= 6.5) {
            leftMotor1.setPower(-1);
            leftMotor2.setPower(-1);
            rightMotor1.setPower(1);
            rightMotor2.setPower(1);
        }

        // go straight
        if (runtime.time() >= 7 && runtime.time() <= 10) {
            leftMotor1.setPower(1);
            leftMotor2.setPower(1);
            rightMotor1.setPower(1);
            rightMotor2.setPower(1);
        }

        else {
            leftMotor1.setPower(0);
            leftMotor2.setPower(0);
            rightMotor1.setPower(0);
            rightMotor2.setPower(0);
        }
    }
}



