package projetotcc.estudandoquimica;

import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Months;
import org.joda.time.Seconds;
import org.joda.time.Weeks;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class TempoCadastro {

    public static String getTempo(Long d){

        Date date = new Date(d);
        DateTime hoje = DateTime.now();
        DateTime dataComentario = new DateTime(d);

//        DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL, new Locale("pt", "BR"));
//
//        formatador.format(dataComentario);
//        Log.i("TAG", formatador.format(d));

        int dias = Days.daysBetween(dataComentario, hoje).getDays();
        Log.i("Dias", String.valueOf(dias));

        int anos = Years.yearsBetween(dataComentario, hoje).getYears();
        Log.i("Anos", String.valueOf(anos));

        int horas = Hours.hoursBetween(dataComentario, hoje).getHours();
        Log.i("Horas", String.valueOf(horas));

        int meses = Months.monthsBetween(dataComentario, hoje).getMonths();
        Log.i("Meses", String.valueOf(meses));

        int semanas = Weeks.weeksBetween(dataComentario, hoje).getWeeks();
        Log.i("semanas", String.valueOf(semanas));

        int minutos = Minutes.minutesBetween(dataComentario, hoje).getMinutes();
        Log.i("minutos", String.valueOf(minutos));

        int segundos = Seconds.secondsBetween(dataComentario, hoje).getSeconds();
        Log.i("Segundos", String.valueOf(segundos));



        String tempo = null;


        if(segundos >= 0 && segundos <= 59){

            tempo = segundos + "s";

        }else if(minutos >= 0 && minutos <= 59){

            tempo = minutos + "min";

        }else if(horas >= 1 && horas < 24){

            tempo = horas + "h";

        }else if(dias >= 1 && dias < 7){

            tempo = dias == 1 ? dias + " dia" : dias + " dias";

        }else if(semanas >= 1 && semanas < 5){

            tempo = semanas + "sem";

        }else if(meses >= 1 && meses <= 12){

            tempo = meses == 1 ? meses + " mes" : meses + " meses";

        }else if(anos >= 1){

            tempo = anos + " ano";
        }

        return tempo;

    }
}
