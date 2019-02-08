package com.walterjwhite.property.modules.cli.handler;

import com.walterjwhite.inject.cli.property.CommandLineHandlerShutdownTimeout;
import com.walterjwhite.inject.cli.service.AbstractCommandLineHandler;
import com.walterjwhite.property.api.SecretService;
import com.walterjwhite.property.impl.DefaultSecretService;
import com.walterjwhite.property.impl.annotation.Property;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.inject.Inject;

/** Helper to list our client id. */
public class EncryptPropertyHandler extends AbstractCommandLineHandler {
  protected final SecretService secretService = new DefaultSecretService();

  @Inject
  public EncryptPropertyHandler(
      @Property(CommandLineHandlerShutdownTimeout.class) int shutdownTimeoutInSeconds) {
    super(shutdownTimeoutInSeconds);
  }

  @Override
  protected void doRun(final String... arguments) throws Exception {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    System.out.println(secretService.encrypt(bufferedReader.readLine()));
  }
}
