package com.lasr.inventario_android;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CodigoActivity extends AppCompatActivity implements Serializable {

    final private List<String> arrayTemp = new ArrayList<String>();
    private String cantidad = "";
    private String code = "";
    private String user = "";
    private String location= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo);

//-------------------Botones-----------------------------------
        Button btnCod =findViewById(R.id.btnTipoCodigo);
//-------------------Accion Botones----------------------------
        ActionBtnCod(btnCod);
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
        ActionSaveTextCodigo(text);
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
                        }
                        else
                        {
                            boton.setText("COD/CANT");
                        }

            }
        });
    }

    private boolean ActionKeyPressTextCodigo(final EditText text)
    {
        text.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                //If the keyevent is a key-down event on the "enter" button
                if(!text.getText().toString().trim().equals(""))
                {
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        Button btnCod =findViewById(R.id.btnTipoCodigo);
                        String tipos = btnCod.getText().toString();
                        if(!tipos.toUpperCase().equals("COD/COD"))
                        {
                            return true;
                        }
                        else
                        {
                            return true;
                        }
                    }
                    else
                    {
                        return false;
                    }
                }
                else
                {
                    return false;
                }
            }
        });
        return false;
    }
    private void ActionSaveTextCodigo(EditText text)
    {
        if(ActionKeyPressTextCodigo(text)!=false)
        {
            Button btnCod =findViewById(R.id.btnTipoCodigo);
            String tipos = btnCod.getText().toString();
            if(!tipos.toUpperCase().equals("COD/COD"))
            {
                COD_CANT();
            }
            else
            {
                COD_COD();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SecondActivity with an OK result
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) { // Activity.RESULT_OK

                String cantidad = data.getStringExtra("cant");
                arrayTemp.add(user+","+location+","+code+","+cantidad);
                EditText text = findViewById(R.id.txtCodigo);
                Limpiar();
                text.setText("");
                text.requestFocus();
            }
            else
            {
                EditText text = findViewById(R.id.txtCodigo);
                text.requestFocus();
            }
        }
    }

    private void COD_CANT()
    {
        /*
        TextView lblUsuario = findViewById(R.id.lblTextUsuario);
        TextView lblUbicacion = findViewById(R.id.lblTextUbicacion);
        EditText text = findViewById(R.id.txtCodigo);

        String codigo = text.getText().toString();
        code = codigo;
        user = lblUsuario.getText().toString();
        location = lblUbicacion.getText().toString();
        */

        Intent intent=new Intent(CodigoActivity.this,CantidadActivity.class);
        startActivityForResult(intent, Activity.RESULT_OK);// Activity is started with requestCode 2
    }

    private void COD_COD()
    {
        TextView lblUsuario = findViewById(R.id.lblTextUsuario);
        TextView lblUbicacion = findViewById(R.id.lblTextUbicacion);
        EditText text = findViewById(R.id.txtCodigo);

        String codigo = text.getText().toString();
        code = codigo;
        user = lblUsuario.getText().toString();
        location = lblUbicacion.getText().toString();
        cantidad = "1";

        arrayTemp.add(user+","+location+","+code+","+cantidad);
        Limpiar();
        text.setText("");
        text.requestFocus();

    }

    private void Limpiar()
    {
        cantidad = "";
        user = "";
        code="";
        location="";
    }
}
