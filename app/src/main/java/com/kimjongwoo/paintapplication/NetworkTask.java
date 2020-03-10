package com.kimjongwoo.paintapplication;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONObject;

public class NetworkTask extends AsyncTask<Void, Void, String> {

    private String url;
    private ContentValues values;
    private Context context;
    private static String TAG = "animobi_paint";

    public NetworkTask(String url, ContentValues values, Context context) {
        this.url = url;
        this.values = values;
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String result;
        HttpConnect httpConnect = new HttpConnect();
        result = httpConnect.request(url, values);
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        try {
            JSONObject jsonOutput = JsonUtil.getJSONObjectFrom(result);
            String response = JsonUtil.getStringFrom(jsonOutput, "response");
            JSONObject jsonObject_response = JsonUtil.getJSONObjectFrom(response);
            String action_result = JsonUtil.getStringFrom(jsonObject_response, "action_result");

            if (action_result.equals("success")) {
                String content = JsonUtil.getStringFrom(jsonOutput, "content");
                JSONObject jsonObject_content = JsonUtil.getJSONObjectFrom(content);
                String name = JsonUtil.getStringFrom(jsonObject_content, "name");
                String age = JsonUtil.getStringFrom(jsonObject_content, "age");
                Toast.makeText(context, name + " " + age, Toast.LENGTH_SHORT).show();
            } else if (action_result.equals("failure")) {
                alert();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void alert() {
        AlertDialog.Builder alertDialog_Builder = new AlertDialog.Builder(context);
        alertDialog_Builder.setTitle(R.string.app_name);

        alertDialog_Builder.setMessage(R.string.alert_msg)
                .setCancelable(false)
                .setPositiveButton(R.string.alert_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((PaintActivity) context).finish();
                    }
                });

        AlertDialog alertDialog = alertDialog_Builder.create();
        alertDialog.show();

    }
}
