package br.com.ufpb.rivanildo.meuabc;

import java.io.Serializable;

/**
 * Created by Rivanildo on 20/04/16.
 */
public class Letra implements Serializable {

    private String letra;
    private String urlAudio;
    private String titulo;

    public Letra(String letra, String titulo) {
        this.letra = letra;
        this.titulo = titulo;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public String getUrlAudio() {
        return urlAudio;
    }

    public void setUrlAudio(String urlAudio) {
        this.urlAudio = urlAudio;
    }

    public String getTitulo() {
        return titulo;
    }
}
