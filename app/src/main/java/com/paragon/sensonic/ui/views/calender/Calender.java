package com.paragon.sensonic.ui.views.calender;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.paragon.sensonic.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Calender extends LinearLayout implements View.OnClickListener {

    private static final String TAG = "CalenderEvent";

    private LinearLayout weekOneLayout;
    private LinearLayout weekTwoLayout;
    private LinearLayout weekThreeLayout;
    private LinearLayout weekFourLayout;
    private LinearLayout weekFiveLayout;
    private LinearLayout weekSixLayout;


    private static final String[] MONTH_FULL_NAMES = {"Jan", "Feb", "Mar", "Apr",
            "May", "Jun", "Jul", "Aug",
            "Sep", "Oct", "Nov", "Dec"};

    /*private static final String[] MONTH_FULL_NAMES = {"January", "February", "March", "April",
            "May", "June", "July", "August",
            "September", "October", "November", "December"};*/

    private static final String[] WEEK_NAMES = {"Sunday", "Monday", "Tuesday", "Wednesday",
            "Thursday", "Friday", "Saturday"};

    private LinearLayout[] weeks;
    private TextView[] days;
    private LinearLayout[] daysContainer;
    // private TextView[] eventsTextViewList;

    private static final String TEXT_EVENT = " TEXT";
    private static final String COLOR_EVENT = " COLOR";

    private ImageView buttonPrevious, buttonNext;

    private TextView textViewMonthName;
    private RelativeLayout header;

    private Calendar mCalendar;

    private static final String CUSTOM_GREY = "#a0a0a0";

    private Context mContext;

    private List<DayContainerModel> dayContainerList;

    private String mToday;
    private String mCurrentMonth, mCurrentYear;

    //selected color element
    private TextView mSelectedTexView;
    private LinearLayout mSelectedLinearLayout;
    private int mPreviousColor;


    private CalenderDayClickListener mCalenderDayClickListener;

    // View component
    private View mainView;

    // View setter component;
    private int mBackgroundColor;
    private int mSelectorColor;
    private int mSelectedDayTextColor;
    private int mCurrentMonthDayColor;
    private int mOffMonthDayColor;
    private int mMonthTextColor;
    private int mWeekNameColor;
    private boolean isShowHeader;

    private Drawable nextButtonDrawable;
    private Drawable prevButtonDrawable;
    private DayContainerModel selectedDate;

    // Default component
    private static final int DEFAULT_BACKGROUND_COLOR = Color.WHITE;
    private static final int DEFAULT_SELECTED_DAY_COLOR = Color.WHITE;
    private static final int DEFAULT_CURRENT_MONTH_DAY_COLOR = Color.BLACK;
    private static final int DEFAULT_OFF_MONTH_DAY_COLOR = Color.parseColor(CUSTOM_GREY);
    private static final int DEFAULT_SELECTOR_COLOR = Color.parseColor("#C2CA83");
    private static final int DEFAULT_TEXT_COLOR = Color.parseColor("#808080");
    private static final boolean DEFAULT_IS_SHOW_HEADER = true;

    public Calender(Context context) {
        super(context);
    }

    public Calender(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);


        initAttrs(attrs);

        initView(context);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        weekOneLayout = findViewById(R.id.calendar_week_1);
        weekTwoLayout = findViewById(R.id.calendar_week_2);
        weekThreeLayout = findViewById(R.id.calendar_week_3);
        weekFourLayout = findViewById(R.id.calendar_week_4);
        weekFiveLayout = findViewById(R.id.calendar_week_5);
        weekSixLayout = findViewById(R.id.calendar_week_6);

        buttonNext = findViewById(R.id.button_next);
        buttonPrevious = findViewById(R.id.button_previous);

        textViewMonthName = findViewById(R.id.text_view_month_name);
        header = findViewById(R.id.header);

        // week container
        LinearLayout linearLayoutWeak = findViewById(R.id.linear_layout_week);

        weeks = new LinearLayout[6];
        days = new TextView[6 * 7];
        daysContainer = new LinearLayout[6 * 7];

        weeks[0] = weekOneLayout;
        weeks[1] = weekTwoLayout;
        weeks[2] = weekThreeLayout;
        weeks[3] = weekFourLayout;
        weeks[4] = weekFiveLayout;
        weeks[5] = weekSixLayout;

        DisplayMetrics metrics = getResources().getDisplayMetrics();

        mCalendar = Calendar.getInstance();
        mToday = mCalendar.get(Calendar.DATE) + " " + MONTH_FULL_NAMES[mCalendar.get(Calendar.MONTH)] + " " + mCalendar.get(Calendar.YEAR);
        mCurrentMonth = "" + mCalendar.get(Calendar.MONTH);
        mCurrentYear = "" + mCalendar.get(Calendar.YEAR);
        Log.d(TAG, "Today, " + mToday);


        initDaysInCalender(getdaysLayoutParams(), mContext, metrics);

        initCalender(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH));

        buttonPrevious.setOnClickListener(this);
        buttonNext.setOnClickListener(this);

        // set view element
        mainView.setBackgroundColor(mBackgroundColor);

        // textViewMonthName.setTextColor(mMonthTextColor);

        for (int i = 0; i < linearLayoutWeak.getChildCount(); i++) {
            TextView textViewWeek = (TextView) linearLayoutWeak.getChildAt(i);
            textViewWeek.setTextColor(mWeekNameColor);
        }

        if (nextButtonDrawable != null) {
            buttonNext.setBackground(nextButtonDrawable);
        }
        if (prevButtonDrawable != null) {
            buttonPrevious.setBackground(prevButtonDrawable);
        }

        if (!isShowHeader) {
            header.setVisibility(GONE);
        }

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_next) {
            gotoNextMonth();
        } else if (view.getId() == R.id.button_previous) {
            gotoPreviousMonth();
        }
    }


    private void initCalender(int selectedYear, int selectedMonth) {
        if (!mCurrentMonth.matches("" + mCalendar.get(Calendar.MONTH))) {
            buttonPrevious.setEnabled(true);
            buttonPrevious.setImageResource(R.drawable.ic_arrow_back_ios);
        } else {
            if (!mCurrentYear.matches("" + mCalendar.get(Calendar.YEAR))) {
                buttonPrevious.setEnabled(true);
                buttonPrevious.setImageResource(R.drawable.ic_arrow_back_ios);
            } else {
                buttonPrevious.setEnabled(false);
                buttonPrevious.setImageResource(R.mipmap.ic_backward);
            }
        }


        dayContainerList = new ArrayList<>();

        mCalendar.set(selectedYear, selectedMonth, 1);

        textViewMonthName.setText(MONTH_FULL_NAMES[selectedMonth] + ", " + selectedYear);

        int daysInCurrentMonth = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);


        int index = 0;

        int firstDayOfCurrentMonth = mCalendar.get(Calendar.DAY_OF_WEEK);

        int previousLeftMonthDays = firstDayOfCurrentMonth - 1;

        int nextMonthDays = 42 - (daysInCurrentMonth + previousLeftMonthDays);

        // now set previous date
        if (previousLeftMonthDays != 0) {
            int prevMonth;
            int prevYear;
            if (selectedMonth > 0) {
                mCalendar.set(selectedYear, selectedMonth - 1, 1);
                prevMonth = selectedMonth - 1;
                prevYear = selectedYear;
            } else {
                mCalendar.set(selectedYear - 1, 11, 1);
                prevMonth = 11;
                prevYear = selectedYear - 1;
            }

            int previousMonthTotalDays = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);

            mCalendar.set(prevYear, prevMonth, (previousMonthTotalDays - previousLeftMonthDays));
            int prevCurrentDay = mCalendar.get(Calendar.DATE);
            prevCurrentDay++;
            for (int i = 0; i < previousLeftMonthDays; i++) {
                days[index].setText(String.valueOf(prevCurrentDay));
                days[index].setTextColor(mOffMonthDayColor);
                days[index].setOnClickListener(null);
                /*suggested changes*/
                //days[index].setVisibility(GONE);

                daysContainer[index].setBackgroundResource(R.drawable.gray_lighter_square);
                final DayContainerModel model = new DayContainerModel();
                model.setIndex(index);
                model.setYear(prevYear);
                model.setMonthNumber(prevMonth);
                model.setMonth(MONTH_FULL_NAMES[prevMonth]);
                model.setDay(prevCurrentDay);
                String date = model.getDay() + " " + model.getMonth() + " " + model.getYear();
                model.setDate(date);
                model.setTimeInMillisecond(TimeUtil.getTimestamp(date));

                dayContainerList.add(model);

                prevCurrentDay++;
                index++;
            }
        }


        // now set current month date
        mCalendar.set(selectedYear, selectedMonth, 1);

        for (int i = 1; i <= daysInCurrentMonth; i++) {
            days[index].setText(String.valueOf(i));
            days[index].setTextColor(mCurrentMonthDayColor);

            final DayContainerModel model = new DayContainerModel();
            model.setIndex(index);
            model.setYear(selectedYear);
            model.setMonthNumber(selectedMonth);
            model.setMonth(MONTH_FULL_NAMES[selectedMonth]);
            model.setDay(i);
            String date = model.getDay() + " " + model.getMonth() + " " + model.getYear();
            model.setDate(date);
            model.setTimeInMillisecond(TimeUtil.getTimestamp(date));

            if (TimeUtil.isPastDate(model.getDate())) {

                if (mToday.equals(model.getDate())) {
                    model.setWeek(TimeUtil.getWeek(model.getDate()));
                    selectedDate = model;
                    mPreviousColor = mCurrentMonthDayColor;
                    mSelectedTexView = days[index];
                    mSelectedTexView.setGravity(Gravity.CENTER);
                    daysContainer[index].setBackgroundColor(getResources().getColor(R.color.brand_accent));
                    mSelectedLinearLayout = daysContainer[index];
                    days[index].setTextColor(mSelectedDayTextColor);
                } else {
                    days[index].setTextColor(mOffMonthDayColor);
                    days[index].setOnClickListener(null);
                    daysContainer[index].setBackgroundResource(R.drawable.gray_lighter_square);
                }

                // Log.d(">>>>>Past", String.valueOf(i));
            } else {
                daysContainer[index].setBackgroundResource(R.drawable.system_white_square);

                // Log.d(">>>>>No Past", String.valueOf(i));
            }
            //TODO put condition to disable past days here

            dayContainerList.add(model);

            final int finalIndex = index;
            days[index].setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!TimeUtil.isPastDate(model.getDate()) || mToday.equals(model.getDate())) {
                        if (mSelectedTexView != null) {
                            mSelectedLinearLayout.setBackgroundResource(R.drawable.system_white_square);
                            mSelectedTexView.setTextColor(mPreviousColor);
                        }
                        mPreviousColor = mCurrentMonthDayColor;
                        mSelectedTexView = days[finalIndex];
                        daysContainer[finalIndex].setBackgroundColor(getResources().getColor(R.color.brand_accent));
                        mSelectedLinearLayout = daysContainer[finalIndex];
                        days[finalIndex].setTextColor(mSelectedDayTextColor);

                        if (mCalenderDayClickListener != null) {
                            model.setWeek(TimeUtil.getWeek(model.getDate()));
                            Log.d(">>>>>Past", model.getWeek());
                            selectedDate = model;
                            mCalenderDayClickListener.onGetDay(model);
                        } else {
                            model.setWeek(TimeUtil.getWeek(model.getDate()));
                            selectedDate = model;
                        }
                    }
                }
            });

            index++;
        }

        int nextYear;
        int nextMonth;
        if (selectedMonth < 11) {
            nextYear = selectedYear;
            nextMonth = selectedMonth + 1;
        } else {
            nextYear = selectedYear + 1;
            nextMonth = 0;
        }

        // now set rest of day
        for (int i = 1; i <= nextMonthDays; i++) {
            days[index].setText(String.valueOf(i));
            days[index].setTextColor(mOffMonthDayColor);
            days[index].setOnClickListener(null);

            daysContainer[index].setBackgroundResource(R.drawable.system_white_square);

            /*suggested changes*/
            //days[index].setVisibility(GONE);

            final DayContainerModel model = new DayContainerModel();
            model.setIndex(index);
            model.setYear(nextYear);
            model.setMonthNumber(nextMonth);
            model.setMonth(MONTH_FULL_NAMES[nextMonth]);
            model.setDay(i);
            String date = model.getDay() + " " + model.getMonth() + " " + model.getYear();
            model.setDate(date);
            model.setTimeInMillisecond(TimeUtil.getTimestamp(date));

            dayContainerList.add(model);

            index++;
        }

    }

    private void gotoNextMonth() {
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);

        if (month < 11) {
            mCalendar.set(year, month + 1, 1);
        } else {
            mCalendar.set(year + 1, 0, 1);
        }

        initCalender(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH));
    }

    private void gotoPreviousMonth() {
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);

        if (month > 0) {
            mCalendar.set(year, month - 1, 1);
        } else {
            mCalendar.set(year - 1, 11, 1);
        }

        initCalender(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH));
    }


    private void initDaysInCalender(LayoutParams buttonParams, Context context, DisplayMetrics metrics) {
        int engDaysArrayCounter = 0;

        for (int weekNumber = 0; weekNumber < 6; ++weekNumber) {
            for (int dayInWeek = 0; dayInWeek < 7; ++dayInWeek) {

                LinearLayout linearLayout = new LinearLayout(context);
                linearLayout.setLayoutParams(buttonParams);
                linearLayout.setOrientation(VERTICAL);
                linearLayout.setGravity(Gravity.CENTER);
                linearLayout.setBackgroundResource(R.drawable.system_white_square);
                final TextView day = new TextView(context);
                day.setTextColor(Color.parseColor(CUSTOM_GREY));
                day.setBackgroundColor(Color.TRANSPARENT);
                day.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                day.setTextSize(18);
                day.setSingleLine();
                day.setPadding(20, 20, 20, 20);
                day.setGravity(Gravity.CENTER);
                linearLayout.addView(day);
                days[engDaysArrayCounter] = day;
                weeks[weekNumber].addView(linearLayout);
                daysContainer[engDaysArrayCounter] = linearLayout;

                engDaysArrayCounter++;
            }
        }
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.Calender);

        mBackgroundColor = typedArray.getColor(R.styleable.Calender_calender_background, DEFAULT_BACKGROUND_COLOR);

        mSelectorColor = typedArray.getColor(R.styleable.Calender_selector_color, DEFAULT_SELECTOR_COLOR);

        mSelectedDayTextColor = typedArray.getColor(R.styleable.Calender_selected_day_text_color, DEFAULT_SELECTED_DAY_COLOR);

        mCurrentMonthDayColor = typedArray.getColor(R.styleable.Calender_current_month_day_color, DEFAULT_CURRENT_MONTH_DAY_COLOR);

        mOffMonthDayColor = typedArray.getColor(R.styleable.Calender_off_month_day_color, DEFAULT_OFF_MONTH_DAY_COLOR);

        mMonthTextColor = typedArray.getColor(R.styleable.Calender_month_color, DEFAULT_TEXT_COLOR);

        mWeekNameColor = typedArray.getColor(R.styleable.Calender_week_name_color, DEFAULT_TEXT_COLOR);

        nextButtonDrawable = typedArray.getDrawable(R.styleable.Calender_next_icon);

        prevButtonDrawable = typedArray.getDrawable(R.styleable.Calender_previous_icon);

        isShowHeader = typedArray.getBoolean(R.styleable.Calender_is_show_header, DEFAULT_IS_SHOW_HEADER);

        typedArray.recycle();
    }

    private void initView(Context context) {
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            mainView = inflater.inflate(R.layout.layout_calender, this);
        }
    }

    private LayoutParams getdaysLayoutParams() {
        LayoutParams buttonParams = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        buttonParams.weight = 1;
        return buttonParams;
    }

    private DayContainerModel getDaysContainerModel(String date) {
        for (DayContainerModel model : dayContainerList) {
            if (model.getDate().equals(date)) {
                return model;
            }
        }
        return null;
    }


    public void initCalderItemClickCallback(CalenderDayClickListener listener) {
        this.mCalenderDayClickListener = listener;
    }

    public DayContainerModel getSelectedDate() {
        return selectedDate;
    }
}
