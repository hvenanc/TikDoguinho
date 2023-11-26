/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.henrique.controller;

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
public class LoginController {
    
    private Tutor tutorLogado;
    
    public String logar(String login, String senha) {
        
        try {
            Tutor tutorLogin = (Tutor) ManagerDao.getCurrentInstance().read(
                    "select t from Tutor t" + " where t.login = '" + login  
                            + "' and t.senha = '" + senha + "'" , Tutor.class).get(0);
            
            this.tutorLogado = tutorLogin;
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Bem-Vindo ao TikDoguinho","Vamos registrar os belos momentos com nosso pets"));
            return "home";
            
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login ou Senha Inválidos!", "Tente Novamente"));
            return null;
        }
        
    }
    
    public String logout() {
        this.tutorLogado = null;
        return "index";
    }

    public Tutor getTutorLogado() {
        return tutorLogado;
    }

    public void setTutorLogado(Tutor tutorLogado) {
        this.tutorLogado = tutorLogado;
    }
    
    
    
}
