package com.vinu.games;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
    @Override
    protected Bitmap doInBackground(String... urls) {
        try {
            URL imageUrl = new URL(urls[0]);
            HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
            InputStream inputStream = conn.getInputStream();
            Bitmap image = BitmapFactory.decodeStream(inputStream);
            return image;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
