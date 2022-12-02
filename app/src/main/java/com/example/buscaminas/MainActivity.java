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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author ernesto
 */
public class MainActivity extends AppCompatActivity {
    //Declaracion de variables
    TableLayout tableLayout;
    ArrayList<Integer> matriz=new ArrayList<Integer>();
    int bandera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Relacionamos la variable con el tablelayout
        tableLayout=findViewById(R.id.tl);
        //Mostramos el dialogo de instrucciones
        dialogo();
        //creamos la matriz
        matriz(8,10);
        //Creamos el tablero
        crearTabla(8);

    }

    /**
     * Creacion de metodo para mostrar las instrucciones
     */
    public void dialogo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Instrucciones");
        builder.setMessage("Esta versión tiene 3 niveles de dificultad que puedes cambiar desde el menú que cambian la extension del tablero." +
                "\n Para ganar despeja el tablero sin detonar ninguna bomba");
        builder.setPositiveButton("Aceptar", null);

        AlertDialog dialog = builder.create();
        //Evitamos que el usuario pueda seguir jugando si intenta tocar fuera del alert dialog
        dialog.setCancelable(false);
        dialog.show();
    }

    /**
     * Inflamos el menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menudificultad, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Creamos los casos para los diferentes items seleccionados
     * @param item
     * @return
     */
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

    /**
     * Metodo para configurar la dificultad
     */
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
        //Evitamos que el usuario pueda seguir jugando si intenta tocar fuera del alert dialog
        dialog.setCancelable(false);
        dialog.show();
    }

    /**
     * Metodo para crear el tablelayout y los botones mediane codigo
     * @param num
     */
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
                    //Asignamos el id
                    boton.setId(View.generateViewId());

                    //Asignamos el tamaño del texto
                    boton.setTextSize(25-num);

                    //Creamos Params
                    TableRow.LayoutParams lpBoton2=new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT);
                    lpBoton2.weight=1;

                    //Asignamos Params
                    boton.setLayoutParams(lpBoton2);
                    int finalK = k;
                    boton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v)
                        {
                            boton.setText(""+ matriz.get(finalK));
                            boton.setEnabled(false);
                        }
                    });
                    //Creamos el metodo para un click largo
                    boton.setOnLongClickListener(this::bandera2);

                    //Añadimos el boton en la fila
                    fila.addView(boton);

                }if (matriz.get(k)==-1){
                    //Creamos imagebutton
                    ImageButton imagenboton = new ImageButton(getApplicationContext());
                    imagenboton.setId(View.generateViewId());

                    //Creamos params
                    TableRow.LayoutParams lpBoton = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
                    lpBoton.weight = 1;

                    //Asignamos parámetros
                    imagenboton.setLayoutParams(lpBoton);

                    //Creamos el metodo para mostrar la bomba
                    imagenboton.setOnClickListener(this::bomba);


                    //Creamos el metodo para mostrar la bandera

                    imagenboton.setOnLongClickListener(new View.OnLongClickListener() {

                        @Override
                        public boolean onLongClick(View view) {
                            imagenboton.setImageResource(R.drawable.flag);
                            imagenboton.setEnabled(false);
                            bandera++;
                            if (num == 8) {
                            bandera(10,bandera);
                            }else if (num==12){
                                bandera(30, bandera);
                            }else if(num==16){
                                bandera(60, bandera);
                            }
                            return true;
                        }
                    });

                    //Añadimos dimensiones de las imagenes
                    imagenboton.setMaxHeight(15);
                    imagenboton.setMaxWidth(15);
                    imagenboton.setAdjustViewBounds(true);
                    imagenboton.setScaleType(ImageView.ScaleType.CENTER_CROP);

                    //Añadir botón a fila
                    fila.addView(imagenboton);
                }
            k++;}
            tableLayout.addView(fila);
        }
    }
    /**
     * Metodod para crear la matriz
     * @param tablero
     * @param minas
     */
    public void matriz(int tablero, int minas){
        matriz.clear();
        for(int i=0; i<tablero*tablero; i++){
            matriz.add(0);
        }
        for(int l=0; l<minas; l++){
            matriz.set(l,-1);
        }
        Collections.shuffle(matriz);
    }

    /**
     * Metodo para mostrar la bomba
     * @param view
     */
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
        //Evitamos que el usuario pueda seguir jugando si intenta tocar fuera del alert dialog
        dialog.setCancelable(false);
        dialog.show();
    }

    /**
     * comprobamos que se hayan puesto el mismo numero de banderas que de bombas en los imagebutton
     * @param bomba
     * @param banderas
     */
    public void bandera(int bomba, int banderas){
        if(bomba==banderas){
            Toast.makeText(this, "Has ganado", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Metodo para mostrar la bandera
     * @param view
     * @return
     */
    public boolean bandera2(View view){
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
        //Evitamos que el usuario pueda seguir jugando si intenta tocar fuera del alert dialog
        dialog.setCancelable(false);
        dialog.show();
        return true;
    }
}