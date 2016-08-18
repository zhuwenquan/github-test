package com.example.dell.demo0816;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DELL on 2016/8/17.
 */
public class VirtualKeyboardView extends RelativeLayout {
    private Context context;
    private GridView gridView;
    private ArrayList<Map<String, String>> valueList;
    private RelativeLayout layoutBack;

    public VirtualKeyboardView(Context context) {
        this(context, null);
    }

    public VirtualKeyboardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VirtualKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;
        View view = View.inflate(context, R.layout.layout_virtual_keyboard, null);
        valueList = new ArrayList<>();

        layoutBack = (RelativeLayout) view.findViewById(R.id.layoutBack);

        gridView = (GridView) view.findViewById(R.id.gv_keybord);

        initValueList();

        setupView();

        addView(view);
    }

    public RelativeLayout getLayoutBack() {
        return layoutBack;
    }

    public ArrayList<Map<String, String>> getValueList() {
        return valueList;
    }

    public GridView getGridView() {
        return gridView;
    }

    private void setupView() {
        KeyBoardAdapter adapter = new KeyBoardAdapter(context, valueList);
        gridView.setAdapter(adapter);
    }

    private void initValueList() {
        for (int i = 1; i < 13; i++) {
            Map<String, String> map = new HashMap<>();

            if (i < 10) {
                map.put("name", String.valueOf(i));
            } else if (i == 10) {
                map.put("name", "确定");
            } else if (i == 11) {
                map.put("name", String.valueOf(0));
            } else if (i == 12) {
                map.put("name", "");
            }
            valueList.add(map);
        }
    }
}
