package com.carbontech.downloadwebcontent;

import android.arch.core.util.Function;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.Certificate;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

public class MainActivity extends AppCompatActivity {

    String image_url = "https://upload.wikimedia.org/wikipedia/en/c/c2/Peter_Griffin.png";
    ImageView image_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image_view = (ImageView) findViewById(R.id.image_view);
    }

    public void downloadImage(View view){

        ImageDowloader task = new ImageDowloader();

        try {
            Bitmap myImage = task.execute(image_url).get();
            image_view.setImageBitmap(myImage);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public class ImageDowloader extends AsyncTask<String, Void, Bitmap>{
        
        @Override
        protected Bitmap doInBackground(String... urls) {

            try {
                URL url = new URL(urls[0]);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.connect();

                InputStream inputStream = connection.getInputStream();

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                return bitmap;
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
