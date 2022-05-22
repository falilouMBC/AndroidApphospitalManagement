package com.example.tawfekh;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class HelpFragment extends Fragment {

    private TextView tvInfo;
    private Button btnCall;
    private String infos;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_help, container, false);
        btnCall = view.findViewById(R.id.btnCall);
        tvInfo = view.findViewById(R.id.tvInfo);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:786098248"));
                startActivity(intent);
            }
        });
        aide();
        return view;
    }

    MainActivity main = new MainActivity();
    public void aide(){
        String url = "http://" + main.address + "/hopital/user.php?login=" + MainActivity.login;
        infos = "";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
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
                    JSONObject jo = new JSONObject(result);
                    JSONArray ja = jo.getJSONArray("user");
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject element = ja.getJSONObject(i);
                        String nom = element.getString("lastname");
                        String prenom = element.getString("firstname");
                        infos += "Salam !!! " + prenom + " " + nom + " C'est Serigne Ndiambé pour vous servir" + "\n\n" + "Cliquez sur ce bouton pour que je puisse vous aider";
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvInfo.setText(infos);
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}