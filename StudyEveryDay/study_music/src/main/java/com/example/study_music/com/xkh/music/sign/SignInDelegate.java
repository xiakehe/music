package com.example.study_music.com.xkh.music.sign;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.study_core.delegate.BaseDelegate;
import com.example.study_core.delegate.PermissionDelegate;
import com.example.study_core.util.ToastHelp;
import com.example.study_music.R;
import com.example.study_music.R2;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.OnClick;

public class SignInDelegate extends PermissionDelegate implements SignInContract.SignInView {
    private LoginMethod method;
    private SingInPresenterImp presenterImp = new SingInPresenterImp(this, new SignInModelImp());

    @BindView(R2.id.til_number)
    TextInputLayout inputLayout_phone;

    @BindView(R2.id.til_password)
    TextInputLayout inputLayout_password;

    @BindView(R2.id.edit_sign_in_password)
    TextInputEditText edit_sign_in_password;

    @BindView(R2.id.edit_sign_in_phone_number)
    TextInputEditText edit_sign_in_phone_number;


    @OnClick(R2.id.iv_login_back)
    void onClickLoginBack() {
        getSupportDelegate().pop();
    }

    @BindView(R2.id.tv_login_code)
    AppCompatTextView mCodeLogin;

    @BindView(R2.id.tv_login_password)
    AppCompatTextView mPasswordLogin;

    @BindView(R2.id.view_login_code)
    View mLineCode;
    @BindView(R2.id.view_login_password)
    View mLinePassword;

    @BindView(R2.id.tv_auto_login_by_phone)
    TextView mAutoLoginByPhone;
    @BindView(R2.id.rl_email_or_forget)
    RelativeLayout mEmailOrForget;

    @BindView(R2.id.btn_sign_in)
    AppCompatButton mButtonLogin;


    @OnClick(R2.id.tv_login_code)
    void onClickLoginByCode() {
        method = LoginMethod.CODE;
        setSelectLoginMode(method);
    }

    @OnClick(R2.id.tv_login_password)
    void onClickLoginByPassword() {
        method = LoginMethod.PASSWORD;
        setSelectLoginMode(method);
    }

    private void setSelectLoginMode(LoginMethod mode) {
        switch (mode) {
            case NORMAL:
                mCodeLogin.setTextColor(getResources().getColor(R.color.black));
                mLineCode.setBackgroundColor(getResources().getColor(R.color.light_gray));
                mPasswordLogin.setTextColor(getResources().getColor(R.color.black));
                mLinePassword.setBackgroundColor(getResources().getColor(R.color.light_gray));
                inputLayout_password.setVisibility(View.GONE);
                mAutoLoginByPhone.setVisibility(View.VISIBLE);
                mEmailOrForget.setVisibility(View.GONE);
                break;
            case CODE:
                mCodeLogin.setTextColor(getResources().getColor(R.color.red));
                mLineCode.setBackgroundColor(getResources().getColor(R.color.red));
                mPasswordLogin.setTextColor(getResources().getColor(R.color.black));
                mLinePassword.setBackgroundColor(getResources().getColor(R.color.light_gray));

                inputLayout_password.setVisibility(View.GONE);
                mAutoLoginByPhone.setVisibility(View.VISIBLE);
                mEmailOrForget.setVisibility(View.GONE);

                break;
            case PASSWORD:
                mPasswordLogin.setTextColor(getResources().getColor(R.color.red));
                mLinePassword.setBackgroundColor(getResources().getColor(R.color.red));
                mCodeLogin.setTextColor(getResources().getColor(R.color.black));
                mLineCode.setBackgroundColor(getResources().getColor(R.color.light_gray));
                inputLayout_password.setVisibility(View.VISIBLE);
                mAutoLoginByPhone.setVisibility(View.GONE);
                mEmailOrForget.setVisibility(View.VISIBLE);

                break;
            default:
                break;
        }
    }


    @OnClick(R2.id.btn_sign_in)
    void onClickSignIn() {
        if (checkForm(method)) {
            if (presenterImp != null) {
                presenterImp.signIn(edit_sign_in_phone_number.getText().toString(), edit_sign_in_password.getText().toString(), method);
            }

        }

    }

    private boolean isPhone() {
        final String phone = edit_sign_in_phone_number.getText().toString();

        if (TextUtils.isEmpty(phone) || !Patterns.PHONE.matcher(phone).matches() || phone.length() != 11) {
            inputLayout_phone.setHint("手机号不正确");
            return false;
        }
        return true;
    }

    private boolean checkForm(LoginMethod mode) {
        switch (mode) {
            case CODE:
                return isPhone();
            case PASSWORD:

                if (!isPhone()) {
                    return false;
                }
                final String password = edit_sign_in_password.getText().toString();
                if (TextUtils.isEmpty(password) || password.length() < 6) {
                    inputLayout_password.setHint("密码不得少于6位");

                    return false;
                }
                break;
            default:
                break;

        }

        return true;
    }


    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

        method = LoginMethod.CODE;
        edit_sign_in_phone_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String info = s.toString();
                if (TextUtils.isEmpty(info)) {
                    mButtonLogin.setBackground(getResources().getDrawable(R.drawable.sign_button_bg_noraml_shape));
                    inputLayout_phone.setHint(getString(R.string.please_input_number));
                    inputLayout_password.setHint(getString(R.string.please_input_password));
                } else {
                    mButtonLogin.setBackground(getResources().getDrawable(R.drawable.sign_button_bg_shape));

                }
            }
        });
        setSelectLoginMode(method);

    }

    @Override
    public void onSignInSuccess() {
        ToastHelp.show(mContext, "登录成功");
        getSupportDelegate().pop();
    }

    @Override
    public void onSignInFailMessage(String msg, int code) {

        ToastHelp.show(mContext, msg);
    }


    @NotNull
    @Override
    public BaseDelegate getHostDelegate() {
        return this;
    }

    @Override
    public void startLoading() {
        ToastHelp.show(mContext, "正在登录中");

    }

    @Override
    public void onLoadSuccess() {

    }

    @Override
    public void onLoadError(@NotNull String error) {
        ToastHelp.show(mContext, error);
    }
}
