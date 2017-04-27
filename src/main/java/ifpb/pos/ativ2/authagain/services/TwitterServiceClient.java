/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.pos.ativ2.authagain.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ifpb.pos.ativ2.authagain.autentication.Credentials;
import ifpb.pos.ativ2.authagain.models.ImmutableInfluenceIm;
import ifpb.pos.ativ2.authagain.models.ImmutableResultTwitter;
import ifpb.pos.ativ2.authagain.models.Influence;
import ifpb.pos.ativ2.authagain.models.InfluenceIm;
import ifpb.pos.ativ2.authagain.models.TwitterFollowers;
import ifpb.pos.ativ2.authagain.models.TwitterRetweetedStatus;
import ifpb.pos.ativ2.authagain.models.TwitterRetweetersList;
import ifpb.pos.ativ2.authagain.models.TwitterSimpleUser;
import ifpb.pos.ativ2.authagain.models.TwitterStatus;
import ifpb.pos.ativ2.authagain.models.TwitterTimelineStatus;
import ifpb.pos.ativ2.authagain.models.TwitterUser;
import ifpb.pos.ativ2.authagain.models.TwitterUserInfo;
import ifpb.pos.ativ2.authagain.redis.ResultRepositoryRedis;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 *
 * @author natarajan
 */
public class TwitterServiceClient {
    
    private List<TwitterUser> followers = new ArrayList<>();
    private final List<TwitterTimelineStatus> status = new ArrayList<>();
    private final List<TwitterRetweetedStatus> retweets = new ArrayList<>();
    private TwitterUserInfo userInfos;
    private final Map<Long, Influence> influenceMap = new HashMap<>();
    private double pop = 0;
    private final List<Influence> influence = new ArrayList<>();
    private ResultRepositoryRedis repository;
    private final Credentials credentials;

    public TwitterServiceClient(Credentials credentials) {
        this.credentials = credentials;
        followers = new ArrayList();
    }
    
    
    public TwitterUserInfo getInfo() {
        if (this.userInfos == null) {
            setUserInfo();
        }
        
        return this.userInfos;
    }
    
    public void setUserInfo() {
        Client cliente = ClientBuilder.newClient();
        WebTarget target = cliente
                .target("http://localhost:8080/ativ2-authagain/api/twitter/userinfos/")
                .queryParam("oauth_token", credentials.getOauth_token())
                .queryParam("oauth_verifier", credentials.getOauth_verifier());
        
        Response resposta = target
                .request().get();
        String result = resposta.readEntity(String.class);
        this.userInfos = jsonToUserInfo(result);
    }

