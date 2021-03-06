package br.com.ufs.centromassa;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import br.com.ufs.centromassa.entity.Ponto;
import br.com.ufs.centromassa.entity.Usuario;
import br.com.ufs.centromassa.util.Util;

public class Jogar extends AppCompatActivity {

    private static final String TAG = "DEBUG";
    private int[] x;
    private int[] y;
    private int[] raios;
    private int[] cores;
    private Ponto centroMassa;
    private Ponto click;
    private MyDraw draw;

    private final int VER_PONTOS = 0;
    private final int AJUDA = 1;
    private final int PARAR_DESENHO = 2;
    private final int ATUALIZAR = 3;

    private Boolean isNew;
    private boolean isDesenheVoce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            checkDesenheVoce(b);


            raios = b.getIntArray("raios");
            cores = b.getIntArray("cores");
            centroMassa = (Ponto) b.getSerializable("centroMassa");
            click = (Ponto) b.getSerializable("click");

            x = b.getIntArray("pontosx");
            y = b.getIntArray("pontosy");

            salveScore();

            if (b.containsKey("isNew")) {
                Log.d(TAG, "isNew");
                isNew = b.getBoolean("isNew");
                draw = new MyDraw(this, x, y, raios, cores, centroMassa, click,
                        isNew);
                draw.setIsBackToDesenheVoce(isDesenheVoce);
            } else {
                Log.d(TAG, "isNotNew");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                draw = new MyDraw(this, x, y, raios, cores, centroMassa, click);
                draw.setIsBackToDesenheVoce(isDesenheVoce);
            }
            setContentView(draw);
        } else {
            draw = new MyDraw(this);
            setContentView(draw);
        }
    }

    private void checkDesenheVoce(Bundle b) {
        if (b.containsKey("desenheVoce")) {
            boolean desenheVoce = b.getBoolean("desenheVoce");
            isDesenheVoce = desenheVoce;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuItem menuItem = menu.add(0, ATUALIZAR, 0, "Atualizar").setIcon(
                android.R.drawable.ic_popup_sync);
        MenuItemCompat.setShowAsAction(menuItem,
                MenuItem.SHOW_AS_ACTION_IF_ROOM);

        menuItem = menu.add(0, VER_PONTOS, 0, "Ver Pontos").setIcon(
                android.R.drawable.ic_menu_info_details);
        MenuItemCompat.setShowAsAction(menuItem,
                MenuItem.SHOW_AS_ACTION_IF_ROOM);

        menuItem = menu.add(0, AJUDA, 0, "Ajuda").setIcon(
                android.R.drawable.ic_menu_help);
        MenuItemCompat.setShowAsAction(menuItem,
                MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        AlertDialog.Builder salvar = Util.createAlert("Salvar", "Salvar Pontuação Obtida?", this);
        salvar.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        salvar.setNegativeButton("Não", null);
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case VER_PONTOS:
                exibirPontos();
                break;
            case AJUDA:
                break;
            case ATUALIZAR:
                atualizarTela();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void atualizarTela() {
        if (isDesenheVoce) {
            startActivity(new Intent(this, DesenheVoce.class));
            finish();
        } else {
            if (isNew == null) {
                startActivity(new Intent(this, Jogar.class));
            } else {
                Intent it = new Intent(this, DesenheVoce.class);
                startActivity(it);
            }
            finish();
        }
    }


    private void salveScore() {
        Double distancia = null;
        try {
            distancia = calcularDistanciaPontos();
            int pontuacao = calcularPontuacao(distancia);
            Usuario usuario = Util.getUser(this);

            // Util.getListPontuacao().add(new Pontuacao(usuario.getId(), pontuacao));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void exibirPontos() {
        try {
            Double distancia = calcularDistanciaPontos();
            int pontuacao = calcularPontuacao(distancia);
            String texto = "Distancia: " + distancia.intValue();
            texto += "\nPontuação: " + pontuacao;

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(texto)
                    .setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    // FIRE ZE MISSILES!
                                }
                            }).create().show();
        } catch (Exception e) {

        }
    }

    private int calcularPontuacao(Double distancia) {
        int tela = draw.getWidth() + draw.getHeight();
        Double valor = distancia / tela;
        valor = 100 * (1 - valor);
        return valor.intValue();
    }

    private double calcularDistanciaPontos() throws Exception {
        double disX = Math
                .pow(click.x.intValue() - centroMassa.x.intValue(), 2);
        double disY = Math
                .pow(click.y.intValue() - centroMassa.y.intValue(), 2);
        double distancia = disX + disY;
        return Math.sqrt(distancia);
    }

    public void setDraw(MyDraw draw) {
        this.draw = draw;
    }

    private void statrProgress() {
        final ProgressDialog ringProgressDialog = ProgressDialog.show(this, "Por favor, aguarde...", "", true);
        ringProgressDialog.setCancelable(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {

                }
                ringProgressDialog.dismiss();
            }
        }).start();
    }
}
