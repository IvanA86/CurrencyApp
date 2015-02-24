package com.ia.app.currencyexchangerate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import clases.Moneda;

/**
 * Created by Iván Agós on 23/02/2015.
 */
public class spinnerAdapter extends BaseAdapter {

    ArrayList<Moneda> list;
    Context contexto;
    private LayoutInflater li;

    public spinnerAdapter(ArrayList<Moneda> lista, Context context) {
        super();
        this.list = lista;
        this.contexto = context;
        li = LayoutInflater.from(this.contexto);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Contenedor c = new Contenedor();

        if (convertView == null) {
            convertView = li.inflate(R.layout.row, null);
            c.nombreMoneda = (TextView) convertView.findViewById(R.id.txtMoneda);
            c.nombrePais = (TextView) convertView.findViewById(R.id.txtPais);

            convertView.setTag(c);
        } else {
            c = (Contenedor) convertView.getTag();
        }

        Moneda m = (Moneda) getItem(position);
        c.nombreMoneda.setText(m.getNomMoneda());
        c.nombrePais.setText(m.getNomPais());

        return convertView;
    }
}

class Contenedor {
    TextView nombrePais;
    TextView nombreMoneda;
}
