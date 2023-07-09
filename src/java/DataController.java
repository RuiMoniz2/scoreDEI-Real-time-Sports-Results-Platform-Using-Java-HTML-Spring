package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.data.Team;
import com.example.data.UserWeb;
import com.example.data.Game;
import com.example.data.Player;
import com.example.data.Event;
import com.example.formdata.FormData;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;





@Controller
public class DataController {
    private final static Logger logger = LoggerFactory.getLogger(DataController.class);
    @Autowired
    TeamService teamService;

    @Autowired
    PlayerService playerService;

    @Autowired
    GameService gameService;

    @Autowired
    UserWebService userService;

    @Autowired
    EventService eventService;

    @GetMapping("/")
    public String redirect() {
        return "redirect:/home";
    }


    @GetMapping("/home")
    public String home() {
        return "home";
    }



    @GetMapping("/createData")
    public String createData() {
        return "createData";
    }

	@PostMapping("/saveData")
    public String saveData(Model model) {
        Team[] myteams = { 
            new Team("Porto", 20,12,12,2), 
            new Team("Benfica", 30,4,3,2), 
            new Team("Sporting", 45,34,21,2)
        };
        Player[] myplayers = { 
            new Player("Paula", 21,"ST",0),
            new Player("Artur", 21,"RB",2),
            new Player("Rui",  19,"CF",4),
            new Player("Luísa", 20,"CM",3),
            new Player("Alexandra", 21,"LW",2),
            new Player("Carlos",  22,"GK",1)
        };
        Game[] mygames = { 
            new Game("Porto-Benfica", "14/08/2021","Portugal",0,0), 
            new Game("Benfica-Sporting", "30/09/2020","Açores",0,0), 
            new Game("Sporting-Porto", "5/05/2022","Espanha",0,0)
        };

        Event[] myevents = { 
            new Event("GOLO", "90"), 
            new Event("Vermelho", "70"), 
            new Event("Amarelo", "8")
        };



        List<Team> teamsGame1=new ArrayList<Team>();
        List<Team> teamsGame2=new ArrayList<Team>();
        List<Team> teamsGame3=new ArrayList<Team>();
        teamsGame1.add(myteams[0]);
        teamsGame1.add(myteams[1]);

        teamsGame2.add(myteams[1]);
        teamsGame2.add(myteams[2]);

        teamsGame3.add(myteams[2]);
        teamsGame3.add(myteams[0]);

        mygames[0].setTeams(teamsGame1);
        mygames[1].setTeams(teamsGame2);
        mygames[2].setTeams(teamsGame3);



        myevents[0].setGame(mygames[0]);
        myevents[1].setGame(mygames[0]);

        List<Event> events=new ArrayList<Event>();
        events.add(myevents[0]);
        events.add(myevents[1]);
        mygames[0].setEvents(events);

        myplayers[0].setTeam(myteams[0]);
        myplayers[1].setTeam(myteams[1]);
        myplayers[2].setTeam(myteams[1]);
        myplayers[3].setTeam(myteams[2]);
        myplayers[4].setTeam(myteams[0]);
        myplayers[5].setTeam(myteams[2]);
        
        UserWeb uw = new UserWeb("admin","admin","admin@gmail.com","989898989","y");
        this.userService.addUser(uw);

        for (Game s : mygames)
            this.gameService.addGame(s);

        for (Player s : myplayers)
            this.playerService.addPlayer(s);
    
		return "redirect:/home";
	}

    @GetMapping("/listPlayers")
    public String listPlayers(Model model) {
        model.addAttribute("players", this.playerService.getAllPlayers());
        return "listPlayers";
    }

    @GetMapping("/createPlayer")
    public String createPlayer(@ModelAttribute Player player,Model m) {
        m.addAttribute("player", player);
        //m.addAttribute("allTeams", this.teamService.getAllTeams());
        return "createPlayer";
    }

    @GetMapping("/bestScorer")
    public String bestScorer(Model model) {
        List<Player> op= this.playerService.getAllPlayers();

        Player aux=new Player();
        int maxGoals=0;
        for (int i=0;i<op.size();i++){
            if(op.get(i).getGoals()>maxGoals){
                maxGoals=op.get(i).getGoals();
                aux=op.get(i);
            }

        }
        model.addAttribute("player",aux);
        return "bestScorer";
    }

    @GetMapping("/editPlayer")
    public String editPlayer(@RequestParam(name="id", required=true) int id, Model m) {
        Optional<Player> op = this.playerService.getPlayer(id);
        if (op.isPresent()) {
            m.addAttribute("player", op.get());
            m.addAttribute("allTeams", this.teamService.getAllTeams());
            return "editPlayer";
        }
        else {
            return "editPlayer";
        }
    }    

