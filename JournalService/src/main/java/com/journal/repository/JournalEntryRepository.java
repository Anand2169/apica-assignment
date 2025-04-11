package com.journal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.journal.model.JournalEntry;

public interface JournalEntryRepository extends JpaRepository<JournalEntry, Long> {

}
