package com.conjuntos.conjuntosback.noticia.repository;

import com.conjuntos.conjuntosback.noticia.entity.Noticia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticiaRepository extends JpaRepository<Noticia, Long> {

    @Query("SELECT n FROM Noticia n JOIN FETCH n.categoria WHERE n.activa = true ORDER BY n.fechaPublicacion DESC")
    List<Noticia> findAllActiveWithCategoriaOrderByFechaDesc();

    @Query("SELECT n FROM Noticia n JOIN FETCH n.categoria WHERE n.activa = true AND n.categoria.id = :categoriaId ORDER BY n.fechaPublicacion DESC")
    List<Noticia> findByActivaTrueAndCategoriaIdOrderByFechaDesc(@Param("categoriaId") String categoriaId);

    @Query("SELECT n FROM Noticia n JOIN FETCH n.categoria WHERE n.activa = true AND n.prioridad = 'alta' ORDER BY n.fechaPublicacion DESC")
    List<Noticia> findByActivaTrueAndPrioridadAltaOrderByFechaDesc();
}