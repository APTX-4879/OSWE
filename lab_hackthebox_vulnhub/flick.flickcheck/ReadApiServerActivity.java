package com.flick.flickcheck;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ReadApiServerActivity extends ActionBarActivity {
    /* access modifiers changed from: protected */
    @Override // android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_api_server);
        String api_server = getSharedPreferences(getString(R.string.preference_file), 0).getString("api_server", null);
        if (api_server != null) {
            ((TextView) findViewById(R.id.server_address)).append(api_server);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_read_api_server, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_secure_close) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setServer(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        String server_address = ((EditText) findViewById(R.id.server_address)).getText().toString();
        if (server_address.isEmpty()) {
            Toast.makeText(this, "Error: A value is required!", 1).show();
            return;
        }
        SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.preference_file), 0).edit();
        editor.putString("api_server", server_address);
        editor.commit();
        Toast.makeText(this, "Server set to: " + server_address, 0).show();
        startActivity(intent);
    }
}