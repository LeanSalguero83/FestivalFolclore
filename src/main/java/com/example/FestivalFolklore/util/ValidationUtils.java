package com.example.FestivalFolklore.util;

import com.example.FestivalFolklore.exception.InvalidOperationException;

public class ValidationUtils {

    public static void validatePositiveId(Long id, String entityName) {
        if (id == null || id <= 0) {
            throw new InvalidOperationException("El ID de " + entityName + " debe ser un número positivo");
        }
    }

}