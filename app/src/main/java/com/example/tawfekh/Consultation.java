
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

public class Consultation extends AppCompatActivity {

    MainActivity main = new MainActivity();
    private EditText txtLogin, txtDate, txtDiagnostic, txtDoctor, txtMedoc, txtService;
    private Button btnSave;
    SharedPreferences spDate;
    String login,date,diagnostic,doctor,medoc,service,loginRecup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation);
        txtDate = findViewById(R.id.txtDate);
        txtDiagnostic = findViewById(R.id.txtDiagnostic);
        txtLogin = findViewById(R.id.txtLogin);
        txtDoctor = findViewById(R.id.txtDoctor);
        txtMedoc = findViewById(R.id.txtMedoc);
        txtService = findViewById(R.id.txtService);
        btnSave = findViewById(R.id.btnSave);

        spDate = getSharedPreferences("DateRecup", Context.MODE_PRIVATE);
        SharedPreferences sp = getApplicationContext().getSharedPreferences("loginSearched", Context.MODE_PRIVATE);
        loginRecup = sp.getString("login","");
        txtLogin.setText(loginRecup);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login = txtLogin.getText().toString().trim();
                date = txtDate.getText().toString().trim();
                diagnostic = txtDiagnostic.getText().toString().trim();
                service = txtService.getText().toString().trim();
                doctor = txtDoctor.getText().toString().trim();
                medoc = txtMedoc.getText().toString().trim();

                SharedPreferences.Editor editor = spDate.edit();
                editor.putString("jour",date);
                editor.putString("service",service);
                editor.putString("symptome",diagnostic);
                editor.commit();
                consulter();
                Intent intent = new Intent(Consultation.this,UpdateDossier.class);
                startActivity(intent);
            }
        });
    }

    public void consulter(){
        String url = "http://" + main.address + "/hopital/consulter.php";
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("login",login)
                .add("dateC",date)
                .add("symptome",diagnostic)
                .add("medoc", medoc)
                .add("medecin",doctor)
                .add("services",service)
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
                        Toast.makeText(Consultation.this, getString(R.string.error_field), Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(Consultation.this, getString(R.string.error_connexion), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txtLogin.setText("");
                                txtDate.setText("");
                                txtDiagnostic.setText("");
                                txtDoctor.setText("");
                                txtMedoc.setText("");
                                txtService.setText("");
                                Toast.makeText(Consultation.this, "rendez vous envoyé avec succées", Toast.LENGTH_SHORT).show();
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