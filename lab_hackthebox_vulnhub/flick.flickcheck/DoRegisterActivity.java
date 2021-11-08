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
import android.widget.Toast;
import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.net.URL;
import java.util.UUID;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import org.json.JSONException;
import org.json.JSONObject;

public class DoRegisterActivity extends ActionBarActivity {
    /* access modifiers changed from: protected */
    @Override // android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_register);
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            /* class com.flick.flickcheck.DoRegisterActivity.AnonymousClass1 */

            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        });
        TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService("phone");
        String deviceId = new UUID((long) ("" + Settings.Secure.getString(getContentResolver(), "android_id")).hashCode(), (((long) ("" + tm.getDeviceId()).hashCode()) << 32) | ((long) ("" + tm.getSimSerialNumber()).hashCode())).toString();
        String api_server = getSharedPreferences(getString(R.string.preference_file), 0).getString("api_server", null);
        new CallAPI().execute("https://" + api_server + "/register/new", deviceId);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_do_register, menu);
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
            String urlString = params[0];
            String deviceId = params[1];
            try {
                HttpsURLConnection urlConnection = (HttpsURLConnection) new URL(urlString).openConnection();
                TrustManager[] tm = {new PubKeyManager()};
                urlConnection.setHostnameVerifier(new HostnameVerifier() {
                    /* class com.flick.flickcheck.DoRegisterActivity.CallAPI.AnonymousClass1 */

                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
                SSLContext sc = SSLContext.getInstance("TLS");
                sc.init(null, tm, null);
                urlConnection.setSSLSocketFactory(sc.getSocketFactory());
                urlConnection.setConnectTimeout(5000);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                JSONObject uuid = new JSONObject();
                uuid.put("uuid", deviceId);
                DataOutputStream outputStream = new DataOutputStream(urlConnection.getOutputStream());
                outputStream.write(uuid.toString().getBytes());
                outputStream.flush();
                outputStream.close();
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
                            return "";
                        }
                    }
                }
                JSONObject responseJson = new JSONObject(response_contents);
                if (responseJson.getString("registered").equals("ok")) {
                    return responseJson.getString("token");
                }
                return "";
            } catch (Exception e2) {
                e2.printStackTrace();
                return "";
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(String token) {
            SharedPreferences.Editor editor = DoRegisterActivity.this.getSharedPreferences(DoRegisterActivity.this.getString(R.string.preference_file), 0).edit();
            editor.putString("api_auth_token", token);
            editor.commit();
            Toast.makeText(DoRegisterActivity.this, "Registered with API", 0).show();
            DoRegisterActivity.this.startActivity(new Intent(DoRegisterActivity.this.getApplicationContext(), CommandActivity.class));
        }
    }
}