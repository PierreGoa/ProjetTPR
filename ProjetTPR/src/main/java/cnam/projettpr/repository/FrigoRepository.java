package cnam.projettpr.repository;

import java.util.List;

import cnam.projettpr.entity.ProduitStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

import cnam.projettpr.entity.Frigo;

@Repository
@Transactional
public interface FrigoRepository extends JpaRepository<Frigo, Integer> {

    List<Frigo> findByNomFrigoIgnoreCase(String keyWord);

    @Query("SELECT f FROM Frigo f ORDER BY f.nomFrigo ASC")
    public List<Frigo> findAll();

}
