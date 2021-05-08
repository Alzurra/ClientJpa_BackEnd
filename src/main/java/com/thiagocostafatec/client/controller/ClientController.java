package com.thiagocostafatec.client.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thiagocostafatec.client.model.ClientModel;
import com.thiagocostafatec.client.repository.ClientRepository;

@RestController
@RequestMapping(value = "/clients")
public class ClientController {

	@Autowired
	ClientRepository repository;
	
	/*Método para retornar todos os Clients cadastrados no banco de dados
	@GetMapping(value = "/get")
	public List<ClientModel> find(){
	   return repository.findAll();
	}*/
	
	/*Método para salvar um Client novo no bando de dados (Create)
	@PostMapping
	public ClientModel create(@RequestBody ClientModel client){
	   return repository.save(client);
	}*/

	@GetMapping
	public ResponseEntity<List<ClientModel>> findAll() {
		List<ClientModel> result = repository.findAll();
		return ResponseEntity.ok(result);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> findById(@PathVariable long id) {
		return repository.findById(id).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping(value = "/pages")
	public ResponseEntity<Page<ClientModel>> findAll(Pageable pageable) {
		Page<ClientModel> result = repository.findAll(pageable);
		return ResponseEntity.ok(result);
	}

	@PostMapping("/cadastrar")
	public ResponseEntity<ClientModel> post(@RequestBody ClientModel Client) {

		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(Client));
		} catch (Exception exception) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable long id) {
		return repository.findById(id).map(record -> {
			repository.deleteById(id);
			return ResponseEntity.ok().build();
		}).orElse(ResponseEntity.notFound().build());
	}

	@PutMapping(value = "/update/{id}")
	public ResponseEntity<ClientModel> update(@PathVariable("id") long id, @RequestBody ClientModel client) {
		return repository.findById(id).map(record -> {
			record.setName(client.getName());
			record.setEmail(client.getEmail());
			record.setSalary(client.getSalary());
			ClientModel updated = repository.save(record);
			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}
}
