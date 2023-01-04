package com.example.firstporject.repository;

import com.example.firstporject.entity.Atricle;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ArticleRepository extends CrudRepository<Atricle,Long>{
    @Override
    ArrayList<Atricle> findAll();
}
