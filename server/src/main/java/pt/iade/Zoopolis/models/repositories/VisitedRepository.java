package pt.iade.Zoopolis.models.repositories;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.iade.Zoopolis.models.Animal;
import pt.iade.Zoopolis.models.Visited;

import java.util.List;


@Repository
public interface VisitedRepository extends CrudRepository<Visited, Integer> {
    @Query("SELECT v.subArea.name, COUNT(v) AS visitCount " +
            "FROM Visited v GROUP BY v.subArea.id " +
            "ORDER BY COUNT(v) DESC")
    List<Object[]> findMostVisitedSubArea();

}
