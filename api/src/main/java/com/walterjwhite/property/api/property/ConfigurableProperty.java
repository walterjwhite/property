package com.walterjwhite.property.api.property;

/** Marks a property for injection. */
// redundant, this is the default
// @PropertyValueType(String.class)
public interface ConfigurableProperty {
  // Type is specified via @PropertyValueType
  //    default Class getType() {
  //      return String.class;
  //    }
}
