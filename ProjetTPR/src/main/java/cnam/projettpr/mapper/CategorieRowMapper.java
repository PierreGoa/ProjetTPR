package cnam.projettpr.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import cnam.projettpr.entity.Categorie;


public class CategorieRowMapper implements RowMapper<Categorie> {


    /**
     * Retourne un objet Categorie dont les propriétés sont renseignées
     * par le ResultSet rs.
     * @param rs   ResultSet de la ligne courante
     * @param arg1 Numéro de la ligne courante
     * @return
     * @throws SQLException
     */
    @Override
    public Categorie mapRow(ResultSet rs, int arg1) throws SQLException {
        Categorie categorie = new Categorie();
        categorie.setIdCategorie(rs.getLong("id_categorie"));
        categorie.setNomCategorie(rs.getString("nom"));
        categorie.setDesignationCategorie(rs.getString("designation"));

        return categorie;
    }
}

