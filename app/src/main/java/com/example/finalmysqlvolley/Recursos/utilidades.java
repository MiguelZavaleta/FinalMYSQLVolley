package com.example.finalmysqlvolley.Recursos;

import android.app.Activity;
import android.app.ProgressDialog;
import com.android.volley.RequestQueue;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.ArrayList;

public class utilidades {
    /*PAra nuestro Hosting*/
    public static final String DireccionHttp = "https://androidwebfinal.000webhostapp.com/";
    /*Variables a Utilizar:*/
    public static ArrayList<Alumno> ListaAlumnos = new ArrayList<>();//Array del tipo Alumno
    public static Alumno AlumnoSeleccionado;//variable tipo Alumno
    public static Alumno AlumnoLogeado;
    //Variables Volley:
    public static ProgressDialog progreso;
    public static RequestQueue request;//Ejecuta la peticion volley
    public static JsonObjectRequest jsonStringRequest;//Capturar JSON Recibido del servidor
    public static StringRequest stringRequest;//Captura el reusltado desde el
    // servidor en este caso lo utilizamos para recibir una cadena si se ejecuto o no la peticion
    public static void InicializarRequets(Activity act) {
        request = Volley.newRequestQueue(act);
    }

    public static boolean ValidarCorreo(String val) {
        String Co = "[a-zA-Z0-9]+[-_.]*[a-zA-Z0-9]+\\@[a-zA-Z]+\\.[a-zA-Z]+";
        return (val.matches(Co) ? true : false);
    }

    public static boolean ValidaTelefono(String num) {
        return (num.matches("(\\+?[0-9]{2,3}\\-)?([0-9]{10})") ? true : false);

    }

    public static boolean ValidarTxtVacios(String txt) {
        //Preguntamos esta vacios:false==no estan, true==si estan vacios
        return (txt.trim().isEmpty());
    }

}
