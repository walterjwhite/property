package com.walterjwhite.property.impl;

import com.walterjwhite.property.api.PropertyManager;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.reflections.Reflections;

@RequiredArgsConstructor
public abstract class AbstractPropertyManager<PropertyClassType extends Object> {
  protected final Reflections reflections;
  protected final PropertyManager propertyManager;

  protected boolean invoked = false;

  public void call() {
    if (invoked) return;

    try {
      for (final Class propertyClass : getClasses()) {
        if (!PropertyHelper.isConcrete(propertyClass)) continue;

        try {
          processClass(propertyClass);
        } catch (InstantiationException
            | IllegalAccessException
            | NoSuchMethodException
            | InvocationTargetException e) {
          handleException(e, propertyClass);
        }
      }
    } catch (Exception e) {
      handleException(e, null);
    }

    invoked = true;
  }

  protected abstract Collection<Class<? extends PropertyClassType>> getClasses();

  protected abstract void processClass(Class<? extends PropertyClassType> targetClass)
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
          InstantiationException;

  protected void handleException(Exception e, final Class configurationClass) {}
}
