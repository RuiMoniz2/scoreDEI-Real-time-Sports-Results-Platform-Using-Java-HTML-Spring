package com.example.demo;

import java.util.List;
import java.util.Optional;

import com.example.data.Team;
import com.example.data.Player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("rest")
public class RESTcontroller {
    @Autowired
    TeamService teamService;

    @Autowired
    PlayerService playerService;

    @GetMapping(value = "teams", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Team> getTeams()
    {
        return teamService.getAllTeams();
    }

    @GetMapping(value = "players", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Player> getPlayers()
    {
        return playerService.getAllPlayers();
    }

    @GetMapping(value = "players/{id}", produces = {MediaType.APPLICATION_XML_VALUE})
    public Player getPlayer(@PathVariable("id") int id) {
        Optional<Player> op = playerService.getPlayer(id);
        if (op.isEmpty())
            return null;
        return op.get();
    }

    @GetMapping(value = "teams/{id}", produces = {MediaType.APPLICATION_XML_VALUE})
    public Team getTeam(@PathVariable("id") int id) {
        Optional<Team> op = teamService.getTeam(id);
        if (op.isEmpty())
            return null;
        return op.get();
    }

    @PostMapping(value = "teams", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void addTeam(@RequestBody Team p) {
        teamService.addTeam(p);
    }

    @PostMapping(value = "players", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void addPlayer(@RequestBody Player s) {
        playerService.addPlayer(s);
    }

    @PutMapping(value = "teams/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public void addTeam(@PathVariable("id") int id, @RequestBody Team p) {
        Optional<Team> op = teamService.getTeam(id);
        if (!op.isEmpty()) {
            Team p1 = op.get();
            p1.setName(p.getName());
           
            teamService.addTeam(p1);
        }
    }

    @PutMapping(value = "players/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public void addPlayer(@PathVariable("id") int id, @RequestBody Player s) {
        System.out.println("PUT called");
        Optional<Player> op = playerService.getPlayer(id);
        if (!op.isEmpty()) {
            Player s1 = op.get();
            s1.setName(s.getName());
            s1.setAge(s.getAge());
            
            playerService.addPlayer(op.get());
        }
    }
}
