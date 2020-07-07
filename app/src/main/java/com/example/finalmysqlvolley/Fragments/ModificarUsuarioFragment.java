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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
 * {@link ModificarUsuarioFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ModificarUsuarioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModificarUsuarioFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ModificarUsuarioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ModificarUsuarioFragment.
     */

    View vista;
    Activity Actividad;
    FloatingActionsMenu grupoBotones;
    FloatingActionButton faEliminar, faModificar;
    RadioButton rM, rF;
    ImageView Img;
    EditText[] Txt = new EditText[7];
    Bundle ObjetoAlumno = null;//variable que Capturara Datos del Activiti
    Alumno AlumnoCapturado = null;///Variable para Almacenar nuestro Objeto del MainActivity
    Alumno AlumnoModificado = null;//Vriable par aAlmacenar los Datos Modificados
    String genero = "";
    ProgressDialog progreso;

    // TODO: Rename and change types and number of parameters
    public static ModificarUsuarioFragment newInstance(String param1, String param2) {
        ModificarUsuarioFragment fragment = new ModificarUsuarioFragment();
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
        vista = inflater.inflate(R.layout.fragment_modificar_usuario, container, false);
        // Inflate the layout for this fragment
        Img = vista.findViewById(R.id.IdImgAvatar);
        Txt[0] = vista.findViewById(R.id.txtControl);
        Txt[1] = vista.findViewById(R.id.txtNomb);
        Txt[2] = vista.findViewById(R.id.txtAp);
        Txt[3] = vista.findViewById(R.id.txtCarrera);
        Txt[4] = vista.findViewById(R.id.txtTel);
        Txt[5] = vista.findViewById(R.id.txtCorreo);
        Txt[6] = vista.findViewById(R.id.txtDireccion);
        rF = vista.findViewById(R.id.radioF);
        rM = vista.findViewById(R.id.radioM);
        grupoBotones = vista.findViewById(R.id.grupoFab);
        faEliminar = vista.findViewById(R.id.idFabEliminar);
        faModificar = vista.findViewById(R.id.idFabActualizar);
       utilidades.request= Volley.newRequestQueue(Actividad);
        //Recibimos los Parametros de Nuestra Consulta de datos Anterior
        ObjetoAlumno =Actividad.getIntent().getExtras();
       if (ObjetoAlumno != null) {
           AlumnoCapturado = (Alumno) ObjetoAlumno.getSerializable("Datos");
           System.out.println("LLEGARON!!!");
            AsignarImagen();
            CargarDatos();
        }
        EventoBotonesFlotantes();
        return vista;
    }
    public void EventoBotonesFlotantes() {
        faModificar.setOnClickListener(new View.OnClickListener() {//Boton para modificar
            @Override
            public void onClick(View v) {
                if (ValidarCampos()) {
                    CargarDatosModificados();
                    String update="Nc=" + AlumnoModificado.getNumeroControl() + "& "
                            + "Nombre=" + AlumnoModificado.getNombre() + "& " +
                           "Apellidos=" + AlumnoModificado.getApellidos() + "&" +
                            "Genero=" + AlumnoModificado.getGenero() + "&" +
                            "Carrera=" + AlumnoModificado.getCarrera() + "&" +
                            "Correo=" + AlumnoModificado.getCorreo() + "&" +
                            "Direccion=" + AlumnoModificado.getDireccion() + "&" +
                           "Telefono=" + AlumnoModificado.getTelefono() + "";
                        EjecutarConsultaModificar(update,Actividad);


                }else{
                    Toast.makeText(Actividad, "Verifica tus Campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        faEliminar.setOnClickListener(new View.OnClickListener() {//Eliminar Boton
            @Override
            public void onClick(View v) {
           CargarEliminar(AlumnoCapturado.getNumeroControl(),Actividad);


            }
        });
    }
    public void EjecutarConsultaModificar(String Query, final Activity Actividad) {
        progreso = new ProgressDialog(Actividad);
        progreso.setTitle("Modificando...");
        progreso.show();
        String url = utilidades.DireccionHttp + "ModificarPDO.php?" + Query;
        System.out.println("Enviando: " + Query);
        utilidades.stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progreso.hide();
                progreso.dismiss();
                System.out.println("Imprimiendo response>> " + response);
                if (response.trim().equalsIgnoreCase("Actualizado")) {
                    new SweetAlertDialog(Actividad, SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("Exito...")
                            .setContentText("Actualizado!")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    startActivity(new Intent(Actividad, ConsultarActivity.class));
                                    Actividad.finish();//destruimos esta actividad
                                }
                            })
                            .show();

                } else if (response.trim().equalsIgnoreCase("no")) {
                    new SweetAlertDialog(Actividad, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("No Se encontro el dato!")
                            .show();
                } else {
                    new SweetAlertDialog(Actividad, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("No se Actualizo!")
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
                        .setContentText("Hubo un Error con la Peticion Volley al servidor :c!")
                        .show();
            }
        });
        utilidades.request.add(utilidades.stringRequest);

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
                    new SweetAlertDialog(Actividad, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Exito...")
                            .setContentText("Se elimino Correctamente!")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    startActivity(new Intent(getActivity(),ConsultarActivity.class));
                                    getActivity().finish();
                                }
                            })
                            .show();
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
    private void AsignarImagen() {
        if (AlumnoCapturado.getGenero().equalsIgnoreCase("Masculino")) {
            rM.setChecked(true);
            rF.setChecked(false);
            Img.setImageResource(R.drawable.varon1);
        } else if (AlumnoCapturado.getGenero().equalsIgnoreCase("Femenino")) {
            rF.setChecked(true);
            rM.setChecked(false);
            Img.setImageResource(R.drawable.usf);
        }
    }
    private void CargarDatosModificados() {
        AlumnoModificado = new Alumno();
        AlumnoModificado.setNumeroControl(Txt[0].getText().toString());
        AlumnoModificado.setNombre(Txt[1].getText().toString());
        AlumnoModificado.setApellidos(Txt[2].getText().toString());
        AlumnoModificado.setCarrera(Txt[3].getText().toString());
        AlumnoModificado.setTelefono(Txt[4].getText().toString());
        AlumnoModificado.setCorreo(Txt[5].getText().toString());
        AlumnoModificado.setDireccion(Txt[6].getText().toString());
        AlumnoModificado.setGenero(genero);
    }
    private void CargarDatos() {
        Txt[0].setText(AlumnoCapturado.getNumeroControl());
        Txt[1].setText(AlumnoCapturado.getNombre());
        Txt[2].setText(AlumnoCapturado.getApellidos());
        Txt[3].setText(AlumnoCapturado.getCarrera());
        Txt[4].setText(AlumnoCapturado.getTelefono());
        Txt[5].setText(AlumnoCapturado.getCorreo());
        Txt[6].setText(AlumnoCapturado.getDireccion());
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
    boolean ValidarCampos() {
        boolean bandera;
        if (rF.isChecked() == true) {
            genero = "Femenino";
        } else if (rM.isChecked() == true) {
            genero = "Masculino";
        } else {
            genero = "No Seleccionado";
        }
        if (!genero.equals("No Seleccionado") && VerificarCamposVacios()
                && utilidades.ValidarCorreo(Txt[5].getText().toString()) &&
                utilidades.ValidaTelefono("" + Txt[4].getText().toString())) {
            CargarDatosModificados();//CargamoslosDatos una vez que pasa la validacion

            bandera = true;
        } else {
            bandera = false;
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
