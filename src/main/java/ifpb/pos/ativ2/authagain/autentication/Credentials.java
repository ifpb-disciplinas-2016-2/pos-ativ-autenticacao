package ifpb.pos.ativ2.authagain.autentication;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
//import javax.annotation.PostConstruct;
//import javax.annotation.PreDestroy;
//import javax.enterprise.context.SessionScoped;
//import javax.inject.Named;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 30/03/2017, 09:44:08
 */
//@Named
//@SessionScoped
public class Credentials implements Serializable {

    private String uuid;
    
    //here set the your app access key and access secret 
    private String acess_key = "SP3YkYRKAzuJjvBo6qsStTINg";
    private String acess_secret = "IMdpxb7dJYaWRt9jCqJsgPhqzi6FMVlxArDQRphljY86bXL5dk";

    private String oauth_token = "";
    private String oauth_verifier = "";

    public Credentials() {
        this.uuid = UUID.randomUUID().toString();
    }

//    @PostConstruct
//    public void init(){
//        System.out.println("CONSTRUIU CREDENTIALS: " + this.toString());
//    }
//    
//    @PreDestroy
//    public void clean(){
//        System.out.println("Destrui cREDENTIALLLLZZZZ: " + this.toString());
//    }
    
    public String getAcess_key() {
        return acess_key;
    }

    public void setAcess_key(String acess_key) {
        this.acess_key = acess_key;
    }

    public String getAcess_secret() {
        return acess_secret;
    }

    public void setAcess_secret(String acess_secret) {
        this.acess_secret = acess_secret;
    }

    public String getOauth_token() {
        return oauth_token;
    }

    public void setOauth_token(String oauth_token) {
        this.oauth_token = oauth_token;
    }

    public String getOauth_verifier() {
        return oauth_verifier;
    }

    public void setOauth_verifier(String oauth_verifier) {
        this.oauth_verifier = oauth_verifier;
    }

    @Override
    public String toString() {
        return "Credentials{" + "uuid=" + uuid + ", acess_key=" + acess_key + ", acess_secret=" + acess_secret + ", oauth_token=" + oauth_token + ", oauth_verifier=" + oauth_verifier + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + Objects.hashCode(this.uuid);
        hash = 31 * hash + Objects.hashCode(this.acess_key);
        hash = 31 * hash + Objects.hashCode(this.acess_secret);
        hash = 31 * hash + Objects.hashCode(this.oauth_token);
        hash = 31 * hash + Objects.hashCode(this.oauth_verifier);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Credentials other = (Credentials) obj;
        if (!Objects.equals(this.uuid, other.uuid)) {
            return false;
        }
        if (!Objects.equals(this.acess_key, other.acess_key)) {
            return false;
        }
        if (!Objects.equals(this.acess_secret, other.acess_secret)) {
            return false;
        }
        if (!Objects.equals(this.oauth_token, other.oauth_token)) {
            return false;
        }
        if (!Objects.equals(this.oauth_verifier, other.oauth_verifier)) {
            return false;
        }
        return true;
    }
    
    

}
