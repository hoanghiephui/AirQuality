package com.air.quality.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by hoanghiep on 3/1/17.
 */

public abstract class BaseFragment extends Fragment {
  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    injectDependences();
    attachView();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(getFragmentLayout(), container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);
    bindEventHandlers(view);
  }

  //Request dependencies. Can be used on a constructor, a field, or a method
  protected abstract void injectDependences();

  //attach view on the presenter
  protected abstract void attachView();

  protected abstract int getFragmentLayout();

  protected abstract void bindEventHandlers(View view);
}
