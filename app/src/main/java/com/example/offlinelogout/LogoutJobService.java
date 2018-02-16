package com.example.offlinelogout;

import android.annotation.SuppressLint;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;
import android.util.Log;

public class LogoutJobService extends JobService {
    public static final String TAG = LogoutJobService.class.getSimpleName();

    LogoutAsyncTask mLogoutTask;

    @SuppressLint("StaticFieldLeak")
    @Override
    public boolean onStartJob(final JobParameters params) {
        Log.d(TAG, "Starting logout job");
        // Runs on the main thread

        mLogoutTask = new LogoutAsyncTask() {
            @Override
            protected void onPostExecute(String s) {
                Log.d(TAG, "Logged out successfully: " + s);
                jobFinished(params, false);
            }
        };
        mLogoutTask.execute();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Stopping logout job");
        if (mLogoutTask != null) {
            mLogoutTask.cancel(true);
        }
        // We want to reschedule logout process for later
        return true;
    }

    private static class LogoutAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, "Logging out...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return "Failed";
            }
            return "Logged out";
        }

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "Preparing to logout");
        }
    }
}
