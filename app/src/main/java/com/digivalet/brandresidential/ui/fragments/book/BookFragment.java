package com.digivalet.brandresidential.ui.fragments.book;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.digivalet.brandresidential.BR;
import com.digivalet.brandresidential.R;
import com.digivalet.brandresidential.databinding.FragmentBookBinding;
import com.digivalet.brandresidential.ui.fragments.bookvisitor.BookVisitorFragment;
import com.digivalet.utils.base.BaseFragment;
import com.digivalet.utils.styler.Host;
import com.digivalet.utils.styler.StyleBuilder;

public class BookFragment extends BaseFragment<FragmentBookBinding, BookViewModel> implements BookNavigator {

    private final BookViewModel bookViewModel = new BookViewModel();

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
        mViewDataBinding.toolbar.toolbarTitle.setText(getString(R.string.menu_book));
        mViewDataBinding.toolbar.toolbarBackBtn.setVisibility(View.INVISIBLE);
        mViewDataBinding.toolbar.toolbarRightImage.setVisibility(View.VISIBLE);
        mViewDataBinding.toolbar.toolbarRightImage.setOnClickListener(e -> {
            getBaseActivity().loadFragment(new BookVisitorFragment(), R.id.container);
        });
    }

    @Override
    public void loadTheme() {
        StyleBuilder.setStyleOnView(Host.tool_bar, mViewDataBinding.toolbar.toolbarDivider);
        StyleBuilder.setStyleOnText(Host.tool_bar, mViewDataBinding.toolbar.toolbarTitle);
    }
}
