package ru.practicum.web.users.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.models.users.model.entities.UserEntity;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("select u from UserEntity as u where u.id in :ids")
    List<UserEntity> findByIds(@Param("ids") List<Long> ids, Pageable pageForUsers);
}
