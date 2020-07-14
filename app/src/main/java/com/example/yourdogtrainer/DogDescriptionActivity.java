package com.example.yourdogtrainer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class DogDescriptionActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    final int CAMERA_REQUEST = 1;
    final int VIDEO_REQUEST = 2;

    ImageView imageView;
    VideoView videoView;
    ImageButton cameraBtn;
    ImageButton videoBtn;
    Button continueBtn;
    Button playBtn;
    TextView playVideoTxt;
    LinearLayout photoLayout;
    LinearLayout videoLayout;
    TextView photoTv;
    TextView videoTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_description);


        imageView = findViewById(R.id.result_image);
        videoView = findViewById(R.id.result_video);

        playBtn =  findViewById(R.id.play_btn);
        playBtn.setOnClickListener(this);

        photoTv = findViewById(R.id.photo_tv);
        photoTv.setOnClickListener(this);

        videoTv = findViewById(R.id.video_tv);
        videoTv.setOnClickListener(this);

        cameraBtn = findViewById(R.id.camera_btn);
        cameraBtn.setOnClickListener(this);
        cameraBtn.setOnTouchListener(this);

        videoBtn = findViewById(R.id.video_btn);
        videoBtn.setOnClickListener(this);
        videoBtn.setOnTouchListener(this);

        continueBtn = findViewById(R.id.continue_btn);
        continueBtn.setOnClickListener(this);

        playVideoTxt = findViewById(R.id.text_play_video);
        photoLayout = findViewById(R.id.activity_result_photo_layout);
        videoLayout = findViewById(R.id.activity_result_video_layout);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.continue_btn:
                continueBtnIsClicked();
                break;
            case R.id.photo_tv:
               performPhotoClick();
                break;
            case R.id.video_tv:
                performVideoClick();
                break;
            case R.id.play_btn:
                videoView.start();
        }
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


            Intent intent = new Intent(DogDescriptionActivity.this, ChooseTimeActivity.class);
            intent.putExtra("name",name);
            intent.putExtra("email",email);
            intent.putExtra("phone",phone);
            intent.putExtra("dogName",dogName);
            intent.putExtra("dogAge",dogAge);
            intent.putExtra("dogBreed",dogBreed);
            startActivity(intent);

        }
        else{
            showCustomToast(getString(R.string.capture_toast));
        }
    }

    private boolean checkIfAllInitialize() {
        boolean isInitialize = true;

        if(imageView.getDrawable() == null)
        {
            isInitialize = false;
        }

        if(videoView.getDuration() == -1)
        {
            isInitialize = false;
        }

        return  isInitialize;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK)
        {
            photoLayout.setVisibility(View.VISIBLE);
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
            showCustomToast(getString(R.string.photo_msg));
        }
        if (requestCode == VIDEO_REQUEST && resultCode == RESULT_OK)
        {
            videoLayout.setVisibility(View.VISIBLE);
            Uri videoUri = data.getData();
            videoView.setVideoURI(videoUri);
            videoView.setVisibility(View.VISIBLE);
            playBtn.setVisibility(View.VISIBLE);
            playVideoTxt.setVisibility(View.VISIBLE);
            showCustomToast(getString(R.string.video_msg));

        }
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

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.camera_btn: {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    photoTv.setTextColor(getResources().getColor(R.color.bordeaux));
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    photoTv.setTextColor(getResources().getColor(R.color.black));
                    performPhotoClick();
                    return true;
                }
            }

            case R.id.video_btn: {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    videoTv.setTextColor(getResources().getColor(R.color.bordeaux));
                    return true;
                }
                else if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    videoTv.setTextColor(getResources().getColor(R.color.black));
                    performVideoClick();
                    return true;
                }

            }
        }
        return false;
    }

    private void performPhotoClick()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, CAMERA_REQUEST);
        }
    }

    private void performVideoClick()
    {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, VIDEO_REQUEST);
        }
    }
}
