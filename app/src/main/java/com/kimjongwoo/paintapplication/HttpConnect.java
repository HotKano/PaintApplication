package com.kimjongwoo.paintapplication;

import android.content.ContentValues;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

class HttpConnect {

    String request(String _url, ContentValues _params) {
        HttpURLConnection urlConnection = null;
        StringBuilder stringBuilder = new StringBuilder();

        if (_params == null) {
            stringBuilder.append("");
        } else {
            boolean endCheck = false;
            String key;
            String value;

            for (Map.Entry<String, Object> parameter : _params.valueSet()) {
                key = parameter.getKey();
                value = parameter.getValue().toString();

                if (endCheck) {
                    stringBuilder.append("&");
                }

                stringBuilder.append(key).append("=").append(value);

                if (!endCheck) {
                    if (_params.size() >= 2)
                        endCheck = true;
                }

            }
        }

        try {
            URL url = new URL(_url);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);

            String strParams = stringBuilder.toString();
            OutputStreamWriter oSteam = new OutputStreamWriter(urlConnection.getOutputStream());
            oSteam.write(strParams);
            oSteam.flush();

            int result_code = urlConnection.getResponseCode();
            if (result_code == HttpURLConnection.HTTP_OK) {
                InputStream is = urlConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuffer result = new StringBuffer();
                while ((line = br.readLine()) != null) {
                    result.append(line);
                    result.append(' ');
                }
                br.close();
                String res = result.toString();
                return res;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;

    }

}
