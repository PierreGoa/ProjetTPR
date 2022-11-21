package cnam.projettpr.service;

import java.util.List;
import cnam.projettpr.entity.Categorie;

public interface ICategorieService {
    List<Categorie> findAll();

    void insertCategorie(Categorie categorie);

    void updateCategorie(Categorie categorie);

    void executeUpdateCategorie(Categorie categorie);

    void deleteCategorie(Categorie categorie);
}
