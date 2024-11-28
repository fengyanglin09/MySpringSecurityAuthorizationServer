package diy.mqml.datalayer.persistence.entity.user;

import diy.mqml.datalayer.persistence.config.DataRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLanId(String landId);

}
