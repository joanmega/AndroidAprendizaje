package com.joanmega1986.fiestas;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Sabado.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Sabado#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Sabado extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ArrayAdapter<String> adaptador;
    ListView list;

    ArrayList<Evento> eventos = new ArrayList<Evento>();

    private OnFragmentInteractionListener mListener;

    public Sabado() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Miercoles.
     */
    // TODO: Rename and change types and number of parameters
    public static Sabado newInstance(String param1, String param2) {
        Sabado fragment = new Sabado();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static Sabado newInstance() {
        Sabado fragment = new Sabado();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_eventos, container, false);

        list = (ListView) view.findViewById(R.id.listEvent);


        eventos.clear();
//        String url = "http://192.168.1.42/fiestas/querry.php?dia=6";
        String url = "http://joanmegapruebas.esy.es/query.php?dia=6";
        new ConsultarDatos().execute(url);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {

                // TODO Auto-generated method stub
                Intent i = new Intent(getActivity(), ModificarEvento.class);
                Evento e = (Evento) list.getItemAtPosition(position);

                int numEvento = e.getNumEvento();
                String evento = e.getEvento();
                String descripcion = e.getDescripcion();
                String lugar = e.getLugar();
                String dia = e.getDia();
                String hora = e.getHora();

                i.putExtra("numEvento", numEvento);
                i.putExtra("evento", evento);
                i.putExtra("descripcion", descripcion);
                i.putExtra("lugar", lugar);
                i.putExtra("dia", dia);
                i.putExtra("hora", hora);

                startActivity(i);
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    // Given a URL, establishes an HttpUrlConnection and retrieves
    // the web page content as a InputStream, which it returns as
    // a string.
    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 5000;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("respuesta", "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    // Reads an InputStream and converts it to a String.
    public String readIt(InputStream stream) throws IOException, UnsupportedEncodingException {

        Reader reader = new InputStreamReader(stream, "UTF-8");
        String line = "";
        StringBuilder sb = new StringBuilder();
        BufferedReader rd = new BufferedReader(reader);
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    private class ConsultarDatos extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            JSONArray resultado = null;
            result = result.trim();
            eventos.clear();

            try {
                Evento evento;
                JSONObject object = new JSONObject(result);
                resultado = object.getJSONArray("Evento");
                for (int i = 0; i < resultado.length(); i++) {
                    JSONObject e = resultado.getJSONObject(i);
                    evento = new Evento(e.optInt("numEvento"), e.optString("evento"), e.optString("descripcion"), e.optString("lugar"), e.optString("dia"), e.optString("hora"));
                    eventos.add(evento);
                }

                fillListView(eventos);

            } catch (JSONException e) {
                Evento evento = new Evento();
                eventos.add(evento);
                fillListView(eventos);

                System.out.println( "Error: " + e);
                Toast.makeText(getActivity(), "Error: " + e, Toast.LENGTH_LONG).show();
            }
        }

        private void fillListView(ArrayList<Evento> eventos) {
            List_row adapter = new List_row(getActivity(), eventos);
            list.setAdapter(adapter);
        }

    }
}
