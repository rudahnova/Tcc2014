package br.com.ufs.centromassa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.ufs.centromassa.adapter.GridAdapter;
import br.com.ufs.centromassa.control.PontuacaoControl;
import br.com.ufs.centromassa.entity.Pontuacao;
import br.com.ufs.centromassa.util.GridSpacingItemDecoration;

public class Rank extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        // Calling the RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setPadding(8, 8, 20, 8);
        mRecyclerView.setLayoutFrozen(true);

        // The number of Columns
        mLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(mLayoutManager);

        int spanCount = 3;
        int spacing = 10;
        boolean includeEdge = false;
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        mAdapter = createGridList();
        mRecyclerView.setAdapter(mAdapter);
    }

    private GridAdapter createGridList() {
        ArrayList<String> list = new ArrayList<>();
        //NOME COLUNAS
        list.add("Nome");
        list.add("Fase");
        list.add("Pontuação");
        try {
            PontuacaoControl pontuacaoControl = new PontuacaoControl(this);
            List<Pontuacao> rank = pontuacaoControl.getRank();

            for(Pontuacao pontuacao : rank){
                list.add(pontuacao.getUsuario().getNome());
                list.add(pontuacao.getFase()+"");
                list.add(pontuacao.getPontos()+"");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        GridAdapter gridAdapter = new GridAdapter(list);
        return gridAdapter;
    }
}
