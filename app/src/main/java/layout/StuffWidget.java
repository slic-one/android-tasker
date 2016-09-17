package layout;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;
import com.silentslic.stufftodo.R;

/**
 * Implementation of App Widget functionality.
 */
public class StuffWidget extends AppWidgetProvider {
    private static final int[] WIDGETTEXT_IDS = {
            R.id.appwidget_text0,
            R.id.appwidget_text1,
            R.id.appwidget_text2,
            R.id.appwidget_text3,
            R.id.appwidget_text4,
            R.id.appwidget_text5,
            R.id.appwidget_text6,
            R.id.appwidget_text7,
            R.id.appwidget_text8,
            R.id.appwidget_text9,
    };

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.stuff_widget);
        for (int i = 0; i < WIDGETTEXT_IDS.length; i++) {
            // TODO : this one won't work
            views.setTextViewText(WIDGETTEXT_IDS[i], context.getSharedPreferences(null, Context.MODE_PRIVATE).getString(String.valueOf(i), "<loading_error>"));
        }

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

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

