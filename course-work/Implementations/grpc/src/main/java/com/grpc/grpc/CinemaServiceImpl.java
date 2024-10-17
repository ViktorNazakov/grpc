package com.grpc.grpc;

import com.grpc.repository.ActorRepository;
import com.grpc.repository.MovieRepository;
import com.grpc.repository.TVSeriesRepository;
import net.devh.boot.grpc.server.service.GrpcService;
import com.grpc.CinemaServiceGrpc.CinemaServiceImplBase;
import com.grpc.CinemaProto.*;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@GrpcService
public class CinemaServiceImpl extends CinemaServiceImplBase {

    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;
    private final TVSeriesRepository tvSeriesRepository;

    @Autowired
    public CinemaServiceImpl(ActorRepository actorRepository, MovieRepository movieRepository, TVSeriesRepository tvSeriesRepository) {
        this.actorRepository = actorRepository;
        this.movieRepository = movieRepository;
        this.tvSeriesRepository = tvSeriesRepository;
    }

    @Override
    public void updateActor(UpdateActorRequest request, StreamObserver<UpdateActorResponse> responseObserver) {
        Actor actor = request.getActor();

        com.grpc.entity.Actor existingActor = actorRepository.findById(actor.getId()).orElse(null);

        if (existingActor == null) {
            UpdateActorResponse response = UpdateActorResponse.newBuilder()
                .setSuccess(false)
                .setMessage("Actor not found")
                .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            return;
        }

        existingActor.setAwards(actor.getAwardsList());
        existingActor.setName(actor.getName());
        existingActor.setBirthDate(actor.getBirthDate());
        existingActor.setNationality(actor.getNationality());
        existingActor.setRating(actor.getRating());
        existingActor.setAlive(actor.getAlive());

        com.grpc.entity.Actor savedActor = actorRepository.save(existingActor);

        UpdateActorResponse response = UpdateActorResponse.newBuilder()
            .setSuccess(true)
            .setMessage("Actor updated successfully")
            .setActor(Actor.newBuilder()
                .setId(savedActor.getId())
                .setBirthDate(savedActor.getBirthDate())
                .setName(savedActor.getName())
                .setAlive(savedActor.getAlive())
                .setRating(savedActor.getRating())
                .setNationality(savedActor.getNationality())
                .addAllAwards(savedActor.getAwards())
                .build())
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }


    @Override
    public void updateMovie(UpdateMovieRequest request, StreamObserver<UpdateMovieResponse> responseObserver) {
        Movie movie = request.getMovie();

        com.grpc.entity.Movie existingMovie = movieRepository.findById(movie.getId()).orElse(null);

        if (existingMovie == null) {
            UpdateMovieResponse response = UpdateMovieResponse.newBuilder()
                .setSuccess(false)
                .setMessage("Movie not found")
                .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            return;
        }

        existingMovie.setGenre(com.grpc.entity.Genre.valueOf(movie.getGenre().name()));
        existingMovie.setDurationMinutes(movie.getDurationMinutes());
        existingMovie.setTitle(movie.getTitle());
        existingMovie.setRating(movie.getRating());
        existingMovie.setReleaseYear(movie.getReleaseYear());

        com.grpc.entity.Movie savedMovie = movieRepository.save(existingMovie);

        UpdateMovieResponse response = UpdateMovieResponse.newBuilder()
            .setSuccess(true)
            .setMessage("Movie updated successfully")
            .setMovie(Movie.newBuilder()
                .setId(savedMovie.getId())
                .setDurationMinutes(savedMovie.getDurationMinutes())
                .setTitle(savedMovie.getTitle())
                .setRating(savedMovie.getRating())
                .setGenre(Genre.valueOf(savedMovie.getGenre().name()))
                .setReleaseYear(savedMovie.getReleaseYear())
                .build())
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }


