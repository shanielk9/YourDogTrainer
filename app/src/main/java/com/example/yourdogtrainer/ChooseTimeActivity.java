package com.example.yourdogtrainer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.N)
public class ChooseTimeActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener{

    EditText dateET;
    Spinner hourSpinner;
    EditText notesET;
    String currentDateString;
    int dayd;
    int mond;
    int yeard;
    int hourd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_time);

        Button continueBtn = findViewById(R.id.continue_btn);
        dateET = findViewById(R.id.date_edit_text);
        notesET = findViewById(R.id.notes_edit_text);

        continueBtn.setOnClickListener(this);
        dateET.setOnClickListener(this);

        hourSpinner = (Spinner) findViewById(R.id.time_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.ChooseTime, R.layout.my_spinner);
        adapter.setDropDownViewResource(R.layout.my_spinner);
        hourSpinner.setAdapter(adapter);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.continue_btn:
                continueBtnIsClicked();
                break;
            case R.id.date_edit_text:
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        dayd = dayOfMonth;
        mond = month;
        yeard = year;

        dateET.setText(currentDateString);
    }

    private void continueBtnIsClicked() {
        if (checkIfAllInitialize()) {
            Intent i = getIntent();
            String name = i.getStringExtra("name");
            String email = i.getStringExtra("email");
            String phone = i.getStringExtra("phone");
            String dogName = i.getStringExtra("dogName");
            String dogAge = i.getStringExtra("dogAge");
            String dogBreed = i.getStringExtra("dogBreed");

            String h = hourSpinner.getSelectedItem().toString();
            StringBuilder sb = new StringBuilder().append(h.charAt(0)).append(h.charAt(1));
            hourd = Integer.parseInt(sb.toString());


            Intent intent = new Intent(ChooseTimeActivity.this, TrainingDetailsActivity.class);
            intent.putExtra("name",name);
            intent.putExtra("email",email);
            intent.putExtra("phone",phone);
            intent.putExtra("dogName",dogName);
            intent.putExtra("dogAge",dogAge);
            intent.putExtra("dogBreed",dogBreed);
            intent.putExtra("time", hourSpinner.getSelectedItem().toString());
            intent.putExtra("date",currentDateString);
            intent.putExtra("dayd",dayd);
            intent.putExtra("mond",mond);
            intent.putExtra("yeard",yeard);
            intent.putExtra("hourd",hourd);
            if(TextUtils.isEmpty(notesET.getText()))
            {
                intent.putExtra("note"," ");
            }
            else
            {
                intent.putExtra("note",notesET.getText().toString());
            }
            startActivity(intent);
        }
        else{
            showCustomToast(getString(R.string.date_toast));
        }
    }

    private boolean checkIfAllInitialize() {
        boolean isInitialize = true;

        if(TextUtils.isEmpty(dateET.getText()))
        {
            isInitialize = false;
        }

        int selected = hourSpinner.getSelectedItemPosition();
        if (selected == 0)
        {
            isInitialize=false;
        }

        return isInitialize;
    }

    private void showCustomToast(String toastString)
    {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup)findViewById(R.id.custom_toast_layout_id));
        TextView text = (TextView)layout.findViewById(R.id.text_toast);
        text.setText(toastString);
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}
