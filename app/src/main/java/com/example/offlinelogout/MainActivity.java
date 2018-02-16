package com.example.offlinelogout;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    private static final int LOGOUT_JOB_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.logout_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogout();
            }
        });
    }

    private void onLogout() {
        if (Utils.isConnected(this)) {
            Log.d(TAG, "Have internet access, logging out now");
        } else {
            // Schedule job to logout when internet is available
            Log.d(TAG, "No internet access. Scheduling logout job");
            scheduleLogoutJob();
        }
    }

    private void scheduleLogoutJob() {
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo logoutJobInfo = new JobInfo.Builder(LOGOUT_JOB_ID,
                new ComponentName(this, LogoutJobService.class))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .build();
        jobScheduler.schedule(logoutJobInfo);
    }
}
