package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.Movie;
import com.app.service.IMovieService;

@RestController
@RequestMapping("movie")
public class MovieController {

	@Autowired
	private IMovieService service;

	@GetMapping("show")
	public String show() {
		return "Hellow , This is Product service";
	}

	@PostMapping("/upload")
	public ResponseEntity<String> uploadMovie(@RequestBody Movie movie) {
		ResponseEntity<String> resp = null;
		try {
			service.saveMovie(movie);
			resp = new ResponseEntity<String>("Movie uploaded succesfully",HttpStatus.OK);
		} catch (Exception e) {
			resp = new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		return resp;
	}
	@SuppressWarnings("unchecked")
	@GetMapping("get/{code}")
	public ResponseEntity<String> getMovieByCode(@PathVariable("code") String movieCode) {
		ResponseEntity<?> resp = null;
		Movie movie = service.findMovie(movieCode);
		try {
			if(movie!=null) {
				resp = new ResponseEntity<Movie>(movie,HttpStatus.OK);
			}
			else {
				resp = new ResponseEntity<String>("Invalid movie code...",HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (ResponseEntity<String>) resp;
	}

	@GetMapping("allmovies")
	public ResponseEntity<?> getAllMovies() {
		ResponseEntity<?> resp = null;
		try {
			List<Movie> list = service.getAllMovies();
			if(list!=null && !list.isEmpty())
				resp = new ResponseEntity<List<Movie>>(list,HttpStatus.OK);	
		} catch (Exception e) {
			resp = new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		return resp;
	}
}
