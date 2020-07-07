package com.example.finalmysqlvolley.Actividades;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.finalmysqlvolley.R;
import com.example.finalmysqlvolley.Fragments.Consultar_UsuariosFragment;
import com.example.finalmysqlvolley.Fragments.ModificarUsuarioFragment;

public class ModificarActivity extends AppCompatActivity
        implements
        Consultar_UsuariosFragment.OnFragmentInteractionListener ,
        ModificarUsuarioFragment.OnFragmentInteractionListener
{
    Fragment vistaFrag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);
        vistaFrag=new ModificarUsuarioFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.IdFragModificar ,vistaFrag).addToBackStack(null).commit();


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
