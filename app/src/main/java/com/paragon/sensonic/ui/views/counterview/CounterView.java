package com.paragon.sensonic.ui.views.counterview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.StringRes;

import com.paragon.sensonic.R;


public class CounterView extends LinearLayout implements View.OnClickListener {


    public static final String TAG = CounterView.class.getSimpleName();
    private TextView itemCounterValue;
    private ImageView incButton;
    private ImageView decButton;
    private LinearLayout rootView;
    private CounterListener listener;


    public CounterView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public CounterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public CounterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);

    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        inflate(context, R.layout.layout_counter_view, this);
        this.rootView = (LinearLayout) findViewById(R.id.counter_view_root);
        this.itemCounterValue = (TextView) findViewById(R.id.countTV);
        this.incButton = (ImageView) findViewById(R.id.plus);
        this.decButton = (ImageView) findViewById(R.id.minus);
        this.incButton.setOnClickListener(this);
        this.decButton.setOnClickListener(this);
        rootView.setBackgroundResource(R.drawable.gray_lighter_radius_10);
    }


    public CounterView setStartCounterValue(String startValue) {
        if (this.itemCounterValue != null)
            this.itemCounterValue.setText(startValue);
        return this;
    }

    public CounterView setStartCounterValue(@StringRes int startValue) {
        if (this.itemCounterValue != null)
            this.itemCounterValue.setText(getString(startValue));
        return this;
    }

    public CounterView setCounterListener(CounterListener counterListener) {
        listener = counterListener;
        return this;
    }

    public String getCounterValue() {
        String text = "";
        if (this.itemCounterValue != null)
            text = this.itemCounterValue.getText().toString();
        return text;
    }

    private String getString(@StringRes int textResourceValue) {
        return getContext().getString(textResourceValue);
    }

    public CounterView setColor(@ColorRes int left, @ColorRes int right, @ColorRes int text) {

        this.incButton.setBackgroundColor(getColor(right));
        this.decButton.setBackgroundColor(getColor(left));
        this.itemCounterValue.setTextColor(getColor(text));
        return this;
    }

    private int getColor(@ColorRes int colorRes) {
        return getContext().getResources().getColor(colorRes);
    }

    @Override
    public void onClick(View view) {
        int value = 0;
        value = Integer.parseInt(this.itemCounterValue.getText().toString());
        int i = view.getId();
        if (i == R.id.plus) {
            value++;
            this.itemCounterValue.setText(String.valueOf(value));
            if (this.listener != null)
                this.listener.onIncClick(this.itemCounterValue.getText().toString());
        } else if (i == R.id.minus) {
            value--;
            if (value < 1) {
                value = 1;
            }
            this.itemCounterValue.setText(String.valueOf(value));
            if (this.listener != null)
                this.listener.onDecClick(this.itemCounterValue.getText().toString());
        }
    }


}
