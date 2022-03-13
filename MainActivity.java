package com.hanan.music.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.content.pm.ActivityInfo;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hanan.music.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    int minteger = 17;

    private ServerSocket serverSocket;
    private Socket tempLEDSocket;
    private Socket tempSensorSocket;
    private Socket tempSensorSocket1;
    private Socket tempIndicatorSocket;

    Thread serverThread = null;
    public static final int SERVER_PORT = 3175;
    private Handler handler;
    private int greenColor;
    private TextView txtServerStatus;
    private TextView txtTemprature;
    private TextView txtPressure;
    private TextView txtHumidity;

    public static String TAG = "MainActivity";   //for hotspot
    TextView timer;
    Button start, pause, reset;
    String keypad1;
    private EditText mEditTextInput;
    private TextView mTextViewCountDown;
    private Button mButtonSet;
    private Button mButtonStartPause;
    private Button mButtonReset;
    private ImageView Button1_on;
    private ImageView Button2_on;
    private ImageView Button3_on;
    private ImageView Button4_on;
    private ImageView Button5_on;
    private ImageView Button6_on;
    private TextView temper;
    private ImageView Temp;
    //private MediaPlayer catSoundMediaPlayer;
    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;

    private long mStartTimeInMillis;
    private long mTimeLeftInMillis;
    private long mEndTime;

    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;
    Handler handler2;
    int Seconds, Minutes, MilliSeconds;
    private Button phone;
    private Button music;
    boolean State=true;
    boolean State1=true;
    boolean State2=true;
    boolean State3=true;
    boolean State4=true;
    boolean State5=true;
    boolean State6=true;
    boolean State7=true;
    boolean State8=true;
    boolean State9=true;
    String temperature;
    String temperature1;


    @Override
    public void onResume(){
        super.onResume();
        // put your code here...
        //txtServerStatus.setText("Server Started. at "+ip);
        this.serverThread = new Thread(new ServerThread());
        this.serverThread.start();
    }
    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        //startActivityForResult(new Intent(Settings.ACTION_WIRELESS_SETTINGS), 0);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        TextView textViewDate = (TextView) findViewById(R.id.text_view_date);
        textViewDate.setText(currentDate);

        mEditTextInput = (EditText) findViewById(R.id.edit_text_input);
        mTextViewCountDown = (TextView) findViewById(R.id.text_view_countdown);

        mButtonSet = (Button) findViewById(R.id.button_set);
        mButtonStartPause = (Button) findViewById(R.id.button_start_pause);
        mButtonReset = (Button) findViewById(R.id.button_reset);

        handler = new Handler();
        txtTemprature =(TextView) findViewById(R.id.temperature0);
        txtPressure = (TextView)findViewById(R.id.pressure0);
        txtHumidity =(TextView) findViewById(R.id.humidity0);
        Button1_on = (ImageView) findViewById(R.id.Button1_on);
        Button2_on = (ImageView) findViewById(R.id.Button2_on);
        Button3_on = (ImageView) findViewById(R.id.Button3_on);
        Button4_on = (ImageView) findViewById(R.id.Button4_on);
        Button5_on = (ImageView) findViewById(R.id.Button5_on);
        Button6_on = (ImageView) findViewById(R.id.Button6_on);
        temper = (TextView) findViewById(R.id.integer_number);
        Temp = (ImageView)findViewById(R.id.temperature);

        temperature1=(temper.getText().toString());
       // MediaPlayer catSoundMediaPlayer = MediaPlayer.create(this, R.raw.cat_sound);

        final ImageView btnOnOff = (ImageView) findViewById(R.id.imageView2);
        btnOnOff.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    if(State)
                    {
                        sendMessageLEd("On");
                        btnOnOff.setImageResource(R.drawable.power_off);
                        State=false;
                        //btnOnOff.setText("Light Off");
                    }
                    else
                    {
                        sendMessageLEd("Off");
                        btnOnOff.setImageResource(R.drawable.power_on);

                        State=true;
                        //btnOnOff.setText("Light On");
                    }
                }
        });

        final ImageView btnOnOff1 = (ImageView) findViewById(R.id.imageView3);
        btnOnOff1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(State1)
                {
                    sendMessageLEd("On1");
                    btnOnOff1.setImageResource(R.drawable.power_off1);
                    State1=false;
                    //btnOnOff.setText("Light Off");
                }
                else
                {
                    sendMessageLEd("Off1");
                    btnOnOff1.setImageResource(R.drawable.power_on1);
                    State1=true;
                    //btnOnOff.setText("Light On");
                }
            }
        });

        final ImageView btnOnOff2 = (ImageView) findViewById(R.id.imageView4);
        btnOnOff2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(State2)
                {
                    sendMessageLEd("On2");
                    btnOnOff2.setImageResource(R.drawable.power_off2);
                    State2=false;
                    //btnOnOff.setText("Light Off");
                }
                else
                {
                    sendMessageLEd("Off2");
                    btnOnOff2.setImageResource(R.drawable.power_on2);
                    State2=true;
                    //btnOnOff.setText("Light On");
                }
            }
        });

        final ImageView btnOnOff3 = (ImageView) findViewById(R.id.imageView5);
        btnOnOff3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(State3)
                {
                    sendMessageLEd("On3");
                    btnOnOff3.setImageResource(R.drawable.power_off3);
                    State3=false;
                    //btnOnOff.setText("Light Off");
                }
                else
                {
                    sendMessageLEd("Off3");
                    btnOnOff3.setImageResource(R.drawable.power_on3);
                    State3=true;
                    //btnOnOff.setText("Light On");
                }
            }
        });

        final ImageView btnOnOff4 = (ImageView) findViewById(R.id.imageView6);
        btnOnOff4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(State4)
                {
                    sendMessageLEd("On4");
                    btnOnOff4.setImageResource(R.drawable.power_off4);
                    State4=false;
                    //btnOnOff.setText("Light Off");
                }
                else
                {
                    sendMessageLEd("Off4");
                    btnOnOff4.setImageResource(R.drawable.power_on4);
                    State4=true;
                    //btnOnOff.setText("Light On");
                }
            }
        });

        final ImageView btnOnOff5 = (ImageView) findViewById(R.id.imageView7);
        btnOnOff5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(State5)
                {
                    sendMessageLEd("On5");
                    btnOnOff5.setImageResource(R.drawable.power_off5);
                    State5=false;
                    //btnOnOff.setText("Light Off");
                }
                else
                {
                    sendMessageLEd("Off5");
                    btnOnOff5.setImageResource(R.drawable.power_on5);
                    State5=true;
                    //btnOnOff.setText("Light On");
                }
            }
        });

        final ImageView btnOnOff6 = (ImageView) findViewById(R.id.imageView8);
        btnOnOff6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(State6)
                {
                    sendMessageLEd("On6");
                    btnOnOff6.setImageResource(R.drawable.power_off6);
                    State6=false;
                    //btnOnOff.setText("Light Off");
                }
                else
                {
                    sendMessageLEd("Off6");
                    btnOnOff6.setImageResource(R.drawable.power_on6);
                    State6=true;
                    //btnOnOff.setText("Light On");
                }
            }
        });

        final ImageView btnOnOff7 = (ImageView) findViewById(R.id.imageView9);
        btnOnOff7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(State7)
                {
                    sendMessageLEd("On7");
                    btnOnOff7.setImageResource(R.drawable.power_off7);
                    State7=false;
                    //btnOnOff.setText("Light Off");
                }
                else
                {
                    sendMessageLEd("Off7");
                    btnOnOff7.setImageResource(R.drawable.power_on7);
                    State7=true;
                    //btnOnOff.setText("Light On");
                }
            }
        });

        final ImageView btnOnOff8 = (ImageView) findViewById(R.id.imageView10);
        btnOnOff8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(State8)
                {
                    sendMessageLEd("On8");
                    btnOnOff8.setImageResource(R.drawable.power_off8);
                    State8=false;
                    //btnOnOff.setText("Light Off");
                }
                else
                {
                    sendMessageLEd("Off8");
                    btnOnOff8.setImageResource(R.drawable.power_on8);
                    State8=true;
                    //btnOnOff.setText("Light On");
                }
            }
        });

        final ImageView btnOnOff9 = (ImageView) findViewById(R.id.imageView11);
        btnOnOff9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(State9)
                {
                    sendMessageLEd("On9");
                    btnOnOff9.setImageResource(R.drawable.power_off9);
                    State9=false;
                    //btnOnOff.setText("Light Off");
                }
                else
                {
                    sendMessageLEd("Off9");
                    btnOnOff9.setImageResource(R.drawable.power_on9);
                    State9=true;
                    //btnOnOff.setText("Light On");
                }
            }
        });

        timer = (TextView) findViewById(R.id.tvTimer);
        start = (Button) findViewById(R.id.btStart);
        pause = (Button) findViewById(R.id.btPause);
        reset = (Button) findViewById(R.id.btReset);
        handler2 = new Handler();

        mButtonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = mEditTextInput.getText().toString();
                if (input.length() == 0) {
                    Toast.makeText(MainActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                long millisInput = Long.parseLong(input) * 60000;
                if (millisInput == 0) {
                    Toast.makeText(MainActivity.this, "Please enter a positive number", Toast.LENGTH_SHORT).show();
                    return;
                }

                setTime(millisInput);
                mEditTextInput.setText("");
            }
        });

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        phone = (Button)findViewById(R.id.phone);
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });

        phone = (Button)findViewById(R.id.music);
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity3();
            }
        });


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartTime = SystemClock.uptimeMillis();
                handler2.postDelayed(runnable, 0);
               reset.setEnabled(false);
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TimeBuff += MillisecondTime;
                handler2.removeCallbacks(runnable);
                reset.setEnabled(true);
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MillisecondTime = 0L;
                StartTime = 0L;
                TimeBuff = 0L;
                UpdateTime = 0L;
                Seconds = 0;
                Minutes = 0;
                MilliSeconds = 0;
                timer.setText("00:00:00");
            }
        });

    }

    public void increaseInteger(View view) {
        if (minteger <= 30) {
            minteger = minteger + 1;
            display(minteger);
        }

    }
    public void decreaseInteger(View view) {
        if(minteger >=17) {
            minteger = minteger - 1;
            display(minteger);
        }
    }

    private void display(int number) {
        TextView displayInteger = (TextView) findViewById(
                R.id.integer_number);
        displayInteger.setText("" + number);
    }


    public Runnable runnable = new Runnable() {

        public void run() {
            MillisecondTime = SystemClock.uptimeMillis() - StartTime;
            UpdateTime = TimeBuff + MillisecondTime;
            Seconds = (int) (UpdateTime / 1000);
            Minutes = Seconds / 60;
            Seconds = Seconds % 60;
            MilliSeconds = (int) (UpdateTime % 1000);
            timer.setText("" + Minutes + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%03d", MilliSeconds));
            handler2.postDelayed(this, 0);
        }

    };

    public void openActivity2(){
        Intent intent = new Intent(this,Main2Activity.class);
        startActivity(intent);
        finish();
    }


    public void openActivity3(){
        Intent intent = new Intent(this,Main3Activity.class);
        startActivity(intent);
    }

    public TextView textView(String message, int color) {
        if (null == message || message.trim().isEmpty()) {
            message = "<Empty Message>";
        }
        TextView tv = new TextView(this);
        tv.setTextColor(color);
        tv.setText(message + " [" + getTime() +"]");
        tv.setTextSize(10);
        tv.setPadding(0, 5, 0, 0);
        return tv;
    }

    public void SetValues(final String message) {
        handler.post(new Runnable() {
            @Override
            public void run() {

                txtTemprature.setText(message.split(",")[1]+" *C");
                temperature=(message.split(",")[1]);
                //txtPressure.setText(message.split(",")[2]+" hPa");
                txtHumidity.setText(message.split(",")[3]+" %");
                // msgList.addView(textView(message, Color.GREEN));
            }
        });
    }

    private void SetnewCommand(final String message1) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                keypad1 = message1.split(",")[1];
                if(keypad1.equals("power"))
                {
                    openActivity2();
                }
            }
        });
    }

    public void SetButtonCommand(final String message) {
        handler.post(new Runnable() {
            @Override
            public void run() {

                if(message.equals("Button1_On"))
                {
                    Button1_on.setImageResource(R.drawable.green);
                }
                else if(message.equals("Button1_Off"))
                {
                   Button1_on.setImageResource(R.drawable.red);
                   // catSoundMediaPlayer.seekTo(0);
                    //catSoundMediaPlayer.start();
                   Button1_on.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Button1_on.setImageResource(R.drawable.yellow);
                           // catSoundMediaPlayer.pause();
                        }
                    });
                }
                else if(message.equals("Button2_On"))
                {
                    Button2_on.setImageResource(R.drawable.green1);
                }
                else if(message.equals("Button2_Off"))
                {
                    Button2_on.setImageResource(R.drawable.red1);
                    Button2_on.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Button2_on.setImageResource(R.drawable.yellow1);
                        }
                    });
                }
                else if(message.equals("Button3_On"))
                {
                    Button3_on.setImageResource(R.drawable.green2);
                }
                else if(message.equals("Button3_Off"))
                {
                    Button3_on.setImageResource(R.drawable.red2);
                    Button3_on.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Button3_on.setImageResource(R.drawable.yellow2);
                        }
                    });
                }
                else if(message.equals("Button4_On"))
                {
                    Button4_on.setImageResource(R.drawable.green3);
                }
                else if(message.equals("Button4_Off"))
                {
                    Button4_on.setImageResource(R.drawable.red3);
                    Button4_on.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Button4_on.setImageResource(R.drawable.yellow3);
                        }
                    });
                }
                else if(message.equals("Button5_On"))
                {
                    Button5_on.setImageResource(R.drawable.green4);
                }
                else if(message.equals("Button5_Off"))
                {
                    Button5_on.setImageResource(R.drawable.red4);
                    Button5_on.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Button5_on.setImageResource(R.drawable.yellow4);
                        }
                    });
                }
                else if(message.equals("Button6_On"))
                {
                    Button6_on.setImageResource(R.drawable.green5);
                }
                else
                {
                    Button6_on.setImageResource(R.drawable.red5);
                    Button6_on.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Button6_on.setImageResource(R.drawable.yellow5);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
    }

    private void sendMessageLEd(final String message) {
        try {
            if (null != tempLEDSocket) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        PrintWriter out = null;
                        try {
                            out = new PrintWriter(new BufferedWriter(
                                    new OutputStreamWriter(tempLEDSocket.getOutputStream())),
                                    true);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        out.println(message);
                    }
                }).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class ServerThread implements Runnable {

        public void run() {
            Socket socket;
            try {
                serverSocket = new ServerSocket(SERVER_PORT);
                //findViewById(R.id.start_server).setVisibility(View.GONE);
            } catch (IOException e) {
                e.printStackTrace();
                //showMessage("Error Starting Server : " + e.getMessage(), Color.RED);
            }
            if (null != serverSocket) {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        socket = serverSocket.accept();
                        CommunicationThread commThread = new CommunicationThread(socket);
                        new Thread(commThread).start();
                    } catch (IOException e) {
                        e.printStackTrace();
                        // showMessage("Error Communicating to Client :" + e.getMessage(), Color.RED);
                    }
                }
            }
        }
    }
    String read;
    String keypad;
    float PresureValue1=0;
    float PresureValue2=0;
    float TempValue=0;
    class CommunicationThread implements Runnable {

        private Socket clientSocket;

        private BufferedReader input;

        public CommunicationThread(Socket clientSocket) {
            this.clientSocket = clientSocket;

            try {
                this.input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
                //showMessage("Error Connecting to Client!!", Color.RED);
            }
            //showMessage("Connected to Client!!", greenColor);
        }


        public void run() {

            while (!Thread.currentThread().isInterrupted()) {
                try {
                     read = input.readLine();
                    if (null == read || "Disconnect".contentEquals(read)) {
                        Thread.interrupted();
                        read = "Client Disconnected";
                        //showMessage("Client : " + read, greenColor);
                        break;
                    }
                    //showMessage("Client : " + read, greenColor);
                    if(read.equals("LED"))
                    {
                        // showMessage("Connected to Client!!", greenColor);
                        tempLEDSocket = clientSocket;
                    }
                    else if(read.equals("Sensor"))
                    {
                        tempSensorSocket=clientSocket;
                    }
                    else if(read.contains("keypad")){
                        SetnewCommand(read);
                    }
                    else if(read.contains("Values1"))
                    {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                PresureValue1 = Float.parseFloat(read.split(",")[1]);
                                txtPressure.setText((PresureValue1 - PresureValue2) + "");
                            }
                        });
                    }
                    else if(read.contains("Values"))
                    {

                        handler.post(new Runnable() {
                                         @Override
                                         public void run() {
                                             try {
                                                 PresureValue2 = Float.parseFloat(read.split(",")[2]);
                                                 TempValue = Float.parseFloat(read.split(",")[1]);
                                                 txtPressure.setText((PresureValue1 - PresureValue2) + "");
                                             }
                                             catch (Exception e)
                                             {e.printStackTrace();}

                                         }
                                     });
                        SetValues(read);
                    }
                    else if(read.equals("Sensor1"))
                    {
                        tempSensorSocket1=clientSocket;
                    }

                    else if (read.equals("Button"))
                    {
                        tempIndicatorSocket = clientSocket;
                    }
                    else if(read.contains("Button"))
                    {
                        SetButtonCommand(read);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != serverThread) {
            //sendMessage("Disconnect");
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            serverThread.interrupt();
            serverThread = null;

        }
    }

    private void setTime(long milliseconds) {
        mStartTimeInMillis = milliseconds;
        resetTimer();
        closeKeyboard();
    }

    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                updateWatchInterface();
            }
        }.start();

        mTimerRunning = true;
        updateWatchInterface();
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        updateWatchInterface();
    }

    private void resetTimer() {
        mTimeLeftInMillis = mStartTimeInMillis;
        updateCountDownText();
        updateWatchInterface();
    }

    private void updateCountDownText() {
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }

        mTextViewCountDown.setText(timeLeftFormatted);
    }

    private void updateWatchInterface() {
        if (mTimerRunning) {
            mEditTextInput.setVisibility(View.INVISIBLE);
            mButtonSet.setVisibility(View.INVISIBLE);
            mButtonReset.setVisibility(View.INVISIBLE);
            mButtonStartPause.setText("Pause");
        } else {
            mEditTextInput.setVisibility(View.VISIBLE);
            mButtonSet.setVisibility(View.VISIBLE);
            mButtonStartPause.setText("Start");

            if (mTimeLeftInMillis < 1000) {
                mButtonStartPause.setVisibility(View.INVISIBLE);
            } else {
                mButtonStartPause.setVisibility(View.VISIBLE);
            }

            if (mTimeLeftInMillis < mStartTimeInMillis) {
                mButtonReset.setVisibility(View.VISIBLE);
            } else {
                mButtonReset.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("startTimeInMillis", mStartTimeInMillis);
        editor.putLong("millisLeft", mTimeLeftInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);

        editor.apply();

        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        mStartTimeInMillis = prefs.getLong("startTimeInMillis", 600000);
        mTimeLeftInMillis = prefs.getLong("millisLeft", mStartTimeInMillis);
        mTimerRunning = prefs.getBoolean("timerRunning", false);

        updateCountDownText();
        updateWatchInterface();

        if (mTimerRunning) {
            mEndTime = prefs.getLong("endTime", 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();

            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = 0;
                mTimerRunning = false;
                updateCountDownText();
                updateWatchInterface();
            } else {
                startTimer();
            }
        }
    }
}