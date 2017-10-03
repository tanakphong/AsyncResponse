package com.wesoft.asyncresponse;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    MyAsyncTask asyncTask;
    private Button mButton;
    private ProgressFragment dialog;

//   implements AsyncResponse
//    @Override
//    public void processFinish(String output) {
//        Log.i("dlg", "processFinish: "+output);
//        dialog.dismiss();
////        Intent i=new Intent(getApplicationContext(),Page2Activity.class);
////        startActivity(i);
//    }


    static class MyAsyncTask extends AsyncTask<String, Void, String> {

        public AsyncResponse delegate = null;

        public MyAsyncTask(AsyncResponse delegate) {
            this.delegate = delegate;
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.i("dlg", "doInBackground: start");
            try {
                Thread.sleep(12000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.i("dlg", "doInBackground: finish");
            return "return doInBackground";
        }

        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);
            Log.i("dlg", "onPostExecute: start");
            delegate.processFinish(s);
            Log.i("dlg", "onPostExecute: finish");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialog = new ProgressFragment();
                Bundle bundle = new Bundle();
                bundle.putString("MSG", "Text Bundle...");
                dialog.setArguments(bundle);
                dialog.show(getSupportFragmentManager(), "tag0");
            }
        });

        Log.i("dlg", "onCreate: start");

        dialog = new ProgressFragment();
        Bundle bundle = new Bundle();
        bundle.putString("MSG", "Text Bundle...");
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), "tag1");
        dialog.setCancelable(false);

        asyncTask = new MyAsyncTask(new AsyncResponse() {
            @Override
            public void processFinish(String output) {
                Log.i("dlg", "processFinish: " + output);
                dialog.dismiss();
            }
        });

//        asyncTask =new MyAsyncTask();
        asyncTask.execute();


        //finish();
        Log.i("dlg", "onCreate: finish");
        Log.i("dlg", "onCreate: next process");
    }
}
