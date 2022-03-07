package org.helmo.memories.model.factories;

import org.helmo.memories.model.Memory;

public class MemoryFactories {


    public static Memory createMemory(String title, String description, String imagePath, String date, double lattitude, double longitude) throws Exception {
        String verifyTitle = onVerifyTitle(title);
        String verifyDecription = onVerifyDescription(description);
        String verifyDate = onVerifyDate(date);
        double veryLattitude = lattitude;
        double veryLongitude = longitude;
        String verifyImagePath = imagePath;
        return new Memory(verifyTitle, verifyDecription, verifyImagePath, verifyDate, false, veryLattitude, veryLongitude);

    }

    private static String onVerifyDate(String date) throws Exception {
        if (!date.equals("Ajouter une date")){
            return "";
        }

        return date;
    }

    private static String onVerifyDescription(String description) throws Exception {
        if (!description.trim().isEmpty() ){
            return description.trim();
        }
        throw new Exception("Description manquante");
    }

    private static String onVerifyTitle(String title) throws Exception {
        if (!title.trim().isEmpty() ){
            return title.trim();
        }
        throw new Exception("Titre manquant");
    }

}
