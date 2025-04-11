package com.journal.controller.rest;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.journal.model.JournalEntry;
import com.journal.repository.JournalEntryRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/journal")
@RequiredArgsConstructor
public class JournalController {

    private final JournalEntryRepository journalEntryRepository;

    @GetMapping
    @PreAuthorize("hasRole('Admin')")
    public List<JournalEntry> getAllEntries() {
        return journalEntryRepository.findAll();
    }
}
