package com.walterjwhite.property.api.property;

import com.walterjwhite.property.api.annotation.NotRequired;

@NotRequired
public interface JavaEnvironmentProperty extends ConfigurableProperty {
  String getKey();

  Class<? extends ConfigurableProperty> getPropertyKey();
}
