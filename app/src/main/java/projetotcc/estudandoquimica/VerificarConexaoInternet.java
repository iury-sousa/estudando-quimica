package projetotcc.estudandoquimica;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.design.widget.Snackbar;
import android.view.View;

import static android.support.design.widget.Snackbar.make;

public class VerificarConexaoInternet {

    public static boolean verificaConexao(Context context) {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert conectivtyManager != null;
        conectado = conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected();
        return conectado;
    }


    public static void getMensagem(View view){
        Snackbar snackbar = Snackbar.make(view, "Sem conexão com a internet!", Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}
