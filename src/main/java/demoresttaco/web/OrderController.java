package demoresttaco.web;

import demoresttaco.data.OrderRepository;
import demoresttaco.data.TacoRepository;
import demoresttaco.data.UserRepository;
import demoresttaco.domain.Order;
import demoresttaco.domain.OrderDTO;
import demoresttaco.domain.Taco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    // GET all orders available
    @GetMapping()
    @ResponseBody
    Iterable<Order> all(){
        return orderRepository.findAll();
    }

    // add endpoint to GET an order by orderId
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Order> findByOrderId(@PathVariable Long id){
        try{
            Optional<Order> optOrder = orderRepository.findById(id);
            if(optOrder.isPresent()){
                return new ResponseEntity<>(optOrder.get(), HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }catch(EmptyResultDataAccessException e) {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // still needed:
    // add endpoint to GET orders by userId
    // add endpoint to GET /recent orders

    // add PUT endpoint to update existing order
    @PutMapping(path="/{orderId}", consumes="application/json")
    public Order putOrder(@RequestBody OrderDTO orderDTO) {
        Optional<Order> order = orderRepository.findById(orderDTO.getUserId());
        if(order.isPresent()) {
            Order update = order.get();
            // updateOrder method is new logic on the OrderDTO
            orderDTO.updateOrder(update, tacoRepository);
            return orderRepository.save(update);
        } else
            return null;
    }

    // add endpoint to DELETE order from the table
    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable("orderId") Long orderId) {
        try {
            orderRepository.deleteById(orderId);
            return ResponseEntity.ok().body("Succesfully deleted order number " + orderId);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.badRequest().body("Failed to delete order number " + orderId +"/n" + e.getMessage());
        }
    }

}
