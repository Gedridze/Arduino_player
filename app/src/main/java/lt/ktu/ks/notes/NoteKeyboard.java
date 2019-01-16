package lt.ktu.ks.notes;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.LinearLayout;

public class NoteKeyboard extends LinearLayout implements View.OnClickListener {
    private Button button1, button2, button3, button4,
            button5, button6, button7, button11, button12, button13, button14,
            button15, button16, button17,button21, button22, button23, button24,
            button25, button26, button27, buttonDelete;

    private SparseArray<String> keyValues = new SparseArray<>();
    private InputConnection inputConnection;

    public NoteKeyboard(Context context) {
        this(context, null, 0);
    }

    public NoteKeyboard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NoteKeyboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.note_keyboard, this, true);
        button1 = (Button) findViewById(R.id.button_1);
        button1.setOnClickListener(this);
        button2 = (Button) findViewById(R.id.button_2);
        button2.setOnClickListener(this);
        button3 = (Button) findViewById(R.id.button_3);
        button3.setOnClickListener(this);
        button4 = (Button) findViewById(R.id.button_4);
        button4.setOnClickListener(this);
        button5 = (Button) findViewById(R.id.button_5);
        button5.setOnClickListener(this);
        button6 = (Button) findViewById(R.id.button_6);
        button6.setOnClickListener(this);
        button7 = (Button) findViewById(R.id.button_7);
        button7.setOnClickListener(this);

        button11 = (Button) findViewById(R.id.button_11);
        button11.setOnClickListener(this);
        button12 = (Button) findViewById(R.id.button_12);
        button12.setOnClickListener(this);
        button13 = (Button) findViewById(R.id.button_13);
        button13.setOnClickListener(this);
        button14 = (Button) findViewById(R.id.button_14);
        button14.setOnClickListener(this);
        button15 = (Button) findViewById(R.id.button_15);
        button15.setOnClickListener(this);
        button16 = (Button) findViewById(R.id.button_16);
        button16.setOnClickListener(this);
        button17 = (Button) findViewById(R.id.button_17);
        button17.setOnClickListener(this);

        button21 = (Button) findViewById(R.id.button_21);
        button21.setOnClickListener(this);
        button22 = (Button) findViewById(R.id.button_22);
        button22.setOnClickListener(this);
        button23 = (Button) findViewById(R.id.button_23);
        button23.setOnClickListener(this);
        button24 = (Button) findViewById(R.id.button_24);
        button24.setOnClickListener(this);
        button25 = (Button) findViewById(R.id.button_25);
        button25.setOnClickListener(this);
        button26 = (Button) findViewById(R.id.button_26);
        button26.setOnClickListener(this);
        button27 = (Button) findViewById(R.id.button_27);
        button27.setOnClickListener(this);

        buttonDelete = (Button) findViewById(R.id.button_delete);
        buttonDelete.setOnClickListener(this);


        keyValues.put(R.id.button_1, "A3 ");
        keyValues.put(R.id.button_2, "B3 ");
        keyValues.put(R.id.button_3, "C3 ");
        keyValues.put(R.id.button_4, "D3 ");
        keyValues.put(R.id.button_5, "E3 ");
        keyValues.put(R.id.button_6, "F3 ");
        keyValues.put(R.id.button_7, "G3 ");

        keyValues.put(R.id.button_11, "A4 ");
        keyValues.put(R.id.button_12, "B4 ");
        keyValues.put(R.id.button_13, "C4 ");
        keyValues.put(R.id.button_14, "D4 ");
        keyValues.put(R.id.button_15, "E4 ");
        keyValues.put(R.id.button_16, "F4 ");
        keyValues.put(R.id.button_17, "G4 ");

        keyValues.put(R.id.button_21, "A5 ");
        keyValues.put(R.id.button_22, "B5 ");
        keyValues.put(R.id.button_23, "C5 ");
        keyValues.put(R.id.button_24, "D5 ");
        keyValues.put(R.id.button_25, "E5 ");
        keyValues.put(R.id.button_26, "F5 ");
        keyValues.put(R.id.button_27, "G5 ");

    }

    @Override
    public void onClick(View view) {
        if (inputConnection == null)
            return;

        if (view.getId() == R.id.button_delete) {
            CharSequence selectedText = inputConnection.getSelectedText(0);

            if (TextUtils.isEmpty(selectedText)) {
                inputConnection.deleteSurroundingText(3, 0);
            } else {
                inputConnection.commitText("", 1);
            }
        } else {
            String value = keyValues.get(view.getId());
            inputConnection.commitText(value, 1);
        }
    }

    public void setInputConnection(InputConnection ic) {
        inputConnection = ic;
    }
}
