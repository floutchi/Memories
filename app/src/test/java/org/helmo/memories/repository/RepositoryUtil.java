package org.helmo.memories.repository;

public class RepositoryUtil {

    public static void initRepository(MemoryRepository memoryRepository) {
        MemoryRepository.instance = memoryRepository;
    }
}
