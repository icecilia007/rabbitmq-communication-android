package com.brzas.rabbitmqcommunication.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.brzas.rabbitmqcommunication.R;
import com.brzas.rabbitmqcommunication.helper.RabbitMQMessageListener;
import com.brzas.rabbitmqcommunication.service.RabbitMQService;

import org.w3c.dom.Text;

public class RabbitMQActivity extends AppCompatActivity implements ServiceConnection , RabbitMQMessageListener {

    private static final String TAG = "RabbitMQActivity";
    private RabbitMQService rabbitMQService;
    private boolean isServiceBound = false;
    private EditText editReceiveTxt;
    private EditText editMessageTxt;
    private String messageSent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rabbitmq);
        editReceiveTxt = findViewById(R.id.editTextReceive);
        editMessageTxt= findViewById(R.id.editTextMessage);
        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageSent= String.valueOf(editMessageTxt.getText());
                if(isServiceBound && !TextUtils.isEmpty(messageSent)){
                    //send message
                    try{
                        rabbitMQService.sendToQueue(messageSent);
                    }catch (Exception e){
                        editReceiveTxt.setText(e.getMessage());
                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent serviceIntent = new Intent(this, RabbitMQService.class);
        startService(serviceIntent); // Strat RabbitMQ service
        bindService(serviceIntent, this, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isServiceBound) {
            unbindService(this);
            isServiceBound = false;
        }
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        RabbitMQService.LocalBinder binder = (RabbitMQService.LocalBinder) iBinder;
        rabbitMQService = binder.getService();
        isServiceBound = true;

        // Aqui você pode chamar métodos do RabbitMQService se precisar
        // rabbitMQService.enviarMensagem("Olá, RabbitMQ!");
        rabbitMQService.addMessageListener(this);

    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        isServiceBound = false;
        rabbitMQService.removeMessageListener(this);
    }

    @Override
    public void onMessageReceived(String message) {
        Log.d(TAG, "Message receive: " + message);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                 editReceiveTxt.setText(message);
            }
        });
    }
}
