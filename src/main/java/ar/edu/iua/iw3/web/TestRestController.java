package ar.edu.iua.iw3.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestRestController {

	@GetMapping(value = "")
	public ResponseEntity<String> dummy1() {

		return new ResponseEntity<String>("Hola desde el servicio Dummy1", HttpStatus.OK);

	}

}
