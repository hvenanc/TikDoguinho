/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devcaotics.model.negocio;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

/**
 *
 * @author ALUNO
 */

@Embeddable
public class Doguinho {
    
    @Column(name="cor_doguinho", length = 30)
    private String cor;
    private String raca;
    @Lob
    private String situacao;
    @Lob
    private byte[] imagem;
    private String porte;
    @Column(length = 2)
    private String sexo;
    //elementos que identifiquem o animal {coleira, corrente, smartphone, corda, lacinho}
    private String identificacao;
    private String deficienciaAparente;

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public String getPorte() {
        return porte;
    }

    public void setPorte(String porte) {
        this.porte = porte;
    }

    public String getDeficienciaAparente() {
        return deficienciaAparente;
    }

    public void setDeficienciaAparente(String deficienciaAparente) {
        this.deficienciaAparente = deficienciaAparente;
    }

    public String getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
    }
    
    
    
    
}
