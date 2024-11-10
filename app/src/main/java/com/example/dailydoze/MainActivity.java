package com.example.dailydoze;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Handler handler;
    private TextView top, bottom;
    Animation fade,up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        top=findViewById(R.id.textView1);
        bottom=findViewById(R.id.bottomText);


        createNotificationChannel();

        SpannableStringBuilder ssb=new SpannableStringBuilder();

        ssb.append("Made with  ");
        ssb.setSpan(new ImageSpan(this,R.drawable.ic_heart),ssb.length()-1, ssb.length(),0);
        ssb.append("  at hackCBS");
        bottom.setText(ssb);


        bottom.setAnimation(up);


        up= AnimationUtils.loadAnimation(this, R.anim.top_animation);
        fade= AnimationUtils.loadAnimation(this,R.anim.fade);
        top.setAnimation(fade);




        handler=new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        },4000);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "LemubitReminderChannel";
            String description = "Channel for Lemubit medication reminders";
            int importance = NotificationManager.IMPORTANCE_HIGH; // Use HIGH for better visibility
            NotificationChannel channel = new NotificationChannel("notifyLemubit", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            } else {
                Log.e("NotificationChannel", "NotificationManager is null");
            }
        }
    }


}