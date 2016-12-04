package de.hszg.apps.playground.util;

import java.util.Comparator;

import de.hszg.apps.playground.model.Publication;

/**
 * Projectname: MobileApp
 * Created on 04.12.2016.
 */

public class PublicationListComparator implements Comparator<Publication> {

    private PublicationCompareEnum publicationCompareEnum;

    public PublicationListComparator(PublicationCompareEnum publicationCompareEnum) {
        this.publicationCompareEnum = publicationCompareEnum;
    }

    @Override
    public int compare(Publication o1, Publication o2) {
        switch(publicationCompareEnum) {
            case ID:
                return compareID(o1, o2);
            case ENTRY_DATE:
                return compareEntryDate(o1, o2);
            case REVISION_DATE:
                return compareRevisionDate(o1, o2);
            case TITLE:
                return o2.getTitle().compareTo(o2.getTitle());
            case STATUS:
                return compareStatus(o1, o2);
            case NUMBER_OF_COMMENTS:
                return o1.getNumberOfComments() - o2.getNumberOfComments();
            default:
                return 0;
        }
    }

    private int compareID(Publication o1, Publication o2) {
        return extractNumberFromID(o1)-(extractNumberFromID(o2));
    }

    private int extractNumberFromID(Publication publication) {
        try{
            String publicationWithoutChar = publication.getId().replace("PUB_", "");
            return Integer.valueOf(publicationWithoutChar);
        }catch(NumberFormatException e) {
            return 0;
        }
    }

    private int compareEntryDate(Publication o1, Publication o2) {
        return o1.getEntryDate().compareTo(o2.getEntryDate());
    }

    private int compareRevisionDate(Publication o1, Publication o2) {
        return o1.getRevisionDate().compareTo(o2.getRevisionDate());
    }

    private int compareStatus(Publication o1, Publication o2) {
        if(o1.getStatus() == o2.getStatus())
            return 0;
        if(o1.getStatus() == "abgeschlossen" & o2.getStatus() == "in Bearbeitung") {
            return -1;
        }else{
            return 1;
        }
    }

}
