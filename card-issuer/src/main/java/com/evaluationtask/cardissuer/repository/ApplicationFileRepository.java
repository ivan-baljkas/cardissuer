package com.evaluationtask.cardissuer.repository;

import com.evaluationtask.cardissuer.model.ApplicationFile;
import com.evaluationtask.cardissuer.model.Status;
import java.io.IOException;

public interface ApplicationFileRepository {

  void createApplicationFile(ApplicationFile applicationFile, Status personStatus)
      throws IOException;

  void deactivateTheLastApplicationFile(String oib, Status personStatus) throws IOException;
}
