package com.walterjwhite.property.impl;

import com.walterjwhite.property.api.annotation.NotRequired;
import com.walterjwhite.property.api.property.ConfigurableProperty;
import com.walterjwhite.property.impl.enumeration.PropertyType;
import java.lang.reflect.Modifier;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PropertyHelper {

  // SEE: ServiceManager, make this a utility class / function
  public static boolean isConcrete(final Class clazz) {
    return (!Modifier.isAbstract(clazz.getModifiers()));
  }

  public static boolean isRequired(
      final Class<? extends ConfigurableProperty> configurablePropertyClass) {
    return configurablePropertyClass.isAnnotationPresent(NotRequired.class);
  }

  public static boolean isValidClass(
      final Class<? extends ConfigurableProperty> configurablePropertyClass) {
    try {
      Class.forName(configurablePropertyClass.getName());
      return true;
    } catch (ClassNotFoundException e) {
      return false;
    }
  }

  public static void validatePropertyConfiguration(
      final Class<? extends ConfigurableProperty> configurablePropertyClass,
      final Class propertyType,
      final String value) {
    if (value != null) {
      if (!PropertyType.isValid(propertyType, value)) {
        throw new PropertyConfigurationError(
            configurablePropertyClass,
            " is not of the proper type (" + propertyType.getName() + ") (" + value + ")");
      }

      if (PropertyHelper.isRequired(configurablePropertyClass)) {
        if (!PropertyHelper.isValidClass(configurablePropertyClass))
          throw new PropertyConfigurationError(
              configurablePropertyClass,
              " was not set and not default value was provided either, this is likely a configuration problem.");
      }
    }
  }
}
