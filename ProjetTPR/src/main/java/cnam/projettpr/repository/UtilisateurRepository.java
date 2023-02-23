package cnam.projettpr.repository;

import java.util.List;

import cnam.projettpr.entity.Frigo;
import cnam.projettpr.entity.ProduitStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

import cnam.projettpr.entity.Utilisateur;

@Repository
@Transactional
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {

    @Query("SELECT u FROM Utilisateur u ORDER BY u.login")
    public List<Utilisateur> findAll();

    @Query("SELECT u FROM Utilisateur u WHERE u.nom = :username")
    public Utilisateur getUserByNom(@Param("username") String username);

    @Query("SELECT u FROM Utilisateur u WHERE u.login = :login")
    public Utilisateur getUserByLogin(@Param("login") String login);


    List<Utilisateur> findByNomIgnoreCase(String keyWord);

}
