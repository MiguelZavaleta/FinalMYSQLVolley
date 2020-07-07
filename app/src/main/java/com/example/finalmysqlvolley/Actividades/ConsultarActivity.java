package com.example.finalmysqlvolley.Actividades;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.finalmysqlvolley.R;
import com.example.finalmysqlvolley.Fragments.AltaUsuariosFragment;
import com.example.finalmysqlvolley.Fragments.Consultar_UsuariosFragment;
import com.example.finalmysqlvolley.Fragments.ModificarUsuarioFragment;

public class ConsultarActivity extends AppCompatActivity
        implements AltaUsuariosFragment.OnFragmentInteractionListener,
        Consultar_UsuariosFragment.OnFragmentInteractionListener ,
        ModificarUsuarioFragment.OnFragmentInteractionListener{
    Fragment vistaFrag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar);
        vistaFrag=new Consultar_UsuariosFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.IdFragConsultar ,vistaFrag).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
