package ru.popov.christmas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.popov.christmas.service.CardService;

@RestController
@RequestMapping("/api/card")
public class CardController {

    @Autowired
    private CardService cardService;


}
