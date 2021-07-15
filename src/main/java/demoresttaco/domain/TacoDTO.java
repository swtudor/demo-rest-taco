package demoresttaco.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TacoDTO {
    String name;
    List<String> ingredientIds = new ArrayList<>();

    public Taco convertToTaco(List<Ingredient> ingredientList){
        Taco taco = new Taco();
        taco.setName(name);
        taco.setIngredients(ingredientList);
        return taco;
    }
}
