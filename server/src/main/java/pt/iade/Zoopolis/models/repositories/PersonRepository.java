package pt.iade.Zoopolis.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.iade.Zoopolis.models.Person;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    // Buscar uma pessoa pelo e-mail
    Optional<Person> findByEmail(String email);

    // Verificar se um e-mail já está cadastrado
    boolean existsByEmail(String email);
}
