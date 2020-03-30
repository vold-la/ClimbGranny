package com.noori.climbgranny;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Granny {
    private double screenWidth, screenHeight, grannyWidth, grannyHeight, screenX, screenY, speed1, speed2;
    private double startingJumpSpeed =45, decelerationJump =3, startingFallSpeed =0,fallFaster=3;
    private double wallBlock;
    private double jumpSpeed,fallSpeed= startingFallSpeed;
    private boolean direction = true;
    private Paint paint;
    private Rect granny;
    private boolean isAlive;
    private int TileHeightpos;
    private boolean directionHasChanged;
    private boolean IsleftwallHitted =false, IsrightwallHitted =false;
    private Bitmap leftg1, leftg2, leftg3, leftg4, leftg0;
    private Bitmap lg1, lg2, lg3, lg4, lg0;
    private Bitmap rightg1, rightg2, rightg3, rightg4, rightg0;
    private Bitmap rg1, rg2, rg3, rg4, rg0;
    private int countStep =0;
    boolean foot;


    //private boolean landed=true;
    public Granny(int screenWidth, int screenHeight, Bitmap leftg0, Bitmap leftg1, Bitmap leftg2, Bitmap leftg3, Bitmap leftg4, Bitmap rightg0, Bitmap rightg1, Bitmap rightg2, Bitmap rightg3, Bitmap rightg4)
    {
        this.leftg0 = leftg0;
        this.leftg1 = leftg1;
        this.leftg2 = leftg2;
        this.leftg3 = leftg3;
        this.leftg4 = leftg4;
        this.rightg0 = rightg0;
        this.rightg1 = rightg1;
        this.rightg2 = rightg2;
        this.rightg3 = rightg3;
        this.rightg4 = rightg4;
        foot = true;
        startingJumpSpeed = screenHeight /40d;
        decelerationJump = screenHeight /600d;
        fallFaster= screenHeight /600d;
        isAlive =true;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        grannyWidth = screenWidth /15d;
        grannyHeight = screenHeight /15d;
        screenX = screenWidth /2d;
        screenY = screenHeight;
        granny = new Rect();
        paint=new Paint();
        paint.setColor(Color.GREEN);
        wallBlock = screenHeight;
        speed1 = screenWidth /50d;
        speed2 = screenWidth /50d;
        setGranny();

    }
    public void stopGranny()
    {
        speed2 =0;
    }
    public void setLeftWallHitted()
    {
        IsleftwallHitted =false;
    }
    public void setRightWallHitted()
    {
        IsrightwallHitted =false;
    }
    public boolean getStatus()
    {
        return isAlive;
    }
    public double getXpos()
    {
        return screenX;
    }
    public int deadHeight(){return TileHeightpos;}
    public void setXpos(double X)
    {
        screenX =X;
    }
    public double getYpos()
    {
        return screenY;
    }
    public void setYpos(double Y)
    {
        screenY =Y;
    }
    public double getgrannyWidth()
    {
        return grannyWidth;
    }
    public void setWidth(int W)
    {
        grannyWidth =W;
    }
    public double getgrannyHeight()
    {
        return grannyHeight;
    }
    public void setHeight(int H)
    {
        grannyHeight =H;
    }
    public void moveUp(int i)
    {
        setYpos(getYpos()+i);
    }
    public void speedUp(int number)
    {
        if(number==1) {
            speed1 = screenWidth / 45d;
            speed2 = screenWidth / 45d;
        }else if(number==2) {
            speed1 = screenWidth / 40d;
            speed2 = screenWidth / 40d;
            startingJumpSpeed = screenHeight /38d;
        }else if(number==3)
        {
            speed1 = screenWidth /35d;
            speed2 = screenWidth /35d;
            startingJumpSpeed = screenHeight /30d;
            decelerationJump = screenHeight /350d;
        }else if(number==4)
        {
            speed1 = screenWidth /32d;
            speed2 = screenWidth /32d;
            startingJumpSpeed = screenHeight /28d;
            decelerationJump = screenHeight /300d;
        }else if(number==5)
        {
            speed1 = screenWidth /30d;
            speed2 = screenWidth /30d;
            startingJumpSpeed = screenHeight /28d;
            decelerationJump = screenHeight /300d;
        }
        else if(number==6)
        {
            speed1 = screenWidth /28d;
            speed2 = screenWidth /28d;
            startingJumpSpeed = screenHeight /28d;
            decelerationJump = screenHeight /300d;
        }
        else if(number==7)
        {
            speed1 = screenWidth /26d;
            speed2 = screenWidth /26d;
            startingJumpSpeed = screenHeight /26d;
            decelerationJump = screenHeight /280d;
        }
        else if(number==8)
        {
            speed1 = screenWidth /24d;
            speed2 = screenWidth /24d;
            startingJumpSpeed = screenHeight /26d;
            decelerationJump = screenHeight /280d;
        }
        else if(number==9)
        {
            speed1 = screenWidth /22d;
            speed2 = screenWidth /22d;
            startingJumpSpeed = screenHeight /26d;
            decelerationJump = screenHeight /280d;
        }
    }
    private void setGranny()
    {
        lg0 =Bitmap.createScaledBitmap(leftg0,(int) getgrannyWidth()+(int) getgrannyWidth()/3,(int) getgrannyHeight(),false);
        lg1 =Bitmap.createScaledBitmap(leftg1,(int) getgrannyWidth()+(int) getgrannyWidth()/3,(int) getgrannyHeight(),false);
        lg2 =Bitmap.createScaledBitmap(leftg2,(int) getgrannyWidth()+(int) getgrannyWidth()/3,(int) getgrannyHeight(),false);
        lg3 =Bitmap.createScaledBitmap(leftg3,(int) getgrannyWidth()+(int) getgrannyWidth()/3,(int) getgrannyHeight(),false);
        lg4 =Bitmap.createScaledBitmap(leftg4,(int) getgrannyWidth()+(int) getgrannyWidth()/3,(int) getgrannyHeight(),false);
        rg0 =Bitmap.createScaledBitmap(rightg0,(int) getgrannyWidth()+(int) getgrannyWidth()/3,(int) getgrannyHeight(),false);
        rg1 =Bitmap.createScaledBitmap(rightg1,(int) getgrannyWidth()+(int) getgrannyWidth()/3,(int) getgrannyHeight(),false);
        rg2 =Bitmap.createScaledBitmap(rightg2,(int) getgrannyWidth()+(int) getgrannyWidth()/3,(int) getgrannyHeight(),false);
        rg3 =Bitmap.createScaledBitmap(rightg3,(int) getgrannyWidth()+(int) getgrannyWidth()/3,(int) getgrannyHeight(),false);
        rg4 =Bitmap.createScaledBitmap(rightg4,(int) getgrannyWidth()+(int) getgrannyWidth()/3,(int) getgrannyHeight(),false);
    }
    public void kill(int TileHeightpos)
    {
        this.TileHeightpos =TileHeightpos;
        jumpSpeed=0;
        wallBlock = screenHeight + getgrannyHeight();
        isAlive =false;
    }

    public void drawGranny(Canvas canvas)
    {
        if(direction==true) {
            if(countStep ==0 || countStep ==1)
                canvas.drawBitmap(rg0, (float) screenX, (float) (screenY - grannyHeight), paint);
            else if(countStep ==2|| countStep ==3)
                canvas.drawBitmap(rg1, (float) screenX, (float) (screenY - grannyHeight), paint);
            else if(countStep ==4|| countStep ==5)
                canvas.drawBitmap(rg2, (float) screenX, (float) (screenY - grannyHeight), paint);
            else if(countStep ==6|| countStep ==7)
                canvas.drawBitmap(rg1, (float) screenX, (float) (screenY - grannyHeight), paint);
            else if(countStep ==8|| countStep ==9)
                canvas.drawBitmap(rg0, (float) screenX, (float) (screenY - grannyHeight), paint);
            else if(countStep ==10|| countStep ==11)
                canvas.drawBitmap(rg3, (float) screenX, (float) (screenY - grannyHeight), paint);
            else if(countStep ==12|| countStep ==13)
                canvas.drawBitmap(rg4, (float) screenX, (float) (screenY - grannyHeight), paint);
            else if(countStep ==14|| countStep ==15)
                canvas.drawBitmap(rg3, (float) screenX, (float) (screenY - grannyHeight), paint);
        }
        else {
            if(countStep ==0 || countStep ==1)
                canvas.drawBitmap(lg0, (float) screenX, (float) (screenY - grannyHeight), paint);
            else if(countStep ==2|| countStep ==3)
                canvas.drawBitmap(lg1, (float) screenX, (float) (screenY - grannyHeight), paint);
            else if(countStep ==4|| countStep ==5)
                canvas.drawBitmap(lg2, (float) screenX, (float) (screenY - grannyHeight), paint);
            else if(countStep ==6|| countStep ==7)
                canvas.drawBitmap(lg1, (float) screenX, (float) (screenY - grannyHeight), paint);
            else if(countStep ==8|| countStep ==9)
                canvas.drawBitmap(lg0, (float) screenX, (float) (screenY - grannyHeight), paint);
            else if(countStep ==10|| countStep ==11)
                canvas.drawBitmap(lg3, (float) screenX, (float) (screenY - grannyHeight), paint);
            else if(countStep ==12|| countStep ==13)
                canvas.drawBitmap(lg4, (float) screenX, (float) (screenY - grannyHeight), paint);
            else if(countStep ==14|| countStep ==15)
                canvas.drawBitmap(lg3, (float) screenX, (float) (screenY - grannyHeight), paint);

        }
        countStep++;
        if(countStep ==16)
            countStep =0;
    }
    public void horizontalMove()
    {

        if(direction==true)
        {
            if (getXpos() + getgrannyWidth() >= screenWidth)
            {
                direction = false;
                speed2 = speed1;
                directionHasChanged = true;
                if(getYpos()+ getgrannyHeight()< screenHeight - getgrannyHeight()) {
                    IsrightwallHitted = true;
                }
            }
        }
        else {
            if (getXpos() <= 0)
            {
                direction = true;
                speed2 = speed1;
                directionHasChanged = true;
                if(getYpos()+ getgrannyHeight()< screenHeight - getgrannyHeight()) {
                    IsleftwallHitted = true;
                }
            }
        }
        if(direction)
            setXpos(getXpos()+ speed2);
        else
            setXpos(getXpos()- speed2);
    }
    public void jump()
    {
        if(jumpSpeed<=0 && fallSpeed<=0) {
            jumpSpeed = startingJumpSpeed;

        }
    }
    public int setDirection(int xl,int xr)
    {
        if(!direction && xl> getXpos()+ getgrannyWidth()) {
            directionHasChanged=false;
            return 1;
        }
        if(direction && xr< getXpos()) {
            directionHasChanged=false;
            return -1;
        }
        return 0;
    }
    public void changeDirection(int dir)
    {
        if(dir!=0)
        {
            if (speed2 > 1 && directionHasChanged==false)
            {
                speed2 -= screenWidth /800d;
                directionHasChanged = false;
            } else if (speed2 <= 1 && directionHasChanged == false)
            {
                speed2 = 0;
                if (dir == 1)
                    direction = true;
                else
                    direction = false;
                directionHasChanged = true;

            }
            if (speed2 < speed1 - 1 && directionHasChanged == true)
                speed2 += screenWidth /800;
            else if (speed2 >= speed1 - 1 && directionHasChanged == true)
            {
                speed2 = speed1;
            }
        }
    }
    public void setWallBlock(double wallBlock)
    {
        if(getStatus())
        {
            this.wallBlock = wallBlock;
        }
    }
    public double getWallBlock()
    {
        return wallBlock;
    }
    public void verticalMove()
    {
        if(jumpSpeed>0) {
            setYpos(getYpos() - jumpSpeed);
            jumpSpeed -= decelerationJump;
            fallSpeed=0;
        }
        else
        {
            if(getYpos()< wallBlock)
            {
                if(fallSpeed<= wallBlock - getYpos()) {
                    setYpos(getYpos() + fallSpeed);
                    fallSpeed += fallFaster;
                }
                else {
                    setYpos(wallBlock);
                }
            }
            else {
                fallSpeed = 0;
            }
        }
    }
}
