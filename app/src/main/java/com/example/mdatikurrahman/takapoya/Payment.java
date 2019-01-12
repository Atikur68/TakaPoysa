package com.example.mdatikurrahman.takapoya;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.roger.catloadinglibrary.CatLoadingView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import util.AppConstants;
import util.CustomAnimation;
import util.CustomToast;


/**
 * A simple {@link Fragment} subclass.
 */

public class Payment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener{
    private View view;
    TextView paymentCondition;
    private EditText emailid, password;
    private Button requestButton;
    private LinearLayout loginLayout;
    private CatLoadingView catLoadingView;
    private CustomAnimation mCustomAnimation;
    private FragmentManager fragmentManager;
    private ProgressDialog progressDialog;
    private String emailString = "", passwordString = "",paymentMethodString="";
    private final int left = 0, right = 1;
    String account,mobileno,email;
    Spinner spin;
    String[] paymentMethod={"Mobile Recharge","bKash","Rocket"};
    int flags[] = {R.drawable.recharge, R.drawable.bkash, R.drawable.rocket};

    public Payment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_payment, container, false);

         account = getArguments().getString("account");
        mobileno=getArguments().getString("mobile");
        email=getArguments().getString("email");
        emailid = view.findViewById(R.id.mobileno);
        password = view.findViewById(R.id.amount);
        loginLayout = view.findViewById(R.id.request_layout);
        requestButton = view.findViewById(R.id.requestBtn);

        catLoadingView = new CatLoadingView();
        mCustomAnimation = new CustomAnimation(getContext());

        setCompDraw(emailid, R.drawable.phone, left);
        setCompDraw(password, R.drawable.password, left);
        paymentCondition = view.findViewById(R.id.paymentCondition);

        if(Integer.parseInt(account)>300){
            paymentCondition.setText("You Have "+account+" coins");
            requestButton.setEnabled(true);
        }
        else {

            paymentCondition.setText("You need at least 300 coins for payment request.");
            requestButton.setEnabled(false);
        }

         spin = (Spinner) view.findViewById(R.id.paymentTypeSpinner);
        spin.setOnItemSelectedListener(this);

        CustomAdapter customAdapter=new CustomAdapter(getContext(),flags,paymentMethod);
        spin.setAdapter(customAdapter);

        requestButton.setOnClickListener(this);
        return view;
    }

    private void setCompDraw(EditText v, int img, int side) {
        Drawable mDrawable = ContextCompat.getDrawable(getContext(), img);
        int size = (int) Math.round(v.getLineHeight() * 0.9);
        mDrawable.setBounds(0, 0, size, size);
        if (side == left) v.setCompoundDrawables(mDrawable, null, null, null);
        if (side == right) v.setCompoundDrawables(null, null, mDrawable, null);
    }

    @Override
    public void onClick(View v) {





        boolean checker = true;


        emailString = emailid.getText().toString();

        passwordString = password.getText().toString();



        if (emailString.isEmpty()|| emailString.length()!=11) {
            emailid.setError("Enter Correct Mobile Number");
            checker = false;
        } else {
            emailid.setError(null);
        }



        if (passwordString.isEmpty()) {
            password.setError("Please fill it");
            checker = false;
        } else {
            password.setError(null);
        }


        if (checker) {
            //Do volly operation
            String HttpUrl = "http://timitbd.com/taka/RequestPage.php";
            // Creating Volley RequestQueue.
            RequestQueue requestQueue;
            // Creating Progress dialog.
            // Creating Volley newRequestQueue .
            requestQueue = Volley.newRequestQueue(getActivity());
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Please Wait, We are Checking Your Data on Server");
            progressDialog.show();
           // catLoadingView.setText("SIGNING IN...");
            //catLoadingView.show(fragmentManager, "");
            // Creating string request with post method.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String ServerResponse) {
                            // Hiding the progress dialog after all task complete.
                            progressDialog.dismiss();
                            //catLoadingView.dismiss();
                            // Showing response message coming from server.
                            //Toast.makeText(getActivity(), ServerResponse, Toast.LENGTH_LONG).show();

                            if(ServerResponse.contains("Successful")) {
                                emailid.setText("");
                                password.setText("");
                                Toast.makeText(getActivity(), "Successful. You will get payment within 10 hours...", Toast.LENGTH_LONG).show();

                            }
                            else {
                            }
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

                    params.put("method",paymentMethodString);
                    params.put("mobile", mobileno.trim());
                    params.put("email", email.trim());
                    params.put("requestAmount", passwordString);
                    params.put("paymentRequestNumber", emailString);
                    return params;
                }
            };
            // Creating RequestQueue.
            requestQueue = Volley.newRequestQueue(getActivity());
            // Adding the StringRequest object into requestQueue.
            requestQueue.add(stringRequest);
        } else {
            return;
        }

    }

    private void checkValidation() {
        // Get email id and password
        final String getEmailId = emailid.getText().toString();
        final String getPassword = password.getText().toString();

        // Check for both field is empty or not
        if (getEmailId.equals("") || getEmailId.length() == 0 || getPassword.equals("") || getPassword.length() == 0) {
            mCustomAnimation.shakeAnim(loginLayout);
            new CustomToast().Show_Toast(getActivity(), view, "Enter both credentials.");
        }
        // Check if email id is valid or not

        // Else do login and do your stuff
        else {

        }

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       // Toast.makeText(getContext(), paymentMethod[position], Toast.LENGTH_LONG).show();
        paymentMethodString=paymentMethod[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
