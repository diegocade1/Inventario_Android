package com.lasr.inventario_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.Serializable;

public class UbicacionActivity extends AppCompatActivity implements Serializable{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion);

//-------------------Botones--------------------------------------------------------
        Button btnAceptar =findViewById(R.id.btnAceptarUbicacion);
        Button btnCancelar =findViewById(R.id.btnCancelarUbicacion);
//-------------------Accion Botones-------------------------------------------------
        ActionBtnAceptar(btnAceptar);
        ActionBtnCancelar(btnCancelar);
//----------------------------------------------------------------------------------
        //String usuario = getIntent().getSerializableExtra("usuario").toString();
        TextView label = findViewById(R.id.lblUser);
        String usuario = (String)getIntent().getSerializableExtra("usuario");
        label.setText(label.getText() + usuario);
    }

    private void ActionBtnAceptar(Button boton)
    {
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txt = findViewById(R.id.txtUbicacion);
                String ubicacion = txt.getText().toString();
                String usuario = (String)getIntent().getSerializableExtra("usuario");
                startActivity(new Intent(UbicacionActivity.this,CodigoActivity.class)
                        .putExtra("usuario",usuario)
                        .putExtra("ubicacion",ubicacion));
                esconderKeyboard();
            }
        });
    }
    private void ActionBtnCancelar(Button boton)
    {
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void esconderKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}