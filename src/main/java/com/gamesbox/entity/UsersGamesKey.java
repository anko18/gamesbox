package com.gamesbox.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
 public class UsersGamesKey implements Serializable {
    private int userid;
    private String gameid;


    public UsersGamesKey(int userId, String gameId) {
        this.userid = userid;
        this.gameid = gameid;
    }

    public UsersGamesKey() {

    }
}
