package demoresttaco.data;

import demoresttaco.domain.Taco;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TacoRepository extends PagingAndSortingRepository<Taco,Long> {
}
