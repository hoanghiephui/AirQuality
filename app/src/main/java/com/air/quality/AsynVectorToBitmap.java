package com.air.quality;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import static com.air.quality.utils.AirQualityUtils.valueQuality;

/**
 * Created by hoanghiep on 3/5/17.
 */

public class AsynVectorToBitmap extends AsyncTask<Integer, Void, BitmapDescriptor> {
  private static final String TAG = AsynVectorToBitmap.class.getSimpleName();
  private Context mContext;
  private IData data;

  public AsynVectorToBitmap(IData data) {
    this.data = data;
  }

  @Override
  protected BitmapDescriptor doInBackground(Integer... params) {
    int image = 0;
    switch (valueQuality(params[0])) {
      case 0:
        image = R.drawable.ic_marker_good_v;
        break;
      case 1:
        image = R.drawable.ic_marker_moderate_v;
        break;
      case 2:
        image = R.drawable.ic_marker_sensitive_v;
        break;
      case 3:
        image = R.drawable.ic_marker_unhealthy_;
        break;
      case 4:
        image = R.drawable.ic_marker_very_unhealthy_;
        break;
      case 5:
        image = R.drawable.ic_marker_hazardous_v;
        break;
      default:
        break;
    }
    Drawable vectorDrawable = ResourcesCompat.getDrawable(data.getContext().getResources(), image, null);
    Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
      vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);
    vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
    //DrawableCompat.setTint(vectorDrawable, color);
    vectorDrawable.draw(canvas);
    return BitmapDescriptorFactory.fromBitmap(bitmap);
  }

  @Override
  protected void onPostExecute(BitmapDescriptor bitmapDescriptor) {
    Log.d(TAG, "onPostExecute: " + bitmapDescriptor);
    data.getBitmap(bitmapDescriptor);
  }

  public interface IData {
    BitmapDescriptor getBitmap(BitmapDescriptor bitmapDescriptor);
    Context getContext();
  }
}
