package com.example.tawfekh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserManagement extends AppCompatActivity {
        private Button btnAjout, btnSearch, btnRv, btnCons, btnDisconnect;
        private EditText loginSearch;
        private TextView tvList, txtAppointment,  txtConsult;
        public static String infos, searched, appointments, consults;
        private SharedPreferences sp;
        MainActivity main = new MainActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);
        tvList = findViewById(R.id.txtPatient);
        btnDisconnect = findViewById(R.id.btnDisconnect);
        btnAjout = findViewById(R.id.btnAjout);
        btnRv = findViewById(R.id.btnRv);
        btnCons = findViewById(R.id.btnCons);
        txtConsult = findViewById(R.id.txtConsult);
        loginSearch = findViewById(R.id.loginSearch);
        btnSearch = findViewById(R.id.btnSearch);
        txtAppointment = findViewById(R.id.txtAppointment);

        btnAjout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserManagement.this, InscriptionActivity.class);
                startActivity(intent);
            }
        });
        sp = getSharedPreferences("loginSearched", Context.MODE_PRIVATE);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searched = loginSearch.getText().toString().trim();
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("login",searched);
                editor.commit();
                list();
                appointment();
                consult();
            }
        });
        btnRv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserManagement.this,RendezVous.class);
                startActivity(intent);

                //getSharedPreferences();
            }
        });

        btnCons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserManagement.this, Consultation.class);
                startActivity(intent);
            }
        });

        btnDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new  Intent(UserManagement.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void list(){
        String url = "http://" + main.address + "/hopital/user.php?login=" + searched;
        infos = "";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(UserManagement.this, getString(R.string.error_connexion), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    JSONObject jo = new JSONObject(result);
                    JSONArray ja = jo.getJSONArray("user");
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject element = ja.getJSONObject(i);
                        String nom = element.getString("lastname");
                        String prenom = element.getString("firstname");
                        String age = element.getString("age");
                        String sexe = element.getString("sexe");
                        infos += "Prenom : " + prenom + "\n\n" + "Nom : " + nom +  "\n\n" + "Age : " + age + "\n\n" + "Sexe : " + sexe;
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvList.setText(infos);
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void appointment(){
        String url = "http://" + main.address + "/hopital/rendezvous.php?login=" + searched;
        appointments = "";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(UserManagement.this, getString(R.string.error_connexion), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    JSONObject jo = new JSONObject(result);
                    JSONArray ja = jo.getJSONArray("rendezvous");
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject element = ja.getJSONObject(i);
                        String date = element.getString("dateR");
                        String service = element.getString("service");
                        appointments += "Date : " + date + "\n\n" + "Service : " + service + "\n\n" + "\n\n" + "--------" + "\n\n\n";
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txtAppointment.setText(appointments);
                            }
                        });
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

    public void consult(){
        String url = "http://" + main.address + "/hopital/consultation.php?login=" + searched;
        OkHttpClient client = new OkHttpClient();
        consults = "";

        Request request =new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(UserManagement.this, getString(R.string.error_connexion), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    JSONObject jo = new JSONObject(result);
                    JSONArray ja = jo.getJSONArray("consultation");
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject element = ja.getJSONObject(i);
                        String date = element.getString("dateC");
                        String symptome = element.getString("symptome");
                        String medoc = element.getString("medoc");
                        String medecin = element.getString("medecin");
                        String service = element.getString("services");

                        consults += "Date de consultation : " + date + "\n\n" + "Maladies trouvées : " + symptome + "\n\n" + "Medicaments prescrits : " + medoc + "\n\n" + "Suivi par : Dr " + medecin + "\n\n" + "Service : " + service + "\n\n\n" + "--------" + "\n\n\n";
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            txtConsult.setText(consults);
                        }
                    });
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}