package com.flick.flickcheck;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedInputStream;
import java.net.URL;
import java.util.UUID;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends ActionBarActivity {
    /* access modifiers changed from: protected */
    @Override // android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService("phone");
        String deviceId = new UUID((long) ("" + Settings.Secure.getString(getContentResolver(), "android_id")).hashCode(), (((long) ("" + tm.getDeviceId()).hashCode()) << 32) | ((long) ("" + tm.getSimSerialNumber()).hashCode())).toString();
        SharedPreferences sharedpreferences = getSharedPreferences(getString(R.string.preference_file), 0);
        String api_server = sharedpreferences.getString("api_server", null);
        if (sharedpreferences.getString("api_auth_token", "none").equals("none")) {
            Toast.makeText(this, "Registering device with API", 0).show();
            startActivity(new Intent(getApplicationContext(), DoRegisterActivity.class));
        }
        new CallAPI().execute("https://" + api_server + "/register/status/" + deviceId);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_secure_close) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
                    /* class com.flick.flickcheck.RegisterActivity.CallAPI.AnonymousClass1 */

                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
                SSLContext sc = SSLContext.getInstance("TLS");
                sc.init(null, tm, null);
                urlConnection.setSSLSocketFactory(sc.getSocketFactory());
                urlConnection.setConnectTimeout(5000);
                urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
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
                            return "no";
                        }
                    }
                }
                if (new JSONObject(response_contents).getString("registered").equals("yes")) {
                    return "yes";
                }
                return "no";
            } catch (Exception e2) {
                e2.printStackTrace();
                return "no";
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(String registered) {
            if (registered.equals("yes")) {
                ((ProgressBar) RegisterActivity.this.findViewById(R.id.progressBarRegister)).setVisibility(4);
                ((TextView) RegisterActivity.this.findViewById(R.id.registration_text)).setText("Already Registered");
                RegisterActivity.this.startActivity(new Intent(RegisterActivity.this.getApplicationContext(), CommandActivity.class));
                return;
            }
            Toast.makeText(RegisterActivity.this, "Registering device with API", 0).show();
            RegisterActivity.this.startActivity(new Intent(RegisterActivity.this.getApplicationContext(), DoRegisterActivity.class));
        }
    }
}