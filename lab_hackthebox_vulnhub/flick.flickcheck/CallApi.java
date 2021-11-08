package com.flick.flickcheck;

import android.os.AsyncTask;
import java.io.BufferedInputStream;
import java.net.URL;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import org.json.JSONException;
import org.json.JSONObject;

public class CallApi extends AsyncTask<String, String, String> {
    /* access modifiers changed from: protected */
    public String doInBackground(String... params) {
        try {
            HttpsURLConnection urlConnection = (HttpsURLConnection) new URL(params[0]).openConnection();
            TrustManager[] tm = {new PubKeyManager()};
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                /* class com.flick.flickcheck.CallApi.AnonymousClass1 */

                public boolean verify(String s, SSLSession sslSession) {
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
                        return "";
                    }
                }
            }
            if (new JSONObject(response_contents).getString("response").equals("pong")) {
                return "true";
            }
            return "";
        } catch (Exception e2) {
            e2.printStackTrace();
            return "";
        }
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(String connected) {
    }
}