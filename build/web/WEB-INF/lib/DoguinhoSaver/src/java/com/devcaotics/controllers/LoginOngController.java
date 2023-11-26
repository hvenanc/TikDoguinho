/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devcaotics.controllers;

import com.devcaotics.model.dao.ManagerDao;
import com.devcaotics.model.negocio.Ong;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author ALUNO
 */
@ManagedBean
@SessionScoped
public class LoginOngController {
    
    private Ong ongLogada;
    
    public String login(String cnpj, String senha){
        
        try{
            Ong logada = (Ong) ManagerDao.getCurrentInstance()
                .read("select o from Ong o where o.cnpj='"+cnpj
                        +"' and o.senha='"+senha+"'", Ong.class).get(0);
        
            this.ongLogada = logada;
            
            return "indexOng";
        }catch(Exception e){
            
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage("erro: ","Não foi possível realizar o login!"));
            
        }
        
        return null;
        
    }
    
    public void logout(){
        
        this.ongLogada = null;
        
    }

    public Ong getOngLogada() {
        return ongLogada;
    }

    public void setOngLogada(Ong ongLogada) {
        this.ongLogada = ongLogada;
    }
    
    
    
}
