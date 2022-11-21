package cnam.projettpr.dao;

import java.util.List;
import cnam.projettpr.entity.Categorie;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategorieDao {
    List<Categorie> findAll();

    void insertCategorie(Categorie categorie);
    void updateCategorie(Categorie categorie);
    void executeUpdateCategorie(Categorie categorie);
    public void deleteCategorie(Categorie categorie);
}
