package com.example.finalmysqlvolley.Actividades;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.finalmysqlvolley.R;
import com.example.finalmysqlvolley.Fragments.PortalFragment;

public class PortalActivity extends AppCompatActivity implements PortalFragment.OnFragmentInteractionListener {
Fragment vistaPortal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portal);
        vistaPortal=new PortalFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.IdFragPortal ,vistaPortal).commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
