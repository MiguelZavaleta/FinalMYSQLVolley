package com.example.finalmysqlvolley;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.finalmysqlvolley.Fragments.AltaUsuariosFragment;
import com.example.finalmysqlvolley.Fragments.Consultar_UsuariosFragment;
import com.example.finalmysqlvolley.Fragments.IconoFragment;
import com.example.finalmysqlvolley.Fragments.ModificarUsuarioFragment;

public class MainActivity extends AppCompatActivity
implements AltaUsuariosFragment.OnFragmentInteractionListener,
        Consultar_UsuariosFragment.OnFragmentInteractionListener,
        ModificarUsuarioFragment.OnFragmentInteractionListener,
        IconoFragment.OnFragmentInteractionListener
        {
    Fragment vistaFrag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vistaFrag=new IconoFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.idContentMain ,vistaFrag).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
        }
