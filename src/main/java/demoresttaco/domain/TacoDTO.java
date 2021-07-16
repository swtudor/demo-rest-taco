package demoresttaco.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
public class TacoDTO {
    @NotBlank(message = "Must include name")
    String name;
    @NotNull
    @Size(min=2, message = "a minimum of ingredients makes a taco")
    List<String> ingredientIds = new ArrayList<>();

    public Taco convertToTaco(List<Ingredient> ingredientList){
        Taco taco = new Taco();
        taco.setName(name);
        taco.setIngredients(ingredientList);
        return taco;
    }
}
