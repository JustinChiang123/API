package com.zhapp.api.android.widget.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhapp.api.android.img.PicassoUtil;

/**
 * <pre>
 * Describe: 默认的提示框。
 *
 * Author: <a href="mailto:Justin_Chiang@foxmail.com">Justin_Chiang<a/>
 * Date: 2017-08-27
 * Time: 23:32
 * <pre/>
 */
public class DialogUtil {
    private Context mContext;
    public AlertDialog mAlertDialog;
    private TextView textView;
    private ImageView imageView;
    private ProgressBar progressBar;
    private LinearLayout layout;
    private boolean cancelable;
    private float density;
    private View.OnClickListener onClik1;
    private View.OnClickListener onClik2;
    private View.OnClickListener onClik3;

    private DialogUtil(Context context) {
        this.mContext = context;
        density = mContext.getResources().getDisplayMetrics().density;
    }

    public static DialogUtil init(Context context) {
        return new DialogUtil(context);
    }

    public DialogUtil create() {
        mAlertDialog = new AlertDialog.Builder(mContext).create();
        mAlertDialog.setOnShowListener(dialog -> start(Effectstype.Shake));
        return this;
    }

    public void start(Effectstype type) {
        BaseEffects animator = type.getAnimator();
        animator.start(mAlertDialog.getWindow().getDecorView());
    }

    public DialogUtil setTitle(String title) {
        mAlertDialog.setTitle(title);
        return this;
    }

    public DialogUtil setCancelable(boolean flag) {
        this.cancelable = flag;
        mAlertDialog.setCancelable(flag);
        return this;
    }

    public DialogUtil setCanceledOnTouchOutside(boolean cancel) {
        mAlertDialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    private DialogUtil buildView() {
        layout = new LinearLayout(mContext);
        layout.setOrientation(LinearLayout.VERTICAL);
        if (textView != null) {
            if (imageView != null || progressBar != null) {
                textView.setPadding((int) (15 * density), 0, (int) (15 * density), (int) (15 * density));
            }
            else {
                textView.setPadding((int) (15 * density), 0, (int) (15 * density), 0);
            }
            layout.addView(textView);
        }
        if (imageView != null) {
            layout.addView(imageView);
        }
        else if (progressBar != null) {
            layout.addView(progressBar);
        }
        mAlertDialog.setView(layout, (int) (15 * density), (int) (15 * density), (int) (15 * density), 0);

        if (!cancelable) {
            mAlertDialog.setOnKeyListener((dialog, keyCode, event) -> {
                if (mAlertDialog.isShowing()) {
                    start(Effectstype.Shake);
                    return true;
                }
                return false;
            });
        }
        return this;
    }


    public DialogUtil setText(String msg) {
        return setText(msg, UIUtils.getColor(R.color.app_zt_black));
    }

    public DialogUtil setText(String msg, int color) {
        textView = new TextView(mContext);
        textView.setMovementMethod(new ScrollingMovementMethod());
        textView.setVerticalScrollBarEnabled(true);
        textView.setTextSize(14);
        textView.setMaxHeight((int) (250 * density));
        textView.setText(msg);
        textView.setTextColor(color);
        return this;
    }

    public DialogUtil setImage(String path, int errorResId, int placeholderResId) {
        imageView = new ImageView(mContext);
        imageView.setVerticalScrollBarEnabled(true);
        imageView.setAdjustViewBounds(true);
        imageView.setMaxHeight((int) (200 * density));
        PicassoUtil.load(mContext, path, errorResId)
                //
                .placeholder(placeholderResId)
                //
                .into(imageView);
        return this;
    }


    public DialogUtil setProgressBar() {
        progressBar = new ProgressBar(mContext, null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setHorizontalScrollBarEnabled(true);
        progressBar.setMax(100);
        return this;
    }

    public DialogUtil updateProgressBar(int progress) {
        progressBar.setProgress(progress);
        return this;
    }

    public DialogUtil setButton_Positive(String text, View.OnClickListener onClik) {
        this.onClik1 = onClik;
        mAlertDialog.setButton(DialogInterface.BUTTON_POSITIVE, text, (dialog, which) -> dialog.dismiss());
        return this;
    }

    public DialogUtil setButton_Negative(String text, View.OnClickListener onClik) {
        this.onClik2 = onClik;
        mAlertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, text, (dialog, which) -> dialog.dismiss());
        return this;
    }

    public DialogUtil setButton_Neutral(String text, View.OnClickListener onClik) {
        this.onClik3 = onClik;
        mAlertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, text, (dialog, which) -> dialog.dismiss());
        return this;
    }

    public void show() {

        if (layout == null) {
            buildView();
        }
        mAlertDialog.show();
        mAlertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(onClik1);
        mAlertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(onClik2);
        mAlertDialog.getButton(DialogInterface.BUTTON_NEUTRAL).setOnClickListener(onClik3);
    }

    public void dismiss() {
        mAlertDialog.dismiss();
    }
}
