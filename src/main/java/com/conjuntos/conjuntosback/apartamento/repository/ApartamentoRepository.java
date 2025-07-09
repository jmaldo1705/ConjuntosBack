package com.conjuntos.conjuntosback.apartamento.repository;

import com.conjuntos.conjuntosback.apartamento.entity.Apartamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ApartamentoRepository extends JpaRepository<Apartamento, Long> {

    @Query("SELECT a FROM Apartamento a WHERE " +
            "(:tipo IS NULL OR a.tipo = :tipo) AND " +
            "(:conjunto IS NULL OR LOWER(a.conjunto) LIKE LOWER(CONCAT('%', :conjunto, '%'))) AND " +
            "(:habitaciones IS NULL OR a.habitaciones = :habitaciones) AND " +
            "(:precioMin IS NULL OR a.precio >= :precioMin) AND " +
            "(:precioMax IS NULL OR a.precio <= :precioMax) AND " +
            "(:disponible IS NULL OR a.disponible = :disponible) AND " +
            "(:destacado IS NULL OR a.destacado = :destacado) AND " +
            "(:busqueda IS NULL OR LOWER(a.titulo) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
            "LOWER(a.descripcion) LIKE LOWER(CONCAT('%', :busqueda, '%'))) " +
            "ORDER BY a.destacado DESC, a.fechaCreacion DESC")
    Page<Apartamento> findApartamentosConFiltros(
            @Param("tipo") Apartamento.TipoApartamento tipo,
            @Param("conjunto") String conjunto,
            @Param("habitaciones") Integer habitaciones,
            @Param("precioMin") BigDecimal precioMin,
            @Param("precioMax") BigDecimal precioMax,
            @Param("disponible") Boolean disponible,
            @Param("destacado") Boolean destacado,
            @Param("busqueda") String busqueda,
            Pageable pageable
    );

    @Query("SELECT DISTINCT a.conjunto FROM Apartamento a ORDER BY a.conjunto")
    List<String> findAllConjuntos();

    @Query("SELECT COUNT(a) FROM Apartamento a WHERE a.tipo = :tipo")
    Long countByTipo(@Param("tipo") Apartamento.TipoApartamento tipo);

    Long countByDisponible(Boolean disponible);

    Long countByDestacado(Boolean destacado);
}