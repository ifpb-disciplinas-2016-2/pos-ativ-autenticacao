package ifpb.pos.ativ2.authagain.models;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.text.DecimalFormat;

/**
 *
 * @author natarajan
 */
public class Influence {
    
    private static DecimalFormat decimalFormat = new DecimalFormat("###.##");
    //    decimalFormat.setMaximumFractionDigits(2);
    
//        return decimalFormat.format(pop) + "%";
    
    private TwitterUser userW;
    
    //numero de tweets com menções a w
    private long mentionsUserW;
    
    //número de retweets que W fez de tweets do usuário da influencia
    private long retweetedsByUserW;

    private double influence;

    public Influence() {
    }
    
    public Influence(TwitterUser user) {
        this.userW = user;
    }
    
    public Influence(InfluenceIm im) {
        this.influence = im.influence();
        this.userW = im.userW();
    }

    public long getIdUserW() {
        return userW.id();
    }

    public void addMention(){
        this.mentionsUserW++;
        setInfluence();
    }
    
    public void addRetweeteds(){
        this.retweetedsByUserW++;
        setInfluence();
    }

    public double getInfluence() {
        setInfluence();
        return influence;
    }

    public TwitterUser getUserW() {
        return userW;
    }
    
    public void setInfluence() {
        
        double infl = ((double) 1 ) / (this.mentionsUserW + this.retweetedsByUserW + 2) ;
        
        this.influence = (double) 1 - infl;
    }
    
    public String getFormattedInfluence(){
        return decimalFormat.format(this.influence * 100) + " %";
    }

    @Override
    public String toString() {
        return "Influence{" + "userW=" + userW + ", mentionsUserW=" + mentionsUserW + ", retweetedsByUserW=" + retweetedsByUserW + ", influence=" + influence + '}';
    }
    
}
