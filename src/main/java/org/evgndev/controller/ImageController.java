package org.evgndev.controller;

import org.apache.log4j.Logger;
import org.evgndev.entity.Image;
import org.evgndev.exception.RestException;
import org.evgndev.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by evgndev on 29.08.16.
 */
@Controller
public class ImageController {

    private static final Logger LOG = Logger.getLogger(ImageController.class);

    @Autowired
    ImageService imageService;

    @RequestMapping(value = "/images", method = RequestMethod.GET)
    public
    @ResponseBody
    String getImages() throws RestException {
        try {
            LOG.info("TEST get image ");
            return "";
        } catch (Exception e) {
            throw new RestException(e);
        }
    }

    @RequestMapping(value = "/images/{imageId}/image.jpg", method = RequestMethod.GET)
    public
    @ResponseBody
    byte[] getImage(@PathVariable Long imageId) throws RestException {
        try {
            LOG.info("TEST get image " + imageId);
            Image image = imageService.getImage(imageId);
            return image.getImageData();
        } catch (Exception e) {
            throw new RestException(e);
        }
    }

    @RequestMapping(value = "/images/{imageId}/thumbnails.jpg", method = RequestMethod.GET)
    public
    @ResponseBody
    byte[] getThumbnails(@PathVariable Long imageId) throws RestException {
        try {
            Image image = imageService.getImage(imageId);
            return image.getThumbnailData();
        } catch (Exception e) {
            throw new RestException(e);
        }
    }

    //@Secured("ROLE_ADMIN")
    @RequestMapping(value = "/images", method = RequestMethod.POST)
    public
    @ResponseBody
    Image addImage(@RequestParam("name") String name,
                   @RequestParam("file") MultipartFile file) throws RestException {

        try {
            byte[] data = file.getBytes();
            Image image = imageService.createImage(name, file.getOriginalFilename(), data);

            image.setImageData(null);

            return image;
        } catch (Exception e) {
            throw new RestException(e);
        }
    }
}
