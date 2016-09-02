package org.evgndev.service;

import org.evgndev.entity.Item;

import java.util.List;

/**
 * Created by evgndev on 21.08.16.
 */
public interface ItemService {
    public Item createItem(String name, Long price, String desc, String specs, Long imageId);
    public Item updateItem(Long itemId, String name, Long price, String desc, String specs, Long imageId);
    public long getItemCount();
    public List<Item> getItems();
    public List<Item> getItems(int start,
                               int delta,
                               String sortProperty,
                               String sortDirection);
    public Item getItem(Long itemId);
}
