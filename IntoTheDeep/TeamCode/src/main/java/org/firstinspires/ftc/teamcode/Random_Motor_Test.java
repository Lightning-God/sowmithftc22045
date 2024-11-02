package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Random_Slide_Test")
public class Random_Motor_Test extends OpMode {

    //Create Slide Variables
    DcMotor Slide;
    DcMotor Arm;
    boolean isSlideRunningToPosition = false;
    boolean isArmRunningToPosition = false;

    String Behavior;


    @Override
    public void init() {

        //Map Drive Train Slides
        Slide = hardwareMap.get(DcMotor.class, "Slide");
        Slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Slide.setDirection(DcMotorSimple.Direction.FORWARD);
        Arm = hardwareMap.get(DcMotor.class, "Arm");
        Arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Arm.setDirection(DcMotorSimple.Direction.FORWARD);

    }

    @Override
    public void loop() {

        // RunToPosition when button is pressed
        if (gamepad2.a && !isSlideRunningToPosition && !isArmRunningToPosition) {
            Slide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            Arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            Slide.setTargetPosition(1000);
            Arm.setTargetPosition(1000);
            Slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            Arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            Slide.setPower(1);
            Arm.setPower(1);
            isSlideRunningToPosition = true;
            isArmRunningToPosition = true;
            Slide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            Arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
        else if (gamepad2.b && !isSlideRunningToPosition && !isArmRunningToPosition) {
            Slide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            Arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            Slide.setTargetPosition(1000);
            Arm.setTargetPosition(1000);
            Slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            Arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            Slide.setPower(1);
            Arm.setPower(1);
            isSlideRunningToPosition = true;
            isArmRunningToPosition = true;
            Slide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            Arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
        else if (gamepad2.y && !isSlideRunningToPosition && !isArmRunningToPosition) {
            Slide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            Arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            Slide.setTargetPosition(1000);
            Arm.setTargetPosition(1000);
            Slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            Arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            Slide.setPower(1);
            Arm.setPower(1);
            isSlideRunningToPosition = true;
            isArmRunningToPosition = true;
            Slide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            Arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
        else if (gamepad2.x && !isSlideRunningToPosition && !isArmRunningToPosition) {
            Slide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            Arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            Slide.setTargetPosition(1000);
            Arm.setTargetPosition(1000);
            Slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            Arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            Slide.setPower(1);
            Arm.setPower(1);
            isSlideRunningToPosition = true;
            isArmRunningToPosition = true;
            Slide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            Arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        else if (isSlideRunningToPosition && !Slide.isBusy() && isArmRunningToPosition && !Arm.isBusy()) {
            Slide.setPower(0);
            Arm.setPower(0);
            Slide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            Arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            Slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            Arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            isSlideRunningToPosition = true;
            isArmRunningToPosition = false;
        }
        // Gamepad control
        else if (!isArmRunningToPosition && isSlideRunningToPosition) {
            Slide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            Arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            Arm.setPower(gamepad2.left_stick_y);
            if (gamepad2.left_stick_y < 0) {
                Slide.setTargetPosition(Slide.getCurrentPosition() + 100);
                Slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                Slide.setPower(1);
            } else {
                isSlideRunningToPosition = false;
            }
        }

        if (!isSlideRunningToPosition && !gamepad2.a && !gamepad2.b && !gamepad2.y && !gamepad2.x) {
            if (gamepad2.right_stick_y != 0) {
                Slide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                Slide.setPower(gamepad2.right_stick_y);
            }
            else {
                isSlideRunningToPosition = true;
            }
        }

        if (Slide.getZeroPowerBehavior() == DcMotor.ZeroPowerBehavior.BRAKE)
            Behavior = "Brake";
        else
            Behavior = "FLOAT";


        telemetry.addData("Slide Position", Slide.getCurrentPosition());
        telemetry.addData("Is Running to Position", isSlideRunningToPosition);
        telemetry.addData("Behavior", Behavior);
        telemetry.update();

    }
}