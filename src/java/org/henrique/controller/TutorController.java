/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.henrique.controller;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.henrique.model.dao.ManagerDao;
import org.henrique.model.negocios.Tutor;

/**
 *
 * @author henrique
 */
@ManagedBean
@SessionScoped
public class TutorController {
    
    private Tutor tutorCadastro;
    
    @PostConstruct
    public void init() {
        this.tutorCadastro = new Tutor();
    }
    
    public void inserir() {
        
        ManagerDao.getCurrentInstance().insert(this.tutorCadastro);
        this.tutorCadastro = new Tutor();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tutor Cadastrado com Sucesso!"));
       
    }

    public Tutor getTutorCadastro() {
        return tutorCadastro;
    }

    public void setTutorCadastro(Tutor tutorCadastro) {
        this.tutorCadastro = tutorCadastro;
    }
    
    
    
}
