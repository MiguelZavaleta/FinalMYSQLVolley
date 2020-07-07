package com.example.finalmysqlvolley.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.finalmysqlvolley.MainActivity;
import com.example.finalmysqlvolley.R;
import com.example.finalmysqlvolley.Actividades.ModificarActivity;
import com.example.finalmysqlvolley.Adaptadores.AdaptadorAlumno;
import com.example.finalmysqlvolley.Recursos.Alumno;
import com.example.finalmysqlvolley.Recursos.utilidades;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Consultar_UsuariosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Consultar_UsuariosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Consultar_UsuariosFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    View vista;
    Activity Actividad;
    RecyclerView RecyclerLista;
    EditText Buscar;
    FloatingActionButton fabModificar, fabEliminar,faHome;
    FloatingActionsMenu grupoBotones;
    AdaptadorAlumno miAdaptadorUsuario;
    ProgressDialog progreso;
    Alumno Alumno;
    TextView prueba;

    boolean Exito;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Consultar_UsuariosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Consultar_UsuariosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Consultar_UsuariosFragment newInstance(String param1, String param2) {
        Consultar_UsuariosFragment fragment = new Consultar_UsuariosFragment();
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
        vista = inflater.inflate(R.layout.fragment_consultar__usuarios, container, false);
        RecyclerLista = vista.findViewById(R.id.recyclerUsuarios);
        Buscar = vista.findViewById(R.id.IdBuscar);
        fabModificar = vista.findViewById(R.id.idFabActualizar);
        fabEliminar = vista.findViewById(R.id.idFabEliminar);
        faHome=vista.findViewById(R.id.idFabHome);
        grupoBotones = vista.findViewById(R.id.grupoFab);

        utilidades.InicializarRequets(Actividad);
        //Agregamos a nuestro EditText el Evento
        Buscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                CargarWebServiceConsultarUsuarios((s.toString().isEmpty())?"todo":s.toString(),Actividad);
               // "SELECT * FROM Alumnos"  + " where NumeroCtrl like '" + s + "%'";

            }
        });
        // Inflate the layout for this fragment
        RecyclerLista.setLayoutManager(new LinearLayoutManager(this.Actividad));
        RecyclerLista.setHasFixedSize(true);
        CargarWebServiceConsultarUsuarios("todo",Actividad);
        EventosBotonesFlotantes();
        return vista;
    }
    public  void CargarWebServiceConsultarUsuarios(String Ctrl, final Activity Actividad) {
        progreso = new ProgressDialog(Actividad);
        progreso.setTitle("Consultando...");
        progreso.show();
        String url = utilidades.DireccionHttp + "ConsultarPDO.php?" +
                "ctrl=" + Ctrl;
        utilidades.jsonStringRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //System.out.println("Response: " + response.toString());
                int i = 0;
                try {
                    progreso.hide();
                    //Declaramos el JSON que Recibimos de nuestra clase PHP
                    JSONArray jsonArray = response.getJSONArray("Alumnos");
                    utilidades.ListaAlumnos.clear();
                    while (i < jsonArray.length()) {//LLenamos nuestros Objetos
                        JSONObject jsonObject = null;
                        jsonObject = jsonArray.getJSONObject(i);
                        Alumno = new Alumno();
                        Alumno.setNumeroControl(jsonObject.optString("NumeroCtrl"));
                        Alumno.setNombre(jsonObject.optString(Alumno.LLaves[1]));
                        Alumno.setApellidos((jsonObject.optString(Alumno.LLaves[2])));
                        Alumno.setGenero(jsonObject.optString(Alumno.LLaves[3]));
                        Alumno.setCarrera(jsonObject.optString(Alumno.LLaves[4]));
                        Alumno.setCorreo(jsonObject.optString(Alumno.LLaves[5]));
                        Alumno.setDireccion(jsonObject.optString(Alumno.LLaves[6]));
                        Alumno.setTelefono(jsonObject.optString(Alumno.LLaves[7]));
                        utilidades.ListaAlumnos.add(Alumno);
                        System.out.println((i + 1) + "<< Imprimiendo Datos: " + utilidades.ListaAlumnos.get(i).getNumeroControl()
                                + "\n>> ");
                        i++;
                    }
                    Toast.makeText(Actividad, "Longitud==" + utilidades.ListaAlumnos.size(), Toast.LENGTH_LONG).show();
                    LLenarAdaptadorAlumnos();
                    //BanderaVolley = true;
                } catch (JSONException e) {
                   utilidades.ListaAlumnos.clear();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
            }
        });
        System.out.println("URL enviada: " +utilidades.jsonStringRequest);
        utilidades.request.add(utilidades.jsonStringRequest);
    }
    void LLenarAdaptadorAlumnos() {
        miAdaptadorUsuario = new AdaptadorAlumno(utilidades.ListaAlumnos, Actividad);
        RecyclerLista.setAdapter(miAdaptadorUsuario);
    }
