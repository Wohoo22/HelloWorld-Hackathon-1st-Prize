package com.hackathon.controller;

import com.hackathon.entity.Map;
import com.hackathon.entity.Response;
import com.hackathon.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/map")
public class MapController {
    @Autowired
    private MapService mapService;

    @RequestMapping(value = "/generate", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<Map> generateMap(@RequestParam int size) {
        Map map = mapService.generateMap(size);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @RequestMapping(value = "/check", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<Response> validateUserPoints(@RequestBody Map map) {
        if (map.getUserPoints().size() == 0) {
            return new ResponseEntity<>(new Response(), HttpStatus.BAD_REQUEST);
        }
        try {
            Response response = mapService.checkUserPath(map, map.getUserPoints(), map.getUsername());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Response(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/generate-big", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<Map> generateMap(@RequestParam int size, @RequestParam boolean hasMom) {
        Map map = mapService.generateBigMap(size, hasMom);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @RequestMapping(value = "/check-big", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> checkBig(@RequestBody Map map) {
        try {
            Response response = mapService.checkUserPathForBigMap(map, map.getUserPoints(), map.getUsername());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Response(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
