/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.pos.ativ2.authagain.services;

import com.sun.net.httpserver.HttpServer;
import ifpb.pos.ativ2.authagain.autentication.Credentials;
import ifpb.pos.ativ2.authagain.models.ImmutableResultTwitter;
import ifpb.pos.ativ2.authagain.models.InfluenceIm;
import ifpb.pos.ativ2.authagain.models.ResultTwitterFormatted;
import java.text.DecimalFormat;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author natarajan
 */
@Named
@RequestScoped
public class PopularidadeControler {
    
    private PopularidadeService service;
    private ImmutableResultTwitter result;
    private static DecimalFormat decimalFormat = new DecimalFormat("###.##");
    private Credentials credentials;
    

    public PopularidadeControler() {
        FacesContext currentInstance = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) currentInstance.getExternalContext().getSession(false);
        this.credentials = (Credentials) session.getAttribute("credentials");
        this.service = new PopularidadeService(this.credentials);
    }

    
//    public ResultTwitterFormatted getResult(){
//        if (this.result == null){
////            this.result = ;
//            this.result = new ResultTwitterFormatted(this.service.getResult());
//        }
//            
//        return result;
//    }
    
    public ImmutableResultTwitter getResult(){
        if (this.result == null)
            this.result = this.service.getResult();
        return result;
    }
    
    public String buscarOutro(){
        FacesContext currentInstance = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) currentInstance.getExternalContext().getSession(false);
        session.invalidate();
        return "/index.html";
    }
    
    public String getFormattedInfluence(InfluenceIm influence){
        double valor = influence.influence();
        return decimalFormat.format(valor * 100) + " %";
    }
    
    /*
        a fórmula da popularidade não indica se o resultado é normalizado. Escolhemos 
        mostrar como uma porcentagem mas ela também pode ser entendidas como pontuação
        com casas decimais. Portanto, aqui é o lugar onde pode ser realizada a mudança
        para mostrar esse resultado .
    */
    public String getFormattedPop(){
        return decimalFormat.format(this.result.pop()) + " %";
    }
    
}
