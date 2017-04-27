package ifpb.pos.ativ2.authagain.resource;


import ifpb.pos.ativ2.authagain.autentication.Credentials;
import ifpb.pos.ativ2.authagain.autentication.HeaderUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 29/03/2017, 11:28:40
 */
@Path("/twitter")
@Stateless
public class TwitterResources {


    @GET
    @Path("timeline/{user_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response timeline(@PathParam("user_id") String userId, 
            @QueryParam("oauth_token") String oauthToken, 
            @QueryParam("oauth_verifier") String oauthVerifier) {
        try {
            
            Credentials credentials = new Credentials();
            credentials.setOauth_token(oauthToken);
            credentials.setOauth_verifier(oauthVerifier);
            
            String url = "https://api.twitter.com/1.1/statuses/user_timeline.json";
            Client cliente = ClientBuilder.newClient();
            WebTarget target = cliente.target(url);
            
            Map<String, String> options = new HashMap<>();
            options.put("user_id", userId);
            options.put("count", "200");
            options.put("include_rts", "true");
            
            target = target
                    .queryParam("user_id", userId)
                    .queryParam("count", 200)
                    .queryParam("include_rts", true);
            
            String header = HeaderUtils.headerWithOtherPairs(options, "GET", url, credentials);

            System.out.println("HEADER PARA TIMELINE: " + header);

            Response resposta = target
                    .request()
                    .header("Authorization", header)
                    .get();

            return resposta;
        } catch (Exception ex) {
            Logger.getLogger(TwitterResources.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.noContent().build();
    }
    
    @GET
    @Path("alltimeline/{user_id}/{max_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response alltimeline(
            @PathParam("user_id") String userId,
            @PathParam("max_id") String maxId,
            @QueryParam("oauth_token") String oauthToken, 
            @QueryParam("oauth_verifier") String oauthVerifier) {
        
        
        try {
            
            Credentials credentials = new Credentials();
            credentials.setOauth_token(oauthToken);
            credentials.setOauth_verifier(oauthVerifier);
            
            String url = "https://api.twitter.com/1.1/statuses/user_timeline.json";
            Client cliente = ClientBuilder.newClient();
            WebTarget target = cliente.target(url);
            
            Map<String, String> options = new HashMap<>();
            options.put("user_id", userId);
            options.put("count", "200");
            options.put("max_id", maxId);
            options.put("include_rts", "true");
            
            target = target
                    .queryParam("user_id", userId)
                    .queryParam("count", 200)
                    .queryParam("max_id", maxId)
                    .queryParam("include_rts", true);
            
            String header = HeaderUtils.headerWithOtherPairs(options, "GET", url, credentials);

            System.out.println("HEADER PARA TIMELINE: " + header);

            Response resposta = target
                    .request()
                    .header("Authorization", header)
                    .get();

            return resposta;
        } catch (Exception ex) {
            Logger.getLogger(TwitterResources.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.noContent().build();
    }
    
    @GET
    @Path("myretweets")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMyFirstRetweets(
            @PathParam("max_id") String maxId, 
            @QueryParam("oauth_token") String oauthToken, 
            @QueryParam("oauth_verifier") String oauthVerifier) {
        try {
            Credentials credentials = new Credentials();
            credentials.setOauth_token(oauthToken);
            credentials.setOauth_verifier(oauthVerifier);

            String url = "https://api.twitter.com/1.1/statuses/retweets_of_me.json";
            System.out.println("Eis a URL: " + url);
            Client cliente = ClientBuilder.newClient();
            WebTarget target = cliente.target(url);

            Map<String, String> options = new HashMap<>();
            options.put("include_user_entities", "false");
            

            target = target
                    .queryParam("include_user_entities", false);

            String header = HeaderUtils.headerWithOtherPairs(options, "GET", url, credentials);

            System.out.println("Aqui a header: " + header);

            System.out.println("Target: " + target.toString());

            Response resposta = target
                    .request()
                    .header("Authorization", header)
                    .get();

            return resposta;
        } catch (Exception ex) {
            Logger.getLogger(TwitterResources.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.noContent().build();
    }

    
    @GET
    @Path("myretweets/{max_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRetweetsWithMaxId(
            @PathParam("max_id") String maxId, 
            @QueryParam("oauth_token") String oauthToken, 
            @QueryParam("oauth_verifier") String oauthVerifier) {
        try {
            Credentials credentials = new Credentials();
            credentials.setOauth_token(oauthToken);
            credentials.setOauth_verifier(oauthVerifier);

            String url = "https://api.twitter.com/1.1/statuses/retweets_of_me.json";
            System.out.println("Eis a URL: " + url);
            Client cliente = ClientBuilder.newClient();
            WebTarget target = cliente.target(url);

            Map<String, String> options = new HashMap<>();
            options.put("count", "20");
            options.put("max_id", maxId);
            options.put("include_user_entities", "false");
            

            target = target
                    .queryParam("count", 100)
                    .queryParam("max_id", maxId)
                    .queryParam("include_user_entities", false);

            String header = HeaderUtils.headerWithOtherPairs(options, "GET", url, credentials);

            System.out.println("Aqui a header: " + header);

            System.out.println("Target: " + target.toString());

            Response resposta = target
                    .request()
                    .header("Authorization", header)
                    .get();

            return resposta;
        } catch (Exception ex) {
            Logger.getLogger(TwitterResources.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.noContent().build();
    }

    
    
    @GET
    @Path("followers/{user_id}/{cursor}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFollowersCursor(
            @PathParam("user_id") String userId, 
            @PathParam("cursor") String cursor,
            @QueryParam("oauth_token") String oauthToken, 
            @QueryParam("oauth_verifier") String oauthVerifier) {
        try {
            Credentials credentials = new Credentials();
            credentials.setOauth_token(oauthToken);
            credentials.setOauth_verifier(oauthVerifier);

            String url = "https://api.twitter.com/1.1/followers/list.json";
            System.out.println("Eis a URL: " + url);
            Client cliente = ClientBuilder.newClient();
            WebTarget target = cliente.target(url);

            Map<String, String> options = new HashMap<>();
            options.put("count", "200");
            options.put("cursor", cursor);
            options.put("include_user_entities", "false");
            options.put("user_id", userId);
            options.put("skip_status", "true");

            target = target
                    .queryParam("count", 200)
                    .queryParam("cursor", cursor)
                    .queryParam("include_user_entities", false)
                    .queryParam("user_id", userId)
                    .queryParam("skip_status", true);

            String header = HeaderUtils.headerWithOtherPairs(options, "GET", url, credentials);

            System.out.println("Aqui a header: " + header);

            System.out.println("Target: " + target.toString());

            Response resposta = target
                    .request()
                    .header("Authorization", header)
                    .get();

            return resposta;
        } catch (Exception ex) {
            Logger.getLogger(TwitterResources.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.noContent().build();
    }



    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response twitar(@FormParam("status") String status,
            @QueryParam("oauth_token") String oauthToken, 
            @QueryParam("oauth_verifier") String oauthVerifier) {
        try {
            Credentials credentials = new Credentials();
            credentials.setOauth_token(oauthToken);
            credentials.setOauth_verifier(oauthVerifier);
            
            String url = "https://api.twitter.com/1.1/statuses/update.json";
            String header = HeaderUtils.headerStatusToUpdate(status, "POST", url, credentials);
            Client cliente = ClientBuilder.newClient();
            WebTarget target = cliente.target(url);
            Form form = new Form("status", status);
            Response resposta = target
                    .request()
                    .header("Authorization", header)
                    .post(Entity.form(form));

            return resposta;
        } catch (Exception ex) {
            Logger.getLogger(TwitterResources.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.noContent().build();
    }

    
    
    @GET
    @Path("userinfos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response userinfos(@Context UriInfo uriInfo, 
            @QueryParam("oauth_token") String oauthToken, 
            @QueryParam("oauth_verifier") String oauthVerifier) {
        try {

            Credentials credentials = new Credentials();
            credentials.setOauth_token(oauthToken);
            credentials.setOauth_verifier(oauthVerifier);
            
            Response resposta;

            String url = "https://api.twitter.com/1.1/account/verify_credentials.json";
            Client cliente = ClientBuilder.newClient();
            WebTarget target = cliente.target(url);
            String header = HeaderUtils.headerUpdate("GET", url, credentials);

            System.out.println("HEADER PARA TIMELINE: " + header);

            resposta = target
                    .request()
                    .header("Authorization", header)
                    .get();
            
            return resposta;
        } catch (Exception ex) {
            Logger.getLogger(TwitterResources.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.noContent().build();
    }
    
    @GET
    @Path("retweeters/{status_id}/{cursor}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retweeters(
            @PathParam("status_id") String id, 
            @PathParam("cursor") String cursor,
            @QueryParam("oauth_token") String oauthToken, 
            @QueryParam("oauth_verifier") String oauthVerifier) {
        try {
            
            Credentials credentials = new Credentials();
            credentials.setOauth_token(oauthToken);
            credentials.setOauth_verifier(oauthVerifier);
            
            String url = "https://api.twitter.com/1.1/statuses/retweeters/ids.json";
            Client cliente = ClientBuilder.newClient();
            WebTarget target = cliente.target(url);
            
            Map<String, String> options = new HashMap<>();
            options.put("id", id);
            options.put("cursor", cursor);
            
            target = target
                    .queryParam("id", id)
                    .queryParam("cursor", cursor);
            
            String header = HeaderUtils.headerWithOtherPairs(options, "GET", url, credentials);

            System.out.println("HEADER PARA TIMELINE: " + header);

            Response resposta = target
                    .request()
                    .header("Authorization", header)
                    .get();

            return resposta;
        } catch (Exception ex) {
            Logger.getLogger(TwitterResources.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.noContent().build();
    }

    
    
}
