package demoresttaco.data;

import demoresttaco.domain.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository <Ingredient,String> {
}
