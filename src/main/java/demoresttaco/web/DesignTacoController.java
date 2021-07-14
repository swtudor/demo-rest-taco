package demoresttaco.web;

import demoresttaco.data.TacoRepository;
import demoresttaco.domain.Taco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path="/design", produces = "application/json")
@CrossOrigin(origins = "*")
public class DesignTacoController {
    private TacoRepository tacoRepository;

    @Autowired
    public DesignTacoController(TacoRepository tacoRepository){
        this.tacoRepository=tacoRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private void postTaco(@RequestBody @Valid Taco taco){
       tacoRepository.save(taco);
    }
}
