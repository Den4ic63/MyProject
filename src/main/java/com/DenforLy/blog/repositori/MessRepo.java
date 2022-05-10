package com.DenforLy.blog.repositori;

import com.DenforLy.blog.model.Messages;
import org.springframework.data.repository.CrudRepository;

public interface MessRepo  extends CrudRepository<Messages,Long> {

}
