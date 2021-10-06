package ar.edu.iua.iw3.modelo.persistencia;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.iua.iw3.modelo.Proveedor;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long>  {

}
