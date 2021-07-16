package demoresttaco.web;

import demoresttaco.data.OrderRepository;
import demoresttaco.data.TacoRepository;
import demoresttaco.data.UserRepository;
import demoresttaco.domain.Order;
import demoresttaco.domain.OrderDTO;
import demoresttaco.domain.Taco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(path="/order", produces ="application/json")
@CrossOrigin(origins = "*")
public class OrderController {
    private OrderRepository orderRepository;
    private TacoRepository tacoRepository;
    private UserRepository userRepository;

    @Autowired
    public OrderController(TacoRepository tacoRepository, OrderRepository orderRepository, UserRepository userRepository){
        this.tacoRepository = tacoRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    // return Order object to user
    @PostMapping (consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Order postNewOrder(@RequestBody OrderDTO dto){
        // receives an OrderDTO
        ArrayList<Taco> orderTacos = new ArrayList<>();
        // finds list of tacos by id
        dto.getTacoIds().forEach(
                tacoId -> orderTacos.add(tacoRepository.findById(tacoId).orElse(null))
        );
        // creates a new Order object and adds to taco list and dtoOrder to object
        Order order = new Order(dto.getName(), dto.getStreet(), dto.getCity(), dto.getState(),
                dto.getZip(), dto.getCcNumber(), dto.getCcExpiration(), dto.getCcCVV(), orderTacos);

        // finds user by id and adds to an order object
        order.setUser(userRepository.findById(dto.getUserId()).orElse(null));

        // insert new order object into Taco_Order table
        return orderRepository.save(order);
    }


}
