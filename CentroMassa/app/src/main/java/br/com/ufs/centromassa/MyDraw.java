package br.com.ufs.centromassa;

import java.util.Vector;

import br.com.ufs.centromassa.dto.Ponto;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MyDraw extends View implements OnClickListener {

	private Vector<Ponto> pontos;
	private Vector<Integer> raios;
	private Vector<Integer> cores;
	private Activity context;

	private Ponto centroMassa;
	private Ponto click;

	private boolean isNew;
	private boolean isRadom;

	public MyDraw(Activity context) {
		super(context);
		this.context = context;
		pontos = new Vector<Ponto>();
		raios = new Vector<Integer>();
		cores = new Vector<Integer>();
		isNew = true;
		isRadom = true;
		setOnClickListener(this);
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
		isRadom = false;
		setOnClickListener(this);
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
		isRadom = false;
		setOnClickListener(this);
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
		if (isNew && isRadom) {
			drawNew(canvas);
		} else {
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

			Integer px = getQtdPontos();
			Integer py = getQtdPontos();

			Integer raio = getRaio();

			Ponto point = new Ponto(px, py);
			canvas.drawCircle(px, py, raio, paint);

			pontos.add(point);
			raios.add(raio);
		}
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

	private Integer getQtdPontos() {
		return 5 + (int) ((getHeight() / 2) * Math.random());
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
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:

			break;
		case MotionEvent.ACTION_MOVE:

			break;
		case MotionEvent.ACTION_UP:
			if (isNew) {
				centroMassa = calcularCentroMassa();

				int x = (int) event.getX();
				int y = (int) event.getY();
				click = new Ponto(x, y);

				isNew = false;

				Bundle b = new Bundle();
				b.putIntArray("pontosx", getArrayX());
				b.putIntArray("pontosy", getArrayY());
				b.putIntArray("cores", convert(cores));
				b.putIntArray("raios", convert(raios));
				b.putSerializable("centroMassa", centroMassa);
				b.putSerializable("click", click);

				
				Intent it = new Intent(context, Jogar.class);
				it.putExtras(b);
				context.startActivity(it);
				context.finish();
			}
			break;
		}
		return super.onTouchEvent(event);
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {

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

}
