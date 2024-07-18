package com.gamesbox.repository;

import com.gamesbox.entity.Game;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GamesRepository extends JpaRepository<Game, Long> {
    @Query(value = "SELECT * FROM users_games WHERE userid = :userId LIMIT 20 OFFSET :offset", nativeQuery = true)
    List<Game> findCustomResultsByUserId(@Param("userId") int userId, @Param("offset") int offset);
    Game findGameByGameidAndUserid(String gameId, int userId);
    Game save(Game game);

}
