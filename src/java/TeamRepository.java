package com.example.demo;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

import com.example.data.Team;

public interface TeamRepository extends CrudRepository<Team, Integer>   
{
    @Query("select t from Team as t order by t.numVitorias ASC")
    public List<Team> sortByWinsCres();

    @Query("select t from Team as t order by t.numVitorias DESC")
    public List<Team> sortByWinsDecres();

    @Query("select t from Team as t order by t.numJogos ASC")
    public List<Team> sortByGamesCres();

    @Query("select t from Team as t order by t.numJogos DESC")
    public List<Team> sortByGamesDecres();

    @Query("select t from Team as t order by t.numDerrotas ASC")
    public List<Team> sortByDerrotasCres();

    @Query("select t from Team as t order by t.numDerrotas DESC")
    public List<Team> sortByDerrotasDecres();

    @Query("select t from Team as t order by t.numEmpates ASC")
    public List<Team> sortByEmpatesCres();

    @Query("select t from Team as t order by t.numEmpates DESC")
    public List<Team> sortByEmpatesDecres();
} 