package com.digivalet.brandresidential.ui.bottomsheets;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.digivalet.brandresidential.BR;
import com.digivalet.brandresidential.R;
import com.digivalet.brandresidential.databinding.BottomDialogGuestRegisterdBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class GuestRegisteredBottomDialogFragment extends BottomSheetDialogFragment {

    private BottomDialogGuestRegisterdBinding binding;
    private InviteShareBottomDialog inviteShareBottomDialog;

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        getDialog().getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_dialog_guest_registerd, container, false);
        binding.setVariable(BR.registeredBottomVM, this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        getPeekHeight();
    }

    private void init(){
        inviteShareBottomDialog= new InviteShareBottomDialog();
    }

    /*method for getting match parent peek height*/
    public void getPeekHeight() {
        getDialog().setOnShowListener(dialog -> {
            BottomSheetDialog d = (BottomSheetDialog) dialog;
            FrameLayout bottomSheet = (FrameLayout) d.findViewById(R.id.design_bottom_sheet);
            CoordinatorLayout coordinatorLayout = (CoordinatorLayout) bottomSheet.getParent();
            BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
            bottomSheetBehavior.setPeekHeight(bottomSheet.getHeight());
            coordinatorLayout.getParent().requestLayout();
        });
    }

    public void onCloseButtonClick() {
        this.dismiss();
    }

    public void onSendMessageClick(){
        Uri uri = Uri.parse("smsto:12346556");
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("sms_body", "Here you can set the SMS text to be sent");
        startActivity(it);
    }

    public void onShareInviteClick(){
        if (!inviteShareBottomDialog.isAdded()) {
            inviteShareBottomDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.TransparentDialog);
            inviteShareBottomDialog.show(getChildFragmentManager(), null);
        }
    }
}
