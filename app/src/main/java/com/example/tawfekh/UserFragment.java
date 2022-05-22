package com.example.tawfekh;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserFragment extends Fragment {

    MainActivity main = new MainActivity();
    private EditText txtFirstName, txtLastName, txtAge, txtLogin, txtPassword;
    private RadioButton rdGender, rdMan, rdWoman;
    private Button btnSign;
    private String firstName, lastName, age, login, password, sex;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        rdMan = view.findViewById(R.id.rdMan);
        rdWoman = view.findViewById(R.id.rdwoman);
        txtLogin = view.findViewById(R.id.txtLoginIns);
        txtPassword = view.findViewById(R.id.txtPwdIns);
        txtFirstName = view.findViewById(R.id.txtFirstName);
        txtLastName = view.findViewById(R.id.txtLastName);
        txtAge = view.findViewById(R.id.txtAge);
        btnSign = view.findViewById(R.id.btnSign);

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
                inscription();

            }
        });

        return view;

    }

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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), getString(R.string.error_connexion), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    JSONObject jo = new JSONObject();
                    JSONArray ja = jo.getJSONArray(result);
                    for (int i = 0; i < ja.length(); i++) {

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}