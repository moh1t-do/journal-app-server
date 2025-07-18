package com.martin.journal.controller;

import com.martin.journal.entity.JournalEntry;
import com.martin.journal.exception.JournalEntryNotFoundException;
import com.martin.journal.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @PostMapping()
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry){
        try {
            journalEntryService.createEntry(myEntry);
            return ResponseEntity.accepted().build();
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping()
    public ResponseEntity<?> getAllJournalEntries(){
        try {
            List<JournalEntry> allJournalEntries = journalEntryService.getAllJournalEntries();
            return new ResponseEntity<List<JournalEntry>>(allJournalEntries, HttpStatus.CREATED);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping("/id/{journalEntryId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable String journalEntryId) {
        try {
            JournalEntry journalEntry = journalEntryService.getJournalEntryById(new ObjectId(journalEntryId));
            return ResponseEntity.ok(journalEntry);
        }catch (JournalEntryNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/id/{journalEntryId}")
    public ResponseEntity<?> updateJournalEntryById(@PathVariable String journalEntryId, @RequestBody JournalEntry newJournalEntry){
        try {
            journalEntryService.updateJournalEntryById(new ObjectId(journalEntryId), newJournalEntry);
            return ResponseEntity.accepted().build();
        }
        catch (JournalEntryNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/id/{journalEntryId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable String journalEntryId){
        try {
            journalEntryService.deleteJournalEntryById(new ObjectId(journalEntryId));
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
