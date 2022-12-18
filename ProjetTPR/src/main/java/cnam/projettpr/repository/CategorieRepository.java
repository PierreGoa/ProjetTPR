package cnam.projettpr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

import cnam.projettpr.entity.Categorie;

@Repository
@Transactional
public interface CategorieRepository extends JpaRepository<Categorie, Integer> {

    List<Categorie> findByNomCategorieIgnoreCase(String keyWord);

}
