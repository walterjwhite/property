package com.walterjwhite.property.impl;

import com.walterjwhite.logging.annotation.Sensitive;
import com.walterjwhite.property.api.SecretService;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import org.apache.commons.io.IOUtils;

// TODO: migrate this out such that this may be configured
// TODO: not capturing EOF currently (CTRL+D)
public class DefaultSecretService implements SecretService {
  public static final String ENCRYPT_EXECUTABLE = "/usr/bin/encrypt-secret";
  public static final String DECRYPT_EXECUTABLE = "/usr/bin/decrypt-secret";

  @Sensitive
  @Override
  public String encrypt(String plainText) {
    try {
      final Process process = Runtime.getRuntime().exec(new String[] {ENCRYPT_EXECUTABLE});
      try (final OutputStream outputStream = process.getOutputStream()) {
        outputStream.write(plainText.getBytes(Charset.defaultCharset()));
        outputStream.write(0);
        outputStream.write(0);
        outputStream.flush();
      }

      try (final InputStream inputStream = process.getInputStream()) {

        final String output = IOUtils.toString(inputStream, Charset.defaultCharset());
        if (output == null || output.isEmpty()) return null;

        return output.trim();
      } catch (IOException e) {
        throw new RuntimeException("Error getting value:", e);
      }
    } catch (IOException e) {
      throw new RuntimeException("Error encrypting:", e);
    }
  }

  @Sensitive
  @Override
  public String decrypt(String cipherText) {
    try {
      final Process process =
          Runtime.getRuntime().exec(new String[] {DECRYPT_EXECUTABLE, cipherText});

      try (final InputStream inputStream = process.getInputStream()) {
        final String output = IOUtils.toString(inputStream, Charset.defaultCharset());
        if (output == null || output.isEmpty()) return null;

        return output.trim();
      }
    } catch (IOException e) {
      throw new RuntimeException("Error getting value:", e);
    }
  }
}
