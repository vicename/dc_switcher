package com.example.dc_admin.dc_switcher;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Field;


public class MainActivity extends BaseActivity {

    private Button mBtn1, mBtnSurface, mBtnRoate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fitStatusBar();
        setContentView(R.layout.activity_main);
        initView();
    }
    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("我是ToolBar");
        setSupportActionBar(toolbar);
        mBtn1 = (Button) findViewById(R.id.button1);
        mBtnSurface = (Button) findViewById(R.id.btn_surface);
        mBtnRoate = (Button) findViewById(R.id.btn_roate);
        mBtn1.setOnClickListener(intentToProgressActivity);
        mBtnSurface.setOnClickListener(intentToSurfaceActivity);
        mBtnRoate.setOnClickListener(intentToRoateActivity);
    }

    private View.OnClickListener intentToProgressActivity = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent().setClass(MainActivity.this, ProgressActivity.class));
        }
    };
    private View.OnClickListener intentToSurfaceActivity = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent().setClass(MainActivity.this, SurfaceActivity.class));
        }
    };
    private View.OnClickListener intentToRoateActivity=new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            startActivity(new Intent().setClass(MainActivity.this,RoateActivity.class));
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
