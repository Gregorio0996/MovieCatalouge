package com.example.gregorio.moviecatalouge;

import android.content.Context;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;

public class SchedullerTask {
    private GcmNetworkManager gcmNetworkManager;

    public SchedullerTask(Context context){
        gcmNetworkManager = GcmNetworkManager.getInstance(context);
    }

    public void createPeriodicTask(){
        Task periodicTask = new PeriodicTask.Builder()
                .setService(SchedullerService.class)
                .setPeriod(60)
                .setTag(SchedullerService.TAG_TASK)
                .setPersisted(true)
                .build();
        gcmNetworkManager.schedule(periodicTask);
    }

    public void cancelPeriodicTask(){
        if(gcmNetworkManager != null){
            gcmNetworkManager.cancelTask(SchedullerService.TAG_TASK, SchedullerService.class);
        }
    }
}
