package projetotcc.estudandoquimica;

import android.arch.lifecycle.LiveData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import projetotcc.estudandoquimica.db.entidade.UsuarioEntity;
import projetotcc.estudandoquimica.db.repositorio.UsuarioRepositorio;
import projetotcc.estudandoquimica.model.Turma;
import projetotcc.estudandoquimica.model.Usuario;
import projetotcc.estudandoquimica.view.turma.CadastrarTurmaDialog;
import projetotcc.estudandoquimica.view.turma.GerarCodigoTurma;
import projetotcc.estudandoquimica.view.turma.TurmaFragment;
import projetotcc.estudandoquimica.view.compartilhado.CadastrarPublicacaoActivity;
import projetotcc.estudandoquimica.view.home.HomeFragment;
import projetotcc.estudandoquimica.view.usuario.LibraryClass;
import projetotcc.estudandoquimica.view.usuario.LoginActivity;
import projetotcc.estudandoquimica.view.usuario.PerfilUsuarioActivity;
import projetotcc.estudandoquimica.view.usuario.ProfessorAlunoActivity;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private NavigationView navigationView;
    private Toolbar toolbar;
    private TurmaFragment turmaFragment;
    private FirebaseAuth auth;
    //private DatabaseReference databaseReference;
    private DrawerLayout drawer;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        auth = FirebaseAuth.getInstance();

//        UsuarioRepositorio usuarioRepositorio = new UsuarioRepositorio(getApplication());
//        LiveData<List<UsuarioEntity>> usuarios = usuarioRepositorio.getAll();
//        List<UsuarioEntity> us = usuarios.getValue();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if( firebaseAuth.getCurrentUser() == null ){
                    Intent intent = new Intent( MainActivity.this, LoginActivity.class );
                    startActivity( intent );
                    overridePendingTransition(R.anim.enter_top, R.anim.zoom_out);
                    finish();
                }
            }
        };


        auth.addAuthStateListener( authStateListener );
       // databaseReference = LibraryClass.getFirebase();
//        auth.signInWithEmailAndPassword("iurysoulyuli@gmail.com", "iury1320");

        getSupportActionBar().setTitle(getString(R.string.app_name));

        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);


        //drawer.addDrawerListener(toggle);



        drawer.addDrawerListener(new DrawerListener());

        //navigationView.setNavigationItemSelectedListener(new DrawerItemClickListener());// setOnItemClickListener(new DrawerItemClickListener());

        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //navigationView.setNavigationItemSelectedListener(new NavigationViewClickListener());
        navigationView.setItemIconTintList(null);


        if(navigationView.getMenu().getItem(0).isChecked()){
            setTextColorForMenuItem(navigationView.getMenu().getItem(0), R.color.colorItemHome);
        }
        if(savedInstanceState == null){

            getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
        }

        ImageView fotoPerfil = navigationView.getHeaderView(0).findViewById(R.id.foto_perfil_header);
        fotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent it = new Intent(getApplicationContext(), ProfessorAlunoActivity.class);
//                startActivity(it);
            }
        });

        /*Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_agenda).setVisible(false);*/

        Turma t = new Turma("Turma 1", "", auth.getUid());
        List<Usuario> list = new ArrayList<>();

        //list.add(new Usuario("Iury", "Wemerson", "12345"));
        //list.add(new Usuario("Ana", "Teste", "12345"));
        t.setUsuarios(list);


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        //ref.child("turmas/usuarios/RSyxVCAQ3Sedcq4OxsLr3j95xrn2").setValue(true);
        //final Turma[] objeto = new Turma[1];

        //ref.child("turmas").push().setValue(new Turma("Iury", new Date(), "UIU"));

        CircleImageView iv =  navigationView.getHeaderView(0).findViewById(R.id.foto_perfil_header);

        TextView nome = navigationView.getHeaderView(0).findViewById(R.id.nome_header);
        TextView email = navigationView.getHeaderView(0).findViewById(R.id.email_header);

        nome.setText(auth.getCurrentUser().getDisplayName());
        email.setText(auth.getCurrentUser().getEmail());
        Glide.with(this).load(auth.getCurrentUser().getPhotoUrl()).into(iv);

