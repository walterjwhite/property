package com.walterjwhite.property.modules.cli.enumeration;

import com.walterjwhite.inject.cli.property.OperatingMode;
import com.walterjwhite.inject.cli.service.AbstractCommandLineHandler;
import com.walterjwhite.property.api.annotation.DefaultValue;
import com.walterjwhite.property.modules.cli.handler.DecryptPropertyHandler;
import com.walterjwhite.property.modules.cli.handler.EncryptPropertyHandler;

public enum PropertyEncryptionOperatingMode implements OperatingMode {
  @DefaultValue
  Encrypt("Encrypt input text", EncryptPropertyHandler.class),
  Decrypt("Send a message(s)", DecryptPropertyHandler.class);

  private final String description;
  private final Class<? extends AbstractCommandLineHandler> initiatorClass;

  PropertyEncryptionOperatingMode(
      String description, Class<? extends AbstractCommandLineHandler> initiatorClass) {
    this.description = description;
    this.initiatorClass = initiatorClass;
  }

  @Override
  public Class<? extends AbstractCommandLineHandler> getInitiatorClass() {
    return initiatorClass;
  }
}
