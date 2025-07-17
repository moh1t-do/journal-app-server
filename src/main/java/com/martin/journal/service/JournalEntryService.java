package com.martin.journal.service;

import com.martin.journal.entity.JournalEntry;
import com.martin.journal.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void createEntry(JournalEntry journalEntry) {
        journalEntry.setCreatedAt(LocalDateTime.now());
        journalEntry.setModifiedAt(LocalDateTime.now());
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAllJournalEntries(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getJournalEntryById(ObjectId journalEntryId){
        return journalEntryRepository.findById(journalEntryId);
    }

    public void updateJournalEntryById(ObjectId journalEntryId, JournalEntry newJournalEntry){
        JournalEntry oldJournalEntry = getJournalEntryById(journalEntryId).orElse(null);
        if (oldJournalEntry != null){
            oldJournalEntry.setTitle(newJournalEntry.getTitle().isEmpty() ? oldJournalEntry.getTitle() : newJournalEntry.getTitle());
            oldJournalEntry.setContent(newJournalEntry.getContent() == null || newJournalEntry.getContent().isEmpty() ? oldJournalEntry.getContent() : newJournalEntry.getContent());
        }
        oldJournalEntry.setModifiedAt(LocalDateTime.now());
        journalEntryRepository.save(oldJournalEntry);
    }

    public void deleteJournalEntryById(ObjectId journalEntryId){
        journalEntryRepository.deleteById(journalEntryId);
    }

}
