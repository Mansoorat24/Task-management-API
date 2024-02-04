package com.ApiWiz.Task.Management.Repository;

import com.ApiWiz.Task.Management.Entities.TaskDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TaskDetailsRepository extends JpaRepository<TaskDetails,Long> {
}
