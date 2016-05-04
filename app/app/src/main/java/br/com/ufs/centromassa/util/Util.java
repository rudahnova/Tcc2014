package br.com.ufs.centromassa.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

import br.com.ufs.centromassa.entity.Pontuacao;
import br.com.ufs.centromassa.entity.Usuario;

/**
 * Created by  on 14/01/2015.
 */
public class Util {

    private static ArrayList<Pontuacao> listPontuacao;

    public static SharedPreferences.Editor getEditor(Context context){
        SharedPreferences preferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        return preferences.edit();
    }

    public static void saveUser(Context context,Usuario usuario){
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString("email", usuario.getEmail());
        editor.putString("nome", usuario.getNome());
        editor.putLong("id", usuario.getId());
        editor.commit();
    }

    public static void deleteUser(Context context){
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString("email", null);
        editor.putString("nome", null);
        editor.putLong("id", -1);
        editor.commit();
    }

    public static Usuario getUser(Context context){
        SharedPreferences preferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        String nome = preferences.getString("nome", null);
        String email = preferences.getString("email", null);
        Long id = preferences.getLong("id", -1);
        return new Usuario(id,email,nome);
    }

    public static AlertDialog.Builder createAlert(String title, String mesasage, Context context){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(title);
        alertDialog.setMessage(mesasage);
        alertDialog.setPositiveButton("Ok", null);
        return alertDialog;
    }

    public static ArrayList<Pontuacao> getListPontuacao() {
        if(listPontuacao == null){
            listPontuacao = new ArrayList<>();
        }
        return listPontuacao;
    }

    public static void deleteListPontuacao(){
        listPontuacao = null;
    }
}
