package com.game.dhanraj.playmysong;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import model.parameters;

public class songdetails extends AppCompatActivity {

    private parameters Parameters;
    private TextView name ;
    private TextView artist;
    private TextView Listeners;
    private Button play;
    private NetworkImageView artistimage;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songdetails);

        Parameters = (parameters) getIntent().getSerializableExtra("songObj");

        name = (TextView) findViewById(R.id.name);
        artist = (TextView)findViewById(R.id.artist);
        Listeners = (TextView) findViewById(R.id.listeners);

        play = (Button) findViewById(R.id.play);

        artistimage = (NetworkImageView) findViewById(R.id.image2);


        name.setText("Name : "+Parameters.getSongname());
        artist.setText("Artist : "+Parameters.getArtist());
        Listeners.setText("Listeners : "+Parameters.getListeners());
        artistimage.setImageUrl(Parameters.getUrl(),imageLoader);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse(Parameters.getWebsite()));
                startActivity(browse);
            }
        });

        Log.v("dhanraj :" , Parameters.getArtist());

    }
}