    private TwitterUserInfo jsonToUserInfo(String json) {
        try {
            ObjectMapper objectMapper
                    = new ObjectMapper()
                            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                                    false);
            TwitterUserInfo userInfo
                    = objectMapper.readValue(json,
                            new TypeReference<TwitterUserInfo>() {
                    });

            return userInfo;
        } catch (IOException ex) {
            Logger.getLogger(TwitterServiceClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
        
    
    public String timeline() {

        Client cliente = ClientBuilder.newClient();
        WebTarget target = cliente
                .target("http://localhost:8080/ativ2-authagain/api/twitter/timeline")
                .queryParam("oauth_token", credentials.getOauth_token())
                .queryParam("oauth_verifier", credentials.getOauth_verifier());
        
        Response resposta = target
                .request()
                .get();
        String result =  resposta.readEntity(String.class);
        
        System.out.println(result);
        
        return result;
    }
    
    
    public void alltimeline() {
        
        // DO A SINGLE REQUEST DO TIMELINE
        Client cliente = ClientBuilder.newClient();

        WebTarget target = cliente.target("http://localhost:8080/ativ2-authagain/api/twitter/timeline/{user_id}")
                .resolveTemplate("user_id", userInfos.id_str())
                .queryParam("oauth_token", credentials.getOauth_token())
                .queryParam("oauth_verifier", credentials.getOauth_verifier());
        
        Response resposta = target
                .request()
                .get();
        String result =  resposta.readEntity(String.class);
        
        List<TwitterTimelineStatus> tweets = parseJsonToStatuses(result);
        
        if (tweets.size() > 0)
            this.status.addAll(tweets);
        
        //now request the tweets olders then last
        int i = 1;
        TwitterTimelineStatus last;
        
        do {
                
            last = tweets.get(tweets.size() - 1);
            System.out.println("LAST: " + i++ + " - "+ last);
        
            Client cliente2 = ClientBuilder.newClient();
            WebTarget target2 = cliente2
                    .target("http://localhost:8080/ativ2-authagain/api/twitter/alltimeline/{user_id}/{max_id}")
                    .resolveTemplate("user_id", userInfos.id_str())
                    .resolveTemplate("max_id", last.id() - 1)
                    .queryParam("oauth_token", credentials.getOauth_token())
                    .queryParam("oauth_verifier", credentials.getOauth_verifier());

            Response resposta2 = target2
                    .request()
                    .get();
            String result2 = resposta2.readEntity(String.class);

            tweets = parseJsonToStatuses(result2);
            
            if (tweets.size() > 0) {
                this.status.addAll(tweets);    
            } 
            
        } while (!last.id_str().equals(status.get(status.size() - 1).id_str()) && i < 5); //com limite
//        } while (!last.id_str().equals(status.get(status.size() - 1).id_str()));
        
    }
    
    private List<TwitterTimelineStatus> parseJsonToStatuses(String json) {
        try {
            ObjectMapper objectMapper
                    = new ObjectMapper()
                            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                                    false);
            List<TwitterTimelineStatus> tweets
                    = objectMapper.readValue(json,
                            new TypeReference<List<TwitterTimelineStatus>>() {
                    });

            return tweets;
        } catch (IOException ex) {
            Logger.getLogger(TwitterServiceClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>();
    }
    
    
    public List<TwitterUser> getMyFollowers() {  
        if (userInfos.followers_count() > 0 && followers.size() < 1) {
            System.out.println("FOI BUSCAR OS FOLLOWERS---->");
            Long cursor = new Long(-1);
            do {            
                String firstResult = followersWithCursor(cursor.toString());
                TwitterFollowers twitterFollowers = jsonToTwitterFollowers(firstResult);
                this.followers.addAll(Arrays.asList(twitterFollowers.users()));
                cursor = twitterFollowers.next_cursor();
            } while (cursor != 0);    
        }
        return this.followers;
    }
    
    public String followersWithCursor(String cursor) {

        Client cliente = ClientBuilder.newClient();
        WebTarget target = cliente
                .target("http://localhost:8080/ativ2-authagain/api/twitter/followers/{user_id}/{cursor}")
                .resolveTemplate("user_id", userInfos.id_str())
                .resolveTemplate("cursor", cursor)
                .queryParam("oauth_token", credentials.getOauth_token())
                .queryParam("oauth_verifier", credentials.getOauth_verifier());
                
        //Basic am9iOjEyMw== Authorization 
        Response resposta = target
                .request()
//                .header("Authorization", "Basic am9iOjEyMw==")
                .get();
        String result = resposta.readEntity(String.class);
        
        System.out.println("PEGANDO FOLLOWERES");
        
        return result;
    }
    
    public TwitterFollowers jsonToTwitterFollowers(String json) {
        try {
            ObjectMapper objectMapper
                    = new ObjectMapper()
                            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                                    false);
            TwitterFollowers followers
                    = objectMapper.readValue(json,
                            new TypeReference<TwitterFollowers>() {
                    });

            return followers;
        } catch (IOException ex) {
            Logger.getLogger(TwitterServiceClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public List<TwitterStatus> requestTimeline(String json) {
        try {
            ObjectMapper objectMapper
                    = new ObjectMapper()
                            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                                    false);
            List<TwitterStatus> twitters
                    = objectMapper.readValue(json,
                            new TypeReference<List<TwitterStatus>>() {
                    });

            return twitters;
        } catch (IOException ex) {
            Logger.getLogger(TwitterServiceClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>();
    }
            
    
    
    public void calculaPop() {
        
        int favoriteds = 0;
        int retweeteds = 0;
        
        for (TwitterTimelineStatus t : this.status) {
            
            if (t.favorited() == true) 
                favoriteds++;
            
//            if (t.retweeted() == true) {
//                retweeteds++;
//            }
        }
        
        retweeteds = this.retweets.size();
        
        favoriteds = (favoriteds > 0 ? favoriteds : 1);
        
        double pop = (double) userInfos.friends_count() / userInfos.followers_count();
        
        double pop2 = ((double) (userInfos.favourites_count() + retweeteds))/ favoriteds;
        
        pop = pop * pop2;
        
        System.out.println("friends: " + userInfos.friends_count());
        System.out.println("followers: " + userInfos.followers_count());
        System.out.println("favs: " + userInfos.favourites_count());
        System.out.println("faveds: " + favoriteds);
        System.out.println("ret: " + retweeteds);
        
        this.pop = (double) pop;
        
    }

    public void calculaInfluence() {
        
        this.followers = getMyFollowers();
        
        for (TwitterUser f : this.followers) {
            
            influenceMap.put(f.id(), new Influence(f));
            
        }
        
        for (TwitterTimelineStatus status : this.status) {
            
            //verifica as menções e adicionar nas influencias dos followers
            TwitterSimpleUser[] mentionedUsers = status.entities().user_mentions();
            for (TwitterSimpleUser u : mentionedUsers) {
                
                if (isFollower(u)) {
                
                    Influence influence = influenceMap.get(u.id());
                    if (influence != null) {
                        influence.addMention();
                    }
                }

            }
            
            //AQUI IRÁ A PARTE DOS RETWEETS
//            if (status.retweeted()) {
//                System.out.println(status.id());
//                List<Long> userRetweeters = getStatusRetweetsUsers(status);
//                
//                for (Long userId : userRetweeters) {
//                    if (isFollower(userId)) {
//
//                        Influence influence = influenceMap.get(userId);
//                        if (influence != null) {
//                            influence.addRetweeteds();
//                        }
//                    }
//                }
//            }
        }//finish main for
        
        //CALCULANDO OS RETWEETS
        for (TwitterRetweetedStatus retweetedStatus : this.retweets) {
            System.out.println(retweetedStatus);
            List<Long> userRetweeters = getStatusRetweetsUsers(retweetedStatus);
                
                for (Long userId : userRetweeters) {
                    if (isFollower(userId)) {

                        Influence influence = influenceMap.get(userId);
                        if (influence != null) {
                            influence.addRetweeteds();
                        }
                    }
                }
        }
        
        // atualiza a influencia, mesmo dos amigos que não tiveram iteração 
        // para que fiquem no mínimo com 50%
        for (Influence inf : this.influence) {
            inf.setInfluence();
        }
        
        List<Influence> influences = new ArrayList(influenceMap.values());
        
        Comparator<Influence> influenceComparator = (i1, i2) -> new Double(i1.getInfluence()).compareTo(i2.getInfluence());
        influences.sort(influenceComparator.reversed());
        
        System.out.println("\n PEGOU INFLUENCES: ");
//        System.out.println(influences);
        
        this.influence.addAll(influences.subList(0, 10));

    }

    private List<Long> getStatusRetweetsUsers(TwitterRetweetedStatus status) {
        
        Long nextCursor = new Long(-1);
        
        List<Long> result = new ArrayList<>();
        
        System.out.println("\nFOI BUSCAR UNS RETWEETERS \n");
        
        do {
            
            String buscarRetweeters = retweetersWithCursor(status.id_str(), nextCursor.toString());        
            TwitterRetweetersList retweetersList = jsonToRetweetersList(buscarRetweeters);
            nextCursor = retweetersList.next_cursor();
            result.addAll(Arrays.asList(retweetersList.ids()));
            
        } while (nextCursor != 0);
        
        return result;
    }
    
    private boolean isFollower(TwitterSimpleUser user) {
        
        if (this.followers != null){
            for (TwitterUser u : this.followers) {
                if (u.id_str().equals(user.id_str()))
                    return true;
            }
        }
        
        return false;
    }
    
    private boolean isFollower(Long userId) {
        
        if (this.followers != null){
            for (TwitterUser u : this.followers) {
                if (u.id() == userId)
                    return true;
            }
        }
        
        return false;
    }
 
    private String retweetersWithCursor(String status, String cursor) {

        Client cliente = ClientBuilder.newClient();
        WebTarget target = cliente
                .target("http://localhost:8080/ativ2-authagain/api/twitter/retweeters/{status_id}/{cursor}")
                .resolveTemplate("status_id", status)
                .resolveTemplate("cursor", cursor)
                .queryParam("oauth_token", credentials.getOauth_token())
                .queryParam("oauth_verifier", credentials.getOauth_verifier());
                
        Response resposta = target
                .request()
                .get();
        String result = resposta.readEntity(String.class);
        
        return result;
    }
    
    public TwitterRetweetersList jsonToRetweetersList(String json) {
        try {
            ObjectMapper objectMapper
                    = new ObjectMapper()
                            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                                    false);
            TwitterRetweetersList followers
                    = objectMapper.readValue(json,
                            new TypeReference<TwitterRetweetersList>() {
                    });

            return followers;
        } catch (IOException ex) {
            Logger.getLogger(TwitterServiceClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }



    ImmutableResultTwitter calcularResultado() {
        
        
        System.out.println("\n PEGANDO A TIMELINE DO USUÁRIO\n");
        alltimeline();
        
        System.out.println("\n PEGANDO OS TWEETS DO USUÁRIO QUE FORAM RETUITADOS\n");
        getRet();

        System.out.println("\n CALCULANDO A POP\n");
        calculaPop();

        System.out.println("\n CALCULANDO A INFLUENCIA\n");
        calculaInfluence();

        return criarResultado();
        

    }

    private ImmutableResultTwitter criarResultado() {
        InfluenceIm[] resultList = new InfluenceIm[influence.size()];
        
        int j = 0;
        for (Influence i : this.influence) {
            System.out.println(i);
            InfluenceIm im = ImmutableInfluenceIm.builder()
                    .influence(i.getInfluence())
                    .userW(i.getUserW())
                    .build();
            resultList[j] = im;
            j++;
        }
        
        ImmutableResultTwitter valueObject =
            ImmutableResultTwitter.builder()
                    .pop(this.pop)
                    .userInfos(this.userInfos)
                    .influence(resultList)
                    .build();
        
        return valueObject;
    }

    
    //ABAIXO OS METODOS PARA PEGAR SOMENTE OS TWEETS DO USUÁRIO QUE FORAM RETUITADOS
    private void getRet() {
        
        // DO A SINGLE REQUEST DO TIMELINE
        Client cliente = ClientBuilder.newClient();

        WebTarget target = cliente.target("http://localhost:8080/ativ2-authagain/api/twitter/myretweets/")
//                .resolveTemplate("cursor", Long.toString(Long.MAX_VALUE))
                .queryParam("oauth_token", credentials.getOauth_token())
                .queryParam("oauth_verifier", credentials.getOauth_verifier());
        
        Response resposta = target
                .request()
                .get();
        String result =  resposta.readEntity(String.class);
        
        List<TwitterRetweetedStatus> tweets = parseRetweeteds(result);
        
        if (tweets.size() > 0)
            this.retweets.addAll(tweets);
        
        //now request the tweets olders then last
//        int i = 1;
//        TwitterRetweetedStatus lastMaxTweet;
//        
//        do {
//                
//            lastMaxTweet = tweets.get(tweets.size() - 1);
//            System.out.println("RETWEETS: " + i++ + " - "+ lastMaxTweet);
//        
//            Client cliente2 = ClientBuilder.newClient();
//            WebTarget target2 = cliente2
//                    .target("http://localhost:8080/ativ2-authagain/api/twitter/myretweets/{max_id}")
//                    .resolveTemplate("max_id", lastMaxTweet.id_str())
//                    .queryParam("oauth_token", credentials.getOauth_token())
//                    .queryParam("oauth_verifier", credentials.getOauth_verifier());
//
//            Response resposta2 = target2
//                    .request()
//                    .get();
//            String result2 = resposta2.readEntity(String.class);
//
//            tweets = parseRetweeteds(result2);
//            
//            if (tweets.size() > 0) {
//                this.retweets.addAll(tweets);    
//            } 
//            System.out.println(lastMaxTweet);
//            
//        } while (!lastMaxTweet.id_str().equals(status.get(status.size() - 1).id_str()) && i < 2); //com limite

//        } while (i < 5); //com limite
        
        System.out.println("TOTAL RETWEETS COLETADOS: " + retweets.size());
        this.retweets.forEach(System.out::println);
    }
    
    private List<TwitterRetweetedStatus> parseRetweeteds(String json) {
        try {
            ObjectMapper objectMapper
                    = new ObjectMapper()
                            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                                    false);
            List<TwitterRetweetedStatus> tweets
                    = objectMapper.readValue(json,
                            new TypeReference<List<TwitterRetweetedStatus>>() {
                    });

            return tweets;
        } catch (IOException ex) {
            Logger.getLogger(TwitterServiceClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>();
    }
    
    
}
