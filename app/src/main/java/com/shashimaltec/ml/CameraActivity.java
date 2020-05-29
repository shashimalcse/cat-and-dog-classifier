package com.shashimaltec.ml;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.IOException;

public class CameraActivity extends AppCompatActivity {


        ImageView imageview;
        Button Camera;
        Button Detect;
        Bitmap bitmap;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_camera);


            imageview = (ImageView) findViewById(R.id.imagePrev);
            Camera = (Button) findViewById(R.id.camerabtn);
            Detect =(Button) findViewById(R.id.detectbtn);
            bitmap=null;

            AdView adView1 = (AdView) findViewById(R.id.adView1);
            AdRequest adRequest = new AdRequest.Builder().build();
            adView1.loadAd(adRequest);



            Camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CropImage.activity().setAspectRatio(1,1).start(CameraActivity.this);


                }
            });

            Detect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(bitmap!=null){
                        Bitmap resized = Bitmap.createScaledBitmap(bitmap, 32, 32, true);
                        Intent intent = new Intent(CameraActivity.this,MainActivity.class);
                        intent.putExtra("Image",resized);

                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"SELECT IMAGE",Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode,resultCode,data);
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    Uri resultUri = result.getUri();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imageview.setImageBitmap(bitmap);


                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
            }
        }
    }

