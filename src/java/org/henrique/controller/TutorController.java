/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.henrique.controller;
import java.io.IOException;
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
import org.henrique.model.negocios.Tutor;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author henrique
 */
@ManagedBean
@SessionScoped
public class TutorController {
    
    private Tutor tutorCadastro;
    private Tutor selection;
    private String tagImagem;
    
    @PostConstruct
    public void init() {
        this.tutorCadastro = new Tutor();
    }
    
    public void inserir() {
        
        ManagerDao.getCurrentInstance().insert(this.tutorCadastro);
        this.tutorCadastro = new Tutor();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tutor Cadastrado com Sucesso!"));
       
    }
    
    public void alterar() {
        ManagerDao.getCurrentInstance().update(this.selection);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Dados atualizados com sucesso"));
    }
    
    
    public void alterarSenha(String senha, String novaSenha, String confimacao) {
        
        Tutor tutorLogado = ((LoginController) ((HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(true))
                .getAttribute("loginController")).getTutorLogado();
        
        if(!tutorLogado.getSenha().equals(senha)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Senha Incorreta!"));
            return;
        }
        
        if(!novaSenha.equals(confimacao)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("A confirmação não bate com a nova senha!"));
            return;
        }
        
        tutorLogado.setSenha(novaSenha);
        ManagerDao.getCurrentInstance().update(tutorLogado);
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Senha Alterada com Sucesso!"));
        
    }
    
    
    public String cadastrarPet(String nome, String porte, String nascimento) {
        
        Pet pet = new Pet();
        Tutor tutorLogado = ((LoginController) ((HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(true))
                .getAttribute("loginController")).getTutorLogado();
        
        pet.setNome(nome);
        pet.setPorte(porte);
        pet.setMesAnoNascimento(nascimento);
        pet.setHashPet(pet.hashPets());
        List<Pet> petsTutor = tutorLogado.getPets();
        petsTutor.add(pet);
        tutorLogado.setPets(petsTutor);
        
        
        ManagerDao.getCurrentInstance().update(tutorLogado);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pet Cadastrado com Sucesso!"));
        
        return "gerenciarPets";
        
        
    }
    
    public void compartilharPet(String codigoPet) {
       
        try {
        Pet pet = (Pet) ManagerDao.getCurrentInstance().read( "select p from Pet p" + " where p.hashPet = '" + codigoPet + "'", Pet.class).get(0);
        
        Tutor tutorLogado = ((LoginController) ((HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(true))
                .getAttribute("loginController")).getTutorLogado();
        
         List<Pet> pets = tutorLogado.getPets();
         pets.add(pet);
         tutorLogado.setPets(pets);
         
         List<Tutor> tutoresPet = pet.getTutores();
         tutoresPet.add(tutorLogado);
         pet.setTutores(tutoresPet);
         
         
         ManagerDao.getCurrentInstance().update(tutorLogado);
         ManagerDao.getCurrentInstance().update(pet);
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Que massa! Agora você tem um Pet Compartilhado"));
        }
        catch(Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Código de Pet Inesixtente ou Pet já compartilhado",""));
            return;
            
        }
    }
    
    public List<Tutor> lerTutores() {
        
        List<Tutor> tutores = null;
        
        tutores = ManagerDao.getCurrentInstance().read("select t from Tutor t", Tutor.class);
        return tutores;
    }
    
    public List<Pet> lerPets() {
        
        Tutor tutorLogado = ((LoginController) ((HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(true))
                .getAttribute("loginController")).getTutorLogado();
        
        return tutorLogado.getPets();
        
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
        
        
        this.tagImagem = "http://localhost:8084/TikDoguinho/ServletExibirImagem";
 
    }
    
    public String fotoPerfilTutor() {
        
        Tutor tutorLogado = ((LoginController) ((HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(true))
                .getAttribute("loginController")).getTutorLogado();
        
        Arquivo arq = (Arquivo) (((HttpSession)FacesContext.getCurrentInstance().getExternalContext()
                 .getSession(true)).getAttribute("arquivo"));
       
        tutorLogado.setArquivo(arq);
        ManagerDao.getCurrentInstance().update(tutorLogado);
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Foto de Perfil Carregada com Sucesso"));
        
        return "perfilTutor";
        
    }

    public Tutor getTutorCadastro() {
        return tutorCadastro;
    }

    public void setTutorCadastro(Tutor tutorCadastro) {
        this.tutorCadastro = tutorCadastro;
    }

    public Tutor getSelection() {
        return selection;
    }

    public void setSelection(Tutor selection) {
        this.selection = selection;
    }

    public String getTagImagem() {
        return tagImagem;
    }

    public void setTagImagem(String tagImagem) {
        this.tagImagem = tagImagem;
    }
    
    
    
    
    
}
