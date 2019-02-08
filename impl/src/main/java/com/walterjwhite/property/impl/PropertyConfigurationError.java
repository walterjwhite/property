package com.walterjwhite.property.impl;

import com.walterjwhite.property.api.property.ConfigurableProperty;

public class PropertyConfigurationError extends Error {
  public PropertyConfigurationError(final Class<? extends ConfigurableProperty> propertyClass) {
    super(
        propertyClass.getName()
            + " was not set and not default value was provided either, this is likely a configuration problem.");
  }
}
