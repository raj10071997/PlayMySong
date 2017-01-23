package util;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by Dhanraj on 12-12-2016.
 */
public class prefs {
    SharedPreferences preferences;
    public prefs(Activity activity){
        preferences = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    public void setSongname(String songname){
        preferences.edit().putString("songname",songname).commit();
    }

    public String getSongname(){
        return preferences.getString("songname","Love yourself");
    }


}
