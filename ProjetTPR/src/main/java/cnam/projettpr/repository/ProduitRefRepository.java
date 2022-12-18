package cnam.projettpr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

import cnam.projettpr.entity.ProduitRef;

@Repository
@Transactional
public interface ProduitRefRepository extends JpaRepository<ProduitRef, Integer> {
    List<ProduitRef> findByNomProduitRefIgnoreCase(String keyWord);
}

