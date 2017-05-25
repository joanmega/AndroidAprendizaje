package com.joanmega1986.fiestas;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

public class AnadirEvento extends AppCompatActivity {

    Button bReset, bAnadir;
    EditText tEvento, tDescripcion, tLugar;
    DatePicker tDia;
    TimePicker tHora;
    int hora, minutos, anio, mes, dia;
    String evento, descripcion, lugar, horaFinal, diaFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_evento);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Calendar calendario = Calendar.getInstance();

        bReset = (Button) findViewById(R.id.bReset);
        bAnadir = (Button) findViewById(R.id.bAnadir);
        tEvento = (EditText) findViewById(R.id.tEvento);
        tDescripcion = (EditText) findViewById(R.id.tDescripcion);
        tLugar = (EditText) findViewById(R.id.tLugar);
        tDia = (DatePicker) findViewById(R.id.tDia);
        tHora = (TimePicker) findViewById(R.id.tHora);
        tDia.init(calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH), calendario.get(Calendar.DAY_OF_MONTH), null);
        tHora.setIs24HourView(true);


        hora = calendario.get(Calendar.HOUR_OF_DAY);
        minutos = calendario.get(Calendar.MINUTE);
        anio = calendario.get(Calendar.YEAR);
        mes = calendario.get(Calendar.MONTH);
        dia = calendario.get(Calendar.DAY_OF_MONTH);

        tHora.setCurrentHour(hora);
        tHora.setCurrentMinute(minutos);
        bReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tEvento.setText("");
                tDescripcion.setText("");
                tLugar.setText("");
                tDia.init(anio, mes, dia, null);
                tHora.setCurrentHour(hora);
                tHora.setCurrentMinute(minutos);
            }
        });


        bAnadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                evento = tEvento.getText().toString();
                descripcion = tDescripcion.getText().toString();
                lugar = tLugar.getText().toString();

                evento = evento.replace(" ", "%20");
                descripcion = descripcion.replace(" ", "%20");
                lugar = lugar.replace(" ", "%20");


                diaFinal = tDia.getYear() +"/"+ (tDia.getMonth()+1) + "/" + tDia.getDayOfMonth();

                if (tHora.getCurrentHour()==0){
                    horaFinal = "24:" + tHora.getCurrentMinute();
                }else{
                    horaFinal = tHora.getCurrentHour() + ":" + tHora.getCurrentMinute();
                }

                if(!evento.equals("") && !descripcion.equals("") && !lugar.equals("")) {
                    //        String url = "http://192.168.1.42/fiestas/querry.php?dia=4";
                    String url = "http://joanmegapruebas.esy.es/insertEvent.php?&evento=" + evento + "&descripcion=" + descripcion + "&lugar=" + lugar + "&dia=" + diaFinal + "&hora=" + horaFinal;
                    new ConsultarDatos().execute(url);

                }else{
                    Toast.makeText(getApplicationContext(), "Inserta todos los campos correctamente.", Toast.LENGTH_SHORT).show();
                }

                finish();

            }
        });
    }



    // Given a URL, establishes an HttpUrlConnection and retrieves
    // the web page content as a InputStream, which it returns as
    // a string.
    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        // Only display the first 5000 characters of the retrieved
        // web page content.
        int len = 5000;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setReadTimeout(15000); // milliseconds
            conn.setConnectTimeout(15000 ); // milliseconds

            System.out.println("URL: " + url);

            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("respuesta", "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
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
    public String readIt(InputStream stream, int len) throws IOException {

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
            if(result.equals("1")){
                Toast.makeText(getApplicationContext(), "Evento modificado correctamente.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
