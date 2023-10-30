package ply.cars.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ply.cars.models.entities.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    
    /*
     * This is the simplest - and arguably the ugliest - approach,
     * it would be better to implement this using the Criteria API, 
     * so only valid filter would be applied in the query without
     * these nasty '...IS NULL OR...' conditions. It is for demonstration
     * purposes only.
     */


    @Query("SELECT c " + 
            "FROM Car c " +
            "WHERE " +
            "(:minYop = -1 OR c.yop >= :minYop) AND " +
            "(:maxYop = -1 OR c.yop <= :maxYop) AND " +
            "(:plates IS NULL OR c.plates LIKE %:plates%) AND " +
            "(:brand IS NULL OR c.brand = :brand) AND " +
            "(:model IS NULL OR c.model = :model)")

    public List<Car> filter(
        int minYop,
        int maxYop,
        String plates,
        String brand,
        String model
    );
}
