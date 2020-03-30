package com.noori.climbgranny;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

public class Syrup {
    int screenWidth, screenHeight;
    Bitmap texture;
    int texturetype;
    int syrupSize;
    int x,y;
    Paint paint;
    int index;
    Random random;
    int randomInt;
    public Syrup(int screenWidth, int screenHeight, int i, Bitmap texture, int texturetype, int pt)
    {
        this.screenHeight=screenHeight;
        this.screenWidth=screenWidth;
        syrupSize =screenWidth/15;
        paint = new Paint();
        setTexture(texture, syrupSize);
        x=RandomSyrup();
        y=pt- syrupSize *2;
        index=i;

    }
    private void setTexture(Bitmap texture, int syrupSize)
    {
        this.texture= Bitmap.createScaledBitmap(texture,syrupSize,syrupSize,false);
    }
    boolean checkColission(Granny granny)
    {
        if((granny.getYpos()-granny.getgrannyHeight()<y+ syrupSize && granny.getYpos()>y) && granny.getXpos()+granny.getgrannyWidth()>x && granny.getXpos()<x+ syrupSize) {
            return true;
        }
        else
            return false;
    }
    public int getIndex()
    {
        return index;
    }
    public int getY()
    {
        return y;
    }
    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(texture,x,y,paint);
    }
    public void moveUp(int moved)
    {
        y+=moved;
    }
    private int RandomSyrup()
    {
        random = new Random();
        randomInt = random.nextInt(screenWidth- syrupSize);
        return randomInt;
    }
}
