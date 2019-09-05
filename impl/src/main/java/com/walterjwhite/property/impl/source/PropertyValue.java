package com.walterjwhite.property.impl.source;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@ToString(doNotUseGetters = true)
@RequiredArgsConstructor
public class PropertyValue {
  // protected final Class<? extends ConfigurableProperty> configurablePropertyClass;

  protected final Class propertyType;

  protected String value;

  public PropertyValue() {
    this(String.class);
  }
}
