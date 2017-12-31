package com.codecool.enterprise.overcomplicated.controller;

import com.codecool.enterprise.overcomplicated.model.Player;
import com.codecool.enterprise.overcomplicated.service.TictactoeGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@SessionAttributes({"player", "game"})
public class GameController {

	@Autowired
	private TictactoeGame tictactoeGame;


	@ModelAttribute("player")
	public Player getPlayer() {
		return new Player();
	}

	@ModelAttribute("game")
	public TictactoeGame getGame() {
		return new TictactoeGame();
	}

	@GetMapping(value = "/")
	public String welcomeView(@ModelAttribute Player player) {
		return "welcome";
	}

	@PostMapping(value = "/changeplayerusername")
	public String changPlayerUserName(@ModelAttribute Player player) {
		return "redirect:/game";
	}

	@GetMapping(value = "/game")
	public String gameView(@ModelAttribute("player") Player player, Model model) {
		List<Integer> moveList = tictactoeGame.getPlayerMoveList();
		List<Integer> computerMoves = tictactoeGame.getComputerMoveList();
		String winner = tictactoeGame.getWinner();
		model.addAttribute("winner", winner);
		model.addAttribute("moveList", moveList);
		model.addAttribute("computerMoves", computerMoves);
		return "game";
	}

	@GetMapping(value = "/game-move")
	public String gameMove(@ModelAttribute("player") Player player, @ModelAttribute("move") int move) {
		tictactoeGame.playerMove(move);
		tictactoeGame.gameState();
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:60003/ai?gameState=" + tictactoeGame.getGameState(), String.class);
			JsonParser parser = new JacksonJsonParser();
			int aiMove = (Integer) parser.parseMap(response.getBody()).get("move");
			tictactoeGame.computerMove(aiMove);
			return "redirect:/game";
		} catch (Exception e) {
			e.getMessage();
			return "redirect:/game";
		}
	}

	@ModelAttribute("funfact")
	public String funFact() {
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:60000/funfact", String.class);
			JsonParser jsonParser = new JacksonJsonParser();
			return (String) jsonParser.parseMap(response.getBody()).get("funfact");
		} catch (ResourceAccessException e) {
			e.getMessage();
			return "Error";
		}
	}

	@ModelAttribute("avatar_uri")
	public String getAvatarUri(@ModelAttribute Player player) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:60001/avatar?avatarString=" + player.getUserName(), String.class);
			JsonParser jsonParser = new JacksonJsonParser();
			return (String) jsonParser.parseMap(response.getBody()).get("uri");
		} catch (ResourceAccessException e) {
			e.getMessage();
			return "Error";
		}
	}

	@ModelAttribute("comic_uri")
	public String getComic() {
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:60002/comics", String.class);
			JsonParser jsonParser = new JacksonJsonParser();
			return (String) jsonParser.parseMap(response.getBody()).get("uri");
		} catch (Exception e) {
			e.getMessage();
			return "Error";
		}
	}
}
