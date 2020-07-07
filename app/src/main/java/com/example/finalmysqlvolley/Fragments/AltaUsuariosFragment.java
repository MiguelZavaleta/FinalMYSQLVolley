package com.example.finalmysqlvolley.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalmysqlvolley.Actividades.PortalActivity;
import com.example.finalmysqlvolley.R;
import com.example.finalmysqlvolley.Actividades.ConsultarActivity;
import com.example.finalmysqlvolley.Recursos.Alumno;
import com.example.finalmysqlvolley.Recursos.utilidades;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AltaUsuariosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AltaUsuariosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AltaUsuariosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    View vista;
    Activity Actividad;
    FloatingActionButton fabRegistro;
    RadioButton rM, rF;
    FloatingActionsMenu grupoBotones;
    EditText[] Txt = new EditText[7];
    String genero = "";
    Alumno Alumno = new Alumno();
    ProgressDialog progreso;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AltaUsuariosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AltaUsuariosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AltaUsuariosFragment newInstance(String param1, String param2) {
        AltaUsuariosFragment fragment = new AltaUsuariosFragment();
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
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_alta_usuarios, container, false);
        Txt[0] = vista.findViewById(R.id.txtControl);
        Txt[1] = vista.findViewById(R.id.txtNomb);
        Txt[2] = vista.findViewById(R.id.txtAp);
        Txt[3] = vista.findViewById(R.id.txtCarrera);
        Txt[4] = vista.findViewById(R.id.txtTel);
        Txt[5] = vista.findViewById(R.id.txtCorreo);
        Txt[6] = vista.findViewById(R.id.txtDireccion);
        rF = vista.findViewById(R.id.radioF);
        rM = vista.findViewById(R.id.radioM);
        fabRegistro = vista.findViewById(R.id.idFabConfirmar);

        grupoBotones = vista.findViewById(R.id.grupoFab);
        utilidades.request = Volley.newRequestQueue(this.Actividad);

        fabRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrarUsuario();
                grupoBotones.collapse();
            }
        });
        return vista;
    }


    public void CargarAlumno() {
        Alumno.setNumeroControl(Txt[0].getText().toString());
        Alumno.setNombre(Txt[1].getText().toString());
        Alumno.setApellidos(Txt[2].getText().toString());
        Alumno.setCarrera(Txt[3].getText().toString());
        Alumno.setTelefono(Txt[4].getText().toString());
        Alumno.setCorreo(Txt[5].getText().toString());
        Alumno.setDireccion(Txt[6].getText().toString());
    }

    private void RegistrarUsuario() {
        CargarAlumno();//Inicializamos el Objeto
        if (!ValidarCampos()) {//mientras no se cumpla, en este metodo validamos si
            //hay campos vacios o si el campo correo electronico y numero telefonico cumples los reuqisitos
            //con expresiones regulares.
            new SweetAlertDialog(Actividad, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Algun Campo Incorrecto o Vacio Verifica!")
                    .show();
        } else {
            String Datos = "Nc=" + Alumno.getNumeroControl() + "&" +
                    "Nombre=" + Alumno.getNombre() + "&" +
                    "Apellidos=" + Alumno.getApellidos() + "&" +
                    "Genero=" + Alumno.getGenero() + "&" +
                    "Carrera=" + Alumno.getCarrera() + "&" +
                    "Correo=" + Alumno.getCorreo() + "&" +
                    "Direccion=" + Alumno.getDireccion() + "&" +
                    "Telefono=" + Alumno.getTelefono() + "";
            InsertarWebService(Datos, Actividad);
        }
    }

    public void InsertarWebService(String Datos, final Activity Actividad) {
        progreso = new ProgressDialog(Actividad);
        progreso.setTitle("Insertando Espera...");
        progreso.show();
        //Mandamos las Variables por el Metodo Get
        String url = utilidades.DireccionHttp + "InsertarPDO.php?" + Datos;
        url = url.replace(" ", "%20");//quitamos espacios para evitar errores
        utilidades.stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progreso.hide();
                progreso.dismiss();
                if (response.trim().equalsIgnoreCase("Insertado")) {
                    new SweetAlertDialog(Actividad)
                            .setTitleText("Registro Exitoso :D")
                            .setConfirmText("Ok")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    startActivity(new Intent(getActivity(), ConsultarActivity.class));
                                    Actividad.finish();
                                }
                            })
                            .show();
                } else {
                    progreso.hide();
                    progreso.dismiss();
                    new SweetAlertDialog(Actividad, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Datos Existentes...")
                            .setContentText("El numero de Control y Correo Electronico\nVerifica!!")
                            .show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
                progreso.dismiss();
                new SweetAlertDialog(Actividad, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Ocurrio un Error :(!")
                        .show();
            }
        });
        //System.out.println("Datos enviados:-> " + stringRequest.toString());
        utilidades.request.add(utilidades.stringRequest);

    }

    public boolean ValidarCampos() {
        boolean bandera = false;
        if (rF.isChecked() == true) {
            genero = "Femenino";
        } else if (rM.isChecked() == true) {
            genero = "Masculino";
        } else {
            genero = "No Seleccionado";
        }
        if (!genero.equals("No Seleccionado") && VerificarCamposVacios()
                && utilidades.ValidarCorreo(Alumno.getCorreo()) && utilidades.ValidaTelefono("" + Alumno.getTelefono())) {
            Alumno.setGenero(genero);
            bandera = true;
        } else {
            bandera = false;
        }
        return bandera;
    }

    boolean VerificarCamposVacios() {
        boolean bandera = false;
        for (int i = 0; i < Txt.length; i++) {
            if (!Txt[i].getText().toString().trim().isEmpty()) {
                bandera = true;//si se valida correctamente los campos no estan vacios
            } else {
                Txt[i].requestFocus();
                bandera = false;//los campos estan vacios
                break;
            }
        }
        return bandera;
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
