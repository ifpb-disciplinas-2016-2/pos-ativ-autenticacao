/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.pos.ativ2.authagain.web;

import ifpb.pos.ativ2.authagain.autentication.Credentials;
import ifpb.pos.ativ2.authagain.autentication.HeaderUtils;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "CallbackServlet", urlPatterns = {"/CallbackServlet"})
public class CallbackServlet extends HttpServlet {

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
        
        
        String requestQuery = request.getQueryString();
        
        String[] querySplit = requestQuery.split("&");
        String oauth_token = querySplit[0].split("=")[1];
        String oauth_verifier = querySplit[1].split("=")[1];
                
        Credentials credentials = new Credentials();
        
        credentials.setOauth_token(oauth_token);
        credentials.setOauth_verifier(oauth_verifier);
        
        try {
            Client cliente = ClientBuilder.newClient();
            WebTarget target = cliente.target("https://api.twitter.com/oauth/access_token");
            Form form = new Form("oauth_verifier", oauth_verifier);

            String header = HeaderUtils.headerUpdate("POST", "https://api.twitter.com/oauth/access_token", credentials);

            Response resposta = target
                    .request()
                    .header("Authorization", header)
                    .post(Entity.form(form));

            String tokens = resposta.readEntity(String.class);
            String[] split = tokens.split("&");
            //tokens finais
            String token_a = split[0].split("=")[1];
            String token_v = split[1].split("=")[1];
            credentials.setOauth_token(token_a);
            credentials.setOauth_verifier(token_v);
        } catch (Exception ex) {
            Logger.getLogger(CallbackServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("NOVA CREDENTIALS: " + credentials);
                
        HttpSession session = (HttpSession) request.getSession();
        session.setAttribute("credentials", credentials);
        
//        response.sendRedirect("http://localhost:8080/ativ2-authagain/CalculaPopularidadeServlet");
        response.sendRedirect("http://localhost:8080/ativ2-authagain/faces/popularidade.xhtml");

        
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

}
