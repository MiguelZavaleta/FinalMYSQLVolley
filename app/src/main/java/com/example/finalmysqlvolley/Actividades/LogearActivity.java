package com.example.finalmysqlvolley.Actividades;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.finalmysqlvolley.R;
import com.example.finalmysqlvolley.Fragments.LoginFragment;

public class LogearActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener {
Fragment LoginFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logear);
        LoginFragment=new LoginFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.IdFragLogear ,LoginFragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
