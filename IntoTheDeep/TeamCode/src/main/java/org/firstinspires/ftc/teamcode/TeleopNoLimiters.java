package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Teleop_No_Limiters")
public class TeleopNoLimiters extends OpMode {

    //Create Motor Variables
    DcMotor Motor_Right_Front;
    DcMotor Motor_Right_Back;
    DcMotor Motor_Left_Front;
    DcMotor Motor_Left_Back;
    DcMotor Motor_Arm;
    DcMotor Motor_Slide;

    Servo Claw_Tilt;
    Servo Claw_Open_Close;
    Servo Claw_Axis;

    double DriveSpeed = 0.7;

    /*double Slide_Max_Position_Offset;
    double Slide_Min_Position_Offset;
    double Slide_Max_Position_Real = 1600;
    //inches = 0.0075ticks + 17

    double Arm_Max_Position_Offset;
    double Arm_Min_Position_Offset;
    double Arm_Max_Position_Real = 2378;
    double Arm_Min_Position_Real = 1189;
    //26.42222 ticks per degree
    //Start out with 90 degrees (Motor perfectly vertical)
     */

    double Claw_Arm = 0.7;
    double Claw_Flat = 0.5278;
    double Close = 0.4;
    double Open = 0;
    double Center = 0.44;
    double Rotate90 = 1;

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
        Motor_Arm = hardwareMap.get(DcMotor.class, "Motor_Arm");
        Motor_Slide = hardwareMap.get(DcMotor.class,"Motor_Slide");

        Motor_Right_Back.setDirection(DcMotor.Direction.REVERSE);
        Motor_Left_Back.setDirection(DcMotor.Direction.REVERSE);


        Claw_Tilt = hardwareMap.get(Servo.class,"Claw_Tilt");
        Claw_Open_Close = hardwareMap.get(Servo.class,"Claw_Open_Close");
        Claw_Axis = hardwareMap.get(Servo.class,"Claw_Axis");

        /*Slide_Min_Position_Offset = Motor_Slide.getCurrentPosition();
        Slide_Max_Position_Offset = Motor_Slide.getCurrentPosition() + Slide_Max_Position_Real;

        Arm_Min_Position_Offset = Motor_Slide.getCurrentPosition() - Arm_Min_Position_Real;
        Arm_Max_Position_Offset = Motor_Slide.getCurrentPosition() + Arm_Max_Position_Real;
         */

    }

    @Override
    public void loop() {
        timer.startTime();

        //DriveTrain
        if (gamepad1.right_bumper) {
            DriveSpeed = 1;
        } else if (gamepad1.left_bumper) {
            DriveSpeed = 0.5;
        } else {
            DriveSpeed = 0.7;
        }
        if (gamepad1.right_trigger>0) {
            Motor_Right_Back.setPower(-gamepad1.right_trigger * DriveSpeed);
            Motor_Right_Front.setPower(gamepad1.right_trigger * DriveSpeed);
            Motor_Left_Back.setPower(-gamepad1.right_trigger * DriveSpeed);
            Motor_Left_Front.setPower(gamepad1.right_trigger * DriveSpeed);
        } else if (gamepad1.left_trigger>0) {
            Motor_Right_Back.setPower(gamepad1.right_trigger * DriveSpeed);
            Motor_Right_Front.setPower(-gamepad1.right_trigger * DriveSpeed);
            Motor_Left_Back.setPower(gamepad1.right_trigger * DriveSpeed);
            Motor_Left_Front.setPower(-gamepad1.right_trigger * DriveSpeed);
        } else {
            moveDriveTrain();

        }

        //Arm
        Motor_Arm.setPower(gamepad2.left_stick_y);

        Motor_Slide.setPower(gamepad2.right_stick_y);

        if (gamepad2.y)
            Claw_Tilt.setPosition(Claw_Arm);
        if (gamepad2.a)
            Claw_Tilt.setPosition(Claw_Flat);
        else {
            Claw_Tilt.setPosition(Claw_Tilt.getPosition()+(gamepad2.left_trigger)*0.01);
            Claw_Tilt.setPosition(Claw_Tilt.getPosition()-(gamepad2.right_trigger)*0.01);
        }

        if (gamepad2.b)
            Claw_Open_Close.setPosition(Close);
        if (gamepad2.x)
            Claw_Open_Close.setPosition(Open);

        if (gamepad2.dpad_right)
            Claw_Axis.setPosition(Rotate90);
        if (gamepad2.dpad_down)
            Claw_Axis.setPosition(Center);
        else {
            if (gamepad2.dpad_up)
                Claw_Axis.setPosition(Claw_Axis.getPosition()+0.01);
            if (gamepad2.dpad_left)
                Claw_Axis.setPosition(Claw_Axis.getPosition()-0.01);
        }


        // Driving logic based on mode

        telemetry.addData("CT:",(Claw_Tilt.getPosition()));
        telemetry.addData("COC:",(Claw_Open_Close.getPosition()));
        telemetry.addData("CA:",(Claw_Axis.getPosition()));
        telemetry.addData("Motor:",(Motor_Slide.getCurrentPosition()));
        telemetry.addData("Arm:",(Motor_Arm.getCurrentPosition()));

        telemetry.update();
    }
}