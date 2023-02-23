package cnam.projettpr.controller;

import java.time.LocalDate;
import java.util.*;

import cnam.projettpr.dto.HistoFrigoDto;
import cnam.projettpr.entity.*;
import cnam.projettpr.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TprController {

    //Frigos
    @Autowired
    private FrigoRepository frigoRepository;

    @GetMapping("/frigos")
    public String getAllFrigos(Model model, @Param("keyword") String keyword) {
        try {
            List<Frigo> frigos = new ArrayList<Frigo>();

            if (keyword == null) {
                frigoRepository.findAll().forEach(frigos::add);
            } else {
                frigoRepository.findByNomFrigoIgnoreCase(keyword).forEach(frigos::add);
                model.addAttribute("keyword", keyword);
            }

            model.addAttribute("frigos", frigos);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        return "frigos";
    }

    @GetMapping("/frigos/new")
    public String addFrigo(Model model) {
        Frigo frigo = new Frigo();

        model.addAttribute("frigo", frigo);
        model.addAttribute("pageTitle", "Créer un frigo.");

        return "frigo_form";
    }


    @PostMapping("/frigos/save")
    public String saveFrigo(Frigo frigo, RedirectAttributes redirectAttributes) {
        try {
            Boolean isInsert = frigo.getId() == null;

            //Mise à jour des temp. min. et max du frigo en fonction de l'historique des températures.
            frigo.updateTemperatureMinMax(EntityHelper.getHistoriqueDuJour(frigo,new Date()));

            frigoRepository.save(frigo);


            /*
            HistoFrigo histoFrigo = new HistoFrigo();
            histoFrigo.setDateHisto(new Date());
            histoFrigo.setAction(isInsert ? 0:1);
            histoFrigo.setFrigo(frigo);
            Utilisateur utilisateur = utilisateurRepository.getUserByLogin("ADMIN");
            histoFrigo.setUtilisateur(utilisateur);
            histoFrigoRepository.save(histoFrigo);
            */
            redirectAttributes.addFlashAttribute("message", "Le frigo a été enregistré avec succès !");
        } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
        }

        return "redirect:/frigos";
    }

    @GetMapping("/frigos/{id}")
    public String editFrigo(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Frigo frigo = frigoRepository.findById(id).get();

            model.addAttribute("frigo", frigo);
            model.addAttribute("pageTitle", "Modifier le frigo (ID: " + id + ")");

            return "frigo_form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());

            return "redirect:/frigos";
        }
    }

    @GetMapping("/frigos/delete/{id}")
    public String deleteFrigo(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            frigoRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Le frigo avec l'id=" + id + " a été supprimé avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/frigos";
    }

    //Saisie des températures des frigos.
    @GetMapping("/tempfrigos/{id}")
    public String editTempFrigo(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Frigo frigo = frigoRepository.findById(id).get();

            HistoFrigo histoFrigo = EntityHelper.getHistoriqueDuJour(frigo,new Date());
            if (histoFrigo == null) {
                //Création d'un nouvel historique...
                histoFrigo = new HistoFrigo();
                histoFrigo.setDateHisto(new Date());
                histoFrigo.setFrigo(frigo);
                histoFrigo.setAction(0);
                frigo.getHistosFrigo().add(histoFrigo);
            } else {
                histoFrigo.setAction(1);
            }
            HistoFrigoDto histoFrigoDto = new HistoFrigoDto(histoFrigo);

            model.addAttribute("histofrigodto", histoFrigoDto);
            model.addAttribute("pageTitle", "Mettre à jour la température du frigo (ID: " + histoFrigo.getIdHistoFrigo() + ")");

            return "tempfrigo_form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());

            return "redirect:/frigos";
        }
    }

    @PostMapping("/temphistofrigos/save")
    public String saveTempHistoFrigo(HistoFrigoDto histofrigoDto, RedirectAttributes redirectAttributes) {
        try {
            Frigo frigo = frigoRepository.findById(histofrigoDto.getIdFrigo()).get();
            HistoFrigo histoFrigo = EntityHelper.getHistoriqueDuJour(frigo,new Date());
            if (histoFrigo == null) {
                histoFrigo = new HistoFrigo();
                histoFrigo.setFrigo(frigoRepository.findById(histofrigoDto.getIdFrigo()).get());
                histoFrigo.setAction(0);
                histoFrigo.setUtilisateur(utilisateurRepository.getUserByLogin("ADMIN"));
            }
            histoFrigo.setActionStr(histofrigoDto.getActionStr());
            histoFrigo.setDateHisto(histofrigoDto.getDateHisto());
            histoFrigo.setTempMatin(histofrigoDto.getTempMatin());
            histoFrigo.setTempMidi(histofrigoDto.getTempMidi());
            histoFrigoRepository.save(histoFrigo);

            //Maj des valeurs max et min de température.
            frigo.updateTemperatureMinMax(histoFrigo);
            frigoRepository.save(frigo);

            redirectAttributes.addFlashAttribute("message", "La température du frigo a été enregistrée avec succès !");
        } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
        }

        return "redirect:/frigos";
    }

    //Catégories
    @Autowired
    private CategorieRepository categorieRepository;
    @GetMapping("/categories")
    public String getAllCategories(Model model, @Param("keyword") String keyword) {
        try {
            List<Categorie> categories = new ArrayList<Categorie>();

            if (keyword == null) {
                categorieRepository.findAll().forEach(categories::add);
            } else {
                categorieRepository.findByNomCategorieIgnoreCase(keyword).forEach(categories::add);
                model.addAttribute("keyword", keyword);
            }

            model.addAttribute("categories", categories);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        return "categories";
    }

    @GetMapping("/categories/new")
    public String addCategorie(Model model) {
        Categorie categorie = new Categorie();

        model.addAttribute("categorie", categorie);
        model.addAttribute("pageTitle", "Créer une catégorie.");

        return "categorie_form";
    }


    @PostMapping("/categories/save")
    public String saveCategorie(Categorie categorie, RedirectAttributes redirectAttributes) {
        try {
            categorieRepository.save(categorie);

            redirectAttributes.addFlashAttribute("message", "La catégorie a été enregistrée avec succès !");
        } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
        }

        return "redirect:/categories";
    }

    @GetMapping("/categories/{id}")
    public String editCategorie(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Categorie categorie = categorieRepository.findById(id).get();

            model.addAttribute("categorie", categorie);
            model.addAttribute("pageTitle", "Modifier la catégorie (ID: " + id + ")");

            return "categorie_form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());

            return "redirect:/categories";
        }
    }

    @GetMapping("/categories/delete/{id}")
    public String deleteCategorie(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            categorieRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "La catégorie avec l'id=" + id + " a été supprimée avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/categories";
    }

    //Produit de référence
    @Autowired
    private ProduitRefRepository produitRefRepository;
    @GetMapping("/produitsref")
    public String getAllProduitsRef(Model model, @Param("keyword") String keyword) {
        try {
            List<ProduitRef> produitsRef = new ArrayList<ProduitRef>();

            if (keyword == null) {
                produitRefRepository.findAll().forEach(produitsRef::add);
            } else {
                produitRefRepository.findByNomProduitRefIgnoreCase(keyword).forEach(produitsRef::add);
                model.addAttribute("keyword", keyword);
            }

            //Tri selon les noms
            Collections.sort(produitsRef,(pr1,pr2) -> {
                return pr1.getNomProduitRef().compareTo(pr2.getNomProduitRef());
            });

            model.addAttribute("produitsref", produitsRef);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        return "produitsref";
    }

    @GetMapping("/produitsref/new")
    public String addProduitRef(Model model) {
        ProduitRef produitRef = new ProduitRef();
        List<Categorie> categoriesRef = categorieRepository.findAll();

        model.addAttribute("produitref", produitRef);
        model.addAttribute("categories", categoriesRef);
        model.addAttribute("pageTitle", "Créer un produit de référence.");

        return "produitref_form";
    }


    @PostMapping("/produitsref/save")
    public String saveProduitRef(ProduitRef produitRef, RedirectAttributes redirectAttributes) {
        try {
            produitRefRepository.save(produitRef);

            redirectAttributes.addFlashAttribute("message", "Le produit de référence a été enregistré avec succès !");
        } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
        }

        return "redirect:/produitsref";
    }

    @GetMapping("/produitsref/{id}")
    public String editProduitRef(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            ProduitRef produitRef = produitRefRepository.findById(id).get();
            List<Categorie> categoriesRef = categorieRepository.findAll();

            model.addAttribute("produitref", produitRef);
            model.addAttribute("categories", categoriesRef);
            model.addAttribute("pageTitle", "Modifier le produit de référence (ID: " + id + ")");

            return "produitref_form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());

            return "redirect:/produitsref";
        }
    }

    @GetMapping("/produitsref/delete/{id}")
    public String deleteProduitRef(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            produitRefRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Le produit de référence avec l'id=" + id + " a été supprimé avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/produitsref";
    }

    //Produit Stock
    @Autowired
    private ProduitStockRepository produitStockRepository;
    @GetMapping("/produitsstock")
    public String getAllProduitsStock(Model model, @Param("keyword") String keyword) {
        try {
            List<ProduitRef> produitsRef = new ArrayList<ProduitRef>();
            produitRefRepository.findAll().forEach(produitsRef::add);

            List<ProduitStock> produitsStock = new ArrayList<ProduitStock>();

            //Chargement "à la main" de la liste : on ne prend pas le statut CONSOMME
            for (ProduitStock ps:produitStockRepository.findAll()){
                if (ps.getStatus() != 1){
                    produitsStock.add(ps);
                }
            }

            //Tri selon les dates d'ouverture décroissantes
            Collections.sort(produitsStock,(ps1,ps2) -> {
                return ps2.getDateOuverture().compareTo(ps1.getDateOuverture());
            });


            model.addAttribute("produitsstock", produitsStock);
            model.addAttribute("produitsref", produitsRef);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        return "produitsstock";
    }

    @GetMapping("/produitsstock/new")
    public String addProduitStock(Model model) {
        ProduitStock produitStock = new ProduitStock();
        List<ProduitRef> produitsRef = produitRefRepository.findAll();

        model.addAttribute("produitstock", produitStock);
        model.addAttribute("produitsref", produitsRef);
        model.addAttribute("pageTitle", "Créer un produit Stock.");

        return "produitstock_form";
    }

    @PostMapping("/produitsstock/save")
    public String saveProduitStock(ProduitStock produitStock, RedirectAttributes redirectAttributes) {
        try {
            Boolean isInsert = produitStock.getId() == null;
            produitStockRepository.save(produitStock);

            Utilisateur utilisateur = utilisateurRepository.getUserByLogin("ADMIN");
            HistoProduitStock histoProduitStock = new HistoProduitStock(produitStock,isInsert ? 0:1,utilisateur);
            histoProduitStockRepository.save(histoProduitStock);

            redirectAttributes.addFlashAttribute("message", "Le produit stock a été enregistré avec succès !");
        } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
        }

        return "redirect:/produitsstock";
    }

    @GetMapping("/produitsstock/{id}")
    public String editProduitStock(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            ProduitStock produitStock = produitStockRepository.findById(id).get();
            List<ProduitRef> produitsRef = produitRefRepository.findAll();

            model.addAttribute("produitstock", produitStock);
            model.addAttribute("produitsref", produitsRef);
            model.addAttribute("pageTitle", "Modifier le produit stock (ID: " + id + ")");

            return "produitstock_form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());

            return "redirect:/produitsstock";
        }
    }

    @GetMapping("/produitsstock/delete/{id}")
    public String deleteProduitStock(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            produitStockRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Le produit stock avec l'id=" + id + " a été supprimé avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/produitsstock";
    }

    //Utilisateurs
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @GetMapping("/utilisateurs")
    public String getAllUtilisateurs(Model model, @Param("keyword") String keyword) {
        try {
            List<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();

            if (keyword == null) {
                utilisateurRepository.findAll().forEach(utilisateurs::add);
            } else {
                utilisateurRepository.findByNomIgnoreCase(keyword).forEach(utilisateurs::add);
                model.addAttribute("keyword", keyword);
            }

            model.addAttribute("utilisateurs", utilisateurs);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        return "utilisateurs";
    }

    @GetMapping("/utilisateurs/new")
    public String addUtilisateur(Model model) {
        Utilisateur utilisateur = new Utilisateur();
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("pageTitle", "Créer un utilisateur.");

        return "utilisateur_form";
    }

    @PostMapping("/utilisateurs/save")
    public String saveUtilisateur(Utilisateur utilisateur, RedirectAttributes redirectAttributes) {
        try {
            utilisateurRepository.save(utilisateur);

            redirectAttributes.addFlashAttribute("message", "L'utilisateur a été enregistré avec succès !");
        } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
        }

        return "redirect:/utilisateurs";
    }

    @GetMapping("/utilisateurs/{id}")
    public String editUtilisateur(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Utilisateur utilisateur = utilisateurRepository.findById(id).get();
            List<Utilisateur> utilisateurs = utilisateurRepository.findAll();

            model.addAttribute("utilisateur", utilisateur);
            model.addAttribute("pageTitle", "Modifier l'utilisateur (ID: " + id + ")");

            return "utilisateur_form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());

            return "redirect:/utilisateurs";
        }
    }

    @GetMapping("/utilisateurs/delete/{id}")
    public String deleteUtilisateur(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            utilisateurRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "L'utilisateur avec l'id=" + id + " a été supprimé avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/utilisateurs";
    }

    //Historiques frigos
    @Autowired
    private HistoFrigoRepository histoFrigoRepository;
    @GetMapping("/histofrigos")
    public String getAllHistoriquesFrigos(Model model, @Param("keyword") String keyword) {
        try {
            List<HistoFrigo> histoFrigos = new ArrayList<HistoFrigo>();

            if (keyword == null){
                histoFrigoRepository.findAll().forEach(histoFrigos::add);
            } else {
                //Chargement "à la main" de la liste : nomFrigo est @Transient dans l'historique
                //et n'est pas supporté par les méthode de filtre du repository.
                for (HistoFrigo hf:histoFrigoRepository.findAll()){
                    if (hf.getNomFrigo().toUpperCase().compareTo(keyword.toUpperCase()) == 0){
                        histoFrigos.add(hf);
                    }
                }
            }

            //Tri selon les dates d'historiques décroissantes
            Collections.sort(histoFrigos,(hf1,hf2) -> {
                return hf2.getDateHisto().compareTo(hf1.getDateHisto());
            });

            model.addAttribute("histofrigos", histoFrigos);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        return "histofrigos";
    }

    @GetMapping("/histofrigos/new")
    public String addHistoFrigo(Model model) {
        HistoFrigo histoFrigo = new HistoFrigo();
        model.addAttribute("histofrigo", histoFrigo);
        model.addAttribute("pageTitle", "Créer un historique frigo.");

        return "histofrigo_form";
    }

    @PostMapping("/histofrigos/save")
    public String saveHistoFrigo(HistoFrigo histoFrigo, RedirectAttributes redirectAttributes) {
        try {
            Utilisateur utilisateur = utilisateurRepository.getUserByLogin("ADMIN");
            histoFrigo.setUtilisateur(utilisateur);
            histoFrigoRepository.save(histoFrigo);

            redirectAttributes.addFlashAttribute("message", "L'historique de frigo a été enregistré avec succès !");
        } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
        }

        return "redirect:/histoFrigos";
    }

    @GetMapping("/histofrigos/{id}")
    public String editHistoFrigo(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            HistoFrigo histoFrigo = histoFrigoRepository.findById(id).get();
            List<HistoFrigo> histoFrigos = histoFrigoRepository.findAll();

            model.addAttribute("histofrigo", histoFrigo);
            model.addAttribute("pageTitle", "Modifier l'historique Frigo (ID: " + id + ")");

            return "histofrigo_form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());

            return "redirect:/histofrigos";
        }
    }

    @GetMapping("/histofrigos/delete/{id}")
    public String deleteHistoFrigo(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            histoFrigoRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "L'historique Frigo avec l'id=" + id + " a été supprimé avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/histofrigos";
    }

    //Historiques des produits stock.
    @Autowired
    private HistoProduitStockRepository histoProduitStockRepository;
    @GetMapping("/histoproduitStock")
    public String getAllHistoriquesProduitStock(Model model, @Param("keyword") String keyword) {
        try {
            List<HistoProduitStock> histoProduitsStock = new ArrayList<HistoProduitStock>();

            //if (keyword == null) {
            histoProduitStockRepository.findAll().forEach(histoProduitsStock::add);
            //} else {
            //  histoFrigoRepository.findByNomIgnoreCase(keyword).forEach(histoFrigos::add);
            //  model.addAttribute("keyword", keyword);
            //}

            model.addAttribute("histoproduitsstock", histoProduitsStock);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        return "histoproduitsstock";
    }

    @GetMapping("/histoproduitStock/new")
    public String addHistoProduitStock(Model model) {
        HistoProduitStock histoProduitStock = new HistoProduitStock();
        model.addAttribute("histoproduitstock", histoProduitStock);
        model.addAttribute("pageTitle", "Créer un historique produit stock.");

        return "histoproduitstock_form";
    }

    @PostMapping("/histoproduitStock/save")
    public String saveHistoProduitStock(HistoProduitStock histoProduitStock, RedirectAttributes redirectAttributes) {
        try {
            histoProduitStockRepository.save(histoProduitStock);

            redirectAttributes.addFlashAttribute("message", "L'historique de produit stock a été enregistré avec succès !");
        } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
        }

        return "redirect:/histoproduitsstock";
    }

    @GetMapping("/histoproduitStock/{id}")
    public String editHistoProduitStock(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            HistoProduitStock histoProduitStock = histoProduitStockRepository.findById(id).get();

            model.addAttribute("histoproduitstock", histoProduitStock);
            model.addAttribute("pageTitle", "Modifier l'historique Frigo (ID: " + id + ")");

            return "histofrigo_form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());

            return "redirect:/histofrigos";
        }
    }

    @GetMapping("/histoproduitStock/delete/{id}")
    public String deleteProduitStockFrigo(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            histoProduitStockRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "L'historique Produit stock avec l'id=" + id + " a été supprimé avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/histofrigos";
    }


}
