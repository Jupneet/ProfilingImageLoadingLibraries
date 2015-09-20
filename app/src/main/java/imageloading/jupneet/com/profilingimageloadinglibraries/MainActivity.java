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

    private final String SCREEN_NAME = "MAIN ACTIVITY";
    private final String IMG_URL = "http://static.comicvine.com/uploads/original/12/120919/3209312-9660445429-hulk_.jpg";
    CustomImageView volley,UL,picasso;
    TextView volleyTime,ULTime,picassoTime;

    private IOnImageChangeListener imageChangeVolley = new IOnImageChangeListener() {
        @Override
        public void imageChangedinView() {
            Log.d(SCREEN_NAME,"Volley Image loaded");
            volleyTime.setText("Volley image loaded");
        }
    };

    private IOnImageChangeListener imageChangePicasso = new IOnImageChangeListener() {
        @Override
        public void imageChangedinView() {
            Log.d(SCREEN_NAME,"Picasso Image loaded");
            picassoTime.setText("Picasso image loaded");
        }
    };

    private IOnImageChangeListener imageChangeUl = new IOnImageChangeListener() {
        @Override
        public void imageChangedinView() {
            Log.d(SCREEN_NAME,"Universal image loader Image loaded");
            ULTime.setText("Univeral image loaded image loaded");
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        volley = (CustomImageView)findViewById(R.id.volleyImage);
        UL = (CustomImageView) findViewById(R.id.uImage);
        picasso = (CustomImageView)findViewById(R.id.picassoImage);

        volleyTime  = (TextView)findViewById(R.id.volleyTime);
        ULTime = (TextView)findViewById(R.id.uTime);
        picassoTime = (TextView)findViewById(R.id.picassoTime);


        volley.setImageChangeListiner(imageChangeVolley);
        UL.setImageChangeListiner(imageChangeUl);
        picasso.setImageChangeListiner(imageChangePicasso);

        getImageWithPicasso(IMG_URL,picasso);
        getImageWithVolley(IMG_URL,volley);
        getImageWithUniversalImageLoader(IMG_URL,UL);

    }

    private void getImageWithUniversalImageLoader(String imageURL,final CustomImageView imageView)
    {
        //your image url

        ImageLoader imageLoader = ImageLoader.getInstance();
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(R.drawable.sorrydog)
                .showImageOnFail(R.drawable.sorrydog)
                .showImageOnLoading(R.drawable.sorrydog).build();

        imageLoader.displayImage(imageURL, imageView, options);
    }

    private void getImageWithPicasso(String imageURL,final CustomImageView imageView)
    {
        Picasso.with(this).load(imageURL).error(R.drawable.sorrydog)
                .into(imageView);
    }

    private void getImageWithVolley(String imageURL, final CustomImageView imageView){

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
