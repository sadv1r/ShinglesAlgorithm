import java.util.ArrayList;

/**
 * Алгоритм шинглов, базирующийся на словах.
 *
 * @author sadv1r
 * @version 1.0, 2/11/14
 */
public class Shingle {
    private static final String STOP_SYMBOLS[] = {".", ",", "!", "?", ":", ";", "-", "\\", "/", "*", "(", ")", "+", "@",
            "#", "$", "%", "^", "&", "=", "'", "\"", "[", "]", "{", "}", "|"};
    private static final String STOP_WORDS_RU[] = {"это", "как", "так", "и", "в", "над", "к", "до", "не", "на", "но", "за",
            "то", "с", "ли", "а", "во", "от", "со", "для", "о", "же", "ну", "вы",
            "бы", "что", "кто", "он", "она"};

    private static final int SHINGLE_LEN = 2;

    /**
     * Метод занимается неполной канонизацией строки. Вырезает из строки предлоги, союзы, знаки препинания
     * и прочие символы, которые не должены участвовать в сравнении.
     *
     * @param str строка, для канонизации
     * @return канонизированная строка
     */
    private String canonize(String str) {
        for (String stopSymbol : STOP_SYMBOLS) {
            str = str.replace(stopSymbol, "");
        }

        for (String stopWord : STOP_WORDS_RU) {
            str = str.replace(" " + stopWord + " ", " ");
        }

        return str;
    }

    /**
     * Метод генерирует разбивающий текст на шинглы, а затем вычисляет их контрольные суммы.
     *
     * @param strNew строка, для создания шинглов
     * @return ArrayList шинглов в числовом виде
     */
    public ArrayList<Integer> genShingle(String strNew) {
        ArrayList<Integer> shingles = new ArrayList<Integer>();
        String str = canonize(strNew.toLowerCase());
        String words[] = str.split(" ");
        int shinglesNumber = words.length - SHINGLE_LEN;

        //Create all shingles
        for (int i = 0; i <= shinglesNumber; i++) {
            String shingle = "";

            //Create one shingle
            for (int j = 0; j < SHINGLE_LEN; j++) {
                shingle = shingle + words[i+j] + " ";
            }

            shingles.add(shingle.hashCode());
        }

        return shingles;
    }

    /**
     * Метод сравнивает две последовательности шинглов
     *
     * @param textShingles1New первая последовательность шинглов
     * @param textShingles2New вторая последовательность шинглов
     * @return процент сходства шинглов
     */
    public double compare(ArrayList<Integer> textShingles1New, ArrayList<Integer> textShingles2New) {
        //textShingles1New and textShingles2New equals null bug fix
        if (textShingles1New == null || textShingles2New == null) return 0.0;

        int textShingles1Number = textShingles1New.size();
        int textShingles2Number = textShingles2New.size();

        double similarShinglesNumber = 0;

        for (int i = 0; i < textShingles1Number; i++) {
            for (int j = 0; j < textShingles2Number; j++) {
                if (textShingles1New.get(i).equals(textShingles2New.get(j))) similarShinglesNumber++;
            }
        }

        return ((similarShinglesNumber / ((textShingles1Number + textShingles2Number) / 2.0)) * 100);
    }
}
