package com.example.mdatikurrahman.takapoya;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionInflater;




import util.CustomAnimation;
import util.AppConstants;

public class WelcomeActivity extends AppCompatActivity {

    private CustomAnimation customAnimation;

    private static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setSharedElementEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.shared_element_transation));
        }
        customAnimation = new CustomAnimation(this);
        fragmentManager = getSupportFragmentManager();

        // If savedinstnacestate is null then replace login fragment
        if (savedInstanceState == null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frameContainer, new LoginFragment(), AppConstants.Login_Fragment).commit();
        }

        if(getIntent().getBooleanExtra("EXIT", false)) {
            Intent intent = new Intent(WelcomeActivity.this, SplashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);
            finish();
        }
    }

    // Replace Login Fragment with animation
    public void replaceLoginFragment() {
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.zoom_in, R.anim.bottom_out)
                .replace(R.id.frameContainer, new LoginFragment(), AppConstants.Login_Fragment).commit();
    }

    // Replace Login Fragment with animation
    public void replaceForgotPasswordFragment() {
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.zoom_in, R.anim.left_out)
                .replace(R.id.frameContainer, new ForgotPasswordFragment(), AppConstants.ForgotPassword_Fragment).commit();
    }

    // Replace Login Fragment with animation
    public void replaceSoulmatesFragment() {
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.zoom_in, R.anim.right_out)
                .replace(R.id.frameContainer, new LoginFragment(),
                        AppConstants.Login_Fragment).commit();
    }

    @Override
    public void onBackPressed() {

        // Find the tag of signup and forgot password fragment
        Fragment SignUp_Fragment = fragmentManager.findFragmentByTag(AppConstants.SignUp_Fragment);
        Fragment ForgotPassword_Fragment = fragmentManager.findFragmentByTag(AppConstants.ForgotPassword_Fragment);
        Fragment Soulmates_Fragment = fragmentManager.findFragmentByTag(AppConstants.Search_Soulmates_Fragment);

        // Check if both are null or not
        // If both are not null then replace login fragment else do backpressed
        // task

        if (SignUp_Fragment != null) replaceLoginFragment();
        else if (ForgotPassword_Fragment != null) replaceForgotPasswordFragment();
        else if (Soulmates_Fragment != null) replaceSoulmatesFragment();
        else {
            Intent intent = new Intent(WelcomeActivity.this, SplashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);
            finish();
            //super.onBackPressed();
        }
    }
}