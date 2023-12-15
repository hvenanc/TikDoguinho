/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.henrique.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.henrique.model.dao.ManagerDao;
import org.henrique.model.negocios.Arquivo;
import org.henrique.model.negocios.Pet;
import org.henrique.model.negocios.Publicacao;
import org.henrique.model.negocios.Video;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author Henri
 */
@ManagedBean
@SessionScoped
public class PublicacaoController {
    
    private Publicacao publicacaoSelection;
    private Publicacao cadastroPublicacao;
    
    @PostConstruct
    public void init() {
        this.publicacaoSelection = new Publicacao();
        this.cadastroPublicacao = new Publicacao();
    }

    public Publicacao getPublicacaoSelection() {
        return publicacaoSelection;
    }

    public void setPublicacaoSelection(Publicacao publicacaoSelection) {
        this.publicacaoSelection = publicacaoSelection;
    }

    public Publicacao getCadastroPublicacao() {
        return cadastroPublicacao;
    }

    public void setCadastroPublicacao(Publicacao cadastroPublicacao) {
        this.cadastroPublicacao = cadastroPublicacao;
    }
    
    
    
    public void videoTutorUpload(FileUploadEvent event) throws IOException {
        
        Video videoTutor = new Video();
        byte[] video1 = new byte[(int) event.getFile().getSize()];
        String nome1 = event.getFile().getFileName();
        String extensao1 = event.getFile().getContentType();
        int tamanho1 = (int) event.getFile().getSize();
        
        videoTutor.setArqVideo(video1);
        videoTutor.setExtensao(extensao1);
        videoTutor.setTamanhoArquivo(tamanho1);
        videoTutor.setNomeArquivo(nome1);
        
        event.getFile().getInputstream().read(video1);
        
        ((HttpSession)FacesContext.getCurrentInstance()
                 .getExternalContext().getSession(true)).setAttribute("videoTutor"
                         ,videoTutor);
         
         FacesContext.getCurrentInstance()
                 .addMessage(null, new FacesMessage("Videos Carregados com Sucesso!"));
    }
    
    public void videoPetUpload(FileUploadEvent event) throws IOException {
        
        Video videoPet = new Video();
        byte[] video = new byte[(int) event.getFile().getSize()];
        String nome = event.getFile().getFileName();
        String extensao = event.getFile().getContentType();
        int tamanho = (int) event.getFile().getSize();
        
        videoPet.setArqVideo(video);
        videoPet.setExtensao(extensao);
        videoPet.setTamanhoArquivo(tamanho);
        videoPet.setNomeArquivo(nome);
        
        event.getFile().getInputstream().read(video);
        
        ((HttpSession)FacesContext.getCurrentInstance()
                 .getExternalContext().getSession(true)).setAttribute("videoPet"
                         ,videoPet);
         
         FacesContext.getCurrentInstance()
                 .addMessage(null, new FacesMessage("Videos Carregados com Sucesso!"));
    }
    
    public String inserirPublicacao(String descricao, String codigoPet) {
        
        Pet pet = (Pet) ManagerDao.getCurrentInstance().read( "select p from Pet p" + " where p.hashPet = '" 
                + codigoPet + "'", Pet.class).get(0);
 
        Publicacao publicacao = new Publicacao();
        publicacao.setDataAtual(new Date());
        publicacao.setPet(pet);
        publicacao.setDescricao(descricao);
        publicacao.setHashPub(publicacao.hashPublicacao());
        
        Video videoPet = (Video) (((HttpSession)FacesContext.getCurrentInstance().getExternalContext()
                 .getSession(true)).getAttribute("videoPet"));
        
        Video videoTutor = (Video) (((HttpSession)FacesContext.getCurrentInstance().getExternalContext()
                 .getSession(true)).getAttribute("videoTutor"));
        
        videoPet.setPublicacao(publicacao);
        videoTutor.setPublicacao(publicacao);
        
        List<Video> videos = new ArrayList<>();
        videos.add(videoPet);
        videos.add(videoTutor);
        
        publicacao.setVideos(videos);
        
        List<Publicacao> posts = pet.getPublicacao();
        posts.add(publicacao);
        
        pet.setPublicacao(posts);
        ManagerDao.getCurrentInstance().update(pet);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Post Inserido com Sucesso!"));
        
        return "perfilPet";
    }
    
    public List<Publicacao> buscarTodasAsPublicacoes() {
      
        return ManagerDao.getCurrentInstance().read("select p from Publicacao p", Publicacao.class);
        
    }
    
    public List<Publicacao> buscarPublicacoesPorPet(String codigoPet) {
      
        Pet pet = (Pet) ManagerDao.getCurrentInstance().read( "select p from Pet p" + " where p.hashPet = '" 
                + codigoPet + "'", Pet.class).get(0);
        
        System.out.println(pet.getPublicacao());
        return pet.getPublicacao();
        
    }
    
}