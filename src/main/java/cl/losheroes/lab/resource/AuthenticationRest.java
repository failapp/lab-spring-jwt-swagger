package cl.losheroes.lab.resource;

import cl.losheroes.lab.persistence.dto.CredentialDto;
import cl.losheroes.lab.security.JwtResponse;
import cl.losheroes.lab.service.AuthenticationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthenticationRest {

    @Autowired
    private AuthenticationService authenticationService;

    @ApiOperation("Autenticacion para pruebas: usuario: lab password: lab")
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticate(@Valid @RequestBody CredentialDto credentialDto) {
        return ResponseEntity.ok(authenticationService.getToken(credentialDto));
    }


}
