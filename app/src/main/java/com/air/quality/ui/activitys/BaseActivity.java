package com.air.quality.ui.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.air.quality.RxBus;
import com.akhgupta.easylocation.EasyLocationAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hoanghiep on 2/28/17.
 */

public abstract class BaseActivity extends EasyLocationAppCompatActivity {
  private Unbinder unbinder;
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    injectDependences();
    attachView();
    setContentView(getLayout());
    unbinder = ButterKnife.bind(this);
    initContent(savedInstanceState);
  }

  //Request dependencies. Can be used on a constructor, a field, or a method
  protected abstract void injectDependences();

  //attach view on the presenter
  protected abstract void attachView();

  protected abstract int getLayout();

  protected abstract void initContent(Bundle bundle);

  @Override
  protected void onDestroy() {
    unbinder.unbind();
    RxBus.getInstance().unSubscribe(this);
    super.onDestroy();
  }
}
