package ru.practicum.admin.users.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.admin.users.model.entities.UserEntity;

import java.util.List;

public interface AdminUserRepository extends JpaRepository<UserEntity, Long> {
    @Query("select u from UserEntity as u where u.id in :ids")
    List<UserEntity> findByIds(@Param("ids") List<Long> ids, Pageable pageForUsers);
}
