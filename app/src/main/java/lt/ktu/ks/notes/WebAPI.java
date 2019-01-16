package lt.ktu.ks.notes;

import android.net.http.HttpResponseCache;
import android.provider.Settings;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;

public class WebAPI {
    private static final String TAG = "WebAPI";

    public static String gautiMelodijas(String URL) throws Exception
    {
        HttpClient client = new DefaultHttpClient();
        HttpGet getRequest = new HttpGet(URL + "/android/getAllMelodies");

        HttpResponse response = client.execute(getRequest);

        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        char[] buffer = new char[4096];
        int read_cnt = 0;

        StringBuffer sb = new StringBuffer();

        while((read_cnt = reader.read(buffer, 0, 4096)) != -1)
        {
            sb.append(buffer, 0, read_cnt);
        }

        Log.e(TAG, sb.toString());
        return sb.toString();
    }




    public static String grotiMelodija(String URL, int id) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpGet getRequest = new HttpGet(URL + "/android/playMelode/" + id);
        HttpResponse response = client.execute(getRequest);
        return response.toString();
    }

    public static String stabdytiMelodija(String URL) throws IOException{
        HttpClient client = new DefaultHttpClient();
        HttpGet getRequest = new HttpGet(URL+"/android/stopMelode");
        HttpResponse response = client.execute(getRequest);
        return response.toString();
    }

    public static String pridetiMelodija(String restURL, String title, String text) {
        HttpClient client = new DefaultHttpClient();
        HttpPost postRequest = new HttpPost(restURL+"/android/addMelody");
        try{
            List<NameValuePair> params = new ArrayList<NameValuePair>(2);
            params.add(new BasicNameValuePair("name", title));
            params.add(new BasicNameValuePair("notes", text));
            postRequest.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse res = client.execute(postRequest);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Done";
    }

    public static String trintiMelodija(String restURL, String id) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpGet getRequest = new HttpGet(restURL+"/android/deleteMelody/"+id);
        HttpResponse res = client.execute(getRequest);
        return res.toString();
    }
}
