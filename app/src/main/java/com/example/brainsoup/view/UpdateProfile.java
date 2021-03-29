package com.example.brainsoup.view;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.brainsoup.R;
import com.example.brainsoup.model.LoginModel;
import com.example.brainsoup.model.MyURL;
import com.example.brainsoup.model.UpdateInfo;
import com.example.brainsoup.model.UpdateModel;
import com.example.brainsoup.service.UpdateAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateProfile extends Fragment {
    ProgressDialog progressDialog;
    EditText profilname;
    EditText profilsurname;
    EditText profilemail;
    EditText profilepassword;
    ArrayList<UpdateModel> registerModels;
    Retrofit retrofit;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UpdateProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateProfile newInstance(String param1, String param2) {
        UpdateProfile fragment = new UpdateProfile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_update_profile, container, false);
        profilname=(EditText)v.findViewById(R.id.profileName);
        profilsurname=(EditText)v.findViewById(R.id.profileSurname);
        profilemail=(EditText)v.findViewById(R.id.profileMail);
        profilepassword=(EditText)v.findViewById(R.id.updatePassword);
        Button submit=(Button)v.findViewById(R.id.patientUSubmit);
        LoginModel loginModel=new LoginModel();
        String userName=getArguments().getString("UserName");
        String userSurname=getArguments().getString("UserSurname");
        String userEmail=getArguments().getString("UserEmail");
        String userKey=getArguments().getString("UserKey");
        profilname.setText(userName);
        profilsurname.setText(userSurname);
        profilemail.setText(userEmail);
        // Inflate the layout for this fragment
        Gson gson=new GsonBuilder().setLenient().create();
        retrofit=new Retrofit.Builder()
                .baseUrl(MyURL.getBASE_URL())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

    submit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if( TextUtils.isEmpty(profilname.getText().toString()) ||TextUtils.isEmpty(profilsurname.getText().toString()) )
            {
                Toast.makeText(view.getContext(), "İsim ve Soyisim Alanları Boş Olamaz",Toast.LENGTH_LONG).show();


            }
            else {
                if(TextUtils.isEmpty(profilepassword.getText().toString()))
                { goUpdate( profilemail.getText().toString(),"-1", profilname.getText().toString(), profilsurname.getText().toString());
                }
                else
                {goUpdate( profilemail.getText().toString(),profilepassword.getText().toString(), profilname.getText().toString(), profilsurname.getText().toString());   }

            }
        }
    });

        return  v;

    }


    private void goUpdate(String email,String password,String name,String surname)
    {
        progressDialog = new ProgressDialog(UpdateProfile.this.getContext());
        progressDialog.setMessage("Lütfen Bekleyiniz");
        progressDialog.show();
        UpdateAPI registerAPI=retrofit.create(UpdateAPI.class);
        UpdateInfo user=new UpdateInfo(email,password,name,surname);

        Call<List<UpdateModel>> call =registerAPI.toRegister(user);
        call.enqueue(new Callback<List<UpdateModel>>() {
            @Override
            public void onResponse(Call<List<UpdateModel>> call, Response<List<UpdateModel>> response) {
                if (response.isSuccessful()) {

                    List<UpdateModel> responseList = response.body();

                    registerModels = new ArrayList<>(responseList);

                    String myResult = registerModels.get(0).result;
                    Toast.makeText(UpdateProfile.this.getContext(), myResult,Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<UpdateModel>> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
            }
        });



    }
}