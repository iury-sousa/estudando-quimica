package projetotcc.estudandoquimica.view.usuario;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import projetotcc.estudandoquimica.MainActivity;
import projetotcc.estudandoquimica.R;

import static java.lang.Math.sqrt;

public class ProfessorAlunoActivity extends AppCompatActivity implements View.OnClickListener{

    private boolean ativo = false;
//    private static final String TAG = "Touch";
//
//    Matrix matrix = new Matrix();
//    Matrix savedMatrix = new Matrix();
//    PointF start = new  PointF();
//    public static PointF mid = new PointF();
//
//    // We can be in one of these 3 states
//    public static final int NONE = 0;
//    public static final int DRAG = 1;
//    public static final int ZOOM = 2;
//    public static int mode = NONE;
//
//    float oldDist;
//
//    private float[] matrixValues = new float[9];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_aluno);

        LinearLayout linearLayout = findViewById(R.id.select_aluno);
        LinearLayout linearLayout1 = findViewById(R.id.select_prof);
        TextView textView = findViewById(R.id.btn_prosseguir);

        linearLayout.setOnClickListener(this);
        linearLayout1.setOnClickListener(this);

        textView.setOnClickListener(this);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onClick(View v) {

        int id = v.getId();
        ImageView imageProf = findViewById(R.id.img_prof);
        ImageView imageAluno = findViewById(R.id.img_aluno);



        switch (id){

            case R.id.select_prof:

                imageAluno.setActivated(false);
//
//                if(viewSwitcherProf.getDisplayedChild() == 0){
//
//                }else{
//                    viewSwitcherProf.showPrevious();
//                }

                if(imageProf.isActivated()){
                    imageProf.setActivated(false);


                }else{

                    imageProf.setActivated(true);
                    // Scaling
//                    Animation scale = new ScaleAnimation(1f, 2f, 1f, 2f, imageProf.getWidth() / 2.0f, imageProf.getHeight() / 2.0f);
//// 1 second duration
//                    scale.setDuration(1000);
//// Moving up
//                    Animation slideUp = new TranslateAnimation(0, 0.2f, 0, 0.2f);
//// 1 second duration
//                    slideUp.setDuration(1000);
//// Animation set to join both scaling and moving
//                    AnimationSet animSet = new AnimationSet(true);
//                    animSet.setFillEnabled(true);
//                    animSet.addAnimation(scale);
//                    animSet.addAnimation(slideUp);
//// Launching animation set
//                    imageProf.startAnimation(animSet);

                    ativo = true;
                }

               // imageProf.setBackground(getDrawable(R.drawable.seletor_professor_aluno));
                break;
            case R.id.select_aluno:
                imageProf.setActivated(false);

                if(imageAluno.isActivated()){

                    imageAluno.setActivated(false);
                }else{

                    imageAluno.setActivated(true);
                    ativo = false;
                }
                break;
            case R.id.btn_prosseguir:

                if(!imageProf.isActivated() && !imageAluno.isActivated()){

                    Toast.makeText(this, "Selecione uma opção!", Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseAuth auth = FirebaseAuth.getInstance();

                DatabaseReference ref2 = FirebaseDatabase
                        .getInstance().getReference("usuarios/" + auth.getCurrentUser().getUid());



                ref2.child("professor").setValue(ativo);

                Intent it = new Intent(this, MainActivity.class);
                startActivity(it);
                overridePendingTransition(R.anim.enter_top, R.anim.zoom_out);

                break;

        }

    }


    //    @SuppressLint("ClickableViewAccessibility")
//    @Override
//    public boolean onTouch(View v, MotionEvent event)
//    {
//    ImageView view = (ImageView) v;
//        view.setScaleType(ImageView.ScaleType.MATRIX);
//    float scale;
//
//    dumpEvent(event);
//    // Handle touch events here...
//
//        switch (event.getAction() & MotionEvent.ACTION_MASK)
//    {
//        case MotionEvent.ACTION_DOWN:   // first finger down only
//            savedMatrix.set(matrix);
//            start.set(event.getX(), event.getY());
//            Log.d(TAG, "mode=DRAG"); // write to LogCat
//            mode = DRAG;
//            break;
//
//        case MotionEvent.ACTION_UP: // first finger lifted
//
//        case MotionEvent.ACTION_POINTER_UP: // second finger lifted
//
//            mode = NONE;
//            Log.d(TAG, "mode=NONE");
//            break;
//
//        case MotionEvent.ACTION_POINTER_DOWN: // first and second finger down
//
//            oldDist = spacing(event);
//            Log.d(TAG, "oldDist=" + oldDist);
//            if (oldDist > 5f) {
//                savedMatrix.set(matrix);
//                midPoint(mid, event);
//                mode = ZOOM;
//                Log.d(TAG, "mode=ZOOM");
//            }
//            break;
//
//        case MotionEvent.ACTION_MOVE:
//
//            if (mode == DRAG)
//            {
//                matrix.set(savedMatrix);
//                matrix.postTranslate(event.getX() - start.x, event.getY() - start.y); // create the transformation in the matrix  of points
//            }
//            else if (mode == ZOOM)
//            {
//                // pinch zooming
//                float newDist = spacing(event);
//                Log.d(TAG, "newDist=" + newDist);
//                if (newDist > 5f)
//                {
//                    matrix.set(savedMatrix);
//                    scale = newDist / oldDist; // setting the scaling of the
//                    // matrix...if scale > 1 means
//                    // zoom in...if scale < 1 means
//                    // zoom out
//                    matrix.postScale(scale, scale, mid.x, mid.y);
//                }
//            }
//            break;
//    }
//
//        view.setImageMatrix(matrix); // display the transformation on screen
//
//        return true; // indicate event was handled
//}
//
//    /*
//     * --------------------------------------------------------------------------
//     * Method: spacing Parameters: MotionEvent Returns: float Description:
//     * checks the spacing between the two fingers on touch
//     * ----------------------------------------------------
//     */
//
//    private float spacing(MotionEvent event)
//    {
//        float x = event.getX(0) - event.getX(1);
//        float y = event.getY(0) - event.getY(1);
//        return (float) Math.sqrt(x * x + y * y);
//    }
//
//    /*
//     * --------------------------------------------------------------------------
//     * Method: midPoint Parameters: PointF object, MotionEvent Returns: void
//     * Description: calculates the midpoint between the two fingers
//     * ------------------------------------------------------------
//     */
//
//    private void midPoint(PointF point, MotionEvent event)
//    {
//        float x = event.getX(0) + event.getX(1);
//        float y = event.getY(0) + event.getY(1);
//        point.set(x / 2, y / 2);
//    }
//
//    /** Show an event in the LogCat view, for debugging */
//    private void dumpEvent(MotionEvent event)
//    {
//        String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE","POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
//        StringBuilder sb = new StringBuilder();
//        int action = event.getAction();
//        int actionCode = action & MotionEvent.ACTION_MASK;
//        sb.append("event ACTION_").append(names[actionCode]);
//
//        if (actionCode == MotionEvent.ACTION_POINTER_DOWN || actionCode == MotionEvent.ACTION_POINTER_UP)
//        {
//            sb.append("(pid ").append(action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
//            sb.append(")");
//        }
//
//        sb.append("[");
//        for (int i = 0; i < event.getPointerCount(); i++)
//        {
//            sb.append("#").append(i);
//            sb.append("(pid ").append(event.getPointerId(i));
//            sb.append(")=").append((int) event.getX(i));
//            sb.append(",").append((int) event.getY(i));
//            if (i + 1 < event.getPointerCount())
//                sb.append(";");
//        }
//
//        sb.append("]");
//        Log.d("Touch Events ---------", sb.toString());
//    }

}
