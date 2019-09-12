package com.walterjwhite.property.impl;

import com.walterjwhite.property.api.property.ConfigurableProperty;

public class PropertyConfigurationError extends Error {
  public PropertyConfigurationError(
      final Class<? extends ConfigurableProperty> propertyClass, final String message) {
    super(propertyClass.getName() + message);
  }
}
