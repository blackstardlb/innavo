package nl.cameldevstudio.innavo.helpers;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;

public class FirebaseUtils {
    public static Query startsWith(String field, String query, CollectionReference collectionReference) {
        if (!query.isEmpty()) {
            String firstChars = "";
            if (query.length() > 1) {
                firstChars = query.substring(0, query.length() - 1);
            }
            char lastChar = query.charAt(query.length() - 1);

            String end = firstChars + String.valueOf((char) (lastChar + 1));
            return collectionReference
                    .whereGreaterThanOrEqualTo(field, query)
                    .whereLessThan(field, end);
        }
        return collectionReference;
    }

    public static Query startsWith(String field, String query, Query aQuery) {
        if (!query.isEmpty()) {
            String firstChars = query.substring(0, query.length() - 1);
            char lastChar = query.charAt(query.length());

            String end = firstChars + String.valueOf((char) (lastChar + 1));
            return aQuery
                    .whereGreaterThanOrEqualTo(field, query)
                    .whereLessThan(field, end);
        }
        return aQuery;
    }
}
