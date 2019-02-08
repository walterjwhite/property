package com.walterjwhite.property.modules.cli.handler;

import com.walterjwhite.inject.cli.property.CommandLineHandlerShutdownTimeout;
import com.walterjwhite.inject.cli.service.AbstractCommandLineHandler;
import com.walterjwhite.property.api.SecretService;
import com.walterjwhite.property.impl.DefaultSecretService;
import com.walterjwhite.property.impl.annotation.Property;
import javax.inject.Inject;

/** Helper to list our client id. */
public class DecryptPropertyHandler extends AbstractCommandLineHandler {
  protected final SecretService secretService = new DefaultSecretService();

  @Inject
  public DecryptPropertyHandler(
      @Property(CommandLineHandlerShutdownTimeout.class) int shutdownTimeoutInSeconds) {
    super(shutdownTimeoutInSeconds);
  }

  @Override
  protected void doRun(final String... arguments) throws Exception {
    //    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    //    System.out.println(secretService.decrypt(bufferedReader.readLine()));
    for (final String argument : arguments) System.out.println(secretService.decrypt(argument));
  }
}