//        Query query1 = ref.child("turmas").orderByChild("nome").equalTo("Turma 1").limitToFirst(1);
//        query1.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists()){
//                    //ref.child(dataSnapshot.getRef() + "/" + dataSnapshot. + "/usuarios/RSyxVCAQ3Sedcq4OxsLr3j95xrn2").setValue(true);
//                    dataSnapshot.getChildrenCount();
//
//                    String idUsuario = dataSnapshot.getRef().push().getKey();
//
//
//
//
//                 //objeto[0] = dataSnapshot.child(t).getValue(Turma.class);
////                 for (DataSnapshot data : dataSnapshot.getChildren()){
////                     Log.d("TAG", "PARENT: "+ data.getKey());
////                     Log.d("TAG",""+ data.child("nome").getValue());
////
////                     objeto[0] = data.getValue(Turma.class);
////                 }
//
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                //Se ocorrer um erro
//            }
//        });





    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if( authStateListener != null ){
            auth.removeAuthStateListener( authStateListener );
        }
    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//
//
//        TurmaFragment nomeFragment = (TurmaFragment) getSupportFragmentManager().findFragmentByTag("TAG_TURMA");
//
//        if (nomeFragment != null && nomeFragment.isVisible()) {
//            //supportInvalidateOptionsMenu();
//            menu.findItem(R.id.action_publicacao).setVisible(false);
//
//        }else{
//            menu.findItem(R.id.action_publicacao).setVisible(true);
//
//        }
//
//
//        return true;
//    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        turmaFragment = new TurmaFragment();
        int id = item.getItemId();
        resetAllMenuItemsTextColor(navigationView);
        setTextColorForMenuItem(item, R.color.colorPrimary);

        AppBarLayout.LayoutParams params2 =
                (AppBarLayout.LayoutParams) toolbar.getLayoutParams();

        if(id == R.id.nav_turma){
            params2.setScrollFlags(0);
            toolbar.setLayoutParams(params2);
        }else{

            params2.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                    | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);

            toolbar.setLayoutParams(params2);
        }


        switch (id){
            case R.id.nav_home:

                    setTextColorForMenuItem(item, R.color.colorItemHome);
                   // getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment(), "TAG_HOME").commit();
                    mNextContentFragment = new HomeFragment();
                break;

            case R.id.nav_turma:
                  setTextColorForMenuItem(item, R.color.colorItemTurma);
                  //getSupportFragmentManager().beginTransaction().replace(R.id.container, turmaFragment, "TAG_TURMA").commit();
                  mNextContentFragment = new TurmaFragment();
                break;

            case R.id.nav_sair:

                auth.signOut();
                finish();

                break;
        }

        mChangeContentFragment = true;

        navigationView.setCheckedItem(item.getItemId());

//        mHandler.postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                drawer.closeDrawer(navigationView);
//            }
//        }, 100);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setTextColorForMenuItem(MenuItem menuItem, @ColorRes int color) {
        SpannableString spanString = new SpannableString(menuItem.getTitle().toString());
        spanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, color)), 0, spanString.length(), 0);
        menuItem.setTitle(spanString);
    }

    private void resetAllMenuItemsTextColor(NavigationView navigationView) {
        for (int i = 0; i < navigationView.getMenu().size(); i++)
            setTextColorForMenuItem(navigationView.getMenu().getItem(i), R.color.colorTextMainDefault);
    }

    private Fragment mNextContentFragment;
    private boolean mChangeContentFragment = false;

    private Handler mHandler = new Handler();

    private class DrawerListener implements android.support.v4.widget.DrawerLayout.DrawerListener {

        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {}

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {}

        @Override
        public void onDrawerStateChanged(int newState) {}

        @Override
        public void onDrawerClosed(View view) {
            if (mChangeContentFragment) {

                if(mNextContentFragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, mNextContentFragment).commit();

                    mNextContentFragment = null;

                    mChangeContentFragment = false;
                }
            }
        }


    }

}
