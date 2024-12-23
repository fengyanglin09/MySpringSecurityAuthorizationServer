package diy.mqml.datalayer.persistence.entity.user;

import diy.mqml.datalayer.persistence.config.DataRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLanId(String landId);

    default Optional<User> findSystemUser() {
        return findByLanId("system");
    }

    @Modifying
    @Query(
            value = """
                    INSERT INTO App_User_Access(app_user_id, access_date_time)
                    VALUES (:userId, :currentDate)
                    """,
            nativeQuery = true
    )
    int noteUserLogin(@Param("userId") Long userId,
                      @Param("currentDate") LocalDateTime currentDate);

}
