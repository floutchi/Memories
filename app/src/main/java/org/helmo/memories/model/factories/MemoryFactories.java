package org.helmo.memories.model.factories;

import org.helmo.memories.model.Memory;

public class MemoryFactories {


    public static Memory createMemory(String title, String description, String imagePath, String date, double lattitude, double longitude) throws Exception {
        String verifyTitle = onVerifyTitle(title);
        String verifyDecription = onVerifyDescription(description);
        String verifyDate = onVerifyDate(date);
        double veryLattitude = onVerifyGPS(lattitude);
        double veryLongitude = onVerifyGPS(longitude);
        String verifyImagePath = onVerifyImagePath(imagePath);
        return new Memory(verifyTitle, verifyDecription, verifyImagePath, verifyDate, false, veryLattitude, veryLongitude);

    }

    private static double onVerifyGPS(double cord) throws Exception {
        /*if (cord == 0.0d){
            throw new Exception("Lieu manquant");
        }*/
        return cord;
    }

    private static String onVerifyDate(String date) throws Exception {
        if (!date.equals("Ajouter une date")){
            return date.trim();
        }
        throw new Exception("Date manquante");
    }

    private static String onVerifyImagePath(String imagePath) throws Exception {
        if (!(imagePath == null)){
            return imagePath.trim();
        }
        throw new Exception("Image manquante");
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
