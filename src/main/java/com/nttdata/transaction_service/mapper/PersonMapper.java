package com.nttdata.transaction_service.mapper;

import com.nttdata.transaction_service.dto.client.ClientResponseDTO;
import com.nttdata.transaction_service.model.Person;
import com.nttdata.transaction_service.model.entity.PersonEntity;

public class PersonMapper {

    // Validate and Converts Person to Person Entity
    public static PersonEntity personToPersonEntity(Person person) throws IllegalArgumentException {
        PersonEntity personEntity = PersonEntity.builder().build();
        if (person.getId() == null && person.getDocument() == null) {
            throw new IllegalArgumentException("Holder or Signatory should have at least document or id");
        }
        if (person.getId() != null)
            personEntity.setId(person.getId());
        if (person.getDocument() != null)
            personEntity.setDocument(person.getDocument());
        if (person.getType() != null)
            personEntity.setType(person.getType().getValue());
        if (person.getFullName() != null)
            personEntity.setFullName(person.getFullName());

        return personEntity;
    }

    // Validate and Converts Person Entity to Person
    public static Person personEntityToPerson(PersonEntity personEntity) throws Error {
        Person person = new Person();
        if (personEntity.getId() == null && personEntity.getDocument() == null) {
            throw new Error("Holder or Signatory should have at least document or id");
        }
        if (personEntity.getId() != null)
            person.setId(personEntity.getId());
        if (personEntity.getDocument() != null)
            person.setDocument(personEntity.getDocument());
        if (personEntity.getType() != null)
            person.setType(Person.TypeEnum.valueOf(personEntity.getType().toUpperCase()));
        if (personEntity.getFullName() != null)
            person.setFullName(personEntity.getFullName());

        return person;
    }

    public static PersonEntity clientResponseDtoToPersonEntity(ClientResponseDTO clientResponseDTO) {
        PersonEntity personEntity = PersonEntity.builder().build();
        personEntity.setId(clientResponseDTO.getId());
        personEntity.setDocument(clientResponseDTO.getDocumentNumber());
        personEntity.setFullName(clientResponseDTO.getFullName());
        switch (clientResponseDTO.getType()) {
            case "PERSONAL":
                personEntity.setType("personal");
            case "BUSINESS":
                personEntity.setType("business");
        }
        return personEntity;
    }


}
