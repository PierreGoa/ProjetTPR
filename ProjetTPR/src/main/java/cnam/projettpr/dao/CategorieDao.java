package cnam.projettpr.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import cnam.projettpr.entity.Categorie;
import cnam.projettpr.mapper.CategorieRowMapper;

@Repository
public class CategorieDao implements ICategorieDao{

    public CategorieDao(NamedParameterJdbcTemplate template)
    {
        this.template = template;
    }
    NamedParameterJdbcTemplate template;

    @Override
    public List<Categorie> findAll()
    {
        return template.query("select * from categorie", new CategorieRowMapper());
    }

    @Override
    public void insertCategorie(Categorie categorie) {
        final String sql = "insert into Categorie(id_categorie, nom , designation) values(:id_categorie,:nom,:designation)";

        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id_categorie", categorie.getIdCategorie())
                .addValue("nom", categorie.getNomCategorie())
                .addValue("designation", categorie.getDesignationCategorie());
        template.update(sql,param, holder);
    }

    @Override
    public void updateCategorie(Categorie categorie) {
        final  String sql = "update Categorie set nom=:nom, designation=:designation where id_categorie=:id_categorie";

        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id_categorie", categorie.getIdCategorie())
                .addValue("nom", categorie.getNomCategorie())
                .addValue("designation", categorie.getDesignationCategorie());
        template.update(sql,param, holder);
    }

    @Override
    public void executeUpdateCategorie(Categorie categorie) {
        final String sql = "update Categorie set nom=:nom, designation=:designation where id_categorie=:id_categorie";

        Map<String,Object> map=new HashMap<String,Object>();
        map.put("id_categorie", categorie.getIdCategorie());
        map.put("nom", categorie.getDesignationCategorie());
        map.put("designation", categorie.getDesignationCategorie());

        template.execute(
                sql,
                map,
                new PreparedStatementCallback<Object>() {
                    @Override
                    public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                        return ps.executeUpdate();
                    }
                }
        );
    }

    @Override
    public void deleteCategorie(Categorie categorie) {
        final String sql = "delete from Categorie where id_categorie=:id_categorie";


        Map<String,Object> map=new HashMap<String,Object>();
        map.put("id_categorie", categorie.getIdCategorie());

        template.execute(
                sql,
                map,
                new PreparedStatementCallback<Object>() {
                    @Override
                    public Object doInPreparedStatement(PreparedStatement ps)
                            throws SQLException, DataAccessException {
                        return ps.executeUpdate();
                    }
                }
        );
    }
}