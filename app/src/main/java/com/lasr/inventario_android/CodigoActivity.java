package com.lasr.inventario_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodigoActivity extends AppCompatActivity implements Serializable {

    //final private List<String> arrayTemp = new ArrayList<String>();
    private int cantidad = 0;
    private String code = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo);
        esconderKeyboard();
//-------------------Botones-----------------------------------
        Button btnCod =findViewById(R.id.btnTipoCodigo);
        Button btnRegresar = findViewById(R.id.btnRegresar);
        Button btnTerminar = findViewById(R.id.btnTerminar);
//-------------------Accion Botones----------------------------
        ActionBtnCod(btnCod);
        ActionBtnRegresar(btnRegresar);
        //ActionBtnTerminar(btnTerminar);
//----------------------------------------------------------------------------
        TextView lblUsuario = findViewById(R.id.lblTextUsuario);
        String usuario = (String)getIntent().getSerializableExtra("usuario");
        lblUsuario.setText(usuario);

        TextView lblUbicacion = findViewById(R.id.lblTextUbicacion);
        String ubicacion = (String)getIntent().getSerializableExtra("ubicacion");
        lblUbicacion.setText(ubicacion);

        TextView lblCantidad = findViewById(R.id.lblTextCantidad);
        lblCantidad.setText("0");

        TextView lblCodigo = findViewById(R.id.lblTextUltimoCodigo);
        lblCodigo.setText("-");
