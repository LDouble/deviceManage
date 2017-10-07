package com.it592.devicemanage.Fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.it592.devicemanage.Beans.UserBean;
import com.it592.devicemanage.MainActivity;
import com.it592.devicemanage.R;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    Button button;
    EditText usernameET;
    EditText passwordET;
    EditText emailET;
    ProgressBar progress;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            MainActivity main = (MainActivity)getActivity();
            main.swithFragment("login");
        }
    };
    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_register, container, false);
        button  =  (Button)view.findViewById(R.id.register);
        usernameET = (EditText)view.findViewById(R.id.username);
        passwordET = (EditText)view.findViewById(R.id.password);
        emailET = (EditText)view.findViewById(R.id.email);
        progress = (ProgressBar)view.findViewById(R.id.registerProgress);
        TextView tv = (TextView)view.findViewById(R.id.link_signup);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity main = (MainActivity)getActivity();
                main.swithFragment("login");
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
        return view;
    }

    private void register(){
        String username = usernameET.getText().toString();
        String password = passwordET.getText().toString();
        String email = emailET.getText().toString();
        if(username.length() < 2){
            Toast.makeText(getContext(),"你的姓名有误",Toast.LENGTH_LONG).show();
            return;
        }
        if(password.length() < 6){
            Toast.makeText(getContext(),"密码必须大于6位",Toast.LENGTH_LONG).show();
            return;
        }
        if(email.length() < 4){
            Toast.makeText(getContext(),"邮箱格式错误!",Toast.LENGTH_LONG).show();
            return;
        }

        UserBean userBean = new UserBean();
        userBean.setPassword(password);
        userBean.setEmail(email);
        userBean.setUsername(username);
        progress.setVisibility(View.VISIBLE);
        userBean.signUp(new SaveListener<UserBean>() {
            @Override
            public void done(UserBean userBean, BmobException e) {
                progress.setVisibility(View.INVISIBLE);
                if(e == null){
                    Toast.makeText(getContext(),"注册成功!即将跳转",Toast.LENGTH_LONG).show();
                    swith();
                }else
                    Toast.makeText(getContext(),"注册失败!" + e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void swith(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(3000);
                    Message msg = new Message();
                    handler.sendMessage(msg);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
