package com.martin.journal.controller;

import com.martin.journal.entity.JournalEntry;
import com.martin.journal.exception.JournalEntryNotFoundException;
import com.martin.journal.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @PostMapping("/id/{username}")
    public ResponseEntity<?> saveEntry(@PathVariable String username, @RequestBody JournalEntry myEntry){
        try {
            journalEntryService.saveEntry(myEntry, username);
            return ResponseEntity.accepted().build();
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/id/{username}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String username){
        try {
            List<JournalEntry> allJournalEntries = journalEntryService.getAllJournalEntries();
            return new ResponseEntity<List<JournalEntry>>(allJournalEntries, HttpStatus.ACCEPTED);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping("/id/{username}/{journalEntryId}")
    public ResponseEntity<?> getJournalEntryOfUserById(@PathVariable ObjectId journalEntryId) {
        try {
            JournalEntry journalEntry = journalEntryService.getJournalEntryById(journalEntryId);
            return ResponseEntity.ok(journalEntry);
        }catch (JournalEntryNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/id/{username}/{journalEntryId}")
    public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId journalEntryId, @RequestBody JournalEntry newJournalEntry){
        try {
            journalEntryService.updateJournalEntryById(journalEntryId, newJournalEntry);
            return ResponseEntity.accepted().build();
        }
        catch (JournalEntryNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/id/{username}/{journalEntryId}")
    public ResponseEntity<?> deleteJournalEntryOfUserById(@PathVariable String username, @PathVariable ObjectId journalEntryId){
        try {
            journalEntryService.deleteJournalEntryOfUserById(username, journalEntryId);
            return ResponseEntity.accepted().build();
        }
        catch (JournalEntryNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
