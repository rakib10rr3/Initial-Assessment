package com.rakib.initialassessment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VocalImitationActivity extends AppCompatActivity {

    TextView mQuestionTextView;
    TextView mResultTextViewTextView;
    TextView mChangeTextView;
    FloatingActionButton fab;
    ImageButton imageButton;
    Button mListenButton;
    Button mListenResultButton;
    SeekBar mSeekBarPitch;
    SeekBar mSeekBarSpeed;

    private TextToSpeech mTTS;
    private SpeechRecognizer mSpeechRecognizer;
    Intent mSpeechRecognizerIntent;

    List<String> questions = new ArrayList<>();
    int j = 0;
    int score = 0;

    Long id;
    int flag;
    int assessmentNo;
    String category;

    String latest = "";

    private static final int RECORD_AUDIO_PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    private boolean sentToSettings = false;
    private SharedPreferences permissionStatus;

    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocal_imitation);

        questions.add("mango");
        questions.add("apple");
        questions.add("cat");
        questions.add("dog");
        questions.add("school");

        id = getIntent().getLongExtra("id", -1);
        flag = getIntent().getIntExtra("date_flag", -1);
        assessmentNo = getIntent().getIntExtra("assessment_no", -1);
        category = getIntent().getStringExtra("cat");

        permissionStatus = getSharedPreferences("permissionStatus", MODE_PRIVATE);

        checkPermission();


        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {
                ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null) {
                    mResultTextViewTextView.setText(matches.get(0));
                    latest = matches.get(0);
                }
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });


        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(VocalImitationActivity.this, "Sorry! There's been an error", Toast.LENGTH_SHORT).show();
                    } else {
                        //
                    }
                } else {
                    Toast.makeText(VocalImitationActivity.this, "Sorry! There's been an error", Toast.LENGTH_SHORT).show();

                }
            }
        });

        initializeView();


        mQuestionTextView.setText(questions.get(0));

        mListenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speak();
            }
        });

        mListenResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speakResult();
            }
        });

        imageButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        mSpeechRecognizer.stopListening();
                        mChangeTextView.setText("Hold to talk");


                        break;
                    case MotionEvent.ACTION_DOWN:
                        mResultTextViewTextView.setText("");
                        mChangeTextView.setText("Listening ... ");
                        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                        break;
                }
                return false;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (questions.get(j).equals(latest)) {
                    score++;
                    Log.d("match", "onClick: matched");
                } else {
                    Log.d("match", "onClick: not matched");
                }

                j++;
                if (j == 5) {
                    Toast.makeText(VocalImitationActivity.this, "Completed", Toast.LENGTH_SHORT).show();
                    //j=0;
                    intent = new Intent(getApplicationContext(), CatagoryResultActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("category", "Vocal Imitation");
                    intent.putExtra("score", score);
                    intent.putExtra("date_flag", flag);
                    intent.putExtra("assessment_no", assessmentNo);
                    startActivity(intent);
                    finish();
                } else
                    mQuestionTextView.setText(questions.get(j));


            }
        });

    }

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(VocalImitationActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(VocalImitationActivity.this, Manifest.permission.RECORD_AUDIO)) {
                //Show Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(VocalImitationActivity.this);
                builder.setTitle("Need Record Audio Permission");
                builder.setMessage("This app needs record audio permission.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(VocalImitationActivity.this, new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_AUDIO_PERMISSION_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else if (permissionStatus.getBoolean(Manifest.permission.RECORD_AUDIO, false)) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(VocalImitationActivity.this);
                builder.setTitle("Need Record Audio Permission");
                builder.setMessage("This app needs record audio permission.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        sentToSettings = true;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                        Toast.makeText(getBaseContext(), "Go to Permissions to Grant Audio", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else {
                //just request the permission
                ActivityCompat.requestPermissions(VocalImitationActivity.this, new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_AUDIO_PERMISSION_CONSTANT);
            }


            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(Manifest.permission.RECORD_AUDIO, true);
            editor.commit();


        } else {
            //You already have the permission, just go ahead.
            proceedAfterPermission();
        }
    }

    private void proceedAfterPermission() {
        //We've got the permission, now we can proceed further
        Toast.makeText(getBaseContext(), "We got the Audio Permission", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RECORD_AUDIO_PERMISSION_CONSTANT) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //The External Storage Write Permission is granted to you... Continue your left job...
                proceedAfterPermission();
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(VocalImitationActivity.this, Manifest.permission.RECORD_AUDIO)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(VocalImitationActivity.this);
                    builder.setTitle("Need Record Audio Permission");
                    builder.setMessage("This app needs record audio permission");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();


                            ActivityCompat.requestPermissions(VocalImitationActivity.this, new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_AUDIO_PERMISSION_CONSTANT);


                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } else {
                    Toast.makeText(getBaseContext(), "Unable to get Permission", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(VocalImitationActivity.this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
            }
        }
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (sentToSettings) {
            if (ActivityCompat.checkSelfPermission(VocalImitationActivity.this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
            }
        }
    }


    @Override
    protected void onDestroy() {
        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
        }
        super.onDestroy();
    }

    private void speak() {
        //TODO question er text change korte hobe
        mQuestionTextView.setText(questions.get(j));

        String text = questions.get(j);
        float pitch = (float) mSeekBarPitch.getProgress() / 50;
        if (pitch < 0.1) pitch = 0.1f;

        float speed = (float) mSeekBarSpeed.getProgress() / 50;
        if (speed < 0.1) speed = 0.1f;

        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }


    }

    private void speakResult()
    {
        String text = latest;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    private void initializeView() {
        mQuestionTextView = findViewById(R.id.txt1);
        mResultTextViewTextView = findViewById(R.id.txt_result);
        mChangeTextView = findViewById(R.id.txt_change);
        fab = findViewById(R.id.fabNext);
        mListenButton = findViewById(R.id.btn_listen);
        mListenResultButton = findViewById(R.id.btn_listen_result);
        imageButton = findViewById(R.id.img_btn);
        mSeekBarSpeed = findViewById(R.id.seek_bar_speed);
        mSeekBarPitch = findViewById(R.id.seek_bar_pitch);

    }
}
