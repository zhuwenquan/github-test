package com.example.dell.demo0816;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;

/**
 * Created by DELL on 2016/8/17.
 */
public class PopEnterPassword extends PopupWindow {
    private PasswordView pwdView;
    private View mMenuView;
    private Activity context;

    public PopEnterPassword(final Activity context) {
        super(context);
        this.context = context;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.pop_enter_password, null);
        pwdView = (PasswordView) mMenuView.findViewById(R.id.pwd_view);

        pwdView.setOnFinishInput(new OnPasswordInputFinish() {
            @Override
            public void inputFinish(String password) {
                dismiss();

                Toast.makeText(context, "支付成功，密码为：" + password, Toast.LENGTH_SHORT).show();
            }
        });

        pwdView.getImgCancel().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        pwdView.getVirtualKeyboardView().getLayoutBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        this.setContentView(mMenuView);

        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        this.setFocusable(true);

        this.setAnimationStyle(R.style.pop_add_ainm);

        ColorDrawable dw = new ColorDrawable(0x66000000);

        this.setBackgroundDrawable(dw);
    }
}
