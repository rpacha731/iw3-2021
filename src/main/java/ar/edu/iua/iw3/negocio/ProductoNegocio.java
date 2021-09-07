package ar.edu.iua.iw3.negocio;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.iua.iw3.modelo.Producto;
import ar.edu.iua.iw3.modelo.persistencia.ProductoRepository;
import ar.edu.iua.iw3.negocio.excepciones.DuplicadoException;
import ar.edu.iua.iw3.negocio.excepciones.EncontradoException;
import ar.edu.iua.iw3.negocio.excepciones.NegocioException;
import ar.edu.iua.iw3.negocio.excepciones.NoEncontradoException;

@Service
//@Configuration
public class ProductoNegocio implements IProductoNegocio {
	private Logger log = LoggerFactory.getLogger(ProductoNegocio.class);
	@Autowired
	private ProductoRepository productoDAO;

	@Override
	public List<Producto> listado() throws NegocioException {
		try {
			return productoDAO.findAll();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}
	}

	@Override
	public Producto agregar(Producto producto) throws NegocioException, EncontradoException, DuplicadoException {
		Optional<Producto> o;
		try {
			cargar(producto.getId());
			throw new EncontradoException("Ya existe un producto con id=" + producto.getId());
		} catch (NoEncontradoException e) {
			o = productoDAO.findByDescripcion(producto.getDescripcion());
			if (o.isPresent())
				throw new DuplicadoException("Ya existe un producto con el nombre = " + producto.getDescripcion());
			// log.info(productoDAO.findByDescripcion(producto.getDescripcion()).toString());
		}
		try {
			return productoDAO.save(producto);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}

	}

	@Override
	public Producto cargar(long id) throws NegocioException, NoEncontradoException {
		Optional<Producto> o;
		try {
			o = productoDAO.findById(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}
		if (!o.isPresent()) {
			throw new NoEncontradoException("No se encuentra el producto con id=" + id);
		}
		return o.get();

	}

	@Override
	public Producto modificar(Producto producto) throws NegocioException, NoEncontradoException, DuplicadoException {

		Producto temp = cargar(producto.getId()); // si no existe el producto sale por el NoEncontradoException
		// acá existe el producto
		Optional<Producto> o = productoDAO.findByDescripcion(producto.getDescripcion()); // encuentra un posible
																							// producto con la misma
																							// descripción
		if (o.isPresent() && temp.getId() != o.get().getId()) {
			throw new DuplicadoException("Ya existe otro producto con el nombre = " + producto.getDescripcion());
		}
		try {

			return productoDAO.save(producto);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}
	}

	@Override
	public void eliminar(long id) throws NegocioException, NoEncontradoException {
		cargar(id);

		try {
			productoDAO.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}
	}

	@Override
	public Producto cargarPorDescr(String descripcion) throws NegocioException, NoEncontradoException {
		Optional<Producto> o;
		try {
			o = productoDAO.findByDescripcion(descripcion);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}
		if (!o.isPresent()) {
			throw new NoEncontradoException("No se encuentra el producto con id=" + descripcion);
		}
		return o.get();
	}

	@Override
	public List<Producto> listadoPorPrecio(double precio) throws NegocioException, NoEncontradoException {
		try {
			List<Producto> aux = productoDAO.findByPrecio(precio);
			if (aux.isEmpty()) {
				throw new NoEncontradoException("No hay productos con el precio = " + precio);
			}
			return aux;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}

	}

	@Override
	public List<Producto> listadoPorPrecioBetween(double precio1, double precio2)
			throws NegocioException, NoEncontradoException {
		try {
			List<Producto> aux = productoDAO.findByPrecioBetween(precio1, precio2);
			if (aux.isEmpty()) {
				throw new NoEncontradoException("No hay productos entre los precios " + precio1 + " y " + precio2);
			}
			return aux;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}
	}

	@Override
	public List<Producto> listadoPorPrecioOrderByDescripcion(double precio)
			throws NegocioException {
		try {
			List<Producto> aux = productoDAO.findByPrecioOrderByDescripcion(precio);
			if (aux.isEmpty()) {
				throw new NoEncontradoException("No hay productos con el precio = ");
			}
			return aux;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}
	}

	// @Bean
	// public IProductoNegocio getProductoNegocio() {
	// return new ProductoNegocio();
	// }

}
