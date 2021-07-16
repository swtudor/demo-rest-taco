package demoresttaco.web;

import demoresttaco.data.IngredientRepository;
import demoresttaco.data.TacoRepository;
import demoresttaco.domain.Ingredient;
import demoresttaco.domain.Taco;
import demoresttaco.domain.TacoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path="/design", produces = "application/json")
@CrossOrigin(origins = "*")
public class DesignTacoController {
    private TacoRepository tacoRepository;
    private IngredientRepository ingredientRepository;

    @Autowired
    public DesignTacoController(TacoRepository tacoRepository, IngredientRepository ingredientRepository){
        this.tacoRepository=tacoRepository;
        this.ingredientRepository=ingredientRepository;
    }

    @GetMapping(path="/{id}", produces = "application/json")
    @ResponseBody
    private Taco getTaco(@PathVariable Long id){
        return tacoRepository.findById(id).orElse(new Taco());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    private Taco postTaco(@RequestBody TacoDTO tacoDTO){
        // declare a list to hold ingredients
        List<Ingredient> tacoIngredients = new ArrayList<>();
        // take the ids from teh ingredients and search the ingredients table by id, add the found ingredient objects to the list
        tacoDTO.getIngredientIds().forEach(x-> tacoIngredients.add(ingredientRepository.findById(x).orElse(null)));
        // pass the ingredients list into our conversion method and return a new taco object
        Taco taco = tacoDTO.convertToTaco(tacoIngredients);
        // save that new taco object in the SQL Taco table
        tacoRepository.save(taco);
        // return the taco to the front end
        return taco;
    }


}
