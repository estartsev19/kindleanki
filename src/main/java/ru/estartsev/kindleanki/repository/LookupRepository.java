package ru.estartsev.kindleanki.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.estartsev.kindleanki.entity.Lookup;

@Repository
public interface LookupRepository  extends CrudRepository<Lookup, String> {

}