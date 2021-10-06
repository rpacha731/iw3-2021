package ar.edu.iua.iw3.modelo.persistencia;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.iua.iw3.modelo.ProductoDetalle;

public interface ProductoDetalleRepository extends JpaRepository<ProductoDetalle, Long>  {

}
