package br.com.lets_code.Movies.Batlle.core.services;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lets_code.Movies.Batlle.core.entities.Film;
import br.com.lets_code.Movies.Batlle.core.entities.FilmCombination;
import br.com.lets_code.Movies.Batlle.core.entities.GameMatch;
import br.com.lets_code.Movies.Batlle.core.entities.GameRound;
import br.com.lets_code.Movies.Batlle.core.entities.User;
import br.com.lets_code.Movies.Batlle.core.exceptions.GameMatchOpenNotFound;
import br.com.lets_code.Movies.Batlle.core.exceptions.WrongFilmCombinationGuess;
import br.com.lets_code.Movies.Batlle.data_providers.repositories.FilmCombinationRepository;
import br.com.lets_code.Movies.Batlle.data_providers.repositories.FilmsRepository;
import br.com.lets_code.Movies.Batlle.data_providers.repositories.GameRoundRepository;
import br.com.lets_code.Movies.Batlle.presenter.rest.dtos.request.QuizzRequest;

@Service
public class GameRoundService {

    @Autowired
    GameRoundRepository gameRoundRepository;

    @Autowired
    FilmsRepository filmsRepository;

    @Autowired
    FilmCombinationRepository filmCombinationRepository;

    @Autowired
    GameMatchService gameMatchService;

    @Autowired
    UserService userService;
    
    private boolean guessSuccess(List<Film> films) {
        return films.get(0).getRating() > films.get(1).getRating();
    }

    public Boolean handlePlayerGuess(QuizzRequest quizzRequest, Principal principal) throws GameMatchOpenNotFound, WrongFilmCombinationGuess {
        User user = userService.getUserFromPrincipal(principal);
        GameMatch gameMatch = gameMatchService.getCurrentGameMatchFromSessionPlayer(user);
        FilmCombination filmCombination = filmCombinationRepository.findFirst1ByUserIdAndAttemptsLessThanOrderByIdAsc(user.getId(), 3).get();
        validatePlayerGuess(quizzRequest, filmCombination);
        List<Film> films = filmsRepository.findAllById(Arrays.asList(quizzRequest.getGuessWinningMovieId(), quizzRequest.getGuessLosingMovieId()));
        if (this.guessSuccess(films)) {
            gameRoundRepository.save(new GameRound(gameMatch, 1));
            filmCombination.setAttempts(3);
            filmCombinationRepository.save(filmCombination);
            return true;
        }
        gameRoundRepository.save(new GameRound(gameMatch, 0));
        filmCombination.setAttempts(filmCombination.getAttempts()+1);
        filmCombinationRepository.save(filmCombination);
        return false;
}

    private void validatePlayerGuess(QuizzRequest quizzRequest, FilmCombination filmCombination) throws WrongFilmCombinationGuess {
        Supplier<Stream<String>> filmsStreamSupplier = () ->  Arrays.asList(String.valueOf(filmCombination.getFirstFilmCombination()), String.valueOf(filmCombination.getSecondFilmCombination())).stream();
        boolean isPlayerGuessingTheRightFilmCombination = filmsStreamSupplier.get().anyMatch(quizzRequest.getGuessLosingMovieId()::equals) && filmsStreamSupplier.get().anyMatch(quizzRequest.getGuessLosingMovieId()::equals);
        if (isPlayerGuessingTheRightFilmCombination) {
            return;
        }
        throw new WrongFilmCombinationGuess();
    }
}
