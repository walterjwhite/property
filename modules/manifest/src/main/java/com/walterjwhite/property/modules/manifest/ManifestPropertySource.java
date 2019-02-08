package com.walterjwhite.property.modules.manifest;

import com.jcabi.manifests.Manifests;
import com.walterjwhite.property.api.PropertyManager;
import com.walterjwhite.property.api.annotation.PropertySourceIndex;
import com.walterjwhite.property.api.property.ConfigurableProperty;
import com.walterjwhite.property.impl.source.AbstractSingularStringPropertySource;

@PropertySourceIndex(2)
public class ManifestPropertySource extends AbstractSingularStringPropertySource {
  public ManifestPropertySource(PropertyManager propertyManager) {
    super(propertyManager, ConfigurableProperty.class);
  }

  @Override
  protected String doGet(final String lookupValue) {
    return Manifests.read(lookupValue);
  }
}
