package com.apys.learning.validator.register;

import com.apys.learning.config.Config;
import com.apys.learning.domain.register.CityRegisterRequest;
import com.apys.learning.domain.register.CityRegisterResponse;
import com.apys.learning.domain.Person;
import com.apys.learning.exception.CityRegisterException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

public class RealCityRegisterChecker implements CityRegisterChecker {
    public CityRegisterResponse checkPerson(Person person) throws CityRegisterException {

        try {
            CityRegisterRequest request = new CityRegisterRequest(person);

            Client client = ClientBuilder.newClient();
            CityRegisterResponse response = client.target(Config.getProperties(Config.CR_URL))
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(request, MediaType.APPLICATION_JSON))
                    .readEntity(CityRegisterResponse.class);

            return response;
        } catch (Exception e) {
            throw new CityRegisterException("", e.getMessage(), e);
        }
    }
}
