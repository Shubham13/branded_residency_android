package com.digivalet.brandresidential.ui.fragments.interests;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.digivalet.brandresidential.BR;
import com.digivalet.brandresidential.R;
import com.digivalet.brandresidential.databinding.FragmentInterestsBinding;
import com.digivalet.brandresidential.helpers.CustomItemClickListener;
import com.digivalet.brandresidential.ui.adapters.InterestAdapter;
import com.digivalet.brandresidential.ui.adapters.InterestFilterAdapter;
import com.digivalet.brdata.dto.InterestData;
import com.digivalet.utils.RecyclerItemClickListener;
import com.digivalet.utils.base.BaseActivity;
import com.digivalet.utils.base.BaseFragment;

import java.util.List;
import java.util.Objects;

public class InterestsActivity extends BaseActivity<FragmentInterestsBinding, InterestsViewModel> implements InterestsNavigator {

    private final InterestsViewModel interestsViewModel = new InterestsViewModel();
    private boolean isToolbarItemClick = true;
    private LinearLayoutManager linearLayoutManager;
    private InterestFilterAdapter interestFilterAdapter;

    @Override
    public int getBindingVariable() {
        return BR.interestsVM;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_interests;
    }

    @Override
    public InterestsViewModel getViewModel() {
        return interestsViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        interestsViewModel.setNavigator(this);
        interestsViewModel.init();
    }
    
    @Override
    public void init() {
        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mViewDataBinding.interestToolbar.toolbarRightImage.setVisibility(View.GONE);
        mViewDataBinding.interestToolbar.toolbarTitle.setText(getString(R.string.label_interests));
        mViewDataBinding.interestToolbar.toolbarBackBtn.setOnClickListener(e -> Objects.requireNonNull(this).onBackPressed());
        interestsViewModel.getInterestsData(this);
    }

    @Override
    public void loadTheme() {
       /* StyleBuilder.setStyleOnView(Host.tool_bar, mViewDataBinding.interestToolbar.toolbarDivider);
        StyleBuilder.setStyleOnText(Host.tool_bar, mViewDataBinding.interestToolbar.toolbarBackBtn);
        StyleBuilder.setStyleOnText(Host.tool_bar, mViewDataBinding.interestToolbar.toolbarTitle);*/
    }

    @Override
    public void setFilterList(List<InterestData> list) {
        /*for smooth scroll snap*/
        RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(this) {
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };

        interestFilterAdapter = new InterestFilterAdapter(this, list);
        mViewDataBinding.interestToolbar.toolbarItemList.setAdapter(interestFilterAdapter);
        /*default selection*/
        interestFilterAdapter.setPosition(0);

        mViewDataBinding.interestToolbar.toolbarItemList.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                smoothScroller.setTargetPosition(position);
                linearLayoutManager.startSmoothScroll(smoothScroller);
                interestFilterAdapter.setPosition(position);
                isToolbarItemClick = true;
                mViewDataBinding.interestToolbar.toolbarItemList.smoothScrollToPosition(position);
            }
        }));
    }

    @Override
    public void setInterestList(List<InterestData> list) {
        InterestAdapter adapter = new InterestAdapter(this);
        adapter.setData(list);
        mViewDataBinding.interestList.setLayoutManager(linearLayoutManager);
        mViewDataBinding.interestList.setAdapter(adapter);
        adapter.setOnItemClickListener(new CustomItemClickListener() {
            @Override
            public void onItemClickListener(int position, int view) {
                if (view == R.id.label_all) {
                    adapter.setAllSelected(position);
                }
            }
        });

        /*touch listener on list*/
        mViewDataBinding.interestList.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                isToolbarItemClick = false;
                return false;
            }
        });

        /*add scroll listener on interest list*/
        mViewDataBinding.interestList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!isToolbarItemClick) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) mViewDataBinding.interestList.getLayoutManager();
                    Rect globalVisibleRect = new Rect();
                    mViewDataBinding.interestList.getGlobalVisibleRect(globalVisibleRect);
                    int first = layoutManager.findFirstVisibleItemPosition();
                    int last = layoutManager.findLastVisibleItemPosition();

                    if (first == 0) {
                        mViewDataBinding.interestToolbar.toolbarItemList.smoothScrollToPosition(0);
                        interestFilterAdapter.setPosition(0);
                    } else if (!recyclerView.canScrollVertically(1)) {
                        mViewDataBinding.interestToolbar.toolbarItemList.smoothScrollToPosition(interestFilterAdapter.getItemCount() - 1);
                        interestFilterAdapter.setPosition(interestFilterAdapter.getItemCount() - 1);
                    }else {
                        mViewDataBinding.interestToolbar.toolbarItemList.smoothScrollToPosition(first);
                        interestFilterAdapter.setPosition(first);
                    }
                }
            }
        });
    }
}
