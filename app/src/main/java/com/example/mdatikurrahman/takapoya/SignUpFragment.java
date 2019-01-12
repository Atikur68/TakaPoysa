package com.example.mdatikurrahman.takapoya;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class SignUpFragment extends Fragment implements OnClickListener {

    private View view;
    private EditText fullName, emailId, mobileNumber, address, password, confirmPassword,refcode;
    private TextView login;
    private Button signUpButton;
    private CheckBox terms_conditions;
    private CatLoadingView catLoadingView;
    private CustomAnimation mCustomAnimation;
    private LinearLayout mLinearLayout;
    private String nameStrng = "", mobilenameStrng = "", addressnameStrng = "", emailString = "", passwordString = "", repasswordString = "",refCodeStr="";
    private TextWatcher textWatcher;
    private FragmentManager fragmentManager;
    private ProgressDialog progressDialog;
    int sdk = Build.VERSION.SDK_INT;

    private String strName,strAddress;
    private String strEmail;
    private String strPhone;
    private String strPassword;
    private String strConfirmPassword;
Intent intent;
    public SignUpFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_signup, container, false);
        initViews();
        setListeners();
        return view;
    }

    // Initialize all views
    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();

        fullName = view.findViewById(R.id.fullName);
        emailId = view.findViewById(R.id.userEmailId);
        mobileNumber = view.findViewById(R.id.mobileNumber);
        password = view.findViewById(R.id.password);
        confirmPassword = view.findViewById(R.id.confirmPassword);
        signUpButton = view.findViewById(R.id.signUpBtn);
        login = view.findViewById(R.id.already_user);
        terms_conditions = view.findViewById(R.id.terms_conditions);
        catLoadingView = new CatLoadingView();
        address = view.findViewById(R.id.address);
        refcode=view.findViewById(R.id.referredCode);
        mLinearLayout = view.findViewById(R.id.layout_content);
        // Load ShakeAnimation
        mCustomAnimation = new CustomAnimation(getContext());

        setCompDraw(fullName, R.drawable.user);
        setCompDraw(emailId, R.drawable.email);
        setCompDraw(mobileNumber, R.drawable.phone);
        setCompDraw(address,R.drawable.user);
        setCompDraw(password, R.drawable.password);
        setCompDraw(confirmPassword, R.drawable.confirm_password);
        setCompDraw(refcode,R.drawable.confirm_password);

    }

    private void setCompDraw(EditText v, int img) {
        Drawable mDrawable = ContextCompat.getDrawable(getContext(), img);
        int size = (int) Math.round(v.getLineHeight() * 0.9);
        mDrawable.setBounds(0, 0, size, size);
        v.setCompoundDrawables(mDrawable, null, null, null);
    }

    private void setCompDrawErr(EditText v, int imgLeft, int imgRight) {
        Drawable mDrawableLeft = ContextCompat.getDrawable(getContext(), imgLeft);
        Drawable mDrawableRight = ContextCompat.getDrawable(getContext(), imgRight);
        int size = (int) Math.round(v.getLineHeight() * 0.9);
        mDrawableLeft.setBounds(0, 0, size, size);
        mDrawableRight.setBounds(0, 0, size, size);
        DrawableCompat.setTint(mDrawableRight, ContextCompat.getColor(getContext(), R.color.red));
        v.setCompoundDrawables(mDrawableLeft, null, mDrawableRight, null);
    }

    // Set Listeners
    private void setListeners() {
        signUpButton.setOnClickListener(this);
        login.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.already_user:
                // Replace login fragment
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.bottom_enter, R.anim.zoom_out)
                        .replace(R.id.frameContainer, new LoginFragment(), AppConstants.Login_Fragment).commit();
                break;
        }


        boolean checker = true;

        nameStrng = fullName.getText().toString();
        addressnameStrng = address.getText().toString();
        emailString = emailId.getText().toString();
        mobilenameStrng = mobileNumber.getText().toString();
        passwordString = password.getText().toString();
        repasswordString = confirmPassword.getText().toString();
        refCodeStr=refcode.getText().toString();
        if (nameStrng.isEmpty() || nameStrng.length() < 3) {
            fullName.setError("at least 3 characters");
            checker = false;
        } else {
            fullName.setError(null);
        }

        if (addressnameStrng.isEmpty()) {
            address.setError("Enter Valid Address");
            checker = false;
        } else {
            address.setError(null);
        }

        if (mobilenameStrng.isEmpty() || mobilenameStrng.length()!=11) {
            mobileNumber.setError("Enter Valid Mobile Number");
            checker = false;
        } else {
            mobileNumber.setError(null);
        }

        if (passwordString.isEmpty() || passwordString.length() < 4 ) {
            password.setError("More than 4 characters");
            checker = false;
        } else {
            password.setError(null);
        }

        if (repasswordString.isEmpty() || repasswordString.length() < 4 || repasswordString.length() > 10 || !(repasswordString.equals(passwordString))) {
            confirmPassword.setError("Password Do not match");
            checker = false;
        } else {
            confirmPassword.setError(null);
        }
        if (checker) {
            //Do volly operation
            String HttpUrl = "http://timitbd.com/taka/DbConnect.php";
            // Creating Volley RequestQueue.
            RequestQueue requestQueue;
            // Creating Progress dialog.
            // Creating Volley newRequestQueue .
            requestQueue = Volley.newRequestQueue(getActivity());
           // progressDialog = new ProgressDialog(getActivity());
           // progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
            //progressDialog.show();
            catLoadingView.setText("SIGNING IN...");
            catLoadingView.show(fragmentManager, "");
            // Creating string request with post method.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String ServerResponse) {
                            // Hiding the progress dialog after all task complete.
                            //progressDialog.dismiss();
                            catLoadingView.dismiss();
                            // Showing response message coming from server.

                            //Clear Field
                           // String serverMessage="Registration Successfull";
                            if(ServerResponse.contains("userall")) {
                                fullName.setText("");
                                mobileNumber.setText("");
                                address.setText("");
                                emailId.setText("");
                                password.setText("");
                                confirmPassword.setText("");
                                refcode.setText("");

                                try {
                                    JSONObject obj = new JSONObject(ServerResponse);

                                    JSONArray heroArray = obj.getJSONArray("userall");

                                    for (int i = 0; i < heroArray.length(); i++) {
                                        JSONObject heroObject = heroArray.getJSONObject(i);
                                        //youtubeVideos.add( new YouTubeVideos("<iframe width='100%' height=\"100%\" src=\""+heroObject.getString("vido")+"\" allowfullscreen=\"allowfullscreen\" mozallowfullscreen=\"mozallowfullscreen\" msallowfullscreen=\"msallowfullscreen\" oallowfullscreen=\"oallowfullscreen\" webkitallowfullscreen=\"webkitallowfullscreen\" frameborder=\"0\" ></iframe>"));
                                        //  paths[i] = heroObject.getString("match");
                                        intent=new Intent(getActivity(),MainActivity.class);
                                        intent.putExtra("username",heroObject.getString("username"));
                                        intent.putExtra("email",heroObject.getString("email"));
                                        intent.putExtra("mobile",heroObject.getString("mobile"));
                                        intent.putExtra("account",heroObject.getString("account"));
                                        intent.putExtra("refcode",heroObject.getString("id"));




                                    }
                                    getActivity().startActivity(intent);
                                } catch (JSONException e) {
                                    // pDialog.hide();
                                    e.printStackTrace();
                                }
                            }
                            else {
                                Toast.makeText(getActivity(), "User has already Registered. Try another Email.", Toast.LENGTH_LONG).show();
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
                    params.put("name", nameStrng);
                    params.put("mobile", mobilenameStrng.trim());
                    params.put("address", addressnameStrng);
                    params.put("email", emailString.trim());
                    params.put("password", passwordString);
                    params.put("refCode",refCodeStr);
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



    // Check Validation Method
    private void checkValidation() {
        // Get all edittext texts
        strName = fullName.getText().toString();
        strEmail = emailId.getText().toString();
        strPhone = mobileNumber.getText().toString();
        strAddress=address.getText().toString();
        strPassword = password.getText().toString();
        strConfirmPassword = confirmPassword.getText().toString();

        // Check if all strings are null or not
        if (strName.equals("") || strName.length() == 0

                || strPhone.equals("") || strPhone.length() == 0
                || strAddress.equals("")|| strAddress.length()==0
                || strPassword.equals("") || strPassword.length() == 0
                || strConfirmPassword.equals("") || strConfirmPassword.length() == 0) {

            mCustomAnimation.shakeAnim(mLinearLayout);
            new CustomToast().Show_Toast(getActivity(), view, "All fields are required. Email and Referred Code are Optional.");

            // Check if email id valid or not
        }

        // Check if both password should be equal
        else if (!strConfirmPassword.equals(strPassword))
            new CustomToast().Show_Toast(getActivity(), view, "Both password doesn't match.");

            // Make sure user should check Terms and Conditions checkbox
        else if (!terms_conditions.isChecked())
            new CustomToast().Show_Toast(getActivity(), view, "Please select Terms and Conditions.");

            // Else do signup or do your stuff
        else {
            // do something
        }
    }
}
