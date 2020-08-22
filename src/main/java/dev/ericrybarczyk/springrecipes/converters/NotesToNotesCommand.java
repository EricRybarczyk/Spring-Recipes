package dev.ericrybarczyk.springrecipes.converters;

import dev.ericrybarczyk.springrecipes.commands.NotesCommand;
import dev.ericrybarczyk.springrecipes.domain.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {

    @Synchronized
    @Nullable
    @Override
    public NotesCommand convert(@Nullable Notes source) {
        if (source == null) {
            return null;
        }
        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(source.getId());
        notesCommand.setRecipeNotes(source.getRecipeNotes());
        return notesCommand;
    }

}
