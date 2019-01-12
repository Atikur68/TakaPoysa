package com.example.mdatikurrahman.takapoya;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;


public class Facebook extends Fragment {

    private WebView liveScoreView;
    private ProgressDialog mProgressDialog;
    private View view;
    public Facebook() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_facebook, container, false);
        liveScoreView = view.findViewById(R.id.liveScoreWevView);
        liveScoreView.getSettings().setJavaScriptEnabled(false);
        liveScoreView.setBackgroundColor(0x00000000);
        liveScoreView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView viewx, String urlx) {
                viewx.loadUrl(urlx);
                return false;
            }
        });
        new LoadData().execute();



         return view;
    }
    private class LoadData extends AsyncTask<Void, Void, Void> {
        String html = new String();
        Document doc = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setTitle("Facebook");
            mProgressDialog.setMessage("Loading Facebook Page...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                //https://web.facebook.com/TimiTBD/
                // https://mobile.facebook.com/TimiTBD/?_rdc=1&_rdr
                //http://www.goal.com/en-in/matches
                doc = Jsoup.connect("https://mobile.facebook.com/TimiTBD/?_rdc=1&_rdr").timeout(100000).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //get total document

            // Elements alldivs=doc.select("div.page-container");
            //   Elements alldivs= doc.getElementsByClass("widget-fixtures-and-results");
//            ArrayList<String> list=new ArrayList<String>();
//
//            for(org.jsoup.nodes.Element e: alldivs)
//            {
//                if(e.className().equals("main-content"))
//                    list.add(e.id());
//            }
//            //removing all <div> without "div2"
//            for(int i=0;i<list.size();i++)
//            {
//                if(!list.get(i).equals(primeDiv))
//                    doc.select("div[id="+list.get(i)+"]").remove();
//            }
            // doc=Jsoup.parse(alldivs.toString());
            //doc.getElementsByTag("header").remove();
           // doc.getElementsByTag("footer").remove();
           // b c d e
           // sidebarMode tinyViewport tinyHeight
            doc.getElementsByClass("b c d e");

            //  html=alldivs.outerHtml();

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
            liveScoreView.loadDataWithBaseURL(null, doc.html(),
                    "text/html", "utf-8", null);
            mProgressDialog.dismiss();
        }
    }

}
