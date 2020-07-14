package com.example.yourdogtrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ScheduleTrainerActivity extends AppCompatActivity implements View.OnClickListener {

    TextView name;
    TextView phone;
    TextView email;
    TextView age;
    TextView breed;
    TextView dogName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_trainer);

        name = findViewById(R.id.name_edit_text);
        phone = findViewById(R.id.phone_edit_text);
        email = findViewById(R.id.email_edit_text);
        age = findViewById(R.id.age_edit_text);
        breed = findViewById(R.id.breed_edit_text);
        dogName = findViewById(R.id.dogsname_edit_text);

        Button continueBtn = findViewById(R.id.continue_btn);
        continueBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.continue_btn:
                continueBtnIsClicked();
                break;
        }
    }

    private void continueBtnIsClicked() {
        if (checkIfAllInitialize()) {
            Intent intent = new Intent(ScheduleTrainerActivity.this, DogDescriptionActivity.class);
            intent.putExtra("name",name.getText().toString());
            intent.putExtra("email",email.getText().toString());
            intent.putExtra("phone",phone.getText().toString());
            intent.putExtra("dogName",dogName.getText().toString());
            intent.putExtra("dogAge",age.getText().toString());
            intent.putExtra("dogBreed",breed.getText().toString());
            startActivity(intent);
        }
        else{
            showCustomToast(getString( R.string.not_all_fields_init));
        }
    }


    private boolean checkIfAllInitialize() {
        boolean isInitialize = true;

        if(TextUtils.isEmpty(name.getText()))
        {
            isInitialize = false;
        }

        if(TextUtils.isEmpty(email.getText()))
        {
            isInitialize = false;
        }

        if(TextUtils.isEmpty(phone.getText()))
        {
            isInitialize = false;
        }

        if(TextUtils.isEmpty(dogName.getText()))
        {
            isInitialize = false;
        }

        if(TextUtils.isEmpty(age.getText()))
        {
            isInitialize = false;
        }

        if(TextUtils.isEmpty(breed.getText()))
        {
            isInitialize = false;
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

