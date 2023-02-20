package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.dto.ItemUpdateDto;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService { // 이렇게 repository 에 위임만 할 경우 꼭 service 를 꼭 만들어야 하는지 고민해봐야 한다. 컨트롤러에서 repository 에 바로 접근할 수 도 있다.
    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, ItemUpdateDto updateDto) { // 변경 감지 기능
        Book item = (Book) itemRepository.findOne(itemId);// id로 찾아온 findItem 은 영속상태이다.
        item.updateBook(updateDto);
         // 로직이 끝나는 시점에 @Transactional 에 의해 commit 이 되고 flush() (변경된것들을 다 찾아 db 에 쿼리를 날려준다.)
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long id) {
        return itemRepository.findOne(id);
    }
}
