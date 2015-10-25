package com.example.dc_admin.dc_switcher;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Collection;


public class SurfaceActivity extends BaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(new MySurfaceView(getApplicationContext()));
    }

    class GameObject
    {
        private float x;
        private float y;
        private Bitmap img;
        private Paint paint;

        public GameObject()
        {
            this.img = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
            this.x = 100;
            this.y = 100;
            this.paint = new Paint();
        }

        // 在SurfaceView加锁同步后传给自己的Canvas上绘制自己
        public void drawSelf(Canvas canvas)
        {
            canvas.drawBitmap(img, x, y, paint);
        }

        // 获取物件下一次要绘制的位置(这里是沿着一个边长为400的正方形不断运动的)
        public void getNextPos()
        {
            if (y == 100 && x != 500)
                x += 5;
            else if (x == 500 && y != 500)
                y += 5;
            else if (y == 500 && x != 100)
                x -= 5;
            else if (x == 100 && y != 100)
                y -= 5;
        }
    }
    class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback,Runnable
    {
        private SurfaceHolder holder;
        private Canvas canvas;
        private MyThread myThread;
        private GameObject obj;
        private Thread thread;

        public MySurfaceView(Context context)
        {
            super(context);
            holder=this.getHolder();
            holder.addCallback(this);
            this.obj=new GameObject();
            //创建一个绘图线程
            myThread = new MyThread(holder);
        }

        @Override
        public void run()
        {
            while (true) {
                obj.getNextPos();
                canvas=this.holder.lockCanvas();
                canvas.drawColor(Color.BLACK);
                obj.drawSelf(canvas);
                this.holder.unlockCanvasAndPost(canvas);
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder)
        {
            toastGo("SurfaceView已经创建");
            this.thread = new Thread(this);
            this.thread.start();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
        {
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder)
        {
            toastGo("SurfaceView已经销毁");
        }


    }

    class MyThread extends Thread
    {
        private SurfaceHolder holder;
        private boolean isRun;

        public MyThread(SurfaceHolder holder)
        {
            this.holder = holder;
            isRun = true;
        }

        @Override
        public void run()
        {
            super.run();
            int count = 0;
            while (isRun) {
                Canvas c = null;
                try {
                    synchronized (holder) {
                        c = holder.lockCanvas();
                        c.drawColor(0xccffffff);
                        Paint p = new Paint();
                        p.setColor(Color.WHITE);
                        Rect r = new Rect(100, 50, 300, 200);
                        c.drawRect(r, p);
                        Log.i("-------", "----");
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    if (c != null) {
                        holder.unlockCanvasAndPost(c);
                    }
                }


            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_surface, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
