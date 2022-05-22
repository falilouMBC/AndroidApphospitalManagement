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

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DossierMedical extends AppCompatActivity {

    MainActivity main = new MainActivity();
    private EditText txtLoginD, txtDateD, txtDiagnosticD, txtMedocD, txtServiceD;
    private Button btnSaveD;
    SharedPreferences sp;
    String login,date,diagnostic,doctor,medoc,service,loginRecup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dossier_medical);
        txtLoginD = findViewById(R.id.txtLoginD);
        txtDateD = findViewById(R.id.txtDateD);
        txtDiagnosticD = findViewById(R.id.txtDiagnosticD);
        txtMedocD = findViewById(R.id.txtMedocD);
        txtServiceD = findViewById(R.id.txtServiceD);
        btnSaveD = findViewById(R.id.btnSaveD);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("loginCreated", Context.MODE_PRIVATE);
        loginRecup = sp.getString("login","");
        txtLoginD.setText(loginRecup);
        btnSaveD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login = txtLoginD.getText().toString().trim();
                date = txtDateD.getText().toString().trim();
                diagnostic = txtDiagnosticD.getText().toString().trim();
                service = txtServiceD.getText().toString().trim();
                medoc = txtMedocD.getText().toString().trim();

                CreateFolder();
                Intent intent = new Intent(DossierMedical.this,UserManagement.class);
                startActivity(intent);
            }
        });
    }

    public void CreateFolder(){
        String url = "http://" + main.address + "/hopital/createdossier.php";
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("login",login)
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
                Toast.makeText(DossierMedical.this, getString(R.string.error_field), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    JSONObject jo = new JSONObject(result);
                    String status = jo.getString("status");
                    if(status.equals("KO")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(DossierMedical.this, getString(R.string.error_connexion), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txtLoginD.setText("");
                                Toast.makeText(DossierMedical.this, "rendez vous envoyé avec succées", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}