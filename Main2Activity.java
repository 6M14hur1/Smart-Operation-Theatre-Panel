package com.hanan.music.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hanan.music.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main2Activity extends AppCompatActivity{

    private ServerSocket serverSocket;
    private Socket tempIRSocket;
    Thread serverThread = null;
    public static final int SERVER_PORT = 3176;
    private Handler handler;

    EditText editText;
    Button b1;
    Button b2;
    Button b3;
    Button b4;
    Button b5;
    Button b6;
    Button b7;
    Button b8;
    Button b9;
    Button b0;
    Button dele;
    Button call;
    Button previous;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main2);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        handler = new Handler();
        editText=(EditText)findViewById(R.id.editText);
        b1=(Button)findViewById(R.id.n1);
        b2=(Button)findViewById(R.id.n2);
        b3=(Button)findViewById(R.id.n3);
        b4=(Button)findViewById(R.id.n4);
        b5=(Button)findViewById(R.id.n5);
        b6=(Button)findViewById(R.id.n6);
        b7=(Button)findViewById(R.id.n7);
        b8=(Button)findViewById(R.id.n8);
        b9=(Button)findViewById(R.id.n9);
        b0=(Button)findViewById(R.id.n0);
        dele=(Button)findViewById(R.id.dele);
        call=(Button)findViewById(R.id.clear);
        previous=(Button)findViewById(R.id.previous);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        this.serverThread = new Thread(new ServerThread());
        this.serverThread.start();

        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editText.setText(editText.getText().insert(editText.getText().length(),"0"));
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editText.setText(editText.getText().insert(editText.getText().length(),"1"));
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editText.setText(editText.getText().insert(editText.getText().length(),"2"));
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editText.setText(editText.getText().insert(editText.getText().length(),"3"));
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editText.setText(editText.getText().insert(editText.getText().length(),"4"));
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editText.setText(editText.getText().insert(editText.getText().length(),"5"));
            }
        });

        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editText.setText(editText.getText().insert(editText.getText().length(),"6"));
            }
        });

        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editText.setText(editText.getText().insert(editText.getText().length(),"7"));
            }
        });

        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editText.setText(editText.getText().insert(editText.getText().length(),"8"));
            }
        });

        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editText.setText(editText.getText().insert(editText.getText().length(),"9"));
            }
        });

        dele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editText.setText(editText.getText().delete(editText.getText().length()-1,editText.getText().length()));
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendMessageLEd(editText.getText().toString());
            }
        });
    }
    private void sendMessageLEd(final String message) {
        try {
            if (null != tempIRSocket) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        PrintWriter out = null;
                        try {
                            out = new PrintWriter(new BufferedWriter(
                                    new OutputStreamWriter(tempIRSocket.getOutputStream())),
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
    public void setvalue(final String message) {

        handler.post(new Runnable() {
            @Override
            public void run() {

                if(message.contains("Remote_REVERSE"))
                {
                    editText.setText(editText.getText().delete(editText.getText().length()-1,editText.getText().length()));
                }
                else if (message.contains("Remote_MODE"))
                {
                    startActivity(new Intent(Main2Activity.this,MainActivity.class));
                    finish();
                }
                else if(editText.getText().length()<=10)
                {
                    editText.append(message.split("_")[1]);

                }
            }
        });
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
                    String read = input.readLine();
                    if (null == read || "Disconnect".contentEquals(read)) {
                        Thread.interrupted();
                        read = "Client Disconnected";
                        //showMessage("Client : " + read, greenColor);
                        break;
                    }
                    //showMessage("Client : " + read, greenColor);
                    if(read.equals("Sensor"))
                    {
                        // showMessage("Connected to Client!!", greenColor);
                        tempIRSocket = clientSocket;
                    }
                    else if(read.contains("Remote_"))
                    {
                        setvalue(read);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != serverThread) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //sendMessage("Disconnect");
            serverThread.interrupt();
            serverThread = null;

        }
    }
    public void previous(View view){
        startActivity(new Intent(Main2Activity.this,MainActivity.class));
        finish();
    }

    }



