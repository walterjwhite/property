package com.walterjwhite.property.impl.enumeration;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PropertyType {
  StringType(String.class) {
    protected boolean isValid(final String input) {
      return true;
    }
  },
  IntegerType(int.class) {
    protected boolean isValid(final String input) {
      try {
        Integer.parseInt(input);
        return true;
      } catch (NumberFormatException e) {
        return false;
      }
    }
  },
  DoubleType(double.class) {
    protected boolean isValid(final String input) {
      try {
        Double.parseDouble(input);
        return true;
      } catch (NumberFormatException e) {
        return false;
      }
    }
  },
  FloatType(float.class) {
    protected boolean isValid(final String input) {
      try {
        Float.parseFloat(input);
        return true;
      } catch (NumberFormatException e) {
        return false;
      }
    }
  },
  @Getter
  Boolean(boolean.class) {
    protected final String[] values =
        new String[] {"true", "yes", "t", "y", "1", "false", "no", "f", "n", "0"};

    protected boolean isValid(final String input) {
      if (input == null || input.length() == 0) return false;

      return Arrays.stream(values).filter(value -> value.equalsIgnoreCase(input)).count() > 0;
    }
  };

  private final Class propertyClass;

  protected abstract boolean isValid(final String input);

  public static boolean isValid(final Class propertyType, final String input) {
    if (propertyType == null)
      throw new IllegalArgumentException("Property type was not properly passed.");

    if (isValidType(propertyType, input)) return true;

    if (propertyType.isEnum()) {
      validateEnum(propertyType, input);
      return true;
    }

    throw new IllegalArgumentException(propertyType.getName() + " is not currently supported.");
  }

  private static boolean isValidType(final Class propertyType, final String input) {
    for (final PropertyType propertyConverterType : values()) {
      if (propertyConverterType.propertyClass.equals(propertyType)) {
        return propertyConverterType.isValid(input);
      }
    }

    return false;
  }

  private static void validateEnum(final Class propertyType, final String input) {
    try {
      Enum.valueOf((Class<? extends Enum>) propertyType, input);
    } catch (Exception e) {
      // return false;
      throw new IllegalArgumentException(
          "Unable to translate (" + input + ") into " + propertyType.getName(), e);
    }
  }
}
