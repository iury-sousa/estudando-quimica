package projetotcc.estudandoquimica;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

public class JobServiceNotification extends JobService {

    private static final String TAG = "MyJobService";

    @Override
    public boolean onStartJob(JobParameters params) {

        Log.d(TAG, "Executando uma tarefa de execução longa em um trabalho agendado ");
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
