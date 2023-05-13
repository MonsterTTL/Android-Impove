package com.example.mvvmdemo;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvmdemo.databinding.RelistLayoutBinding;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<SwordsMan> mList;

    public MyAdapter(List<SwordsMan> mList){
        this.mList = mList;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        RelistLayoutBinding binding;

        //会先调用ViewHolder
        public ViewHolder(ViewDataBinding binding){
            super(binding.getRoot());
            this.binding = (RelistLayoutBinding) binding;
        }

        public RelistLayoutBinding getBinding(){
            return binding;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.relist_layout,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.tvsName.setText(mList.get(position).getName());
        holder.binding.tvsLevel.setText(mList.get(position).getLevel());
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }
}
