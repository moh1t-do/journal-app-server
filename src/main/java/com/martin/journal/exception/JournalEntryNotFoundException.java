package com.martin.journal.exception;

import org.bson.types.ObjectId;

public class JournalEntryNotFoundException extends RuntimeException {
    public JournalEntryNotFoundException(ObjectId id) {
        super("Journal entry not found with id: " + id);
    }
}