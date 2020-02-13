package com.example.vcc_khoamanhinh2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DetailsImageActivity extends AppCompatActivity {

    String showImage, downImage;
    private RelativeLayout downloadImage;
    private TextView tvPhantram;

    FragmentItemImage fragmentItem;
    AdapterViewPagerImage adapterViewPagerImage;
    ArrayList<Image>  arrImage;

    private ViewPager mViewPager;
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_image);
        getLink();
        initView();

        addListener();

       downloadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dowloadImage();
                finish();
            }
        });
    }


    private void addListener(){
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });
    }

    private void getLink() {
        showImage = getIntent().getStringExtra("show");
        downImage = getIntent().getStringExtra("down");
        arrImage = (ArrayList<Image>) getIntent().getSerializableExtra("arrList");
        position = getIntent().getIntExtra("position", 0);
    }

    private void dowloadImage(){
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(showImage);
        Toast.makeText(this, "Download success", Toast.LENGTH_SHORT).show();
    }

    private void initView() {
        downloadImage = findViewById(R.id.downloadImage);
        tvPhantram = findViewById(R.id.tv_phantram);

        mViewPager = findViewById(R.id.viewPagerImage);
        fragmentItem = new FragmentItemImage(showImage);
        adapterViewPagerImage = new AdapterViewPagerImage(getSupportFragmentManager(), arrImage);
        mViewPager.setAdapter(adapterViewPagerImage);
        mViewPager.setCurrentItem(position);
    }


    class DownloadTask extends AsyncTask<String, Integer, String> {
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(DetailsImageActivity.this);
            dialog.setMessage("Downloading ... ");
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setMax(100);
            dialog.setProgress(0);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String path = strings[0];
            int file_length = 0;
            try {
                URL url = new URL(path);
                URLConnection urlConnection = url.openConnection();
                urlConnection.connect();
                file_length = urlConnection.getContentLength();
                File new_folder = new File("sdcard/KhoaManHinh");
                if (!new_folder.exists()) {
                    new_folder.mkdir();
                }

                File input_file = new File(new_folder, "Image_download" + UUID.randomUUID().toString() + ".jpg");
                InputStream inputStream = new BufferedInputStream(url.openStream(), 8129);
                byte[] data = new byte[1024];
                int total = 0;
                int count = 0;
                OutputStream outputStream = new FileOutputStream(input_file);
                while ((count = inputStream.read(data)) != -1) {
                    total += count;
                    outputStream.write(data, 0, count);
                    int progress = total * 100 / file_length;
                    publishProgress(progress);
                }
                inputStream.close();
                outputStream.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Download complete";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            dialog.setProgress(values[0]);
            tvPhantram.setText(values[0]+"%");
        }

        @Override
        protected void onPostExecute(String aVoid) {
            dialog.hide();
            tvPhantram.setText("");
        }
    }
}
