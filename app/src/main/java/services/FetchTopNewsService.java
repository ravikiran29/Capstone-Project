package services;

import android.annotation.SuppressLint;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import models.Articles;
import models.CompleteResponse;
import rest.Client;
import rest.GetTopNewsWorldEnglish;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.icu.text.UnicodeSet.CASE;

/**
 * Created by ravikiranpathade on 12/24/17.
 */

@SuppressLint("NewApi")
public class FetchTopNewsService extends JobService {
    public final String KEY = "16a2ce7a435e4acb8482fae088ba6b9e";
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    List<Articles> newAlerts;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {

        Toast.makeText(getApplicationContext(),"Started",Toast.LENGTH_SHORT).show();
        Log.d("Service Check ","Running");

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit();

        String country = preferences.getString("countryList","");

        String language = preferences.getString("languageList","");

        String category = preferences.getString("categoriesList","");

        if (String.valueOf(language).equals("null") || String.valueOf(language).equals("")
                || String.valueOf(language).equals("0")) {
            language = "en";
            editor.putString("languageList", "en");
            editor.commit();
        }
        //TODO Implement Counrty Specific API
        if (String.valueOf(country).equals("null") || String.valueOf(country).equals("0")) {
            country = "";
            editor.putString("countryList", "");
        }

        if (String.valueOf(category).equals("null") || String.valueOf(category).equals("0")) {
            category = "";
            editor.putString("categoriesList", "");
        }

        GetTopNewsWorldEnglish service = Client.getClient().create(GetTopNewsWorldEnglish.class);
        Call<CompleteResponse> call = service.getTopNewsArticles(KEY,language,country,category);

        call.enqueue(new Callback<CompleteResponse>() {
            @Override
            public void onResponse(Call<CompleteResponse> call, Response<CompleteResponse> response) {
                Log.d("Check Service",call.request().url().toString());
                newAlerts = new ArrayList<>();
                newAlerts = response.body().getArticles();
                String json = new Gson().toJson(newAlerts);
                editor.putString("topnews", json);
                editor.putLong("topNewsFetchedAt",System.currentTimeMillis());
                editor.commit();
                //TODO Update Widget
                WidgetUpdateService updateWidget = new WidgetUpdateService();
                updateWidget.updateWidget(getApplicationContext());

            }

            @Override
            public void onFailure(Call<CompleteResponse> call, Throwable t) {

            }
        });


        jobFinished(jobParameters, false);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }


}
