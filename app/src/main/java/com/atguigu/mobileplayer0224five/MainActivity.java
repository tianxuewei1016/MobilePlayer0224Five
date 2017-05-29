package com.atguigu.mobileplayer0224five;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.atguigu.mobileplayer0224five.base.BaseFragment;
import com.atguigu.mobileplayer0224five.fragment.LocalAudioFragment;
import com.atguigu.mobileplayer0224five.fragment.LocalVideoFragment;
import com.atguigu.mobileplayer0224five.fragment.NetAudioFragment;
import com.atguigu.mobileplayer0224five.fragment.RecyclerviewFragment;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.atguigu.mobileplayer0224five.R.id.rg_main;

public class MainActivity extends AppCompatActivity {

    @InjectView(rg_main)
    RadioGroup rgMain;
    private ArrayList<BaseFragment> fragments;

    private int position;
    private Fragment tempFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        //Android6.0动态获取权限
        isGrantExternalRW(this);
        initFragment();
        initListenter();
    }

    private void initListenter() {
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_local_video:
                        position = 0;
                        break;
                    case R.id.rb_local_audio:
                        position = 1;
                        break;
                    case R.id.rb_net_audio:
                        position = 2;
                        break;
                    case R.id.rb_net_video:
                        position = 3;
                        break;
                }
                //根据位置得到相应的Fragment
                BaseFragment currentFragment = fragments.get(position);
                addFragment(currentFragment);
            }
        });
        //默认选中本地视频
        rgMain.check(R.id.rb_local_video);
    }

    private void addFragment(Fragment currentFragment) {
        //缓存的不等于当前的
        if (tempFragment != currentFragment) {
            //开启事务
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //切换
            if (currentFragment != null) {
                //是否添加过,如果没有添加过
                if (!currentFragment.isAdded()) {
                    //把之前显示的给隐藏
                    if (tempFragment != null) {
                        ft.hide(tempFragment);
                    }
                    //如果没有添加就添加
                    ft.add(R.id.fl_mainc_content, currentFragment);
                } else {
                    //把之前的隐藏
                    if (tempFragment != null) {
                        ft.hide(tempFragment);
                    }
                    //如果添加了就直接显示
                    ft.show(currentFragment);
                }
                //最后一步，提交事务
                ft.commit();
            }
            tempFragment = currentFragment;
        }
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new LocalVideoFragment());//本地视频
        fragments.add(new LocalAudioFragment());//本地音乐
        fragments.add(new NetAudioFragment());//网络音乐
        fragments.add(new RecyclerviewFragment());// recyclerview
    }

    /**
     * 解决安卓6.0以上版本不能读取外部存储权限的问题
     *
     * @param activity
     * @return
     */

    public static boolean isGrantExternalRW(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            activity.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);

            return false;
        }

        return true;
    }

    private boolean isExit = false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (position != 0) {
                rgMain.check(R.id.rb_local_video);
                return true;
            } else if (!isExit) {
                Toast.makeText(MainActivity.this, "再按一次退出软件", Toast.LENGTH_SHORT).show();
                isExit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isExit = false;
                    }
                }, 2000);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
