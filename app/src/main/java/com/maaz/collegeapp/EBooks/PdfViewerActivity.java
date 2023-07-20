package com.maaz.collegeapp.EBooks;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;
import com.maaz.collegeapp.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Stream;

public class PdfViewerActivity extends AppCompatActivity {

    private String url;
    private PDFView pdfView;

    private ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

        url = getIntent().getStringExtra("pdfUrl");

        pdfView = findViewById(R.id.pdfView);
        progressBar = findViewById(R.id.pdfProgressBar);

        loadPDF(url);

        // new pdfDownload().execute(url);

    }

    // this code will load pdf in APP.
    private void loadPDF(String url) {
        FileLoader.with(this)
                .load(url) //2nd parameter is optioal, pass true to force load from network
                .fromDirectory("test4", FileLoader.DIR_INTERNAL)
                .asFile(new FileRequestListener<File>() {
                    @Override
                    public void onLoad(FileLoadRequest request, FileResponse<File> response) {
                        progressBar.setVisibility(View.GONE);
                        File loadedFile = response.getBody();

                        pdfView.fromFile(loadedFile)  // lot of functionality available here.
                                .password(null)
                                .defaultPage(0)
                                .enableSwipe(true)
                                .enableDoubletap(true)
                                .load();
                    }

                    @Override
                    public void onError(FileLoadRequest request, Throwable t) {
                        Toast.makeText(PdfViewerActivity.this, "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }



    // This Code was for Downloading Pdf and Then Load.

    // AsyncTask is like background thread, it runs in background for download the pdf.
    // then after download we will show in PdfViewer.
    /*
    private class pdfDownload extends AsyncTask<String, Void, InputStream> {

        // it will run in background.
        @Override
        protected InputStream doInBackground(String... strings) { // this strings is url
            InputStream inputStream = null;

            try {
                // this strings is basically above url.
                URL url = new URL(strings[0]);

                // making connection
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                if (httpURLConnection.getResponseCode() == 200) {

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
            // it will load pdf from inputStream after downloading.
            pdfView.fromStream(inputStream).load();
        }

    }
     */


}
