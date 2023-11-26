/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devcaotics.controllers;

import com.devcaotics.model.dao.ManagerDao;
import com.devcaotics.model.negocio.Alerta;
import com.devcaotics.model.negocio.Usuario;
import java.io.IOException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author ALUNO
 */
@ManagedBean
@RequestScoped
public class AlertaController {
    
    private Alerta alertaCadastro;
    private Alerta selected;
     
    //isto é para a gambiarra da imagem
    private String tagImagem;
    private int tabIndex = 0;
    
    @PostConstruct
    public void init(){
        this.alertaCadastro = new Alerta();
    }

    public Alerta getAlertaCadastro() {
        return alertaCadastro;
    }

    public void setAlertaCadastro(Alerta alertaCadastro) {
        this.alertaCadastro = alertaCadastro;
    }
    
     public void handleFileUpload(FileUploadEvent event) throws IOException {
        //FacesMessage msg = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
        //FacesContext.getCurrentInstance().addMessage(null, msg);
        
        byte[] im = new byte[(int) event.getFile().getSize()];
        
        event.getFile().getInputstream().read(im);
        
        //this.alertaCadastro.getDoguinho().setImagem(im);
         ;
         //gambiarra
         ((HttpSession)FacesContext.getCurrentInstance()
                 .getExternalContext().getSession(true)).setAttribute("imagem"
                         ,im);
         
         FacesContext.getCurrentInstance()
                 .addMessage(null, new FacesMessage("Imagem Apiloidada"));
     
     
         this.tagImagem = "http://localhost:8080/DoguinhoSaver/ServletExibirImagemDoguinhoGambiarra";
     }
     
     public String inserir(){
         
         Usuario bemFeitor = ((LoginController)((HttpSession)FacesContext.getCurrentInstance().getExternalContext()
                 .getSession(true)).getAttribute("loginController")).getUsuarioLogado();
         
         byte[] imagem = (byte[]) (((HttpSession)FacesContext.getCurrentInstance().getExternalContext()
                 .getSession(true)).getAttribute("imagem"));
         
         this.alertaCadastro.getDoguinho().setImagem(imagem);
         this.alertaCadastro.setBemFeitor(bemFeitor);
         
         ManagerDao.getCurrentInstance().insert(this.alertaCadastro);
         
         FacesContext.getCurrentInstance().addMessage(null, 
                 new FacesMessage("Sucesso", "O alerta foi cadastrado"));
         
         return "indexUsuario";
     }
     
     public List<Alerta> readAlertas(Usuario uLogado){
         
         if(uLogado == null){
             return null;
         }
         
         List<Alerta> alertas = ManagerDao.getCurrentInstance()
                 .read("select a from Alerta a where a.bemFeitor.codigo = "+uLogado.getCodigo(), Alerta.class);
         
         return alertas;
     
     }
     
     public List<Alerta> filterByNaoAtendidas(){
         
         List<Alerta> alertas = ManagerDao.getCurrentInstance()
                 .read("select a from Alerta a where a.acolhedor IS NULL", Alerta.class );
         
         return alertas;
         
     }

     //gambiarra
    public String getTagImagem() {
        return tagImagem;
    }

    public void setTagImagem(String tagImagem) {
        this.tagImagem = tagImagem;
    }

    public int getTabIndex() {
        return tabIndex;
    }

    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
    }

    public Alerta getSelected() {
        return selected;
    }

    public void setSelected(Alerta selected) {
        this.selected = selected;
    }
    
    
    
    
     
     
    
}
