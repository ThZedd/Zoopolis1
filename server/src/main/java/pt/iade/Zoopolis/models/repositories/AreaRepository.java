package pt.iade.Zoopolis.models.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.iade.Zoopolis.models.Animal;
import pt.iade.Zoopolis.models.Area;


@Repository
public interface AreaRepository extends CrudRepository<Area, Integer> {

}