    @Override
    public void updateTVSeries(UpdateTVSeriesRequest request, StreamObserver<UpdateTVSeriesResponse> responseObserver) {
        TVSeries tvSeries = request.getSeries();

        com.grpc.entity.TVSeries existingTVSeries = tvSeriesRepository.findById(tvSeries.getId()).orElse(null);

        if (existingTVSeries == null) {
            UpdateTVSeriesResponse response = UpdateTVSeriesResponse.newBuilder()
                .setSuccess(false)
                .setMessage("TV Series not found")
                .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            return;
        }

        existingTVSeries.setGenre(com.grpc.entity.Genre.valueOf(tvSeries.getGenre().name()));
        existingTVSeries.setTitle(tvSeries.getTitle());
        existingTVSeries.setOngoing(tvSeries.getOngoing());
        existingTVSeries.setRating(tvSeries.getRating());
        existingTVSeries.setSeasons(tvSeries.getSeasons());

        com.grpc.entity.TVSeries savedTVSeries = tvSeriesRepository.save(existingTVSeries);

        UpdateTVSeriesResponse response = UpdateTVSeriesResponse.newBuilder()
            .setSuccess(true)
            .setMessage("TV Series updated successfully")
            .setSeries(TVSeries.newBuilder()
                .setId(savedTVSeries.getId())
                .setGenre(Genre.valueOf(savedTVSeries.getGenre().name()))
                .setOngoing(savedTVSeries.getOngoing())
                .setSeasons(savedTVSeries.getSeasons())
                .setRating(savedTVSeries.getRating())
                .setTitle(savedTVSeries.getTitle())
                .build())
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }


