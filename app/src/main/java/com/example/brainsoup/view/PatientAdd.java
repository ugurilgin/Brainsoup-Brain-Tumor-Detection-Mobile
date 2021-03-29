package com.example.brainsoup.view;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.brainsoup.R;
import com.example.brainsoup.model.AddPatientInfo;
import com.example.brainsoup.model.AddPatientModel;
import com.example.brainsoup.model.MyURL;
import com.example.brainsoup.service.AddPatientAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import br.com.sapereaude
        .maskedEditText
        .MaskedEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PatientAdd#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatientAdd extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Spinner spinner;
    ProgressDialog progressDialog;
    ArrayList<AddPatientModel> registerModels;
    Retrofit retrofit;
    String cinsiyet;
    EditText name;
    EditText surname;
    EditText email;
    EditText tc;
    MaskedEditText date;



    public PatientAdd() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PatientAdd.
     */
    // TODO: Rename and change types and number of parameters
    public static PatientAdd newInstance(String param1, String param2) {
        PatientAdd fragment = new PatientAdd();
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
                             Bundle savedInstanceState)  {
        View v = inflater.inflate(R.layout.fragment_patient_add, container, false);
        String userEmail=getArguments().getString("UserEmail");
        String userKey=getArguments().getString("UserKey");
        spinner=(Spinner)v.findViewById(R.id.patientCinsiyet);

        name=(EditText)v.findViewById(R.id.patientName);
        surname=(EditText)v.findViewById(R.id.patientSurname);
        email=(EditText)v.findViewById(R.id.patientMail);
        tc=(EditText)v.findViewById(R.id.patientTC);
        Button submit=(Button)v.findViewById(R.id.patientSubmit);
        date=v.findViewById(R.id.patientDate);



        AddPatientModel loginModel=new AddPatientModel();
        Gson gson=new GsonBuilder().setLenient().create();
        retrofit=new Retrofit.Builder()
                .baseUrl(MyURL.getBASE_URL())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(name.getText().toString()) || TextUtils.isEmpty(surname.getText().toString()) ||TextUtils.isEmpty(tc.getText().toString()) ||TextUtils.isEmpty(cinsiyet) ||TextUtils.isEmpty(date.getText().toString())||TextUtils.isEmpty(email.getText().toString()))
                {
                    Toast.makeText(view.getContext(), "Bütün Alanlar Doldurulmalıdır",Toast.LENGTH_LONG).show();


                }
                else {
                    if(Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches())
                        goAddPatient(tc.getText().toString(),date.getText().toString(),cinsiyet,email.getText().toString(),name.getText().toString(),surname.getText().toString(),userKey);
                    else
                        Toast.makeText(PatientAdd.this.getContext(), "Lütfen Geçerli Bir Mail Adresi Giriniz",Toast.LENGTH_LONG).show();



                }
            }
        });
        initspinnerfooter();
        return  v;
    }





    private void initspinnerfooter() {
        String[] items = new String[]{
                "Kadın", "Erkek"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                cinsiyet=(String) parent.getItemAtPosition(position);
                //Toast.makeText(PatientAdd.this.getContext(), (String) parent.getItemAtPosition(position),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }

    private void goAddPatient(String tc,String date,String cinsiyet,String email,String name,String surname,String userKey)
    {
        progressDialog = new ProgressDialog(PatientAdd.this.getContext());
        progressDialog.setMessage("Lütfen Bekleyiniz");
        progressDialog.show();
        AddPatientAPI registerAPI=retrofit.create(AddPatientAPI.class);
        AddPatientInfo user=new AddPatientInfo(tc,cinsiyet,date,email,name,surname,userKey);

        Call<List<AddPatientModel>> call =registerAPI.toRegister(user);
        call.enqueue(new Callback<List<AddPatientModel>>() {
            @Override
            public void onResponse(Call<List<AddPatientModel>> call, Response<List<AddPatientModel>> response) {
                if (response.isSuccessful()) {

                    List<AddPatientModel> responseList = response.body();

                    registerModels = new ArrayList<>(responseList);

                    String myResult = registerModels.get(0).result;
                    Toast.makeText(PatientAdd.this.getContext(), myResult,Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<AddPatientModel>> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
            }
        });



    }


}