package br.com.ufs.centromassa;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.view.View;
import android.view.View.OnClickListener;

import java.util.Vector;

import br.com.ufs.centromassa.entity.Ponto;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MyDrawFases extends View implements OnClickListener {

    private int fase = 1;

    private Vector<Ponto> pontos;
    private Vector<Integer> raios;
    private Vector<Integer> cores;
    private Activity context;

    private Ponto centroMassa;
    private Ponto click;

    private boolean isNew;

    public MyDrawFases(Activity context, int fase) {
        super(context);
        this.fase = fase;
        this.context = context;
        pontos = new Vector<Ponto>();
        raios = new Vector<Integer>();
        cores = new Vector<Integer>();
        isNew = true;
        setOnClickListener(this);
    }

    public MyDrawFases(Activity context, int[] x, int[] y, int[] raios, int[] cores,
                       Ponto centroMassa, Ponto click) {
        super(context);
        this.context = context;
        this.pontos = convert(x, y);
        this.raios = convert(raios);
        this.centroMassa = centroMassa;
        this.click = click;
        this.cores = convert(cores);
        isNew = false;
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
        drawNew(canvas);
    }

    private void drawNew(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);

        canvas.drawPaint(paint);
        for (int i = 0; i < fase; i++) {
            int cor = getColor();
            paint.setColor(cor);
            cores.add(cor);

            Integer px = getQtdPontoX();
            Integer py = getQtdPontoY();

            Integer raio = getRaio();

            Ponto point = new Ponto(px, py);
            canvas.drawCircle(px, py, raio, paint);

            pontos.add(point);
            raios.add(raio);
        }
        setWillNotDraw(false);
    }


    private Integer getNumBolas(int fase) {
        return 1 + (int) ((fase) * Math.random());
    }

    private Integer getRaio() {
        if (fase == 1) {
            return 120;
        }
        return 20 + (int) ((getWidth() / 8) * Math.random());
    }

    private Integer getQtdPontoY() {
        if (fase == 1) {
            return getHeight() / 2;
        }
        return 5 + (int) ((getHeight()) * Math.random());
    }

    private Integer getQtdPontoX() {
        if (fase == 1) {
            return getWidth() / 2;
        }
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

    public Vector<Ponto> getPontos() {
        return pontos;
    }

    public Vector<Integer> getRaios() {
        return raios;
    }
}
