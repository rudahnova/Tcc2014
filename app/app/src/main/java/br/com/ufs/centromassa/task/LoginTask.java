package br.com.ufs.centromassa.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import br.com.ufs.centromassa.MainActivity;
import br.com.ufs.centromassa.constants.Constants;
import br.com.ufs.centromassa.dto.Usuario;
import br.com.ufs.centromassa.util.Util;

/**
 * Created by Jonas on 12/01/2015.
 */
public class LoginTask extends AsyncTask<Object, Void, Boolean> {

    private Activity context;
    private Usuario usuario;
    private ProgressDialog progressDialog;
    private String cookies;


    public LoginTask(Activity context, Usuario usuario) {
        this.context = context;
        this.usuario = usuario;
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

        DefaultHttpClient httpClient = new DefaultHttpClient();


        HttpPut post = new HttpPut(Constants.URL_USER+"login");
        post.setHeader("Accept", "application/json;charset=UTF-8");
        post.setHeader("Content-type", "application/json;charset=UTF-8");


        HttpResponse response = null;
        try {
            post.setEntity(new StringEntity(Usuario.ToJSON(usuario)));
            response = httpClient.execute(post);
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
        Util.saveUser(context, usuario);
        progressDialog.dismiss();
        context.startActivity(new Intent(context, MainActivity.class));
        context.finish();
    }
}
