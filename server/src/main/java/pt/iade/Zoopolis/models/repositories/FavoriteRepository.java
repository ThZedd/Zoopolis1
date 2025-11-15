package pt.iade.Zoopolis.models.repositories;


import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.iade.Zoopolis.models.Animal;
import pt.iade.Zoopolis.models.AnimalDTO;
import pt.iade.Zoopolis.models.Favorite;
import pt.iade.Zoopolis.models.Person;


@Repository
public interface FavoriteRepository extends CrudRepository<Favorite, Integer> {

    @Query("""
            SELECT new pt.iade.Zoopolis.models.AnimalDTO(
                a.id, a.name, a.ciName, a.description, a.imageUrl
            )
            FROM Animal a
            JOIN Favorite f ON a.id = f.animal.id
            JOIN Person p ON p.id = f.person.id
            WHERE p.id = :personId
            """)
    Iterable<AnimalDTO> findAnimalsByPersonId(int personId);

    @Query("""
           SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END
           FROM Favorite f
           WHERE f.person.id = :personId AND f.animal.id = :animalId
           """)
    Boolean isFavorite(int personId, int animalId);



    @Query("""
        SELECT f
        FROM Favorite f
        WHERE f.person.id = :personId AND f.animal.id = :animalId
        """)
    Favorite findFavoriteByPersonAndAnimal(int personId, int animalId);

    @Modifying
    @Transactional
    @Query("""
        DELETE FROM Favorite f
        WHERE f.person.id = :personId AND f.animal.id = :animalId
        """)
    void removeFavorite(int personId, int animalId);

    // Método default para adicionar um favorito
    default void addFavorite(int personId, int animalId) {
        Person person = new Person();
        person.setId(personId);

        Animal animal = new Animal();
        animal.setId(animalId);

        Favorite favorite = new Favorite();
        favorite.setPerson(person);
        favorite.setAnimal(animal);

        save(favorite); // O método save já existe em CrudRepository
    }

}

