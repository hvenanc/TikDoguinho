/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.henrique.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.henrique.model.dao.ManagerDao;
import org.henrique.model.negocios.Video;

/**
 *
 * @author Henri
 */
@WebServlet(name = "ServletCarregarVideos", urlPatterns = {"/ServletCarregarVideos"})
public class ServletCarregarVideos extends HttpServlet {

  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String codigoVideo = request.getParameter("codVideo");
        
        Video video = (Video) ManagerDao.getCurrentInstance().read( "select v from Video v" + " where v.codigo = '" 
                + codigoVideo + "'", Video.class).get(0);
        
        System.out.println(video.getNomeArquivo());
        
        byte[] videoPublicacao = video.getArqVideo();
        
        response.setContentType("video/mp4");
        
        response.getOutputStream().write(videoPublicacao);
        
        response.getOutputStream().flush();
      
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
