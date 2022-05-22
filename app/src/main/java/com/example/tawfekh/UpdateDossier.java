package com.example.tawfekh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UpdateDossier extends AppCompatActivity {

    MainActivity main = new MainActivity();
    private EditText txtLoginU, txtDateU, txtDiagnosticU, txtMedocU, txtServiceU;
    private Button btnSaveU;
    SharedPreferences sp;
    String login, date, diagnostic, medoc, service, loginRecup, serviceRecup, dateRecup, diagnosticRecup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_dossier);

        txtLoginU = findViewById(R.id.txtLoginU);
        txtDateU = findViewById(R.id.txtDateU);
        txtDiagnosticU = findViewById(R.id.txtDiagnosticU);
        txtMedocU = findViewById(R.id.txtMedocU);
        txtServiceU = findViewById(R.id.txtServiceU);
        btnSaveU = findViewById(R.id.btnSaveU);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("loginSearched", Context.MODE_PRIVATE);
        loginRecup = sp.getString("login","");
        txtLoginU.setText(loginRecup);
        SharedPreferences sp0 = getApplicationContext().getSharedPreferences("DateRecup", Context.MODE_PRIVATE);
        loginRecup = sp.getString("login","");
        dateRecup = sp0.getString("jour","");
        serviceRecup = sp0.getString("service","");
        diagnosticRecup = sp0.getString("symptome","");

        txtLoginU.setText(loginRecup);
        txtLoginU.setText(loginRecup);
        txtDateU.setText(dateRecup);
        txtServiceU.setText(serviceRecup);
        txtDiagnosticU.setText(diagnosticRecup);

        btnSaveU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login = txtLoginU.getText().toString().trim();
                date = txtDateU.getText().toString().trim();
                diagnostic = txtDiagnosticU.getText().toString().trim();
                medoc = txtMedocU.getText().toString().trim();
                service = txtServiceU.getText().toString().trim();
                updateRendezVous();
                Intent intent = new Intent(UpdateDossier.this,UserManagement.class);
                startActivity(intent);
            }
        });
    }

    public void updateRendezVous(){
        String url = "http://" + main.address + "/hopital/updatedossier.php?login=" + UserManagement.searched;
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("login",UserManagement.searched)
                .add("diagnostic",diagnostic)
                .add("medocs",medoc)
                .add("service", service)
                .add("jour",date)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(UpdateDossier.this, getString(R.string.error_field), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtLoginU.setText("");
                        Toast.makeText(UpdateDossier.this, "rendez vous envoyé avec succées", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }
}