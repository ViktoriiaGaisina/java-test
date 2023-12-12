package cz.fio.controller;

import cz.fio.service.StoreContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/storeContact")
public class StoreContactController {
    private final StoreContactService storeContactService;

    public StoreContactController(StoreContactService storeContactService) {
        this.storeContactService = storeContactService;
    }

    @PostMapping("/save")
    public ResponseEntity<Void> storeContact(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email) {
        storeContactService.saveContact(firstName, lastName, email);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
