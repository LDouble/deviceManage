package com.it592.devicemanage;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.it592.devicemanage.Beans.UserBean;
import com.it592.devicemanage.Fragment.LoginFragment;
import com.it592.devicemanage.Fragment.RegisterFragment;
import com.it592.devicemanage.Fragment.deviceFragment;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;


public class MainActivity extends AppCompatActivity {
    FragmentManager fm;
    RegisterFragment rfm;
    LoginFragment lfm;
    deviceFragment dfm;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this,"91f3663ee47e77f5e02ac51e46031df0");
        fm = getSupportFragmentManager();
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true); //设置返回按钮
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        }
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);
        navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.device:
                        Toast.makeText(MainActivity.this,"You click the device",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.my:
                        Toast.makeText(MainActivity.this,"You click the my",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.about:
                        Toast.makeText(MainActivity.this,"You click the about",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.exit:
                        Toast.makeText(MainActivity.this,"You CLick the exit",Toast.LENGTH_SHORT).show();
                        break;
                }
                return  true;
            }
        });

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_PHONE_STATE},1);
        }else {
            UserBean user =  BmobUser.getCurrentUser(UserBean.class);
            if(user == null)
                swithFragment("login");
            else
                swithFragment("index");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    UserBean user =  (UserBean) UserBean.getCurrentUser();
                    if(user == null)
                        swithFragment("login");
                    else
                        swithFragment("index");
                }else{
                    Toast.makeText(MainActivity.this,"授权失败,请关闭后重新授权!",Toast.LENGTH_LONG).show();
                }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);break;
            case R.id.scan:
                Toast.makeText(MainActivity.this,"你点击了扫描按钮",Toast.LENGTH_SHORT).show();
                break;

        }
        return  true;
    }

    public  void swithFragment(String type){
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        switch (type){
            case "index":
                toolbar.setVisibility(View.VISIBLE);
                if(dfm == null){
                    dfm = new deviceFragment();
                    transaction.add(R.id.fm_container,dfm);
                }else
                    transaction.show(dfm);
                break;
            case "register":
                toolbar.setVisibility(View.GONE);
                if(rfm == null){
                    rfm = new RegisterFragment();
                    transaction.add(R.id.fm_container,rfm);
                }else
                    transaction.show(rfm);
                break;
            case "login":
                toolbar.setVisibility(View.GONE);
                if(lfm == null){
                    lfm = new LoginFragment();
                    transaction.add(R.id.fm_container,lfm);
                }else
                    transaction.show(lfm);
                break;
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction){
        if(rfm != null){
            transaction.hide(rfm);
        }
        if(lfm != null){
            transaction.hide(lfm);
        }
    }
}
