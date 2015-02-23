package br.com.ufs.centromassa;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import br.com.ufs.centromassa.dto.Ponto;

public class DesenheVoce extends ActionBarActivity {

	private int[] x;
	private int[] y;
	private int[] raios;
	private int[] cores;
	private Ponto centroMassa;
	private Ponto click;
	private MyDrawDesenheVoce draw;

	private final int VER_PONTOS = 0;
	private final int AJUDA = 1;
	private final int PARAR_DESENHO = 2;
	private final int ATUALIZAR = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle b = getIntent().getExtras();
		if (b != null) {
			raios = b.getIntArray("raios");
			cores = b.getIntArray("cores");
			centroMassa = (Ponto) b.getSerializable("centroMassa");
			click = (Ponto) b.getSerializable("click");

			x = b.getIntArray("pontosx");
			y = b.getIntArray("pontosy");
			draw = new MyDrawDesenheVoce(this, x, y, raios, cores, centroMassa,
					click);
			setContentView(draw);
		} else {
			draw = new MyDrawDesenheVoce(this);
			setContentView(draw);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem menuItem = menu.add(0, PARAR_DESENHO, 0, "Parar desenho.")
				.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
		MenuItemCompat.setShowAsAction(menuItem,
				MenuItem.SHOW_AS_ACTION_IF_ROOM);

		menuItem = menu.add(0, ATUALIZAR, 0, "Atualizar").setIcon(
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
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case VER_PONTOS:
			exibirPontos();
			break;
		case AJUDA:
			break;
		case PARAR_DESENHO:
			gerarCentroMassa();
			break;
		case ATUALIZAR:
			startActivity(new Intent(this, DesenheVoce.class));
			finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void gerarCentroMassa() {
		Bundle b = new Bundle();
		b.putIntArray("pontosx", x);
		b.putIntArray("pontosy", y);
		b.putIntArray("cores", cores);
		b.putIntArray("raios", raios);
		b.putSerializable("centroMassa", centroMassa);
		b.putSerializable("click", click);
		b.putBoolean("isNew", true);

		Intent it = new Intent(this, Jogar.class);
		it.putExtras(b);
		startActivity(it);
		finish();

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
}
