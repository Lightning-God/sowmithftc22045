package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Teleop_Automation")
public class Teleop_Automation extends OpMode {


    //Motor Object Declarations
    DcMotor motorRightFront;
    DcMotor motorRightBack;
    DcMotor motorLeftFront;
    DcMotor motorLeftBack;
    DcMotor motorArm;
    DcMotor motorSlide;

    //Servo Object Declarations
    Servo servoClawAxis;
    Servo servoClawOpenClose;

    //Motor Control Variables
    double driveSpeed = 0.7;

    //inches = 0.0075ticks + 17
    double slideHighBasketValue;
    double slideLowBasketValue;
    double slideHighChamberValue;
    double slideLowChamberValue;

    //26.42222 ticks per degree
    double armHighBasketValue;
    double armLowBasketValue;
    double armHighChamberValue;
    double armLowChamberValue;

    //Claw Constants
    double clawTilt = 0.7;
    double clawFlat = 0;
    double close = 0.48;
    double open = 0.2974;
    double openCloseMode = 0;

    //DriveTrain
    public void moveDriveTrain() {
        //DriveTrain
        if (gamepad1.right_bumper) {
            driveSpeed = 0.7;
        } else if (gamepad1.left_bumper) {
            driveSpeed = 0.3;
        } else {
            driveSpeed = 0.5;
        }
        if (gamepad1.x) {
            motorRightBack.setPower(-gamepad1.right_trigger * driveSpeed);
            motorRightFront.setPower(gamepad1.right_trigger * driveSpeed);
            motorLeftBack.setPower(-gamepad1.right_trigger * driveSpeed);
            motorLeftFront.setPower(gamepad1.right_trigger * driveSpeed);
        } else if (gamepad1.b == true) {
            motorRightBack.setPower(gamepad1.right_trigger * driveSpeed);
            motorRightFront.setPower(-gamepad1.right_trigger * driveSpeed);
            motorLeftBack.setPower(gamepad1.right_trigger * driveSpeed);
            motorLeftFront.setPower(-gamepad1.right_trigger * driveSpeed);
        } else {
            double vertical = 0;
            double horizontal = 0;
            double pivot = 0;

            horizontal = -gamepad1.left_stick_y * driveSpeed;
            vertical = gamepad1.right_stick_x * driveSpeed;

            motorLeftBack.setPower((pivot + (-vertical + horizontal)));
            motorRightFront.setPower((pivot + (-vertical + horizontal)));
            motorRightBack.setPower((pivot + (-vertical - horizontal)));
            motorLeftFront.setPower((pivot + (-vertical - horizontal)));
        }
    }

    public void sleep(int milis) {
        try {
            Thread.sleep(milis);
        } catch (Exception e) {
        }
    }
    private void setSlideMove(int inches, double speed) {
        motorSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motorSlide.setTargetPosition(inches);
        motorSlide.setPower(speed);
        motorSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    private void setArmRotate(int degrees, double speed) {
        motorArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motorArm.setTargetPosition(degrees);
        motorArm.setPower(speed);
        motorArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    private void setHighBasket(){
        if (gamepad2.dpad_up) {
            setSlideMove(0, 1);
            setArmRotate(0, 1);
        }
        else{
            if (gamepad2.right_bumper);
            openCloseMode = (openCloseMode+1) % 2;
            if (openCloseMode == 0)
                servoClawOpenClose.setPosition(open);
            else {
                servoClawOpenClose.setPosition(close);
                servoClawAxis.setPosition(servoClawAxis.getPosition() + (gamepad2.left_trigger) * 0.01);
                servoClawAxis.setPosition(servoClawAxis.getPosition() - (gamepad2.right_trigger) * 0.01);
            }
        }
    }
    private void setLowBasket(){
        if (gamepad2.dpad_right) {
            setSlideMove(0, 1);
            setArmRotate(0, 1);
        }
        else{
            if (gamepad2.right_bumper);
            openCloseMode = (openCloseMode+1) % 2;
            if (openCloseMode == 0)
                servoClawOpenClose.setPosition(open);
            else {
                servoClawOpenClose.setPosition(close);
                servoClawAxis.setPosition(servoClawAxis.getPosition() + (gamepad2.left_trigger) * 0.01);
                servoClawAxis.setPosition(servoClawAxis.getPosition() - (gamepad2.right_trigger) * 0.01);
            }
        }
    }
    private void setHighChamber(){
        if (gamepad2.dpad_down) {
            setSlideMove(0, 1);
            setArmRotate(0, 1);
        }
        else{
            if (gamepad2.right_bumper);
            openCloseMode = (openCloseMode+1) % 2;
            if (openCloseMode == 0)
                servoClawOpenClose.setPosition(open);
            else{
                servoClawOpenClose.setPosition(close);
                servoClawAxis.setPosition(servoClawAxis.getPosition()+(gamepad2.left_trigger)*0.01);
                servoClawAxis.setPosition(servoClawAxis.getPosition()-(gamepad2.right_trigger)*0.01);
            }
        }

    }
    private void setLowChamber(){
        if (gamepad2.dpad_left) {
            setSlideMove(0, 1);
            setArmRotate(0, 1);
        }
        else{
            if (gamepad2.right_bumper);
            openCloseMode = (openCloseMode+1) % 2;
            if (openCloseMode == 0)
                servoClawOpenClose.setPosition(open);
            else {
                servoClawOpenClose.setPosition(close);
                servoClawAxis.setPosition(servoClawAxis.getPosition() + (gamepad2.left_trigger) * 0.01);
                servoClawAxis.setPosition(servoClawAxis.getPosition() - (gamepad2.right_trigger) * 0.01);
            }
        }
    }
    private void setFeed(){
        if (gamepad2.y) {
            setSlideMove(0, 1);
            setArmRotate(0, 1);
        }
        else{
            if (gamepad2.right_bumper);
            openCloseMode = (openCloseMode+1) % 2;
            if (openCloseMode == 0)
                servoClawOpenClose.setPosition(open);
            else {
                servoClawOpenClose.setPosition(close);
                servoClawAxis.setPosition(servoClawAxis.getPosition() + (gamepad2.left_trigger) * 0.01);
                servoClawAxis.setPosition(servoClawAxis.getPosition() - (gamepad2.right_trigger) * 0.01);
            }
        }
    }

    ElapsedTime timer;

    @Override
    public void init() {
        timer = new ElapsedTime();

        //Map Motors
        motorRightFront = hardwareMap.get(DcMotor.class, "motorRightFront");
        motorRightBack = hardwareMap.get(DcMotor.class, "motorRightBack");
        motorLeftFront = hardwareMap.get(DcMotor.class, "motorLeftFront");
        motorLeftBack = hardwareMap.get(DcMotor.class, "motorLeftBack");
        motorArm = hardwareMap.get(DcMotor.class, "motorArm");
        motorSlide = hardwareMap.get(DcMotor.class,"motorSlide");

        // Motors are custom installed so need to be reveresed
        motorRightBack.setDirection(DcMotor.Direction.REVERSE);
        motorLeftBack.setDirection(DcMotor.Direction.REVERSE);
        motorSlide.setDirection(DcMotor.Direction.REVERSE);

        //Make slide and arm run using encoder and reset encoders
        motorSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Map Servos
        servoClawAxis = hardwareMap.get(Servo.class,"servoClawAxis");
        servoClawOpenClose = hardwareMap.get(Servo.class,"servoClawOpenClose");

    }

    @Override
    public void loop() {

        moveDriveTrain();
        setFeed();
        setHighBasket();
        setLowBasket();
        setHighChamber();
        setLowChamber();


    }
}