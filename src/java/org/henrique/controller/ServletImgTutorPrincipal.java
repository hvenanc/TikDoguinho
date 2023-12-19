/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.henrique.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.henrique.model.dao.ManagerDao;
import org.henrique.model.negocios.Pet;

/**
 *
 * @author Henri
 */
@WebServlet(name = "ServletImgTutorPrincipal", urlPatterns = {"/ServletImgTutorPrincipal"})
public class ServletImgTutorPrincipal extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String codigoPet = request.getParameter("codPet");
        
        Pet pet = (Pet) ManagerDao.getCurrentInstance().read( "select p from Pet p" + " where p.hashPet = '" 
                + codigoPet + "'", Pet.class).get(0);
        
        byte[] imagem = pet.getTutores().get(0).getArquivo().getArquivo();
        
        response.setContentType("image/jpg");
        
        response.getOutputStream().write(imagem);
        
        response.getOutputStream().flush();
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
