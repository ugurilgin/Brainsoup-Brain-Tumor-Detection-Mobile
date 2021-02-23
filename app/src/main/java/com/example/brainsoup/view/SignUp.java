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
import com.example.brainsoup.model.LoginInfo;
import com.example.brainsoup.model.LoginModel;
import com.example.brainsoup.model.MyURL;
import com.example.brainsoup.model.RegisterInfo;
import com.example.brainsoup.model.RegisterModel;
import com.example.brainsoup.service.LoginAPI;
import com.example.brainsoup.service.RegisterAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUp extends AppCompatActivity {
    private ImageView img;
    TextView signin2;
    ProgressDialog progressDialog;
    Retrofit retrofit;
    EditText email;
    EditText name;
    EditText surname;
    EditText password;
    ArrayList<RegisterModel> registerModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);
        img = (ImageView)findViewById(R.id.logo2);


        signin2 = (TextView) findViewById(R.id.signin2);
        img.setImageResource(R.drawable.logo);
        String signinTxt="Hesabınız Var mı? Giriş Yapmak İçin Tıklayın";
        SpannableString s1=new SpannableString(signinTxt);

        ClickableSpan clickableSpan1=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent myIntent = new Intent(SignUp.this,MainActivity.class);
                startActivity(myIntent);
            }
        };

        s1.setSpan(clickableSpan1,36,44, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        signin2.setText(s1);
        signin2.setMovementMethod(LinkMovementMethod.getInstance());

        Gson gson=new GsonBuilder().setLenient().create();
        retrofit=new Retrofit.Builder()
                .baseUrl(MyURL.getBASE_URL())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
    public  void Submit(View view)
    {
        Log.d("host adı", MyURL.getBASE_URL());
        email=(EditText)findViewById(R.id.email2);
        password=(EditText)findViewById(R.id.password2);
        name=(EditText)findViewById(R.id.name);
        surname=(EditText)findViewById(R.id.surname);
        if(TextUtils.isEmpty(email.getText().toString()) ||TextUtils.isEmpty(password.getText().toString())||TextUtils.isEmpty(name.getText().toString()) ||TextUtils.isEmpty(surname.getText().toString()) )
        {
            Toast.makeText(SignUp.this, "Lütfen Tüm Alanları Doldurunuz", Toast.LENGTH_SHORT).show();

        }
        else {
            if(Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches())
            { goLogin(email.getText().toString(), password.getText().toString(), name.getText().toString(), surname.getText().toString());name.setText("");surname.setText("");email.setText("");password.setText("");}
       else
                {    Toast.makeText(SignUp.this, "Lütfen Geçerli Bir Mail Adresi Giriniz", Toast.LENGTH_SHORT).show();}

        }
        }

    private void goLogin(String email,String password,String name,String surname)
    {
        progressDialog = new ProgressDialog(SignUp.this);
        progressDialog.setMessage("Lütfen Bekleyiniz");
        progressDialog.show();
        RegisterAPI registerAPI=retrofit.create(RegisterAPI.class);
        RegisterInfo user=new RegisterInfo(email,password,name,surname);

        Call<List<RegisterModel>> call =registerAPI.toRegister(user);
        call.enqueue(new Callback<List<RegisterModel>>() {
            @Override
            public void onResponse(Call<List<RegisterModel>> call, Response<List<RegisterModel>> response) {
                if (response.isSuccessful()) {

                    List<RegisterModel> responseList = response.body();
                    registerModels = new ArrayList<>(responseList);
                    String myResult = registerModels.get(0).result;
                    Toast.makeText(SignUp.this, myResult, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<RegisterModel>> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
            }
        });


    }
}
