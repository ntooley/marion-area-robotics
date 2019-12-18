package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Disabled
@Autonomous (name = "Backup Auto", group = "Autonomous")
public class autoTest extends OpMode{

    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor leftMotor1 = null;
    private DcMotor rightMotor1 = null;
    private DcMotor leftMotor2 = null;
    private DcMotor rightMotor2 = null;


    @Override
    public void init() {
        leftMotor1 = hardwareMap.dcMotor.get("left motor 1");
        rightMotor1 = hardwareMap.dcMotor.get("right motor 1");
        leftMotor2 = hardwareMap.dcMotor.get("left motor 2");
        rightMotor2 = hardwareMap.dcMotor.get("right motor 2");

        leftMotor1.setDirection(DcMotor.Direction.REVERSE);
        leftMotor2.setDirection(DcMotor.Direction.REVERSE);
    }
    public void move(float power1, float power2, float power3, float power4){
        leftMotor1.setPower(power1);
        leftMotor2.setPower(power2);
        rightMotor1.setPower(power3);
        rightMotor2.setPower(power4);
    }
    @Override
    public void loop() {
        if(runtime.time() >= 0 && runtime.time() <= 2.45){
            move(1,1,1,1);
        }
        if(runtime.time() >= 2.45 && runtime.time() <= 3.6){
            move(-1,-1,1,1);
        }
        if(runtime.time() >= 3.6 && runtime.time() <= 6.45){
            move(1,1,1,1);
        }
        if(runtime.time() >= 6.45){
            move(0,0,0,0);
        }
    }
}
/*package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import static android.os.SystemClock.sleep;

/**
 * Created by mars on 11/14/17.
 /
@Disabled
@Autonomous(name="AutoTestingStuff", group="Testing")
public class autoTest extends OpMode {

    private DcMotor leftMotor = null;
    private DcMotor rightMotor = null;

    @Override
    public void init() {

        leftMotor  = hardwareMap.dcMotor.get("left motor");
        rightMotor = hardwareMap.dcMotor.get("right motor");

        rightMotor.setDirection(DcMotor.Direction.REVERSE);

        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    @Override
    public void start() {

    }

    public void motorPowerLoop(double Power){

        leftMotor.setPower(Power);
        rightMotor.setPower(Power);

    }

    @Override
    public void loop() {

        int motorPos = leftMotor.getCurrentPosition();

        telemetry.addData("Left Position: ", leftMotor.getCurrentPosition());
        telemetry.addData("Right Position: ", rightMotor.getCurrentPosition());

        if(motorPos <= 5000){

            motorPowerLoop(.2);

        } else if(motorPos <= 10000){

            motorPowerLoop(.4);

        } else if(motorPos <= 15000){

            motorPowerLoop(.6);

        } else if(motorPos <= 20000){

            motorPowerLoop(.8);

        } else if(motorPos <= 25000){

            motorPowerLoop(1);

        } else {

            motorPowerLoop(0);

        }


        /*if (leftMotor.getCurrentPosition() < 1000) {
            leftMotor.setTargetPosition(1000);
            rightMotor.setTargetPosition(1000);
        } else if (leftMotor.getCurrentPosition() >= 1000) {
            leftMotor.setTargetPosition(5000);
            rightMotor.setTargetPosition(5000);
        }

        leftMotor.setPower(1);
        rightMotor.setPower(1);
*/

        /*if (leftMotor.getCurrentPosition() <= 5000) {
            leftMotor.setPower(1);
            rightMotor.setPower(1);
        } else {
            leftMotor.setPower(0);
            rightMotor.setPower(0);
        }


        //We are going to test whether this is runable or not
        //sleep(1000);
    }

    @Override
    public void stop() {

    }
}
*/