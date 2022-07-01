import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BooleanSearchEngine implements SearchEngine {
    private Map<String, List<PageEntry>> indexMap = new HashMap<>();

    public BooleanSearchEngine(File pdfsDir) throws IOException {
        List<String> pdfNames = readDirectory(pdfsDir.getName());

        if (pdfNames != null) {
            for (String name : pdfNames) {
                var doc = new PdfDocument(new PdfReader(new File(name)));

                for (int i = 0; i < doc.getNumberOfPages(); i++) {
                    var text = PdfTextExtractor.getTextFromPage(doc.getPage(i + 1));
                    var words = text.split("\\P{IsAlphabetic}+");

                    Map<String, Integer> freqs = new HashMap<>();
                    Arrays.stream(words).filter(word -> !word.isEmpty()).forEach(word -> freqs.put(word.toLowerCase(),
                            freqs.getOrDefault(word.toLowerCase(), 0) + 1));

                    for (Map.Entry<String, Integer> entry : freqs.entrySet()) {
                        if (freqs.get(entry.getKey().toLowerCase()) != null) {
                            indexMap.computeIfAbsent(entry.getKey().toLowerCase(), k -> new ArrayList<>())
                                    .add(new PageEntry(new File(name).getName(), i + 1, entry.getValue()));
                        }
                    }
                    freqs.clear();
                }
                doc.close();
            }
            // Sort indexMap on indexing stage
            sort();
        }
    }

    @Override
    public List<PageEntry> search(String word) {
        if (!indexMap.isEmpty()) {
            if (indexMap.containsKey(word.toLowerCase())) {
                return indexMap.get(word.toLowerCase());
            }
        }
        return null;
    }

    private void sort() {
        if (!indexMap.isEmpty()) {
            Map<String, List<PageEntry>> sortedMap = new HashMap<>();
            indexMap.forEach((key, value) -> sortedMap.put(key, value.stream().sorted().collect(Collectors.toList())));
            indexMap.clear();
            indexMap = sortedMap;
        }
    }

    private List<String> readDirectory(String dirName) {
        if (Files.isDirectory(Paths.get(dirName))) {
            try (Stream<Path> walk = Files.walk(Paths.get(dirName))) {
                return walk.filter(Files::isRegularFile).map(Path::toString).collect(Collectors.toList());
            } catch (IOException ex) {
                ex.getMessage();
            }
        }
        return null;
    }
}
