package com.digivalet.brandresidential.ui.bottomsheets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.digivalet.brandresidential.R;
import com.digivalet.brandresidential.databinding.BottomDialogRepeatBinding;
import com.digivalet.brandresidential.helpers.CustomItemClickListener;
import com.digivalet.brandresidential.ui.adapters.RepeatDialogAdapter;
import com.digivalet.utils.RecyclerItemClickListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Arrays;


public class RepeatBottomDialogFragment extends BottomSheetDialogFragment {

    private BottomDialogRepeatBinding bottomDialogRepeatBinding;
    private CustomItemClickListener listener;

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        getDialog().getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bottomDialogRepeatBinding = DataBindingUtil.inflate(inflater, R.layout.bottom_dialog_repeat,
                container, false);
        return bottomDialogRepeatBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    public void setOnItemClickListener(CustomItemClickListener listener) {
        this.listener = listener;
    }

    private void initView() {
        //set labels
        bottomDialogRepeatBinding.bottomSheetTitle.setText(R.string.label_repeat);

        /*add adapter*/
        RepeatDialogAdapter adapter = new RepeatDialogAdapter(Arrays.asList(getContext().getResources().getStringArray(R.array.label_repeat_array)));
        bottomDialogRepeatBinding.bottomSheetList.setAdapter(adapter);
        bottomDialogRepeatBinding.bottomSheetList.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), (view, position) -> {
            adapter.setPosition(position);

            if (position == 5) {
                CustomDialogFragment customDialogFragment = new CustomDialogFragment();
                customDialogFragment.show(getParentFragmentManager(), null);
            }
        }
        ));

        /*done btn click*/
        bottomDialogRepeatBinding.bottomSheetDoneBtn.setOnClickListener(e -> {
            if (listener != null)
                listener.onItemClickListener(0, adapter.getSelectedValue());

            dismiss();
        });
    }
}
