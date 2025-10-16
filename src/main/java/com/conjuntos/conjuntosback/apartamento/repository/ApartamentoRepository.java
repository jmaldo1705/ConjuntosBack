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

    @Query("SELECT a FROM Apartamento a JOIN FETCH a.conjuntoResidencial WHERE " +
            "(:tipo IS NULL OR a.tipo = :tipo) AND " +
            "(:conjuntoNombre IS NULL OR a.conjuntoResidencial.nombre = :conjuntoNombre) AND " +
            "(:ciudad IS NULL OR a.conjuntoResidencial.ciudad = :ciudad) AND " +
            "(:habitaciones IS NULL OR a.habitaciones = :habitaciones) AND " +
            "(:precioMin IS NULL OR a.precio >= :precioMin) AND " +
            "(:precioMax IS NULL OR a.precio <= :precioMax) AND " +
            "(:disponible IS NULL OR a.disponible = :disponible) AND " +
            "(:destacado IS NULL OR a.destacado = :destacado) AND " +
            "(:busqueda IS NULL OR LOWER(a.titulo) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
            "LOWER(a.descripcion) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
            "LOWER(a.conjuntoResidencial.nombre) LIKE LOWER(CONCAT('%', :busqueda, '%')))")
    Page<Apartamento> findByFiltros(@Param("tipo") String tipo,
                                    @Param("conjuntoNombre") String conjuntoNombre,
                                    @Param("ciudad") String ciudad,
                                    @Param("habitaciones") Integer habitaciones,
                                    @Param("precioMin") BigDecimal precioMin,
                                    @Param("precioMax") BigDecimal precioMax,
                                    @Param("disponible") Boolean disponible,
                                    @Param("destacado") Boolean destacado,
                                    @Param("busqueda") String busqueda,
                                    Pageable pageable);

    @Query("SELECT DISTINCT c.nombre FROM Apartamento a JOIN a.conjuntoResidencial c ORDER BY c.nombre")
    List<String> findDistinctConjuntos();
    
    @Query("SELECT DISTINCT c.ciudad FROM Apartamento a JOIN a.conjuntoResidencial c WHERE c.ciudad IS NOT NULL ORDER BY c.ciudad")
    List<String> findDistinctCiudades();

    Long countByTipo(Apartamento.TipoApartamento tipo);

    Long countByDisponibleTrue();

    Long countByDestacadoTrue();

    @Query("SELECT AVG(a.precio) FROM Apartamento a WHERE a.disponible = true")
    BigDecimal findAveragePrecio();
}