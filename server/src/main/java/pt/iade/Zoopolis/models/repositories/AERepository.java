package pt.iade.Zoopolis.models.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.iade.Zoopolis.models.AE;
import pt.iade.Zoopolis.models.Animal;
import java.util.List;


@Repository
public interface AERepository extends CrudRepository<AE, Integer> {
        List<AE> findByAnimalId(int animalId);
}
