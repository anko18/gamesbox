package com.gamesbox.service;

import com.api.igdb.apicalypse.APICalypse;
import com.api.igdb.exceptions.RequestException;
import com.api.igdb.request.ProtoRequestKt;
import com.gamesbox.dto.GameDTO;
import com.gamesbox.entity.Game;
import com.gamesbox.entity.User;
import com.gamesbox.igdbapi.IGDB;
import com.gamesbox.repository.GamesRepository;
import com.gamesbox.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class GBService {

    private IGDB igdb;
    private UserRepository userRepository;
    private GamesRepository gamesRepository;

    @Autowired
    public GBService(IGDB igdb, UserRepository userRepository, GamesRepository gamesRepository){
        this.igdb = igdb;
        this.userRepository = userRepository;
        this.gamesRepository = gamesRepository;
    }

    public String addGame(String slug, String username) {
        if(igdb.getToken().getExpires_in() < Instant.now().getEpochSecond()) {
            this.igdb.renewToken();
        }
        User user = userRepository.findUserByUserName(username);
        Game checkGame = gamesRepository.findGameByGameidAndUserid(slug, user.getId());
        if(checkGame != null) {
            return "This game is already added!";
        }

        APICalypse apicalypse = new APICalypse().fields("*").where("slug = \""+ slug+"\"");

        try{
            List<proto.Game> searchResult = ProtoRequestKt.games(igdb.getWrapper(), apicalypse);
            Game newGame = new Game(user.getId(), searchResult.get(0).getSlug());
            gamesRepository.save(newGame);
            user.setGames_count(user.getGames_count()+1);
            userRepository.save(user);
            return "Game " + searchResult.get(0).getName() + " was added";

        } catch(RequestException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public List<GameDTO> getGamesbyName(String name) {
        if(igdb.getToken().getExpires_in() < Instant.now().getEpochSecond()) {
            this.igdb.renewToken();
        }

        APICalypse apicalypse = new APICalypse().search(name).fields("*");
        try{
            List<proto.Game> searchResult = ProtoRequestKt.games(igdb.getWrapper(), apicalypse);
            List<GameDTO> gameDTOList = new ArrayList<>();
            for(proto.Game s : searchResult) {
                GameDTO gameDTO = new GameDTO();
                gameDTO.setName(s.getName());
                gameDTO.setSlug(s.getSlug());
                gameDTOList.add(gameDTO);
            }
            return gameDTOList;

        } catch(RequestException e) {
            System.out.println();
            System.out.println("Error!");
            System.out.println();
            return null;
        }
    }

    public List<String> getUserGames(String username, int offset) {
            User user = userRepository.findUserByUserName(username);
            List<String> results = new ArrayList<>();
            List<Game> gamesIds = gamesRepository.findCustomResultsByUserId(user.getId(), offset);
            for(Game g : gamesIds) {
                if(igdb.getToken().getExpires_in() < Instant.now().getEpochSecond()) {
                    this.igdb.renewToken();
                }
                APICalypse apicalypse = new APICalypse().fields("*").where("slug = \""+ g.getGame_id()+"\"");
                try{
                    List<proto.Game> searchResult = ProtoRequestKt.games(igdb.getWrapper(), apicalypse);
                    results.add(searchResult.get(0).getName());

                } catch(RequestException e) {
                    return null;
                }
            }
            return results;
        }

    public int countPages(String username) {
        User user = userRepository.findUserByUserName(username);
        int pages = user.getGames_count()/20;
        if(user.getGames_count() % 20 != 0) {
            pages++;
        }
        return pages;
    }


}
