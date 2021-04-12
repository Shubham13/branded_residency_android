package com.digivalet.brandresidential.ui.viewholders;

import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.digivalet.brandresidential.R;
import com.digivalet.brdata.dto.GuestAccessX;
import com.digivalet.utils.expandable.ParentViewHolder;
import com.digivalet.utils.styler.Host;
import com.digivalet.utils.styler.StyleBuilder;

public class GuestAccessParentViewHolder extends ParentViewHolder {

    private static final float INITIAL_POSITION = 180f;
    private static final float ROTATED_POSITION = 0.0f;

    private final ImageView arrowImage;
    private final TextView label_access;

    public GuestAccessParentViewHolder(View itemView) {
        super(itemView);
        label_access = (TextView) itemView.findViewById(R.id.label_access);
        arrowImage = (ImageView) itemView.findViewById(R.id.arrow_expand);
    }

    public void bind(GuestAccessX accessX) {
        label_access.setText(label_access.getContext().getResources().getString(R.string.label_access));
    }

    @Override
    public void setExpanded(boolean expanded) {
        super.setExpanded(expanded);

        if (expanded) {
            arrowImage.setRotation(ROTATED_POSITION);
        } else {
            arrowImage.setRotation(INITIAL_POSITION);
        }

    }

    @Override
    public void onExpansionToggled(boolean expanded) {
        super.onExpansionToggled(expanded);

        RotateAnimation rotateAnimation;
        if (expanded) {
            rotateAnimation = new RotateAnimation(INITIAL_POSITION,
                    ROTATED_POSITION,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        } else {
            rotateAnimation = new RotateAnimation(-1 * INITIAL_POSITION,
                    ROTATED_POSITION,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        }

        rotateAnimation.setDuration(200);
        rotateAnimation.setFillAfter(true);
        arrowImage.startAnimation(rotateAnimation);
    }
}
