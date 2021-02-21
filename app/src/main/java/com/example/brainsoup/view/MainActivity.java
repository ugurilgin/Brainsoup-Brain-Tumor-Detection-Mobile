package com.example.brainsoup.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brainsoup.R;
import com.example.brainsoup.model.ForgetModel;
import com.example.brainsoup.model.LoginInfo;
import com.example.brainsoup.model.LoginModel;
import com.example.brainsoup.model.MyURL;
import com.example.brainsoup.service.ForgetAPI;
import com.example.brainsoup.service.LoginAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ImageView img;
    TextView forgot;
    TextView signin;
    EditText email;
    EditText password;
    ArrayList<LoginModel> loginModels;
    Retrofit retrofit;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = (ImageView)findViewById(R.id.logo);
        forgot = (TextView) findViewById(R.id.forgot);
        signin = (TextView) findViewById(R.id.signin);
        img.setImageResource(R.drawable.logo);
        String forgotTxt="Şifremi Unuttum";
        String signinTxt="Hesabınız Yok mu? Hemen Kayıt Olun";
        SpannableString s1=new SpannableString(forgotTxt);
        SpannableString s2=new SpannableString(signinTxt);
        ClickableSpan clickableSpan1=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent myIntent = new Intent(MainActivity.this,Forget.class);
                //myIntent.putExtra("key", value); //Optional parameters
                startActivity(myIntent);
            }
        };
      ClickableSpan clickableSpan2=new ClickableSpan() {
          @Override
          public void onClick(@NonNull View view) {
              Intent myIntent2 = new Intent(MainActivity.this,SignUp.class);
              startActivity(myIntent2);
          }
      };
        s1.setSpan(clickableSpan1,0,15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        s2.setSpan(clickableSpan2,24,34, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        forgot.setText(s1);
        forgot.setMovementMethod(LinkMovementMethod.getInstance());

        signin.setText(s2);
        signin.setMovementMethod(LinkMovementMethod.getInstance());

        Gson gson=new GsonBuilder().setLenient().create();
        retrofit=new Retrofit.Builder()
                .baseUrl(MyURL.getBASE_URL())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }


    public  void Submit(View view)
    {
        Log.d("host adı",MyURL.getBASE_URL());
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        if(TextUtils.isEmpty(email.getText().toString() )  ||TextUtils.isEmpty(password.getText().toString()) )
        {
            Toast.makeText(MainActivity.this, "Lütfen Tüm Alanları Doldurunuz", Toast.LENGTH_SHORT).show();

        }
        else {
            if(Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches())
            goLogin(email.getText().toString(), password.getText().toString());
            else
                Toast.makeText(MainActivity.this, "Lütfen Geçerli Bir Mail Adresi Giriniz", Toast.LENGTH_SHORT).show();

        }
    }

    private void goLogin(String email,String password)
    {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Lütfen Bekleyiniz");
        progressDialog.show();
        LoginAPI loginAPI=retrofit.create(LoginAPI.class);
        LoginInfo user=new LoginInfo(email,password);

        Call<List<LoginModel>> call =loginAPI.toLogin(user);
        call.enqueue(new Callback<List<LoginModel>>() {

            @Override
            public void onResponse(Call<List<LoginModel>> call, Response<List<LoginModel>> response) {

                if (response.isSuccessful()) {

                    List<LoginModel> responseList = response.body();
                    loginModels = new ArrayList<>(responseList);
                    String myKey = loginModels.get(0).userKey;
                    String myName = loginModels.get(0).name;
                    String myEmail = loginModels.get(0).email;
                    String myError = loginModels.get(0).error;
                    if (myError.equals("Success")) {

                        Toast.makeText(MainActivity.this, "Giriş İşlemi Başarılı", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                    else if (myError.equals("Database")) {

                        Toast.makeText(MainActivity.this, "Veritabanına Erişilemiyor", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Kullanıcı Adı veya Şifre Hatalı", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                } else
                    {
                        Toast.makeText(MainActivity.this, "Sunucuya Erişilemiyor", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                }
            }
            @Override
            public void onFailure(Call<List<LoginModel>> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
            }
        });
    }
}