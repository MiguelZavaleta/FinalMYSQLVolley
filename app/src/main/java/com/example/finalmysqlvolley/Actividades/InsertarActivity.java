package com.example.finalmysqlvolley.Actividades;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.finalmysqlvolley.Fragments.AltaUsuariosFragment;
import com.example.finalmysqlvolley.Fragments.Consultar_UsuariosFragment;
import com.example.finalmysqlvolley.Fragments.ModificarUsuarioFragment;
import com.example.finalmysqlvolley.R;


public class InsertarActivity extends AppCompatActivity
implements AltaUsuariosFragment.OnFragmentInteractionListener,
        Consultar_UsuariosFragment.OnFragmentInteractionListener ,
        ModificarUsuarioFragment.OnFragmentInteractionListener
        {
Fragment vistaFrag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar);
        vistaFrag=new AltaUsuariosFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.idContentMain ,vistaFrag).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
