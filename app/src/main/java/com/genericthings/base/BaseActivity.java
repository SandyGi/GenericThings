package com.genericthings.base;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.genericthings.helper.UiHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public abstract class BaseActivity<B extends ViewDataBinding> extends AppCompatActivity {
    private B mDataBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this, getLayoutResId());

        init(mDataBinding);
    }

    public abstract int getLayoutResId();

    public abstract void init(B dataBinding);

    protected void gotoActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        if (null != bundle)
            intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void gotoActivityThenKill(Class<?> cls, Bundle bundle) {
        gotoActivity(cls, bundle);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    /**
     * On event.81/
     *
     * @param <T>    the type parameter
     * @param output the output
     */
    @Subscribe
    public <T> void onEvent(T output) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        UiHelper.hideKeyboard(this);
    }
}
