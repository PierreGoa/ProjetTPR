package cnam.projettpr.repository;

import java.util.Date;
import java.util.List;

import cnam.projettpr.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

import cnam.projettpr.entity.HistoFrigo;

@Repository
@Transactional
public interface HistoFrigoRepository extends JpaRepository<HistoFrigo, Integer> {


    List<HistoFrigo> findByIdFrigo(@Param("id") Integer idHisto, @Param("datehisto") Date dateHisto);
}
