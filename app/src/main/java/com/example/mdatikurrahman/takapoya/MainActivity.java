package com.example.mdatikurrahman.takapoya;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.roger.catloadinglibrary.CatLoadingView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

   public Bundle emailintent;
    public TextView nameTxt,emailtxt,accounttxt,refcodetxt;
    private CatLoadingView catLoadingView;

    private FragmentManager fragmentManager;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        emailintent=getIntent().getExtras();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView=navigationView.getHeaderView(0);
        nameTxt=headerView.findViewById(R.id.nametext);
        emailtxt=headerView.findViewById(R.id.emailtext);
        accounttxt=headerView.findViewById(R.id.accounttext);
        refcodetxt=headerView.findViewById(R.id.refcodetext);


        nameTxt.setText(emailintent.getString("username"));
        emailtxt.setText(emailintent.getString("email"));
        accounttxt.setText("Account Balance : "+emailintent.getString("account")+" coins");
        refcodetxt.setText("Referrable Code : "+emailintent.getString("mobile"));

        navigationView.setNavigationItemSelectedListener(this);

       displaySelectedScreen(R.id.blank);
    }


    /*
    private void SendingData() {

        String HttpUrl = "http://timitbd.com/taka/Login.php";
        // Creating Volley RequestQueue.
        RequestQueue requestQueue;
        // Creating Progress dialog.
        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(this);
        progressDialog = new ProgressDialog(this);
        //progressDialog.setMessage("Please Wait, We are Checking Your Data on Server");
        //progressDialog.show();
        catLoadingView.setText("SIGNING IN...");
        catLoadingView.show(fragmentManager, "");
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        catLoadingView.dismiss();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        // Showing error message if something goes wrong.
                        //Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                // Adding All values to Params.

                params.put("email", emailintent.getString("email"));

                return params;
            }
        };
        // Creating RequestQueue.
        requestQueue = Volley.newRequestQueue(this);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);


    }
    */


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.blank:
                fragment = new BlankFragment();
                break;
            case R.id.earnFrafment:
                fragment = new InstructionsToEarn();
                break;
            case R.id.facebook:
                fragment = new Facebook();
                break;
            case R.id.payment:
                fragment = new Payment();
                Bundle data = new Bundle();//Use bundle to pass data
                data.putString("account", emailintent.getString("account"));//put string, int, etc in bundle with a key value
                data.putString("mobile",emailintent.getString("mobile"));
                data.putString("email",emailintent.getString("email"));
                fragment.setArguments(data);//Finally set argument bundle to fragment
                break;
            case R.id.statistics:
                fragment = new Statistics();
                break;
            case R.id.referred:
                fragment = new Reffered();
                Bundle mob = new Bundle();//Use bundle to pass data

                mob.putString("mobile",emailintent.getString("mobile"));

                fragment.setArguments(mob);//Finally set argument bundle to fragment
                break;
            case R.id.update:
                fragment = new Update();
                break;
            case R.id.about:
                fragment = new About();


                break;
            case R.id.contact:
                fragment = new Contact();
                break;

        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        displaySelectedScreen(item.getItemId());
        //make this method blank
        return true;
    }
}
