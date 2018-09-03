package projetotcc.estudandoquimica.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BindingUtils {

    public static String capitalize(String text) {
        return text.toUpperCase();
    }

    public static String verificarSexo(boolean sexo){

        if(sexo){
            return "Masculino";
        }else{
            return "Feminino";
        }
    }

    public static String converterDateToString(Date data){

        SimpleDateFormat postFormater = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        return postFormater.format(data);

    }

    public static Date converterStringToDate(String date){

        SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date dateObj = null;
        try {

            dateObj = curFormater.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("Erro: ", e.toString());
        }

        return dateObj;
    }
}
