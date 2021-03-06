package ravikiran.pathade.ravikiranpathade.newstrends;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.RemoteViews;

import ravikiran.pathade.ravikiranpathade.newstrends.activities.MainActivity;

import services.WidgetListViewService;

/**
 * Implementation of App Widget functionality.
 */
public class NewsAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.news_app_widget);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);

        String check = PreferenceManager.getDefaultSharedPreferences(context).getString(context.getResources().getString(R.string.topnews_key), context.getResources().getString(R.string.empty_string));


        if (check == context.getResources().getString(R.string.empty_string) || check.isEmpty() || check == null) {


            views.setViewVisibility(R.id.noNewsWidgetText, View.VISIBLE);
            views.setViewVisibility(R.id.listViewWidget, View.GONE);

        } else {

            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.listViewWidget);

        }

        Intent listIntent = new Intent(context, WidgetListViewService.class);
        views.setRemoteAdapter(R.id.listViewWidget, listIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        for (int appWidgetId : appWidgetIds) {

            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    public static void updateNewsWidget(Context context, AppWidgetManager appWidgetManager,
                                        int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