    @PostMapping("/savePlayer")
    public String savePlayer(@ModelAttribute Player st) {
        this.playerService.addPlayer(st);
        return "redirect:/listPlayers";
    }

    @GetMapping("/listTeams")
    public String listTeams(Model model) {
        model.addAttribute("teams", this.teamService.getAllTeams());
        return "listTeams";
    }

    @GetMapping("/listTeams2")
    public String listTeams2(Model model) {
        model.addAttribute("teams", this.teamService.getAllTeams());


        return "listTeams2";
    }

    @GetMapping("/listTeams3")
    public String listTeams3(Model model, @RequestParam(name = "field", required = false) String field,
    @RequestParam(name = "order", required = false, defaultValue = "desc") String order) {
        //model.addAttribute("teams", this.teamService.getAllTeams());

        List<Player> op= this.playerService.getAllPlayers();

        Player aux=new Player();
        int maxGoals=0;
        for (int i=0;i<op.size();i++){
            if(op.get(i).getGoals()>maxGoals){
                maxGoals=op.get(i).getGoals();
                aux=op.get(i);
            }

        }

        model.addAttribute("player",aux);

        if (field == null) {
            model.addAttribute("teams", this.teamService.getAllTeams());
            return "listTeams3";
        }

        order = order.equals("desc") ? "asc" : "desc";
        model.addAttribute("field", field);
        model.addAttribute("order", order);

        
        if (order.equals("asc") && field.equals("numVitorias"))
            model.addAttribute("teams", this.teamService.sortByWinsCres());
        
        else if (order.equals("desc") && field.equals("numVitorias"))
            model.addAttribute("teams", this.teamService.sortByWinsDecres());

        else if (order.equals("asc") && field.equals("numJogos"))
            model.addAttribute("teams", this.teamService.sortByGameCres());

        else if (order.equals("desc") && field.equals("numJogos"))
            model.addAttribute("teams", this.teamService.sortByGamesDecres());

        else if (order.equals("asc") && field.equals("numDerrotas"))
            model.addAttribute("teams", this.teamService.sortByDerrotasCres());

        else if (order.equals("desc") && field.equals("numDerrotas"))
            model.addAttribute("teams", this.teamService.sortByDerrotasDecres());

        else if (order.equals("asc") && field.equals("numEmpates"))
            model.addAttribute("teams", this.teamService.sortByEmpatesCres());

        else if (order.equals("desc") && field.equals("numEmpates"))
            model.addAttribute("teams", this.teamService.sortByEmpatesDecres());
       
            return "listTeams3";
    }

    @GetMapping("/createTeam")
    public String createTeam(@ModelAttribute Team team,Model m) {
        m.addAttribute("teams", team);
        return "createTeam";
    }

      

    @PostMapping("/saveTeam")
    public String saveTeam(@ModelAttribute Team team) {
        this.teamService.addTeam(team);
        return "redirect:/listTeams";
    }

    @GetMapping("/editTeam")
    public String editTeam(@RequestParam(name="id", required=true) int id, Model m) {
        Optional<Team> op = this.teamService.getTeam(id);
        if (op.isPresent()) {
            m.addAttribute("teams", op.get());
            m.addAttribute("allTeams", this.teamService.getAllTeams());
            return "editTeams";
        }
        else {
            return "redirect:/listTeams";
        }
    }
    
    
    @GetMapping("/listUsers")
    public String listUsers(Model model) {
        model.addAttribute("user", this.userService.getAllUsers());
        return "listUsers";
    }

    @GetMapping("/userLogin")
    public String userLogin(Model model) {
        model.addAttribute("user",new UserWeb());
        return "login";
    }

    @GetMapping("/login")
    public String login(@ModelAttribute UserWeb user, Model model) {
        model.addAttribute("user",new UserWeb());
        List<UserWeb> u1 = this.userService.findByUsername(user.getUsername());
        
        if(u1.size()==1){
            if(u1.get(0).getPassword().equals(user.getPassword()) && u1.get(0).getRole().toLowerCase().equals("y")){
                return "redirect:/admin";
            }
            if(u1.get(0).getPassword().equals(user.getPassword()) && u1.get(0).getRole().toLowerCase().equals("n")){
                return "redirect:/user";
            }
            return "redirect:/userLogin";
        }
        return "userLogin";
    }

    @GetMapping("/userRegist")
    public String userRegist(Model m) {
        m.addAttribute("user", new UserWeb());
        return "regist";
    }

