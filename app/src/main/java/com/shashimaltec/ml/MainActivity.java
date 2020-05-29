package com.shashimaltec.ml;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

import butterknife.ButterKnife;
;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();



    TextView ClassName;
    TextView CatProb;
    TextView DogProb;
    TextView TimeCount;


    private Classifier mClassifier;
    private ArrayList<String> wordList;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ClassName = (TextView) findViewById(R.id.classname);
        CatProb =(TextView) findViewById(R.id.dogprob);
        DogProb =(TextView) findViewById(R.id.catprob);
        TimeCount =(TextView) findViewById(R.id.timecount);


        AdView adView1 = (AdView) findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView1.loadAd(adRequest);

        ButterKnife.bind(this);
        init();

        Intent i = getIntent();
        Bitmap bitmap = (Bitmap) i.getParcelableExtra("Image");

        Result result = mClassifier.classify(bitmap);
        renderResult(result);





    }




    private void init() {
        try {
            mClassifier = new Classifier(this);
        } catch (IOException e) {
            Toast.makeText(this, R.string.failed_to_create_classifier, Toast.LENGTH_LONG).show();
        }
    }

    private void renderResult(Result result) {
        ClassName.setText(getName(result.getNumber()));
        CatProb.setText(result.getCatprob());
        DogProb.setText(result.getDogprob());
        TimeCount.setText("Time cost: "+Long.toString(result.getTimeCost())+"ms");

    }

    private String getName(int number){
        if(number==0){
            return  "Cat";
        }
        else{
            return "Dog";
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getClassName(){
        AssetManager assetManager = getAssets();

        // To load text file
        InputStream input;
        try {
            input = assetManager.open("labels.txt");
            wordList= new ArrayList<String>();

            BufferedReader reader = new BufferedReader(new InputStreamReader(input , Charset.defaultCharset().forName("UTF-8")));

            String line = "";



            while ((line = reader.readLine()) != null) {
                wordList.add(line);
            }





        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
