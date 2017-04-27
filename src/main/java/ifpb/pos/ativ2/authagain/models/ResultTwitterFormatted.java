package ifpb.pos.ativ2.authagain.models;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import ifpb.pos.ativ2.authagain.models.ImmutableResultTwitter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author natarajan
 */
public class ResultTwitterFormatted {
    
    private static DecimalFormat decimalFormat = new DecimalFormat("###.##");
    
    private TwitterUserInfo user;
    private List<Influence> influences;
    private double pop;

    public ResultTwitterFormatted() {
        
    }
    
    public ResultTwitterFormatted(ImmutableResultTwitter immutableResultTwitter) {
        this.user = immutableResultTwitter.userInfos();
        this.pop = immutableResultTwitter.pop();
        this.influences = new ArrayList<>();
       
        for (InfluenceIm i : immutableResultTwitter.influence()) {
            
            Influence inf = new Influence(i);
            System.out.println(inf);
            influences.add(inf);
        }
//        this.influences = Arrays.asList( (InfluenceIm[]) immutableResultTwitter.influence() );
    }

    public TwitterUserInfo getUser() {
        return user;
    }

    public List<Influence> getInfluences() {
        return influences;
    }

    public String getPop() {
        return decimalFormat.format(this.pop) + " %";
    }
    
    
    
    
    
    
}
