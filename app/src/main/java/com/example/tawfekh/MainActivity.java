package com.example.tawfekh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.OnMapReadyCallback;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity{

    private Button btnConnect;
    private EditText txtLogin, txtPassword;
    private String password;
    public static String login;
    public String address = "192.168.1.7";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnConnect = findViewById(R.id.btnConnect);
        txtLogin = findViewById(R.id.txtLogin);
        txtPassword = findViewById(R.id.txtPassword);

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login =txtLogin.getText().toString().trim();
                password = txtPassword.getText().toString().trim();

                if (login.isEmpty() || password.isEmpty()){
                    String message = getString(R.string.error_field);
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                }else {
                    //Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    //startActivity(intent);
                    if(login.equals("admin") || password.equals("admin")){
                        Intent intent = new Intent(MainActivity.this,UserManagement.class);
                        startActivity(intent);
                    }else{
                        authentification();
                    }

                }
            }
        });

    }

    public void authentification(){
        String url = "http://"+ address + "/hopital/connexion.php?login="+ login + "&password=" + password;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                final String message = getString(R.string.error_connexion);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result =response.body().string();
                    JSONObject jo = new JSONObject(result);
                    String status = jo.getString("status");
                    if(status.equals("KO")){
                        final String message = getString(R.string.error_parameters);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    else{
                        Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                        intent.putExtra("LOGIN",login);
                        startActivity(intent);
                    }

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}