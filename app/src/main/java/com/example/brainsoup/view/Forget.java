package com.example.brainsoup.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brainsoup.R;
import com.example.brainsoup.model.ForgetModel;
import com.example.brainsoup.model.LoginModel;
import com.example.brainsoup.model.MyURL;
import com.example.brainsoup.service.ForgetAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Path;

public class Forget extends AppCompatActivity {
TextView signin2;
    TextView email;
private ImageView img;
    ArrayList<ForgetModel> forgetModels;
    Retrofit retrofit;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        img = (ImageView)findViewById(R.id.logo3);

        email=(TextView)findViewById(R.id.email3);

        signin2 = (TextView) findViewById(R.id.homeLink);
        img.setImageResource(R.drawable.logo);
        String signinTxt="Giriş Yapmak İçin Tıklayınız";
        SpannableString s1=new SpannableString(signinTxt);

        ClickableSpan clickableSpan1=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent myIntent = new Intent(Forget.this,MainActivity.class);
                startActivity(myIntent);
            }
        };

        s1.setSpan(clickableSpan1,18,28, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        signin2.setText(s1);
        signin2.setMovementMethod(LinkMovementMethod.getInstance());
        Gson gson=new GsonBuilder().setLenient().create();
        retrofit=new Retrofit.Builder()
                .baseUrl(MyURL.getBASE_URL())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


    }
public  void SendMail(View view)
{

    email=(TextView)findViewById(R.id.email3);
    String text="forgetAPI?email="+email.getText().toString();
    if(Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches())
        goForget(text);
    else
        Toast.makeText(this, "Lütfen Geçerli Bir Mail Adresi Giriniz", Toast.LENGTH_SHORT).show();



}
    private void goForget(String email)
    {
        progressDialog = new ProgressDialog(Forget.this);
        progressDialog.setMessage("Lütfen Bekleyiniz");
        progressDialog.show();
        ForgetAPI forgetAPI=retrofit.create(ForgetAPI.class);
        Call<List<ForgetModel>> call= forgetAPI.getData(email);

       call.enqueue(new Callback<List<ForgetModel>>() {

            @Override
            public void onResponse(Call<List<ForgetModel>> call, Response<List<ForgetModel>> response) {
              if(response.isSuccessful())
              {

                List<ForgetModel> responseList=response.body();
                forgetModels=new ArrayList<>(responseList);
                String myresult=forgetModels.get(0).result;

                  Toast.makeText(Forget.this, myresult, Toast.LENGTH_SHORT).show();
                  progressDialog.dismiss();
              }

            }

            @Override
            public void onFailure(Call<List<ForgetModel>> call, Throwable t) {
            t.printStackTrace();
            progressDialog.dismiss();
            }
        });
    }
}