package cl.losheroes.lab;

import cl.losheroes.lab.persistence.dto.CustomerDto;
import cl.losheroes.lab.persistence.entity.Address;
import cl.losheroes.lab.shared.Util;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest
public class UtilTest {

    @Test
    public void trimBracketsTest() {
        String str = "[aaa, bbb]";
        str = Util.trimBrackets(str);
        assertEquals("aaa, bbb", str);
    }


    @Test
    public void passwordEncoderTest() {
        String pass = new BCryptPasswordEncoder().encode("admin");
        log.info("[x] pass: {}", pass);
        assertThat(pass.length()).isGreaterThan(0);
    }

    @Test
    public void compareObjestsTest() {

        String streetName = "street test";
        String streetNumber = "#101";
        String state = "RM";
        String city = "Santiago";
        String country = "Chile";

        Address address1 = new Address(streetName, streetNumber, state, city, country);
        //Address address2 = new Address(streetName, streetNumber, state, city, country);
        Address address2 = new Address(streetName, "#102", state, city, country);
        //Address address2 = new Address("Av. Norte", "#102", state, city, country);

        //Comparator<Address> compareByStreetName = Comparator.comparing(Address::getStreetName);
        boolean isEquals = address2.equals(address1);

        //assertThat(isEquals).isTrue();
        assertThat(isEquals).isFalse();

    }

}
