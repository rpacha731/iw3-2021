package ar.edu.iua.iw3.web;

import java.sql.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.iua.iw3.modelo.Producto;
import ar.edu.iua.iw3.negocio.IProductoNegocio;
import ar.edu.iua.iw3.negocio.excepciones.DuplicadoException;
import ar.edu.iua.iw3.negocio.excepciones.EncontradoException;
import ar.edu.iua.iw3.negocio.excepciones.NegocioException;
import ar.edu.iua.iw3.negocio.excepciones.NoEncontradoException;

@RestController
public class ProductosRestController {
	private Logger log = LoggerFactory.getLogger(ProductosRestController.class);
	@Autowired
	private IProductoNegocio productoNegocio;

	// curl http://localhost:8080/productos
	
	@GetMapping(value="/productos")
	public ResponseEntity<List<Producto>> listado() {
		try {
			return new ResponseEntity<List<Producto>>(productoNegocio.listado(), HttpStatus.OK);
		} catch (NegocioException e) {
			return new ResponseEntity<List<Producto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// curl http://localhost:8080/productos/1
	
	@GetMapping(value="/productos/{id}")
	public ResponseEntity<Producto> cargar(@PathVariable("id") long id) {
		try {
			return new ResponseEntity<Producto>(productoNegocio.cargar(id), HttpStatus.OK);
		} catch (NegocioException e) {
			return new ResponseEntity<Producto>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NoEncontradoException e) {
			return new ResponseEntity<Producto>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	@GetMapping(value="/productos/descripcion")
	public ResponseEntity<Producto> cargarDescripcion(@RequestParam("descripcion") String descrip) {
		try {
			return new ResponseEntity<Producto>(productoNegocio.cargarPorDescr(descrip), HttpStatus.OK);
		} catch (NegocioException e) {
			return new ResponseEntity<Producto>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NoEncontradoException e) {
			return new ResponseEntity<Producto>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value="/productos/precio")
	public ResponseEntity<List<Producto>> listadoPorPrecio(@RequestParam("precio") double precio) {
		try {
			return new ResponseEntity<List<Producto>>(productoNegocio.listadoPorPrecio(precio), HttpStatus.OK);
		} catch (NegocioException e) {
			return new ResponseEntity<List<Producto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NoEncontradoException e) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("message", e.getMessage());
			return new ResponseEntity<List<Producto>>(responseHeaders, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value="/productos/precioBetween")
	public ResponseEntity<List<Producto>> listadoPorPrecioBetween(@RequestParam("precio1") double precio1, @RequestParam("precio2") double precio2) {
		try {
			return new ResponseEntity<List<Producto>>(productoNegocio.listadoPorPrecioBetween(precio1, precio2), HttpStatus.OK);
		} catch (NegocioException e) {
			return new ResponseEntity<List<Producto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NoEncontradoException e) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("message", e.getMessage());
			return new ResponseEntity<List<Producto>>(responseHeaders, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value="/productos/precioOrderByDescripcion")
	public ResponseEntity<List<Producto>> listadoPorPrecioOrdenadoPorDescripcion(@RequestParam("precio") double precio) {
		try {
			return new ResponseEntity<List<Producto>>(productoNegocio.listadoPorPrecioOrderByDescripcion(precio), HttpStatus.OK);
		} catch (NoEncontradoException ex) {
			log.info("entre ******");
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("message", ex.getMessage());
			return new ResponseEntity<List<Producto>>(responseHeaders, HttpStatus.NOT_FOUND);
			
		} catch (NegocioException e) {
			return new ResponseEntity<List<Producto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value="/productos/fechaVencimientoNoNull")
	public ResponseEntity<List<Producto>> listadoPorFechaVencimientoNoNull() {
		try {
			return new ResponseEntity<List<Producto>>(productoNegocio.listadoPorFechaVencimientoNoNull(), HttpStatus.OK);
		} catch (NoEncontradoException ex) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("message", ex.getMessage());
			return new ResponseEntity<List<Producto>>(responseHeaders, HttpStatus.NOT_FOUND);
			
		} catch (NegocioException e) {
			return new ResponseEntity<List<Producto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@GetMapping(value="/productos/fechaVencimientoDespues")
	public ResponseEntity<List<Producto>> listadoDespuesFechaVencimiento(@RequestParam("fechaVenci") String fechaVenci) {
		try {
			return new ResponseEntity<List<Producto>>(productoNegocio.listadoDespuesFechaVencimiento(Date.valueOf(fechaVenci)), HttpStatus.OK);
		} catch (NoEncontradoException ex) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("message", ex.getMessage());
			return new ResponseEntity<List<Producto>>(responseHeaders, HttpStatus.NOT_FOUND);
			
		} catch (NegocioException e) {
			return new ResponseEntity<List<Producto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value="/productos/dosPorVencer")
	public ResponseEntity<List<Producto>> listadoDosPorVencer() {
		try {
			return new ResponseEntity<List<Producto>>(productoNegocio.listadoPrimerosDosPorVencer(), HttpStatus.OK);
		} catch (NoEncontradoException ex) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("message", ex.getMessage());
			return new ResponseEntity<List<Producto>>(responseHeaders, HttpStatus.NOT_FOUND);
			
		} catch (NegocioException e) {
			return new ResponseEntity<List<Producto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//curl -X POST  http://localhost:8080/productos -H "Content-Type: application/json" -d '{"id":2,"descripcion":"Leche","enStock":false,"precio":104.7,"rubro":{"id":1,"rubro":"Alimentos"},"descripcionExtendida":"Se trata de leche larga vida"}'
	
	// 1° --> org.springframework.dao.DataIntegrityViolationException
	// 2° --> causado por  org.hibernate.exception.ConstraintViolationException
	// 3° --> causado por  java.sql.SQLIntegrityConstraintViolationException
	@PostMapping(value="/productos")
	public ResponseEntity<String> agregar(@RequestBody Producto producto) {
		try {
			Producto respuesta=productoNegocio.agregar(producto);
			HttpHeaders responseHeaders=new HttpHeaders();
			responseHeaders.set("location", "/productos/"+respuesta.getId());
			return new ResponseEntity<String>(responseHeaders, HttpStatus.CREATED);
		} catch (NegocioException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (EncontradoException e) {
			return new ResponseEntity<String>(HttpStatus.FOUND);
		} catch (DuplicadoException e) {
			return new ResponseEntity<String>("Ya se encuentra un producto con ese nombre", HttpStatus.CONFLICT);
		}
	}
	
	// curl -X PUT  http://localhost:8080/productos -H "Content-Type: application/json" -d '{"id":2,"descripcion":"Leche","enStock":false,"precio":55,"rubro":{"id":1,"rubro":"Alimentos"},"descripcionExtendida":"Se trata de leche larga vida"}' -v
	@PutMapping(value="/productos")
	public ResponseEntity<String> modificar(@RequestBody Producto producto) {
		try {
			productoNegocio.modificar(producto);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (NegocioException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NoEncontradoException e) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		} catch (DuplicadoException e) {
			return new ResponseEntity<String>("Ya se encuentra un producto con ese nombre", HttpStatus.CONFLICT);
		}
	}
	
	// curl -X DELETE http://localhost:8080/productos/11 -v
	
	@DeleteMapping(value="/productos/{id}")
	public ResponseEntity<String> eliminar(@PathVariable("id") long id) {
		try {
			productoNegocio.eliminar(id);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (NegocioException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NoEncontradoException e) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
	}
}
