package com.nttdata.transaction_service.mapper;

import com.nttdata.transaction_service.dto.client.ClientResponseDTO;
import com.nttdata.transaction_service.model.Person;
import com.nttdata.transaction_service.model.entity.PersonEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Test Person Mapper")
class PersonMapperTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void personToPersonEntity() {
        Person person = new Person();
        //Validating exception at person without id nor document
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                PersonMapper.personToPersonEntity(person)
        );
        //Validating Transformation with only id
        person.setId("personid");
        var response = PersonMapper.personToPersonEntity(person);
        Assertions.assertInstanceOf(PersonEntity.class, response);
        Assertions.assertEquals("personid", response.getId());
        Assertions.assertNull(response.getType());
        Assertions.assertNull(response.getDocument());
        Assertions.assertNull(response.getFullName());
        //Validating transformation with id and document
        person.setId(null);
        person.setDocument("12345678");
        response = PersonMapper.personToPersonEntity(person);
        Assertions.assertEquals("12345678", response.getDocument());
        //Validating with personal
        person.setId("personid");
        person.setType(Person.TypeEnum.PERSONAL);
        response = PersonMapper.personToPersonEntity(person);
        Assertions.assertEquals("personal", response.getType());
        //Validating with Fullname
        person.setFullName("Full person name");
        response = PersonMapper.personToPersonEntity(person);
        Assertions.assertEquals("Full person name", response.getFullName());

    }

    @Test
    void personEntityToPerson() {
        PersonEntity personEntity = PersonEntity.builder().build();
        //Validating exception at person without id nor document
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                PersonMapper.personEntityToPerson(personEntity)
        );
        //Validating Transformation with only id
        personEntity.setId("personid");
        var response = PersonMapper.personEntityToPerson(personEntity);
        Assertions.assertInstanceOf(Person.class, response);
        Assertions.assertEquals("personid", response.getId());
        Assertions.assertNull(response.getType());
        Assertions.assertNull(response.getDocument());
        Assertions.assertNull(response.getFullName());
        //Validating transformation with document
        personEntity.setDocument("12345678");
        personEntity.setId(null);
        response = PersonMapper.personEntityToPerson(personEntity);
        Assertions.assertEquals("12345678", response.getDocument());
        //Validating with correct type
        personEntity.setId("personid");
        personEntity.setType(Person.TypeEnum.PERSONAL.getValue());
        response = PersonMapper.personEntityToPerson(personEntity);
        Assertions.assertEquals(Person.TypeEnum.PERSONAL, response.getType());
        //Validating wrong type
        personEntity.setType("other");
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                PersonMapper.personEntityToPerson(personEntity)
        );
        personEntity.setType(Person.TypeEnum.PERSONAL.getValue());
        //Validating with Fullname
        personEntity.setFullName("Full person name");
        response = PersonMapper.personEntityToPerson(personEntity);
        Assertions.assertEquals("Full person name", response.getFullName());

    }

    @Test
    void clientResponseDtoToPersonEntity() {
        ClientResponseDTO clientResponseDTO = ClientResponseDTO.builder().build();
        //Testing empty
        Assertions.assertThrows(IllegalArgumentException.class, () -> PersonMapper.clientResponseDtoToPersonEntity(clientResponseDTO));
        clientResponseDTO.setId("personid");
        clientResponseDTO.setDocumentNumber("12345678");
        clientResponseDTO.setFirstName("Pedro");
        clientResponseDTO.setLastName("Pablo");
        clientResponseDTO.setType("PERSONAL");
        //Testing with all arguments type:Personal
        var response = PersonMapper.clientResponseDtoToPersonEntity(clientResponseDTO);
        Assertions.assertInstanceOf(PersonEntity.class, response);
        Assertions.assertEquals("personid", response.getId());
        clientResponseDTO.setId(null);
        response = PersonMapper.clientResponseDtoToPersonEntity(clientResponseDTO);
        Assertions.assertEquals("12345678", response.getDocument());
        Assertions.assertEquals("Pedro Pablo", response.getFullName());
        Assertions.assertEquals(Person.TypeEnum.PERSONAL.getValue(), response.getType());
        //Testing type:PERSONAL_VIP
        clientResponseDTO.setType("PERSONAL_VIP");
        response = PersonMapper.clientResponseDtoToPersonEntity(clientResponseDTO);
        Assertions.assertEquals(Person.TypeEnum.PERSONAL_VIP.getValue(), response.getType());
        //Testing type:PERSONAL_VIP
        clientResponseDTO.setType("BUSINESS");
        response = PersonMapper.clientResponseDtoToPersonEntity(clientResponseDTO);
        Assertions.assertEquals(Person.TypeEnum.BUSINESS.getValue(), response.getType());
        //Testing type:PERSONAL_VIP
        clientResponseDTO.setType("BUSINESS_VIP");
        response = PersonMapper.clientResponseDtoToPersonEntity(clientResponseDTO);
        Assertions.assertEquals(Person.TypeEnum.BUSINESS_VIP.getValue(), response.getType());
        //Testing type:OTHER/INCORRECT
        clientResponseDTO.setType("OTHER");
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                PersonMapper.clientResponseDtoToPersonEntity(clientResponseDTO)
        );
    }
}