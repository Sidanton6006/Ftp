package com.example.ftpserver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Base64;

import com.example.ftpserver.API.IFtpApi;
import com.example.ftpserver.API.Image;
import com.example.ftpserver.API.NetworkService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ImageView iv;
    private String base64Image;
    private TextView tv;
    private EditText et;

    int SELECT_PICTURE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = findViewById(R.id.imageView);
        tv = findViewById(R.id.textView3);
    }

    public void handleClick(View view){
        NetworkService.getInstance()
                .getFtpApi()
                .uploadImage(base64Image)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        tv.setText(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        tv.setText("Error"+t.getMessage());
                        System.out.println(t.toString());
                    }
                });

//        Gson gson = new GsonBuilder().setLenient().create();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://10.0.2.2:7278")
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .build();
//        IFtpApi ftpApi = retrofit.create(IFtpApi.class);
//        Image image = new Image(base64Image);
//        Call<Image> call = ftpApi.uploadImage(image);
//        call.enqueue(new Callback<Image>() {
//            @Override
//            public void onResponse(Call<Image> call, Response<Image> response) {
//                tv.setText(response.body().toString());
//            }
//
//            @Override
//            public void onFailure(Call<Image> call, Throwable t) {
//                tv.setText("Error"+t.getMessage());
//                System.out.println(t.toString());
//            }
//        });
    }

    public void pick(View view) {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(i,"Select Picture"),SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode == SELECT_PICTURE){
                base64Image = getBase64FromImage(data.getData());
                iv.setImageURI(data.getData());
            }
        }
    }

    private String getBase64FromImage(Uri uri){
        Bitmap bitmap=null;
        try {
            bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
        } catch (IOException e){
            e.printStackTrace();
        }
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] bytes=stream.toByteArray();
        return Base64.encodeToString(bytes,Base64.DEFAULT);
    }
}