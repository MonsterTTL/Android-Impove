package com.example.mvpdemo.uis;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mvpandmvvmdemo.R;
import com.example.mvpandmvvmdemo.databinding.FragmentIpInfo2Binding;
import com.example.mvpdemo.interfaces.IpInfoContract;
import com.example.mvpdemo.middler.LoginInPresenter;
import com.example.mvpdemo.middler.LoginInTask;
import com.example.mvpdemo.modules.IpInfo;
import com.example.mvpdemo.modules.ipInfoDB;


public class IpInfoFragment extends Fragment implements IpInfoContract.View{
    //MVP模式使得Fragment能专注于UI的处理而不需要太注重于业务方面
    private FragmentIpInfo2Binding myBinding;
    private IpInfoContract.Presenter mPresenter;
    private Dialog mDialog;

    public IpInfoFragment() {

    }

    public static IpInfoFragment newInstance(){
        return new IpInfoFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_ip_info2,container,false);
        View root = myBinding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDialog = new ProgressDialog(requireActivity());
        mDialog.setTitle("正在加载中...");
        myBinding.btTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getIpInfo("39.155.184.147");//业务方面就交给了Presenter
                //至于回调方法，mPresenter的getIpInfo方法会执行回调方法，最终调用addTaskView的回调方法
            }
        });

        myBinding.btChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentCont,LoginInFragment.newInstance(new LoginInPresenter(),new LoginInTask()));
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    @Override
    public void setPresenter(IpInfoContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setIpInfo(IpInfo ipInfo) {//刷新UI的方法
        if(ipInfo != null && ipInfo.getData()!= null){
            ipInfoDB db = ipInfo.getData();
            myBinding.tvProvince.setText(db.province);
            myBinding.tvAdcode.setText(db.adcode);
            myBinding.tvCity.setText(db.city);
        }
    }

    @Override
    public void showLoading() {//显示进度条
        mDialog.show();
    }

    @Override
    public void hideLoading() {//隐藏进度条
        if(mDialog.isShowing()){
            mDialog.dismiss();
        }
    }

    @Override
    public void showError() {
        Toast.makeText(requireActivity(), "Something Wrong!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}