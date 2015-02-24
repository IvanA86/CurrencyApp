package com.ia.app.currencyexchangerate;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;

import clases.Moneda;


public class MainActivity extends ListActivity {
    //Falta ejecutarlo no se ha ejecutado ni una sola vez crear AVD

    ArrayList<Moneda> listMoneda;
    String moneda, monedaDest;
    double tasaCambio;
    double num1, num2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinMonedaOriginal = (Spinner) findViewById(R.id.spinMonedaOriginal);
        final Spinner spinMonedaDestino = (Spinner) findViewById(R.id.spinmonedaDestino);
        final EditText numero1 = (EditText) findViewById(R.id.txtMonedaOriginal);
        final TextView numero2 = (TextView) findViewById(R.id.txtMonedaDestino);

        //Coloco el formato que quiero
        listMoneda = getDataJSON();

        spinnerAdapter adapter = new spinnerAdapter(listMoneda, this);
        spinnerAdapter adapter2 = new spinnerAdapter(listMoneda, this);

        spinMonedaDestino.setAdapter(adapter);
        spinMonedaOriginal.setAdapter(adapter2);

        spinMonedaDestino.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                moneda = spinMonedaDestino.getSelectedItem().toString();
                convierteValores(moneda, monedaDest, tasaCambio, numero1, numero2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Si no se ha seleccionado nada borro los valores
                moneda = "";
            }
        });
    }

    private void convierteValores(String moneda, String monedaDest, double tasaCambio, EditText numero1, TextView numero2) {
        //Cuando llego aquí tengo todos los valores?
        if ((moneda.length() != 0) && (monedaDest.length() != 0)) {
            //Busco en el array de todos los elementos que tengo el indice del pais para coger el id de la moneda
            String idMoneda1 = buscaID(moneda);
            String idMoneda2 = buscaID(monedaDest);
            //Una vez hecho eso busco la tasa de cambio
            tasaCambio = updateTasaCambio(idMoneda1, idMoneda2);
            //Con la nueva tasa de cambio actualizo el valor introducido
            if ((isNumero(numero1)) && (isNumero(numero2))) {
                double aux = Double.valueOf(numero1.getText().toString()) * tasaCambio;
                numero2.setText(String.valueOf(aux));
            } else numero2.setText("0");
        } else {
            numero2.setText("0");
        }
    }

    private boolean isNumero(TextView numero1) {
        String val = numero1.getText().toString();
        try {
            double v = Double.parseDouble(val);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private double updateTasaCambio(String idMoneda1, String idMoneda2) {
        //TODO cuando tenga la librería de JSON metida
        String contenido = null;
        String aux = null;
        double res = 0.0;
        String dir = "http://www.freecurrencyconverterapi.com/api/v3/convert?q=" + idMoneda1 + "_" + idMoneda2 + "&compact=y";

        URL url = null;
        try {
            url = new URL(dir);
            URLConnection con = url.openConnection();
            InputStreamReader is = null;
            if (con != null) {
                con.setReadTimeout(60 * 1000);
            }
            //Si la página tiene contenido creo el buffer
            if ((con != null) && (con.getInputStream() != null)) {
                is = new InputStreamReader(con.getInputStream(),
                        Charset.defaultCharset());
                BufferedReader br = new BufferedReader(is);

                if (br != null) {
                    // Guardar el contenido de la página en la variable

                    while ((aux = br.readLine()) != null)
                        contenido = contenido + aux;
                }
                br.close();
            }
            is.close();

            JSONObject jobjeto = new JSONObject(contenido);
            jobjeto = jobjeto.getJSONObject("USD_EUR");
            res = jobjeto.getDouble("val");
            return res;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return -1.0;
        } catch (IOException e) {
            e.printStackTrace();
            return -1.0;
        } catch (JSONException e) {
            e.printStackTrace();
            return -1.0;
        }
    }

    private String buscaID(String moneda) {
        Moneda m;
        //Tengo que encontrar en el ArrayList<Moneda> el elemento con ese nombre
        for (int i = 0; i < listMoneda.size(); i++) {
            m = listMoneda.get(i);
            if (m.getNomMoneda().equals(moneda)) {
                return m.getIdMoneda();
            }
        }
        //¿Código de error para el país?
        //TODO mirar!
        return "Error";
    }

    private ArrayList<Moneda> getDataJSON() {
        ArrayList<Moneda> res = new ArrayList<>();
        return res;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
