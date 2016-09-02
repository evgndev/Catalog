package org.evgndev.service;

import org.evgndev.entity.Image;

import java.util.List;

/**
 * Created by evgndev on 21.08.16.
 */
public interface ImageService {
    public Image createImage(String name, String originalFileName,  byte[] data);
    public Image getImage(Long id);
    public List<Image> getImages();
}
