package com.example.demo;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import java.util.ArrayList;    
import org.springframework.beans.factory.annotation.Autowired;    
import org.springframework.stereotype.Service;

import com.example.data.Team;

@Service    
public class TeamService   
{    
    @Autowired    
    private TeamRepository teamRepository;

    public List<Team> getAllTeams()  
    {    
        List<Team>userRecords = new ArrayList<>();    
        teamRepository.findAll().forEach(userRecords::add);    
        return userRecords;    
    }

    public void addTeam(Team teams)  
    {
        System.out.println(teams);
        teamRepository.save(teams);    
    }

    public Optional<Team> getTeam(int id) {
        return teamRepository.findById(id);
    }
    public List<Team> sortByWinsCres(){
        return teamRepository.sortByWinsCres();
    }
    public List<Team> sortByWinsDecres(){
        return teamRepository.sortByWinsDecres();
    }

    public List<Team> sortByGameCres(){
        return teamRepository.sortByGamesCres();
    }
    public List<Team> sortByGamesDecres(){
        return teamRepository.sortByGamesDecres();
    }

    public List<Team> sortByDerrotasCres(){
        return teamRepository.sortByDerrotasCres();
    }
    public List<Team> sortByDerrotasDecres(){
        return teamRepository.sortByDerrotasDecres();
    }

    public List<Team> sortByEmpatesCres(){
        return teamRepository.sortByEmpatesCres();
    }
    public List<Team> sortByEmpatesDecres(){
        return teamRepository.sortByEmpatesDecres();
    }




    

}    