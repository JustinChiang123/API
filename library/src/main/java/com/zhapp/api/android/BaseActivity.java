package com.zhapp.api.android;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.zhapp.api.R;
import com.zhapp.api.android.network.NetAction;
import com.zhapp.api.android.widget.dialog.DialogUtil;
import com.zhapp.api.java.JavaUtil;

import java.util.ArrayList;

/**
 * <pre>
 * Describe: BaseActivity。
 *
 * Author: <a href="mailto:Justin_Chiang@foxmail.com">Justin_Chiang<a/>
 * Date: 2017-02-11
 * Time: 13:01
 * <pre/>
 */
public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";

    private static Context context;
    private static BaseActivity activity;

    @SuppressWarnings("unchecked")
    public <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }

    protected abstract String getCurrentTag();

    /**
     * <pre>
     * 当前activity的layout资源id。
     * </pre>
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * <pre>
     * 初始化UI。
     * </pre>
     */
    protected abstract boolean initView();

    /**
     * <pre>
     * 初始化DATA。
     * </pre>
     */
    protected abstract void initData();

    /**
     * <pre>
     * 销毁数据。
     * </pre>
     */
    protected abstract void destroy();

    /**
     * <pre>
     * onResume。
     * </pre>
     */
    protected abstract void resume();

    /**
     * <pre>
     * onPause。
     * </pre>
     */
    protected abstract void pause();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutId = getLayoutId();
        if (layoutId != 0) {
            setContentView(layoutId);
        }

        context = getApplicationContext();
        activity = this;

        CollectorUtil.addActivity(this);

        if (initView()) {
            initData();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // 点击返回图标事件
            case android.R.id.home:
                finish(RESULT_CANCELED, getIntent());
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * 初始化 Toolbar
     */
    public void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
    }

    private ArrayList<DialogUtil> dialoglist;

    public void showPushDialog(String msg) {
        if (dialoglist == null) {
            dialoglist = new ArrayList<>();
        }
        DialogUtil dialogBuilder = DialogUtil.init(this).create();
        dialogBuilder
                //
                .setTitle("温馨提示")
                //
                .setCancelable(false)
                //
                .setCanceledOnTouchOutside(false)
                //
                .setText(msg, UIUtil.getColor(R.color.app_zt_black))
                //
                .setButton_Negative("确定", v -> dialogBuilder.dismiss())
                //
                .show()
        //
        ;
        dialoglist.add(dialogBuilder);
    }

    private ArrayList<Toast> toasts;


    public void MToast(String content) {
        Toast.makeText(UIUtil.getContext(), content, Toast.LENGTH_SHORT).show();
    }

    public void Toast(String content) {
        if (toasts == null) {
            toasts = new ArrayList<>();
        }
        Toast toast = Toast.makeText(UIUtil.getContext(), content, Toast.LENGTH_SHORT);
        toasts.add(toast);
        toast.show();
    }

    protected void Toast(int stringId) {
        if (toasts == null) {
            toasts = new ArrayList<>();
        }
        Toast toast = Toast.makeText(UIUtil.getContext(), UIUtil.getString(stringId), Toast.LENGTH_SHORT);
        toasts.add(toast);
        toast.show();
    }

    protected ProgressDialog dialog;

    public void showLoading() {
        showLoading("请求网络中...");
    }


    public void showLoading(String msg) {
        if (dialog != null && dialog.isShowing())
            return;
        dialog = new ProgressDialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(msg);
        dialog.show();
    }

    public void dismissLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void dismissLoading(long time) {
        new Thread(() -> {
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                runOnUiThread(() -> dismissLoading());
            }
        }).start();
    }

    public static Context getContext() {
        return context;
    }

    public static BaseActivity getActivity() {
        return activity;
    }

    @Override
    protected void onResume() {
        context = getApplicationContext();
        activity = this;
        resume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        pause();
        super.onPause();
    }

    /**
     * @param resultCode
     * @param data
     *
     * @return void 返回类型
     *
     * @Description: 专用于activity之间通信处理的finish()
     * @author Justin_Chiang
     * @mail Justin_Chiang@foxmail.com
     */
    public void finish(int resultCode, Intent data) {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        if (data != null) {
            setResult(resultCode, data);
        }
        super.finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissLoading();
        CollectorUtil.removeActivity(this);
        if (!JavaUtil.isNull(toasts)) {
            for (Toast toast : toasts) {
                if (toast != null) {
                    toast.cancel();
                }
            }
        }
        if (!JavaUtil.isNull(dialoglist)) {
            for (DialogUtil niftyDialogBuilder : dialoglist) {
                if (niftyDialogBuilder != null) {
                    niftyDialogBuilder.dismiss();
                }
            }
        }
        NetAction.cancel(getCurrentTag());
        destroy();
    }
}
