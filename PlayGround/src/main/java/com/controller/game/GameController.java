package com.controller.game;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.GameDTO;
import com.dto.MemberDTO;
import com.service.GameService;

@RestController
@RequestMapping("/game")
public class GameController {
	@Autowired
	GameService gService;

	
	@GetMapping("/gameCategoy/{gameCategory}")
	public List<GameDTO> gameMainTopList(@PathVariable String gameCategory,
										HttpSession session) {
		MemberDTO login = (MemberDTO) session.getAttribute("login");
		List<GameDTO> gameList = null;
		if (gameCategory == null || gameCategory.contentEquals("new"))
			gameList = gService.newGameListSelect();
		else if (login == null && gameCategory.contentEquals("recommend"))
			gameList = gService.recommendGameListSelect(11);
		else if (login != null && gameCategory.contentEquals("recommend"))
			gameList = gService.recommendUserTagListSelect(login.getMbrId());
		return gameList;
	}
	
	
	@GetMapping("/tag/{tags}")
	public List<GameDTO> gameMainMidList(@PathVariable String tags) {
		System.out.println(tags);
		List<GameDTO> gameList = null;
		if (tags == null)
			gameList = gService.recommendGameListSelect(6);
		else {
			String[] tag_array = tags.split(",");
			List<Integer> listTags = new ArrayList<Integer>();
			for (String t : tag_array) 
				listTags.add(Integer.parseInt(t));
			gameList = gService.tagGameListSelect(listTags);
		}
		return gameList;
	}
}
