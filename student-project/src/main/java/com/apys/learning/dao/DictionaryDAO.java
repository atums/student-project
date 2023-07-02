package com.apys.learning.dao;

import com.apys.learning.domain.CountryArea;
import com.apys.learning.domain.PassportOffice;
import com.apys.learning.domain.RegisterOffice;
import com.apys.learning.domain.Street;
import com.apys.learning.exception.DAOException;

import java.util.List;

public interface DictionaryDAO {
    List<Street> findStreet(String pattern) throws DAOException;
    List<PassportOffice> findPassportOffices(String areaId) throws DAOException;
    List<RegisterOffice> findRegisterOffices(String areaId) throws DAOException;
    List<CountryArea> findAreas(String areaId) throws DAOException;
}
