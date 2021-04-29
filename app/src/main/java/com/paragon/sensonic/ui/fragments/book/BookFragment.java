package com.paragon.sensonic.ui.fragments.book;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.paragon.sensonic.BR;
import com.paragon.sensonic.R;
import com.paragon.sensonic.databinding.FragmentBookBinding;
import com.paragon.utils.base.BaseFragment;
import com.example.amplifydemo.adapter.FavoriteAdapter;
import com.example.amplifydemo.adapter.RecentAdapter;
import com.google.android.material.appbar.AppBarLayout;

public class BookFragment extends BaseFragment<FragmentBookBinding, BookViewModel> implements BookNavigator {

    private final BookViewModel bookViewModel = new BookViewModel();
    FavoriteAdapter favoriteAdapter;
    RecentAdapter recentAdapter;

    @Override
    public int getBindingVariable() {
        return BR.bookVM;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_book;
    }

    @Override
    public BookViewModel getViewModel() {
        return bookViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookViewModel.setNavigator(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bookViewModel.init();
    }

    @Override
    public void init() {
       /* mViewDataBinding.toolbar.toolbarTitle.setText(getString(R.string.menu_book));
        mViewDataBinding.toolbar.toolbarLeftLayout.setVisibility(View.INVISIBLE);
        mViewDataBinding.toolbar.toolbarRightImage.setVisibility(View.VISIBLE);
        mViewDataBinding.toolbar.toolbarRightImage.setOnClickListener(e -> {
            getBaseActivity().loadFragment(new BookVisitorFragment(), R.id.container);
        });*/
        getBaseActivity().setTitle("Book");
        getBaseActivity().setSupportActionBar(mViewDataBinding.toolbar);
        getBaseActivity().getSupportActionBar().setDisplayShowTitleEnabled(true);

        favoriteAdapter = new FavoriteAdapter();
        mViewDataBinding.contentInclude.favoriteRv.setAdapter(favoriteAdapter);

        recentAdapter = new RecentAdapter();
        mViewDataBinding.contentInclude.recentRv.setAdapter(recentAdapter);

        mViewDataBinding.appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset)-appBarLayout.getTotalScrollRange() == 0) {
                    Log.e("state","Collapsed");
                    mViewDataBinding.toolbarLayout.setBackgroundColor(getActivity().getColor(R.color.dark_background));
                }
                else {
                    //Expanded
                    mViewDataBinding.toolbarLayout.setBackgroundColor(getActivity().getColor(R.color.bar_tabbar));
                    Log.e("state","Expanded");
                }
            }
        });

    }

    @Override
    public void loadTheme() {

        //StyleBuilder.setStyleOnText(Host.tool_bar, mViewDataBinding.toolbar.toolbarTitle);
    }
}
