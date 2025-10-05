package com.beatport.scraper.backend.demo.controller;

import com.beatport.scraper.backend.demo.model.BeatportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.beatport.scraper.backend.demo.service.Track;

import java.util.List;

@RestController
@RequestMapping("/api/beatport")
@CrossOrigin(origins = "*")
public class BeatportController {

    private final BeatportService beatportService;

    public BeatportController(BeatportService beatportService) {
        this.beatportService = beatportService;
    }

    @GetMapping("/top100")
    public ResponseEntity<List<Track>> getTop100() {
        List<Track> tracks = beatportService.getTopTracks();
        return ResponseEntity.ok(tracks);
    }
}
