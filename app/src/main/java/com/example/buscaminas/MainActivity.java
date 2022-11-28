package com.example.buscaminas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tableLayout=findViewById(R.id.tl);

        dialogo();

        crearTabla(8);
    }

    public void dialogo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Instrucciones");
        builder.setMessage("Esta versión tiene 3 niveles de dificultad que puedes cambiar desde el menú que cambian la extension del tablero." +
                "\n Para ganar despeja el tablero sin detonar ninguna bomba");
        builder.setPositiveButton("Aceptar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menudificultad, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int n=item.getItemId();
        switch(n){
            case R.id.nuevo:
                recreate();
                return true;
            case R.id.configurar:
                configurarDificultad();
                return true;
            case R.id.info:
                dialogo();

        }
        return super.onOptionsItemSelected(item);
    }
    public void configurarDificultad(){
        final String[] dificultad={"Amateur", "Normal", "Dificil"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Configura el juego");
        builder.setSingleChoiceItems(dificultad, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int eleccion=i;
            switch(eleccion){
                case 0:
                    crearTabla(8);
                    break;
                case 1:
                    crearTabla(12);
                    break;
                case 2:
                    crearTabla(16);
                    break;
            }
            }
        });
        builder.setPositiveButton("Aceptar", null);
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                crearTabla(8);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void crearTabla(int num){
        tableLayout.removeAllViews();
        for(int i=0;i<num;i++){
            //Creamos fila
            TableRow fila=new TableRow(getApplicationContext());

            // Creamos parámetros
            TableLayout.LayoutParams lpFila=new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.MATCH_PARENT);
            lpFila.weight=1;

            //Asignamos parámetros
            fila.setLayoutParams(lpFila);

            for(int j=0;j<num;j++){
                // Creamos botón
                ImageButton imagenboton=new ImageButton(getApplicationContext());
                imagenboton.setId(View.generateViewId());


                //Creamos params
                TableRow.LayoutParams lpBoton=new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT);
                lpBoton.weight=1;

                //Asignamos parámetros
                imagenboton.setLayoutParams(lpBoton);

                //Añadir botón a fila
                fila.addView(imagenboton);
            }

            tableLayout.addView(fila);

        }
    }
}