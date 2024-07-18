package com.gamesbox.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name="users_games")
@IdClass(UsersGamesKey.class)
public class Game {
    @Id
    private int userid;
    @Id
    private String gameid;

    public Game(int userId, String gameId) {
        this.userid = userId;
        this.gameid = gameId;
    }

    public Game() {

    }

    public int getUser_id() {
        return userid;
    }

    public void setUser_id(int userId) {
        this.userid = userId;
    }

    public String getGame_id() {
        return gameid;
    }

    public void setGame_id(String gameId) {
        this.gameid = gameId;
    }
}
