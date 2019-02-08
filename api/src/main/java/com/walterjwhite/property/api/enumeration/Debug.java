package com.walterjwhite.property.api.enumeration;

import com.walterjwhite.property.api.annotation.DefaultValue;
import com.walterjwhite.property.api.property.ConfigurableProperty;

/** Should the application run in debug mode? */
// TODO: placeholder to experiment more with debugging
public interface Debug extends ConfigurableProperty {
  @DefaultValue boolean DEFAULT = false;
}
