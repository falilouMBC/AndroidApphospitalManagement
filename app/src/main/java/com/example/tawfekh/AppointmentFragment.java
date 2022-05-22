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

public class AppointmentFragment extends Fragment {

    MainActivity main = new MainActivity();
    private TextView tvAppointment;

    private String appointments;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_appointment, container, false);
        tvAppointment = view.findViewById(R.id.tvAppointment);
        appointment();
        return view;

    }

    public void appointment(){
        String url = "http://" + main.address + "/hopital/rendezvous.php?login=" + MainActivity.login;
        appointments = "";
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
                    JSONArray ja = jo.getJSONArray("rendezvous");
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject element = ja.getJSONObject(i);
                        String date = element.getString("dateR");
                        String service = element.getString("service");
                        appointments += "Date : " + date + "\n\n" + "Service : " + service +  "\n\n" + "--------" + "\n\n\n";
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvAppointment.setText(appointments);
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