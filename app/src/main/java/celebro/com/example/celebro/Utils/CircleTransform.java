package celebro.com.example.celebro.Utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

import com.squareup.picasso.Transformation;

/**
 * Created by 502575517 on 3/9/2016.
 */
public class CircleTransform implements Transformation {
    @Override
    public Bitmap transform(Bitmap source) {
        Bitmap output;
        if (source.getWidth() > source.getHeight()) {
            output = Bitmap.createBitmap(source.getHeight(), source.getHeight(), Bitmap.Config.ARGB_8888);
        } else {
            output = Bitmap.createBitmap(source.getWidth(), source.getWidth(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, source.getWidth(), source.getWidth());

        float r = 0;

        if (source.getWidth() > source.getHeight()) {

            r = source.getHeight() / 2;
        } else {
            r = source.getWidth() / 2;
        }

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(r, r, r, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, rect, rect, paint);

        if (output != source) {
            source.recycle();
        }
        return output;
    }

    @Override
    public String key() {
        return null;
    }
}