    @Override
    public void createActor(CreateActorRequest request, StreamObserver<CreateActorResponse> responseObserver) {
        Actor actor = request.getActor();
        com.grpc.entity.Actor actorEntity = new com.grpc.entity.Actor();
        actorEntity.setAwards(actor.getAwardsList());
        actorEntity.setName(actor.getName());
        actorEntity.setBirthDate(actor.getBirthDate());
        actorEntity.setNationality(actor.getNationality());
        actorEntity.setRating(actor.getRating());
        actorEntity.setAlive(actor.getAlive());
        com.grpc.entity.Actor savedActor = actorRepository.save(actorEntity);

        CreateActorResponse response = CreateActorResponse.newBuilder()
            .setSuccess(true)
            .setMessage("Actor created successfully")
            .setActor(Actor.newBuilder().setId(savedActor.getId())
                .setBirthDate(savedActor.getBirthDate())
                .setName(savedActor.getName())
                .setAlive(savedActor.getAlive())
                .setRating(savedActor.getRating())
                .setNationality(savedActor.getNationality())
                .addAllAwards(savedActor.getAwards()).build())
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getActor(GetActorRequest request, StreamObserver<GetActorResponse> responseObserver) {
        int actorId = request.getId();
        Optional<com.grpc.entity.Actor> actorOptional = actorRepository.findById(actorId);

        GetActorResponse.Builder responseBuilder = GetActorResponse.newBuilder();
        if (actorOptional.isPresent()) {
            responseBuilder.setActor(Actor.newBuilder().setId(actorOptional.get().getId())
                .setBirthDate(actorOptional.get().getBirthDate())
                .setName(actorOptional.get().getName())
                .setAlive(actorOptional.get().getAlive())
                .setRating(actorOptional.get().getRating())
                .setNationality(actorOptional.get().getNationality())
                .addAllAwards(actorOptional.get().getAwards()).build());
            responseBuilder.setSuccess(true);
        } else {
            responseBuilder.setSuccess(false);
            responseBuilder.setMessage("Actor not found");
        }

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void deleteActor(DeleteActorRequest request, StreamObserver<DeleteActorResponse> responseObserver) {
        int actorId = request.getId();
        actorRepository.deleteById(actorId);

        DeleteActorResponse response = DeleteActorResponse.newBuilder()
            .setSuccess(true)
            .setMessage("Actor deleted successfully")
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void createMovie(CreateMovieRequest request, StreamObserver<CreateMovieResponse> responseObserver) {
        Movie movie = request.getMovie();
        com.grpc.entity.Movie movieEntity = new com.grpc.entity.Movie();
        movieEntity.setGenre(com.grpc.entity.Genre.valueOf(movie.getGenre().name()));
        movieEntity.setDurationMinutes(movie.getDurationMinutes());
        movieEntity.setTitle(movie.getTitle());
        movieEntity.setRating(movie.getRating());
        movieEntity.setReleaseYear(movie.getReleaseYear());
        com.grpc.entity.Movie savedMovie = movieRepository.save(movieEntity);

        CreateMovieResponse response = CreateMovieResponse.newBuilder()
            .setSuccess(true)
            .setMessage("Movie created successfully")
            .setMovie(Movie.newBuilder()
                .setId(savedMovie.getId())
                .setDurationMinutes(savedMovie.getDurationMinutes())
                .setTitle(savedMovie.getTitle())
                .setRating(savedMovie.getRating())
                .setGenre(Genre.valueOf(savedMovie.getGenre().name()))
                .setReleaseYear(savedMovie.getReleaseYear()).build())
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getMovie(GetMovieRequest request, StreamObserver<GetMovieResponse> responseObserver) {
        int movieId = request.getId();
        Optional<com.grpc.entity.Movie> movieOptional = movieRepository.findById(movieId);

        GetMovieResponse.Builder responseBuilder = GetMovieResponse.newBuilder();
        if (movieOptional.isPresent()) {
            responseBuilder.setMovie(Movie.newBuilder()
                .setId(movieOptional.get().getId())
                .setReleaseYear(movieOptional.get().getReleaseYear())
                .setTitle(movieOptional.get().getTitle())
                .setRating(movieOptional.get().getRating())
                .setDurationMinutes(movieOptional.get().getDurationMinutes())
                .setGenre(Genre.valueOf(movieOptional.get().getGenre().name())).build());
            responseBuilder.setSuccess(true);
        } else {
            responseBuilder.setSuccess(false);
            responseBuilder.setMessage("Movie not found");
        }

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void deleteMovie(DeleteMovieRequest request, StreamObserver<DeleteMovieResponse> responseObserver) {
        int movieId = request.getId();
        movieRepository.deleteById(movieId);

        DeleteMovieResponse response = DeleteMovieResponse.newBuilder()
            .setSuccess(true)
            .setMessage("Movie deleted successfully")
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void createTVSeries(CreateTVSeriesRequest request, StreamObserver<CreateTVSeriesResponse> responseObserver) {
        TVSeries tvSeries = request.getSeries();
        com.grpc.entity.TVSeries tvSeriesEntity = new com.grpc.entity.TVSeries();
        tvSeriesEntity.setGenre(com.grpc.entity.Genre.valueOf(tvSeries.getGenre().name()));
        tvSeriesEntity.setTitle(tvSeries.getTitle());
        tvSeriesEntity.setOngoing(tvSeries.getOngoing());
        tvSeriesEntity.setRating(tvSeries.getRating());
        tvSeriesEntity.setSeasons(tvSeries.getSeasons());
        com.grpc.entity.TVSeries savedTVSeries = tvSeriesRepository.save(tvSeriesEntity);

        CreateTVSeriesResponse response = CreateTVSeriesResponse.newBuilder()
            .setSuccess(true)
            .setMessage("TV Series created successfully")
            .setSeries(TVSeries.newBuilder()
                .setId(savedTVSeries.getId())
                .setGenre(Genre.valueOf(savedTVSeries.getGenre().name()))
                .setOngoing(savedTVSeries.getOngoing())
                .setSeasons(savedTVSeries.getSeasons())
                .setRating(savedTVSeries.getRating())
                .setTitle(savedTVSeries.getTitle()).build())
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getTVSeries(GetTVSeriesRequest request, StreamObserver<GetTVSeriesResponse> responseObserver) {
        int tvSeriesId = request.getId();
        Optional<com.grpc.entity.TVSeries> tvSeriesOptional = tvSeriesRepository.findById(tvSeriesId);

        GetTVSeriesResponse.Builder responseBuilder = GetTVSeriesResponse.newBuilder();
        if (tvSeriesOptional.isPresent()) {
            responseBuilder.setSeries(TVSeries.newBuilder()
                .setId(tvSeriesOptional.get().getId())
                .setTitle(tvSeriesOptional.get().getTitle())
                .setSeasons(tvSeriesOptional.get().getSeasons())
                .setRating(tvSeriesOptional.get().getRating())
                .setOngoing(tvSeriesOptional.get().getOngoing())
                .setGenre(Genre.valueOf(tvSeriesOptional.get().getGenre().name())).build());
            responseBuilder.setSuccess(true);
        } else {
            responseBuilder.setSuccess(false);
            responseBuilder.setMessage("TV Series not found");
        }

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void deleteTVSeries(DeleteTVSeriesRequest request, StreamObserver<DeleteTVSeriesResponse> responseObserver) {
        int tvSeriesId = request.getId();
        tvSeriesRepository.deleteById(tvSeriesId);

        DeleteTVSeriesResponse response = DeleteTVSeriesResponse.newBuilder()
            .setSuccess(true)
            .setMessage("TV Series deleted successfully")
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}

