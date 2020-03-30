package com.noori.climbgranny;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

import static com.noori.climbgranny.GameThread.canvas;

public class GameView  extends SurfaceView implements SurfaceHolder.Callback
{
    private int losecounter;
    int randombound;
    int restartPos;
    public int points=0;
    int indextokill=-1;
    Paint textpaint;
    private Granny granny;
    private ArrayList<Tile> tiles;
    private ArrayList<Syrup> syrups;
    private GameThread thread;
    private int sW,sH;
    private int dir;
    private int syrupCounter;
    private boolean gameOn;
    private boolean muted;
    private boolean ableToContinue=true;
    private boolean threadrunned=false;
    private int TileCounter =0;
    private int TileType =0;
    private int movedTimer=60;
    MediaPlayer mp;
    MediaPlayer lose;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedpreferencesEditor;
    private int grannyMoved;
    int move1,move2,movestable;
    private boolean moved;
    int deadDecider, deadDecider2;
    Bitmap p0left,p1left,p2left,p3left,p4left,p0right,p1right,p2right,p3right,p4right;
    Bitmap start, Continue, stonyTile, snowyStonyTile, sandyTile, snowySandyTile, dirtyTile,background,background2,mute,sound;
    Bitmap syrup;
    ShowMenu menu;
    Random random;
    int randomInt;
    int temp;
    MainActivity main;
    public GameView(Context context, int sW, int sH, MainActivity main)
    {

        super(context);
        loadGraphics();
        syrupCounter =0;
        randombound=15;
        muted=false;
        moved=false;
        this.main=main;
        menu=new ShowMenu(context,sW,sH, start, Continue,mute, syrup);
        getHolder().addCallback(this);
        this.sW=sW;
        this.sH=sH;
        move1=sH/250;
        move2=sH/180;
        movestable=sH/350;
        thread=new GameThread(getHolder(),this);
        thread.setActivity(2);
        setFocusable(true);
        gameOn=true;
        losecounter=0;
        losecounter=0;
        indextokill=-1;
        background=BitmapFactory.decodeResource(getResources(),R.drawable.background1);
        background2=Bitmap.createScaledBitmap(background,sW,sH,false);
        sharedPreferences =PreferenceManager.getDefaultSharedPreferences(context);
        sharedpreferencesEditor = sharedPreferences.edit();
        mp=MediaPlayer.create(context,R.raw.coin);
        lose=MediaPlayer.create(context,R.raw.lose);
        textpaint = new Paint();
        textpaint.setTextSize(sW/7);
        textpaint.setTypeface(Typeface.create("Arial",Typeface.ITALIC));
        textpaint.setColor(Color.argb(200,180,180,180)); //kolor prsotokąta

    }
    public void lose()
    {

        if(!muted)
            lose.start();
        menu.setPoints(points);
        if(sharedPreferences.getInt("POINTS",0)<points)
        {
            sharedpreferencesEditor.putInt("POINTS", points);
            sharedpreferencesEditor.commit();
        }
        temp = sharedPreferences.getInt("SYRUP",0);
        temp += syrupCounter;
        sharedpreferencesEditor.putInt("SYRUP", temp);
        sharedpreferencesEditor.commit();
        temp =0;
        syrupCounter =0;
        restartPos = granny.deadHeight();
        thread.setActivity(0);
    }
    public void newGame()
    {
        randombound=15;
        TileCounter =0;
        indextokill=-1;
        if(losecounter>=4 && points>5) {
            losecounter=0;
              }
        else {
            movedTimer=60;
            granny = new Granny(sW, sH,p0left,p1left,p2left,p3left,p4left,p0right,p1right,p2right,p3right,p4right);
            moved=false;
            ableToContinue=true;
            points=0;
            losecounter++;
            deadDecider = 0;
            deadDecider2 = 0;
            tiles = new ArrayList<Tile>();
            syrups = new ArrayList<Syrup>();
            for (int i = 0; i < 6; i++) {
                TileCounter++;
                tiles.add(new Tile(sW, sH, tiles.size(), stonyTile,1));
            }

            thread.setActivity(1);
        }
    }
    public void continueGame()
    {
        moved=false;
        ableToContinue=false;
        deadDecider =0;
        deadDecider2 =0;
        granny = new Granny(sW, sH,p0left,p1left,p2left,p3left,p4left,p0right,p1right,p2right,p3right,p4right);
        thread.setActivity(1);
        granny.setYpos((double)restartPos);
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {

        thread.setRunning(true);
        //if(threadrunned=false)
        thread.start();
        threadrunned=true;

    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
        thread.setRunning(true);
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        boolean retry=true;
        while(retry)
        {
            try{
                thread.setRunning(false);
                thread.join();
            }catch(InterruptedException e) {
                e.printStackTrace();
            }
            retry=false;
        }
    }
    private void loadGraphics()
    {
        p0left = BitmapFactory.decodeResource(getResources(),R.drawable.gl0);
        p1left = BitmapFactory.decodeResource(getResources(),R.drawable.gl1);
        p2left = BitmapFactory.decodeResource(getResources(),R.drawable.gl2);
        p3left = BitmapFactory.decodeResource(getResources(),R.drawable.gl3);
        p4left = BitmapFactory.decodeResource(getResources(),R.drawable.gl4);

        p0right = BitmapFactory.decodeResource(getResources(),R.drawable.gr0);
        p1right = BitmapFactory.decodeResource(getResources(),R.drawable.gr1);
        p2right = BitmapFactory.decodeResource(getResources(),R.drawable.gr2);
        p3right = BitmapFactory.decodeResource(getResources(),R.drawable.gr3);
        p4right = BitmapFactory.decodeResource(getResources(),R.drawable.gr4);

        start = BitmapFactory.decodeResource(getResources(),R.drawable.start);
        Continue = BitmapFactory.decodeResource(getResources(),R.drawable.continue1);
        stonyTile =BitmapFactory.decodeResource(getResources(),R.drawable.stonetile);
        snowyStonyTile =BitmapFactory.decodeResource(getResources(),R.drawable.snowstile);
        sandyTile =BitmapFactory.decodeResource(getResources(),R.drawable.sandytile);
        snowySandyTile =BitmapFactory.decodeResource(getResources(),R.drawable.snowtile);
        dirtyTile =BitmapFactory.decodeResource(getResources(),R.drawable.dirtytile);
        mute=BitmapFactory.decodeResource(getResources(),R.drawable.mute);
        sound=BitmapFactory.decodeResource(getResources(),R.drawable.sound);

        syrup = BitmapFactory.decodeResource(getResources(),R.drawable.syrup);

    }
    public void drawMyStuff(final Canvas canvas)
    {

        Paint paint =new Paint();

        canvas.drawBitmap(background2,0,0,paint);
        granny.drawGranny(canvas);
        for(Tile x: tiles) {
            if (x.getPointStatus() == 1 && granny.getStatus())
            {
                if(points==8)
                    movestable=sH/330;
                else if(points==16)
                    movestable=sH/310;
                else if(points==24)
                    movestable=sH/290;
                else if(points==32)
                    movestable=sH/270;
                else if(points==40)
                    movestable=sH/250;
                else if(points==50)
                {
                    movestable=sH/220;
                    move1=sH/200;
                }
                points++;
                if(!muted)
                    mp.start();
                x.setPointStatus(2);
            }
            if(granny.getStatus())
            {
                if (granny.getYpos() < sH / 5 * 2 || grannyMoved == 2) {
                    x.moveUp(move2);
                    if(movedTimer<=0)
                        moved=true;

                } else if (granny.getYpos() < sH / 5 * 3 || grannyMoved == 1) {
                    x.moveUp(move1);
                    if(movedTimer<=0)
                        moved=true;
                } else if(moved)
                {
                    x.moveUp(movestable);
                }
            }

            x.drawTile(canvas, granny);

            if(granny.getStatus())
            {
                if(x.checkColission(granny))
                {
                    granny.setWallBlock(x.getTop());
                    if(tiles.get(tiles.indexOf(x)+1).dirNotChanged()) {
                        dir= granny.setDirection(tiles.get(tiles.indexOf(x) + 1).LgetR(), tiles.get(tiles.indexOf(x) + 1).RgetL());
                        tiles.get(tiles.indexOf(x) + 1).setDirChanged();
                    }
                    if(!tiles.get(tiles.indexOf(x)+1).dirNotChanged())
                    {
                        granny.changeDirection(dir);
                    }
                }
            }



        }
        for(Syrup x: syrups)
        {
            if(granny.getStatus())
            {
                if(granny.getStatus())
                {
                    if (granny.getYpos() < sH / 5 * 2 || grannyMoved == 2)
                    {
                        x.moveUp(move2);
                    } else if (granny.getYpos() < sH / 5 * 3 || grannyMoved == 1)
                    {
                        x.moveUp(move1);
                    } else if(moved)
                    {
                        x.moveUp(movestable);
                    }
                }
            }
            x.draw(canvas);
        }
        for(Syrup x: syrups)
        {
            if(x.checkColission(granny))
            {
                if(!muted)
                    mp.start();
                syrupCounter++;
                indextokill= syrups.indexOf(x);

                break;
            }
        }
        if(indextokill!=-1) {
            syrups.remove(indextokill);
            indextokill = -1;
        }
        if(granny.getStatus())
        {
            canvas.drawText(Integer.toString(points),0,sW/7,textpaint);
        }

    }
    public void drawMenu()
    {
        if(thread.getActivity()==0) {
            if (deadDecider < 150)
                deadDecider += 10;
            else
                deadDecider = 150;
            if(deadDecider2 <250)
                deadDecider2 +=10;
            else
                deadDecider2 =255;
        }
        else
        {
            deadDecider2 =255;
            deadDecider =255;
        }
        menu.draw(canvas, deadDecider, deadDecider2,ableToContinue);
    }
    public void update()
    {
        for(Syrup x: syrups)
            if(x.getY()>sH)
                indextokill= syrups.indexOf(x);
        if(indextokill!=-1) {
            syrups.remove(indextokill);
            indextokill=-1;
        }
        if(points==1)
            granny.speedUp(1*15);
        else if(points==2*15)
            granny.speedUp(2);
        else if(points==3*15)
            granny.speedUp(3);
        else if(points==4*15)
            granny.speedUp(4);
        else if(points==5*15)
            granny.speedUp(5);
        else if(points==6*15)
            granny.speedUp(6);
        else if(points==7*15)
            granny.speedUp(7);
        else if(points==8*15)
            granny.speedUp(8);
        else if(points==9*15)
            granny.speedUp(9);
        if(granny.getYpos()>sH+20 && granny.getStatus()&& moved && movedTimer==0) {
            granny.kill(0);
            ableToContinue=false;
        }
        if(movedTimer>0)
            movedTimer--;
        if(granny.getStatus()) {
            if (tiles.get(0).getY() >= sH) {
                TileCounter++;
                tiles.remove(0);
                if(TileCounter <=10)
                    tiles.add(new Tile(sW, sH, tiles.size(), stonyTile,1));
                else if(TileCounter <=20)
                    tiles.add(new Tile(sW, sH, tiles.size(), snowyStonyTile,2));
                else if(TileCounter <=30)
                    tiles.add(new Tile(sW, sH, tiles.size(), sandyTile,3));
                else if(TileCounter <=40)
                    tiles.add(new Tile(sW, sH, tiles.size(), snowySandyTile,4));
                else
                    tiles.add(new Tile(sW, sH, tiles.size(), dirtyTile,5));
                if(randomSyrup())
                {
                    syrups.add(new Syrup(sW,sH, syrups.size(), syrup,1, tiles.get(tiles.size()-1).getTop()));
                }
            }
        }
        granny.horizontalMove();

        granny.verticalMove();
        if(granny.getStatus()) {
            if (granny.getYpos() < sH / 5 * 2) {
                granny.moveUp(move2);
                grannyMoved = 2;
            } else if (granny.getYpos() < sH / 5 * 3) {
                granny.moveUp(move1);
                grannyMoved = 1;
            } else if(moved)
            {
                granny.moveUp(movestable);
                grannyMoved = 3;
            }
            else grannyMoved = 0;
        }
        if(!granny.getStatus() && thread.getActivity()!=0)
        {
            lose();
        }
    }
    private boolean randomSyrup()
    {
        if(points%2==0)
            randombound++;
        random = new Random();
        randomInt = random.nextInt(100);
        if(randomInt<randombound)
            return true;
        else
            return false;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction()==MotionEvent.ACTION_DOWN)
        {
            if(thread.getActivity()==1 && granny.getStatus()) {
                granny.jump();

            }
            else if(thread.getActivity()!=1)
            {
                if(event.getX()>menu.getBarCoords("left") && event.getX()<menu.getBarCoords("right")
                        && event.getY()>menu.getBarCoords("top") && event.getY()<menu.getBarCoords("bottom"))
                {
                    newGame();
                }
                if(event.getX()>menu.get2BarCoords("left") && event.getX()<menu.get2BarCoords("right")
                        && event.getY()>menu.get2BarCoords("top") && event.getY()<menu.get2BarCoords("bottom") && ableToContinue)
                {
                }
                if(event.getX()>=sW-menu.getSoundsize() && event.getY()<=menu.getSoundsize()){
                    if(!muted)
                    {
                        menu.changeimage(sound);
                        muted=true;
                    }
                    else {
                        menu.changeimage(mute);
                        muted = false;
                    }
                }
            }
        }
        return true;
    }

}

