/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.pos.ativ2.authagain.services;

import ifpb.pos.ativ2.authagain.autentication.Credentials;
import ifpb.pos.ativ2.authagain.models.ImmutableResultTwitter;
import ifpb.pos.ativ2.authagain.models.TwitterUserInfo;
import ifpb.pos.ativ2.authagain.redis.ResultRepositoryRedis;

/**
 *
 * @author natarajan
 */
public class PopularidadeService {
    
    private TwitterUserInfo userInfos;
    private Credentials credentials;
    private TwitterServiceClient serviceClient;
    
    
    public PopularidadeService(Credentials credentials) {
        this.credentials = credentials;
        this.serviceClient = new TwitterServiceClient(this.credentials);
    }

    public TwitterUserInfo getUserInfos() {
        return serviceClient.getInfo();
    }
    
    public ImmutableResultTwitter getResult(){
        
        System.out.println("\n PEGANDO INFOS DO USUÁRIO LOGADO\n");
        this.userInfos = serviceClient.getInfo();
        System.out.println("user que chegou: " + this.userInfos);
        
        ImmutableResultTwitter r = new ResultRepositoryRedis().getCachedResult(this.userInfos.id_str());
        
        if (r == null) {
            
            System.out.println("ainda não tem esse resultado. Mande calcular");

           ImmutableResultTwitter resultado = serviceClient.calcularResultado();
           new ResultRepositoryRedis().save(resultado);

           return resultado;
        
        } else {
            return r;
        }

    }
    
    
    
}
