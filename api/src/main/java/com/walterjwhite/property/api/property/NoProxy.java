package com.walterjwhite.property.api.property;

import com.walterjwhite.property.api.annotation.DefaultValue;
import com.walterjwhite.property.api.annotation.NotRequired;

/** Hosts to bypass the proxy. This is not currently used. */
@NotRequired
public interface NoProxy extends ConfigurableProperty {
  @DefaultValue String Default = "";
}
