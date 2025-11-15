package pt.iade.Zoopolis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.iade.Zoopolis.models.Visited;
import pt.iade.Zoopolis.models.Person;
import pt.iade.Zoopolis.models.Sub_Area;
import pt.iade.Zoopolis.models.repositories.SubAreaRepository;
import pt.iade.Zoopolis.models.repositories.VisitedRepository;
import pt.iade.Zoopolis.models.repositories.PersonRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/visited")
public class VisitedController {

    @Autowired
    private VisitedRepository visitedRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private SubAreaRepository subAreaRepository;

    // Sub_area fixa (pode ser ajustada conforme necessário)
    private static final int FIXED_SUB_AREA_ID = 1;

    // Get all visits
    @GetMapping("")
    public Iterable<Visited> getAllVisits() {
        return visitedRepository.findAll();
    }

    // Get a visit by ID
    @GetMapping("/{id}")
    public ResponseEntity<Visited> getVisitById(@PathVariable int id) {
        Optional<Visited> visit = visitedRepository.findById(id);
        if (visit.isPresent()) {
            return ResponseEntity.ok(visit.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Create a new visit
    @PostMapping("")
    public ResponseEntity<Visited> createVisit(
            @RequestParam int personId,
            @RequestParam int animalId
    ) {
        // Buscar a pessoa pelo ID
        Optional<Person> personOpt = personRepository.findById(personId);
        if (personOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        // Determinar o Sub_Area com base no animalId
        int subAreaId = animalId >= 5 ? 3 : FIXED_SUB_AREA_ID;
        Optional<Sub_Area> subAreaOpt = subAreaRepository.findById(subAreaId);
        if (subAreaOpt.isEmpty()) {
            return ResponseEntity.internalServerError().body(null);
        }

        // Criar a nova visita
        Visited visited = new Visited();
        visited.setPerson(personOpt.get());
        visited.setSubArea(subAreaOpt.get());
        visited.setDtime(LocalDateTime.now()); // Preencher com a data e hora atuais

        // Salvar no repositório
        Visited savedVisit = visitedRepository.save(visited);
        return ResponseEntity.ok(savedVisit);
    }


    // Update a visit
    @PutMapping("/{id}")
    public ResponseEntity<Visited> updateVisit(@PathVariable int id, @RequestBody Visited updatedVisit) {
        Optional<Visited> visit = visitedRepository.findById(id);
        if (visit.isPresent()) {
            Visited existingVisit = visit.get();
            existingVisit.setDtime(updatedVisit.getDtime());
            existingVisit.setPerson(updatedVisit.getPerson());
            existingVisit.setSubArea(updatedVisit.getSubArea());
            visitedRepository.save(existingVisit);
            return ResponseEntity.ok(existingVisit);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a visit
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVisit(@PathVariable int id) {
        Optional<Visited> visit = visitedRepository.findById(id);
        if (visit.isPresent()) {
            visitedRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get the most visited sub-area
    @GetMapping("/most-visited-subarea")
    public ResponseEntity<?> getMostVisitedSubArea() {
        List<Object[]> results = visitedRepository.findMostVisitedSubArea();

        if (results.isEmpty()) {
            return ResponseEntity.ok("Nenhuma subárea foi visitada.");
        }

        Object[] mostVisited = results.get(0); // Primeiro resultado é a subárea mais visitada
        String subAreaName = (String) mostVisited[0];
        Long visitCount = (Long) mostVisited[1];

        return ResponseEntity.ok(Map.of(
                "subArea", subAreaName,
                "visitCount", visitCount
        ));
    }

}
