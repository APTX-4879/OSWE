package com.flick.flickcheck;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.Properties;
import java.util.UUID;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import org.json.JSONException;
import org.json.JSONObject;

public class CommandActivity extends ActionBarActivity {
    String integrity_check = "YFhaRBMNFRQDFxJEFlFDExIDVUMGEhcLAUNFBVdWQGFeXBIVWEsZWQ==";

    /* access modifiers changed from: protected */
    @Override // android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_command);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_command, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_secure_close) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void doCmd(View view) {
        Toast.makeText(this, "Running command: " + view.getTag().toString(), 0).show();
        String base64_command = Base64.encodeToString(view.getTag().toString().getBytes(), 0);
        TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService("phone");
        String deviceId = new UUID((long) ("" + Settings.Secure.getString(getContentResolver(), "android_id")).hashCode(), (((long) ("" + tm.getDeviceId()).hashCode()) << 32) | ((long) ("" + tm.getSimSerialNumber()).hashCode())).toString();
        SharedPreferences sharedpreferences = getSharedPreferences(getString(R.string.preference_file), 0);
        String api_server = sharedpreferences.getString("api_server", null);
        String api_auth_token = sharedpreferences.getString("api_auth_token", null);
        new CallAPI().execute("https://" + api_server + "/do/cmd/" + base64_command, deviceId, api_auth_token);
    }

    private class CallAPI extends AsyncTask<String, String, String> {
        private CallAPI() {
        }

        /* access modifiers changed from: protected */
        public String doInBackground(String... params) {
            String urlString = params[0];
            String deviceId = params[1];
            String api_auth_token = params[2];
            try {
                HttpsURLConnection urlConnection = (HttpsURLConnection) new URL(urlString).openConnection();
                TrustManager[] tm = {new PubKeyManager()};
                urlConnection.setHostnameVerifier(new HostnameVerifier() {
                    /* class com.flick.flickcheck.CommandActivity.CallAPI.AnonymousClass1 */

                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
                SSLContext sc = SSLContext.getInstance("TLS");
                sc.init(null, tm, null);
                urlConnection.setSSLSocketFactory(sc.getSocketFactory());
                urlConnection.setConnectTimeout(5000);
                urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                urlConnection.setRequestProperty("X-UUID", deviceId);
                urlConnection.setRequestProperty("X-Token", api_auth_token);
                BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());
                byte[] response = new byte[1024];
                String response_contents = "";
                while (true) {
                    int bytesRead = in.read(response);
                    if (bytesRead != -1) {
                        response_contents = response_contents + new String(response, 0, bytesRead);
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
                if (responseJson.getString("status").equals("ok")) {
                    return responseJson.getString("output");
                }
                if (responseJson.getString("status").equals("error")) {
                    return "Command error: " + responseJson.getString("output");
                }
                return "";
            } catch (Exception e2) {
                e2.printStackTrace();
                return "";
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(String cmd_output) {
            ((TextView) CommandActivity.this.findViewById(R.id.cmd_output)).setText(cmd_output);
        }
    }

    public void doSSHCmd(View view) {
        if (((Switch) findViewById(R.id.use_ssh)).isChecked()) {
            Toast.makeText(this, "Enabling Secure Access...", 0).show();
            String api_server = getSharedPreferences(getString(R.string.preference_file), 0).getString("api_server", null);
            new SSHCommand().execute(api_server);
            return;
        }
        Toast.makeText(this, "Disabling Secure Access...", 0).show();
    }

    /* access modifiers changed from: private */
    public static String validate(String input) {
        char[] key = {'T', 'h', 'i', 's', ' ', 'i', 's', ' ', 'a', ' ', 's', 'u', 'p', 'e', 'r', ' ', 's', 'e', 'c', 'r', 'e', 't', ' ', 'm', 'e', 's', 's', 'a', 'g', 'e', '!'};
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            output.append((char) (input.charAt(i) ^ key[i % key.length]));
        }
        return output.toString();
    }

    private class SSHCommand extends AsyncTask<String, String, String> {
        private SSHCommand() {
        }

        /* access modifiers changed from: protected */
        public String doInBackground(String... params) {
            String server = params[0];
            String text = new String(Base64.decode(CommandActivity.this.integrity_check, 0));
            try {
                Session session = new JSch().getSession("robin", server, 22);
                session.setPassword(CommandActivity.validate(text));
                Properties prop = new Properties();
                prop.put("StrictHostKeyChecking", "no");
                session.setConfig(prop);
                session.connect();
                ChannelExec channelssh = (ChannelExec) session.openChannel("exec");
                channelssh.setOutputStream(new ByteArrayOutputStream());
                channelssh.setCommand("/usr/bin/id");
                channelssh.connect();
                channelssh.disconnect();
                return "Secure Access Enabled";
            } catch (Exception e) {
                e.printStackTrace();
                return "Secure Access Failed";
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(String cmd_output) {
            ((TextView) CommandActivity.this.findViewById(R.id.cmd_output)).setText(cmd_output);
        }
    }
}