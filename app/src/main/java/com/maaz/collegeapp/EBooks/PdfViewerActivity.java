package com.maaz.collegeapp.EBooks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;
import com.maaz.collegeapp.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Stream;

public class PdfViewerActivity extends AppCompatActivity {

    private String url;
    private PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

        url = getIntent().getStringExtra("pdfUrl");

        pdfView = findViewById(R.id.pdfView);

        new pdfDownload().execute(url);

    }

    // AsyncTask is like background thread, it runs in background for download the pdf.
    private class pdfDownload extends AsyncTask<String, Void, InputStream>{

        // it will run in background .
        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;

            try {
                // this strings is basically above url.
                URL url = new URL(strings[0]);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                if (httpURLConnection.getResponseCode() == 200){

                    inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return inputStream;
        }

        // it will show result on Ui. after execute doInBackground.
        @Override
        protected void onPostExecute(InputStream inputStream) {
            // it will load pdf from inputStream.
            pdfView.fromStream(inputStream).load();
        }
    }
}