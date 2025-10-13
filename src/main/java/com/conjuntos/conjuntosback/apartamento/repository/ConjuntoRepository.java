package com.conjuntos.conjuntosback.apartamento.repository;

import com.conjuntos.conjuntosback.apartamento.entity.Conjunto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConjuntoRepository extends JpaRepository<Conjunto, Integer> {

    Optional<Conjunto> findByNombre(String nombre);

    @Query("SELECT c.nombre FROM Conjunto c ORDER BY c.nombre")
    List<String> findAllNombres();

    @Query("SELECT c FROM Conjunto c WHERE c.ciudad = ?1 ORDER BY c.nombre")
    List<Conjunto> findByCiudad(String ciudad);

    @Query("SELECT c FROM Conjunto c WHERE c.sector = ?1 ORDER BY c.nombre")
    List<Conjunto> findBySector(String sector);

    @Query("SELECT DISTINCT c.ciudad FROM Conjunto c WHERE c.ciudad IS NOT NULL ORDER BY c.ciudad")
    List<String> findDistinctCiudades();

    @Query("SELECT DISTINCT c.sector FROM Conjunto c WHERE c.sector IS NOT NULL ORDER BY c.sector")
    List<String> findDistinctSectores();

    @Query("SELECT DISTINCT c FROM Conjunto c LEFT JOIN FETCH c.apartamentos")
    List<Conjunto> findAllWithApartamentos();

    @Query("SELECT DISTINCT c FROM Conjunto c LEFT JOIN FETCH c.apartamentos WHERE c.ciudad = ?1 ORDER BY c.nombre")
    List<Conjunto> findByCiudadWithApartamentos(String ciudad);

    @Query("SELECT DISTINCT c FROM Conjunto c LEFT JOIN FETCH c.apartamentos WHERE c.sector = ?1 ORDER BY c.nombre")
    List<Conjunto> findBySectorWithApartamentos(String sector);
}