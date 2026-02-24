package com.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileCabinet implements Cabinet {

    private List<Folder> folders;

    public FileCabinet(List<Folder> folders) {
        this.folders = folders;
    }

    @Override
    public Optional<Folder> findFolderByName(String name) {
        return flattenFolders().stream()
                .filter(folder -> folder.getName().equals(name))
                .findFirst();
    }

    @Override
    public List<Folder> findFoldersBySize(String size) {
        return flattenFolders().stream()
                .filter(folder -> folder.getSize().equals(size))
                .collect(Collectors.toList());
    }

    @Override
    public int count() {
        return flattenFolders().size();
    }

    private List<Folder> flattenFolders() {
        List<Folder> allFolders = new ArrayList<>();
        if (folders != null) {
            for (Folder folder : folders) {
                traverseFolder(folder, allFolders);
            }
        }
        return allFolders;
    }

    private void traverseFolder(Folder folder, List<Folder> allFolders) {
        allFolders.add(folder);
        if (folder instanceof MultiFolder) {
            List<Folder> subFolders = ((MultiFolder) folder).getFolders();
            if (subFolders != null) {
                for (Folder subFolder : subFolders) {
                    traverseFolder(subFolder, allFolders);
                }
            }
        }
    }
}