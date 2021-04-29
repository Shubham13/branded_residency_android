package com.paragon.utils.base;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.paragon.utils.ActivityNavigator;
import com.paragon.utils.MultiplePermissionsCallBack;
import com.paragon.utils.R;
import com.paragon.utils.ScreenOrientationHelper;
import com.paragon.utils.SinglePermissionCallBack;
import com.paragon.utils.dialogs.DVDialog;
import com.paragon.utils.dialogs.DVProgressDialog;
import com.paragon.utils.dialogs.OnDialogButtonsClick;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.List;


/**
 * Created by Rupesh Saxena
 */

public abstract class BaseActivity<T extends ViewDataBinding, V extends BaseViewModel> extends AppCompatActivity implements BaseNavigator, ScreenOrientationHelper.ScreenOrientationChangeListener {

    public T mViewDataBinding;
    private V mViewModel;
    private DVProgressDialog progressDialog;
    private ActivityNavigator activityNavigator;
    private ScreenOrientationHelper helper;

    public abstract int getBindingVariable();

    public abstract @LayoutRes
    int getLayoutId();

    public abstract V getViewModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new ScreenOrientationHelper(this);
        activityNavigator = new ActivityNavigator(this);
        helper.onCreate(savedInstanceState);
        helper.setScreenOrientationChangeListener(this);
        performDataBinding();
    }

    @Override
    protected void onStart() {
        super.onStart();
        helper.onStart();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        helper.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        helper.onRestoreInstanceState(savedInstanceState);
    }

    public T getViewDataBinding() {
        return mViewDataBinding;
    }

    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        this.mViewModel = mViewModel == null ? getViewModel() : mViewModel;
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.executePendingBindings();
    }


    @Override
    public void showProgressDialog() {
        progressDialog = new DVProgressDialog(this);
        progressDialog.showProgressDialog("Loading");
    }

    @Override
    public void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.hideProgressDialog();
        }
    }

    @Override
    public void showGeneralDialog(String title, String message, String buttonTxt, OnDialogButtonsClick listener) {
        DVDialog.showGeneralDialog(this, title, message, buttonTxt, listener);
    }

    @Override
    public void showNPButtonDialog(String title, String message, String pButtonTxt, String nButtonTxt, OnDialogButtonsClick listener) {
        DVDialog.showNPButtonDialog(this, title, message, pButtonTxt, message, listener);
    }

    @Override
    public int getIntegerResource(int id) {
        return getResources().getInteger(id);
    }

    @Override
    public ActivityNavigator getActivityNavigator(Context context) {
        return activityNavigator;
    }

    /**
     * Requesting single permission
     * This uses single permission model from dexter
     */
    public void requestSinglePermission(SinglePermissionCallBack singlePermissionCallBack, String permission) {
        Dexter.withActivity(this)
                .withPermission(permission)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        // permission is granted
                        singlePermissionCallBack.isPermissionsGranted();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        if (response.isPermanentlyDenied()) {
                            singlePermissionCallBack.isPermissionPermanentlyDenied();
                        } else {
                            singlePermissionCallBack.isPermissionDenied();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        singlePermissionCallBack.onPermissionRationaleShouldBeShown(token);
                    }
                }).
                withErrorListener(error -> {
                    singlePermissionCallBack.onError();
                })
                .onSameThread()
                .check();
    }

    /**
     * Requesting multiple permissions at once This uses multiple permission model from dexter
     */
    public void requestMultiplePermissions(MultiplePermissionsCallBack multiplePermissionsCallBack, String... permissions) {
        Dexter.withActivity(this)
                .withPermissions(permissions)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            multiplePermissionsCallBack.isAllPermissionsGranted();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            multiplePermissionsCallBack.isAnyPermissionPermanentlyDenied();
                        }

                        if (!report.isAnyPermissionPermanentlyDenied()) {
                            if (report.getDeniedPermissionResponses().size() > 0) {
                                multiplePermissionsCallBack.somePermissionsDenied();
                            }
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        multiplePermissionsCallBack.onPermissionRationaleShouldBeShown(token);
                    }
                }).
                withErrorListener(error -> {
                    multiplePermissionsCallBack.onError();
                })
                .onSameThread()
                .check();
    }


    /*Load fragment with back stack*/
    public void loadFragment(Fragment fragment, @IdRes int containerViewId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(containerViewId, fragment);
        transaction.addToBackStack(fragment.getClass().getName());
        transaction.commit();
    }

    /*Load default fragment without back stack*/
    public void loadDefaultFragment(Fragment fragment, @IdRes int containerViewId) {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(containerViewId, fragment);
        transaction.commit();
    }

    /*get view model*/
    public <VM extends ViewModel> VM getVM(@NonNull Class<VM> modelClass) {
        return new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(modelClass);
    }
}

