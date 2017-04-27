package ifpb.pos.ativ2.authagain.models;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author natarajan
 */
public class Result {
    private TwitterUserInfo userInfos;
    private List<Influence> influence;
    private double pop;

    public Result() {
        this.influence = new ArrayList<>();
        this.pop = 0;
    }

    public Result(TwitterUserInfo userInfos, List<Influence> influence, double pop) {
        this.userInfos = userInfos;
        this.influence = influence;
        this.pop = pop;
    }

    public TwitterUserInfo getUserInfos() {
        return userInfos;
    }

    public void setUserInfos(TwitterUserInfo userInfos) {
        this.userInfos = userInfos;
    }

    public List<Influence> getInfluence() {
        return influence;
    }

    public void setInfluence(List<Influence> influence) {
        this.influence = influence;
    }

    public double getPop() {
        return pop;
    }

    public void setPop(double pop) {
        this.pop = pop;
    }
    
    

    
    
    
}
