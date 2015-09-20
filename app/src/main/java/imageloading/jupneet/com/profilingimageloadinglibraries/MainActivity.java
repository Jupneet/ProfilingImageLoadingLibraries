package imageloading.jupneet.com.profilingimageloadinglibraries;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    CustomImageView volley, UL, picasso;
    TextView volleyTime, ULTime, picassoTime;
    boolean volleyCache,ulCache;
    long volleyStartTime, volleyEndTime, picassoStartTime, picassoEndTime, UlStartTime, UlEndTime;
    private final String SCREEN_NAME = "MAIN ACTIVITY";
    private final String IMG_URL =
            "https://yt3.ggpht.com/-DaDQYP98Hpc/AAAAAAAAAAI/AAAAAAAAAAA/DaBLSpgxXXc/s900-c-k-no/photo.jpg";


    private IOnImageChangeListener imageChangeVolley = new IOnImageChangeListener() {
        @Override
        public void imageChangedinView() {
            Log.d(SCREEN_NAME, "Volley Image loaded");

            volleyEndTime = System.currentTimeMillis();
            if (volleyCache)
                volleyTime.setText("Volley image loaded in " + (volleyEndTime - volleyStartTime) + " milliseconds"
                        + " from cache ");
            else
                volleyTime.setText("Volley image loaded in " + (volleyEndTime - volleyStartTime) + " milliseconds"
                        + " from web ");

        }
    };

    private IOnImageChangeListener imageChangePicasso = new IOnImageChangeListener() {
        @Override
        public void imageChangedinView() {
            Log.d(SCREEN_NAME, "Picasso Image loaded");
            picassoEndTime = System.currentTimeMillis();
            picassoTime.setText("Picasso image loaded in " + (picassoEndTime - picassoStartTime) + " milliseconds");
        }
    };

    private IOnImageChangeListener imageChangeUl = new IOnImageChangeListener() {
        @Override
        public void imageChangedinView() {
            Log.d(SCREEN_NAME, "Universal image loader Image loaded");
            UlEndTime = System.currentTimeMillis();
            if (ulCache)
                ULTime.setText("Universal image loader image loaded in " + (UlEndTime - UlStartTime) + " milliseconds"
                        + " from cache ");
            else
                ULTime.setText("Universal image loader image loaded in " + (UlEndTime - UlStartTime) + " milliseconds"
                        + " from web ");

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        volley = (CustomImageView) findViewById(R.id.volleyImage);
        UL = (CustomImageView) findViewById(R.id.uImage);
        picasso = (CustomImageView) findViewById(R.id.picassoImage);

        volleyTime = (TextView) findViewById(R.id.volleyTime);
        ULTime = (TextView) findViewById(R.id.uTime);
        picassoTime = (TextView) findViewById(R.id.picassoTime);

        volley.setImageChangeListiner(imageChangeVolley);
        UL.setImageChangeListiner(imageChangeUl);
        picasso.setImageChangeListiner(imageChangePicasso);

        picassoStartTime = System.currentTimeMillis();
        getImageWithPicasso(IMG_URL, picasso);
        volleyStartTime = System.currentTimeMillis();
        getImageWithVolley(IMG_URL, volley);
        UlStartTime = System.currentTimeMillis();
        getImageWithUniversalImageLoader(IMG_URL, UL);


    }

    private void getImageWithUniversalImageLoader(String imageURL, final CustomImageView imageView) {
        ulCache = false;
        ImageLoader imageLoader = ImageLoader.getInstance();
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(R.drawable.sorrydog)
                .showImageOnFail(R.drawable.sorrydog)
                .showImageOnLoading(R.drawable.sorrydog).build();

        if(imageLoader.getMemoryCache().get(IMG_URL)!=null ||imageLoader.getDiskCache().get(IMG_URL)!=null )
            ulCache = true;

        imageLoader.displayImage(imageURL, imageView, options);
    }

    private void getImageWithPicasso(String imageURL, final CustomImageView imageView) {

        Picasso.with(this).setLoggingEnabled(true);
        Picasso.with(this).load(imageURL).error(R.drawable.sorrydog)
                .into(imageView);

    }

    private void getImageWithVolley(String imageURL, final CustomImageView imageView) {
        volleyCache = false;
        ImageRequest request = new ImageRequest(imageURL,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        imageView.setImageBitmap(bitmap);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        imageView.setImageResource(R.drawable.sorrydog);
                    }
                });
        if (VolleySingleton.getInstance(this).getRequestQueue().getCache().get(IMG_URL) != null)
            volleyCache = true;
        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
