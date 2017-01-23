package data;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.game.dhanraj.playmysong.AppController;
import com.game.dhanraj.playmysong.R;
import com.game.dhanraj.playmysong.songdetails;

import java.util.ArrayList;

import model.parameters;

/**
 * Created by Dhanraj on 12-12-2016.
 */
public class CustomListViewAdapter extends ArrayAdapter<parameters> {

    private LayoutInflater inflater;
    private ArrayList<parameters> data;
    private Activity mContext;
    private int layoutResourceId; //for xml list_row
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomListViewAdapter(Activity context, int resource, ArrayList<parameters> objs) {
        super(context, resource, objs);
        data = objs;
        mContext = context;
        layoutResourceId = resource;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public parameters getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getPosition(parameters item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder viewHolder = null;

        if(row == null){
            inflater = LayoutInflater.from(mContext);
            row = inflater.inflate(layoutResourceId,parent,false);

            viewHolder = new ViewHolder();


            //Get references to our views
            viewHolder.artistimage = (NetworkImageView) row.findViewById(R.id.artistimage1);
            viewHolder.Songname = (TextView) row.findViewById(R.id.artistname);
            viewHolder.Artistname = (TextView) row.findViewById(R.id.songname);


            row.setTag(viewHolder);
        }else{

            viewHolder = (ViewHolder) row.getTag();
        }

        viewHolder.para = data.get(position);

        //we can now display the data

        viewHolder.Songname.setText("Songname : " + viewHolder.para.getSongname() );
        viewHolder.Artistname.setText("Artist : " + viewHolder.para.getArtist() );
        viewHolder.artistimage.setImageUrl(viewHolder.para.getUrl(),imageLoader);

        viewHolder.website = viewHolder.para.getWebsite();


        final ViewHolder finalViewHolder = viewHolder;
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i  = new Intent(mContext,songdetails.class);
                Bundle mbundle = new Bundle();
                mbundle.putSerializable("songObj", finalViewHolder.para);
                i.putExtras(mbundle);
                mContext.startActivity(i);

            }
        });


        return row;
    }

    public class ViewHolder{
        parameters para;
        TextView Songname;
        TextView Artistname;
        String website;
        NetworkImageView artistimage;


    }




}
