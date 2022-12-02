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
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    TableLayout tableLayout;
    ArrayList<Integer> matriz=new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tableLayout=findViewById(R.id.tl);

        dialogo();

        matriz(8,10);
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
                    matriz(8, 10);
                    crearTabla(8);


                    break;
                case 1:
                    matriz(12, 30);
                    crearTabla(12);

                    break;
                case 2:
                    matriz(16,60);
                    crearTabla(16);

                    break;
            }
            }
        });
        builder.setPositiveButton("Aceptar", null);
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                matriz(8, 10);
                crearTabla(8);

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void crearTabla(int num){
        int k=0;
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
                if(matriz.get(k)!=-1){
                    Button boton=new Button(getApplicationContext());
                    boton.setId(matriz.get(k));
                    boton.setText(""+matriz.get(k));
                    boton.setTextSize(25-num);
                    //Creamos Params
                    TableRow.LayoutParams lpBoton2=new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT);
                    lpBoton2.weight=1;
                    //Asignamos Params
                    boton.setLayoutParams(lpBoton2);
                    boton.setOnLongClickListener(this::bandera2);
                    fila.addView(boton);
                }if (matriz.get(k)==-1){
                    ImageButton imagenboton = new ImageButton(getApplicationContext());
                    imagenboton.setId(View.generateViewId());


                    //Creamos params
                    TableRow.LayoutParams lpBoton = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
                    lpBoton.weight = 1;


                    //Asignamos parámetros
                    imagenboton.setLayoutParams(lpBoton);
                    imagenboton.setOnClickListener(this::bomba);
                    imagenboton.setOnLongClickListener(this::bandera);
                    imagenboton.setMaxHeight(15);
                    imagenboton.setMaxHeight(15);
                    imagenboton.setAdjustViewBounds(true);
                    imagenboton.setScaleType(ImageView.ScaleType.CENTER_CROP);


                    //Añadir botón a fila
                    fila.addView(imagenboton);
                }
            k++;}

            tableLayout.addView(fila);

        }
    }

    public void matriz(int tablero, int minas){

        for(int i=0; i<tablero*tablero; i++){
            matriz.add(0);
        }
        for(int j=0; j<minas; j++){
            matriz.set(j,-1);
        }

        Collections.shuffle(matriz);

        System.out.println(Arrays.toString(matriz.toArray()));
    }

    public void bomba(View view){
        int id=view.getId();
        ImageButton ib =findViewById(id);
        ib.setImageResource(R.drawable.bomba);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Has perdido");
        builder.setMessage("Has detonado una bomba, más suerte la próxima vez");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                recreate();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();


    }

    public boolean bandera(View view){
        int id=view.getId();
        ImageButton ib =findViewById(id);
        ib.setImageResource(R.drawable.flag);
        ib.setEnabled(false);
        return false;
    }

    public boolean bandera2(View view){
        int idB=view.getId();
        Button b=findViewById(idB);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Has perdido");
        builder.setMessage("Has puesto una bandera en un sitio sin bomba");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                recreate();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        return false;
    }
}