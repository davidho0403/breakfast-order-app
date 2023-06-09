package com.fcu.breakfast_order_app;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

public class ButtonWithIcon extends LinearLayout {

    private Button buttonWithIcon;

    public ButtonWithIcon(Context context) {
        super(context);
        init(context);
    }

    public ButtonWithIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ButtonWithIcon(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.button_with_icon, this);

        buttonWithIcon = findViewById(R.id.optionButton);
    }

    public void setButtonWithIcon(String optionName, int drawableResId) {
        buttonWithIcon.setText(optionName);
        buttonWithIcon.setCompoundDrawablesWithIntrinsicBounds(drawableResId, 0, 0, 0);
    }
}
