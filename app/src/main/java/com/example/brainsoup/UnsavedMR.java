package com.example.brainsoup;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.brainsoup.model.MyURL;
import com.example.brainsoup.model.UnsavedInfo;
import com.example.brainsoup.model.UnsavedModel;
import com.example.brainsoup.model.UnsavedSaveInfo;
import com.example.brainsoup.model.UnsavedSaveModel;
import com.example.brainsoup.model.UpdateInfo;
import com.example.brainsoup.model.UpdateModel;
import com.example.brainsoup.service.UnsavedAPI;
import com.example.brainsoup.service.UnsavedSaveAPI;
import com.example.brainsoup.service.UpdateAPI;
import com.example.brainsoup.view.UpdateProfile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UnsavedMR#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UnsavedMR extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Spinner spinnerunsaved;

    String sonuc;
    ProgressDialog progressDialog;
    ImageView imgloc;
    ImageView tumorloc;
    EditText tc;
    ArrayList<UnsavedModel> registerModels;
    ArrayList<UnsavedSaveModel> unsavedModels;
    Retrofit retrofit;
    public UnsavedMR() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UnsavedMR.
     */
    // TODO: Rename and change types and number of parameters
    public static UnsavedMR newInstance(String param1, String param2) {
        UnsavedMR fragment = new UnsavedMR();
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
        View v = inflater.inflate(R.layout.fragment_unsaved_m_r, container, false);
        String userKey=getArguments().getString("UserKey");
        spinnerunsaved=(Spinner)v.findViewById(R.id.unsavedspinner);
        imgloc=(ImageView)v.findViewById(R.id.normalImg);
        tumorloc=(ImageView)v.findViewById(R.id.tumorImg);
        tc=(EditText)v.findViewById(R.id.unsavedtc) ;
        Button submit=(Button)v.findViewById(R.id.UnsavedSubmit);
        Gson gson=new GsonBuilder().setLenient().create();
        retrofit=new Retrofit.Builder()
                .baseUrl(MyURL.getBASE_URL())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        initspinnerfooterun();
        viewUnsaved(userKey);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( TextUtils.isEmpty(tc.getText().toString())  )
                {
                    Toast.makeText(UnsavedMR.this.getContext(), "TC Kimlik No Boş Olamaz",Toast.LENGTH_LONG).show();

                }
                else
                {
                    goUpdate(tc.getText().toString(),sonuc,userKey);
                }
            }
        });
        return v;
    }

    private void initspinnerfooterun() {
        String[] items = new String[]{
                "Pozitif", "Negatif"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items);
        spinnerunsaved.setAdapter(adapter);
        spinnerunsaved.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                sonuc=(String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }
    private void viewUnsaved(String key)
    {

        UnsavedAPI registerAPI=retrofit.create(UnsavedAPI.class);
        UnsavedInfo user=new UnsavedInfo(key);
        Call<List<UnsavedModel>> call =registerAPI.toRegister(user);
        call.enqueue(new Callback<List<UnsavedModel>>() {
            @Override
            public void onResponse(Call<List<UnsavedModel>> call, Response<List<UnsavedModel>> response) {
                if (response.isSuccessful()) {

                    List<UnsavedModel> responseList = response.body();

                    registerModels = new ArrayList<>(responseList);
                    String a=registerModels.get(0).tumorloc;
                    if(a.equals("0")) {
                        HomeMenu first=new HomeMenu();

                        FragmentManager fragmentManager=getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.fragment,first).commit();
                    }
                    else
                    {
                        Picasso.get().load(MyURL.getImageUrl() + registerModels.get(0).imgloc).fit().into(imgloc);
                        Picasso.get().load(MyURL.getImageUrl() + registerModels.get(0).tumorloc).fit().into(tumorloc);

                        String myResult = registerModels.get(0).result;
                        if (myResult.equals("Pozitif")) {
                            spinnerunsaved.setSelection(0);
                            sonuc="Pozitif";
                        }
                        else
                        {
                            spinnerunsaved.setSelection(1);
                            sonuc="Negatif";
                        }
                    }




                }
            }

            @Override
            public void onFailure(Call<List<UnsavedModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });



    }
    private void goUpdate(String tc,String result,String userKey)
    {
        progressDialog = new ProgressDialog(UnsavedMR.this.getContext());
        progressDialog.setMessage("Lütfen Bekleyiniz");
        progressDialog.show();
        UnsavedSaveAPI registerAPI=retrofit.create(UnsavedSaveAPI.class);
        UnsavedSaveInfo user=new UnsavedSaveInfo(tc,result,userKey);

        Call<List<UnsavedSaveModel>> call =registerAPI.toRegister(user);
        call.enqueue(new Callback<List<UnsavedSaveModel>>() {
            @Override
            public void onResponse(Call<List<UnsavedSaveModel>> call, Response<List<UnsavedSaveModel>> response) {
                if (response.isSuccessful()) {

                    List<UnsavedSaveModel> responseList = response.body();

                    unsavedModels = new ArrayList<>(responseList);

                    String myResult = unsavedModels.get(0).result;
                    Toast.makeText(UnsavedMR.this.getContext(), myResult,Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<UnsavedSaveModel>> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
            }
        });



    }

}