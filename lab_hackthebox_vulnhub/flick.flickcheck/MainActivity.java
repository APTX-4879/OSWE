package com.flick.flickcheck;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.io.BufferedInputStream;
import java.net.URL;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends ActionBarActivity {
    /* access modifiers changed from: protected */
    @Override // android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String api_server = getSharedPreferences(getString(R.string.preference_file), 0).getString("api_server", "none");
        if (api_server.equals("none")) {
            startActivity(new Intent(this, ReadApiServerActivity.class));
        } else {
            ((TextView) findViewById(R.id.connectionMessage)).append(" to " + api_server);
        }
        new CallAPI().execute("https://" + api_server + "/ping");
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_secure_close) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setServer(View view) {
        startActivity(new Intent(this, ReadApiServerActivity.class));
    }

    private class CallAPI extends AsyncTask<String, String, String> {
        private CallAPI() {
        }

        /* access modifiers changed from: protected */
        public String doInBackground(String... params) {
            try {
                HttpsURLConnection urlConnection = (HttpsURLConnection) new URL(params[0]).openConnection();
                TrustManager[] tm = {new PubKeyManager()};
                urlConnection.setHostnameVerifier(new HostnameVerifier() {
                    /* class com.flick.flickcheck.MainActivity.CallAPI.AnonymousClass1 */

                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
                SSLContext sc = SSLContext.getInstance("TLS");
                sc.init(null, tm, null);
                urlConnection.setSSLSocketFactory(sc.getSocketFactory());
                urlConnection.setConnectTimeout(5000);
                BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());
                byte[] response = new byte[1024];
                String response_contents = "";
                while (true) {
                    int bytesRead = in.read(response);
                    if (bytesRead != -1) {
                        response_contents = new String(response, 0, bytesRead);
                    } else {
                        try {
                            break;
                        } catch (JSONException e) {
                            e.printStackTrace();
                            return "false";
                        }
                    }
                }
                if (new JSONObject(response_contents).getString("response").equals("pong")) {
                    return "true";
                }
                return "false";
            } catch (Exception e2) {
                e2.printStackTrace();
                return "false";
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(String connected) {
            if (connected.equals("true")) {
                MainActivity.this.startActivity(new Intent(MainActivity.this.getApplicationContext(), RegisterActivity.class));
                return;
            }
            ((ProgressBar) MainActivity.this.findViewById(R.id.progressBar)).setVisibility(4);
            ((TextView) MainActivity.this.findViewById(R.id.connectionMessage)).setText("Connection failed.");
        }
    }
}