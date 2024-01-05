package org.example.controller;

import org.example.model.Countries;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/countries")
public class CountriesController {
    @GetMapping("/all")
    public Countries[] getAllCountries() {
        return Countries.values();
    }
}
