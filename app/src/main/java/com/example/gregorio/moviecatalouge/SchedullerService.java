package com.example.gregorio.moviecatalouge;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.gregorio.moviecatalouge.MainActivity.API_KEY;
import static com.example.gregorio.moviecatalouge.MainActivity.LANG;

public class SchedullerService extends GcmTaskService {
    public static final String TAG = "Upcome";
    public static final String TAG_TASK = "UpcomeTask";

    @Override
    public int onRunTask(TaskParams taskParams) {
        int result = 0;
        if (taskParams.getTag().equals(TAG_TASK)){
            result = GcmNetworkManager.RESULT_SUCCESS;
            getUpcomingData();
        }
        return result;
    }


    public void onInitializeTask(){
        super.onInitializeTasks();
       SchedullerTask schedullerTask = new SchedullerTask(this);
       schedullerTask.createPeriodicTask();
    }

    private void getUpcomingData(){
        OkHttpClient.Builder okhttpClientBuilder  = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttpClientBuilder.addInterceptor(logging);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClientBuilder.build());

        Retrofit retrofit = builder.build();
        Client client = retrofit.create(Client.class);
        Call<AllItems> call = client.getUpcoming(API_KEY,LANG);


        call.enqueue(new Callback<AllItems>(){
            @Override
            public void onResponse(Call<AllItems> call, retrofit2.Response<AllItems> response) {
              int size = response.body().getResults().size();
              FilmItems getUpComing = response.body().getResults().get(size - 1);
              showNotification(getApplicationContext(), getUpComing);
            }

            @Override
            public void onFailure(Call<AllItems> call, Throwable t) {

            }
        });
    }

    private void showNotification(Context context, FilmItems items){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        Intent contentIntent = new Intent(context, DetailActivity.class);
        //contentIntent.putExtra(DetailActivity.EXTRA_DETAIL, items);
        Uri uri = Uri.parse(DatabaseContract.CONTENT_URI+"/"+items.getJudulFilm());
        contentIntent.setData(uri);
        PendingIntent contentPendingIntent = PendingIntent.getActivity(
                context,
                200,
                contentIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_local_movies_black)
                .setContentTitle("New movie is coming!")
                .setContentText(items.getJudulFilm()+" in this "+ items.getTanggal())
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        notificationManager.notify(200, builder.build());
    }

}
