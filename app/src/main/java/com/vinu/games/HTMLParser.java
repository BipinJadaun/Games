package com.vinu.games;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTMLParser extends AsyncTask<String, Void, String> {

    @Override
    public String doInBackground(String... urls){
        StringBuilder result = new StringBuilder();
        try {
            URL pageUrl = new URL(urls[0]);
            HttpURLConnection conn = (HttpURLConnection) pageUrl.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line ;
            while((line = reader.readLine()) != null){
                result.append(line);
            }
            reader.close();
            conn.disconnect();
            return result.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
