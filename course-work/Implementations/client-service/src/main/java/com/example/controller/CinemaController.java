package com.example.controller;

import com.example.dto.ActorRequestDto;
import com.example.dto.ActorResponseDto;
import com.example.dto.MovieRequestDto;
import com.example.dto.MovieResponseDto;
import com.example.dto.TVSeriesRequestDto;
import com.example.dto.TVSeriesResponseDto;
import com.example.service.CinemaClientService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RestController
@RequestMapping("/cinema")
@CrossOrigin(origins = "*")
public class CinemaController {

    private final CinemaClientService cinemaClientService;

    @GetMapping("/actor/{id}")
    public ActorResponseDto getActor(@PathVariable int id) {
        return cinemaClientService.getActor(id);
    }

    @PostMapping("/actor")
    public String createActor(
        @RequestBody ActorRequestDto actorRequestDto) {
        return cinemaClientService.createActor(actorRequestDto.getName(),
            actorRequestDto.getNationality(),
            actorRequestDto.getAlive(),
            actorRequestDto.getRating(),
            actorRequestDto.getBirthDate(),
            actorRequestDto.getAwards());
    }

    @PutMapping("/actor/{id}")
    public ActorResponseDto updateActor(@PathVariable int id,
        @RequestBody ActorRequestDto actorRequestDto) {
        return cinemaClientService.updateActor(id, actorRequestDto.getName(),
            actorRequestDto.getNationality(),
            actorRequestDto.getAlive(),
            actorRequestDto.getRating(),
            actorRequestDto.getBirthDate(),
            actorRequestDto.getAwards());
    }

    @DeleteMapping("/actor/{id}")
    public String deleteActor(@PathVariable int id) {
        return cinemaClientService.deleteActor(id);
    }

    @GetMapping("/movie/{id}")
    public MovieResponseDto getMovie(@PathVariable int id) {
        return cinemaClientService.getMovie(id);
    }

    @PostMapping("/movie")
    public String createMovie(
        @RequestBody MovieRequestDto request) {
        return cinemaClientService.createMovie(request.getTitle(), request.getReleaseYear(), request.getRating(), request.getGenre(), request.getDurationMinutes());
    }

    @PutMapping("/movie/{id}")
    public MovieResponseDto updateMovie(@PathVariable int id, @RequestBody MovieRequestDto request) {
        return cinemaClientService.updateMovie(id, request.getTitle(), request.getReleaseYear(), request.getRating(), request.getGenre(), request.getDurationMinutes());
    }

    @DeleteMapping("/movie/{id}")
    public String deleteMovie(@PathVariable int id) {
        return cinemaClientService.deleteMovie(id);
    }

    @GetMapping("/tvseries/{id}")
    public TVSeriesResponseDto getTVSeries(@PathVariable int id) {
        return cinemaClientService.getTVSeries(id);
    }

    @PostMapping("/tvseries")
    public TVSeriesResponseDto createTVSeries(
        @RequestBody TVSeriesRequestDto request) {
        return cinemaClientService.createTVSeries(request.getTitle(), request.getGenre(), request.getRating(), request.getOngoing(), request.getSeasons());
    }

    @PutMapping("/tvseries/{id}")
    public TVSeriesResponseDto updateTVSeries(@PathVariable int id,
        @RequestBody TVSeriesRequestDto request) {
        return cinemaClientService.updateTVSeries(id, request.getTitle(), request.getGenre(), request.getRating(), request.getOngoing(), request.getSeasons());
    }

    @DeleteMapping("/tvseries/{id}")
    public String deleteTVSeries(@PathVariable int id) {
        return cinemaClientService.deleteTVSeries(id);
    }
}
