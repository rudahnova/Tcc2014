package br.com.ufs.centromassa.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import br.com.ufs.centromassa.ISaveScore;
import br.com.ufs.centromassa.util.Util;

/**
 * Created by Jonas on 14/01/2015.
 */
public class SaveScoreTask extends AsyncTask<Object, Void, Boolean> {

    private Context activity;
    private ProgressDialog progressDialog;
    private ISaveScore iSaveScore;

    public SaveScoreTask(Activity activity, ISaveScore iSaveScore) {
        this.activity = activity;
        this.iSaveScore = iSaveScore;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Salvando...");
        progressDialog.show();
    }

    @Override
    protected Boolean doInBackground(Object... params) {
        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
            Util.deleteListPontuacao();
        String msg = aBoolean ? "Salvo com sucesso." : "Erro na operação";
        iSaveScore.onResult(aBoolean, msg);
    }
}
