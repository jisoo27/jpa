package jpabook.jpashop.domain.item;

import jpabook.jpashop.dto.ItemUpdateDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")
@Getter
@Setter
public class Book extends Item{

    private String author;
    private String isbn;

    public void updateBook(ItemUpdateDto dto) {
        super.setName(dto.getName());
        super.setPrice(dto.getPrice());
        super.setStockQuantity(dto.getStockQuantity());
        this.author = dto.getAuthor();
        this.isbn = dto.getIsbn();
    }

}
