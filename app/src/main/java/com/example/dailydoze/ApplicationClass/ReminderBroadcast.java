package com.example.dailydoze.ApplicationClass;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.dailydoze.R;
import com.example.dailydoze.TokenManager;

public class ReminderBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String medName = intent.getStringExtra("medName");
        final String doseQty = intent.getStringExtra("doseQty");
        String medId = intent.getStringExtra("medId");

        String myUsername = TokenManager.getInstance(context.getApplicationContext()).getUsername();

        // Check if the action is the "Taken" button
        if ("ACTION_TAKEN".equals(intent.getAction())) {
            // Display a Toast and cancel the notification
            Toast.makeText(context, "You have taken " + medName + " (" + doseQty + ")", Toast.LENGTH_SHORT).show();

            int notificationId = intent.getIntExtra("notificationId", -1); // Retrieve unique notification ID
            if (notificationId != -1) {
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                notificationManager.cancel(notificationId); // Cancel the notification after "Taken" button is clicked
            }

            // Log or update any backend/database as needed
            System.out.println("Medication ID: " + medId);
            System.out.println("Username: " + myUsername);
            return; // Exit to prevent showing a new notification
        }

        // Create an Intent for "Taken" action
        Intent takenIntent = new Intent(context, ReminderBroadcast.class);
        takenIntent.setAction("ACTION_TAKEN");
        takenIntent.putExtra("medName", medName);
        takenIntent.putExtra("doseQty", doseQty);
        takenIntent.putExtra("medId", medId);
        takenIntent.putExtra("notificationId", 200); // Unique ID for this notification

        // PendingIntent for "Taken" action button
        PendingIntent takenPendingIntent = PendingIntent.getBroadcast(
                context, 200, takenIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // Build the notification with the "Taken" action button
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyLemubit")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(medName)
                .setContentText("Dose: " + doseQty)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true) // Auto-dismiss on tap
                .addAction(R.drawable.bell_icon, "Taken", takenPendingIntent); // Add "Taken" button

        // Display the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(200, builder.build()); // Use unique ID per notification
    }
}
