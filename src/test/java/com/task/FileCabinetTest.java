package com.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class FileCabinetTest {

    class SimpleFolder implements Folder {
        private String name;
        private String size;

        public SimpleFolder(String name, String size) {
            this.name = name;
            this.size = size;
        }

        @Override public String getName() { return name; }
        @Override public String getSize() { return size; }
    }

    class SimpleMultiFolder extends SimpleFolder implements MultiFolder {
        private List<Folder> folders;

        public SimpleMultiFolder(String name, String size, List<Folder> folders) {
            super(name, size);
            this.folders = folders;
        }

        @Override public List<Folder> getFolders() { return folders; }
    }

    private FileCabinet cabinet;

    @BeforeEach
    void setUp() {
        Folder file1 = new SimpleFolder("Photos", "LARGE");
        Folder file2 = new SimpleFolder("Documents", "SMALL");
        Folder file3 = new SimpleFolder("Resume", "SMALL");
        MultiFolder multiFolder = new SimpleMultiFolder("Important", "MEDIUM", List.of(file2, file3));

        cabinet = new FileCabinet(List.of(file1, multiFolder));
    }

    @Test
    void shouldReturnCorrectTotalCountOfFolders() {
        assertEquals(4, cabinet.count(), "Cabinet should count exactly 4 folders");
    }

    @Test
    void shouldFindExistingFolderByName() {
        Optional<Folder> found = cabinet.findFolderByName("Resume");

        assertTrue(found.isPresent(), "Should find 'Resume' folder");
        assertEquals("Resume", found.get().getName(), "Found folder name should match");
    }

    @Test
    void shouldReturnEmptyOptionalWhenFolderDoesNotExist() {
        Optional<Folder> found = cabinet.findFolderByName("Secret_Passwords");

        assertTrue(found.isEmpty(), "Should return empty Optional for non-existent folder");
    }

    @Test
    void shouldFindAllFoldersOfGivenSize() {
        List<Folder> smallFolders = cabinet.findFoldersBySize("SMALL");

        assertEquals(2, smallFolders.size(), "Should find exactly 2 folders with SMALL size");
    }

    @Test
    void shouldReturnEmptyListWhenNoFoldersMatchSize() {
        List<Folder> unknownSizeFolders = cabinet.findFoldersBySize("EXTRA_LARGE");

        assertTrue(unknownSizeFolders.isEmpty(), "List should be empty for non-existent size");
    }
}