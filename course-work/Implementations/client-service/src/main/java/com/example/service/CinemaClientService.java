package com.example.service;

import com.example.dto.ActorResponseDto;
import com.example.dto.MovieResponseDto;
import com.example.dto.TVSeriesResponseDto;
import com.example.utils.BearerToken;
import com.example.utils.JwtContextHolder;
import com.google.protobuf.Descriptors;
import com.grpc.CinemaProto;
import com.grpc.CinemaServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.MetadataUtils;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.*;
import io.grpc.Metadata;

@Service
public class CinemaClientService {

    @GrpcClient(value = "cinema")
    private CinemaServiceGrpc.CinemaServiceBlockingStub synchronousClient;

    @GrpcClient(value = "cinema")
    private CinemaServiceGrpc.CinemaServiceStub asynchronousClient;

    public ActorResponseDto getActor(int actorId) {
        CinemaProto.GetActorRequest request = CinemaProto.GetActorRequest.newBuilder().setId(actorId).build();
        CinemaProto.GetActorResponse response = synchronousClient.withCallCredentials(new BearerToken(JwtContextHolder.getJwtToken())).getActor(request);
        return new ActorResponseDto(
            response.getActor().getName(),
            response.getActor().getNationality(),
            response.getActor().getBirthDate(),
            response.getActor().getRating(),
            response.getActor().getAlive(),
            response.getActor().getAwardsList()
        );
    }

    public String createActor(String name, String nationality, boolean alive, double rating, String birthDate, List<String> awards) {
        CinemaProto.Actor actor = CinemaProto.Actor.newBuilder()
            .setName(name)
            .setNationality(nationality)
            .setBirthDate(birthDate)
            .setAlive(alive)
            .setRating(rating)
            .addAllAwards(awards)
            .build();
        CinemaProto.CreateActorRequest request = CinemaProto.CreateActorRequest.newBuilder()
            .setActor(actor)
            .build();
        CinemaProto.CreateActorResponse response = synchronousClient.withCallCredentials(new BearerToken(JwtContextHolder.getJwtToken())).createActor(request);
        return response.getMessage();
    }

    public ActorResponseDto updateActor(Integer id,String name, String nationality, boolean alive, double rating, String birthDate, List<String> awards) {
        CinemaProto.Actor actor = CinemaProto.Actor.newBuilder()
            .setId(id)
            .setName(name)
            .setNationality(nationality)
            .setBirthDate(birthDate)
            .setAlive(alive)
            .setRating(rating)
            .addAllAwards(awards)
            .build();
        CinemaProto.UpdateActorRequest request = CinemaProto.UpdateActorRequest.newBuilder()
            .setActor(actor)
            .build();
        CinemaProto.UpdateActorResponse response = synchronousClient.withCallCredentials(new BearerToken(JwtContextHolder.getJwtToken())).updateActor(request);
        return new ActorResponseDto(
            response.getActor().getName(),
            response.getActor().getNationality(),
            response.getActor().getBirthDate(),
            response.getActor().getRating(),
            response.getActor().getAlive(),
            response.getActor().getAwardsList()
        );
    }

    public String deleteActor(int actorId) {
        CinemaProto.DeleteActorRequest request = CinemaProto.DeleteActorRequest.newBuilder().setId(actorId).build();
        CinemaProto.DeleteActorResponse response = synchronousClient.withCallCredentials(new BearerToken(JwtContextHolder.getJwtToken())).deleteActor(request);
        return response.getMessage();
    }

    public String createMovie(String title, String releaseYear, double rating, String genre, float durationMinutes) {
        CinemaProto.Movie movie = CinemaProto.Movie.newBuilder()
            .setTitle(title)
            .setReleaseYear(releaseYear)
            .setRating(rating)
            .setGenre(CinemaProto.Genre.valueOf(genre))
            .setDurationMinutes(durationMinutes)
            .build();
        CinemaProto.CreateMovieRequest request = CinemaProto.CreateMovieRequest.newBuilder()
            .setMovie(movie)
            .build();
        CinemaProto.CreateMovieResponse response = synchronousClient.withCallCredentials(new BearerToken(JwtContextHolder.getJwtToken())).createMovie(request);
        return response.getMessage();
    }

    public MovieResponseDto getMovie(int movieId) {
        CinemaProto.GetMovieRequest request = CinemaProto.GetMovieRequest.newBuilder().setId(movieId).build();
        CinemaProto.GetMovieResponse response = synchronousClient.withCallCredentials(new BearerToken(JwtContextHolder.getJwtToken())).getMovie(request);
        return new MovieResponseDto(
            response.getMovie().getId(),
            response.getMovie().getTitle(),
            response.getMovie().getGenre().name(),
            response.getMovie().getReleaseYear(),
            response.getMovie().getDurationMinutes(),
            response.getMovie().getRating()
        );
    }

