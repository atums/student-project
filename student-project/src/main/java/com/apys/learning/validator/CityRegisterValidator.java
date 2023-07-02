package com.apys.learning.validator;

import com.apys.learning.domain.Person;
import com.apys.learning.domain.register.AnswerCityRegister;
import com.apys.learning.domain.Child;
import com.apys.learning.domain.register.AnswerCityRegisterItem;
import com.apys.learning.domain.register.CityRegisterResponse;
import com.apys.learning.domain.StudentOrder;
import com.apys.learning.exception.CityRegisterException;
import com.apys.learning.validator.register.CityRegisterChecker;
import com.apys.learning.validator.register.RealCityRegisterChecker;


public class CityRegisterValidator {
    public static final String IN_CODE = "NO_GRN";
    public String hostName;
    protected int port;
    private String login;
    String password;
    private CityRegisterChecker pesonChecker;

    public CityRegisterValidator() {
//        pesonChecker = new RealCityRegisterChecker();
        pesonChecker = new RealCityRegisterChecker();
    }

    public AnswerCityRegister checkCityRegister(StudentOrder studentOrder) {
        AnswerCityRegister ans = new AnswerCityRegister();

        ans.addItem(checkPerson(studentOrder.getHusband()));
        ans.addItem(checkPerson(studentOrder.getWife()));

        for(Child child : studentOrder.getChildren()) {
            ans.addItem(checkPerson(child));
        }
        return ans;
    }
    private AnswerCityRegisterItem checkPerson(Person person) {
        AnswerCityRegisterItem.CityStatus status = null;
        AnswerCityRegisterItem.CityError error = null;
        try {
            CityRegisterResponse tmp = pesonChecker.checkPerson(person);
            status = tmp.isRegistered() ?
                    AnswerCityRegisterItem.CityStatus.YES :
                    AnswerCityRegisterItem.CityStatus.NO;
        } catch (CityRegisterException ex) {
            ex.printStackTrace();
            status = AnswerCityRegisterItem.CityStatus.ERROR;
            error = new AnswerCityRegisterItem.CityError(ex.getCode(), ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            status = AnswerCityRegisterItem.CityStatus.ERROR;
            error = new AnswerCityRegisterItem.CityError(IN_CODE, ex.getMessage());
        }

        AnswerCityRegisterItem ans = new AnswerCityRegisterItem(status, person, error);
        return ans;
    }
}
