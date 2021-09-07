package ar.edu.iua.iw3.negocio;

import java.util.List;

import ar.edu.iua.iw3.modelo.Producto;
import ar.edu.iua.iw3.negocio.excepciones.EncontradoException;
import ar.edu.iua.iw3.negocio.excepciones.NegocioException;
import ar.edu.iua.iw3.negocio.excepciones.NoEncontradoException;

public interface IProductoNegocio {
	public List<Producto> listado() throws NegocioException;

	public Producto cargar(long id) throws NegocioException, NoEncontradoException;

	public Producto agregar(Producto producto) throws NegocioException, EncontradoException;

	public Producto modificar(Producto producto) throws NegocioException, NoEncontradoException;

	public void eliminar(long id) throws NegocioException, NoEncontradoException;
}
