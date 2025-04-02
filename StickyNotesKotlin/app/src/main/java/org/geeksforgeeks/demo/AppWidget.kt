package org.geeksforgeeks.demo

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews


// Implementation of App Widget functionality
class AppWidget : AppWidgetProvider() {
    // Create an Intent to launch activity
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // Perform this loop procedure for each App Widget
        // that belongs to this provider
        for (appWidgetId in appWidgetIds) {
            // Create an Intent to launch MainActivity
            val launchActivity = Intent(
                context,
                MainActivity::class.java
            ).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }

            // Attaching a Pending Intent
            // to trigger it when
            // application is not alive
            val pendingIntent = PendingIntent.getActivity(context, 0, launchActivity,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)


            // Get the layout for the App Widget and attach
            // an on-click listener to the button
            val remoteViews = RemoteViews(context.packageName, R.layout.app_widget)
            remoteViews.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent)

            // Tell the AppWidgetManager to perform an
            // update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
        }
    }
}