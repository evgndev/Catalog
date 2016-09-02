package org.evgndev.service;

import org.apache.log4j.Logger;
import org.evgndev.entity.Item;
import org.evgndev.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created by evgndev on 21.08.16.
 */
@Service("itemService")
public class ItemServiceImpl implements ItemService {

    private static final Logger LOG = Logger.getLogger(ItemServiceImpl.class);

    @Autowired
    ItemRepository itemRepository;

    @Override
    @Transactional
    public Item createItem(String name, Long price, String desc, String specs, Long imageId) {

        Item item = new Item();

        item.setName(name);
        item.setPrice(price);
        item.setDescription(desc);
        item.setSpecs(specs);
        item.setImageId(imageId);
        item.setCreateDate(new Date());
        item.setUpdateDate(new Date());
        item.setImageId(imageId);

        return itemRepository.saveAndFlush(item);
    }

    @Override
    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    @Override
    public List<Item> getItems(int page,
                               int pageSize,
                               String sortProperty,
                               String sortDirection) {

        PageRequest pageRequest = new PageRequest(
                page - 1,
                pageSize,
                Sort.Direction.fromStringOrNull(sortDirection),
                sortProperty
        );

        return itemRepository.findAll(pageRequest).getContent();
    }

    @Override
    public Item getItem(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    @Override
    @Transactional
    public Item updateItem(Long itemId, String name, Long price, String desc, String specs, Long imageId) {

        Item item = itemRepository.getOne(itemId);

        item.setName(name);
        item.setPrice(price);
        item.setDescription(desc);
        item.setSpecs(specs);
        item.setImageId(imageId);
        item.setUpdateDate(new Date());

        return itemRepository.saveAndFlush(item);
    }

    @Override
    public long getItemCount() {
        return itemRepository.count();
    }
}
