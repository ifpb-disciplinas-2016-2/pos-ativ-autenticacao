/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.pos.ativ2.authagain.web;


import ifpb.pos.ativ2.authagain.autentication.Credentials;
import ifpb.pos.ativ2.authagain.autentication.AuthenticationHeaderTwitter;
import ifpb.pos.ativ2.authagain.autentication.HeaderUtils;
import ifpb.pos.ativ2.authagain.autentication.Pair;
//import ifpb.pos.ativ2.authagain.resource.TwitterResources;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

/**
 *
 * @author natarajan
 */
@WebServlet(name = "InitialServlet", urlPatterns = {"/InitialServlet"})
public class InitialServlet extends HttpServlet {
    
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        HttpSession session = (HttpSession) request.getSession(true);
        Credentials credentials = new Credentials();
        
        String url = "https://api.twitter.com/oauth/authenticate?" + requestFirstToken(credentials);
        
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.sendRedirect(url);
        
        
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
        processRequest(request, response);
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

    private String requestFirstToken(Credentials credentials) {
        
        System.out.println("REQUEST FIRST NO resource");
        try {
            Client cliente = ClientBuilder.newClient();
            WebTarget target = cliente.target("https://api.twitter.com/oauth/request_token");
            Form form = new Form();
            String header = HeaderUtils.headerUpdate("POST", "https://api.twitter.com/oauth/request_token", credentials);

            Response resposta = target
                    .request()
                    .header("Authorization", header)
                    .post(Entity.form(form));
            return resposta.readEntity(String.class);

        } catch (Exception ex) {
            Logger.getLogger(InitialServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    
}
