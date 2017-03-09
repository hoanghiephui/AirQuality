package com.air.quality.ui.widget;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.air.quality.R;
import com.air.quality.utils.ViewUtil;

import java.lang.ref.WeakReference;

import static android.text.format.DateUtils.getRelativeTimeSpanString;

/**
 * Created by Hoang Hiep on 12/05/2016.
 */
public class ShortTimeView extends AppCompatTextView {
  private static final long TICKER_DURATION = 5000L;

  private final Runnable mTicker;
  private boolean mShowAbsoluteTime;
  private long mTime;

  public ShortTimeView(final Context context) {
    this(context, null);
  }

  public ShortTimeView(final Context context, final AttributeSet attrs) {
    this(context, attrs, android.R.attr.textViewStyle);
  }

  public ShortTimeView(final Context context, final AttributeSet attrs, final int defStyle) {
    super(context, attrs, defStyle);
    mTicker = new TickerRunnable(this);
  }

  public void setShowAbsoluteTime(boolean showAbsoluteTime) {
    mShowAbsoluteTime = showAbsoluteTime;
    invalidateTime();
  }

  public void setTime(final long time) {
    mTime = time;
    invalidateTime();
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    post(mTicker);
  }

  @Override
  protected void onDetachedFromWindow() {
    removeCallbacks(mTicker);
    super.onDetachedFromWindow();
  }

  private void invalidateTime() {
    if (mShowAbsoluteTime) {
      setText(ViewUtil.formatSameDayTime(getContext(), mTime));
    } else {
      final long current = System.currentTimeMillis();
      if (Math.abs(current - mTime) > 60 * 1000) {
        setText(getRelativeTimeSpanString(mTime, System.currentTimeMillis(),
          android.text.format.DateUtils.MINUTE_IN_MILLIS, android.text.format.DateUtils.FORMAT_ABBREV_ALL));
      } else {
        setText(R.string.just_now);
      }
    }
  }

  private static class TickerRunnable implements Runnable {

    private final WeakReference<ShortTimeView> mViewRef;

    private TickerRunnable(final ShortTimeView view) {
      mViewRef = new WeakReference<>(view);
    }

    @Override
    public void run() {
      final ShortTimeView view = mViewRef.get();
      if (view == null) return;
      final Handler handler = view.getHandler();
      if (handler == null) return;
      view.invalidateTime();
      final long now = SystemClock.uptimeMillis();
      final long next = now + TICKER_DURATION - now % TICKER_DURATION;
      handler.postAtTime(this, next);
    }
  }
}
