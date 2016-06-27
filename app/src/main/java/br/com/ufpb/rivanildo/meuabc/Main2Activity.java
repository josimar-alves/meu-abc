package br.com.ufpb.rivanildo.meuabc;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;


public class Main2Activity extends AppCompatActivity {

    public static final int IMAGEM_INTERNA = 12;

    private TextView mtexto;
    private Button mProximo;
    private Button mAnterior;

    private FloatingActionButton mGravar;
    private FloatingActionButton mSelecImagem;

    private Button mGrava;
    private Button mReproduz;
    private Button mSalvar;
    private Button mCancelar;
    private Button mPlay;

    private ImageView mHome;

    private ManagerDB managerDB;


    private MeuABCApplication application;

    private int posicaoLetra;

    private String tituloAux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ocultarBarraDeNavegacao();
        overridePendingTransition(R.anim.slide2, R.anim.slide);
        setContentView(R.layout.content_main2);

        application = (MeuABCApplication) getApplicationContext();
        managerDB = new ManagerDB(this);

        final Letra letra;

        if(getIntent().getExtras() != null){
            int i = getIntent().getExtras().getInt("idx");
            letra = application.getLetra(i);
            posicaoLetra = i;
        }else{
            letra = application.getLetra(application.getCount());
            posicaoLetra = application.getCount();
        }


        mHome = (ImageView)findViewById(R.id.home);

        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this, MainActivity.class));
                finish();
            }
        });


        // botão próximo
        mtexto = (TextView) findViewById(R.id.txt);
        mtexto.setText(letra.getLetra());

        final String titulo = letra.getTitulo();
        this.tituloAux = titulo;


        /////////////////////////// IMAGEM ////////////////////////////

        String uriImg = managerDB.getUri(titulo);

        if(uriImg != null){
            ImageView img = (ImageView) findViewById(R.id.imgPalavra);
            Uri uri = Uri.parse(uriImg);
            Log.e("jjesus", uriImg);
            Log.e("Titulo 1", this.tituloAux);
            img.setImageURI(uri);
        }

        ////////////////////////////// IMAGEM /////////////////////////


        mProximo = (Button) findViewById(R.id.proximo);
        if(application.getCount()==25){
            mProximo.setVisibility(View.GONE);
        } else{
            if(letra.getTitulo()!="Z"){
                mProximo.setText(application.getLetra(posicaoLetra + 1).getTitulo());
                mProximo.setVisibility(View.VISIBLE);
                mProximo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        application.setCount(posicaoLetra);
                        startActivity(new Intent(Main2Activity.this, Main2Activity.class));
                        finish();

                    }
                });
            }else{
                mProximo.setVisibility(View.GONE);

                mProximo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        application.setCount(posicaoLetra);
                        startActivity(new Intent(Main2Activity.this, Main2Activity.class));
                        finish();

                    }
                });
            }

        }

        // botão anterior

        mAnterior = (Button) findViewById(R.id.anterior);
        if(letra.getTitulo().equalsIgnoreCase("A")){
            mAnterior.setVisibility(View.GONE);
        }else{
            mAnterior.setText(application.getLetra(posicaoLetra-1).getTitulo());
            mAnterior.setVisibility(View.VISIBLE);
            mAnterior.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    application.setCount2(posicaoLetra);
                    startActivity(new Intent(Main2Activity.this, Main2Activity.class));
                    finish();
                }
            });
        }

        // botão floating de gravar
        mGravar = (FloatingActionButton) findViewById(R.id.fab2);
        mGravar = (FloatingActionButton)findViewById(R.id.fab2);
        mGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.activity_recorder, null);
                final Dialog dialog = new Dialog(Main2Activity.this);
                dialog.setTitle("Gravar áudio");
                dialog.setContentView(view);
                dialog.show();
                mGrava = (Button)view.findViewById(R.id.bt_gravar);
                mReproduz = (Button)view.findViewById(R.id.bt_reproduzir);
                mSalvar = (Button)view.findViewById(R.id.salvar);
                mCancelar = (Button)view.findViewById(R.id.cancelar);

                final VoiceRecorder voiceRecorder = new VoiceRecorder(mGrava,mReproduz,titulo);

                mGrava.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        voiceRecorder.onRecord();
                    }
                });
                mReproduz.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        voiceRecorder.onPlay();
                    }
                });
                mCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                mSalvar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String caminho = voiceRecorder.getmFileName();
                        letra.setUrlAudio(caminho);
                        managerDB.addPath(titulo, caminho);
                        dialog.cancel();
                    }
                });
            }
        });
        mPlay = (Button)findViewById(R.id.play);
        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lol = managerDB.getPath(titulo);
                Log.e("Titulo audio", titulo);
                if(lol == null){
                    Toast.makeText(Main2Activity.this,"audio ainda não gravado",Toast.LENGTH_SHORT).show();
                }else{
                    Log.e("lol", lol);
                    VoiceRecorder voiceRecorder = new VoiceRecorder(lol);
                    voiceRecorder.startPlaying2();

                }
            }
        });

        mSelecImagem = (FloatingActionButton)findViewById(R.id.fab1);
        mSelecImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pegarImg();
            }
        });


    }

    private void ocultarBarraDeNavegacao() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void onResume() {
        Typeface fonte = Typeface.createFromAsset(this.getAssets(), "fonts/penmanship.ttf");
        mtexto.setTypeface(fonte);
        super.onResume();
    }


    //////////////////////////// aqui Jr

    public void pegarImg(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGEM_INTERNA);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        if(requestCode == IMAGEM_INTERNA){
            if(resultCode == RESULT_OK){
                Uri imgUri = intent.getData();
                String stringUri = imgUri.toString();
                managerDB.addUri(this.tituloAux, stringUri);
                ImageView img = (ImageView) findViewById(R.id.imgPalavra);
                img.setImageURI(imgUri);
            }
        }
    }
}