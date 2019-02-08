package com.walterjwhite.property.modules.system;

import com.walterjwhite.property.api.PropertyManager;
import com.walterjwhite.property.api.property.JavaEnvironmentProperty;
import com.walterjwhite.property.impl.tgt.AbstractPropertyTarget;

public class MappedJavaPropertiesPropertyTarget
    extends AbstractPropertyTarget<JavaEnvironmentProperty> {
  public MappedJavaPropertiesPropertyTarget(PropertyManager propertyManager) {
    super(propertyManager, JavaEnvironmentProperty.class);
  }

  @Override
  protected void set(
      Class<? extends JavaEnvironmentProperty> javaEnvironmentProperty, final String value) {}

  @Override
  protected void set(Class<? extends JavaEnvironmentProperty> property) {
    for (final JavaEnvironmentProperty javaEnvironmentProperty : property.getEnumConstants()) {
      System.setProperty(
          javaEnvironmentProperty.getKey(),
          propertyManager.get(javaEnvironmentProperty.getPropertyKey()));
    }
  }
}
