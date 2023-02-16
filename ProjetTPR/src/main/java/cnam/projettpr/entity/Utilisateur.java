package cnam.projettpr.entity;

import javax.persistence.*;

@Entity
@Table(name = "utilisateur")
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idUtilisateur;

    @Column(length = 80, nullable = false)
    private String login;

    @Column(length = 80, nullable = true)
    private String nom;

    @Column(length = 80, nullable = true)
    private String prenom;

    @Column(length = 80, nullable = true)
    private String password;

    @Column(nullable = false)
    private int role = 0;

    @Transient
    private String roleStr;


    public void setId(Integer id){
        this.idUtilisateur = id;
    }
    public Integer getId(){
        return this.idUtilisateur;
    }

    public void setLogin(String login){this.login = login;}
    public String getLogin(){return this.login;}

    public void setNom(String nom){this.nom = nom;}
    public String getNom(){return this.nom;}

    public void setPrenom(String prenom){this.prenom = prenom;}
    public String getPrenom(){return this.prenom;}

    public void setPassword(String password){this.password = password;}
    public String getPassword(){return this.password;}

    public void setRole(int role){this.role = role;}
    public int getRole(){return this.role;}

    @Transient
    public String getRoleStr(){
        String result = "";

        switch (getRole()) {
            case 0 :
                result = "ROLE_USER";
                break;
            case 1 :
                result = "ROLE_ADMINISTRATEUR";
                break;
            default:
                break;
        }
        return result;
    }

    @Transient
    public void setRoleStr(String roleStr){
        this.roleStr = roleStr;
        if (roleStr.equals("ROLE_USER")){
            setRole(0);
        } else if (roleStr.equals("ROLE_ADMINISTRATEUR")){
            setRole(1);
        }
    }

    public Utilisateur(){}

    @Override
    public String toString() {
        return "";
    }

}


