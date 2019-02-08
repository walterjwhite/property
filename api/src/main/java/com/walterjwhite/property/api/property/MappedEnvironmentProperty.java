package com.walterjwhite.property.api.property;

import com.walterjwhite.property.api.annotation.NotRequired;

@NotRequired
public interface MappedEnvironmentProperty extends ConfigurableProperty {
  String getKey();

  Class<? extends ConfigurableProperty>[] getSupportedProperties();

  String getValue(Class<? extends ConfigurableProperty> propertyClass);
}
