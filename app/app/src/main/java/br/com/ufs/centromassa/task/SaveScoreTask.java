package br.com.ufs.centromassa.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import br.com.ufs.centromassa.ISave;
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
        try {
            PontuacaoControl pontuacaoControl = new PontuacaoControl(context);
            Pontuacao insert = pontuacaoControl.insert(params[0]);
            return insert;
        } catch (Exception e) {
            return e;
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
