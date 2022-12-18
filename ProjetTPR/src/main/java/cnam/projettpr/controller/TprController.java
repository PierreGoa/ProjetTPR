package cnam.projettpr.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import cnam.projettpr.entity.Frigo;
import cnam.projettpr.repository.FrigoRepository;

import cnam.projettpr.entity.Categorie;
import cnam.projettpr.repository.CategorieRepository;

import cnam.projettpr.entity.ProduitRef;
import cnam.projettpr.repository.ProduitRefRepository;

import cnam.projettpr.entity.ProduitStock;
import cnam.projettpr.repository.ProduitStockRepository;

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
            frigoRepository.save(frigo);

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
            produitStockRepository.findAll().forEach(produitsStock::add);

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
            produitStockRepository.save(produitStock);

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

}
