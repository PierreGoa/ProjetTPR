package cnam.projettpr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

import cnam.projettpr.entity.HistoFrigo;

@Repository
@Transactional
public interface HistoFrigoRepository extends JpaRepository<HistoFrigo, Integer> {

    //List<HistoFrigo> findByNomIgnoreCase(String keyWord);

}
