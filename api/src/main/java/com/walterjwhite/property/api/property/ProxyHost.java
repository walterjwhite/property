package com.walterjwhite.property.api.property;

import com.walterjwhite.property.api.annotation.NotRequired;

/**
 * Default value was removed, instead system relies on environmental variable to be set OR,
 * command-line option, or properties file.
 */
@NotRequired
public interface ProxyHost extends ConfigurableProperty {

}
