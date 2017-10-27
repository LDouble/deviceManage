package com.it592.devicemanage.Adapter;

import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.it592.devicemanage.Beans.LendBean;
import com.it592.devicemanage.R;

import java.util.List;

/**
 * Created by doublel on 17-10-8.
 */

public class BorrowAdapter extends RecyclerView.Adapter<BorrowAdapter.ViewHolder> {
    List<LendBean> lendBeanLists;
    public  BorrowAdapter(List<LendBean> lendBeanLists){
        this.lendBeanLists = lendBeanLists;
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ViewHolder(View view){
            super(view);
            textView = (TextView)view.findViewById(R.id.borrow_item);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.borrow_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LendBean lendBean = lendBeanLists.get(position);
        if(lendBean.getLender().getUsername() !=null)
            holder.textView.setText(lendBean.getLender().getUsername() + "于" + lendBean.getCreatedAt()+"借走");
        else
            holder.textView.setText("于" + lendBean.getCreatedAt() + "借走: " + lendBean.getDeviceBean().getDevice_name());
    }

    @Override
    public int getItemCount() {
        return  lendBeanLists.size();
    }
}
