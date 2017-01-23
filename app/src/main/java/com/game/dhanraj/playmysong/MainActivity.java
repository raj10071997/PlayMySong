package com.game.dhanraj.playmysong;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import data.CustomListViewAdapter;
import model.parameters;
import util.prefs;

public class MainActivity extends AppCompatActivity {
    private CustomListViewAdapter adapter;
    private ArrayList<parameters> para = new ArrayList<>();
    private String urlleft = "http://ws.audioscrobbler.com/2.0/?method=track.search&track=";
    private String urlright = "&api_key=91a0bd0b2a7455d0a744cbc66dcba001&format=json";
    private ListView listView;
    private TextView tracksearch;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tracksearch = (TextView) findViewById(R.id.tracksearched);

        listView = (ListView) findViewById(R.id.list);
        adapter = new CustomListViewAdapter(MainActivity.this,R.layout.list_row,para);
        listView.setAdapter(adapter);

        prefs Prefs = new prefs(MainActivity.this);
        String song  = Prefs.getSongname();

        tracksearch.setText("TrackSearched :" + song);

        showPara(song);


    }

    private void getTracks(String traname){

        para.clear();

        String finalurl = urlleft + traname + urlright;
        JsonObjectRequest trackrequest = new JsonObjectRequest(Request.Method.GET,
                finalurl, (JSONObject) null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject results = response.getJSONObject("results");
                    JSONObject trackfound = results.getJSONObject("trackmatches");
                    JSONArray track = trackfound.getJSONArray("track");

                    for(int i=0;i<track.length();i++){
                        JSONObject jsonObject = track.getJSONObject(i);

                        String nameText = jsonObject.getString("name");
                        String artistText = jsonObject.getString("artist");
                        String websiteurl = jsonObject.getString("url");
                        String  listeners = jsonObject.getString("listeners");

                        JSONArray imageArray = jsonObject.getJSONArray("image");

                        JSONObject largeimage = imageArray.getJSONObject(1);

                        String imageurl = largeimage.getString("#text");

                        parameters paramet = new parameters();
                        paramet.setArtist(artistText);
                        paramet.setListeners(listeners);
                        paramet.setWebsite(websiteurl);
                        paramet.setUrl(imageurl);
                        paramet.setSongname(nameText);

                        para.add(paramet);


                        Log.v("artist : ",artistText);
                    }

                        adapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(trackrequest);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.action_settings){
            showInputDialog();
        }

        return super.onOptionsItemSelected(item);
    }


    private void showInputDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Change song");

        final EditText songInput = new EditText(MainActivity.this);
        songInput.setInputType(InputType.TYPE_CLASS_TEXT);
        songInput.setHint("Hint :Love yourself");
        builder.setView(songInput);


        builder.setPositiveButton("search", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                prefs songPreference = new prefs(MainActivity.this);
                songPreference.setSongname(songInput.getText().toString());


                String newsong = songPreference.getSongname();
                tracksearch.setText("TrackSearched: " + newsong);

                showPara(newsong);
            }
        });
        builder.show();

    }

private void showPara(String newsong){
    getTracks(newsong);
}


}
