package com.nttdata.transaction_service.mapper;

import com.nttdata.transaction_service.model.Person;
import com.nttdata.transaction_service.model.entity.PersonEntity;

public class PersonMapper {

    // Validate and Converts Person to Person Entity
    public static PersonEntity personToPersonEntity(Person person) {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setDocument(person.getDocument());
        if(person.getFullName() != null)
            personEntity.setFullName(person.getFullName());
        if(person.getSignature() != null)
            personEntity.setSignature(person.getSignature());
        return personEntity;
    }

    // Validate and Converts Person Entity to Person
    public static Person personEntityToPerson(PersonEntity personEntity) {
        Person person = new Person();
        person.setDocument(personEntity.getDocument());
        if (personEntity.getFullName() != null)
            person.setFullName(personEntity.getFullName());
        if (personEntity.getSignature() != null)
            person.setSignature(personEntity.getSignature());
        return person;
    }
}
