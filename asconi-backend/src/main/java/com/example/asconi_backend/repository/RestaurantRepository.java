package com.example.asconi_backend.repository;
import com.example.asconi_backend.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    List<Restaurant> findByRestaurantName(String restaurantName);
//filltrare sali dupa restaurant, cu statut
    @Query("SELECT r.restaurantName, s.hallName, s.hallStatus FROM Restaurant r " +
            "JOIN r.hall s " +
            "WHERE r.id = :restaurantId " +
            "ORDER BY r.restaurantName")
    List<Object[]> findHallsByRestaurantId(@Param("restaurantId") Integer restaurantId);

    @Query("SELECT r.id, s.hallName, s.hallStatus FROM Restaurant r " +
            "JOIN r.hall s " +
            "WHERE r.restaurantName = :restaurantName " +
            "ORDER BY r.id")
    List<Object[]> findHallsByRestaurantName(@Param("restaurantName") Integer restaurantId);
}