//-----------------------------------------------------------------
        EditText text = findViewById(R.id.txtCodigo);
        ActionKeyPressTextCodigo(text);

        EditText textCant = findViewById(R.id.txtCantidad);
        ActionKeyPressTextCantidad(textCant);
    }

    private void ActionBtnCod(final Button boton)
    {
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String texto = boton.getText().toString();
                        if(!texto.toUpperCase().equals("COD/COD"))
                        {
                            boton.setText("COD/COD");
                            TextView view = findViewById(R.id.lblTituloCantidad);
                            EditText cant = findViewById(R.id.txtCantidad);
                            view.setVisibility(View.GONE);
                            cant.setVisibility(View.GONE);
                        }
                        else
                        {
                            boton.setText("COD/CANT");
                            TextView view = findViewById(R.id.lblTituloCantidad);
                            EditText cant = findViewById(R.id.txtCantidad);
                            view.setVisibility(View.VISIBLE);
                            cant.setVisibility(View.VISIBLE);
                        }

            }
        });
    }

    private boolean ActionKeyPressTextCodigo(EditText text)
    {
        text.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {

                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        EditText text = findViewById(R.id.txtCodigo);

                        if(!text.getText().toString().trim().equals(""))
                        {
                        Button btnCod =findViewById(R.id.btnTipoCodigo);
                        String tipos = btnCod.getText().toString();

                            TextView lblUsuario = findViewById(R.id.lblTextUsuario);
                            TextView lblUbicacion = findViewById(R.id.lblTextUbicacion);

                            if(!tipos.toUpperCase().equals("COD/COD"))
                            {
                                EditText cant = findViewById(R.id.txtCantidad);
                                /*
                                if(!cant.getText().toString().trim().equals(""))
                                {
                                    COD_CANT(lblUsuario.getText().toString(),lblUbicacion.getText().toString(),text.getText().toString(),cant.getText().toString());
                                    cant.setText("");
                                    text.setText("");
                                    text.requestFocus();
                                    return true;
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), "Ingrese la Cantidad", Toast.LENGTH_LONG).show();
                                    cant.requestFocus();
                                    return true;
                                }
                                */
                                cant.requestFocus();
                                return true;
                            }
                            else
                            {
                                COD_COD(lblUsuario.getText().toString(),lblUbicacion.getText().toString(),text.getText().toString());
                                text.setText("");
                                text.requestFocus();
                                return true;
                            }
                        }
                        else
                        {
                            return true;
                        }
                    }
                    else
                    {
                        //return CodigoActivity.super.onKeyDown(keyCode, keyevent);
                        return false;
                    }
            }
        });
        return false;
    }

    private boolean ActionKeyPressTextCantidad(EditText text)
    {
        text.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {

                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    EditText cant = findViewById(R.id.txtCantidad);

                    if(!cant.getText().toString().trim().equals(""))
                    {

                        TextView lblUsuario = findViewById(R.id.lblTextUsuario);
                        TextView lblUbicacion = findViewById(R.id.lblTextUbicacion);

                        EditText text = findViewById(R.id.txtCodigo);

                        COD_CANT(lblUsuario.getText().toString(),lblUbicacion.getText().toString(),text.getText().toString(),cant.getText().toString());
                        text.setText("");
                        cant.setText("");
                        text.requestFocus();
                        return true;
                    }
                    else
                    {
                        return true;
                    }
                }
                else
                {
                    //return CodigoActivity.super.onKeyDown(keyCode, keyevent);
                    return false;
                }
            }
        });
        return false;
    }


    private void COD_CANT(String usuario, String ubicacion, String codigo, String cant)
    {

        /*
        String codigo = text.getText().toString();
        code = codigo;
        user = lblUsuario.getText().toString();
        location = lblUbicacion.getText().toString();
        cantidad = cant.getText().toString();
        */
        cantidad += Integer.parseInt(cant);
        code = codigo;

        try
        {
            String fileNombre = "Datos" + ".txt";//like 2016_01_12.txt
            File root = new File(Environment.getExternalStorageDirectory()+File.separator+"Inventario");
            //File root = new File(Environment.getExternalStorageDirectory(), "Notes");
            if (!root.exists())
            {
                root.mkdirs();
            }
            File archivo = new File(root, fileNombre);

            FileWriter writer = new FileWriter( archivo,true);

            String line = Padding(' ',2,usuario) + ","+ Padding(' ',15,ubicacion) +","+
                    Padding(' ',25,codigo)+","+Padding('0',10,cant);
            writer.append(line+"\r\n");

            writer.flush();
            writer.close();
            //Toast.makeText(getApplicationContext(), "Archivo generado en el Almacenamiento Interno", Toast.LENGTH_SHORT).show();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }


        TextView txtCodigo = findViewById(R.id.lblTextUltimoCodigo);
        txtCodigo.setText(code);

        TextView txtCantidad = findViewById(R.id.lblTextCantidad);
        txtCantidad.setText(Integer.toString(cantidad));
        /*Limpiar();
        cant.setText("");
        text.setText("");
        cant.requestFocus();
        */
    }

    private void COD_COD(String usuario, String ubicacion, String codigo)
    {
        /*
        TextView lblUsuario = findViewById(R.id.lblTextUsuario);
        TextView lblUbicacion = findViewById(R.id.lblTextUbicacion);
        EditText text = findViewById(R.id.txtCodigo);
        */
        /*
        String codigo = text.getText().toString();
        code = codigo;
        user = lblUsuario.getText().toString();
        location = lblUbicacion.getText().toString();
        */
        String cant = "1";
        cantidad += 1;
        code = codigo;

        try
        {
            String fileNombre = "Datos" + ".txt";//like 2016_01_12.txt
            File root = new File(Environment.getExternalStorageDirectory()+File.separator+"Inventario");
            //File root = new File(Environment.getExternalStorageDirectory(), "Notes");
            if (!root.exists())
            {
                root.mkdirs();
            }
            File archivo = new File(root, fileNombre);

            FileWriter writer = new FileWriter( archivo,true);

            String line = Padding(' ',2,usuario) + ","+ Padding(' ',15,ubicacion) +","+
                        Padding(' ',25,codigo)+","+Padding('0',10,cant);

            writer.append(line+"\r\n");

            writer.flush();
            writer.close();
            //Toast.makeText(getApplicationContext(), "Archivo generado en el Almacenamiento Interno", Toast.LENGTH_SHORT).show();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        TextView txtCodigo = findViewById(R.id.lblTextUltimoCodigo);
        txtCodigo.setText(code);

        TextView txtCantidad = findViewById(R.id.lblTextCantidad);
        txtCantidad.setText(Integer.toString(cantidad));
        /*
        Limpiar();
        text.setText("");
        text.requestFocus();
        */
    }

    private void ActionBtnRegresar(Button boton)
    {
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /*
    private void ActionBtnTerminar(Button boton)
    {
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arrayTemp.size()!=0)
                {
                    String fileName = "Datos" + ".txt";//like 2016_01_12.txt


                    try
                    {
                        File root = new File(Environment.getExternalStorageDirectory()+File.separator+"Inventario");
                        //File root = new File(Environment.getExternalStorageDirectory(), "Notes");
                        if (!root.exists())
                        {
                            root.mkdirs();
                        }
                        File archivo = new File(root, fileName);

                        FileWriter writer = new FileWriter( archivo,true);

                        for(int i = 0;i<arrayTemp.size();i++)
                        {
                            String []lineTemp = arrayTemp.get(i).split(",");
                            String line = Padding(' ',2,lineTemp[0]) + ","+ Padding(' ',15,lineTemp[1]) +","+
                                    Padding(' ',25,lineTemp[2])+","+Padding('0',10,lineTemp[3]);
                            writer.append(line+"\r\n");
                        }
                        writer.flush();
                        writer.close();
                        //Toast.makeText(getApplicationContext(), "Archivo generado en el Almacenamiento Interno", Toast.LENGTH_SHORT).show();
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Lista vacia", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
*/
    private String Padding(char relleno,int largo, String palabra)
    {
        String padded = new String(new char[largo - palabra.length()]).replace('\0', relleno) + palabra;
        return padded;
    }

    public void esconderKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
