package imageloading.jupneet.com.profilingimageloadinglibraries;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by jupneet on 21/09/15.
 */
public class CustomImageView extends ImageView {

    private IOnImageChangeListener onImageChangeListiner;


    public CustomImageView(Context context) {
        super(context);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setImageChangeListiner(
            IOnImageChangeListener onImageChangeListiner) {
        this.onImageChangeListiner = onImageChangeListiner;
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        if (onImageChangeListiner != null)
            onImageChangeListiner.imageChangedinView();
    }

    @Override
    public void setBackgroundResource(int resid) {
        super.setBackgroundResource(resid);
        if (onImageChangeListiner != null)
            onImageChangeListiner.imageChangedinView();
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        if (onImageChangeListiner != null)
            onImageChangeListiner.imageChangedinView();
    }

    @Override
    public void setBackgroundDrawable(Drawable background) {
        super.setBackgroundDrawable(background);
        if (onImageChangeListiner != null)
            onImageChangeListiner.imageChangedinView();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        if (onImageChangeListiner != null)
            onImageChangeListiner.imageChangedinView();
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        if (onImageChangeListiner != null)
            onImageChangeListiner.imageChangedinView();
    }
}
