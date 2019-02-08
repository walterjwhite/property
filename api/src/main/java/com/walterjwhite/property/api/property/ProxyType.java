package com.walterjwhite.property.api.property;

import com.walterjwhite.property.api.annotation.DefaultValue;
import com.walterjwhite.property.api.annotation.NotRequired;

/** Proxy Type (HTTP/SOCKS), not currently used / supported. */
@NotRequired
public interface ProxyType extends ConfigurableProperty {
  @DefaultValue
  com.walterjwhite.property.api.enumeration.ProxyType Default =
      com.walterjwhite.property.api.enumeration.ProxyType.HTTP;
}
