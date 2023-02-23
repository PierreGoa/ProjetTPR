package cnam.projettpr.repository;

import java.util.List;

import cnam.projettpr.entity.HistoProduitStock;
import cnam.projettpr.entity.ProduitRef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

import cnam.projettpr.entity.ProduitStock;


@Repository
@Transactional
public interface HistoProduitStockRepository  extends JpaRepository<HistoProduitStock, Integer> {
    @Query("SELECT p FROM HistoProduitStock p ORDER BY p.dateHisto DESC")
    public List<HistoProduitStock> findAll();
}

