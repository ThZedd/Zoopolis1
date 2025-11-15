package pt.iade.Zoopolis.models.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.iade.Zoopolis.models.Animal;



@Repository
public interface AnimalRepository extends CrudRepository<Animal, Integer> {

}
