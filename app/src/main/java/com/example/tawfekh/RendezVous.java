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

public class RendezVous extends AppCompatActivity {
    private EditText etDate, etService, etLogin;
    private String date, service, login, loginRecup;
    private Button btnSoumettre;
    MainActivity main = new MainActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rendez_vous);
        etLogin = findViewById(R.id.etLogin);
        etDate = findViewById(R.id.etDate);
        etService = findViewById(R.id.etService);
        btnSoumettre = findViewById(R.id.btnSoumettre);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("loginSearched", Context.MODE_PRIVATE);
        loginRecup = sp.getString("login","");
        etLogin.setText(loginRecup);
        btnSoumettre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login = etLogin.getText().toString().trim();
                date = etDate.getText().toString().trim();
                service = etService.getText().toString().trim();
                soumettre();
            }
        });
    }

    public void soumettre(){
        String url = "http://" + main.address + "/hopital/take.php";
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("login",login)
                .add("dateR",date)
                .add("service",service)
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
                        Toast.makeText(RendezVous.this, getString(R.string.error_field), Toast.LENGTH_SHORT).show();
                    }
                });
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
                                Toast.makeText(RendezVous.this, getString(R.string.error_connexion), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                etLogin.setText("");
                                etDate.setText("");
                                etService.setText("");
                                Toast.makeText(RendezVous.this, "rendez vous envoyé avec succées", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RendezVous.this,UserManagement.class);
                                startActivity(intent);
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