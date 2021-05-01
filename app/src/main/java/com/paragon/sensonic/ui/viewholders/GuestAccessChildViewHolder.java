package com.paragon.sensonic.ui.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paragon.sensonic.R;
import com.paragon.sensonic.utils.GuestType;
import com.paragon.brdata.dto.CommonAccess;
import com.paragon.utils.expandable.ChildViewHolder;
import com.paragon.utils.expandable.ParentListItem;

import java.util.List;

public class GuestAccessChildViewHolder extends ChildViewHolder {

    private final CheckBox checkbox_access;
    private final LinearLayout row_checkbox;
    private final TextView label_privileges;
    private final LinearLayout row_notes;
    private final Context context;
    private final EditText edit_note;

    public GuestAccessChildViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        row_checkbox = itemView.findViewById(R.id.row_checkbox);
        checkbox_access = (CheckBox) itemView.findViewById(R.id.checkbox_access);
        label_privileges = itemView.findViewById(R.id.label_privileges);
        row_notes = itemView.findViewById(R.id.row_notes);
        edit_note = itemView.findViewById(R.id.edit_note);
    }

    public void bind(CommonAccess commonAccess, List<? extends ParentListItem> parentItemList, int position, String guestType) {
        if (guestType.equalsIgnoreCase(GuestType.ONE_TIME_GUEST.getValue())) {
            checkbox_access.setText(commonAccess.getTitle());
        } else {
            checkbox_access.setText(commonAccess.getTitle());

            if (!commonAccess.getOneTimeGuestAllowed()) {
                label_privileges.setVisibility(View.VISIBLE);
            } else {
                label_privileges.setVisibility(View.GONE);
            }

            if (parentItemList.get(0).getChildItemList().size() == position) {
                row_notes.setVisibility(View.VISIBLE);
            } else {
                row_notes.setVisibility(View.GONE);
            }
        }
    }
}
