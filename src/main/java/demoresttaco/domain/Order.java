package demoresttaco.domain;

import lombok.Data;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "Taco_order")
//put in place for now, use later
@RestResource(rel = "orders", path = "orders")
public class Order {

    private String name;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String ccNumber;
    private String ccExpiration;
    private String ccCVV;
    private Date createdAt;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToMany(targetEntity = Taco.class)
    private List<Taco> tacos = new ArrayList<>();

    public Order(){}

    public Order(String name, String street, String city, String state, String zip,
                 String ccNumber, String ccExpiration, String ccCVV, List<Taco> tacos) {
        this.name = name;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.ccNumber = ccNumber;
        this.ccExpiration = ccExpiration;
        this.ccCVV = ccCVV;
        this.tacos = tacos;

    }

    public void addTaco(Taco taco) {
        this.tacos.add(taco);
    }

    @PrePersist
    void createdAt() {
        this.createdAt = new Date();
    }
}
