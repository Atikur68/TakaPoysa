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
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.AppConstants;
import util.CustomAnimation;
import util.CustomToast;

public class LoginFragment extends Fragment implements OnClickListener {
    private View view;

    private EditText emailid, password;
    private Button loginButton;
    private TextView forgotPassword, signUp;
    private CheckBox show_hide_password;
    private LinearLayout loginLayout;
    private CatLoadingView catLoadingView;

    private FragmentManager fragmentManager;
    private ProgressDialog progressDialog;
    private String emailString = "", passwordString = "";
    private TextWatcher textWatcher;
    private CustomAnimation mCustomAnimation;
    Intent intent;
    int sdk = Build.VERSION.SDK_INT;

    private final int left = 0, right = 1;

    public LoginFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_login, container, false);
        initViews();
        setListeners();
        return view;
    }

    // Initiate Views
    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();

        emailid = view.findViewById(R.id.login_emailid);
        password = view.findViewById(R.id.login_password);
        loginButton = view.findViewById(R.id.loginBtn);
        forgotPassword = view.findViewById(R.id.forgot_password);
        signUp = view.findViewById(R.id.createAccount);
        show_hide_password = view.findViewById(R.id.show_hide_password);
        loginLayout = view.findViewById(R.id.login_layout);
        catLoadingView = new CatLoadingView();

        // Load ShakeAnimation
        mCustomAnimation = new CustomAnimation(getContext());

        setCompDraw(emailid, R.drawable.user, left);
        setCompDraw(password, R.drawable.password, left);

        //checkSessionValidity();
    }

    private void setCompDraw(EditText v, int img, int side) {
        Drawable mDrawable = ContextCompat.getDrawable(getContext(), img);
        int size = (int) Math.round(v.getLineHeight() * 0.9);
        mDrawable.setBounds(0, 0, size, size);
        if (side == left) v.setCompoundDrawables(mDrawable, null, null, null);
        if (side == right) v.setCompoundDrawables(null, null, mDrawable, null);
    }

    private void setCompDrawErr(EditText v, int imgLeft, int imgRight) {
        Drawable mDrawableLeft = ContextCompat.getDrawable(getContext(), imgLeft);
        Drawable mDrawableRight = ContextCompat.getDrawable(getContext(), imgRight);
        int size = (int) Math.round(v.getLineHeight() * 0.9);
        mDrawableLeft.setBounds(0, 0, size, size);
        mDrawableRight.setBounds(0, 0, size, size);
        DrawableCompat.setTint(mDrawableRight, ContextCompat.getColor(getContext(), R.color.colorAccent));
        v.setCompoundDrawables(mDrawableLeft, null, mDrawableRight, null);
    }

    // Set Listeners
    private void setListeners() {
        loginButton.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        signUp.setOnClickListener(this);

        // Set check listener over checkbox for showing and hiding password
        show_hide_password.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton button,
                                         boolean isChecked) {

                // If it is checkec then show password else hide
                // password
                if (isChecked) {

                    show_hide_password.setText(R.string.hide_pwd);// change
                    // checkbox
                    // text
                    password.setInputType(InputType.TYPE_CLASS_TEXT);
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());// show password
                } else {
                    show_hide_password.setText(R.string.show_pwd);// change
                    // checkbox
                    // text
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    password.setTransformationMethod(PasswordTransformationMethod
                            .getInstance());// hide password

                }

            }
        });



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                checkValidation();
                break;
            case R.id.forgot_password:
                // Replace forgot password fragment with animation
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.left_enter, R.anim.zoom_out)
                        .replace(R.id.frameContainer, new ForgotPasswordFragment(), AppConstants.ForgotPassword_Fragment).commit();
                break;
            case R.id.createAccount:
                // Replace signup frgament with animation
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.bottom_enter, R.anim.zoom_out)
                        .replace(R.id.frameContainer, new SignUpFragment(), AppConstants.SignUp_Fragment).commit();
                break;
        }

        boolean checker = true;


        emailString = emailid.getText().toString();

        passwordString = password.getText().toString();



        if (emailString.isEmpty()) {
            emailid.setError("enter a correct email address or Mobile Number");
            checker = false;
        } else {
            emailid.setError(null);
        }



        if (passwordString.isEmpty() || passwordString.length() < 4 || passwordString.length() > 10) {
            password.setError("More than 4 alphanumeric characters");
            checker = false;
        } else {
            password.setError(null);
        }


        if (checker) {
            //Do volly operation
            String HttpUrl = "http://timitbd.com/taka/Login.php";
            // Creating Volley RequestQueue.
            RequestQueue requestQueue;
            // Creating Progress dialog.
            // Creating Volley newRequestQueue .
            requestQueue = Volley.newRequestQueue(getActivity());
            progressDialog = new ProgressDialog(getActivity());
            //progressDialog.setMessage("Please Wait, We are Checking Your Data on Server");
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
                            //Toast.makeText(getActivity(), ServerResponse, Toast.LENGTH_LONG).show();

                           if(ServerResponse.contains("userall")) {
                                emailid.setText("");
                                password.setText("");

                               //Bundle bnd=new Bundle();
                               //bnd.putString("email",emailString);
                               //BlankFragment blankf=new BlankFragment();
                               //blankf.setArguments(bnd);
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
                               Toast.makeText(getActivity(), "Wrong Email,Mobile Number or Password. Try Again.", Toast.LENGTH_LONG).show();
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

                    params.put("mobile", emailString.trim());
                    params.put("password", passwordString.trim());
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




    // Check Validation before login
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






}
