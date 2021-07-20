package demoresttaco.domain;

import lombok.Data;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
// this can go on the domain object OR on the repository interface
@RepositoryRestResource(collectionResourceRel = "taco", path="taco")
public class Taco {
    private String name;

    @ManyToMany(targetEntity = Ingredient.class)
    private List<Ingredient> ingredients;

    public void addIngredient(Ingredient ingredient){
        this.ingredients.add(ingredient);
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private Date createdAt;

    @PrePersist
    void createdAt(){
        this.createdAt = new Date();
    }

}
