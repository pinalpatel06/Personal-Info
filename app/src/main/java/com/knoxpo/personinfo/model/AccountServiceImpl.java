package com.knoxpo.personinfo.model;

/**
 * Created by Tejas Sherdiwala on 4/11/2017.
 * &copy; Knoxpo
 */

public class AccountServiceImpl implements AccountService {

    @Override
    public Person createNew(Person person) {
        return person;
    }

    @Override
    public Person updatePersonInfo(Person person) {
        person.setFname("xyz");
        return person;
    }
}
