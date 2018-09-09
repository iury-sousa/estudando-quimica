package projetotcc.estudandoquimica;

import android.app.Fragment;
import android.arch.lifecycle.LiveData;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import projetotcc.estudandoquimica.db.entidade.UsuarioEntity;
import projetotcc.estudandoquimica.db.repositorio.UsuarioRepositorio;
import projetotcc.estudandoquimica.model.Turma;
import projetotcc.estudandoquimica.model.Usuario;
import projetotcc.estudandoquimica.view.AgendaFragment;
import projetotcc.estudandoquimica.view.CadastrarTurmaDialog;
import projetotcc.estudandoquimica.view.TurmaFragment;
import projetotcc.estudandoquimica.view.compartilhado.CadastrarPublicacaoActivity;
import projetotcc.estudandoquimica.view.home.HomeFragment;
import projetotcc.estudandoquimica.view.usuario.LoginActivity;
import projetotcc.estudandoquimica.view.usuario.PerfilUsuarioActivity;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CadastrarTurmaDialog.CadastrarTurmaListener {


    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        UsuarioRepositorio usuarioRepositorio = new UsuarioRepositorio(getApplication());
        LiveData<List<UsuarioEntity>> usuarios = usuarioRepositorio.getAll();
        List<UsuarioEntity> us = usuarios.getValue();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword("iurysoulyuli@gmail.com", "iury1320");

        Log.i("code", GerarCodigoTurma.gerar(8));

        getSupportActionBar().setTitle(getString(R.string.app_name));
        //TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        /*TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
*//*        tabLayout.addTab(tabLayout.newTab().setText("1"));
        tabLayout.addTab(tabLayout.newTab().setText("2"));
        tabLayout.addTab(tabLayout.newTab().setText("3"));*//*
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        TabsAdapter tabsAdapter = new TabsAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        viewPager.setAdapter(tabsAdapter);
        tabLayout.setupWithViewPager(viewPager);*/


       // MenuItem menuItem = toolbar.getMenu().findItem(R.id.action_add_conteudo);
        //onOptionsItemSelected(null);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
                Intent it = new Intent(getApplicationContext(), PerfilUsuarioActivity.class);
                startActivity(it);
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

        Query query1 = ref.child("turmas").orderByChild("nome").equalTo("Turma 1").limitToFirst(1);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    //ref.child(dataSnapshot.getRef() + "/" + dataSnapshot. + "/usuarios/RSyxVCAQ3Sedcq4OxsLr3j95xrn2").setValue(true);
                    dataSnapshot.getChildrenCount();

                    String idUsuario = dataSnapshot.getRef().push().getKey();




                 //objeto[0] = dataSnapshot.child(t).getValue(Turma.class);
//                 for (DataSnapshot data : dataSnapshot.getChildren()){
//                     Log.d("TAG", "PARENT: "+ data.getKey());
//                     Log.d("TAG",""+ data.child("nome").getValue());
//
//                     objeto[0] = data.getValue(Turma.class);
//                 }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Se ocorrer um erro
            }
        });

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {


        TurmaFragment nomeFragment = (TurmaFragment) getSupportFragmentManager().findFragmentByTag("TAG_TURMA");

        if (nomeFragment != null && nomeFragment.isVisible()) {
            //supportInvalidateOptionsMenu();
            menu.findItem(R.id.action_add_conteudo).setVisible(false);

        }else{
            menu.findItem(R.id.action_add_conteudo).setVisible(true);

        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();


        if (id == R.id.action_add_conteudo) {


//            item.setVisible(false);
//            return true;
            Intent it = new Intent(this, CadastrarPublicacaoActivity.class);
            startActivity(it);
        }

        return super.onOptionsItemSelected(item);
    }

    TurmaFragment turmaFragment;

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
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
                break;
//            case R.id.nav_agenda:
//                setTextColorForMenuItem(item, R.color.colorItemAgenda);
//                getSupportFragmentManager().beginTransaction().replace(R.id.container, new AgendaFragment()).commit();
//                break;
            case R.id.nav_turma:
                setTextColorForMenuItem(item, R.color.colorItemTurma);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, turmaFragment, "TAG_TURMA").commit();
                break;

//            case R.id.nav_atividade:
//                setTextColorForMenuItem(item, R.color.colorItemActivities);
//                break;
//
//            case R.id.nav_desempenho:
//                setTextColorForMenuItem(item, R.color.colorItemPerformance);
//                break;
            case R.id.nav_sair:

                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();

                startActivity(new Intent(this, LoginActivity.class));
                finish();

                break;

        }

        /*if (id == R.id.nav_home) {

            setTextColorForMenuItem(item, R.color.colorItemHome);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();

        } else if (id == R.id.nav_agenda) {

            setTextColorForMenuItem(item, R.color.colorItemAgenda);
            Intent it = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(it);

        } else if (id == R.id.nav_atividade) {

            setTextColorForMenuItem(item, R.color.colorItemActivities);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new ConteudoOfflineFragment()).commit();
        } else if (id == R.id.nav_desempenho) {

            setTextColorForMenuItem(item, R.color.colorItemPerformance);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

    @Override
    public void onDialogPositiveClick(CadastrarTurmaDialog dialog) {

        String nome = dialog.getNome();
        ///TurmaFragment turma = new TurmaFragment();
        if(!TextUtils.isEmpty(nome)) {

            turmaFragment.setNome(nome);
            dialog.dismiss();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.container, turmaFragment).commit();
    }

    @Override
    public void onDialogNegativeClick(CadastrarTurmaDialog dialog) {
        dialog.dismiss();
    }
}
