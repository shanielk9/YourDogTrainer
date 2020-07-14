package com.example.yourdogtrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    TextView callTv;
    TextView contactsTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button scheduleTraining = findViewById(R.id.schedule_trainer_btn);
        scheduleTraining.setOnClickListener(this);

        callTv = findViewById(R.id.call_tv);
        callTv.setOnClickListener(this);

        contactsTv = findViewById(R.id.contacts_tv);
        contactsTv.setOnClickListener(this);

        ImageButton callUs = findViewById(R.id.call_btn);
        callUs.setOnClickListener(this);
        callUs.setOnTouchListener(this);

        ImageButton addToContacts = findViewById(R.id.add_to_contacts_btn);
        addToContacts.setOnClickListener(this);
        addToContacts.setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.schedule_trainer_btn:
                Intent intent = new Intent(MainActivity.this, ScheduleTrainerActivity.class);
                startActivity(intent);
                break;
            case R.id.call_tv:
                performCallClick();
                break;
            case R.id.contacts_tv:
                performContactsClick();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (v.getId()) {
            case R.id.call_btn: {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    callTv.setTextColor(getResources().getColor(R.color.bordeaux));
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    callTv.setTextColor(getResources().getColor(R.color.black));
                    performCallClick();
                    return true;
                }
            }

            case R.id.add_to_contacts_btn: {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    contactsTv.setTextColor(getResources().getColor(R.color.bordeaux));
                    return true;
                }
                else if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    contactsTv.setTextColor(getResources().getColor(R.color.black));
                    performContactsClick();
                    return true;
                }

            }
        }
        return false;
    }

    private void performCallClick()
    {
        Intent intentCall = new Intent(Intent.ACTION_DIAL);
        intentCall.setData(Uri.parse("tel:"+ getString(R.string.telephone_action_call)));
        startActivity(intentCall);

    }


    private void performContactsClick()
    {
        Intent intentAddContact = new Intent(Intent.ACTION_INSERT, ContactsContract.Contacts.CONTENT_URI);
        intentAddContact.setType( ContactsContract.RawContacts.CONTENT_TYPE);
        intentAddContact.putExtra(ContactsContract.Intents.Insert.NAME, getString(R.string.app_name));
        intentAddContact.putExtra(ContactsContract.Intents.Insert.PHONE,getString(R.string.telephone_action_call));
        startActivity(intentAddContact);
    }
}


