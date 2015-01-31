package com.example.bra00424.pibalance;



import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private static final int REQUEST_CODE = 1234;
    private ListView resultList;
    Button speakBalanceButton;
    Button speakSampleButton;
    Button uploadButton;
    EditText balanceInput;
    EditText sampleInput;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_demo);

        speakBalanceButton = (Button) findViewById(R.id.speakBalanceButton);

        speakSampleButton = (Button) findViewById(R.id.speakSampleButton);

      //  resultList = (ListView) findViewById(R.id.list);
        balanceInput = (EditText) findViewById(R.id.balanceInput);
        sampleInput = (EditText) findViewById(R.id.sampleInput);
        // Disable button if no recognition service is present
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() == 0) {
            speakBalanceButton.setEnabled(false);
            speakSampleButton.setEnabled(false);

        }
        speakBalanceButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startVoiceRecognitionActivity(1);
            }
        });

        speakSampleButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startVoiceRecognitionActivity(2);
            }
        });



    }

    private void startVoiceRecognitionActivity(int req) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "AndroidBite Voice Recognition...");
        //startActivityForResult(intent, REQUEST_CODE);
        startActivityForResult(intent, req);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

      //  if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            ArrayList<String> matches = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            //resultList.setAdapter(new ArrayAdapter<String>(this,
                  //  android.R.layout.simple_list_item_1, matches));
            balanceInput.setText(matches.get(0));


        }

        if (requestCode == 2 && resultCode == RESULT_OK) {
            ArrayList<String> matches = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            //resultList.setAdapter(new ArrayAdapter<String>(this,
            //  android.R.layout.simple_list_item_1, matches));
            sampleInput.setText(matches.get(0));


        }


        super.onActivityResult(requestCode, resultCode, data);
    }
}
