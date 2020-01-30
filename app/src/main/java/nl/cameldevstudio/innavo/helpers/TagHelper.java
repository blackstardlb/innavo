package nl.cameldevstudio.innavo.helpers;

/**
 * Helper class for getting {@link android.util.Log} tag
 */
public class TagHelper {
    /**
     * Get {@link android.util.Log} tag.
     *
     * @return {@link android.util.Log} tag
     */
    public static String getTag() {
        String tag = "";
        final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
        for (int i = 0; i < ste.length; i++) {
            if (ste[i].getMethodName().equals("getTag")) {
                tag = "(" + ste[i + 1].getFileName() + ":" + ste[i + 1].getLineNumber() + ")";
            }
        }
        return tag;
    }
}
