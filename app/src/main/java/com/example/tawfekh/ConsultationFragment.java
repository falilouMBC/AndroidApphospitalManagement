package com.example.tawfekh;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class ConsultationFragment extends Fragment {

    MainActivity main = new MainActivity();
    private TextView tvConsult;
    private String consults;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_consultation, container, false);
        tvConsult = view.findViewById(R.id.tvConsult);


        consult();

        return view;
    }

    public void consult(){
        String url = "http://" + main.address + "/hopital/consultation.php?login=" + MainActivity.login;
        OkHttpClient client = new OkHttpClient();
        consults = "";

        Request request =new Request.Builder()
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
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvConsult.setText(consults);
                        }
                    });
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}