package com.example.tawfekh;

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

public class FolderFragment extends Fragment {
    private TextView tvDossier;
    private String dossiers;
    MainActivity main = new MainActivity();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_folder, container, false);

        tvDossier = view.findViewById(R.id.tvDossier);

        getFolder();

        return view;
    }

    public void getFolder() {
        String url = "http://" + main.address + "/hopital/dossier.php?login=" + MainActivity.login;
        OkHttpClient client = new OkHttpClient();
        dossiers = "";
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(main, getString(R.string.error_connexion), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    JSONObject jo = new JSONObject(result);
                    JSONArray ja = jo.getJSONArray("dossier");
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject element =ja.getJSONObject(i);
                        String diagnostic = element.getString("diagnostic");
                        String service = element.getString("service");
                        String medoc = element.getString("medocs");
                        String jour = element.getString("jour");
                        dossiers += "Traitement en cours : " + diagnostic + "\n\n" + "Medicament : " + medoc + "\n\n" + "date dernier rendez-vous : " + jour + "\n\n" + "Service : " + service + "\n\n" + "\n\n\n";
                    }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvDossier.setText(dossiers);
                    }
                });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}