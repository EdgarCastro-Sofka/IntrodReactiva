package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.demo.CsvUtilFile.getPlayers;

@RestController
public class Controller {

    @GetMapping("/playersByClubAndAge")
    private Flux<List<Player>> playersByClubAndAge(@RequestParam String clubParam) {
        List<Player> playerList = getPlayers();
        List<Player> playersByClub = new ArrayList<>();
        return Flux.fromIterable(playerList).filter(data -> data.age > 34).filter(data -> data.club.equals(clubParam)).map(dataFilter -> {
            playersByClub.add(dataFilter);
            return playersByClub;
        });
    }

    @GetMapping("/numberPlayersByCountry")
    private Mono<Object> numberPlayersByCountry() {
        List<Player> playerList = getPlayers();
   return Flux.fromIterable(playerList)
           .collect(Collectors.groupingByConcurrent(s-> s.national, Collectors.counting()));


    }

}

