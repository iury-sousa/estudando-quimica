package projetotcc.estudandoquimica;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ViewSwitcher;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;
import java.util.zip.Inflater;

import projetotcc.estudandoquimica.view.compartilhado.CadastrarPublicacaoActivity;
import projetotcc.estudandoquimica.view.home.ConteudoCompartilhadoFragment;
import projetotcc.estudandoquimica.view.home.QuizFragment;
import projetotcc.estudandoquimica.view.offline.ConteudoOfflineFragment;
import projetotcc.estudandoquimica.view.turma.TurmaFragment;
import projetotcc.estudandoquimica.view.usuario.LoginActivity;

public class HomeActivity extends AppCompatActivity implements ViewSwitcher.OnClickListener{

    protected AppBarLayout appBar;
    private TabLayout tabLayout;
    private boolean isProfessor = false;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onStart() {
        super.onStart();
        VerificarUsuario.verificarTipoUsuario(retorno -> {
            isProfessor = retorno;
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        if(b != null){
            isProfessor = getIntent().getExtras().getBoolean("isProfessor");
        }
        setContentView(R.layout.activity_home);



        TabsAdapter tabsAdapter = new TabsAdapter(getSupportFragmentManager());
        auth = FirebaseAuth.getInstance();
        appBar = findViewById(R.id.appbar);
        Toolbar toolbar = findViewById(R.id.toolbar);

//
        setSupportActionBar(toolbar);

//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setLogo(R.drawable.ic_flask);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);

        ViewPager viewPager = findViewById(R.id.pagina);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(tabsAdapter);

        tabLayout = findViewById(R.id.tabs);
        //tabLayout.setTabTextColors(Color.parseColor("#FF5D5D5D"), Color.parseColor("#410C5A"));
       // tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //tabLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
        //tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#410C5A"));
        //tabLayout.setSelectedTabIndicatorHeight(8);
        tabLayout.setupWithViewPager(viewPager);
//
//       // appBar.addView(tabLayout);
//
//        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) tabLayout.getLayoutParams();
//        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);
//        tabLayout.setLayoutParams(params);

        getSupportActionBar().setTitle(getString(R.string.app_name));

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if( firebaseAuth.getCurrentUser() == null ){
                    Intent intent = new Intent( HomeActivity.this, LoginActivity.class );
                    startActivity( intent );
                    overridePendingTransition(R.anim.enter_top, R.anim.zoom_out);
                    finish();
                }
            }
        };

        auth.addAuthStateListener( authStateListener );
//
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_friends);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_open_book);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_game);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                appBar.setExpanded(true,true);

                if(tab.getPosition() == 0){
                    getSupportActionBar().setTitle(getString(R.string.app_name));
                    getSupportActionBar().show();

                }else if(tab.getPosition() == 1){
                    getSupportActionBar().setTitle("Turmas");
                    getSupportActionBar().show();

                }else if(tab.getPosition() == 2){
                    getSupportActionBar().setTitle("Conteúdo offline");
                    getSupportActionBar().show();

                }else{
                    getSupportActionBar().setTitle("Quiz química");
                    getSupportActionBar().show();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        if (isProfessor) {

           inflater.inflate(R.menu.menu_conteudo, menu);
        }else{

            inflater.inflate(R.menu.menu_conteudo, menu);
            menu.removeItem(R.id.action_publicacao);
        }

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_publicacao:

                Intent it = new Intent(this, CadastrarPublicacaoActivity.class);
                startActivity(it);
                overridePendingTransition(R.anim.enter_top, R.anim.zoom_out);
                return true;

            case R.id.action_sair:

                auth.signOut();
                finish();
                return true;

            case R.id.action_questionario:

                browseTo("https://goo.gl/forms/LZcaOFbWnrdDvu192");
                return true;

            case R.id.action_compartilhe:

                Intent compartilha = new Intent(Intent.ACTION_SEND);
                compartilha.setType("text/plain");
                compartilha.putExtra(Intent.EXTRA_SUBJECT, "Compartilhe Estudando Química e ajude pessoas a conhecerem nossa ferramenta.");
                compartilha.putExtra(Intent.EXTRA_TEXT, "Estudando Química App \n\nLink para baixar o aplicativo: https://1drv.ms/f/s!AqnODiiU_rJ4iNB6PorhJwcOONxxzA");
                startActivity(Intent.createChooser(compartilha, "Compartilhar link!"));

                return true;

            case R.id.action_sobre:

                Intent intent = new Intent(this, SobreActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter_top, R.anim.zoom_out);

                return true;

            default:
                return false;
        }
    }

    public void browseTo(String url){

        if (!url.startsWith("http://") && !url.startsWith("https://")){
            url = "http://" + url;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @Override
    public void onClick(View v) {

        ViewSwitcher p = (ViewSwitcher) v;

        p.showNext();


     }

    class TabsAdapter extends FragmentPagerAdapter {

        private String[] titles = new String[]{"Conteúdos", "Estudos", "Quiz", "Turmas"};
        private int tabCount = 3;

        public TabsAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null; //titles[position];
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:

                    return new ConteudoCompartilhadoFragment();

                case 1:
                    return new TurmaFragment();


                case 2:
                    return new ConteudoOfflineFragment();

                case 3:
                    return new QuizFragment();

                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
