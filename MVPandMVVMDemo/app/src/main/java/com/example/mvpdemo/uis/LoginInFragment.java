package com.example.mvpandmvvmdemo.uis;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mvpandmvvmdemo.R;
import com.example.mvpandmvvmdemo.databinding.FragmentLoginInBinding;
import com.example.mvpandmvvmdemo.interfaces.LoginContract;
import com.example.mvpandmvvmdemo.middler.LoginInPresenter;
import com.example.mvpandmvvmdemo.middler.LoginInTask;


public class LoginInFragment extends Fragment implements LoginContract.BindView {


    private LoginInPresenter presenter;

    private FragmentLoginInBinding binding;

    @Override
    public void FailedToLogin() {
        Toast.makeText(requireActivity(), "登录失败", Toast.LENGTH_SHORT).show();
        binding.igStar.setImageResource(R.drawable.baseline_star_border_24);
    }

    @Override
    public void Success() {
        Toast.makeText(requireActivity(), "登陆成功", Toast.LENGTH_SHORT).show();
        binding.igStar.setImageResource(R.drawable.baseline_star_24);
    }

    public static LoginInFragment newInstance(LoginInPresenter presenter,LoginInTask task) {
        LoginInFragment fragment = new LoginInFragment(presenter, task);
        return fragment;
    }

    public LoginInFragment(LoginInPresenter presenter,LoginInTask task){
        this.presenter = presenter;
        this.presenter.setView(this);
        this.presenter.setTask(task);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_login_in,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.edUserName.getText().toString();
                Integer password = Integer.parseInt(binding.edPassword.getText().toString());
                presenter.getTask().Inputs(name,password);//密码的验证是靠Presenter中的LoginInTask实现的
                presenter.verify();
            }
        });
    }
}