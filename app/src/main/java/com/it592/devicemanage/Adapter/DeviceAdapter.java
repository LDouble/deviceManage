package com.it592.devicemanage.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.it592.devicemanage.Beans.DeviceBean;
import com.it592.devicemanage.Deviceinfo;
import com.it592.devicemanage.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by doublel on 17-10-7.
 */

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {
    List<DeviceBean> deviceLists;
    Context context;
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView device_name;
        TextView status;
       public ViewHolder(View view){
            super(view);
           device_name = (TextView)view.findViewById(R.id.device_name);
           status = (TextView)view.findViewById(R.id.device_status);
           view.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   DeviceBean deviceBean = deviceLists.get(getAdapterPosition());
                   Intent intent = new Intent(context, Deviceinfo.class);
                   intent.putExtra("device",deviceBean.getObjectId());
                   context.startActivity(intent);
               }
           });
        }

    }
    public DeviceAdapter(Context context,List<DeviceBean> deviceLists) {
        this.context = context;
        this.deviceLists = deviceLists;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_lists_itme,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DeviceBean deviceBean = deviceLists.get(position);
        String devicename = deviceBean.getDevice_name();
        Log.d("info",devicename);
        String[] status = new String[]{"已借出","未借出"};
        holder.device_name.setText(devicename);
        if(deviceBean.getLast_lend() == null) //未借出
            holder.status.setText(status[1]);
        else
            holder.status.setText(status[0]);
    }

    @Override
    public int getItemCount() {
        return deviceLists.size();
    }

}
