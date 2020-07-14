package com.example.yourdogtrainer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.actions.NoteIntents;

public class TrainingDetailsActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    String name;
    String email;
    String phone;
    String dogName;
    String dogAge;
    String dogBreed;
    String hour;
    String date;
    String notes;
    int day_date;
    int mon_date;
    int year_date;
    int hour_date;
    String trainingDetails;
    TextView detailsTV;
    TextView emailTv;
    TextView alarmTv;
    TextView calendarTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_details);

        getExtra();

        Button continueBtn = findViewById(R.id.finish_btn);
        continueBtn.setOnClickListener(this);

        calendarTv = findViewById(R.id.calendar_tv);
        calendarTv.setOnClickListener(this);

        emailTv = findViewById(R.id.email_us_tv);
        emailTv.setOnClickListener(this);

        alarmTv = findViewById(R.id.alarm_tv);
        alarmTv.setOnClickListener(this);

        ImageButton sendMailBtn = findViewById(R.id.email_us_btn);
        sendMailBtn.setOnClickListener(this);
        sendMailBtn.setOnTouchListener(this);

        ImageButton calendarBtn = findViewById(R.id.calendar_btn);
        calendarBtn.setOnClickListener(this);
        calendarBtn.setOnTouchListener(this);

        ImageButton alarmBtn = findViewById(R.id.alarm_btn);
        alarmBtn.setOnClickListener(this);
        alarmBtn.setOnTouchListener(this);

        detailsTV = findViewById(R.id.details_text_view);

        initializeExtra();

    }

    private void initializeExtra() {
        StringBuilder detailsString = new StringBuilder()
                .append(getString(R.string.name)).append(": ").append(name).append("\n")
                .append(getString(R.string.email)).append(": ").append(email).append("\n")
                .append(getString(R.string.phone)).append(": ").append(phone).append("\n")
                .append(getString(R.string.dog_name)).append(": ").append(dogName).append("\n")
                .append(getString(R.string.age)).append(": ").append(dogAge).append("\n")
                .append(getString(R.string.breed)).append(": ").append(dogBreed).append("\n")
                .append(getString(R.string.date_)).append(": ").append(date).append("\n")
                .append(getString(R.string.hour_)).append(": ").append(hour).append("\n");
        trainingDetails = detailsString.toString();
        detailsTV.setText(trainingDetails);
    }

    private void getExtra() {
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        phone = intent.getStringExtra("phone");
        dogName = intent.getStringExtra("dogName");
        dogAge = intent.getStringExtra("dogAge");
        dogBreed = intent.getStringExtra("dogBreed");
        hour = intent.getStringExtra("time");
        date = intent.getStringExtra("date");
        notes = intent.getStringExtra("note");
        day_date = intent.getIntExtra("dayd", 1);
        mon_date = intent.getIntExtra("mond", 0);
        year_date = intent.getIntExtra("yeard", 2000);
        hour_date = intent.getIntExtra("hourd", 00);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.finish_btn:
                showCustomToast(getString(R.string.finish_msg));
                Intent intentFinish = new Intent(getApplicationContext(), MainActivity.class);
                intentFinish.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentFinish);
                break;
            case R.id.email_us_tv:
               performEmailClick();
                break;
            case R.id.calendar_tv:
                performCalendarClick();
                break;
            case R.id.alarm_tv:
                performAlarmClick();
                break;
        }
    }

    private void showCustomToast(String toastString) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast_layout_id));
        TextView text = (TextView) layout.findViewById(R.id.text_toast);
        text.setText(toastString);
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.email_us_btn: {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    emailTv.setTextColor(getResources().getColor(R.color.bordeaux));
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    emailTv.setTextColor(getResources().getColor(R.color.black));
                    performEmailClick();
                    return true;
                }
            }

            case R.id.calendar_btn: {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    calendarTv.setTextColor(getResources().getColor(R.color.bordeaux));
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    calendarTv.setTextColor(getResources().getColor(R.color.black));
                    performCalendarClick();
                    return true;
                }

            }

            case R.id.alarm_btn: {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    alarmTv.setTextColor(getResources().getColor(R.color.bordeaux));
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    alarmTv.setTextColor(getResources().getColor(R.color.black));
                    performAlarmClick();
                    return true;
                }


            }

        }
        return false;
    }


    private void performEmailClick() {
        StringBuilder sb = new StringBuilder().append(getString(R.string.mail_body)).append(notes)
                .append("\n").append(getString(R.string.mail_body_thankyou)).append(name).append("&").append(dogName);
        String address = getString(R.string.mail_addresse);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));

        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{address});
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mail_subject));
        intent.putExtra(Intent.EXTRA_TEXT, sb.toString());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void performCalendarClick()
    {
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(year_date, mon_date, day_date, hour_date, 00);
        Calendar endTime = Calendar.getInstance();
        endTime.set(year_date, mon_date, day_date, hour_date + 2, 00);
        Intent intentCal = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.TITLE, getString(R.string.cal_title))
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis());
        if (intentCal.resolveActivity(getPackageManager()) != null) {
            startActivity(intentCal);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void performAlarmClick()
    {
        Calendar beginTimeAlarm = Calendar.getInstance();
        beginTimeAlarm.set(year_date, mon_date, day_date, hour_date, 00);
        Intent intentAlarm = new Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_MESSAGE, getString(R.string.alarm_msg))
                .putExtra(AlarmClock.EXTRA_HOUR, hour_date)
                .putExtra(AlarmClock.EXTRA_MINUTES, 00);
        if (intentAlarm.resolveActivity(getPackageManager()) != null) {
            startActivity(intentAlarm);
        }
    }
}
