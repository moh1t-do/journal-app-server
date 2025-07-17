package com.martin.journal.controller;

import com.martin.journal.entity.JournalEntry;
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

    @PostMapping()
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry){
        try {
            journalEntryService.createEntry(myEntry);
            return new ResponseEntity<String>("Journal created", HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping()
    public ResponseEntity<?> getAllJournalEntries(){
        try {
            List<JournalEntry> allJournalEntries = journalEntryService.getAllJournalEntries();
            return new ResponseEntity<List<JournalEntry>>(allJournalEntries, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{journalEntryId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable String journalEntryId){
        try {
            ObjectId journalEntryObjectId = new ObjectId(journalEntryId);
            JournalEntry journalEntry = journalEntryService.getJournalEntryById(journalEntryObjectId).orElse(null);
            return new ResponseEntity<>(journalEntry, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/id/{journalEntryId}")
    public void updateJournalEntryById(@PathVariable String journalEntryId, @RequestBody JournalEntry newJournalEntry){
        try {
            ObjectId journalEntryObjectId = new ObjectId(journalEntryId);
            journalEntryService.updateJournalEntryById(journalEntryObjectId, newJournalEntry);

        }
        catch (Exception e){

        }
    }

    @DeleteMapping("/id/{journalEntryId}")
    public void deleteJournalEntryById(@PathVariable String journalEntryId){
        ObjectId journalEntryObjectId = new ObjectId(journalEntryId);
        journalEntryService.deleteJournalEntryById(journalEntryObjectId);
    }
}
