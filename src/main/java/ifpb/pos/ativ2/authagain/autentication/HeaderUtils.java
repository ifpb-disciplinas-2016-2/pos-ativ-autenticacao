/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.pos.ativ2.authagain.autentication;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author natarajan
 */
public class HeaderUtils {
    
    public static String headerUpdate(String method, String url, Credentials credentials) throws Exception {
        List<Pair> urlParams = new ArrayList<>();
        return new AuthenticationHeaderTwitter(credentials)
                .header(urlParams, method, url);
    }
    
    
    public static String headerStatusToUpdate(String status, String method, String url, Credentials credentials) throws Exception {
        List<Pair> urlParams = new ArrayList<>();
        urlParams.add(Pair.create("status", status));
        return new AuthenticationHeaderTwitter(credentials)
                .header(urlParams, method, url);

    }

    public static String headerWithOtherPairs(Map<String, String> pairs, String method, String url, Credentials credentials) throws Exception {
        List<Pair> urlParams = new ArrayList<>();
        pairs.entrySet().forEach((entries) -> {
            urlParams.add(Pair.create(entries.getKey(), entries.getValue()));
            System.out.println("entries = " + entries.getKey());
        });
//        urlParams.sort((p1, p2) -> p1.key().compareTo(p2.key()));
//        urlParams.forEach(a->System.out.println(a.key()));
        String result = new AuthenticationHeaderTwitter(credentials)
                .header(urlParams, method, url);
        System.out.println("Headers others: " + result);
        return result;
    }
    
}
