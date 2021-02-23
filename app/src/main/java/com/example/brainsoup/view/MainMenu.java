package com.example.brainsoup.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;

import com.example.brainsoup.HomeMenu;
import com.example.brainsoup.MRPredict;
import com.example.brainsoup.MRView;
import com.example.brainsoup.PatientAdd;
import com.example.brainsoup.PatientView;
import com.example.brainsoup.R;
import com.example.brainsoup.UnsavedMR;
import com.example.brainsoup.UpdateProfile;
import com.example.brainsoup.model.LoginInfo;
import com.example.brainsoup.model.LoginModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView name;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    int id=item.getItemId();
        name=findViewById(R.id.namelabel);
        name.setText("Login");
        if(id==R.id.home)
        {

            setTitle("Profili Düzenle");
            HomeMenu first=new HomeMenu();
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment,first).commit();
        }
    if(id==R.id.profile)
    {
        Intent intent = getIntent();
        String userName = intent.getStringExtra("UserName");
        String userSurname = intent.getStringExtra("UserSurname");
        String userEmail = intent.getStringExtra("UserEmail");
        String userKey = intent.getStringExtra("UserKey");
        Bundle bundle=new Bundle();
        bundle.putString("UserName", userName);
        bundle.putString("UserSurname", userSurname);
        bundle.putString("UserEmail", userEmail);
        bundle.putString("UserKey", userKey);

        //set Fragmentclass Arguments


        setTitle("Profili Düzenle");
        UpdateProfile first=new UpdateProfile();
        first.setArguments(bundle);
        FragmentManager fragmentManager=getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.fragment,first).commit();

    }
        if(id==R.id.addPatient)
        {
            setTitle("Profili Düzenle");
            PatientAdd first=new PatientAdd();
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment,first).commit();
        }
        if(id==R.id.viewPatient)
        {

            setTitle("Profili Düzenle");
            PatientView first=new PatientView();
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment,first).commit();
        }
        if(id==R.id.predictMR)
        {

            setTitle("Profili Düzenle");
            MRPredict first=new MRPredict();
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment,first).commit();
        }
        if(id==R.id.viewMR)
        {

            setTitle("Profili Düzenle");
            MRView first=new MRView();
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment,first).commit();
        }
        if(id==R.id.unsavedMR)
        {

            setTitle("Profili Düzenle");
            UnsavedMR first=new UnsavedMR();
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment,first).commit();
        }
        if(id==R.id.logout)
        {
            Intent i = new Intent(this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }
        return true;
    }
}