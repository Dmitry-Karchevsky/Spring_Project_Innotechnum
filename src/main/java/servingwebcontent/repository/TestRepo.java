package servingwebcontent.repository;

import org.springframework.data.repository.CrudRepository;
import servingwebcontent.domain.Test;

import java.util.List;

public interface TestRepo extends CrudRepository<Test, Long> {
    List<Test> findByName(String name);
}
