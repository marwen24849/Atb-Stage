package fsb.ucar.Microservice.User.repository;


import fsb.ucar.Microservice.User.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    public Optional<UserEntity> findByUsername(String username);


}
