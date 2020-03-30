package com.noori.climbgranny;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.preference.PreferenceManager;

public class ShowMenu
{
    int soundsize;
    Paint paint;
    Paint clearPaint;
    Rect rect;
    int barWidth=300,barHeight=100, barWidth2;
    int screenWidth, screenHeight;
    int a,b,c,d,a2,b2,c2,d2;
    Bitmap startpng,startpng2,continuepng,continuepng2,mute,mute2;
    Bitmap syrup1, syrup2;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedpreferenceEditor;
    int points=0;
    int localPoints;
    int g1, g2;

    public ShowMenu(Context context, int screenWidth, int screenHeight, Bitmap startpng, Bitmap continuepng, Bitmap mute, Bitmap syrup1)
    {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        paint = new Paint();
        clearPaint=new Paint();
        rect = new Rect();
        soundsize= screenWidth /10;
        barWidth= screenWidth /2;
        barHeight= screenHeight /8;
        barWidth2= screenWidth /2+ screenWidth /3;
        this.startpng=startpng;
        this.continuepng=continuepng;
        this.mute=mute;
        this.syrup1 = syrup1;
        setBars();
        sharedPreferences =PreferenceManager.getDefaultSharedPreferences(context);
        sharedpreferenceEditor = sharedPreferences.edit();

    }
    private void setBars()
    {
        startpng2=Bitmap.createScaledBitmap(startpng,barWidth,barHeight,false);
        continuepng2=Bitmap.createScaledBitmap(continuepng,barWidth2,barHeight,false);
        mute2=Bitmap.createScaledBitmap(mute,soundsize,soundsize,false);
        syrup2 =Bitmap.createScaledBitmap(syrup1,soundsize,soundsize,false);
    }
    public void draw(Canvas canvas, int deadDecider, int deadDecider2, boolean ableToContinue)
    {

        paint.setColor(Color.argb(deadDecider,50,50,50));//kolor tła
        canvas.drawRect(0,0,canvas.getWidth(),canvas.getHeight(),paint); // tło

        paint.setColor(Color.argb(deadDecider2,180,180,180)); //kolor prsotokąta

        setCenter(screenWidth /2, screenHeight /2,barWidth,barHeight); // ustawienie pozycji prostokąta

        canvas.drawBitmap(startpng2,a,b,paint); // rysowanie prostokąta
        if(deadDecider!=255 && ableToContinue)
            canvas.drawBitmap(continuepng2,a2,b2,paint);


        canvas.drawRect(screenWidth -soundsize,0, screenWidth,soundsize,paint); //kwadrat w prawym gornym
        canvas.drawBitmap(mute2, screenWidth -soundsize,0,paint);
        paint.setTextSize(screenWidth /10);
        paint.setTypeface(Typeface.create("Arial",Typeface.ITALIC));
        localPoints= sharedPreferences.getInt("POINTS",0);
        if(localPoints<10)
            g1 =1;
        else if(localPoints<100)
            g1 =2;
        else if(localPoints<1000)
            g1 =3;
        else if(localPoints<10000)
            g1 =4;
        else g1 =5;

        if(points<10)
            g2 =1;
        else if(points<100)
            g2 =2;
        else if(points<1000)
            g2 =3;
        else if(points<10000)
            g2 =4;
        else g2 =5;
        if(deadDecider!=255) {
            canvas.drawText("SCORE", screenWidth / 2 - (screenWidth / 20 * 3)-5, screenHeight / 7, paint);
            canvas.drawText(Integer.toString(points), screenWidth / 2 - (screenWidth / 25 * g2), screenHeight / 5, paint);
        }
        canvas.drawText("HIGH SCORE", screenWidth /2-(screenWidth /20*6), screenHeight /4+ screenHeight /30,paint);
        canvas.drawText(Integer.toString(localPoints), screenWidth /2-(screenWidth /25* g1), screenHeight /3,paint);

        canvas.drawBitmap(syrup2,5,5,paint);
        paint.setTextSize(soundsize);
        canvas.drawText(Integer.toString(sharedPreferences.getInt("SYRUP",0)),soundsize+5,soundsize,paint);

    }
    public void setCenter(int centerx,int centery,int width,int height)
    {
        a=centerx-width/2;
        b=centery-height;
        c=centerx+width/2;
        d=centery;
        a2=centerx-barWidth2/2;
        b2=b+2*barHeight;
        c2=centerx+barWidth2/2;
        d2=d+2*barHeight;
    }
    public int getBarCoords(String side)
    {
        if(side=="left")
            return a;
        else if(side == "top")
            return b;
        else if(side=="right")
            return c;
        else if(side =="bottom")
            return d;
        else return 0;
    }
    public int get2BarCoords(String side)
    {
        if(side=="left")
            return a2;
        else if(side == "top")
            return b2;
        else if(side=="right")
            return c2;
        else if(side =="bottom")
            return d2;
        else return 0;
    }
    public int getSoundsize()
    {
        return soundsize;
    }
    public void changeimage(Bitmap image)
    {
        mute2=Bitmap.createScaledBitmap(image,soundsize,soundsize,false);
    }
    public void setPoints(int points)
    {
        this.points=points;
    }

}
