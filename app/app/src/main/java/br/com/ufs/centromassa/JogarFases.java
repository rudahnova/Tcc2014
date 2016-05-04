package br.com.ufs.centromassa;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Vector;

import br.com.ufs.centromassa.entity.Ponto;
import br.com.ufs.centromassa.entity.Pontuacao;
import br.com.ufs.centromassa.task.SaveScoreTask;
import br.com.ufs.centromassa.util.Util;

/**
 * Created by  on 26/10/2015.
 */
public class JogarFases extends AppCompatActivity implements ISave {

    private final String next_fase = "next_fase";

    private MyDrawFases myDrawFases;
    private int fase = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(next_fase)) {
            fase = extras.getInt(next_fase);
        }
        getSupportActionBar().setSubtitle("Fase " + fase);
        myDrawFases = new MyDrawFases(this, fase);
        setContentView(myDrawFases);
        myDrawFases.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    try {
                        int x = (int) event.getX();
                        int y = (int) event.getY();

                        Ponto centroMassa = calcularCentroMassa();
                        double distancia = calcularDistanciaPontos(x, y, centroMassa);

                        int pontuacao = calcularPontuacao(distancia);
                        int pontuacaoMinima = getPontuacaoMinima();
                        checkPontuacao(pontuacao, pontuacaoMinima);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }
        });
    }


    private void checkPontuacao(final int pontuacaoUser, int pontuacaoMinima) {
        final boolean isOk = pontuacaoUser >= pontuacaoMinima;
        final int fas = fase + 1;
        String msg_fase = "Fase " + fase;
        String msg = "Sua pontuação: " + pontuacaoUser + "\n";
        msg += "Pontuação mínima: " + pontuacaoMinima + "\n";
        msg += isOk ? "Você conseguiu pontuação igual/superior a pontuação mínima da fase. \n Cliique para avançar." : "Você não conseguiu pontuação igual/superior a pontuação mínima da fase.";
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(msg_fase);
        builder.setCancelable(false);
        builder.setMessage(msg)
                .setPositiveButton(!isOk ? "OK" : ("Ir para fase " + fas),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                if (isOk) {
                                    Pontuacao pontuacao = new Pontuacao();
                                    pontuacao.setFase(fase);
                                    pontuacao.setUsuario(Util.getUser(JogarFases.this));
                                    pontuacao.setPontos(pontuacaoUser);

                                    SaveScoreTask saveScoreTask = new SaveScoreTask(JogarFases.this, JogarFases.this);
                                    saveScoreTask.execute(pontuacao);
                                } else {
                                    Bundle b = new Bundle();
                                    b.putInt(next_fase, 1);
                                    Intent intent = new Intent(JogarFases.this, JogarFases.class);
                                    intent.putExtras(b);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }).create().show();
    }

    private int getPontuacaoMinima() {
        if (fase >= 4) {
            return 60;
        }
        int value = fase * 10;
        int minima = 100 - value;
        return minima;
    }

    private Ponto calcularCentroMassa() {
        Integer cmx = 0;
        Integer cmy = 0;
        Integer cmr = 0;
        Vector<Ponto> pontos = myDrawFases.getPontos();
        Vector<Integer> raios = myDrawFases.getRaios();
        for (int i = 0; i < pontos.size(); i++) {
            cmx += pontos.get(i).x * raios.get(i);
            cmy += pontos.get(i).y * raios.get(i);
            cmr += raios.get(i);
        }
        Ponto pCM = new Ponto((cmx / cmr), (cmy / cmr));
        return pCM;
    }

    private int calcularPontuacao(Double distancia) {
        int tela = myDrawFases.getWidth() + myDrawFases.getHeight();
        Double valor = distancia / tela;
        valor = 100 * (1 - valor);
        return valor.intValue();
    }

    private double calcularDistanciaPontos(int clickX, int clickY, Ponto centroMassa) throws Exception {
        double disX = Math
                .pow(clickX - centroMassa.x.intValue(), 2);
        double disY = Math
                .pow(clickY - centroMassa.y.intValue(), 2);
        double distancia = disX + disY;
        return Math.sqrt(distancia);
    }

    @Override
    public void onResultSucess(Object object) {
        Pontuacao pontuacao = (Pontuacao) object;
        Bundle b = new Bundle();
        b.putInt(next_fase, pontuacao.getFase() + 1);
        Intent intent = new Intent(JogarFases.this, JogarFases.class);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }

    @Override
    public void onResultFaild(Object object) {
        Exception exception = (Exception) object;
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}
