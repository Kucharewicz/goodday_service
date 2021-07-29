package pl.project.goodday.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.project.goodday.models.GoldenThought;

import java.util.List;

@Repository
public interface GoldenThoughtsRepository extends JpaRepository<GoldenThought,Integer> {
    @Query("select id from GoldenThought")
    List<Integer> getAllIndexes();

}
