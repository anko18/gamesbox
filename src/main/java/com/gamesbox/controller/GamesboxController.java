package com.gamesbox.controller;

import com.api.igdb.exceptions.RequestException;
import com.gamesbox.dto.GameDTO;
import com.gamesbox.service.GBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class GamesboxController {
    private GBService service;

    @Autowired
    public GamesboxController(GBService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String index() {
        return "login";
    }

    @GetMapping("/yourlist")
    public String getGames(Model model, Principal principal, @RequestParam("page") int page) {
        List<String> games = service.getUserGames(principal.getName(), (page-1)*20);

        if(games.isEmpty()) {
            model.addAttribute("emptyList", "You haven't added any games yet ;)");
            return "list";
        }
        else {
            model.addAttribute("games", games);
            model.addAttribute("pagesize", 20);
            model.addAttribute("pages", service.countPages(principal.getName()));
            return "list";
        }

    }

    @GetMapping ("/findgame")
    public String showFormForName() throws RequestException {
        return "find";
    }

    @GetMapping("/listGamesByName")
    public String findGamesByName(Model model, @RequestParam("name") String name) {
        List<GameDTO> gameDTOList = service.getGamesbyName(name);
        if(gameDTOList == null) {
            model.addAttribute("emptyList", "There are no games with this name. Try again!");
            return "addgame";
        } else {
            model.addAttribute("list", gameDTOList);
            return "addgame";
        }
    }

    @PostMapping("/addgame")
    public String addGame(@RequestParam("game_id") String slug, Principal principal, Model model) {
        String result = service.addGame(slug, principal.getName());
        model.addAttribute("result", result);
        return "find";
    }

}
