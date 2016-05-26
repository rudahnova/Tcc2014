package br.com.ufs.centromassa.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import br.com.ufs.centromassa.ISave;
import br.com.ufs.centromassa.constants.Constants;
import br.com.ufs.centromassa.entity.Usuario;
import br.com.ufs.centromassa.util.Util;

/**
 * Created by  on 12/01/2015.
 */
public class LoginTask extends AsyncTask<Object, Void, Boolean> {

    private Activity context;
    private ISave iSave;
    private Usuario usuario;
    private ProgressDialog progressDialog;
    private String cookies;


    public LoginTask(ISave iSave,Activity context, Usuario usuario) {
        this.context = context;
        this.usuario = usuario;
        this.iSave = iSave;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Conectando...");
        progressDialog.show();
    }

    @Override
    protected Boolean doInBackground(Object... params) {

        HttpClient httpClient = new DefaultHttpClient();


        HttpPut put = new HttpPut(Constants.URL_USER+"login");
        put.setHeader("Accept", "application/json;charset=UTF-8");
        put.setHeader("Content-type", "application/json;charset=UTF-8");


        HttpResponse response = null;
        try {
            put.setEntity(new StringEntity(Usuario.ToJSON(usuario)));
            response = httpClient.execute(put);
            String s = EntityUtils.toString(response.getEntity());
            usuario = Usuario.fromJSON(new JSONObject(s));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        if (aBoolean != null && aBoolean) {
            Util.saveUser(context, usuario);
            progressDialog.dismiss();
            iSave.onResultSucess(usuario);
        }else{
            iSave.onResultFaild(usuario);
        }
    }
}
