package com.nttdata.transaction_service.mapper;

import com.nttdata.transaction_service.model.Card;
import com.nttdata.transaction_service.model.entity.CardEntity;

import java.time.OffsetDateTime;

public class CardMapper {

    public static CardEntity cardToCardEntity(Card card) throws IllegalArgumentException {
        CardEntity cardEntity = CardEntity.builder().build();

        if (card.getId() == null)
            throw new IllegalArgumentException("Card should have ID");
        cardEntity.setId(card.getId());

        if (card.getCardNumber() != null)
            cardEntity.setCardNumber(card.getCardNumber());

        if (card.getIsVirtual() != null)
            cardEntity.setVirtual(card.getIsVirtual());

        if (card.getCreationDate() != null)
            cardEntity.setCreationDate(card.getCreationDate().toLocalDateTime());

        if (card.getCardType() != null)
            cardEntity.setCardType(card.getCardType().getValue());

        if (card.getBrand() != null)
            cardEntity.setBrand(card.getBrand().getValue());

        return cardEntity;
    }

    public static Card cardEntityToCard(CardEntity cardEntity) {
        Card card = new Card();

        card.setId(cardEntity.getId());

        card.setCardNumber(cardEntity.getCardNumber());

        card.setIsVirtual(cardEntity.isVirtual());

        card.setCreationDate(OffsetDateTime.from(cardEntity.getCreationDate()));

        card.setCardType(Card.CardTypeEnum.fromValue(cardEntity.getCardType()));

        card.setBrand(Card.BrandEnum.fromValue(cardEntity.getBrand()));

        return card;
    }

}
