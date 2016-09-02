package org.evgndev.service;

import com.google.common.io.Files;
import org.apache.log4j.Logger;
import org.evgndev.entity.Image;
import org.evgndev.repository.ImageRepository;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * Created by evgndev on 21.08.16.
 */
@Service("imageService")
public class ImageServiceImpl implements ImageService {

    private static final Logger LOG = Logger.getLogger(ImageServiceImpl.class);

    @Autowired
    ImageRepository imageRepository;

    @Override
    @Transactional
    public Image createImage(String name, String originalFileName, byte[] imageData) {
        Image image = new Image();

        image.setName(name);
        image.setOriginalName(originalFileName);
        image.setExtension(Files.getFileExtension(originalFileName));
        image.setCreationDate(new Date());
        image.setImageData(getConvertImage(imageData, originalFileName));
        image.setThumbnailData(getThumbnail(imageData));

        return imageRepository.saveAndFlush(image);
    }

    @Override
    public Image getImage(Long id) {
        return imageRepository.findOne(id);
    }

    @Override
    public List<Image> getImages() {
        return imageRepository.findAll();
    }

    private byte[] getConvertImage(byte[] imageData, String fileName) {
        try {
            String extension = "jpg";
            if (!Files.getFileExtension(fileName).equalsIgnoreCase(extension)) {
                InputStream inputStream = new ByteArrayInputStream(imageData);
                BufferedImage img = ImageIO.read(inputStream);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(img, extension, outputStream);
                imageData = outputStream.toByteArray();
            }
        } catch (Exception e) {
            LOG.error("Cannot convert image", e);
        }
        return imageData;
    }

    private byte[] getThumbnail(byte[] imageData) {

        byte[] thumbnailData = null;
        try {
            InputStream image = new ByteArrayInputStream(imageData);
            if (image != null) {
                // imgscalr – Java Image Scaling Library
                BufferedImage thumbnail =
                        Scalr.resize(
                                ImageIO.read(image),
                                Scalr.Method.ULTRA_QUALITY,
                                Scalr.Mode.FIT_TO_WIDTH,
                                480, 360, Scalr.OP_ANTIALIAS
                        );

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                ImageIO.write(thumbnail, "jpg", stream); //td ext

                thumbnailData = stream.toByteArray();
            }
        } catch (Exception e) {
            LOG.error("Cannot create thumbnail", e);
        }
        return thumbnailData;
    }
}
