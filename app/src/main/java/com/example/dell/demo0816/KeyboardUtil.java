package com.example.dell.demo0816;

import android.app.Activity;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by DELL on 2016/8/17.
 */
public class KeyboardUtil implements View.OnClickListener, AdapterView.OnItemClickListener {
    private VirtualKeyboardView virtualKeyboardView;
    private EditText editText;
    private Activity activity;
    private GridView gridView;

    private Animation enterAnim;
    private Animation exitAnim;
    private ArrayList<Map<String, String>> valueList;

    public KeyboardUtil(EditText editText, VirtualKeyboardView virtualKeyboardView, Activity activity) {
        this.editText = editText;
        this.virtualKeyboardView = virtualKeyboardView;
        this.activity = activity;

        valueList = virtualKeyboardView.getValueList();
        enterAnim = AnimationUtils.loadAnimation(activity, R.anim.push_bottom_in);
        exitAnim = AnimationUtils.loadAnimation(activity, R.anim.push_bottom_out);


        if (Build.VERSION.SDK_INT <= 10) {
            editText.setInputType(InputType.TYPE_NULL);
        } else {
            activity.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus"
                        , boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(editText, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        virtualKeyboardView.getLayoutBack().setOnClickListener(this);

        gridView = virtualKeyboardView.getGridView();
        gridView.setOnItemClickListener(this);
        editText.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layoutBack:
                virtualKeyboardView.startAnimation(exitAnim);
                virtualKeyboardView.setVisibility(View.GONE);
                break;
            case R.id.textAmount:
                virtualKeyboardView.setFocusable(true);
                virtualKeyboardView.setFocusableInTouchMode(true);

                virtualKeyboardView.startAnimation(enterAnim);
                virtualKeyboardView.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position < 11 && position != 9) {    //点击0~9按钮

            String amount = editText.getText().toString().trim();
            amount = amount + valueList.get(position).get("name");

            editText.setText(amount);

            Editable ea = editText.getText();
            editText.setSelection(ea.length());
        } else {

            if (position == 9) {
                String text = editText.getText().toString();
                if (text.length() > 0 && !text.equals("")) {
                    Toast.makeText(activity, editText.getText().toString(), Toast.LENGTH_SHORT).show();

                    virtualKeyboardView.startAnimation(exitAnim);
                    virtualKeyboardView.setVisibility(View.GONE);
                }
            }

            if (position == 11) {      //点击退格键
                String amount = editText.getText().toString().trim();
                if (amount.length() > 0) {
                    amount = amount.substring(0, amount.length() - 1);
                    editText.setText(amount);

                    Editable ea = editText.getText();
                    editText.setSelection(ea.length());
                }
            }
        }
    }
}
