package br.com.ufs.centromassa;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;

import br.com.ufs.centromassa.entity.Usuario;
import br.com.ufs.centromassa.util.Util;

public class MainActivity extends ActionBarActivity {

    private static final int LOGOUT = 0;
    private Button btnJogar;
	private Button btnDesenheVc;
	private Button btnRank;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuario = Util.getUser(this);

        getSupportActionBar().setSubtitle(usuario.getNome());
        
        loadComponents();
        
        loadActionComponents();
    }
    
    private void loadComponents(){
    	btnJogar = (Button) findViewById(R.id.btnJogas);
        btnDesenheVc = (Button) findViewById(R.id.btnDesenheVc);
        btnRank = (Button) findViewById(R.id.btnrank);
    }
    
    private void loadActionComponents(){
    	btnJogar.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, Jogar.class));
			}
		});
    	
    	btnDesenheVc.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, DesenheVoce.class));
			}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.add(0, LOGOUT, 0, "Logout");
        MenuItemCompat.setShowAsAction(menuItem, MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case LOGOUT:
                showLogout();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showLogout(){
        AlertDialog.Builder dialog = Util.createAlert("Logout", "Deseja realmente fazer logout?", this);

        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                GoogleApiClient build = new GoogleApiClient.Builder(MainActivity.this)
                        .addApi(Plus.API)
                        .addScope(Plus.SCOPE_PLUS_LOGIN)
                        .build();
                build.disconnect();

                Util.deleteUser(MainActivity.this);
                startActivity(new Intent(MainActivity.this, GooglePlayServicesActivity.class));
                finish();
            }
        });

        dialog.setNegativeButton("Não", null);

        dialog.create().show();
    }

    public void onFasesCliked(View view) {
        startActivity(new Intent(this, JogarFases.class));
    }

    public void onRankCliked(View view) {
        startActivity(new Intent(this, Rank.class));
    }
}
