package com.rendi.tutorial.data_lokasi;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private Button btn_simpan;
    private EditText namalokasi;
    private EditText latitude1;
    private EditText longitude1;
    private  EditText alamat1;
    private EditText dataparam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        namalokasi= (EditText)findViewById(R.id.txt_namalokasi);
        latitude1 = (EditText)findViewById(R.id.txt_lati);
        longitude1 = (EditText)findViewById(R.id.txt_long);
        alamat1 = (EditText)findViewById(R.id.txt_alamat);
        dataparam = (EditText)findViewById(R.id.txt_parameter);
        btn_simpan = (Button)findViewById(R.id.btn_simpan);

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nama_lokasi = namalokasi.getText().toString().trim();
                final String latitude = longitude1.getText().toString().trim();
                final String longitude = longitude1.getText().toString().trim();
                final String alamat = alamat1.getText().toString().trim();
                final String data_param = dataparam.getText().toString().trim();

                //pembuatan class asyntask yang berfungsi untuk koneksi ke database server

                class TambahData extends AsyncTask<Void,Void,String> {

                    ProgressDialog loading;

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        loading = ProgressDialog.show(MainActivity.this,"Proses Kirim Data...","Wait...",false,false);

                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        loading.dismiss();
                        Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    protected String doInBackground(Void... v) {
                        HashMap<String,String> params = new HashMap<>();
                        //Sesuaikan bagian ini dengan field di tabel mahasiswa
                        params.put(config.KEY_EMP_nama_lokasi,nama_lokasi);
                        params.put(config.KEY_EMP_latitude,latitude);
                        params.put(config.KEY_EMP_longitude,longitude);
                        params.put(config.KEY_EMP_alamat,alamat);
                        params.put(config.KEY_EMP_data_parameter,data_param);
                        RequestHandler rh = new RequestHandler();
                        String res = rh.sendPostRequest(config.URL_ADD,params);
                        return res;

                    }
                }
                //jadikan class tambahdata sebagai object baru
                TambahData ae = new TambahData();
                ae.execute();
            }
        });
    }
}
