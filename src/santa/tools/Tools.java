package santa.tools;

public class Tools {

    public static <T> T[][] deepCopy(T[][] matrix) {
        return java.util.Arrays.stream(matrix).map(el -> el.clone()).toArray($ -> matrix.clone());
    }

}
