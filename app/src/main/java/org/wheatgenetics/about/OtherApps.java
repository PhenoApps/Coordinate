package org.wheatgenetics.about;

import org.wheatgenetics.coordinate.R;

/**
 * Uses:
 * androidx.annotation.IntRange
 *
 * org.wheatgenetics.androidlibrary.R
 */
@SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class OtherApps extends Object
{
    @SuppressWarnings({"UnnecessaryEnumModifier"}) public enum Index
    {
        NONE(-1), FIELD_BOOK(0), INVENTORY(1),
            COORDINATE(2), ONE_KK(3);

        @androidx.annotation.IntRange(from = -1, to = 3) private final int position;

        private Index(@androidx.annotation.IntRange(from = -1, to = 3) final int position)
        { this.position = position; }
    }

    // region Constants
    @SuppressWarnings({"CStyleArrayDeclaration"}) private static final String
        ALL_TEXTS[] = {"Field Book", "Inventory", "Coordinate", "1KK"};
    @SuppressWarnings({"CStyleArrayDeclaration"}) private static final Integer
        ALL_RES_IDS[] = {
            R.drawable.ic_field_book,
            R.drawable.ic_inventory ,
            R.drawable.ic_coordinate,
            R.drawable.ic_1kk       };
    @SuppressWarnings({"CStyleArrayDeclaration"}) private static final String
        ALL_URIS[] = {
            "https://play.google.com/store/apps/details?id=com.fieldbook.tracker"       ,
            "https://play.google.com/store/apps/details?id=org.wheatgenetics.inventory" ,
            "https://play.google.com/store/apps/details?id=org.wheatgenetics.coordinate",
            "https://play.google.com/store/apps/details?id=org.wheatgenetics.onekk"     };
    // endregion

    // region Fields
    @SuppressWarnings({"CStyleArrayDeclaration"}) private final String texts[];
    @SuppressWarnings({"CStyleArrayDeclaration"}) private final String uris [];
    @SuppressWarnings({"CStyleArrayDeclaration"})
        private final Integer resIds[];
    // endregion

    private static <T> T[] removeElement(
    @SuppressWarnings({"CStyleArrayDeclaration"}) final T allTs[],
    final Index suppressIndex)
    {
        if (null == allTs)
            return null;
        else
        {
            @SuppressWarnings({"CStyleArrayDeclaration"}) final T result[] =
                java.util.Arrays.copyOf(allTs,allTs.length - 1);
            {
                int i = 0;
                for (final Index index:
                Index.values())
                    if (Index.NONE != index
                    &&  suppressIndex                                != index)
                        result[i++] = allTs[index.position];
            }
            return result;
        }
    }

    OtherApps(final Index suppressIndex)
    {
        super();

        if (Index.NONE == suppressIndex)
        {
            this.texts  = OtherApps.ALL_TEXTS  ;
            this.resIds = OtherApps.ALL_RES_IDS;
            this.uris   = OtherApps.ALL_URIS   ;
        }
        else
        {
            // noinspection RedundantTypeArguments
            this.texts = OtherApps.<String>removeElement(
                OtherApps.ALL_TEXTS, suppressIndex);
            this.resIds = OtherApps.<Integer>removeElement(
                OtherApps.ALL_RES_IDS, suppressIndex);
            // noinspection RedundantTypeArguments
            this.uris = OtherApps.<String>removeElement(
                OtherApps.ALL_URIS, suppressIndex);
        }
    }

    // region Package Methods
    String [] getTexts () { return this.texts ; }
    Integer[] getResIds() { return this.resIds; }
    String [] getUris  () { return this.uris  ; }
    // endregion
}