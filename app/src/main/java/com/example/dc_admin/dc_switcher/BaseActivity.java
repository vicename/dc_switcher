package com.example.dc_admin.dc_switcher;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

/**
 * Created by DC-ADMIN on 15-10-1.
 */
public class BaseActivity extends AppCompatActivity
{
    private int buildVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.abc_grow_fade_in_from_bottom, R.animator.on_in_hold);
        buildVersion=Build.VERSION.SDK_INT;
        if (buildVersion == Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
//        fitStatusBar(true);
    }

    public void fitStatusBar(boolean flag)
    {
        if (buildVersion != 19) {
            return;
        }
        if (flag) {
            fitStatusBar();
        }
    }

    //用TextView填充StatusBar
    public void fitStatusBar()
    {
        if (buildVersion != 19) {
            return;
        }
        Log.i("---", "fitstatus");
        // 创建TextView
        TextView textView = new TextView(this);
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getStatusBarHeight());
        textView.setBackgroundColor(getResources().getColor(R.color.material_deep_teal_500));
        textView.setLayoutParams(lParams);
        // 获得根视图并把TextView加进去。
        ViewGroup view = (ViewGroup) getWindow().getDecorView();
        view.addView(textView);
    }

    //获取状态栏高度
    public int getStatusBarHeight()
    {
        Class<?> c;
        Object obj;
        Field field;
        int x, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    public void closeDcActivity()
    {
        this.finish();
        overridePendingTransition(R.animator.on_out_hold, R.animator.drown);
    }

    /**
     * 返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            BaseActivity.this.closeDcActivity();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void toastGo(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
