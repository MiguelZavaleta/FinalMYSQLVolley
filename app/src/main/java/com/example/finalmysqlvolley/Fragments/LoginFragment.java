package com.example.finalmysqlvolley.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalmysqlvolley.Actividades.InsertarActivity;
import com.example.finalmysqlvolley.Actividades.PortalActivity;
import com.example.finalmysqlvolley.R;
import com.example.finalmysqlvolley.Recursos.Alumno;
import com.example.finalmysqlvolley.Recursos.utilidades;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    View vista;
    Activity Actividad;
    EditText pass,correo;
    Button btnCrear,btnLogear;
    Bundle bundle=new Bundle();
    Intent intent=null;
    ProgressDialog progreso;
    Alumno Alumno;
    public LoginFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        vista=inflater.inflate(R.layout.fragment_login, container, false);
        correo =vista. findViewById(R.id.TxtCorreo);
        pass = vista.findViewById(R.id.Txtpass);
        btnCrear=vista.findViewById(R.id.btnCrear);
        btnLogear=vista.findViewById(R.id.btnIniciar);
        utilidades.request= Volley.newRequestQueue(Actividad);
        EventosBotnones();
        return vista;
    }
    public void EventosBotnones(){
        btnLogear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(!utilidades.ValidarTxtVacios(correo.getText().toString())&& !utilidades.ValidarTxtVacios(pass.getText().toString())
               && utilidades.ValidarCorreo(correo.getText().toString())){
                   //Si tdo esta Correcto Entramos ala solicitud de validar

                   WebServiceExistenciaUsuario(correo.getText().toString(),pass.getText().toString(),Actividad);
               }else{
                   String mensaje=(!utilidades.ValidarTxtVacios(correo.getText().toString())&&
                           !utilidades.ValidarTxtVacios(pass.getText().toString())?"Los Campos No Estan vacios":"Alguno  u de tus Campos estan vacios Verifica")+
                           (utilidades.ValidarCorreo(correo.getText().toString())?"":"\nCorreo Incorrecto");
                   new SweetAlertDialog(Actividad, SweetAlertDialog.WARNING_TYPE)
                           .setTitleText("Verifica los Campos\n")
                           .setContentText(mensaje)
                           .show();
               }
            }
        });
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), InsertarActivity.class));
            }
        });
    }
    public void WebServiceExistenciaUsuario(String mail, final String password, final Activity Actividad) {
        progreso = new ProgressDialog(Actividad);
        progreso.setTitle("Consultando espera...");
        progreso.show();
        String DatosUrl = "ctrl=" + password + "&correo=" + mail;
        String url = utilidades.DireccionHttp + "ConsultarUsuarioPDO.php?" + DatosUrl;
        utilidades.stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                System.out.println("Peticion>> " + response);
                switch (response) {//segun lo que nos devuelva el Response:
                    case "Existes":
                        ConsultarDato(password);
                        break;
                    case "NoExiste":
                       progreso.hide();
                        progreso.dismiss();
                        new SweetAlertDialog(Actividad, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("No Existes\n Verifica tu Correo Y Pass...")
                                .setContentText("No estas Registrado Crea una Cuenta ;D")
                                .show();
                        break;
                    default:
                        progreso.hide();
                        progreso.dismiss();
                        Toast.makeText(Actividad, response, Toast.LENGTH_LONG).show();
                        break;
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                utilidades.progreso.hide();
                new SweetAlertDialog(Actividad, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Error en peticion al servidor!")
                        .show();
                System.out.println("error Login" + error.getMessage());

            }
        });
        utilidades.request.add(utilidades.stringRequest);
    }
    public void ConsultarDato(String NumeroControl){
        String url = utilidades.DireccionHttp + "ConsultarPDO.php?" +
                "ctrl=" + NumeroControl;
        utilidades.jsonStringRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //System.out.println("Response: " + response.toString());
                int i = 0;
                try {
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
                    Toast.makeText(Actividad, "estas vacios?" + utilidades.ListaAlumnos.isEmpty(), Toast.LENGTH_SHORT).show();
                    utilidades.AlumnoLogeado=utilidades.ListaAlumnos.get(0);
                    CambiarActividad();//cerramos nuestro progreesDialog y cambiamos de Activity
                } catch (JSONException e) {
                    utilidades.ListaAlumnos.clear();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Actividad, "Hubo un error en Volley", Toast.LENGTH_LONG).show();
            }
        });
        System.out.println("URL enviada: " +utilidades.jsonStringRequest);
        utilidades.request.add(utilidades.jsonStringRequest);
    }

    private void CambiarActividad() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progreso.hide();
                intent= new Intent(Actividad, PortalActivity.class);
                if(intent!=null){
                    bundle.putSerializable("usuario",utilidades.AlumnoLogeado);
                    intent.putExtras(bundle);
                    progreso.dismiss();
                    startActivity(intent);
                    Actividad.finish();
                }
            }
        },4000);

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
            Actividad = (Activity) context;
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
