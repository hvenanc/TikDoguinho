/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.henrique.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.henrique.model.dao.ManagerDao;
import org.henrique.model.negocios.Arquivo;
import org.henrique.model.negocios.Pet;
import org.henrique.model.negocios.Seguidor;
import org.henrique.model.negocios.Seguindo;
import org.henrique.model.negocios.Tutor;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author Henri
 */
@ManagedBean
@SessionScoped
public class PetController {
    
    private Pet selection;
    private String buscaPet;
    private Seguidor seguidor;
    private Seguindo seguindo;
    
    @PostConstruct
    public void init() {
        this.selection = new Pet();
        this.buscaPet = null;
        this.seguidor = new Seguidor();
        this.seguindo = new Seguindo();
  
    }
    
    public Pet getSelection() {
        return selection;
    }

    public void setSelection(Pet selection) {
        this.selection = selection;
    }

    public String getBuscaPet() {
        return buscaPet;
    }

    public void setBuscaPet(String buscaPet) {
        this.buscaPet = buscaPet;
    }
    
    public List<Pet> lerPets() {
        
        Tutor tutorLogado = ((LoginController) ((HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(true))
                .getAttribute("loginController")).getTutorLogado();
        
        return tutorLogado.getPets();
        
    }
    
    
    public void deletarPet(String codigoPet) {
        
        try {
        
        Tutor tutorLogado = ((LoginController) ((HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(true))
                .getAttribute("loginController")).getTutorLogado();
        
        Pet petRemove = (Pet) ManagerDao.getCurrentInstance().read( "select p from Pet p" + " where p.hashPet = '" 
                + codigoPet + "'", Pet.class).get(0);
        List<Tutor> tutores = petRemove.getTutores();
        List<Pet> petsTutorLogado = tutorLogado.getPets();
        petsTutorLogado.remove(petRemove);
        tutorLogado.setPets(petsTutorLogado);
        ManagerDao.getCurrentInstance().update(tutorLogado);
        
        tutores.remove(tutorLogado);
        petRemove.setTutores(tutores);
        ManagerDao.getCurrentInstance().update(petRemove);
        if(tutores.isEmpty()) {
            ManagerDao.getCurrentInstance().delete(petRemove);
        }
        
        FacesContext.getCurrentInstance().addMessage(null, new 
        FacesMessage(FacesMessage.SEVERITY_INFO, "Você deletou seu estimado Pet do Sistema!", ""));
        }
        
        catch(ArrayIndexOutOfBoundsException e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Pet não encontrado em nosso Sistema!", ""));
        }
        
    }
    
    public void handleFileUpload(FileUploadEvent event) throws IOException {
        
        Arquivo dadosArquivo = new Arquivo();
        byte[] imagem = new byte[(int) event.getFile().getSize()];
        String nome = event.getFile().getFileName();
        String extensao = event.getFile().getContentType();
        int tamanho = (int) event.getFile().getSize();
        
        dadosArquivo.setArquivo(imagem);
        dadosArquivo.setExtensao(extensao);
        dadosArquivo.setTamanhoArquivo(tamanho);
        dadosArquivo.setNomeArquivo(nome);
        
        event.getFile().getInputstream().read(imagem);
        
        ((HttpSession)FacesContext.getCurrentInstance()
                 .getExternalContext().getSession(true)).setAttribute("arquivo"
                         ,dadosArquivo);
         
         FacesContext.getCurrentInstance()
                 .addMessage(null, new FacesMessage("Imagem Carregada"));
       
 
    }
    
    public String atualizarPet(String codigoPet) {
        
        try {
        
        Pet petAtualiza = (Pet) ManagerDao.getCurrentInstance().read( "select p from Pet p" + " where p.hashPet = '" 
                + codigoPet + "'", Pet.class).get(0);
        
        Arquivo arq = (Arquivo) (((HttpSession)FacesContext.getCurrentInstance().getExternalContext()
                 .getSession(true)).getAttribute("arquivo"));
        
        petAtualiza.setNome(this.selection.getNome());
        petAtualiza.setMesAnoNascimento(this.selection.getMesAnoNascimento());
        petAtualiza.setPorte(this.selection.getPorte());
        petAtualiza.setArquivo(arq);
        ManagerDao.getCurrentInstance().update(petAtualiza);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Pet Atualizado com Sucesso!", ""));
        }
        
        catch(ArrayIndexOutOfBoundsException e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Por Segunça informe corretamente o TikDogCode do Pet!", ""));
        }
        
        return "gerenciarPets";
    }
    
    public List<String> tutoresPets(String codigoPet) {
        
        Pet pet = (Pet) ManagerDao.getCurrentInstance().read( "select p from Pet p" + " where p.hashPet = '" 
                + codigoPet + "'", Pet.class).get(0);
        
        List<Tutor> tutores = pet.getTutores();
        return tutores.stream().map(Tutor::getLogin).
                collect(Collectors.toList());
    }
    
    public List<Pet> buscarPets(String busca) {
        
        Tutor tutorLogado = ((LoginController) ((HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(true))
                .getAttribute("loginController")).getTutorLogado();
        
        this.buscaPet = busca;
        List<Pet> petsLocalizados =  ManagerDao.getCurrentInstance().findPetByLikeNome(this.buscaPet);
        List<Pet> petsTutorLogado =  tutorLogado.getPets();
        petsLocalizados.removeAll(petsTutorLogado);
        return petsLocalizados;
    }
    
    public void seguirPet(String hashPetSeguido, String hashPetSeguir) {
        
        try {
        
        Pet petSeguido = (Pet) ManagerDao.getCurrentInstance().read( "select p from Pet p" + " where p.hashPet = '" 
                + hashPetSeguido + "'", Pet.class).get(0);
     
        Pet petSeguir = (Pet) ManagerDao.getCurrentInstance().read( "select p from Pet p" + " where p.hashPet = '" 
                + hashPetSeguir + "'", Pet.class).get(0);
        
        List<Seguidor> seguidores = petSeguido.getSeguidores();
        
        this.seguidor.setPet(petSeguido);
        this.seguidor.setSeguidor(petSeguir);
        seguidores.add(seguidor);
        
        petSeguido.setSeguidores(seguidores);
        ManagerDao.getCurrentInstance().update(petSeguido);
        
        List<Seguindo> petsQueSigo = petSeguir.getSeguidos();
        
        this.seguindo.setPet(petSeguir);
        this.seguindo.setSeguindo(petSeguido);
        petsQueSigo.add(seguindo);
        
        petSeguir.setSeguidos(petsQueSigo);
        ManagerDao.getCurrentInstance().update(petSeguir);
        
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso! Agora " + 
                petSeguir.getNome() + " está seguindo " + petSeguido.getNome(), ""));
         
        }
        
        catch(ArrayIndexOutOfBoundsException e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Você Precisa ter um Pet Cadastrado no TikDoguinho!", ""));
        }
        
        
    }
    
}
