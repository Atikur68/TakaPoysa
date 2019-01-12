package com.example.mdatikurrahman.takapoya;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class BlankFragment extends Fragment implements View.OnClickListener{

    public BlankFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final Context cta=new ContextThemeWrapper(getActivity(),R.style.LoginSignUp1Input);
        LayoutInflater localInflater=inflater.cloneInContext(cta);



        return localInflater.inflate(R.layout.fragment_blank, null, false);

    }

    @Override
    public void onClick(View v) {

    }


    // TODO: Rename method, update argument and hook method into UI event










}
