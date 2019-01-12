package com.example.mdatikurrahman.takapoya;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class Reffered extends Fragment {
    private View view;
    RecyclerView recyclerView;
    AdapterReffered adapter;
    SharedPreferences sp;
    List<Reffered_list> matchList=new ArrayList<>();
    private ProgressDialog progressDialog;
    String mobileno;


    public Reffered() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_reffered, container, false);
        recyclerView = view.findViewById(R.id.recyclerview1);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mobileno=getArguments().getString("mobile");
       //matchList.add(new Reffered_list(1,"Hello"));
        fetchingData(view);
       // loadMatchse(view);




        return view;

    }

    private void fetchingData(View view) {
        String HttpUrl = "http://timitbd.com/taka/RefferedPage.php";
         RequestQueue requestQueue;

        requestQueue = Volley.newRequestQueue(getActivity());
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait, We are Checking Your Data on Server");
        progressDialog.show();

         StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        try {

                            JSONObject obj = new JSONObject(ServerResponse);

                            JSONArray heroArray = obj.getJSONArray("userall");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < heroArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject heroObject = heroArray.getJSONObject(i);

                                Reffered_list rfl=new Reffered_list(i+1,heroObject.getString("username"));
                                matchList.add(rfl);


                            }
                            adapter = new AdapterReffered(getContext(),matchList);
                            recyclerView.setAdapter(adapter);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        // Showing error message if something goes wrong.
                        Toast.makeText(getActivity(), volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                // Adding All values to Params.


                params.put("mobile", mobileno);

                return params;
            }
        };
        // Creating RequestQueue.
        requestQueue = Volley.newRequestQueue(getActivity());
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }





}
