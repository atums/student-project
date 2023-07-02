package com.apys.learning.validator.register;

import com.apys.learning.domain.register.CityRegisterResponse;
import com.apys.learning.domain.Person;
import com.apys.learning.exception.CityRegisterException;

public interface CityRegisterChecker {
    CityRegisterResponse checkPerson(Person person) throws CityRegisterException;
}
