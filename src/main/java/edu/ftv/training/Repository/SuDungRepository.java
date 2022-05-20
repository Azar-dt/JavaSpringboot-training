package edu.ftv.training.Repository;

import edu.ftv.training.Model.SuDung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SuDungRepository extends JpaRepository<SuDung, Integer>, JpaSpecificationExecutor<SuDung> {
}
