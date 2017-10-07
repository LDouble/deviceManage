package com.it592.devicemanage.Fragment;


import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
public class LoginFragment extends Fragment {

    EditText emailtv;
    EditText passwordtv;
    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_login, container, false);
        TextView tv = view.findViewById(R.id.link_reg);
        emailtv = view.findViewById(R.id.input_username);
        passwordtv = view.findViewById(R.id.input_password);
        Button button = (Button)view.findViewById(R.id.btn_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity)getActivity();
                mainActivity.swithFragment("register");
            }
        });
        return view;
    }

    private void login(){
        String email = emailtv.getText().toString();
        String password = passwordtv.getText().toString();
        UserBean userBean = new UserBean();
        userBean.setUsername(email);
        userBean.setPassword(password);
        userBean.login(new SaveListener<UserBean>() {
            @Override
            public void done(UserBean userBean, BmobException e) {
                if(e == null){
                    Toast.makeText(getContext(),"登录成功!即将跳转到首页",Toast.LENGTH_LONG).show();
                    MainActivity mainActivity = (MainActivity)getActivity();
                    mainActivity.swithFragment("index");
                }else
                    Toast.makeText(getContext(),"登录失败," + e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

}
