package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Test")
public class Test extends OpMode {

    //Create Motor Variables
    DcMotor Motor_Right_Front;
    DcMotor Motor_Right_Back;
    DcMotor Motor_Left_Front;
    DcMotor Motor_Left_Back;
    DcMotor Motor_Arm;


    public void sleep(int milis) {
        try {
            Thread.sleep(milis);
        } catch (Exception e) {
        }
    }

    @Override
    public void init() {

        //Map Drive Train Motors
        Motor_Right_Front = hardwareMap.get(DcMotor.class, "Motor_Right_Front");
        Motor_Right_Back = hardwareMap.get(DcMotor.class, "Motor_Right_Back");
        Motor_Left_Front = hardwareMap.get(DcMotor.class, "Motor_Left_Front");
        Motor_Left_Back = hardwareMap.get(DcMotor.class, "Motor_Left_Back");
        Motor_Arm = hardwareMap.get(DcMotor.class, "Motor_Tilt");

        Motor_Right_Front.setDirection(DcMotor.Direction.REVERSE);
        Motor_Left_Front.setDirection(DcMotor.Direction.REVERSE);

    }

    @Override
    public void loop() {

        //DriveTrain
        if (gamepad1.x == true) {
            Motor_Left_Back.setPower(0.1);
        }
        else {
            Motor_Left_Back.setPower(0);
        }
        if (gamepad1.a == true) {
            Motor_Right_Front.setPower(0.1);
        }
        else {
            Motor_Right_Front.setPower(0);
        }
        if (gamepad1.b == true) {
            Motor_Left_Front.setPower(0.1);
        }
        else {
            Motor_Left_Front.setPower(0);
        }
        if (gamepad1.y == true) {
            Motor_Right_Back.setPower(0.1);
        }
        else {
            Motor_Right_Back.setPower(0);
        }
        //Arm
        Motor_Arm.setPower(gamepad2.left_stick_y);
    }
}
