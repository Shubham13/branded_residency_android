
package com.paragon.utils.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

import com.paragon.utils.R;
import com.paragon.utils.logger.Logger;

/**
 * Created by Rupesh Saxena
 */

public class DVProgressDialog extends Dialog {
    private Dialog pDialog;
    private final Context context;


    public DVProgressDialog(Context context) {
        super(context);
        this.context = context;
    }

    public void hideProgressDialog() {
        try {
            if (pDialog != null) {
                if (pDialog.isShowing())
                    pDialog.dismiss();
                pDialog = null;
            }
        } catch (Exception e) {
            Logger.e(this.getClass().getName(), e.toString());
        }
    }

    public void showProgressDialog(String message) {
        try {
            if (pDialog == null) {
                pDialog = createProgressDialog(context, message);
                pDialog.show();
            }
        } catch (Exception e) {
            Logger.e(this.getClass().getName(), e.toString());
        }
    }

    private Dialog createProgressDialog(Context context, String message) {
        pDialog = new Dialog(context, R.style.CustomProgressDialog);
        pDialog.setContentView(R.layout.dialog_custom_progress);
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        TextView textOfLoader = (TextView) pDialog.findViewById(R.id.messageTV);
        textOfLoader.setText(message);
        pDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = pDialog.getWindow().getAttributes();
        lp.dimAmount = 0.4f;
        pDialog.getWindow().setAttributes(lp);
        return pDialog;
    }
}

