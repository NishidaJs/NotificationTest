package com.ceshizhuanyong.notification;

import android.app.NotificationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {

    public static final int CUSTOMIZED_QUERY_CODE = 1000;

    Button promptNotification;
    RadioButton choiceNormalView, choiceBigView;
    EditText inputTitle, inputMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        promptNotification = (Button) findViewById(R.id.buttonPromptNotification);
        choiceNormalView = (RadioButton) findViewById(R.id.choiceNormalView);
        choiceBigView = (RadioButton) findViewById(R.id.choiceBigView);
        inputTitle = (EditText) findViewById(R.id.inputTitle);
        inputMessage = (EditText) findViewById(R.id.inputMessage);
        promptNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = inputTitle.getText().toString();
                String message = inputMessage.getText().toString();
                NotificationCompat.Builder builder = new NotificationCompat.Builder(v.getContext());
                builder.setContentTitle(title);
                if(choiceBigView.isChecked()) {
                    builder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));
                    builder.setSmallIcon(android.R.drawable.stat_notify_chat);
                } else {
                    builder.setContentText(message);
                    builder.setSmallIcon(android.R.drawable.stat_notify_chat);
                }
                NotificationManager nm = (NotificationManager) v.getContext().getSystemService(NOTIFICATION_SERVICE);
                nm.notify(CUSTOMIZED_QUERY_CODE, builder.build());
            }
        });
    }
}
