package com.example.tawfekh;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class InscriptionActivity extends AppCompatActivity {

    private EditText txtFirstName, txtLastName, txtAge, txtLogin, txtPassword;
    private RadioButton rdGender, rdMan, rdWoman;
    private Button btnSign;
    private String firstName, lastName, age, login, password, sex;
    SharedPreferences sp;

    private RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        rdMan = findViewById(R.id.rdMan);
        rdWoman = findViewById(R.id.rdwoman);
        txtLogin = findViewById(R.id.txtLoginIns);
        txtPassword = findViewById(R.id.txtPwdIns);
        txtFirstName = findViewById(R.id.txtFirstName);
        txtLastName = findViewById(R.id.txtLastName);
        txtAge = findViewById(R.id.txtAge);
        radioGroup = findViewById(R.id.rdGender);
        btnSign = findViewById(R.id.btnSign);

        txtLogin.setText("patient-");
        sp = getSharedPreferences("loginCreated", Context.MODE_PRIVATE);
        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstName = txtFirstName.getText().toString().trim();
                lastName = txtLastName.getText().toString().trim();
                age = txtAge.getText().toString().trim();
                sex = "";
                login = txtLogin.getText().toString().trim();
                password = txtPassword.getText().toString().trim();

                if(rdMan.isChecked()){
                    sex += rdMan.getText().toString() + " ";
                }
                if (rdWoman.isChecked()){
                    sex += rdWoman.getText().toString()+ " ";
                }
                //Intent intent = new Intent(InscriptionActivity.this,HomeActivity.class);

                SharedPreferences.Editor editor = sp.edit();
                editor.putString("login",login);
                editor.commit();
                inscription();
                Toast.makeText(InscriptionActivity.this, getString(R.string.success_inscription), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(InscriptionActivity.this,DossierMedical.class);
                startActivity(intent);
            }
        });
    }
    MainActivity main = new MainActivity();
    public void inscription(){
        String url = "http://" + main.address + "/hopital/inscription.php";
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("login",login)
                .add("password",password)
                .add("firstname",firstName)
                .add("lastname",lastName)
                .add("sexe",sex)
                .add("age",age)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                final String message = getString(R.string.error_connexion);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(main, message, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    JSONObject jo = new JSONObject(result);
                    String status = jo.getString("status");
                    if (status.equals("KO")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(InscriptionActivity.this, getString(R.string.error_parameters), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txtLogin.setText("");
                                txtPassword.setText("");
                                txtFirstName.setText("");
                                txtAge.setText("");
                                Toast.makeText(InscriptionActivity.this, getString(R.string.success_inscription), Toast.LENGTH_SHORT).show();
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