package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="Motor_Test")
public class Motor_Test extends OpMode {

        private DcMotor slide;
        private DcMotor arm;
        private boolean isRunningToPosition = false;

        @Override
        public void init() {
                slide = hardwareMap.get(DcMotor.class, "slide");
                slide.setDirection(DcMotorSimple.Direction.FORWARD);
                slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                arm = hardwareMap.get(DcMotor.class, "arm");
                arm.setDirection(DcMotorSimple.Direction.FORWARD);
                arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        @Override
        public void loop() {
                // Gamepad control
                if (!isRunningToPosition) {
                        double power = gamepad2.left_stick_y;
                        slide.setPower(power);
                }

                // RunToPosition when button is pressed
                if (gamepad2.a && !isRunningToPosition) {
                        slide.setTargetPosition(10000);
                        arm.setTargetPosition(10000);
                        slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        slide.setPower(0.5);
                        arm.setPower(0.5);
                        isRunningToPosition = true;
                }

                if (isRunningToPosition && !slide.isBusy()) {
                        slide.setPower(0);
                        arm.setPower(0);
                        slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        isRunningToPosition = false;
                }

                telemetry.addData("Motor Position", slide.getCurrentPosition());
                telemetry.addData("Is Running to Position", isRunningToPosition);
                telemetry.update();
        }
}