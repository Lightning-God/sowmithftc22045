package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Teleop")
public class Teleop extends OpMode {

    //Create Motor Variables
    DcMotor Motor_Right_Front;
    DcMotor Motor_Right_Back;
    DcMotor Motor_Left_Front;
    DcMotor Motor_Left_Back;
    DcMotor Motor_Tilt;
    DcMotor Motor_Slide;

    Servo Claw_Axis;
    Servo Claw_Open_Close;

    double DriveSpeed = 0.7;

    double Slide_Max_Position_Offset;
    double Slide_Min_Position_Offset;
    double Slide_Max_Position_Real = 1600;
    //inches = 0.0075ticks + 17

    double Wanted_Vertical_Height;
    double Wanted_Angle;

    double Tilt_Max_Position_Offset;
    double Tilt_Min_Position_Offset;
    double Tilt_Max_Position_Real = 2378;
    double Tilt_Min_Position_Real = 1189;
    //26.42222 ticks per degree
    //Start out with 90 degrees (Motor perfectly vertical)

    double Claw_Tilt = 0.7;
    double Claw_Flat = 0;
    double Close = 0.48;
    double Open = 0.2974;

    double High_Basket_Height = 3000;
    double Low_Basket_Height = 1200;
    double Low_Chamber_Height = 0;
    double High_Chamber_Height = 1200;


    //DriveTrain
    public void moveDriveTrain() {
        double vertical = 0;
        double horizontal = 0;
        double pivot = 0;

        horizontal = -gamepad1.left_stick_y * DriveSpeed;
        vertical = gamepad1.right_stick_x * DriveSpeed;

        Motor_Left_Back.setPower((pivot + (-vertical + horizontal)));
        Motor_Right_Front.setPower((pivot + (-vertical + horizontal)));
        Motor_Right_Back.setPower((pivot + (-vertical - horizontal)));
        Motor_Left_Front.setPower((pivot + (-vertical - horizontal)));

    }
    //Arm_Angle
    private void MoveSlide(int Ticks, double Speed) {
        Motor_Slide.setTargetPosition(Ticks);
        Motor_Slide.setPower(Speed);
        Motor_Slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }


    public void sleep(int milis) {
        try {
            Thread.sleep(milis);
        } catch (Exception e) {
        }
    }

    ElapsedTime timer;

    @Override
    public void init() {
        timer = new ElapsedTime();

        //Map Drive Train Motors
        Motor_Right_Front = hardwareMap.get(DcMotor.class, "Motor_Right_Front");
        Motor_Right_Back = hardwareMap.get(DcMotor.class, "Motor_Right_Back");
        Motor_Left_Front = hardwareMap.get(DcMotor.class, "Motor_Left_Front");
        Motor_Left_Back = hardwareMap.get(DcMotor.class, "Motor_Left_Back");
        Motor_Tilt = hardwareMap.get(DcMotor.class, "Motor_Arm");
        Motor_Slide = hardwareMap.get(DcMotor.class,"Motor_Slide");

        Motor_Right_Back.setDirection(DcMotor.Direction.REVERSE);
        Motor_Left_Back.setDirection(DcMotor.Direction.REVERSE);


        Claw_Axis = hardwareMap.get(Servo.class,"Claw_Tilt");
        Claw_Open_Close = hardwareMap.get(Servo.class,"Claw_Open_Close");

        Slide_Min_Position_Offset = Motor_Slide.getCurrentPosition();
        Slide_Max_Position_Offset = Motor_Slide.getCurrentPosition() + Slide_Max_Position_Real;

        Tilt_Min_Position_Offset = Motor_Slide.getCurrentPosition() - Tilt_Min_Position_Real;
        Tilt_Max_Position_Offset = Motor_Slide.getCurrentPosition() + Tilt_Max_Position_Real;

    }

    @Override
    public void loop() {
        timer.startTime();

        //DriveTrain
        if (gamepad1.right_bumper) {
            DriveSpeed = 0.7;
        } else if (gamepad1.left_bumper) {
            DriveSpeed = 0.3;
        } else {
            DriveSpeed = 0.5;
        }
        if (gamepad1.right_trigger>0) {
            Motor_Right_Back.setPower(-gamepad1.right_trigger * 0.7);
            Motor_Right_Front.setPower(gamepad1.right_trigger * 0.7);
            Motor_Left_Back.setPower(-gamepad1.right_trigger * 0.7);
            Motor_Left_Front.setPower(gamepad1.right_trigger * 0.7);
        } else if (gamepad1.b == true) {
            Motor_Right_Back.setPower(gamepad1.right_trigger * 0.7);
            Motor_Right_Front.setPower(-gamepad1.right_trigger * 0.7);
            Motor_Left_Back.setPower(gamepad1.right_trigger * 0.7);
            Motor_Left_Front.setPower(-gamepad1.right_trigger * 0.7);
        } else {
            moveDriveTrain();

        }

        //Arm




        if ((Motor_Tilt.getCurrentPosition()>=Tilt_Min_Position_Offset && gamepad2.left_stick_y>0) || (Motor_Tilt.getCurrentPosition()<=Slide_Max_Position_Offset && gamepad2.left_stick_y<0))
            Motor_Tilt.setPower(-gamepad2.left_stick_y*0.5);
        else {
            Motor_Tilt.setPower(0);
            Motor_Tilt.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }


        if ((Motor_Slide.getCurrentPosition()>=Slide_Min_Position_Offset && gamepad2.right_stick_y>0) || (Motor_Slide.getCurrentPosition()<=Slide_Max_Position_Offset && gamepad2.right_stick_y<0))
            Motor_Slide.setPower(-gamepad2.right_stick_y);
        else {
            Motor_Slide.setPower(0);
            Motor_Slide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        if (gamepad2.y)
            Claw_Axis.setPosition(Claw_Tilt);
        if (gamepad2.a)
            Claw_Axis.setPosition(Claw_Flat);
        else {
            Claw_Axis.setPosition(Claw_Axis.getPosition()+(gamepad2.left_trigger)*0.01);
            Claw_Axis.setPosition(Claw_Axis.getPosition()-(gamepad2.right_trigger)*0.01);
        }
        if (gamepad2.b)
            Claw_Open_Close.setPosition(Close);
        if (gamepad2.x)
            Claw_Open_Close.setPosition(Open);

        telemetry.addData("CA:",(Claw_Axis.getPosition()));
        telemetry.addData("COC:",(Claw_Open_Close.getPosition()));
        telemetry.addData("Motor:",(Motor_Slide.getCurrentPosition()));
        telemetry.addData("Difference:",(Motor_Slide.getCurrentPosition()-Slide_Min_Position_Offset));
        telemetry.addData("Tilt:",(Motor_Tilt.getCurrentPosition()));

        telemetry.update();
    }
}