package org.evgndev.controller;

import com.google.common.base.Strings;
import org.apache.log4j.Logger;
import org.evgndev.entity.Item;
import org.evgndev.exception.RestException;
import org.evgndev.service.ItemService;
import org.evgndev.util.Ajax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by evgndev on 21.08.16.
 */

@Controller
public class ItemController {

    private static final Logger LOG = Logger.getLogger(ItemController.class);

    @Autowired
    ItemService itemService;


    @RequestMapping(value = "/items/count", method = RequestMethod.GET)
    public
    @ResponseBody
    Map<String, Object> getItemCount() throws RestException {
        try {
            Long itemCount = itemService.getItemCount();

            return Ajax.successResponse(itemCount);
        } catch (Exception e) {
            throw new RestException(e);
        }
    }


    @RequestMapping(value = "/items", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Item> getItems(@RequestParam(required = false, defaultValue = "1") int page,
                        @RequestParam(required = false, defaultValue = "20") int pageSize,
                        @RequestParam(required = false, defaultValue = "itemId") String sortProperty,
                        @RequestParam(required = false, defaultValue = "asc") String sortDirection) throws RestException {

        try {

            List<Item> result = itemService.getItems(page, pageSize, sortProperty, sortDirection);

            LOG.info("TEST LOG: item size " + result.size());

            return result;
        } catch (Exception e) {
            throw new RestException(e);
        }
    }

    @RequestMapping(value = "/items/{itemId}", method = RequestMethod.GET)
    public
    @ResponseBody
    Item getItem(@PathVariable Long itemId) throws RestException {
        try {
            LOG.info("TEST getItem mm " + itemId);
            return itemService.getItem(itemId);
        } catch (Exception e) {
            throw new RestException(e);
        }
    }

    //@Secured("ROLE_ADMIN")
    @RequestMapping(value = "/items", method = RequestMethod.POST)
    public
    @ResponseBody
    Item createItem(@RequestBody Item item) throws RestException {
        try {
            LOG.info("TEST create item: " + item);
            Item result = null;
            if (!Strings.isNullOrEmpty(item.getName())) {
                result = itemService.createItem(
                        item.getName(),
                        item.getPrice(),
                        item.getDescription(),
                        item.getSpecs(),
                        item.getImageId());
            }

            return result;
        } catch (Exception e) {
            throw new RestException(e);
        }
    }

    //@Secured("ROLE_ADMIN")
    @RequestMapping(value = "/items/{itemId}", method = RequestMethod.PUT)
    public
    @ResponseBody
    Item updateItem(@PathVariable Long itemId,
                    @RequestBody Item item) throws RestException {
        try {
            item = itemService.updateItem(
                    itemId,
                    item.getName(),
                    item.getPrice(),
                    item.getDescription(),
                    item.getSpecs(),
                    item.getImageId()
            );

            return item;
        } catch (Exception e) {
            throw new RestException(e);
        }
    }
}
