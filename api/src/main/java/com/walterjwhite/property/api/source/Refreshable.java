package com.walterjwhite.property.api.source;

/**
 * Indicates that the property or property source or property target is refreshable (updatable).
 * TODO: does this mean we need to periodically "refresh", or can we auto-detect, or do we just call
 * refresh when we need the value again?
 */
// TODO: add callback on change?
// TODO: add interval to refresh the value?
public interface Refreshable {}