public  void EventosBotonesFlotantes(){
        fabModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AdaptadorAlumno.UltimaSeleccion != -1 && utilidades.AlumnoSeleccionado != null && !utilidades.AlumnoSeleccionado.getNumeroControl().equalsIgnoreCase("NA")) {
                    //Mandamos a nuestra Interface el objeto del alumno Seleccionado este Retornara a nuestro mainActivity
                    /*Y de este Repintaremos un fragment pasando como Arguento el Objeto enviado para asi poder trabajar con la
                     * clase Modificar Usuario*/
                  // Comunica.EnviarDatosAlumno(utilidades.AlumnoSeleccionado = utilidades.ListaAlumnos.get(AdaptadorAlumno.UltimaSeleccion));
                    Intent intent= new Intent(Actividad, ModificarActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("Datos",utilidades.AlumnoSeleccionado=utilidades.ListaAlumnos.get(AdaptadorAlumno.UltimaSeleccion));
                    intent.putExtras(bundle);
                    startActivity(intent);

                } else {
                    new SweetAlertDialog(Actividad, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("No tienes nada Seleccionado")
                            .show();
                }
            }
        });
        fabEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AdaptadorAlumno.UltimaSeleccion != -1 && utilidades.AlumnoSeleccionado != null && !utilidades.AlumnoSeleccionado.getNumeroControl().equalsIgnoreCase("NA")) {
                    new SweetAlertDialog(Actividad, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Estas seguro?")
                            .setContentText("Vas a Eliminar a [ " + utilidades.AlumnoSeleccionado.getNumeroControl() + " ]\nY todos sus datos!")
                            .setConfirmText("Si,Elimiar!")
                            .setConfirmButtonBackgroundColor(R.color.md_red_900)
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    CargarEliminar(utilidades.AlumnoSeleccionado.getNumeroControl(),Actividad);

                                }
                            }).setCancelText("Cancelar")
                    .show();
                } else {
                    new SweetAlertDialog(Actividad, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("No tienes nada Seleccionado")
                            .show();
                }
            }
        });
        faHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Actividad, MainActivity.class));
            }
        });
}
    public void CargarEliminar(String query, final Activity Actividad) {
        progreso = new ProgressDialog(Actividad);
        progreso.setTitle("Eliminando...");
        progreso.show();
        String url = utilidades.DireccionHttp + "EliminarPDO.php?" +
                "ctrl=" + query;
        System.out.println("Mandando url = " + url);
        utilidades.stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progreso.hide();
                System.out.println("Imprimiendo response>> " + response);
                if (response.trim().equalsIgnoreCase("Eliminado")) {
                   CargarWebServiceConsultarUsuarios("todo",Actividad);
                } else if (response.trim().equalsIgnoreCase("noExiste")) {
                    new SweetAlertDialog(Actividad, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("No Se encontro el dato!")
                            .show();
                } else {
                    new SweetAlertDialog(Actividad, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("No se Elimino!")
                            .show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
                new SweetAlertDialog(Actividad, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Hubo un error en la Peticion del Servidor :C")
                        .show();
                System.out.println("Error en vooler" + error.getMessage());

            }
        });
        utilidades.request.add(utilidades.stringRequest);
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            try {
                this.Actividad = (Activity) context;
                 }catch (Exception e){
                System.out.println("Cachando error---"+e.toString());
            }
        }
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }




    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
