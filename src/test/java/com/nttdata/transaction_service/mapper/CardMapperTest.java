package com.nttdata.transaction_service.mapper;

import com.nttdata.transaction_service.model.Card;
import com.nttdata.transaction_service.model.entity.CardEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

class CardMapperTest {

    @Test
    void cardToCardEntity() {
        Card card = new Card();
        //Testing empty
        Assertions.assertThrows(IllegalArgumentException.class, () -> CardMapper.cardToCardEntity(card));
        //Testing id
        card.setId("cardid");
        var response = CardMapper.cardToCardEntity(card);
        Assertions.assertInstanceOf(CardEntity.class, response);
        Assertions.assertEquals("cardid", response.getId());
        //Testing nulls
        Assertions.assertNull(response.getCardNumber());
        Assertions.assertFalse(response.isVirtual());
        Assertions.assertNull(response.getCreationDate());
        Assertions.assertNull(response.getCardType());
        Assertions.assertNull(response.getBrand());
        //Testing Card Number
        card.setCardNumber("1234567890");
        response = CardMapper.cardToCardEntity(card);
        Assertions.assertEquals("1234567890", response.getCardNumber());
        //Testing Creation Date
        card.setCreationDate(OffsetDateTime.now());
        response = CardMapper.cardToCardEntity(card);
        Assertions.assertInstanceOf(LocalDateTime.class, response.getCreationDate());
        //Testing Card Type
        card.setCardType(Card.CardTypeEnum.DEBIT);
        response = CardMapper.cardToCardEntity(card);
        Assertions.assertEquals(Card.CardTypeEnum.DEBIT.getValue(), response.getCardType());
        //Testing Brand
        card.setBrand(Card.BrandEnum.VISA);
        response = CardMapper.cardToCardEntity(card);
        Assertions.assertEquals(Card.BrandEnum.VISA.getValue(), response.getBrand());
    }

    @Test
    void cardEntityToCard() {
        CardEntity cardEntity = CardEntity.builder().build();
        //Testing empty
        Assertions.assertThrows(IllegalArgumentException.class, () -> CardMapper.cardEntityToCard(cardEntity));
        //Testing id
        cardEntity.setId("cardid");
        var response = CardMapper.cardEntityToCard(cardEntity);
        Assertions.assertInstanceOf(Card.class, response);
        Assertions.assertEquals("cardid", response.getId());
        //Testing nulls
        Assertions.assertNull(response.getCardNumber());
        Assertions.assertFalse(response.getIsVirtual());
        Assertions.assertNull(response.getCreationDate());
        Assertions.assertNull(response.getCardType());
        Assertions.assertNull(response.getBrand());
        //Testing Card Number
        cardEntity.setCardNumber("1234567890");
        response = CardMapper.cardEntityToCard(cardEntity);
        Assertions.assertEquals("1234567890", response.getCardNumber());
        //Testing Creation Date
        cardEntity.setCreationDate(LocalDateTime.now());
        response = CardMapper.cardEntityToCard(cardEntity);
        Assertions.assertInstanceOf(OffsetDateTime.class, response.getCreationDate());
        //Testing Card Type
        cardEntity.setCardType(Card.CardTypeEnum.DEBIT.getValue());
        response = CardMapper.cardEntityToCard(cardEntity);
        Assertions.assertEquals(Card.CardTypeEnum.DEBIT, response.getCardType());
        //Testing Card Type Wrong
        cardEntity.setCardType("WRONG");
        response = CardMapper.cardEntityToCard(cardEntity);
        //Assertions.assertThrows(IllegalArgumentException.class, () -> CardMapper.cardEntityToCard(cardEntity));
        //cardEntity.setCardType(Card.CardTypeEnum.DEBIT.getValue());
        Assertions.assertNull(response.getCardType());
        //Testing Brand
        cardEntity.setBrand(Card.BrandEnum.VISA.getValue());
        response = CardMapper.cardEntityToCard(cardEntity);
        Assertions.assertEquals(Card.BrandEnum.VISA, response.getBrand());
        //Testing Brand Wrong
        cardEntity.setBrand("WRONG");
        response = CardMapper.cardEntityToCard(cardEntity);
        Assertions.assertNull(response.getBrand());
    }
}