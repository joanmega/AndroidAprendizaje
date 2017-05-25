package com.joanmega1986.fiestas;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by joanm on 26/09/2016.
 */

public class List_row extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<Evento> items;

    public List_row (Activity activity, ArrayList<Evento> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void clear() {
        items.clear();
    }

    public void addAll(ArrayList<Evento> Evento) {
        for (int i = 0; i < Evento.size(); i++) {
            items.add(Evento.get(i));
        }
    }

    @Override
    public Object getItem(int arg0) {
        return items.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.list_row, null);
        }

        Evento e = items.get(position);

        TextView title = (TextView) v.findViewById(R.id.cabeceraEvento);
        title.setText(e.getHora() + " - " + e.getEvento());

        TextView descripcion = (TextView) v.findViewById(R.id.descripcion);
        descripcion.setText(e.getDescripcion());

        TextView lugar = (TextView) v.findViewById(R.id.lugar);
        lugar.setText("Lugar: " + e.getLugar());

        return v;
    }
}