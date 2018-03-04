package com.example.yavor.pingpongcounter;

/**
 * Created by yavor on 3/1/18.
 */

public class Player {

    private int points = 0;
    private int games = 0;
    private String name = "";
    private boolean serve = false;


    public Player (String name){
        this.name = name;
    }

    public Player(){
    }

    public void setName(String name){
        this.name=name;
    }

    public void addPoint(){
        points+=1;

    }

    public void subPoint(){
        if(points>0)
        points-=1;
        else points=0;
    }

    public void addGame(){
        games+=1;
    }

    public void setServe(boolean serve){
        this.serve=serve;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setGames(int games) {
        this.games = games;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public int getGames() {
        return games;
    }

    public boolean getServe(){
        return serve;
    }

    public void resetPoints(){
        points=0;
    }

    public void resetGames(){
        games=0;
    }
}
