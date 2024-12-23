package diy.mqml.datalayer.persistence.config;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.io.Serializable;

@NoRepositoryBean
public interface DataRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, QueryByExampleExecutor<T>, QuerydslPredicateExecutor<T> {

}
