package com.productivity.repository;

import com.productivity.model.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TasksRepository extends JpaRepository<Tasks,String> {

    //List<Tasks> findAllByUserName(String name);

    @Query(value = "select * from tasks where user_id_fk=?1",nativeQuery = true)
    List<Tasks> findAllByUser_id_fk(Integer id);
}
