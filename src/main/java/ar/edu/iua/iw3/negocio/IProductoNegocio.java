package ar.edu.iua.iw3.negocio;

import java.sql.Date;
import java.util.List;

import ar.edu.iua.iw3.modelo.Producto;
import ar.edu.iua.iw3.negocio.excepciones.DuplicadoException;
import ar.edu.iua.iw3.negocio.excepciones.EncontradoException;
import ar.edu.iua.iw3.negocio.excepciones.NegocioException;
import ar.edu.iua.iw3.negocio.excepciones.NoEncontradoException;

public interface IProductoNegocio {
	
	public List<Producto> listado() throws NegocioException;

	public Producto cargar(long id) throws NegocioException, NoEncontradoException;
	
	public Producto cargarPorDescr(String descripcion) throws NegocioException, NoEncontradoException;
	
	public List<Producto> listadoPorPrecio(double precio) throws NegocioException, NoEncontradoException;
	
	public List<Producto> listadoPorPrecioBetween(double precio1, double precio2) throws NegocioException, NoEncontradoException;
	
	public List<Producto> listadoPorPrecioOrderByDescripcion(double precio) throws NegocioException, NoEncontradoException;
	
	public List<Producto> listadoPorFechaVencimientoNoNull() throws NegocioException, NoEncontradoException;
	
	public List<Producto> listadoDespuesFechaVencimiento(Date fechaVenci) throws NegocioException, NoEncontradoException;
	
	public List<Producto> listadoPrimerosDosPorVencer() throws NegocioException, NoEncontradoException;

	public Producto agregar(Producto producto) throws NegocioException, EncontradoException, DuplicadoException;

	public Producto modificar(Producto producto) throws NegocioException, NoEncontradoException, DuplicadoException;

	public void eliminar(long id) throws NegocioException, NoEncontradoException;
	
}
