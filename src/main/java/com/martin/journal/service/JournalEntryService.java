package com.martin.journal.service;

import com.martin.journal.entity.JournalEntry;
import com.martin.journal.entity.User;
import com.martin.journal.exception.JournalEntryNotFoundException;
import com.martin.journal.repository.JournalEntryRepository;
import com.martin.journal.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String username) {
        User userInDb = userService.findByUsername(username);
        journalEntry.setCreatedAt(LocalDateTime.now());
        journalEntry.setModifiedAt(LocalDateTime.now());
        JournalEntry journalEntryInDb = journalEntryRepository.save(journalEntry);
        userInDb.getJournalEntries().add(journalEntryInDb);
        userRepository.save(userInDb);
    }

    public List<JournalEntry> getAllJournalEntries(){
        return journalEntryRepository.findAll();
    }

    public JournalEntry getJournalEntryById(ObjectId journalEntryId) {
        return journalEntryRepository.findById(journalEntryId).orElseThrow(() -> new JournalEntryNotFoundException(journalEntryId));
    }

    public void updateJournalEntryById(ObjectId journalEntryId, JournalEntry newJournalEntry){
        JournalEntry oldJournalEntry = getJournalEntryById(journalEntryId);
        oldJournalEntry.setTitle(newJournalEntry.getTitle().isEmpty() ? oldJournalEntry.getTitle() : newJournalEntry.getTitle());
        oldJournalEntry.setContent(newJournalEntry.getContent() == null || newJournalEntry.getContent().isEmpty() ? oldJournalEntry.getContent() : newJournalEntry.getContent());
        oldJournalEntry.setModifiedAt(LocalDateTime.now());
        journalEntryRepository.save(oldJournalEntry);
    }

    public void deleteJournalEntryOfUserById(String username, ObjectId journalEntryId){
        User userInDb = userService.findByUsername(username);
        getJournalEntryById(journalEntryId);
        userInDb.getJournalEntries().removeIf(entry -> entry.getId().equals(journalEntryId));
        journalEntryRepository.deleteById(journalEntryId);
        userRepository.save(userInDb);
    }

}
