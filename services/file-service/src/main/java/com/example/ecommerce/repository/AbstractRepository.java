package com.example.ecommerce.repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import java.io.Serializable;
@NoRepositoryBean
public interface AbstractRepository <ENTITY,ID extends Serializable> extends SliceBaseRepository<ENTITY,ID>, JpaSpecificationExecutor<ENTITY> {
}
