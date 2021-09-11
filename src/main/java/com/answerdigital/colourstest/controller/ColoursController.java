package com.answerdigital.colourstest.controller;

import com.answerdigital.colourstest.model.Colour;
import com.answerdigital.colourstest.repository.ColoursRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/Colours")
public class ColoursController {

    @Autowired
    private ColoursRepository coloursRepository;

    @GetMapping
    public ResponseEntity<List<Colour>> getColours() {
        return new ResponseEntity(coloursRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Colour> getColour(@PathVariable("id") long id) {
        if (coloursRepository.existsById(id)) {
            return new ResponseEntity(coloursRepository.findColourById(id), HttpStatus.OK);
        } else {
            System.out.println("COLOUR NOT FOUND");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public Colour createColour(@RequestBody Colour colour) {
        return coloursRepository.save(colour);
    }

}
