package com.apys.learning.validator.register;

import com.apys.learning.domain.Adult;
import com.apys.learning.domain.Child;
import com.apys.learning.domain.register.CityRegisterResponse;
import com.apys.learning.domain.Person;
import com.apys.learning.exception.CityRegisterException;

public class FakeCityRegisterChecker implements CityRegisterChecker {
    public static final String GOOD_1 = "1000";
    public static final String GOOD_2 = "2000";
    public static final String BAD_1 = "1001";
    public static final String BAD_2 = "2001";
    public static final String ERROR_1 = "1002";
    public static final String ERROR_2 = "2002";
    public static final String ERROR_T_1 = "1003";
    public static final String ERROR_T_2 = "2003";
    public CityRegisterResponse checkPerson(Person person) throws CityRegisterException {
        CityRegisterResponse result = new CityRegisterResponse();
        if(person instanceof Adult) {
            Adult adult = (Adult) person;
            if(adult.getPassportSeria().equals(GOOD_1) || adult.getPassportSeria().equals(GOOD_2)) {
                result.setRegistered(true);
                result.setTemporal(false);
            }
            if(adult.getPassportSeria().equals(BAD_1) || adult.getPassportSeria().equals(BAD_2)) {
                result.setTemporal(false);
            }
            if(adult.getPassportSeria().equals(ERROR_1) || adult.getPassportSeria().equals(ERROR_2)) {
                CityRegisterException ex = new CityRegisterException("1", "GRN ERROR");
                throw ex;
            }
        }
        if(person instanceof Child) {
            result.setRegistered(true);
            result.setTemporal(true);
        }
        System.out.println(result);
        return result;
    }
}
