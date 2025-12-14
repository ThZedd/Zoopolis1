package pt.iade.Zoopolis.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pt.iade.Zoopolis.models.Animal;

import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Integer> {

    // Usa uma query SQL nativa para garantir que todos os registos são retornados,
    // contornando qualquer configuração de paginação por defeito do Spring.
    @Query(value = "SELECT * FROM animal", nativeQuery = true)
    List<Animal> findAllAnimalsNatively();
}
