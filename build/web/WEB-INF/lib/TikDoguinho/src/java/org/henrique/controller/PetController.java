/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.henrique.controller;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.henrique.model.dao.ManagerDao;
import org.henrique.model.negocios.Pet;
import org.henrique.model.negocios.Tutor;

/**
 *
 * @author Henri
 */
@ManagedBean
@SessionScoped
public class PetController {
    
    private Pet selection;
    
    @PostConstruct
    public void init() {
        this.selection = new Pet();
    }
    
    public Pet getSelection() {
        return selection;
    }

    public void setSelection(Pet selection) {
        this.selection = selection;
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
    
    public void atualizarPet(String codigoPet) {
        
        try {
        
        Pet petAtualiza = (Pet) ManagerDao.getCurrentInstance().read( "select p from Pet p" + " where p.hashPet = '" 
                + codigoPet + "'", Pet.class).get(0);
        
        petAtualiza.setNome(this.selection.getNome());
        petAtualiza.setMesAnoNascimento(this.selection.getMesAnoNascimento());
        petAtualiza.setPorte(this.selection.getPorte());
        ManagerDao.getCurrentInstance().update(petAtualiza);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Pet Atualizado com Sucesso!", ""));
        }
        
        catch(ArrayIndexOutOfBoundsException e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Por Segunça informe corretamente o TikDogCode do Pet!", ""));
        }
        
    }
    
    
}
