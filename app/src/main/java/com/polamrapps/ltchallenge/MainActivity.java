package com.polamrapps.ltchallenge;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Device> devicesList;
    private RecyclerView recyclerView;
    private RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        devicesList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        layout = (RelativeLayout) findViewById(R.id.mainLayout);
        checkNetworkConnection();
    }

    private void checkNetworkConnection() {
        if(isConnected()) {
            networkCall();
        } else {
            Util.showLog("not internet");
            Snackbar snackbar = Snackbar.make(layout, "No Internet Connection", Snackbar.LENGTH_LONG);
            snackbar.setAction("Retry", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkNetworkConnection();
                }
            });
            snackbar.show();
        }
    }

    private void updateUI() {
        Util.showLog("update UI");
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        if(devicesList.size() == 0) {
            Util.showLog("no data");
        } else {
            Util.showLog("devices : "+devicesList.size());
            MyAdapter adapter = new MyAdapter(devicesList);
            recyclerView.setAdapter(adapter);
        }
    }

    private void networkCall() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Util.showLog("thread start");
                HttpURLConnection urlConnection = null;
                try {
                    URL url = new URL("https://s3.amazonaws.com/harmony-recruit/devices.json");
                    urlConnection = (HttpURLConnection) url.openConnection();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    String data = stringBuilder.toString();
                    Util.showLog("data : "+data);
                    devicesList = parsingData(data);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateUI();
                        }
                    });
                } catch (Exception exception) {
                    Util.showLog("Exception : "+ exception.toString());
                } finally {
                    Util.showLog("finally block");
                    if(urlConnection != null)
                        urlConnection.disconnect();
                }
            }
        }).start();
    }

    /**
     parsing json data
     **/
    private ArrayList<Device> parsingData(String data) {
        ArrayList<Device> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray("devices");
            for(int i=0; i<jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                list.add(new Device(object.getString("deviceType"), object.getString("model"), object.getString("name")));
            }
        } catch (Exception exception) {
            Util.showLog("parsingData Exception : "+exception.toString());
        }
        return list;
    }

    /**
    Network check
     **/
    private boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}
