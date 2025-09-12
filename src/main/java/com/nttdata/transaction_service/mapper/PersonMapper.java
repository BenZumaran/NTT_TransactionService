package com.nttdata.transaction_service.mapper;

import com.nttdata.transaction_service.dto.client.ClientResponseDTO;
import com.nttdata.transaction_service.dto.transaction.TransactionPersonDTO;
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
    public static Person personEntityToPerson(PersonEntity personEntity) throws IllegalArgumentException {
        Person person = new Person();
        if (personEntity.getId() == null && personEntity.getDocument() == null) {
            throw new IllegalArgumentException("Holder or Signatory should have at least document or id");
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

    public static PersonEntity clientResponseDtoToPersonEntity(ClientResponseDTO clientResponseDTO) throws IllegalArgumentException {
        if (
                clientResponseDTO.getId() == null &&
                        clientResponseDTO.getDocumentNumber() == null
        ) throw new IllegalArgumentException("Person should have at least id or document number");
        PersonEntity personEntity = PersonEntity.builder().build();
        if (clientResponseDTO.getId() != null)
            personEntity.setId(clientResponseDTO.getId());
        if (clientResponseDTO.getDocumentNumber() != null)
            personEntity.setDocument(clientResponseDTO.getDocumentNumber());
        if (clientResponseDTO.getFullName() != null)
            personEntity.setFullName(clientResponseDTO.getFullName());
        /*
        PERSONAL, PERSONAL_VIP, BUSINESS, BUSINESS_VIP
       personal, personal_vip, business, business_vip
         */
        if (clientResponseDTO.getType() != null)
            switch (clientResponseDTO.getType()) {
                case "PERSONAL":
                    personEntity.setType("personal");
                    break;
                case "PERSONAL_VIP":
                    personEntity.setType("personal_vip");
                    break;
                case "BUSINESS":
                    personEntity.setType("business");
                    break;
                case "BUSINESS_VIP":
                    personEntity.setType("business_vip");
                    break;
                default:
                    throw new IllegalArgumentException("Person type should be like PERSONAL, PERSONAL_VIP, BUSINESS, BUSINESS_VIP");
            }
        return personEntity;
    }

    public static Person transactionPersonDtoToPerson(TransactionPersonDTO transactionPersonDTO) throws IllegalArgumentException {
        Person person = new Person();
        if (transactionPersonDTO.getId() == null && transactionPersonDTO.getDocument() == null) {
            throw new IllegalArgumentException("Holder or Signatory should have at least document or id");
        }
        if (transactionPersonDTO.getId() != null)
            person.setId(transactionPersonDTO.getId());
        if (transactionPersonDTO.getDocument() != null)
            person.setDocument(transactionPersonDTO.getDocument());

        return person;
    }

}
