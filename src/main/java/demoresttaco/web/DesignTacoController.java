package demoresttaco.web;

import demoresttaco.data.IngredientRepository;
import demoresttaco.data.TacoRepository;
import demoresttaco.domain.Ingredient;
import demoresttaco.domain.Taco;
import demoresttaco.domain.TacoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

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
    private Taco getTaco(@PathVariable Long id){
        return tacoRepository.findById(id).orElse(new Taco());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private void postTaco(@RequestBody TacoDTO tacoDTO){
        List<Ingredient> tacoIngredients = new ArrayList<>();
        tacoDTO.getIngredientIds().forEach(x-> tacoIngredients.add(ingredientRepository.findById(x).orElse(null)));
        Taco taco = tacoDTO.convertToTaco(tacoIngredients);
        tacoRepository.save(taco);
    }


}
