package lt.ktu.ks.notes;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpRequest;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Juozas on 2015-10-03.
 */
public class DataAPI {

    private static final String TAG = "DataAPI";
    public static List<Melodija> gautiMelodijas(String RestURL) throws Exception
    {
        List<Melodija> melodijos = new ArrayList<Melodija>();


        String response = WebAPI.gautiMelodijas(RestURL);
        String data = response;
        JSONArray jsonArr = new JSONArray(data);
        List<JSONObject> objects = new ArrayList<>();

        for (int i = 0; i < jsonArr.length(); i++)
        {
            objects.add(jsonArr.getJSONObject(i));
        }
        int a = 5;
        if(response.length() > 1)
        {
            for(int i = 0; i < objects.size(); i++) {
                JSONObject obj = objects.get(i);
                melodijos.add(new Melodija(obj.getInt("id"), obj.getString("pavadinimas"), obj.getString("natos")));
            }
        }
        return melodijos;
    }

    public static void grotiMelodija(int id) throws IOException {
        String response = WebAPI.grotiMelodija(Tools.RestURL, id);
    }


    public static void stabdytiMelodija() throws IOException{
        String res = WebAPI.stabdytiMelodija(Tools.RestURL);
    }

    public static void pridetiMelodija(String name, String notes) throws  IOException{
        String res = WebAPI.pridetiMelodija(Tools.RestURL, name, notes);
    }

    public static void trintiMelodija(String id) throws IOException {
        String res = WebAPI.trintiMelodija(Tools.RestURL, id);
    }
}
