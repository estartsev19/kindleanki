package ru.estartsev.kindleanki.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.estartsev.kindleanki.entity.Word;


@Repository
public interface WordRepository extends CrudRepository<Word, String> {
}