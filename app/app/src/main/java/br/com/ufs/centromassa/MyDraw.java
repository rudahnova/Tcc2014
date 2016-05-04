package br.com.ufs.centromassa;

import java.util.Vector;

import br.com.ufs.centromassa.entity.Ponto;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MyDraw extends View {

	private static final String TAG = "DEBUG_MYDRAW";
	private Vector<Ponto> pontos;
	private Vector<Integer> raios;
	private Vector<Integer> cores;
	private Activity context;

	private Ponto centroMassa;
	private Ponto click;

	private boolean isNew;
	private boolean isRandom;
	private boolean isBackToDesenheVoce;

	public MyDraw(Activity context) {
		super(context);
		this.context = context;
		pontos = new Vector<Ponto>();
		raios = new Vector<Integer>();
		cores = new Vector<Integer>();
		isNew = true;
		isRandom = true;
	}

	public MyDraw(Activity context, int[] x, int[] y, int[] raios, int[] cores,
			Ponto centroMassa, Ponto click) {
		super(context);
		this.context = context;
		this.pontos = convert(x, y);
		this.raios = convert(raios);
		this.centroMassa = centroMassa;
		this.click = click;
		this.cores = convert(cores);
		isNew = false;
		isRandom = false;
	}
	
	public MyDraw(Activity context, int[] x, int[] y, int[] raios, int[] cores,
			Ponto centroMassa, Ponto click, boolean isNew) {
		super(context);
		this.context = context;
		this.pontos = convert(x, y);
		this.raios = convert(raios);
		this.centroMassa = centroMassa;
		this.click = click;
		this.cores = convert(cores);
		this.isNew = isNew;
		isRandom = false;
	}
	

	public Vector<Integer> convert(int[] array) {
		Vector<Integer> integers = new Vector<Integer>();
		for (int i = 0; i < array.length; i++) {
			integers.add(array[i]);
		}
		return integers;
	}

	private Vector<Ponto> convert(int[] x, int[] y) {
		Vector<Ponto> pontos = new Vector<Ponto>();
		Ponto p;
		for (int i = 0; i < x.length; i++) {
			p = new Ponto(x[i], y[i]);
			pontos.add(p);
		}
		return pontos;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (isNew && isRandom) {
			Log.d(TAG, "isNew and isRandom");
			drawNew(canvas);
		} else {
			Log.d(TAG, "isNotNew and isNotRadom");
			drawNotNew(canvas);
		}
	}

	private void drawNew(Canvas canvas) {
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.WHITE);

		canvas.drawPaint(paint);
		for (int i = 0; i < getNumBolas(); i++) {
			int cor = getColor();
			paint.setColor(cor);
			cores.add(cor);

			Integer px = getQtdPontosX();
			Integer py = getQtdPontosY();

			Integer raio = getRaio();

			Ponto point = new Ponto(px, py);
			canvas.drawCircle(px, py, raio, paint);

			pontos.add(point);
			raios.add(raio);
		}
		setWillNotDraw(false);
	}

	private void drawNotNew(Canvas canvas) {
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.WHITE);

		canvas.drawPaint(paint);
		for (int i = 0; i < pontos.size(); i++) {
			paint.setColor(cores.get(i));

			Ponto p = pontos.get(i);
			Integer cor = cores.get(i);
			Integer raio = raios.get(i);
			canvas.drawCircle(p.x, p.y, raio, paint);
		}
		if(!centroMassa.isEmpty()){
			paint.setColor(Color.GRAY);
			canvas.drawCircle(centroMassa.x, centroMassa.y, 13, paint);
			// canvas.drawText("Centro de Massa", centroMassa.x,
			// centroMassa.y+30,paint);
			canvas.drawCircle(click.x, click.y, 13, paint);
			canvas.drawLine(centroMassa.x, centroMassa.y, click.x, click.y, paint);
		}
	}

	private Integer getNumBolas() {
		return 1 + (int) (5 * Math.random());
	}

	private Integer getRaio() {
		return 20 + (int) ((getWidth() / 8) * Math.random());
	}

	private Integer getQtdPontosY() {
		return 5 + (int) ((getHeight()) * Math.random());
	}

	private Integer getQtdPontosX() {
		return 5 + (int) ((getWidth()) * Math.random());
	}

	private Integer getMassa() {
		return 3 + (int) ((getWidth() / 2) * Math.random());
	}

	private Integer getColor() {
		Integer[] cores = new Integer[5];
		cores[0] = Color.YELLOW;
		cores[1] = Color.BLUE;
		cores[2] = Color.GREEN;
		cores[3] = Color.RED;
		cores[4] = Color.MAGENTA;
		return cores[0 + (int) (4 * Math.random())];
	}

	private Integer getNumColor() {
		Integer numPossibilidades = 255 - 0;
		Integer aleat = ((int) Math.random()) * numPossibilidades;
		aleat = ((int) Math.floor(aleat));
		return aleat;

	}

	private Ponto calcularCentroMassa() {
		Integer cmx = 0;
		Integer cmy = 0;
		Integer cmr = 0;
		for (int i = 0; i < pontos.size(); i++) {
			cmx += pontos.get(i).x * raios.get(i);
			cmy += pontos.get(i).y * raios.get(i);
			cmr += raios.get(i);
		}
		Ponto pCM = new Ponto((cmx / cmr), (cmy / cmr));
		return pCM;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		return super.dispatchTouchEvent(event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			if (isNew) {
				centroMassa = calcularCentroMassa();

				int x = (int) event.getX();
				int y = (int) event.getY();
				click = new Ponto(x, y);

				isNew = false;

				Bundle b = new Bundle();
				int[] arrayX = getArrayX();
				b.putIntArray("pontosx", arrayX);
				int[] arrayY = getArrayY();
				b.putIntArray("pontosy", arrayY);
				b.putIntArray("cores", convert(cores));
				b.putIntArray("raios", convert(raios));
				b.putSerializable("centroMassa", centroMassa);
				b.putSerializable("click", click);
				b.putBoolean("desenheVoce", isBackToDesenheVoce);

				pontos = new Vector<>();


				Intent it = new Intent(context, Jogar.class);
				it.putExtras(b);
				context.startActivity(it);
				context.finish();
				try {
					invalidate();
					this.finalize();
				} catch (Throwable throwable) {
					throwable.printStackTrace();
				}
			}
			break;
		case MotionEvent.ACTION_MOVE:

			break;
		case MotionEvent.ACTION_UP:

			break;
		}
		return true;
	}

	private int[] convert(Vector<Integer> array) {
		int[] is = new int[array.size()];
		int count = 0;
		for (Integer i : array) {
			is[count++] = i;
		}
		return is;

	}

	private int[] getArrayY() {
		int[] y = new int[pontos.size()];
		int count = 0;
		for (Ponto p : pontos) {
			y[count++] = p.y;
		}
		return y;
	}

	private int[] getArrayX() {
		int[] x = new int[pontos.size()];
		int count = 0;
		for (Ponto p : pontos) {
			x[count++] = p.x;
		}
		return x;
	}

	public boolean isBackToDesenheVoce() {
		return isBackToDesenheVoce;
	}

	public void setIsBackToDesenheVoce(boolean isBackToDesenheVoce) {
		this.isBackToDesenheVoce = isBackToDesenheVoce;
	}
}
