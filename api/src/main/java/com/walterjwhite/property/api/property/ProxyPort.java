package com.walterjwhite.property.api.property;

import com.walterjwhite.property.api.annotation.NotRequired;
import com.walterjwhite.property.api.annotation.PropertyValueType;

@PropertyValueType(int.class)
@NotRequired
public interface ProxyPort extends ConfigurableProperty {}
