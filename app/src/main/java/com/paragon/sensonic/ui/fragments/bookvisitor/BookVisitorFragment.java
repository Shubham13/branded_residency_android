package com.paragon.sensonic.ui.fragments.bookvisitor;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.paragon.sensonic.BR;
import com.paragon.sensonic.R;
import com.paragon.sensonic.databinding.FragmentBookVisitorBinding;
import com.paragon.sensonic.helpers.CustomItemClickListener;
import com.paragon.sensonic.ui.adapters.DateSelectorAdapter;
import com.paragon.sensonic.ui.adapters.GuestTypeAdapter;
import com.paragon.sensonic.ui.bottomsheets.CalenderBottomDialogFragment;
import com.paragon.sensonic.ui.bottomsheets.RepeatBottomDialogFragment;
import com.paragon.sensonic.ui.bottomsheets.TimeBottomDialogFragment;
import com.paragon.sensonic.ui.fragments.guestsprofile.GuestsProfileFragment;
import com.paragon.sensonic.ui.fragments.registerguest.RegisterGuestFragment;
import com.paragon.brdata.dto.GuestType;
import com.paragon.utils.RecyclerItemClickListener;
import com.paragon.utils.base.BaseFragment;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class BookVisitorFragment extends BaseFragment<FragmentBookVisitorBinding, BookVisitorViewModel> implements BookVisitorNavigator {

    private final BookVisitorViewModel bookVisitorViewModel = new BookVisitorViewModel();
    private com.paragon.sensonic.helpers.GuestType type;
    private CalenderBottomDialogFragment calenderBottomDialogFragment;
    private TimeBottomDialogFragment timeBottomDialogFragment;
    private RepeatBottomDialogFragment repeatBottomDialogFragment;

    @Override
    public int getBindingVariable() {
        return BR.bookVisitorVM;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_book_visitor;
    }

    @Override
    public BookVisitorViewModel getViewModel() {
        return bookVisitorViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookVisitorViewModel.setNavigator(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bookVisitorViewModel.init();
    }

    @Override
    public void init() {
        mViewDataBinding.toolbar.toolbarTitle.setText(getString(R.string.label_guest));
        mViewDataBinding.toolbar.toolbarLeftLayout.setVisibility(View.VISIBLE);
        mViewDataBinding.toolbar.toolbarRightImage.setVisibility(View.VISIBLE);
        mViewDataBinding.toolbar.toolbarLeftLayout.setOnClickListener(e -> Objects.requireNonNull(getActivity()).onBackPressed());
        bookVisitorViewModel.setGuestTypeData(getContext());

        //init bottom dialogs
        calenderBottomDialogFragment = new CalenderBottomDialogFragment();
        timeBottomDialogFragment = new TimeBottomDialogFragment();
        repeatBottomDialogFragment = new RepeatBottomDialogFragment();
    }

    @Override
    public void setGuestTypeData(GuestType guestType) {
        type = com.paragon.sensonic.helpers.GuestType.ONE_TIME_GUEST;
        mViewDataBinding.listGuestType.setLayoutManager(new GridLayoutManager(getContext(), 3));
        GuestTypeAdapter guestTypeAdapter = new GuestTypeAdapter(getContext(), guestType.getData());
        guestTypeAdapter.onItemClickListener(new CustomItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                switch (position) {
                    case 0:
                        type = com.paragon.sensonic.helpers.GuestType.ONE_TIME_GUEST;
                        onOneTimeGuestClick();
                        break;
                    case 1:
                        type = com.paragon.sensonic.helpers.GuestType.FREQUENT_GUEST;
                        onFrequentGuestClick();
                        break;
                    case 2:
                        type = com.paragon.sensonic.helpers.GuestType.SHORT_STAYING_GUEST;
                        onShortStayingGuestClick();
                        break;
                }

                guestTypeAdapter.setPosition(position);
            }
        });

        mViewDataBinding.listGuestType.setAdapter(guestTypeAdapter);
    }

    @Override
    public void onOneTimeGuestClick() {
        mViewDataBinding.rowStartDate.setVisibility(View.GONE);
        mViewDataBinding.rowAllowUntil.setVisibility(View.GONE);
        mViewDataBinding.divider.setVisibility(View.VISIBLE);
        mViewDataBinding.rowDatesHolder.setVisibility(View.VISIBLE);
        mViewDataBinding.rowScheduleHolder.setVisibility(View.VISIBLE);
        mViewDataBinding.rowTimeSelection.setVisibility(View.VISIBLE);
        mViewDataBinding.rowRepeatSelection.setVisibility(View.GONE);
    }

    @Override
    public void onFrequentGuestClick() {
        mViewDataBinding.rowStartDate.setVisibility(View.VISIBLE);
        mViewDataBinding.rowAllowUntil.setVisibility(View.VISIBLE);
        mViewDataBinding.divider.setVisibility(View.GONE);
        mViewDataBinding.rowDatesHolder.setVisibility(View.GONE);
        mViewDataBinding.rowScheduleHolder.setVisibility(View.GONE);
        mViewDataBinding.rowTimeSelection.setVisibility(View.GONE);
        mViewDataBinding.rowRepeatSelection.setVisibility(View.VISIBLE);
    }

    @Override
    public void onShortStayingGuestClick() {
        mViewDataBinding.rowStartDate.setVisibility(View.VISIBLE);
        mViewDataBinding.rowAllowUntil.setVisibility(View.VISIBLE);
        mViewDataBinding.divider.setVisibility(View.GONE);
        mViewDataBinding.rowDatesHolder.setVisibility(View.GONE);
        mViewDataBinding.rowScheduleHolder.setVisibility(View.GONE);
        mViewDataBinding.rowTimeSelection.setVisibility(View.GONE);
        mViewDataBinding.rowRepeatSelection.setVisibility(View.VISIBLE);
    }

    @Override
    public void onContinueClick() {
        getBaseActivity().loadFragment(new RegisterGuestFragment(type), R.id.container);
    }

    @Override
    public void onGuestStatusClick() {
        getBaseActivity().loadFragment(new GuestsProfileFragment(), R.id.container);
    }

    @Override
    public void onRepeatClick() {
        if (!repeatBottomDialogFragment.isAdded()) {
            repeatBottomDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.TransparentDialog);
            repeatBottomDialogFragment.show(getChildFragmentManager(), null);
            repeatBottomDialogFragment.setOnItemClickListener(new CustomItemClickListener() {
                @Override
                public void onItemClickListener(int position, String value) {
                    if (value != null)
                        mViewDataBinding.editRepeat.setText(value);
                }
            });
        }
    }

    @Override
    public void onTimeClick() {
        if (!timeBottomDialogFragment.isAdded()) {
            timeBottomDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.TransparentDialog);
            timeBottomDialogFragment.show(getChildFragmentManager(), null);
            timeBottomDialogFragment.setOnItemClickListener(new CustomItemClickListener() {
                @Override
                public void onItemClickListener(int position, String value) {
                    if (value != null)
                        mViewDataBinding.editTime.setText(value);
                }
            });
        }
    }

    @Override
    public void setDateSelector(Date date) {
        List<Date> list = bookVisitorViewModel.getDates(date);
        setDateLabel(0, list);
        DateSelectorAdapter adapter = new DateSelectorAdapter(getContext(), list);
        mViewDataBinding.listDate.setAdapter(adapter);
        mViewDataBinding.listDate.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), (view, position) -> {
            adapter.setPosition(position);
            if (position != 4) {
                setDateLabel(position, list);
            } else {
                if (!calenderBottomDialogFragment.isAdded()) {
                    calenderBottomDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.TransparentDialog);
                    calenderBottomDialogFragment.show(getChildFragmentManager(), null);
                    calenderBottomDialogFragment.setOnCalendarClickListener(new CustomItemClickListener() {
                        @Override
                        public void onItemClickListener(int position, String value) {
                            mViewDataBinding.editDate.setText(value);
                        }
                    });
                }
            }
        }));
    }

    @Override
    public void startDateClick() {
        if (!calenderBottomDialogFragment.isAdded()) {
            calenderBottomDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.TransparentDialog);
            calenderBottomDialogFragment.show(getChildFragmentManager(), null);
            calenderBottomDialogFragment.setOnCalendarClickListener(new CustomItemClickListener() {
                @Override
                public void onItemClickListener(int position, String value) {
                    mViewDataBinding.editStartDate.setText(value);
                }
            });
        }
    }

    @Override
    public void allowUntilClick() {
        if (!calenderBottomDialogFragment.isAdded()) {
            calenderBottomDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.TransparentDialog);
            calenderBottomDialogFragment.show(getChildFragmentManager(), null);
            calenderBottomDialogFragment.setOnCalendarClickListener(new CustomItemClickListener() {
                @Override
                public void onItemClickListener(int position, String value) {
                    mViewDataBinding.editEndDate.setText(value);
                }
            });
        }
    }

    private void setDateLabel(int position, List<Date> list) {
        String week;
        String month = DateFormat.format("MMM", list.get(position)).toString();
        String day = DateFormat.format("dd", list.get(position)).toString();
        String year = DateFormat.format("yyyy", list.get(position)).toString();

        if (position == 0)
            week = getString(R.string.label_today);
        else
            week = DateFormat.format("EEEE", list.get(position)).toString();

        String date = week + "," + month + " " + day + ", " + year;
        mViewDataBinding.editDate.setText(date);
    }

}
