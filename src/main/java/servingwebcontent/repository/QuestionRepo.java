package servingwebcontent.repository;

import org.springframework.data.repository.CrudRepository;
import servingwebcontent.domain.Question;
import servingwebcontent.domain.Test;

import java.util.List;

public interface QuestionRepo extends CrudRepository<Question, Long> {
    Question findByQuestionName(String questionName);
    List<Question> findByTest(Test test);
}
