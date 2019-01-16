package lt.ktu.ks.notes;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class NaujaMelodijaActivity extends AppCompatActivity {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nauja_melodija);

        EditText editText = (EditText) findViewById(R.id.editText);
        NoteKeyboard keyboard = (NoteKeyboard) findViewById(R.id.keyboard);
        editText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        editText.setTextIsSelectable(false);
        editText.setShowSoftInputOnFocus(false);

        InputConnection ic = editText.onCreateInputConnection(new EditorInfo());
        keyboard.setInputConnection(ic);
    }

    public void addNewMelody(View view) {
        EditText name = (EditText)findViewById(R.id.melodyName);
        EditText notes = (EditText)findViewById(R.id.editText);
        if(name.length() == 0 || notes.length() == 0){
            Toast.makeText(this, "Tušti laukai negalimi", Toast.LENGTH_SHORT).show();
        }
        else{
            String params[] = {name.getText().toString(), notes.getText().toString()};
                new addMelody().execute(params);
        }
    }

    private class addMelody extends AsyncTask<String, Void, Void>{

        ProgressDialog actionProgressDialog = new ProgressDialog(NaujaMelodijaActivity.this);

        @Override
        protected void onPreExecute()
        {
            actionProgressDialog.setMessage("Pridedama...");
            actionProgressDialog.setCancelable(false);
            actionProgressDialog.show();

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... strings) {
            Integer noteCount;
            String parts[] = strings[1].split(" ");
            noteCount = parts.length;
            @SuppressLint("DefaultLocale") String noteString = String.format("%d %s", noteCount, strings[1].substring(0, strings[1].length()-1));
            for(int i = 0; i < noteCount; i++){
                noteString += " 4";
            }
            noteString += ",";
            try {
                DataAPI.pridetiMelodija(strings[0], noteString);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }



        protected void onProgressUpdate(Void... progress) {

        }

        protected void onPostExecute(Void returnType)
        {
            actionProgressDialog.cancel();
            Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(myIntent);

        }
    }
}
