package com.example.mdatikurrahman.takapoya;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class InstructionsToEarn extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final Context cta=new ContextThemeWrapper(getActivity(),R.style.LoginSignUp1Input);
        LayoutInflater localInflater=inflater.cloneInContext(cta);



        return localInflater.inflate(R.layout.fragment_instructions_to_earn, null, false);
    }


}
