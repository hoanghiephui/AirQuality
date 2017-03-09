package com.air.quality.mvp.presenters;

import com.air.quality.mvp.view.BaseView;

public interface BasePresenter<T extends BaseView> {

  void attachView(T view);

  void subscribe();

  void unsubscribe();
}
