package com.walterjwhite.property.impl.source;

import com.walterjwhite.logging.annotation.Sensitive;
import com.walterjwhite.property.api.PropertyManager;
import com.walterjwhite.property.api.SecretService;
import com.walterjwhite.property.api.annotation.PropertyValueType;
import com.walterjwhite.property.api.property.ConfigurableProperty;
import com.walterjwhite.property.api.source.PropertySource;
import com.walterjwhite.property.impl.AbstractPropertyManager;
import com.walterjwhite.property.impl.DefaultSecretService;
import com.walterjwhite.property.impl.PropertyHelper;
import com.walterjwhite.property.impl.PropertySourceComparator;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import lombok.Getter;
import org.reflections.Reflections;

@Getter
public class PropertySourceManager extends AbstractPropertyManager<PropertySource> {
  // protected final Properties properties = new Properties();
  protected final Map<Class<? extends ConfigurableProperty>, PropertyValue> propertyValueMap =
      new HashMap<>();
  protected final Map<Class<? extends ConfigurableProperty>, PropertyValue>
      encryptedPropertyValueMap = new HashMap<>();
  //  protected final Properties encryptedProperties = new Properties();

  // TODO: allow this to be overridden
  protected final SecretService secretService = new DefaultSecretService();

  public PropertySourceManager(Reflections reflections, final PropertyManager propertyManager) {
    super(reflections, propertyManager);
  }

  public void call() {
    super.call();

    decryptProperties();
    validateProperties();
  }

  public Iterable<Class<? extends ConfigurableProperty>> getKeys() {
    return (reflections.getSubTypesOf(ConfigurableProperty.class));
  }

  protected void validateProperties() {
    getKeys().forEach(p -> validateProperty(p));
  }

  protected void validateProperty(
      final Class<? extends ConfigurableProperty> configurablePropertyClass) {
    final String value = get(configurablePropertyClass);

    // TODO: guice / CDI need to register these beans / initialize the container

    PropertyHelper.validatePropertyConfiguration(configurablePropertyClass, value);
  }

  protected List<Class<? extends PropertySource>> getClasses() {
    final List<Class<? extends PropertySource>> orderedSourceClasses = new ArrayList<>();
    orderedSourceClasses.addAll(reflections.getSubTypesOf(PropertySource.class));
    Collections.sort(orderedSourceClasses, new PropertySourceComparator());

    return orderedSourceClasses;
  }

  protected void processClass(Class<? extends PropertySource> targetClass)
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
          InstantiationException {
    final PropertySource propertySource =
        targetClass.getConstructor(PropertyManager.class).newInstance(propertyManager);
    propertySource.get();
  }

  protected void decryptProperties() {
    try {
      for (final Map.Entry<Class<? extends ConfigurableProperty>, PropertyValue> entry :
          encryptedPropertyValueMap.entrySet()) {
        final Class<? extends ConfigurableProperty> keyName = entry.getKey();
        final String encryptedValue = entry.getValue().getValue();

        if (encryptedValue == null)
          throw new IllegalArgumentException("Encrypted value is null, unable to decrypt it.");

        final String plaintextValue = decryptProperty(encryptedValue);
        if (plaintextValue == null)
          throw new IllegalArgumentException(
              "Plaintext value is null, check the encrypted value is correct.");

        final PropertyValue propertyValue = new PropertyValue(entry.getValue().getPropertyType());
        propertyValue.setValue(plaintextValue);
        propertyValueMap.put(keyName, propertyValue);
      }

    } finally {
      encryptedPropertyValueMap.clear();
    }
  }

  /**
   * Sets the property to the value specified and automatically decrypts if it is a sensitive field.
   *
   * @param configurableProperty the property to set
   * @param value the value (encrypted if sensitive), NOTE that it gets stored in properties
   *     decrypted.
   */
  public void set(Class<? extends ConfigurableProperty> configurableProperty, final String value) {
    if (value == null) return;

    if (isSensitive(configurableProperty)) setSensitiveProperty(configurableProperty, value);
    else setProperty(configurableProperty, value);
  }

  protected boolean isSensitive(
      final Class<? extends ConfigurableProperty> configurablePropertyClass) {
    return configurablePropertyClass.isAnnotationPresent(Sensitive.class);
  }

  @Sensitive
  protected void setSensitiveProperty(
      Class<? extends ConfigurableProperty> configurableProperty, final String value) {
    final PropertyValue propertyValue =
        new PropertyValue(getPropertyValueType(configurableProperty));
    propertyValue.setValue(value);
    encryptedPropertyValueMap.put(configurableProperty, propertyValue);
  }

  protected void setProperty(
      Class<? extends ConfigurableProperty> configurableProperty, final String value) {
    final PropertyValue propertyValue =
        new PropertyValue(getPropertyValueType(configurableProperty));
    propertyValue.setValue(value);
    propertyValueMap.put(configurableProperty, propertyValue);
  }

  protected Class getPropertyValueType(Class<? extends ConfigurableProperty> configurableProperty) {
    if (configurableProperty.isAnnotationPresent(PropertyValueType.class))
      return configurableProperty.getAnnotation(PropertyValueType.class).value();

    return String.class;
  }

  @Sensitive
  protected String decryptProperty(final String encryptedValue) {
    return secretService.decrypt(encryptedValue);
  }

  public String get(final Class<? extends ConfigurableProperty> configurableProperty) {
    final PropertyValue propertyValue = propertyValueMap.get(configurableProperty);
    if (propertyValue != null) return propertyValue.getValue();

    return null;
  }

  public Class type(final Class<? extends ConfigurableProperty> configurableProperty) {
    final PropertyValue propertyValue = propertyValueMap.get(configurableProperty);
    if (propertyValue != null) return propertyValue.getPropertyType();

    return null;
  }
}
