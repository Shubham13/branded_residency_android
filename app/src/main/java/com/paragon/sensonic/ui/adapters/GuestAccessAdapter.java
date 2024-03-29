package com.paragon.sensonic.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paragon.sensonic.R;

import com.paragon.sensonic.ui.viewholders.GuestAccessChildViewHolder;
import com.paragon.sensonic.ui.viewholders.GuestAccessParentViewHolder;
import com.paragon.brdata.dto.CommonAccess;
import com.paragon.brdata.dto.GuestAccessX;

import com.paragon.utils.expandable.ExpandableRecyclerAdapter;
import com.paragon.utils.expandable.ParentListItem;

import java.util.List;

public class GuestAccessAdapter extends ExpandableRecyclerAdapter<GuestAccessParentViewHolder, GuestAccessChildViewHolder> {

    private final LayoutInflater layoutInflater;
    private final Context context;
    private final String guestType;

    public GuestAccessAdapter(Context context, List<? extends ParentListItem> parentItemList, String guestType) {
        super(parentItemList);
        this.context = context;
        this.guestType = guestType;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public GuestAccessParentViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View view = layoutInflater.inflate(R.layout.row_guest_access_parent, parentViewGroup, false);
        return new GuestAccessParentViewHolder(view);
    }

    @Override
    public GuestAccessChildViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        View view = layoutInflater.inflate(R.layout.row_guest_access_child, childViewGroup, false);
        return new GuestAccessChildViewHolder(view, context);
    }

    @Override
    public void onBindParentViewHolder(GuestAccessParentViewHolder holder, int position, ParentListItem parentListItem) {
        GuestAccessX data = (GuestAccessX) parentListItem;
        holder.bind(data);
    }

    @Override
    public void onBindChildViewHolder(GuestAccessChildViewHolder holder, int position, Object childListItem) {
        CommonAccess commonAccess = (CommonAccess) childListItem;
        holder.bind(commonAccess, getParentItemList(), position, guestType);
    }
}
