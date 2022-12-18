package cnam.projettpr.repository;

import java.util.List;

import cnam.projettpr.entity.ProduitRef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

import cnam.projettpr.entity.ProduitStock;


@Repository
@Transactional
public interface ProduitStockRepository  extends JpaRepository<ProduitStock, Integer> {
    @Query("SELECT p FROM ProduitStock p WHERE p.status=0 OR p.status=2")
    public List<ProduitStock> findAll();
}

