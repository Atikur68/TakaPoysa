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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.AppConstants;
import util.CustomAnimation;
import util.CustomToast;


public class ForgotPasswordFragment extends Fragment implements OnClickListener {
    private static View view;
    private ProgressDialog progressDialog;
    private static EditText emailId;
    private static TextView submit, back, fortxt;
    private String emailString = "";
    private CatLoadingView catLoadingView;
    private FragmentManager fragmentManager;
    public ForgotPasswordFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_forgotpassword, container, false);
        initViews();
        setListeners();
        return view;
    }

    // Initialize the views
    private void initViews() {
        emailId = view.findViewById(R.id.registered_emailid);
        submit = view.findViewById(R.id.forgot_button);
        back = view.findViewById(R.id.backToLoginBtn1);
        fortxt = view.findViewById(R.id.forgettext);
        setCompDraw(emailId, R.drawable.email);
    }

    private void setCompDraw(EditText v, int img) {
        Drawable mDrawable = ContextCompat.getDrawable(getContext(), img);
        int size = (int) Math.round(v.getLineHeight() * 0.9);
        mDrawable.setBounds(0, 0, size, size);
        v.setCompoundDrawables(mDrawable, null, null, null);
    }

    // Set Listeners over buttons
    private void setListeners() {
        back.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backToLoginBtn1:
                // Replace Login Fragment on Back Presses
                // new WelcomeActivity().replaceForgotPasswordFragment();
                new WelcomeActivity().replaceLoginFragment();
                break;
        }

        boolean checker = true;

        emailString = emailId.getText().toString();

        if (emailString.isEmpty()) {
            emailId.setError("enter a correct email address or Mobile Number");
            checker = false;
        } else {
            emailId.setError(null);
        }

        if (checker) {
            //Do volly operation
            String HttpUrl = "http://timitbd.com/taka/Forgot.php";
            // Creating Volley RequestQueue.
            RequestQueue requestQueue;
            // Creating Progress dialog.
            // Creating Volley newRequestQueue .
            requestQueue = Volley.newRequestQueue(getActivity());
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Please Wait, We are Checking Your Data on Server");
            progressDialog.show();

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

                            if (ServerResponse.contains("userall")) {
                                emailId.setText("");
                                 fortxt.setText("We will contact with you within two days. You have to answer some questions for verification.");
                            } else {
                                fortxt.setText("");
                                Toast.makeText(getActivity(), "Wrong Email or Mobile Number. Try Again.", Toast.LENGTH_LONG).show();
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

                    params.put("mobile", emailString);

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

    private void submitButtonTask() {
        String getEmailId = emailId.getText().toString();
        // Pattern for email id validation
        Pattern p = Pattern.compile(AppConstants.EmailRegEx);
        // Match the pattern
        Matcher m = p.matcher(getEmailId);
        // First check if email id is not null else show error toast
        if (getEmailId.equals("") || getEmailId.length() == 0)
            new CustomToast().Show_Toast(getActivity(), view, "Please enter your Email Id.");
            // Check if email id is valid or not
        else if (!m.find())
            new CustomToast().Show_Toast(getActivity(), view, "Your Email Id is Invalid.");
            // Else submit email id and fetch passwod or do your stuff
        else
            Toast.makeText(getActivity(), "Get Forgot Password.", Toast.LENGTH_SHORT).show();
    }
}