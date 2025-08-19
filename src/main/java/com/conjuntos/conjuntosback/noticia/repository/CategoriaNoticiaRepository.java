package com.conjuntos.conjuntosback.noticia.repository;

import com.conjuntos.conjuntosback.noticia.entity.CategoriaNoticia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaNoticiaRepository extends JpaRepository<CategoriaNoticia, String> {

    List<CategoriaNoticia> findByActivaTrueOrderByOrdenAsc();

    CategoriaNoticia findByIdAndActivaTrue(String id);
}