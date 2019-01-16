package lt.ktu.ks.notes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener
{
    private static  final  String TAG = "MainActivity";
    ArrayList<HashMap<String, String>> UzrasaiDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gautiMelodijas();
    }

    private void gautiMelodijas()
    {
        new gautiMelodijasTask().execute(Tools.RestURL, null, null);
    }

    private void rodytiMelodijas(List<Melodija> uzrasai)
    {
        UzrasaiDataList = new ArrayList<HashMap<String, String>>();

        for(int i=0; i<uzrasai.size(); i++)
        {
            Melodija u = uzrasai.get(i);

            HashMap<String, String> UzrasasDataMap =  new HashMap<String, String>();

            UzrasasDataMap.put("id", String.valueOf(u.ID));
            UzrasasDataMap.put("pavadinimas", u.Pavadinimas);
            UzrasasDataMap.put("trukme", String.valueOf(u.Trukme));

            UzrasaiDataList.add(UzrasasDataMap);
        }

        ListView mlv = (ListView)findViewById(R.id.uzrasaiListView);

        SimpleAdapter SimpleMiestaiAdapter = new SimpleAdapter(this, UzrasaiDataList, R.layout.uzrasai_list_row,
                new String[] {"id", "pavadinimas", "trukme"},
                new int[] {R.id.idPlaceholder, R.id.pavadinimasTextView, R.id.trukmeTextView});

        mlv.setAdapter(SimpleMiestaiAdapter);
        mlv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int pos, long id)
    {
        String UzrasoID = UzrasaiDataList.get(pos).get("id");
        Float trukme = Float.parseFloat(UzrasaiDataList.get(pos).get("trukme"));
        new grotiMelodija().execute(Integer.parseInt(UzrasoID), Math.round(trukme*1000));

    }



    public void naujaMelodija(View view) {
        Intent myIntent = new Intent(this, NaujaMelodijaActivity.class);
        startActivity(myIntent);
    }




    private class grotiMelodija extends AsyncTask<Integer, Void, Integer> {
        private class stabdytiMelodija extends AsyncTask<Void, Void, Void>{

            @Override
            protected void onPreExecute()
            {
                actionProgressDialog.setMessage("Stabdoma..");
                actionProgressDialog.setCancelable(false);
                actionProgressDialog.show();

                super.onPreExecute();
            }
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    DataAPI.stabdytiMelodija();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void v)
            {
                actionProgressDialog.cancel();

            }
        }

        ProgressDialog actionProgressDialog = new ProgressDialog(MainActivity.this);
            @Override
            protected void onPreExecute()
            {
                actionProgressDialog.setMessage("Grojama...");
                actionProgressDialog.setCancelable(true);
                actionProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        cancel(true);
                    }
                });
                actionProgressDialog.show();

                super.onPreExecute();
            }

        @Override
        protected Integer doInBackground(Integer... integers) {
                long time = System.currentTimeMillis();
            try {
                DataAPI.grotiMelodija(integers[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            while((System.currentTimeMillis() - time) != integers[1]){
                if(isCancelled()){
                    break;
                }
            }

            return 1;
        }
        protected void onProgressUpdate(Void... progress) {
        }

        protected void onPostExecute(Integer returnType)
        {
                actionProgressDialog.cancel();

        }
        @Override
        protected void onCancelled()
        {
            int a = 5;
            //called on ui thread
            if (this.actionProgressDialog != null) {
                this.actionProgressDialog.dismiss();
            }
            new stabdytiMelodija().execute();
        }

    }


    private class gautiMelodijasTask extends AsyncTask<String, Void, List<Melodija>>
    {
        ProgressDialog actionProgressDialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute()
        {
            actionProgressDialog.setMessage("Gaunami užrašai...");
            actionProgressDialog.show();
            actionProgressDialog.setCancelable(true);
            super.onPreExecute();
        }

        protected List<Melodija> doInBackground(String... str_param)
        {
            String RestURL = str_param[0];
            List<Melodija> uzrasai = new ArrayList<Melodija>();
            try
            {
                uzrasai = DataAPI.gautiMelodijas(RestURL);
            }
            catch(Exception ex)
            {
                Log.e(TAG, ex.toString());
            }

            return uzrasai;
        }

        protected void onProgressUpdate(Void... progress) {}

        protected void onPostExecute(List<Melodija> result)
        {
            actionProgressDialog.cancel();

            rodytiMelodijas(result);
        }
    }
}
