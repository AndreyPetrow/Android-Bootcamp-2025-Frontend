package com.example.myapplication.utils;

import static com.example.myapplication.core.UrlConstants.DOWNLOAD_URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.material.imageview.ShapeableImageView;

import java.io.InputStream;
import java.net.URL;

public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
    ImageView imageView;

    public DownloadImage(ShapeableImageView imageView) {
        this.imageView = imageView;
    }

    public DownloadImage(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... imageUrls) {
        String imageUrl = imageUrls[0];

        try {
            InputStream inputStream = new URL(DOWNLOAD_URL + imageUrl).openStream();
            return BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            Log.e("Test", "(DownloadImage) При скачивании изображения произошла ошибка: " + e);
            return null;
        }
    }

    protected void onPostExecute(Bitmap result) {
        Log.d("Test", "(DownloadImage) Устанавливаем изображение в imageView (" + imageView.getId() + ")");
        imageView.setImageBitmap(result);
    }// todo: Сделать статические методы по скачиванию.
}