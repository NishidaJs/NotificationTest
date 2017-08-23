package com.ceshizhuanyong.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {

    public static final int CUSTOMIZED_QUERY_CODE = 1000;

    Button promptNotification;
    RadioButton choiceNormalView, choiceBigView;
    EditText inputTitle, inputMessage;
    CheckBox startIntent, autoCancel, modifyNotification;
    int messageNo;

    //关于SecondActivity，随便Create一个就可以了。
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //此处可省略，全是findViewById
        promptNotification = (Button) findViewById(R.id.buttonPromptNotification);
        choiceNormalView = (RadioButton) findViewById(R.id.choiceNormalView);
        choiceBigView = (RadioButton) findViewById(R.id.choiceBigView);
        inputTitle = (EditText) findViewById(R.id.inputTitle);
        inputMessage = (EditText) findViewById(R.id.inputMessage);
        startIntent = (CheckBox) findViewById(R.id.checkBoxStartIntent);
        autoCancel = (CheckBox) findViewById(R.id.checkBoxAutoCancel);
        modifyNotification = (CheckBox) findViewById(R.id.checkBoxModifyNotification);
        //到这儿。
        messageNo = 0;
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
                if(startIntent.isChecked()) {
                    PendingIntent pendingIntent;
                    Intent resultIntent = new Intent(v.getContext(), SecondActivity.class);
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(v.getContext());
                        stackBuilder.addParentStack(SecondActivity.class);
                        stackBuilder.addNextIntent(resultIntent);
                        pendingIntent = stackBuilder.getPendingIntent(CUSTOMIZED_QUERY_CODE, PendingIntent.FLAG_UPDATE_CURRENT);
                    } else {
                        pendingIntent = PendingIntent.getActivity(v.getContext(), CUSTOMIZED_QUERY_CODE, resultIntent, 0);
                    }
                    builder.setContentIntent(pendingIntent);
                }
                if(autoCancel.isChecked()) {
                    builder.setAutoCancel(true);
                } else {
                    builder.setAutoCancel(false);
                }
                if(modifyNotification.isChecked()) {
                    builder.setNumber(messageNo++);
                }
                NotificationManager nm = (NotificationManager) v.getContext().getSystemService(NOTIFICATION_SERVICE);
                nm.notify(CUSTOMIZED_QUERY_CODE, builder.build());
            }
        });
    }
}
