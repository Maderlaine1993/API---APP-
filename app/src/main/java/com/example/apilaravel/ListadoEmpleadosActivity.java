package com.example.apilaravel;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.Toast;
import android.widget.TextView;

import java.util.ArrayList;

public class ListadoEmpleadosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_empleados);

        // Obtener el JSON de la actividad anterior (asumimos que se pasa como String)
        String jsonString = getIntent().getStringExtra("jsonArray");

        // Mostrar los datos en el ListView
        mostrarDatosEnListView(jsonString);
    }

    private void mostrarDatosEnListView(String jsonString) {
        try {
            JSONArray jsonArray = new JSONArray(jsonString);

            ArrayList<String> listaDatos = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    // Agregar los datos al ArrayList
                    String datosEmpleado = "Código: " + jsonObject.getString("codigo_empleado") +
                            "\nNombre: " + jsonObject.getString("nombre_empleado") +
                            "\nTeléfono: " + jsonObject.getString("numero_telefono") +
                            "\nCorreo: " + jsonObject.getString("correo") +
                            "\nDirección: " + jsonObject.getString("direccion") +
                            "\nDepartamento: " + jsonObject.getString("departamento") +
                            "\n\n";

                    listaDatos.add(datosEmpleado);
                } catch (JSONException e) {
                    // Manejar la excepción según sea necesario
                    e.printStackTrace();
                }
            }

            // Configurar el ArrayAdapter para el ListView
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaDatos);

            // Obtener el ListView desde el layout
            ListView listView = findViewById(R.id.listView);

            // Configurar el adaptador para el ListView
            listView.setAdapter(adapter);

            // Mostrar datos en el TextView
            mostrarDatosEnInterfaz(listaDatos);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al procesar los datos JSON", Toast.LENGTH_SHORT).show();
        }
    }

    private void mostrarDatosEnInterfaz(ArrayList<String> listaDatos) {
        // Obtener el TextView desde el layout
        TextView txtDatos = findViewById(R.id.txtDatos);

        // Limpiar datos anteriores
        txtDatos.setText("");

        // Concatenar y mostrar los nuevos datos
        for (String datos : listaDatos) {
            txtDatos.append(datos);
        }
    }

}
