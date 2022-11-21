package cnam.projettpr.controller;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import cnam.projettpr.entity.Categorie;
import cnam.projettpr.service.ICategorieService;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/tprapp")
public class TprController {

    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello Spring Boot";
    }


    @Resource
    ICategorieService categorieService;

    @GetMapping(value = "/categorieList")
    public List<Categorie> getCategories() {
        return categorieService.findAll();
    }

    @PostMapping(value = "/createCategorie")
    public void createCategorie(@RequestBody Categorie categorie) {
        categorieService.insertCategorie(categorie);
    }

    @PutMapping(value = "/updateCategorie")
    public void updateEmployee(@RequestBody Categorie categorie) {
        categorieService.updateCategorie(categorie);
    }

    @PutMapping(value = "/executeUpdateCategorie")
    public void executeUpdateEmployee(@RequestBody Categorie categorie) {
        categorieService.executeUpdateCategorie(categorie);
    }

    @DeleteMapping(value = "/deleteCategorieById")
    public void deleteEmployee(@RequestBody Categorie categorie) {
        categorieService.deleteCategorie(categorie);
    }


}
