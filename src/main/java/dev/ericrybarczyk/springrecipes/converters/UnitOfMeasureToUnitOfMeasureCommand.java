package dev.ericrybarczyk.springrecipes.converters;

import dev.ericrybarczyk.springrecipes.commands.UnitOfMeasureCommand;
import dev.ericrybarczyk.springrecipes.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasureCommand convert(@Nullable UnitOfMeasure source) {
        if (source == null) {
            return null;
        }

        UnitOfMeasureCommand command = new UnitOfMeasureCommand();
        command.setId(source.getId());
        command.setDescription(source.getDescription());
        return command;
    }
}
