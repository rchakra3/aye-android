package com.aye.rohan.locationapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ListEvent extends Activity {

    public static String API_SERVER = "http://45.55.27.229";
    public static String PORT = "9000";
    public static String FIND_EVENT_URI = "/find_event";
    public static JSONArray results;

    // This is the Adapter being used to display the list's data
    SimpleCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        setContentView(R.layout.activity_list_event);


        //retreiving contents from past activity
        String latitude = intent.getStringExtra(MainActivity.LATITUDE_MSG_KEY);
        String longitude = intent.getStringExtra(MainActivity.LONGITUDE_MSG_KEY);
        String find_distance = intent.getStringExtra(MainActivity.FIND_DISTANCE_KEY);

        // Connect to API here and find relevant events to populate List View
        try {
            new HttpSender().execute("" + latitude, "" + longitude, "" + 1000000000).get();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        }

        // For the cursor adapter, specify which columns go into which views
        final ArrayList<String> list = getRows(find_distance);
        //String[] columns = new String[]{"aa", "bb"};
        int[] toViews = {android.R.id.text1}; // The TextView in simple_list_item_1

        final ListView listview = (ListView) findViewById(R.id.list);
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                view.animate().setDuration(2000).alpha(0)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                list.remove(item);
                                adapter.notifyDataSetChanged();
                                view.setAlpha(1);
                            }
                        });
            }

        });

    }

    // Get rows data into ListView
    public static ArrayList<String> getRows(String find_distance) {

        Double find_within_meters = Double.parseDouble(find_distance);
        ArrayList<String> rows_arr = new ArrayList<String>();
        try
        {

            for(int i=0; i<results.length(); i++)
            {
                JSONArray record_arr = (JSONArray) results.get(i);
                String title = (String) record_arr.get(0);
                String desc = (String) record_arr.get(1);
                //Double event_lon = (Double) record_arr.get(2);
                //Double event_lat = (Double) record_arr.get(3);
                Double event_distance = (Double) record_arr.get(4);

                if(event_distance <= find_within_meters)
                {
                    rows_arr.add(""+title+"\nDescription:\n"+desc+
                            "\nEvent is at a distance of : "+event_distance.toString()+
                            " Meters");
                }

            }
        }

        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return rows_arr;
    }


    //Connect to APi to fetch new Events
    public JSONArray findEventsAPI(double latitude, double longitude, double distance)
    {
        JSONArray data = null;

        try {

                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost postRequest = new HttpPost(API_SERVER+":"+PORT+FIND_EVENT_URI);
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("latitude", latitude);
                jsonObj.put("longitude", longitude);
                jsonObj.put("distance", distance);

                StringEntity input = new StringEntity(jsonObj.toString(), HTTP.UTF_8);
                System.out.print(API_SERVER+":"+PORT+FIND_EVENT_URI);
                System.out.print(jsonObj.toString());

                input.setContentType("application/json");
                postRequest.setHeader("Accept", "application/json");
                postRequest.setEntity(input);

                HttpClient client = new DefaultHttpClient();
                HttpResponse response = client.execute(postRequest);

                //Reading the content
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line = null;

                StringBuffer sb = new StringBuffer();

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                String results = sb.toString();
                JSONObject jObject  = new JSONObject(results); // json
                data = jObject.getJSONArray("results"); // get data object

                System.out.print(data);
                System.out.print(response.getStatusLine().getStatusCode());

                httpClient.getConnectionManager().shutdown();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return data;


    }

    private class HttpSender extends AsyncTask<String, Integer, Double> {
        @Override
        protected Double doInBackground(String... params) {
            // TODO Auto-generated method stub
            results = findEventsAPI(Double.parseDouble(params[0]),
                    Double.parseDouble(params[1]), Double.parseDouble(params[2]));
            return null;
        }

        protected void onPostExecute(Double result){

        }

    }


    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }


}


