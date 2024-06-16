package com.seoultech.dayo.image.service;

import com.luciad.imageio.webp.WebPWriteParam;
import com.seoultech.dayo.image.Category;
import com.seoultech.dayo.image.Image;
import com.seoultech.dayo.image.repository.ImageRepository;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageService {

  private final ImageRepository imageRepository;

  @Value("${file.dir}")
  private String fileDir;

  @Value("${file.origin}")
  private String fileOriginDir;

  public String getFullPath(String fileName) {
    return fileDir + fileName;
  }

  public String getOriginFullPath(String fileName) {
    return fileOriginDir + fileName;
  }

  public List<Image> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
    List<Name> collect = new ArrayList<>();
    File folder = new File(fileDir);
    if (!folder.exists()) {
      folder.mkdir();
    }
    for (MultipartFile multipartFile : multipartFiles) {
      if (!multipartFile.isEmpty()) {
        String originalFilename = multipartFile.getOriginalFilename();
        String storeFilename = createStoreFileName(originalFilename);

        File inputFile = new File(getOriginFullPath(originalFilename));
        File outputFile = new File(getFullPath(storeFilename));
        multipartFile.transferTo(inputFile);
        createWebpFile(inputFile, outputFile);

        Name name = new Name(originalFilename, storeFilename);
        collect.add(name);
      }
    }

    List<Image> images = collect.stream()
        .map(
            (name) -> new Image(name.getOriginalFilename(), name.getStoreFileName(), Category.POST))
        .collect(Collectors.toList());
    imageRepository.saveAll(images);

    return images;
  }

  private void createWebpFile(File inputFile, File outputFile) {
    try {
      BufferedImage image = ImageIO.read(inputFile);

      ImageWriter writer = ImageIO.getImageWritersByMIMEType("image/webp").next();

      // Create a WebPWriteParam with desired compression quality
      WebPWriteParam writeParam = new WebPWriteParam(writer.getLocale());
      writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
      writeParam.setCompressionType(
          writeParam.getCompressionTypes()[WebPWriteParam.LOSSLESS_COMPRESSION]);

      // Instantiate WebPImageWriter
      writer.setOutput(new FileImageOutputStream(outputFile));

      // Write the image with the specified compression parameters
      writer.write(null, new IIOImage(image, null, null), writeParam);

      // Cleanup resources
      writer.dispose();
      System.out.println("Image compressed to WebP successfully.");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Image storeFile(MultipartFile multipartFile, Category category) throws IOException {
    File folder = new File(fileDir);
    if (!folder.exists()) {
      folder.mkdir();
    }
    String originalFilename = multipartFile.getOriginalFilename();
    String storeFilename = createStoreFileName(originalFilename);

    File inputFile = new File(getOriginFullPath(originalFilename));
    File outputFile = new File(getFullPath(storeFilename));
    multipartFile.transferTo(inputFile);
    createWebpFile(inputFile, outputFile);

    Image image = new Image(originalFilename, storeFilename, category);
    return imageRepository.save(image);
  }

  public void resizeFile(String fileName, int width, int height) throws IOException {
    File image = new File(getFullPath(fileName));

    File outputFile = new File(getFullPath(renameFile(fileName, width, height)));
    reCompressWebpFile(image, outputFile, width);
  }

  public Image findDefaultProfileImage() {
    return imageRepository.findById(1L).get();
  }

  public Image findDefaultFolderImage() {
    return imageRepository.findById(2L).get();
  }

  private void reCompressWebpFile(File inputFile, File outputFile, int width) {
    try {
      BufferedImage image = ImageIO.read(inputFile);

      ImageWriter writer = ImageIO.getImageWritersByMIMEType("image/webp").next();

      // Create a WebPWriteParam with desired compression quality
      WebPWriteParam writeParam = new WebPWriteParam(writer.getLocale());
      writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
      writeParam.setCompressionType(
          writeParam.getCompressionTypes()[WebPWriteParam.LOSSY_COMPRESSION]);
      if (width == 17) {
        writeParam.setCompressionQuality(0.3f);
      } else if (width == 37) {
        writeParam.setCompressionQuality(0.5f);
      } else if (width == 45) {
        writeParam.setCompressionQuality(0.7f);
      } else if (width == 220) {
        writeParam.setCompressionQuality(0.7f);
      } else {
        writeParam.setCompressionQuality(0.7f);
      }

      // Instantiate WebPImageWriter
      writer.setOutput(new FileImageOutputStream(outputFile));

      // Write the image with the specified compression parameters
      writer.write(null, new IIOImage(image, null, null), writeParam);

      // Cleanup resources
      writer.dispose();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private String createStoreFileName(String originalFilename) {
    String ext = extractExt(originalFilename);
    String uuid = UUID.randomUUID().toString();
    return uuid + ".webp";
  }

  private String extractExt(String originalFilename) {
    int pos = originalFilename.lastIndexOf(".");
    return originalFilename.substring(pos + 1);
  }

  private String renameFile(String fileName, int width, int height) {
    int pos = fileName.lastIndexOf(".");
    String ext = extractExt(fileName);
    return fileName.substring(0, pos) + "_" + width + "x" + height + "." + ext;
  }

  @Data
  @AllArgsConstructor
  private static class Name {

    private String originalFilename;
    private String storeFileName;
  }

}
