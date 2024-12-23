package diy.mqml.actuatorlayer.controller;


import diy.mqml.actuatorlayer.model.User;
import diy.mqml.actuatorlayer.service.UserEventService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;


@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UsersController {

    private UserEventService userEventService;

    @GetMapping
    public ResponseEntity<Iterable<User>> getAll(){
        return ResponseEntity.ok(this.userEventService.getAllUsers());
    }

//    @GetMapping("/{email}")
//    public ResponseEntity<User> findUserById(@PathVariable String email) throws Throwable {
//        return ResponseEntity.of(this.userService.findUserByEmail(email));
//    }

    @RequestMapping(method = {RequestMethod.POST,RequestMethod.PUT})
    public ResponseEntity<User> save(@RequestBody User user){
       User userResult = this.userEventService.saveUpdateUser(user);

       URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{email}")
                .buildAndExpand(userResult.getEmail())
                .toUri();
       return ResponseEntity.created(location).body(userResult);
    }

//    @DeleteMapping("/{email}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void save(@PathVariable String email){
//        this.userService.removeUserByEmail(email);
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }
}