    public MovieResponseDto updateMovie(Integer id, String title, String releaseYear, double rating, String genre, float durationMinutes) {
        CinemaProto.Movie movie = CinemaProto.Movie.newBuilder()
            .setId(id)
            .setTitle(title)
            .setReleaseYear(releaseYear)
            .setRating(rating)
            .setGenre(CinemaProto.Genre.valueOf(genre))
            .setDurationMinutes(durationMinutes)
            .build();
        CinemaProto.UpdateMovieRequest request = CinemaProto.UpdateMovieRequest.newBuilder()
            .setMovie(movie)
            .build();
        CinemaProto.UpdateMovieResponse response = synchronousClient.withCallCredentials(new BearerToken(JwtContextHolder.getJwtToken())).updateMovie(request);
        return new MovieResponseDto(
            response.getMovie().getId(),
            response.getMovie().getTitle(),
            response.getMovie().getGenre().name(),
            response.getMovie().getReleaseYear(),
            response.getMovie().getDurationMinutes(),
            response.getMovie().getRating()
        );
    }

    public String deleteMovie(int movieId) {
        CinemaProto.DeleteMovieRequest request = CinemaProto.DeleteMovieRequest.newBuilder().setId(movieId).build();
        CinemaProto.DeleteMovieResponse response = synchronousClient.withCallCredentials(new BearerToken(JwtContextHolder.getJwtToken())).deleteMovie(request);
        return response.getMessage();
    }

    public TVSeriesResponseDto createTVSeries(String title, String genre, double rating, boolean ongoing, int seasons) {
        CinemaProto.TVSeries tvSeries = CinemaProto.TVSeries.newBuilder()
            .setTitle(title)
            .setGenre(CinemaProto.Genre.valueOf(genre))
            .setOngoing(ongoing)
            .setSeasons(seasons)
            .setRating(rating)
            .build();
        CinemaProto.CreateTVSeriesRequest request = CinemaProto.CreateTVSeriesRequest.newBuilder()
            .setSeries(tvSeries)
            .build();
        CinemaProto.CreateTVSeriesResponse response = synchronousClient.withCallCredentials(new BearerToken(JwtContextHolder.getJwtToken())).createTVSeries(request);
        return new TVSeriesResponseDto(
            response.getSeries().getId(),
            response.getSeries().getTitle(),
            response.getSeries().getGenre().name(),
            response.getSeries().getSeasons(),
            response.getSeries().getOngoing(),
            response.getSeries().getRating()
        );
    }

    public TVSeriesResponseDto getTVSeries(int seriesId) {
        CinemaProto.GetTVSeriesRequest request = CinemaProto.GetTVSeriesRequest.newBuilder().setId(seriesId).build();
        CinemaProto.GetTVSeriesResponse response = synchronousClient.withCallCredentials(new BearerToken(JwtContextHolder.getJwtToken())).getTVSeries(request);
        return new TVSeriesResponseDto(
            response.getSeries().getId(),
            response.getSeries().getTitle(),
            response.getSeries().getGenre().name(),
            response.getSeries().getSeasons(),
            response.getSeries().getOngoing(),
            response.getSeries().getRating()
        );
    }

    public TVSeriesResponseDto updateTVSeries(Integer id, String title, String genre, double rating, boolean ongoing, int seasons) {
        CinemaProto.TVSeries tvSeries = CinemaProto.TVSeries.newBuilder()
            .setId(id)
            .setTitle(title)
            .setGenre(CinemaProto.Genre.valueOf(genre))
            .setOngoing(ongoing)
            .setSeasons(seasons)
            .setRating(rating)
            .build();
        CinemaProto.UpdateTVSeriesRequest request = CinemaProto.UpdateTVSeriesRequest.newBuilder()
            .setSeries(tvSeries)
            .build();
        CinemaProto.UpdateTVSeriesResponse response = synchronousClient.withCallCredentials(new BearerToken(JwtContextHolder.getJwtToken())).updateTVSeries(request);
        return new TVSeriesResponseDto(
            response.getSeries().getId(),
            response.getSeries().getTitle(),
            response.getSeries().getGenre().name(),
            response.getSeries().getSeasons(),
            response.getSeries().getOngoing(),
            response.getSeries().getRating()
        );
    }
    public String deleteTVSeries(int seriesId) {
        CinemaProto.DeleteTVSeriesRequest request = CinemaProto.DeleteTVSeriesRequest.newBuilder().setId(seriesId).build();
        CinemaProto.DeleteTVSeriesResponse response = synchronousClient.withCallCredentials(new BearerToken(JwtContextHolder.getJwtToken())).deleteTVSeries(request);
        return response.getMessage();
    }
}
