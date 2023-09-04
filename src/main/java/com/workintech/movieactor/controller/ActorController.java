package com.workintech.movieactor.controller;

import com.workintech.movieactor.dto.ActorResponse;
import com.workintech.movieactor.dto.MovieActorRequest;
import com.workintech.movieactor.dto.MovieActorResponse;
import com.workintech.movieactor.dto.MovieResponse;
import com.workintech.movieactor.entity.Actor;
import com.workintech.movieactor.entity.Movie;
import com.workintech.movieactor.service.ActorService;
import com.workintech.movieactor.service.MovieService;
import com.workintech.movieactor.util.HollywoodUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/actor")
public class ActorController {


    private ActorService actorService;
    private MovieService movieService;
@Autowired
    public ActorController(ActorService actorService, MovieService movieService) {
        this.actorService = actorService;
        this.movieService = movieService;
    }

    @GetMapping("/")
    public List<ActorResponse> findAll() {
        List<ActorResponse> actorResponses = new ArrayList<>();
        List<Actor> actors = actorService.findAll();
        for (Actor actor : actors) {
            actorResponses.add(new ActorResponse(actor.getId(), actor.getFirstName(), actor.getLastName(), actor.getBirthDate()));
        }
        return actorResponses;
    }

    @GetMapping("/{id}")
    public ActorResponse findById(@PathVariable int id) {
        Actor foundActor = actorService.findById(id);
        return new ActorResponse(foundActor.getId(), foundActor.getFirstName(), foundActor.getLastName(),
                foundActor.getBirthDate());

    }

    @PostMapping("/")
    public MovieActorResponse save(@RequestBody MovieActorRequest movieActorRequest) {
        Movie movie = movieActorRequest.getMovie();
        Actor actor = movieActorRequest.getActor();
        movie.addActor(actor);
        Movie savedMovie = movieService.save(movie);
        return HollywoodUtility.convertMovieActorResponse(savedMovie, actor);
    }

    @PostMapping("/addActor/{movieId}")
    public List<ActorResponse> addActor(@RequestBody List<Actor> actors, @PathVariable int movieId) {
        Movie movie = movieService.findById(movieId);
        movie.addAllActor(actors);
        Movie savedMovie = movieService.save(movie);
        return HollywoodUtility.convertActorResponses(savedMovie);
    }


    @PutMapping("/{id}")
    public ActorResponse update(@RequestBody Actor actor, @PathVariable int id) {
        Actor foundActor = actorService.findById(id);
        actor.setId(id);
        actor.setMovies(foundActor.getMovies());
        Actor updatedActor = actorService.save(actor);
        return new ActorResponse(actor.getId(), actor.getFirstName(), actor.getLastName(), actor.getBirthDate());
    }

    @DeleteMapping("/{id}")
    public ActorResponse delete(@PathVariable int id) {
        Actor actor = actorService.delete(id);
        return new ActorResponse(actor.getId(), actor.getFirstName(), actor.getLastName(),
                actor.getBirthDate());
    }

}
