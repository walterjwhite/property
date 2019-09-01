package com.walterjwhite.property.cli.commons;

import com.walterjwhite.inject.cli.service.CLIParser;
import com.walterjwhite.property.api.property.ConfigurableProperty;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.cli.*;

public class CommonsCLIParser implements CLIParser {
  @Override
  public Map<Class<? extends ConfigurableProperty>, String> parseCommandLineArguments(
      Iterable<Class<? extends ConfigurableProperty>> configurableProperties, String[] arguments) {
    final Map<Class<? extends ConfigurableProperty>, String> commandLinePropertyMap =
        new HashMap<>();

    try {
      final Map<Integer, Option> optionMap = new HashMap<>();

      final Options options = buildOptions(configurableProperties, optionMap);
      final CommandLine commandLine = new DefaultParser().parse(options, arguments);

      parseCommandLineOptions(
          configurableProperties, commandLinePropertyMap, optionMap, commandLine);
    } catch (ParseException e) {
      throw (new RuntimeException(e));
    }

    return commandLinePropertyMap;
  }

  private static void parseCommandLineOptions(
      final Iterable<Class<? extends ConfigurableProperty>> configurableProperties,
      final Map<Class<? extends ConfigurableProperty>, String> commandLinePropertyMap,
      final Map<Integer, Option> optionMap,
      final CommandLine commandLine) {
    int i = 0;
    for (final Class<? extends ConfigurableProperty> configurableProperty :
        configurableProperties) {
      Option option = optionMap.get(i++);

      if (commandLine.hasOption(option.getOpt())) {
        commandLinePropertyMap.put(
            configurableProperty, commandLine.getOptionValue(option.getOpt()));
      }
    }
  }

  private static Options buildOptions(
      Iterable<Class<? extends ConfigurableProperty>> configurableProperties,
      final Map<Integer, Option> optionMap) {
    final Options options = new Options();

    int i = 0;
    for (Class<? extends ConfigurableProperty> configurablePropertyClass : configurableProperties) {
      final Option option =
          new Option(
              configurablePropertyClass.getSimpleName(), true, configurablePropertyClass.getName());
      options.addOption(option);
      optionMap.put(i++, option);
    }

    return (options);
  }
}
