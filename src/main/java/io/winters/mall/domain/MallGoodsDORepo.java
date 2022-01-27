package io.winters.mall.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MallGoodsDORepo extends CrudRepository<MallGoodsDO, Long> {

//    List<MallGoodsDO> findAllByCreateTime

}
