package com.lasr.inventario_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import java.io.Serializable;

public class UsuarioActivity extends AppCompatActivity implements Serializable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//-------------------Botones-----------------------------------
        Button btnAceptar =findViewById(R.id.btnAceptarUsuario);
        Button btnCancelar =findViewById(R.id.btnCancelarUsuario);
//-------------------Accion Botones----------------------------
        ActionBtnAceptar(btnAceptar);
        ActionBtnCancelar(btnCancelar);
    }

    private void ActionBtnAceptar(Button boton)
    {
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txt = findViewById(R.id.txtUsuario);
                String usuario = txt.getText().toString();
                startActivity(new Intent(UsuarioActivity.this,UbicacionActivity.class)
                        .putExtra("usuario",usuario));
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
