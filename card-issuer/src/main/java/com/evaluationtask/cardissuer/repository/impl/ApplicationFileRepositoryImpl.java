package com.evaluationtask.cardissuer.repository.impl;

import static com.evaluationtask.cardissuer.infrastructure.Constants.ERROR_ON_WRITING_TO_FILE;

import com.evaluationtask.cardissuer.exceptions.ApplicationFileException;
import com.evaluationtask.cardissuer.model.ApplicationFile;
import com.evaluationtask.cardissuer.model.Status;
import com.evaluationtask.cardissuer.repository.ApplicationFileRepository;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ApplicationFileRepositoryImpl implements ApplicationFileRepository {

  private final String APPLICATION_FILES_FOLDER = "/src/main/java/com/evaluationtask/cardissuer/applicationfiles";

  @Override
  public void createApplicationFile(ApplicationFile applicationFile, Status personStatus)
      throws IOException {
    if (personStatus == Status.ACTIVE) {
      Optional<ApplicationFile> lastActiveApplicationFile =
          findActiveApplicationFileForOib(applicationFile.getOIB());

      if (lastActiveApplicationFile.isPresent()) {
        lastActiveApplicationFile.get().setStatus(Status.INACTIVE);
        writeToFile(lastActiveApplicationFile.get());
      }
    }

    writeToFile(applicationFile);
  }

  @Override
  public void deactivateTheLastApplicationFile(String oib, Status personStatus) throws IOException {
    if (personStatus == Status.ACTIVE) {
      Optional<ApplicationFile> lastActiveApplicationFile =
          findActiveApplicationFileForOib(oib);

      if (lastActiveApplicationFile.isPresent()) {
        lastActiveApplicationFile.get().setStatus(Status.INACTIVE);
        writeToFile(lastActiveApplicationFile.get());
      }
    }
  }

  private Optional<ApplicationFile> findActiveApplicationFileForOib(String oib) throws IOException {

    String activeApplicationFileName = null;
    Path activeApplicationFile = null;

    try {
      Path folderPath = Paths.get(System.getProperty("user.dir"), APPLICATION_FILES_FOLDER);

      if (Files.exists(folderPath) && Files.isDirectory(folderPath)) {
        DirectoryStream.Filter<Path> filter = path -> {
          String fileName = path.getFileName().toString();
          return fileName.startsWith(oib) && fileName.endsWith(".txt");
        };

        DirectoryStream<Path> files = Files.newDirectoryStream(folderPath, filter);
        for (Path filePath : files) {
          String fileNameString = filePath.getFileName().toString();
          if (activeApplicationFileName == null) {
            activeApplicationFile = filePath;
            activeApplicationFileName = fileNameString;
          } else {
            if (fileNameString.compareTo(activeApplicationFileName) > 0) {
              activeApplicationFile = filePath;
              activeApplicationFileName = fileNameString;
            }
          }
        }
      }
    } catch (IOException e) {
      throw new ApplicationFileException(
          HttpStatus.INTERNAL_SERVER_ERROR, ERROR_ON_WRITING_TO_FILE);
    }

    assert activeApplicationFile != null;
    List<String> lines = Files.readAllLines(activeApplicationFile);

    StringBuilder content = new StringBuilder();

    for (String line : lines) {
      content.append(line).append("\n");
    }

    String[] applicationInfo =
        content.toString().replaceAll("\\s+", "").split(",");

    ApplicationFile applicationFile = new ApplicationFile();
    applicationFile.setFirstName(applicationInfo[0]);
    applicationFile.setLastName(applicationInfo[1]);
    applicationFile.setOIB(applicationInfo[2]);
    applicationFile.setStatus(Status.valueOf(applicationInfo[3]));
    applicationFile.setTimestamp(
        Instant.parse(
            activeApplicationFileName.substring(12, activeApplicationFileName.length() - 4)));

    return Optional.of(applicationFile);
  }

  private void writeToFile(ApplicationFile applicationFile) {
    try {
      Path folderPath = Paths.get(System.getProperty("user.dir"), APPLICATION_FILES_FOLDER);
      Path filePath = folderPath.resolve(
          applicationFile.getOIB() + "_" + applicationFile.getTimestamp() + ".txt");

      if (!Files.exists(folderPath)) {
        Files.createDirectories(folderPath);
      }

      if (Files.exists(filePath)) {
        Files.delete(filePath);
      }

      Files.write(filePath, createContentForApplicationFile(applicationFile).getBytes());

    } catch (IOException e) {
      throw new ApplicationFileException(
          HttpStatus.INTERNAL_SERVER_ERROR, ERROR_ON_WRITING_TO_FILE);
    }

  }

  private String createContentForApplicationFile(ApplicationFile applicationFile) {
    List<String> contentAsList = List.of(
        applicationFile.getFirstName(),
        applicationFile.getLastName(),
        applicationFile.getOIB(),
        applicationFile.getStatus().name());

    String delimiter = ",";

    return String.join(delimiter, contentAsList);
  }
}
