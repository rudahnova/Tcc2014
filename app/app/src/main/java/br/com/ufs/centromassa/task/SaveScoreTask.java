package br.com.ufs.centromassa.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import br.com.ufs.centromassa.ISave;
import br.com.ufs.centromassa.constants.Constants;
import br.com.ufs.centromassa.control.PontuacaoControl;
import br.com.ufs.centromassa.entity.Pontuacao;
import br.com.ufs.centromassa.util.Util;

/**
 * Created by  on 14/01/2015.
 */
public class SaveScoreTask extends AsyncTask<Pontuacao, Void, Object> {

    private Context context;
    private ProgressDialog progressDialog;
    private ISave iSave;

    public SaveScoreTask(Context context, ISave iSave) {
        this.context = context;
        this.iSave = iSave;
    }


    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Salvando Pontuação...");
        progressDialog.show();
    }

    @Override
    protected Object doInBackground(Pontuacao... params) {
        Pontuacao pontuacao = params[0];

        HttpClient httpClient = new DefaultHttpClient();

        HttpPut put = new HttpPut(Constants.URL_PONTUACAO);
        put.setHeader("Accept", "application/json;charset=UTF-8");
        put.setHeader("Content-type", "application/json;charset=UTF-8");

        try {

            PontuacaoControl pontuacaoControl = new PontuacaoControl(context);
            Pontuacao insert = pontuacaoControl.insert(pontuacao);

            put.setEntity(new StringEntity(Pontuacao.ToJSON(pontuacao)));
            HttpResponse response = httpClient.execute(put);
            String s = EntityUtils.toString(response.getEntity());
            Pontuacao.fromJSON(new JSONObject(s));
            return insert;
        } catch (Exception e) {
            e.printStackTrace();
            return new Exception("Informações salvas localmente. Falha ao acessar servidor.");
        }
    }

    @Override
    protected void onPostExecute(Object pontuacao) {
        progressDialog.dismiss();
        if(pontuacao != null){
            iSave.onResultSucess(pontuacao);
        }else{
            iSave.onResultFaild(pontuacao);
        }
    }
}
