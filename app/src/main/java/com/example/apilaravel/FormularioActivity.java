package com.example.apilaravel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FormularioActivity extends AppCompatActivity {

    EditText edtCodigo, edtEmpleado, edtTelefono, edtCorreo, edtDireccion, edtDepartamento;
    Button btnEditar;
    ImageButton btnVisualizar;
    Button btnBuscar;
    Button btnEliminar;
    Button btnRegistrar;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        edtCodigo = findViewById(R.id.edtCodigo);
        edtEmpleado = findViewById(R.id.edtEmpleado);
        edtTelefono = findViewById(R.id.edtTelefono);
        edtCorreo = findViewById(R.id.edtCorreo);
        edtDireccion = findViewById(R.id.edtDireccion);
        edtDepartamento = findViewById(R.id.edtDepartamento);
        btnEditar = findViewById(R.id.btnEditar);
        btnVisualizar = findViewById(R.id.btnVisualizar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnBuscar = findViewById(R.id.btnBuscar);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editarEmpleado("http://18.222.133.79/api/edit-empleados/31?codigo_empleado=55&nombre_empleado=prueba por favor&numero_telefono=654321&correo=pruebafunciona@gmail.com&direccion=Puerto Barrios&departamento=Guatemala&control=api");
            }
        });

        btnVisualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                visualizarEmpleados("http://18.222.133.79/api/get-empleados");
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarEmpleado("http://18.222.133.79/api/delete-empleados/31");
            }

        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearEmpleado("http://18.222.133.79/api/save-empleados");
            }
        });

    btnBuscar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            buscarEmpleado("http://18.222.133.79/api/get-empleados/15");
        }
    });
}

    private void editarEmpleado(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "EMPLEADO ACTUALIZADO", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("codigo_empleado", edtCodigo.getText().toString());
                parametros.put("nombre_empleado", edtEmpleado.getText().toString());
                parametros.put("numero_telefono", edtTelefono.getText().toString());
                parametros.put("correo", edtCorreo.getText().toString());
                parametros.put("direccion", edtDireccion.getText().toString());
                parametros.put("departamento", edtDepartamento.getText().toString());
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void crearEmpleado(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "EMPLEADO CREADO", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                String tipocontrol = "api";
                parametros.put("codigo_empleado", edtCodigo.getText().toString());
                parametros.put("nombre_empleado", edtEmpleado.getText().toString());
                parametros.put("numero_telefono", edtTelefono.getText().toString());
                parametros.put("correo", edtCorreo.getText().toString());
                parametros.put("direccion", edtDireccion.getText().toString());
                parametros.put("departamento", edtDepartamento.getText().toString());
                parametros.put("control", tipocontrol);
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void visualizarEmpleados(String URL) {
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Toast.makeText(getApplicationContext(), "ListadoEmpleadosActivity", Toast.LENGTH_SHORT).show();

                // Verificar si hay al menos un empleado en la respuesta
                if (response.length() > 0) {
                    // Limpiar campos antes de mostrar nuevos datos
                    limpiarCampos();

                    // Iterar a través de todos los objetos en el array
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);

                            // Muestra los datos según sea necesario
                            String datosEmpleado = "Código: " + jsonObject.getString("codigo_empleado") +
                                    "\nNombre: " + jsonObject.getString("nombre_empleado") +
                                    "\nTeléfono: " + jsonObject.getString("numero_telefono") +
                                    "\nCorreo: " + jsonObject.getString("correo") +
                                    "\nDirección: " + jsonObject.getString("direccion") +
                                    "\nDepartamento: " + jsonObject.getString("departamento") +
                                    "\n\n";

                            mostrarDatosEnListView(datosEmpleado);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    Intent intent = new Intent(FormularioActivity.this, ListadoEmpleadosActivity.class);
                    intent.putExtra("jsonArray", response.toString());
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), "No se encontraron empleados", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error", "Error: " + error.getMessage(), error);

                Toast.makeText(getApplicationContext(), "Error de red. Por favor, inténtalo de nuevo.", Toast.LENGTH_SHORT).show();
            }
    });

        Volley.newRequestQueue(this).add(getRequest);
    }

    // Método para limpiar los campos de texto
    private void limpiarCampos() {
        edtCodigo.setText("");
        edtEmpleado.setText("");
        edtTelefono.setText("");
        edtCorreo.setText("");
        edtDireccion.setText("");
        edtDepartamento.setText("");
    }

    // Método para mostrar los datos en la interfaz
    private void mostrarDatosEnListView(String datos) {
        TextView txtDatos = findViewById(R.id.txtDatos);
        txtDatos.append(datos);
    }

    private void eliminarEmpleado(String URL) {
        StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(getApplicationContext(), "EMPLEADO ELIMINADO", Toast.LENGTH_SHORT).show();

                limpiarCampos();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR", "Error al intentar eliminar empleado: " + error.getMessage());
            }
        });

        Volley.newRequestQueue(this).add(deleteRequest);
    }

    private void buscarEmpleado(String URL){
        StringRequest postRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "EMPLEADO ENCONTRADO", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    edtCodigo.setText(jsonObject.getString("codigo_empleado"));
                    edtEmpleado.setText(jsonObject.getString("nombre_empleado"));
                    edtTelefono.setText(jsonObject.getString("numero_telefono"));
                    edtCorreo.setText(jsonObject.getString("correo"));
                    edtDireccion.setText(jsonObject.getString("direccion"));
                    edtDepartamento.setText(jsonObject.getString("departamento"));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR", error.getMessage());
            }
        }
        );
        Volley.newRequestQueue(this).add(postRequest);
    }

    public void regresarMenu(View view) {
        Intent regresarmenu = new Intent(this, MainActivity.class);
        startActivity(regresarmenu);
    }

    public void mostrarDatosEnListView(View view) {
        Log.d("TAG", "Método ejecutado");
        Intent intent = new Intent(this, ListadoEmpleadosActivity.class);
        startActivity(intent);
    }


}

