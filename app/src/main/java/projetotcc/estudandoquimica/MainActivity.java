package projetotcc.estudandoquimica;

import android.arch.lifecycle.LiveData;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.design.widget.AppBarLayout;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import projetotcc.estudandoquimica.db.entidade.UsuarioEntity;
import projetotcc.estudandoquimica.db.repositorio.UsuarioRepositorio;
import projetotcc.estudandoquimica.model.Publicacao;
import projetotcc.estudandoquimica.model.PublicacaoItem;
import projetotcc.estudandoquimica.model.Usuario;
import projetotcc.estudandoquimica.view.AgendaFragment;
import projetotcc.estudandoquimica.view.CadastrarTurmaDialog;
import projetotcc.estudandoquimica.view.TurmaFragment;
import projetotcc.estudandoquimica.view.compartilhado.PublicacaoFragment;
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
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        invalidateOptionsMenu();
        menu.findItem(R.id.action_add_conteudo).setVisible(false);

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

            item.setVisible(false);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

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
            case R.id.nav_agenda:
                setTextColorForMenuItem(item, R.color.colorItemAgenda);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new AgendaFragment()).commit();
                break;
            case R.id.nav_turma:
                setTextColorForMenuItem(item, R.color.colorItemTurma);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new TurmaFragment()).commit();
                break;

            case R.id.nav_atividade:
                setTextColorForMenuItem(item, R.color.colorItemActivities);
                break;

            case R.id.nav_desempenho:
                setTextColorForMenuItem(item, R.color.colorItemPerformance);
                break;
            case R.id.nav_sair:

                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();

                startActivity(new Intent(this, LoginActivity.class));
                finish();

                break;
            case R.id.nav_share:



                break;
            case R.id.nav_send:
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
        TurmaFragment turma = new TurmaFragment();
        if(!TextUtils.isEmpty(nome)) {

            turma.setNome(nome);
            dialog.dismiss();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.container, turma).commit();
    }

    @Override
    public void onDialogNegativeClick(CadastrarTurmaDialog dialog) {
        dialog.dismiss();
    }
}
