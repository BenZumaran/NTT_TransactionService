package com.nttdata.transaction_service.mapper;

import com.nttdata.transaction_service.dto.transaction.TransactionCardDTO;
import com.nttdata.transaction_service.model.Card;
import com.nttdata.transaction_service.model.entity.CardEntity;

import java.time.ZoneId;

public class CardMapper {

    public static CardEntity cardToCardEntity(Card card) throws IllegalArgumentException {
        if (card.getId() == null)
            throw new IllegalArgumentException("Card should have ID");

        CardEntity cardEntity = CardEntity.builder().build();

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
        if (cardEntity.getId() == null)
            throw new IllegalArgumentException("Card should have ID");

        Card card = new Card();
        card.setId(cardEntity.getId());

        if (cardEntity.getCardNumber() != null)
            card.setCardNumber(cardEntity.getCardNumber());

        card.setIsVirtual(cardEntity.isVirtual());

        if (cardEntity.getCreationDate() != null)
            card.setCreationDate(
                    cardEntity.getCreationDate().atZone(ZoneId.systemDefault()).toOffsetDateTime()
            );

        if (cardEntity.getCardType() != null)
            card.setCardType(Card.CardTypeEnum.fromValue(cardEntity.getCardType()));

        if (cardEntity.getBrand() != null)
            card.setBrand(Card.BrandEnum.fromValue(cardEntity.getBrand()));

        return card;
    }

    public static Card transactionCardDtoToCard(TransactionCardDTO transactionCardDTO) throws IllegalArgumentException {
        if (transactionCardDTO.getId() == null)
            throw new IllegalArgumentException("Card should have ID");

        Card card = new Card();
        card.setId(transactionCardDTO.getId());

        if (transactionCardDTO.getCardNumber() != null)
            card.setCardNumber(transactionCardDTO.getCardNumber());

        card.setIsVirtual(transactionCardDTO.isVirtual());

        if (transactionCardDTO.getCreationDate() != null)
            card.setCreationDate(transactionCardDTO.getCreationDate().toLocalDateTime().atZone(ZoneId.systemDefault()).toOffsetDateTime());

        if (transactionCardDTO.getCardType() != null)
            card.setCardType(Card.CardTypeEnum.fromValue(transactionCardDTO.getCardType()));

        if (transactionCardDTO.getBrand() != null)
            card.setBrand(Card.BrandEnum.fromValue(transactionCardDTO.getBrand()));

        return card;
    }
}