    @GetMapping("/regist")
    public String regist(@ModelAttribute UserWeb user, Model model) {
        model.addAttribute("user",new UserWeb());
        
        
        return "redirect:/userRegist";
        
       
    }
    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute UserWeb st) {
        this.userService.addUser(st);
        logger.info(st.getTelephone());
        return "redirect:/userLogin";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("user",new UserWeb());
        return "admin";
    }

    @GetMapping("/user")
    public String user(Model model) {
        model.addAttribute("user",new UserWeb());
        return "user";
    }

    @GetMapping("/userReload")
    public String userReload(Model model) {
        model.addAttribute("user",new UserWeb());
        return "redirect:/user";
    }

    @PostMapping("/saveEvent")
    public String saveEvent(@ModelAttribute Event event,int id) {
        Optional<Game> op = this.gameService.getGame(id);
        System.out.println(op.get().getName());
        event.setGame(op.get());
        this.eventService.addEvent(event);
        return "redirect:/listGames";
    }

    @PostMapping("/saveEvent2")
    public String saveEvent2(@ModelAttribute Event event,int id) {
        Optional<Game> op = this.gameService.getGame(id);
        System.out.println(op.get().getName());
        event.setGame(op.get());
        this.eventService.addEvent(event);
        return "redirect:/listGames3";
    }

    @GetMapping("/addEvent")
    public String addEvent(@RequestParam(name="id", required=true) int id,@ModelAttribute Event event, Model m) {
        Optional<Game> op = this.gameService.getGame(id);

        //Event test= new Event();
        if (op.isPresent()) {
            //Game aux =op.get();
 
            //System.out.println(game.getName());
            m.addAttribute("event", event);

            //m.addAttribute("game", op.get());
            //event.setGame(aux);
            //System.out.println(event.getGame().getName());

            // m.addAttribute("newEvent", test);
            return "addEvent";
        }
        else {
            return "redirect:/listGames";
        }

    }

    @GetMapping("/addEvent2")
    public String addEvent2(@RequestParam(name="id", required=true) int id,@ModelAttribute Event event, Model m) {
        Optional<Game> op = this.gameService.getGame(id);

        //Event test= new Event();
        if (op.isPresent()) {
            //Game aux =op.get();
 
            //System.out.println(game.getName());
            m.addAttribute("event", event);

            //m.addAttribute("game", op.get());
            //event.setGame(aux);
            //System.out.println(event.getGame().getName());

            // m.addAttribute("newEvent", test);
            return "addEvent2";
        }
        else {
            return "redirect:/listGames3";
        }

    }

    @GetMapping("/showGame")
    public String showGame(@RequestParam(name="id", required=true) int id, Model m) {
        Optional<Game> op = this.gameService.getGame(id);
        if (op.isPresent()) {
            m.addAttribute("game", op.get());
            System.out.println(op.get().getEvents());
            m.addAttribute("events", op.get().getEvents());

            return "showGame";
        }
        else {
            return "redirect:/listGames";
        }
    }

    @GetMapping("/listGames2")
    public String listGames2(Model model) {
        model.addAttribute("games", this.gameService.getAllGames());
        return "listGames2";
    }

    @GetMapping("/showGame2")
    public String showGame2(@RequestParam(name="id", required=true) int id, Model m) {
        Optional<Game> op = this.gameService.getGame(id);
        if (op.isPresent()) {
            m.addAttribute("game", op.get());
            System.out.println(op.get().getEvents());
            m.addAttribute("events", op.get().getEvents());

            return "showGame2";
        }
        else {
            return "redirect:/listGames2";
        }
    }

    @GetMapping("/listGames3")
    public String listGames3(Model model) {
        model.addAttribute("games", this.gameService.getAllGames());
        return "listGames3";
    }

    @GetMapping("/showGame3")
    public String showGame3(@RequestParam(name="id", required=true) int id, Model m) {
        Optional<Game> op = this.gameService.getGame(id);
        if (op.isPresent()) {
            m.addAttribute("game", op.get());
            System.out.println(op.get().getEvents());
            m.addAttribute("events", op.get().getEvents());

            return "showGame3";
        }
        else {
            return "redirect:/listGames3";
        }
    }

    @GetMapping("/listGames")
    public String listGames(Model model) {
        model.addAttribute("games", this.gameService.getAllGames());
        return "listGames";
    }
    

    @GetMapping("/editGame")
    public String editGame(@RequestParam(name="id", required=true) int id, Model m) {
        Optional<Game> op = this.gameService.getGame(id);
        if (op.isPresent()) {
            m.addAttribute("game", op.get());
            //m.addAttribute("allGames", this.gameService.getAllGames());
            return "editGame";
        }
        else {
            return "redirect:/listGames";
        }
    }


    @GetMapping("/createGame")
    public String createGame(@ModelAttribute Game game,Model m) {
        m.addAttribute("game", game);
        //m.addAttribute("allTeams", this.teamService.getAllTeams());
        return "createGame";
    }
    @PostMapping("/saveGame")
    public String saveGame(@ModelAttribute Game st) {
        this.gameService.addGame(st);
        return "redirect:/listGames";
    }
    
    
    


    
  

}